package com.project.mechanic.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;


public class HelloService extends Service {

    private int     mInterval = 5000; // 5 seconds by default, can be changed later
    private Handler mHandler;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //        mHandler = new Handler();
        //        mHandler.postDelayed(mStatusChecker, mInterval);

        return Service.START_FLAG_REDELIVERY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void startAlert(View view) {

        //        your code for running in background in period time  
        //        Toast.makeText(this, "Service is runnig",
        //                Toast.LENGTH_SHORT).show();
    }

    Runnable mStatusChecker = new Runnable() {

                                @Override
                                public void run() {
                                    startAlert(null);
                                    mHandler.postDelayed(mStatusChecker, mInterval);
                                }
                            };


    void startRepeatingTask() {
        mStatusChecker.run();
    }


    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }
}
