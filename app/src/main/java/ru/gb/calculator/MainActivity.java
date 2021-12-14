package ru.gb.calculator;

import static ru.gb.calculator.R.drawable.button_square_blue;
import static ru.gb.calculator.R.drawable.button_square_blue_pressed;
import static ru.gb.calculator.R.drawable.button_square_green;
import static ru.gb.calculator.R.drawable.button_square_green_pressed;
import static ru.gb.calculator.R.drawable.button_square_grey;
import static ru.gb.calculator.R.drawable.button_square_grey_pressed;
import static ru.gb.calculator.R.drawable.button_square_red;
import static ru.gb.calculator.R.drawable.button_square_red_pressed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button_0, button_1, button_2, button_3, button_4, button_5,
            button_6, button_7, button_8, button_9,
            button_dot, button_negative,
            button_reset,
            button_put_in_memory, button_get_from_memory,
            button_plus, button_minus, button_multiply, button_divide,
            button_equal;
    TextView display;
    static Calculator calculator;

    private final static String DISPLAY_TEXT = "DISPLAY_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (calculator == null) calculator = new Calculator(this);

        getSupportActionBar().hide(); // скрыть заголовок окна

        display = findViewById(R.id.display);

        button_0 = initializeButton(R.id.button_0, Buttons.ZERO, button_square_grey_pressed, button_square_grey);
        button_1 = initializeButton(R.id.button_1, Buttons.ONE, button_square_grey_pressed, button_square_grey);
        button_2 = initializeButton(R.id.button_2, Buttons.TWO, button_square_grey_pressed, button_square_grey);
        button_3 = initializeButton(R.id.button_3, Buttons.THREE, button_square_grey_pressed, button_square_grey);
        button_4 = initializeButton(R.id.button_4, Buttons.FOUR, button_square_grey_pressed, button_square_grey);
        button_5 = initializeButton(R.id.button_5, Buttons.FIVE, button_square_grey_pressed, button_square_grey);
        button_6 = initializeButton(R.id.button_6, Buttons.SIX, button_square_grey_pressed, button_square_grey);
        button_7 = initializeButton(R.id.button_7, Buttons.SEVEN, button_square_grey_pressed, button_square_grey);
        button_8 = initializeButton(R.id.button_8, Buttons.EIGHT, button_square_grey_pressed, button_square_grey);
        button_9 = initializeButton(R.id.button_9, Buttons.NEGATIVE, button_square_grey_pressed, button_square_grey);
        button_dot = initializeButton(R.id.button_dot, Buttons.DOT, button_square_grey_pressed, button_square_grey);
        button_negative = initializeButton(R.id.button_negative, Buttons.NEGATIVE, button_square_grey_pressed, button_square_grey);
        button_reset = initializeButton(R.id.button_reset, Buttons.RESET, button_square_red_pressed, button_square_red);
        button_put_in_memory = initializeButton(R.id.button_put_in_memory, Buttons.PUT_MEMORY, button_square_green_pressed, button_square_green);
        button_get_from_memory = initializeButton(R.id.button_get_from_memory, Buttons.GET_MEMORY, button_square_green_pressed, button_square_green);
        button_plus = initializeButton(R.id.button_plus, Buttons.PLUS, button_square_blue_pressed, button_square_blue);
        button_minus = initializeButton(R.id.button_minus, Buttons.MINUS, button_square_blue_pressed, button_square_blue);
        button_multiply = initializeButton(R.id.button_multiply, Buttons.MULTIPLY, button_square_blue_pressed, button_square_blue);
        button_divide = initializeButton(R.id.button_divide, Buttons.DIVIDE, button_square_blue_pressed, button_square_blue);
        button_equal = initializeButton(R.id.button_equal, Buttons.EQUAL, button_square_red_pressed, button_square_red);
    }

    @SuppressLint("ClickableViewAccessibility")
    private Button initializeButton(int button_id, Buttons button_value, int pressed_image, int unpressed_image) {
        Button button = findViewById(button_id);
        button.setOnTouchListener((v, event) -> onTouchListener(button, event, pressed_image, unpressed_image));
        button.setOnClickListener(v -> display.setText(calculator.put(button_value)));
        return button;
    }

    public boolean onTouchListener(Button button, MotionEvent event, int pressed_image, int unpressed_image) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            button.setBackgroundResource(pressed_image);
        if (event.getAction() == MotionEvent.ACTION_UP)
            button.setBackgroundResource(unpressed_image);
        return false;
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        display.setText(savedInstanceState.getString(DISPLAY_TEXT));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DISPLAY_TEXT, display.getText().toString());
    }
}