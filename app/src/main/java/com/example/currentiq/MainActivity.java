package com.example.currentiq;

import static com.example.currentiq.SplashActivity.list;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.currentiq.databinding.ActivityMainBinding;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Model> allList;
    Model model;
    int postion = 0;
    ActivityMainBinding binding;
    CountDownTimer countDownTimer;
    int timervalue=20;
    int correctCount = 0;
    int wrongCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        allList = list;
        Collections.shuffle(list);
        model = allList.get(postion);
        setAllQues();
        binding.nextbtn.setClickable(false);
        binding.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialog, id) -> {
                            finish();
                        })
                        .setNegativeButton("No", null)  // Dismiss dialog when user clicks "No"
                        .show();
            }
        });

    }

    private void setAllQues() {
        startTimer();
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
                dialog.findViewById(R.id.try_again).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                });
                dialog.show();
            }
        }.start();


    }

    public void correct(CardView cardView) {
        cardView.setCardBackgroundColor(getColor(R.color.green));
        binding.nextbtn.setClickable(true);
        binding.nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctCount++;
                if (postion<allList.size()-1){
                    postion++;
                    model = allList.get(postion);
                    resetColor();
                    cancelTimer();
                    setAllQues();
                    setCardEnable();
                } else {
                    gamewon();
                }

            }
        });
    }

    public void wrong(CardView cardView) {
        cardView.setCardBackgroundColor(getColor(R.color.red));
        binding.nextbtn.setClickable(true);
        binding.nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrongCount++;
                if (postion < allList.size() - 1) {
                    postion++;
                    model = allList.get(postion);
                    cancelTimer();
                    setAllQues();
                    resetColor();
                    setCardEnable();

                } else {
                    gamewon();
                }
            }
        });

    }

    private void gamewon() {
        startActivity(new Intent(this, WinActivity.class)
                .putExtra("correct",correctCount).putExtra("wrong",wrongCount));
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
        binding.nextbtn.setClickable(true);
        if (model.getopA().equals(model.getAns())) {
            binding.cardOpA.setCardBackgroundColor((getResources().getColor(R.color.teal_700)));
            if (postion < allList.size() - 1) {
                correct(binding.cardOpA);
            } else {
                gamewon();
            }
        } else {
            wrong(binding.cardOpA);
        }
    }

    public void optionB(View view) {
        setCardDesable();
        binding.nextbtn.setClickable(true);
        if (model.getOpB().equals(model.getAns())) {
            binding.cardOpB.setCardBackgroundColor((getResources().getColor(R.color.teal_700)));
            if (postion < allList.size() - 1) {
                correct(binding.cardOpB);
            } else {
                gamewon();
            }
        } else {
            wrong(binding.cardOpB);
        }
    }

    public void optionC(View view) {
        setCardDesable();
        binding.nextbtn.setClickable(true);
        if (model.getOpC().equals(model.getAns())) {
            binding.cardOpC.setCardBackgroundColor((getResources().getColor(R.color.teal_700)));
            if (postion < allList.size() - 1) {
                correct(binding.cardOpC);
            } else {
                gamewon();
            }
        } else {
            wrong(binding.cardOpC);
        }
    }

    public void optionD(View view) {
        setCardDesable();
        binding.nextbtn.setClickable(true);
        if (model.getOpD().equals(model.getAns())) {
            binding.cardOpD.setCardBackgroundColor((getResources().getColor(R.color.teal_700)));
            if (postion < allList.size() - 1) {
                correct(binding.cardOpD);
            } else {
                gamewon();
            }
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