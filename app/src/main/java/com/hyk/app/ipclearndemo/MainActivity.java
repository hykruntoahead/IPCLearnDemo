package com.hyk.app.ipclearndemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserManager.sUserId ++ ;
        Log.d(TAG,"user_Id"+UserManager.sUserId);
    }

    public void startSecond(View view) {
        startActivity(new Intent(this,SecondActivity.class));
    }
}
