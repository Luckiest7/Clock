package com.example.clock;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_main,btn_stopwatch,btn_timing;//切換頁面按鈕宣告

    private TimePicker tp;      //TimePicker是選擇時間元件
    private Button btn_set,btn_cancel;
    private EditText ed_message;
    static String message;
    static AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        dialog =new AlertDialog.Builder(AlarmActivity.this);

        ed_message =findViewById(R.id.ed_message);



        //回首頁功能
        btn_main =findViewById(R.id.btn_main2);
        btn_main.setOnClickListener( v -> {
            Intent intent =new Intent(this,MainActivity.class);
            startActivity(intent);
        });
        //切換碼表頁面
        btn_stopwatch =findViewById(R.id.btn_stopwatch2);
        btn_stopwatch.setOnClickListener( v -> {
            Intent intent =new Intent(this,StopwatchActivity.class);
            startActivity(intent);
        });

        //切換計時頁面
        btn_timing =findViewById(R.id.btn_timing2);
        btn_timing.setOnClickListener( v -> {
            Intent intent =new Intent(this,TimingActivity.class);
            startActivity(intent);
        });

        //鬧鐘功能
        btn_set = findViewById(R.id.set_alarm);
        tp = findViewById(R.id.time);
        btn_set.setOnClickListener(this);

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener( view -> {
            AlarmManager alarm2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);;
            Intent intent = new Intent(this, Alarm.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
            alarm2.cancel(pendingIntent);
            Toast.makeText(this, "取消成功", Toast.LENGTH_SHORT).show();
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view){
        //取得現在時間
        Calendar cal = Calendar.getInstance();      //獲取Calendar實體
        cal.set(Calendar.HOUR_OF_DAY,tp.getHour());    //設置鬧鐘小時數
        cal.set(Calendar.MINUTE,tp.getMinute());       //設置鬧鐘分鐘數
        cal.set(Calendar.SECOND,0);                    //設置鬧鐘秒數

        Alarm_set(cal.getTimeInMillis());           //設置鬧鐘   cal.getTimeInMillis()是取得以毫秒為單位的時間
    }


    private void Alarm_set(long timeInMillis)
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE); //獲取AlarmManager對象
        Intent intent = new Intent(this, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
        //AlarmManager類別底下有很多鬧鐘方法，RTC_WAKEUP是採用實際時間，並在休眠狀台下能夠提醒系統，參考bilibili
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeInMillis,5000,pendingIntent);
        message =ed_message.getText().toString().trim();
        Toast.makeText(this, "鬧鐘設置成功", Toast.LENGTH_SHORT).show();
    }


}