package com.example.android.quizzapp;

        import android.app.Activity;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.res.Resources;
        import android.graphics.Color;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.support.v7.app.AlertDialog;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.ImageView;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.TextView;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{

    Button submit;
    int score = 0, buttonState = 1;
    String userName, message;
    ImageView picResult;

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

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.submit:
                if (buttonState == 1){
                    getRadioAnswers();
                    getCheckBoxAnswers();
                    checkAnswer();
                } else resetAnswer();
                //Toast.makeText(getApplicationContext(), "Your score is "  + score, Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private void checkAnswer(){
        TextView scoreResult = (TextView) findViewById(R.id.scoreResult);
        picResult = (ImageView) findViewById(R.id.pic_answer);
        message = getTheMessage(score, picResult);
        scoreResult.setText(message);
        scoreResult.setVisibility(View.VISIBLE);
        picResult.setVisibility(View.VISIBLE);
        submit.setText(getString(R.string.again));
        score = 0;
        buttonState = 0;
    }

    private void resetAnswer(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        TextView scoreResult = (TextView) findViewById(R.id.scoreResult);
        ImageView picResult = (ImageView) findViewById(R.id.pic_answer);
        scoreResult.setVisibility(View.GONE);
        picResult.setVisibility(View.GONE);
        submit.setText(getString(R.string.again));
        clearCheckBoxAnswers();
        clearRadioAnswers();
        buttonState = 1;
    }

    //calculates RadioButton question
    private void getRadioAnswers(){
        int i = 0;
        Resources res = getResources();
        String[] answerRB = res.getStringArray(R.array.correctRadioGroupsArr);
        for (int idRadioGroup : allRadioGroupsArr){
            RadioGroup currRadioGroup = (RadioGroup) findViewById(idRadioGroup);
            if (currRadioGroup.getCheckedRadioButtonId() != -1) {
                String textRadioButton = ((RadioButton) findViewById(currRadioGroup.getCheckedRadioButtonId())).getText().toString();
                if (textRadioButton.equals(answerRB[i])) score++;
            }
            i++;
        }
    }

    //calculates CheckBoxes question
    private void getCheckBoxAnswers(){
        int i = 0, k = 0;
        Resources res = getResources();
        String[] answerCB = res.getStringArray(R.array.correctCheckBoxesArr);

        for (int idCB : allCheckBoxesArr){
            CheckBox currCheckBox = (CheckBox) findViewById(idCB);
            if (currCheckBox.isChecked()){
                String textCheckBox = ((CheckBox) findViewById(idCB)).getText().toString();
                if (textCheckBox.equals(answerCB[i])) k++;
            }
            i++;
        }
        if (k == 3) score++;
    }

    private void clearRadioAnswers(){
        for (int idRB : allRadioGroupsArr){
            RadioGroup currRadioGroup = (RadioGroup) findViewById(idRB);
            currRadioGroup.clearCheck();
        }
    }

    private void clearCheckBoxAnswers(){
        for (int idCB : allCheckBoxesArr){
            CheckBox currCheckBox = (CheckBox) findViewById(idCB);
            if (currCheckBox.isChecked()) currCheckBox.setChecked(false);
        }
    }

    //set the result message after Submit button clicked
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
    @Override
    public void onBackPressed() {
        AlertDialog.Builder exit = new AlertDialog.Builder(QuizActivity.this);
        exit
                .setMessage(R.string.exitApp)
                .setNegativeButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                exit();
                            }
                        })
                .setPositiveButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });

        AlertDialog alertDialog_exit = exit.create();
        alertDialog_exit.show();
        Button p = alertDialog_exit.getButton(DialogInterface.BUTTON_POSITIVE);
        p.setTextColor(Color.BLACK);
        Button n = alertDialog_exit.getButton(DialogInterface.BUTTON_NEGATIVE);
        n.setTextColor(Color.BLACK);
    }

    public void exit() {
        Intent setMainActivity = new Intent(Intent.ACTION_MAIN);
        setMainActivity.addCategory(Intent.CATEGORY_HOME);
        setMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setMainActivity);
    }
}