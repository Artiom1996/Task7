package com.epam.androidlab.task7;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;


public class SleepService extends IntentService {

    private int mId = 1;

    public SleepService() {
        super("SleepService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        long endTime = System.currentTimeMillis() + 60000;
        while (System.currentTimeMillis() < endTime) {

            synchronized (this) {

                try {
                    wait(endTime - System.currentTimeMillis());
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(this)
                                    .setSmallIcon(R.drawable.ic_service)
                                    .setContentTitle(getString(R.string.text_my_notification))
                                    .setContentText(getString(R.string.text_service_finished));


                    Intent resultIntent = new Intent(this, MainActivity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

                    stackBuilder.addParentStack(MainActivity.class);

                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(
                                    0,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    mBuilder.setContentIntent(resultPendingIntent);
                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    mNotificationManager.notify(mId, mBuilder.build());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, R.string.text_starting_service, Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }
}
