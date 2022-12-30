package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class TimingActivity extends AppCompatActivity {
    private Button btn_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timing);

        btn_main =findViewById(R.id.btn_main4);

        btn_main.setOnClickListener( v -> {
            Intent intent =new Intent(TimingActivity.this,MainActivity.class);
            startActivity(intent);
        });
    }
}