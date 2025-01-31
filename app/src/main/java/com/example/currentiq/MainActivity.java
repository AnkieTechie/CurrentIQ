package com.example.currentiq;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.currentiq.databinding.ActivityMainBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public List<Model> allList;
    public Model model;
    public int postion = 0;
    public int total = 0;
    ActivityMainBinding binding;
    CountDownTimer countDownTimer;
    int timervalue=20;
    int correctCount = 0;
    int wrongCount = 0;
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String catId=getIntent().getStringExtra("catId");
        // Change status bar color
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#FE190632"));


        fetchques(catId);
        binding.nextbtn.setClickable(false);
        binding.backbtn.setOnClickListener(v -> finish());
        binding.exit.setOnClickListener(v -> new AlertDialog.Builder(MainActivity.this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> moveTaskToBack(true))
                .setNegativeButton("No", null)  // Dismiss dialog when user clicks "No"
                .show());

    }

    private void fetchques(String catId) {
        db.collection("category").document(catId).collection("question")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        allList=new ArrayList<>();
                        QuerySnapshot result=task.getResult();
                        for (DocumentSnapshot snapshot : result) {
                            Model questionModel=snapshot.toObject(Model.class);
                            allList.add(questionModel);
                        }
                        if (allList.isEmpty()){
                            Toast.makeText(getApplicationContext(), "No questions available", Toast.LENGTH_SHORT).show();
                        } else {
                            Collections.shuffle(allList);
                            model = allList.get(postion);
                            setAllQues();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Error fetching questions", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    private void setAllQues() {
        startTimer();
        binding.nextbtn.setClickable(false);
        binding.ques.setText(model.getQues());
        binding.opA.setText(model.getopA());
        binding.opB.setText(model.getOpB());
        binding.opC.setText(model.getOpC());
        binding.opD.setText(model.getOpD());
    }

    private void startTimer() {
        timervalue=20;
        binding.progressBar.setProgress(0);
        binding.progressBar.setMax(timervalue);
        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timervalue = timervalue - 1;
                binding.progressBar.setProgress(timervalue);
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(MainActivity.this, R.style.dialog);
                dialog.setContentView(R.layout.custom_dialoge);
                dialog.findViewById(R.id.try_again).setOnClickListener(v -> {
                    resetQuiz();
                    dialog.dismiss();
                });
                dialog.show();
            }
        }.start();

    }
    private void resetQuiz() {
        postion=0;
        correctCount = 0;
        wrongCount = 0;
        Collections.shuffle(allList);
        postion=(postion+1) % allList.size();
        model = allList.get(postion);
        resetColor();
        cancelTimer();
        setAllQues();
        setCardEnable();
        binding.nextbtn.setClickable(false);
    }

    public void correct(CardView cardView) {
        cardView.setCardBackgroundColor(getColor(R.color.green));
        binding.nextbtn.setClickable(true);
        binding.nextbtn.setOnClickListener(v -> {
            correctCount++;
            total++;
            if (postion<allList.size()-1){
                postion++;
                model = allList.get(postion);
                resetColor();
                cancelTimer();
                setAllQues();
                setCardEnable();
            } else {
                countDownTimer.cancel();
                binding.nextbtn.setClickable(false);
                gamewon();
            }

        });
    }

    public void wrong(CardView cardView) {
        cardView.setCardBackgroundColor(getColor(R.color.red));
        binding.nextbtn.setClickable(true);
        binding.nextbtn.setOnClickListener(v -> {
            wrongCount++;
            total++;
            if (postion < allList.size() - 1) {
                postion++;
                model = allList.get(postion);
                cancelTimer();
                setAllQues();
                resetColor();
                setCardEnable();

            } else {
                binding.nextbtn.setClickable(false);
                countDownTimer.cancel();
                gamewon();
            }
        });
    }

    private void gamewon() {
        startActivity(new Intent(this, WinActivity.class)
                .putExtra("correct",correctCount).
                putExtra("wrong",wrongCount).putExtra("total",total));
    }

    public void setCardEnable() {
        binding.cardOpA.setEnabled(true);
        binding.cardOpB.setEnabled(true);
        binding.cardOpC.setEnabled(true);
        binding.cardOpD.setEnabled(true);
    }

    public void setCardDesable() {
        binding.cardOpA.setEnabled(false);
        binding.cardOpB.setEnabled(false);
        binding.cardOpC.setEnabled(false);
        binding.cardOpD.setEnabled(false);
    }

    public void resetColor() {
        binding.cardOpA.setCardBackgroundColor(getResources().getColor(R.color.white));
        binding.cardOpB.setCardBackgroundColor(getResources().getColor(R.color.white));
        binding.cardOpC.setCardBackgroundColor(getResources().getColor(R.color.white));
        binding.cardOpD.setCardBackgroundColor(getResources().getColor(R.color.white));
    }

    public void optionA(View view) {
        setCardDesable();
        if (model.getopA().equals(model.getAns())) {
            binding.cardOpA.setCardBackgroundColor((getResources().getColor(R.color.teal_700)));
            correct(binding.cardOpA);

        } else {
            wrong(binding.cardOpA);
        }
    }

    public void optionB(View view) {
        setCardDesable();
        if (model.getOpB().equals(model.getAns())) {
            binding.cardOpB.setCardBackgroundColor((getResources().getColor(R.color.teal_700)));
                correct(binding.cardOpB);
        } else {
            wrong(binding.cardOpB);
        }
    }

    public void optionC(View view) {
        setCardDesable();
        if (model.getOpC().equals(model.getAns())) {
            binding.cardOpC.setCardBackgroundColor((getResources().getColor(R.color.teal_700)));
                correct(binding.cardOpC);
        } else {
            wrong(binding.cardOpC);
        }
    }

    public void optionD(View view) {
        setCardDesable();
        if (model.getOpD().equals(model.getAns())) {
            binding.cardOpD.setCardBackgroundColor((getResources().getColor(R.color.teal_700)));
                correct(binding.cardOpD);
        } else {
            wrong(binding.cardOpD);
        }
    }
    public void cancelTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // This will stop the timer
            binding.progressBar.setProgress(20);
        }
    }
}