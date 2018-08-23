package com.hyk.app.ipclearndemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = getProName();
        Log.d(TAG, "application start ,process name :" + processName);
    }


    private String getProName() {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                processName = process.processName;
            }
        }

        return processName;
    }
}
