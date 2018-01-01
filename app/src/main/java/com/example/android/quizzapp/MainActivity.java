package com.example.android.quizzapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String EXTRA_MESSAGE = "com.example.android.quizzapp.MESSAGE";
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //hiding keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mProgressBar = findViewById(R.id.pb_loading_indicator);

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.startButton) {
                final Intent intent = new Intent(this, QuizActivity.class);
                EditText name = findViewById(R.id.name);
                String userName = name.getText().toString();
                if (userName.isEmpty()) userName = getString(R.string.username);
                intent.putExtra(EXTRA_MESSAGE, userName);
                mProgressBar.setVisibility(View.VISIBLE);
                startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.exitApp)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }
    /*
    void createAlert(String message) {
        new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitFromApp();
                    }
                })
                .setPositiveButton(R.string.new_game, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                }).create().show();
    }*/
}