package com.example.currentiq;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.example.currentiq.databinding.ActivityWinBinding;

public class WinActivity extends AppCompatActivity {
    ActivityWinBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Change status bar color
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#FE190632"));

        int total = getIntent().getIntExtra("total", 0);
        int wrong = getIntent().getIntExtra("wrong", 0);
        int i = total - wrong;
        binding.circularProgressBar.setProgress(i);
        binding.circularProgressBar.setProgressMax(total);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                finishAffinity();
            }
        });

        binding.result.setText("You Score is\n " + i + "/" + total);

        binding.share.setOnClickListener(v -> {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "CurrentIQ");
                String shareMessage = "\nHey! I got "+i+" out of "+total+" ! Think you can do better? \nPlay now and share your score!";
                /*
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
               */
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, DashboardActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

        finishAffinity();
    }
}