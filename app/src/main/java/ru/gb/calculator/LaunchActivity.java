package ru.gb.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class LaunchActivity extends AppCompatActivity {

    private MaterialButton button_start_day_theme;
    private MaterialButton button_start_night_theme;

    public static final String THEME_SELECTION = "THEME_SELECTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        button_start_day_theme = findViewById(R.id.theme_day);
        button_start_night_theme = findViewById(R.id.theme_night);
        button_start_day_theme.setOnClickListener((v)->{
            startCalculator("Calculator");
        });

        button_start_night_theme.setOnClickListener((v)->{
            startCalculator("Calculator.Night");
        });
    }

    private void startCalculator(String themeName){
        Intent intent  = new Intent(this, MainActivity.class);
        intent.putExtra(THEME_SELECTION,themeName);
        startActivity(intent);
    }
}