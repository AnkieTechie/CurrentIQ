package com.example.currentiq;

import static com.example.currentiq.SplashActivity.list;

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
    int timervalue = 20;
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
                dialog.show();
                countDownTimer.cancel();

            }
        }.start();
    }

    private void setAllQues() {
        timervalue = 20;
        startTimer();
        binding.ques.setText(model.getQues());
        binding.opA.setText(model.getopA());
        binding.opB.setText(model.getOpB());
        binding.opC.setText(model.getOpC());
        binding.opD.setText(model.getOpD());
    }

    private void startTimer() {


    }

    public void correct(CardView cardView) {
        cardView.setCardBackgroundColor(getColor(R.color.teal_200));
        binding.nextbtn.setClickable(true);
        binding.nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctCount++;
                postion++;
                model = allList.get(postion);
                setAllQues();
                resetColor();
                setEnable();
            }
        });
    }

    public void wrong(CardView cardView) {
        cardView.setCardBackgroundColor(getColor(R.color.purple_500));
        binding.nextbtn.setClickable(true);
        binding.nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrongCount++;
                if (postion < allList.size() - 1) {
                    postion++;
                    model = allList.get(postion);
                    setAllQues();
                    resetColor();
                    setEnable();
                } else {
                    gamewon();
                }
            }
        });

    }

    private void gamewon() {
        startActivity(new Intent(this, DashboardActivity.class));
    }

    public void setEnable() {
        binding.cardOpA.setEnabled(true);
        binding.cardOpB.setEnabled(true);
        binding.cardOpC.setEnabled(true);
        binding.cardOpD.setEnabled(true);
    }

    public void setDesable() {
        binding.cardOpA.setEnabled(false);
        binding.cardOpB.setEnabled(false);
        binding.cardOpC.setEnabled(false);
        binding.cardOpD.setEnabled(false);
    }

    public void resetColor() {
        binding.cardOpA.setBackgroundColor(getResources().getColor(R.color.white));
        binding.cardOpB.setBackgroundColor(getResources().getColor(R.color.white));
        binding.cardOpC.setBackgroundColor(getResources().getColor(R.color.white));
        binding.cardOpD.setBackgroundColor(getResources().getColor(R.color.white));
    }

    public void optionA(View view) {
        setDesable();
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
        setDesable();
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
        setDesable();
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
        setDesable();
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
}