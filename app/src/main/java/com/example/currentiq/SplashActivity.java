package com.example.currentiq;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    public static List<Model> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        list=new ArrayList<>();
        list.add((new Model("Which planet in the solar system is known as the “Red Planet ?","Venus","Earth","Mars","Jupiter","Mars")));
        list.add((new Model("Who wrote the novel “War and Peace”?","Anton Chekhov","Fyodor Dostoevsky","Leo Tolstoy","Ivan Turgenev","Leo Tolstoy")));
        list.add((new Model("What is the capital of Japan?","Beijing","Tokyo","Seoul","Bangkok","Tokyo")));
        list.add((new Model("Which river is the longest in the world?","Amazon","Mississippi","Nile","Yangtze","Nile")));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        },2000);
    }
}