package com.example.currentiq;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.currentiq.databinding.ActivityDashboardBinding;
import com.example.currentiq.databinding.ActivityWinBinding;

public class WinActivity extends AppCompatActivity {
    ActivityWinBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int correct=getIntent().getIntExtra("correct",0);
        int wrong=getIntent().getIntExtra("wrong",0);
        int i=4-wrong;
        binding.circularProgressBar.setProgress(i);
        binding.result.setText("You Score is\n "+i+"/4");

        binding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "CurrentIQ");
                    String shareMessage= "\nI got "+i+" out of 4";/*
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                   */ shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });
    }
}