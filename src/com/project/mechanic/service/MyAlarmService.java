package com.project.mechanic.service;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.project.mechanic.MainActivity;
import com.project.mechanic.R;


public class MyAlarmService extends Service

{

    private NotificationManager mManager;


    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub  
        super.onCreate();
    }


    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);

        mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), MainActivity.class);

        Notification notification = new Notification(R.drawable.ic_launcher, "Mechanic", System.currentTimeMillis());

        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.setLatestEventInfo(this.getApplicationContext(), "servise is runnig", "this service runnig in specified time\r\for set time go to onCreat MainActivity part set calender\r\for chang cod for runnig go to MyAlarmService in package service", pendingNotificationIntent);

        mManager.notify(0, notification);
    }


    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}