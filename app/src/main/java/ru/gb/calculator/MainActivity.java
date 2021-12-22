package ru.gb.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {

    MaterialButton button_0, button_1, button_2, button_3, button_4, button_5,
                   button_6, button_7, button_8, button_9,
                   button_dot, button_negative,
                   button_reset,
                   button_put_in_memory, button_get_from_memory,
                   button_plus, button_minus, button_multiply, button_divide,
                   button_equal;
    MaterialTextView display;
    private Calculator calculator;
    private String themeName;
    private final String themeDayName = "Calculator";
    private final String themeNightName = "Calculator.Night";

    private static final String DISPLAY_TEXT = "DISPLAY_TEXT";
    public static final String CALCULATOR = "CALCULATOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        themeName = sharedPreferences.getString(LaunchActivity.THEME_SELECTION,"Calculator");

        String launchThemeName = getIntent().getStringExtra(LaunchActivity.THEME_SELECTION);
        if (!themeName.equals(launchThemeName)) {
            switch (launchThemeName){
                case themeDayName:
                    setTheme(R.style.Calculator);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
                case themeNightName:
                    setTheme(R.style.Calculator);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
            }
        }
        if (calculator == null) calculator = new Calculator(this);

        display = findViewById(R.id.display);

        button_0 = initializeButton(R.id.button_0, Buttons.ZERO);
        button_1 = initializeButton(R.id.button_1, Buttons.ONE);
        button_2 = initializeButton(R.id.button_2, Buttons.TWO);
        button_3 = initializeButton(R.id.button_3, Buttons.THREE);
        button_4 = initializeButton(R.id.button_4, Buttons.FOUR);
        button_5 = initializeButton(R.id.button_5, Buttons.FIVE);
        button_6 = initializeButton(R.id.button_6, Buttons.SIX);
        button_7 = initializeButton(R.id.button_7, Buttons.SEVEN);
        button_8 = initializeButton(R.id.button_8, Buttons.EIGHT);
        button_9 = initializeButton(R.id.button_9, Buttons.NINE);
        button_dot = initializeButton(R.id.button_dot, Buttons.DOT);
        button_negative = initializeButton(R.id.button_negative, Buttons.NEGATIVE);
        button_reset = initializeButton(R.id.button_reset, Buttons.RESET);
        button_put_in_memory = initializeButton(R.id.button_put_in_memory, Buttons.PUT_MEMORY);
        button_get_from_memory = initializeButton(R.id.button_get_from_memory, Buttons.GET_MEMORY);
        button_plus = initializeButton(R.id.button_plus, Buttons.PLUS);
        button_minus = initializeButton(R.id.button_minus, Buttons.MINUS);
        button_multiply = initializeButton(R.id.button_multiply, Buttons.MULTIPLY);
        button_divide = initializeButton(R.id.button_divide, Buttons.DIVIDE);
        button_equal = initializeButton(R.id.button_equal, Buttons.EQUAL);
    }

    @SuppressLint("ClickableViewAccessibility")
    private MaterialButton initializeButton(int button_id, Buttons button_value) {
        MaterialButton button = findViewById(button_id);
        button.setOnClickListener(v -> display.setText(calculator.put(button_value)));
        return button;
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calculator = savedInstanceState.getParcelable(CALCULATOR);
        calculator.setActivity(this);
        display.setText(savedInstanceState.getString(DISPLAY_TEXT));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CALCULATOR,calculator);
        outState.putString(DISPLAY_TEXT, display.getText().toString());


    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LaunchActivity.THEME_SELECTION, themeName);
    }
}