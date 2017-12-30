package com.example.android.quizzapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    public final static String EXTRA_MESSAGE = "com.example.android.quizzapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //REVU Я без понятия что это. Чтото мягкое :)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //REVU Студия говорит что кастинг не нужен
        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //REVU switch тут излишен и break тоже
        switch (view.getId()) {
            case R.id.startButton:
                //REVU интент можно назвать типа startQuizActivityIntent
                //Хотя я не знаю BestPractices на этот счет
                Intent intent = new Intent(this, QuizActivity.class);

                //REVU Студия говорит что кастинг не нужен
                //Не знаю скажется ли на скорости, можно попробовать name вынести в поле класса
                EditText name = (EditText) findViewById(R.id.name);
                String userName = name.getText().toString();
                //REVU Ктото говорит, что если в одну строку то скобки можно не ставить
                //Ктото против. Я стараюсь всегда {} ставить
                if (userName.isEmpty()) userName = getString(R.string.username);

                intent.putExtra(EXTRA_MESSAGE, userName);
                startActivity(intent);
                break;
            //REVU else { ErrorTextView.setText("Введите имя!")} и не стартуем активити
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.exitApp)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}