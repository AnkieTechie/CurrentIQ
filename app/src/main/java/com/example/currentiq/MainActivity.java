package com.example.currentiq;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.currentiq.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    CountDownTimer countDownTimer;
    int timervalue=20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        countDownTimer=new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timervalue=timervalue-1;
                binding.progressBar.setProgress(timervalue);
            }

            @Override
            public void onFinish() {

                // Apply the blur effect to the root view behind the dialog

                Dialog dialog=new Dialog(MainActivity.this, R.style.dialog);
                dialog.setContentView(R.layout.custom_dialoge);
                dialog.show();
            }
        }.start();


    }
}