package com.hyk.app.ipclearndemo;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hyk.app.ipclearndemo.utils.MyConstants;
import com.hyk.app.ipclearndemo.utils.MyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserManager.sUserId++;
        Log.d(TAG, "user_Id" + UserManager.sUserId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        persistToFile();
    }

    public void startSecond(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }

    private void persistToFile() {
        new Thread(
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        User user = new User(1, "hello world", false);
                        File dir = new File(MyConstants.CHAPTER_2_PATH);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        File cachedFile = new File(MyConstants.CACHE_FILE_PATH);
                        ObjectOutputStream objectOutputStream = null;
                        Log.d(TAG, "persist user :" + user);
                        try {
                            objectOutputStream = new ObjectOutputStream(
                                    new FileOutputStream(cachedFile));
                            objectOutputStream.writeObject(user);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            MyUtils.close(objectOutputStream);
                        }

                    }
                })
        ).start();
    }
}
