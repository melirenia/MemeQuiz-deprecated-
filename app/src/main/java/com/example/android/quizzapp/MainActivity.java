package com.example.android.quizzapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    public final static String EXTRA_MESSAGE = "com.example.android.quizzapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startButton:
                Intent intent = new Intent(this, QuizActivity.class);

                EditText name = (EditText) findViewById(R.id.name);
                String userName = name.getText().toString();
                if (userName.length() == 0) userName = getString(R.string.username);

                intent.putExtra(EXTRA_MESSAGE, userName);
                startActivity(intent);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder exit = new AlertDialog.Builder(MainActivity.this);
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
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}