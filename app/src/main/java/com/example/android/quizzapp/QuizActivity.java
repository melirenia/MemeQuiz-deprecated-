package com.example.android.quizzapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private Button submit;
    private TextView scoreResult;
    private int score = 0, buttonState = 1;
    private String userName, message;
    private ImageView picResult;
    private ScrollView getScrollView;
    /**
     * ID array for all RadioGroups
     */
    private int[] allRadioGroupsArr = {
            R.id.answers1,
            R.id.answers2,
            R.id.answers3,
            R.id.answers5,
            R.id.answers6,
            R.id.answers7,
            R.id.answers8
    };
    /**
     * ID array for all RadioGroups with correct answers
     */
    private int[] allCheckBoxesArr = {
            R.id.radio4_1,
            R.id.radio4_2,
            R.id.radio4_3,
            R.id.radio4_4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);

        scoreResult = findViewById(R.id.scoreResult);
        picResult = findViewById(R.id.pic_answer);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit) {
                if (buttonState == 1) {
                    getRadioAnswers();
                    getCheckBoxAnswers();
                    checkAnswer();
                    scrollDialogDown();
                } else {
                    resetAnswer();
                }
        }
    }

    private void checkAnswer() {
        message = getTheMessage(score, picResult);
        scoreResult.setText(message);
        scoreResult.setVisibility(View.VISIBLE);
        picResult.setVisibility(View.VISIBLE);
        submit.setText(getString(R.string.again));
        score = 0;
        buttonState = 0;
    }

    private void resetAnswer() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        scoreResult.setVisibility(View.GONE);
        picResult.setVisibility(View.GONE);
        submit.setText(getString(R.string.again));
        clearCheckBoxAnswers();
        clearRadioAnswers();
        buttonState = 1;
    }

    //calculates RadioButton question
    private void getRadioAnswers() {
        int i = 0;
        Resources res = getResources();
        String[] answerRB = res.getStringArray(R.array.correctRadioGroupsArr);
        for (int idRadioGroup : allRadioGroupsArr) {
            RadioGroup currRadioGroup = findViewById(idRadioGroup);
            if (currRadioGroup.getCheckedRadioButtonId() != -1) {
                String textRadioButton = ((RadioButton) findViewById(currRadioGroup.getCheckedRadioButtonId())).getText().toString();
                if (textRadioButton.equals(answerRB[i])) {
                    score++;
                }
            }
            i++;
        }
    }

    //calculates CheckBoxes question
    private void getCheckBoxAnswers() {
        int i = 0, k = 0;
        Resources res = getResources();
        String[] answerCB = res.getStringArray(R.array.correctCheckBoxesArr);
        for (int idCB : allCheckBoxesArr) {
            CheckBox currCheckBox = findViewById(idCB);
            if (currCheckBox.isChecked()) {
                String textCheckBox = ((CheckBox) findViewById(idCB)).getText().toString();
                for (int j = 0; j < answerCB.length; j++) {
                    if (textCheckBox.equals(answerCB[j])) k++;
                }
            }
            i++;
        }
        if (k == 3) {
            score++;
        }
    }

    //clear RadioButtons questions
    private void clearRadioAnswers() {
        for (int idRB : allRadioGroupsArr) {
            RadioGroup currRadioGroup = findViewById(idRB);
            currRadioGroup.clearCheck();
        }
    }

    //clear CheckBoxes questions
    private void clearCheckBoxAnswers() {
        for (int idCB : allCheckBoxesArr) {
            CheckBox currCheckBox = findViewById(idCB);
            if (currCheckBox.isChecked()) currCheckBox.setChecked(false);
        }
    }

    //set the result message after Submit button is clicked
    private String getTheMessage(int score, ImageView picResult) {
        Intent getQuizActivity = getIntent();
        userName = getQuizActivity.getStringExtra(MainActivity.EXTRA_MESSAGE);
        message = getString(R.string.dear) + userName + "!\n" + getString(R.string.messageBase) + score;
        if (score > 6) {
            message += getString(R.string.messageresultGenius);
            picResult.setImageResource(R.drawable.answer1);
        } else if (score > 3) {
            message += getString(R.string.messageresultCaptain);
            picResult.setImageResource(R.drawable.answer2);
        } else {
            message += getString(R.string.messageresultSlowpoke);
            picResult.setImageResource(R.drawable.answer3);
        }
        return message;
    }

    //scrolls to the bottom of the screen when Submit button is pressed
    private void scrollDialogDown() {
        getScrollView = findViewById(R.id.scrollview);
        getScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                getScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 100);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.exitQuiz)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                }).create().show();
    }
}