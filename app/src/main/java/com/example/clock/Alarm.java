package com.example.clock;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver {
    static  MediaPlayer mp;
    private Boolean close =true;
    private Boolean op =false;
    @Override
    public void onReceive (Context context, Intent intent)
    {
        mp = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mp.start();
        Toast.makeText(context, "Wakeup", Toast.LENGTH_LONG).show();
        AlarmActivity.dialog.setTitle("鬧鐘響了");
        AlarmActivity.dialog.setMessage(AlarmActivity.message);

        AlarmActivity.dialog.setPositiveButton("關閉鬧鐘", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mp.pause();
            }
        });
        AlarmActivity.dialog.setNeutralButton("再睡10秒鐘", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try{
                    // delay 5 mins
                    mp.pause();
                    dialogInterface.dismiss();
                    Thread.sleep(10000);
                    mp.start();
                    AlarmActivity.dialog.show();

                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        AlarmActivity.dialog.show();
    }

}
