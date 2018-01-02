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
    private EditText name;

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
                name = findViewById(R.id.name);
                String userName = name.getText().toString();
                if (userName.isEmpty()) userName = getString(R.string.username);
                intent.putExtra(EXTRA_MESSAGE, userName);
                mProgressBar.setVisibility(View.VISIBLE);
                startActivity(intent);
                //call the destroy method to kill MainActivity intent after redirecting to QuizActivity
                finish();
        }
    }
/*
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.exitApp)
                .setCancelable(false)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitFromApp();
                    }
                }).create().show();
    }

    public void exitFromApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }*/
}