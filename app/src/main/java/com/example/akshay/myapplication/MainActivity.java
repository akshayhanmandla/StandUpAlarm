package com.example.akshay.myapplication;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import static android.net.Uri.parse;

public class MainActivity extends AppCompatActivity {

    ToggleButton alarm;
    NotificationManager notificationManager;
    int NOTIFICATION_ID=0;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        alarm=findViewById(R.id.toggle);

        Intent notifyIntent=new Intent(this,MyReceiver.class);

        notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        boolean alarmUp = (PendingIntent.getBroadcast(this,0,notifyIntent,PendingIntent.FLAG_NO_CREATE)!=null);

        alarm.setChecked(alarmUp);

        final PendingIntent notifyPendingIntent=PendingIntent.getBroadcast(this,NOTIFICATION_ID,
                notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        final AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        alarm.setOnCheckedChangeListener
                (
                new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        String toast_message;
                        if(isChecked)
                        {
                            long repeatInterval=AlarmManager.INTERVAL_FIFTEEN_MINUTES;

                            long triggerTime= SystemClock.elapsedRealtime()+repeatInterval;

                            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, repeatInterval,
                                    notifyPendingIntent);
                            //deliverNotification(getApplicationContext());
                            toast_message="Stand Up Alarm On!";
                        }
                        else
                        {
                            toast_message="Stand Up Alarm Off!";
                            notificationManager.cancelAll();
                            alarmManager.cancel(notifyPendingIntent);
                        }
                        Toast.makeText(MainActivity.this,toast_message,Toast.LENGTH_LONG).show();
                    }
                }

                );


    }

}
