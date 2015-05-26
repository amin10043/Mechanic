package com.project.mechanic.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;


public class HelloService extends Service {

    private int     mInterval = 5000; // 5 seconds by default, can be changed later
    private Handler mHandler;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 54);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if (System.currentTimeMillis() > cal.getTimeInMillis()) {
            cal.setTimeInMillis(cal.getTimeInMillis() + 24 * 60 * 60 * 1000);// Okay, then tomorrow ...
        }
        mHandler = new Handler();
        mHandler.postDelayed(mStatusChecker, mInterval);

        return Service.START_FLAG_REDELIVERY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void startAlert(View view) {

        Toast.makeText(this, "Service is runnig",
                Toast.LENGTH_SHORT).show();
        //        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
        //                + (20 * 1000), null);
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
