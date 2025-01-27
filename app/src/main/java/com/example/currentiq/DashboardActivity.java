package com.example.currentiq;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.currentiq.databinding.ActivityMainBinding;

public class DashboardActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}