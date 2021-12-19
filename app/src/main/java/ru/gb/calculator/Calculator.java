package ru.gb.calculator;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Calculator implements Parcelable {
    private String firstValue = "";
    private String secondValue = "";
    private String memoValue = "";
    private String resultValue = "";
    private CurrentState currentState = CurrentState.FIRST_NUM_INPUT;
    private Operator operator = Operator.NONE;
    private AppCompatActivity activity;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public Calculator(AppCompatActivity activity) {
        this.activity = activity;
    }

    protected Calculator(Parcel in) {
        firstValue = in.readString();
        secondValue = in.readString();
        memoValue = in.readString();
        resultValue = in.readString();
        currentState = CurrentState.values()[in.readInt()];
        operator = Operator.values()[in.readInt()];
    }

    public static final Creator<Calculator> CREATOR = new Creator<Calculator>() {
        @Override
        public Calculator createFromParcel(Parcel in) {
            return new Calculator(in);
        }

        @Override
        public Calculator[] newArray(int size) {
            return new Calculator[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstValue);
        dest.writeString(secondValue);
        dest.writeString(memoValue);
        dest.writeString(resultValue);
        dest.writeInt(currentState.ordinal());
        dest.writeInt(operator.ordinal());
    }

    private enum CurrentState {
        FIRST_NUM_INPUT, OPERATOR_INPUT, SECOND_NUM_INPUT, EQUAL
    }

    private enum Operator {
        NONE, PLUS, MINUS, MULTIPLY, DIVIDE
    }

    public String put(Buttons button) {
        String str = activity.getString(R.string.error_result);
        if (firstValue.equals(str) && button != Buttons.RESET) return "";
        if (currentState == CurrentState.OPERATOR_INPUT &&
                            button != Buttons.PLUS &&
                            button != Buttons.MINUS &&
                            button != Buttons.MULTIPLY &&
                            button != Buttons.DIVIDE &&
                            button != Buttons.NEGATIVE) reset();
        switch (button) {
            case ZERO:
            case ONE:
            case TWO:
            case THREE:
            case FOUR:
            case FIVE:
            case SIX:
            case SEVEN:
            case EIGHT:
            case NINE:
                switch (currentState) {
                    case FIRST_NUM_INPUT:
                        firstValue += button.getValue();
                        break;
                    case SECOND_NUM_INPUT:
                        secondValue += button.getValue();
                        break;
                }
                break;
            case DOT:
                switch (currentState) {
                    case FIRST_NUM_INPUT:
                        if (!firstValue.contains(","))
                            firstValue += button.getValue();
                        break;
                    case SECOND_NUM_INPUT:
                        if (!secondValue.contains(","))
                            secondValue += button.getValue();
                        break;
                }
                break;
            case NEGATIVE:
                switch (currentState) {
                    case FIRST_NUM_INPUT:
                        firstValue = invertNumber(firstValue);
                        break;
                    case SECOND_NUM_INPUT:
                        secondValue = invertNumber(secondValue);
                        break;
                }
                break;
            case PLUS:
                operator = Operator.PLUS;
                currentState = CurrentState.SECOND_NUM_INPUT;
                break;
            case MINUS:
                operator = Operator.MINUS;
                currentState = CurrentState.SECOND_NUM_INPUT;
                break;
            case MULTIPLY:
                operator = Operator.MULTIPLY;
                currentState = CurrentState.SECOND_NUM_INPUT;
                break;
            case DIVIDE:
                operator = Operator.DIVIDE;
                currentState = CurrentState.SECOND_NUM_INPUT;
                break;
            case EQUAL:
                return calculateResult();
            case RESET:
                reset();
                break;
            case PUT_MEMORY:
                switch (currentState) {
                    case FIRST_NUM_INPUT:
                        memoValue = firstValue;
                        break;
                    case SECOND_NUM_INPUT:
                        memoValue = secondValue;
                        break;
                }
                break;
            case GET_MEMORY:
                switch (currentState) {
                    case FIRST_NUM_INPUT:
                        firstValue = memoValue;
                        break;
                    case SECOND_NUM_INPUT:
                        secondValue = memoValue;
                        break;
                }
                break;
        }
        return this.toString();
    }

    public String invertNumber(String value) {
        String result;
        try {
            result = String.valueOf(-1 * Long.parseLong(value));
        } catch (Exception e) {
            result = String.valueOf(-1 * Double.parseDouble(value));
        }
        return result;
    }

    public void reset() {
        firstValue = "";
        secondValue = "";
        operator = Operator.NONE;
        currentState = CurrentState.FIRST_NUM_INPUT;
    }

    @NonNull
    @Override
    public String toString() {
        String retValue;
        if (currentState == CurrentState.EQUAL) {
            return resultValue;
        }
        retValue = firstValue;
        if (currentState == CurrentState.FIRST_NUM_INPUT || currentState == CurrentState.OPERATOR_INPUT)
            return retValue;
        switch (operator) {
            case PLUS:
                retValue += " + ";
                break;
            case MINUS:
                retValue += " - ";
                break;
            case MULTIPLY:
                retValue += " * ";
                break;
            case DIVIDE:
                retValue += " / ";
                break;
        }
        if (!secondValue.equals(""))
            retValue += secondValue;
        return retValue;
    }

    private String calculateResult() {
        try {
            Double firstNumber = Double.valueOf(firstValue);
            Double secondNumber = Double.valueOf(secondValue);
            double result;
            switch (operator) {
                case PLUS:
                    result = firstNumber + secondNumber;
                    break;
                case MINUS:
                    result = firstNumber - secondNumber;
                    break;
                case MULTIPLY:
                    result = firstNumber * secondNumber;
                    break;
                default:
                    result = firstNumber / secondNumber;
            }
            resultValue = String.valueOf(result);
            firstValue = resultValue;
            secondValue = "";
            operator = Operator.NONE;
            currentState = CurrentState.OPERATOR_INPUT;
            return resultValue;
        } catch (NumberFormatException e) {
            firstValue = activity.getString(R.string.error_result);
            return firstValue;
        }
    }
}
