package com.example.clock;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.media.MediaPlayer;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimingActivity extends AppCompatActivity {
    private Button btn_main,btn_alarm,btn_stopwatch;//切換頁面按鈕宣告
    static long Durtime;
    private long leftTime = Durtime;
    private Chronometer chronometer;
    private Button btn_start,btn_Reset,settime,btn_picker;
    private EditText etmin,etsec;
    private TextView tv;
    private Boolean timing_flag = true;

    //////////////////
    private TimePickerDialog timePickerDialog;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    //Calendar calendar;
    int second;
    int minute;


   @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timing);

        initView();
        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTimeSet(TimePicker view, int min, int sec) {
                DecimalFormat decimalFormat = new DecimalFormat("00");
                Durtime = min*60 + sec;
                leftTime = Durtime;
                setChronometerText();
            }
        };

        //切換首頁頁面
        btn_main.setOnClickListener( v -> {
            Intent intent =new Intent(TimingActivity.this,MainActivity.class);
            startActivity(intent);
        });
        //切換鬧鐘頁面
        btn_alarm =findViewById(R.id.btn_alarm4);
        btn_alarm.setOnClickListener( v -> {
            Intent intent =new Intent(this,AlarmActivity.class);
            startActivity(intent);
        });
        //切換碼表頁面
        btn_stopwatch =findViewById(R.id.btn_stopwatch4);
        btn_stopwatch.setOnClickListener( v -> {
            Intent intent =new Intent(this,StopwatchActivity.class);
            startActivity(intent);
        });


    }
    public void picker(View view) {
        timePickerDialog = new TimePickerDialog(this, onTimeSetListener, minute,second, true);
        timePickerDialog.show();
    }

    private void initView() {
        chronometer = findViewById(R.id.chronometer);
        btn_main =findViewById(R.id.btn_main4);
        btn_start = findViewById(R.id.btnStart);
        btn_Reset = findViewById(R.id.btnReset);
        settime = findViewById(R.id.settime);
        etmin = findViewById(R.id.etmin);
        etsec = findViewById(R.id.etsec);
        btn_picker =findViewById(R.id.picker);
        tv =findViewById(R.id.textView4);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timing_flag){
                    chronometer.start();
                    btn_start.setText("暫停");
                    timing_flag =false;
                    settime.setVisibility(View.INVISIBLE);
                    etmin.setVisibility(View.INVISIBLE);
                    etsec.setVisibility(View.INVISIBLE);
                    tv.setVisibility(View.INVISIBLE);
                    btn_picker.setVisibility(View.INVISIBLE);
                    btn_Reset.setVisibility(View.VISIBLE);
                }else {
                    chronometer.stop();
                    btn_start.setText("繼續");
                    timing_flag =true;
                }
            }
        });
        btn_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                leftTime = Durtime;
                setChronometerText();
                btn_start.setText("開始");
                timing_flag =true;

                settime.setVisibility(View.VISIBLE);
                etmin.setVisibility(View.VISIBLE);
                etsec.setVisibility(View.VISIBLE);
                tv.setVisibility(View.VISIBLE);
                btn_picker.setVisibility(View.VISIBLE);
                btn_Reset.setVisibility(View.INVISIBLE);
            }
        });

        settime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minute = Integer.parseInt(etmin.getText().toString());
                second = Integer.parseInt(etsec.getText().toString());
                Durtime = minute*60 + second;
                leftTime = Durtime;
                setChronometerText();
                chronometer.stop();
            }
        });
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                leftTime--;
                setChronometerText();
                if(leftTime == 0 ){
                    Alarm.mp.start();

                    Toast.makeText(TimingActivity.this,"時間到！",Toast.LENGTH_SHORT).show();
                    chronometer.stop();
                    AlertDialog.Builder dialog =new AlertDialog.Builder(TimingActivity.this);
                    dialog.setTitle("時間到了!");

                    dialog.setPositiveButton("關閉鬧鐘", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Alarm.mp.pause();
                        }
                    });
                    dialog.show();
                    return;
                }
            }
        });
        setChronometerText();
    }

    public void setChronometerText()
    {
        Date d = new Date(leftTime * 1000);
        SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
        chronometer.setText(timeFormat.format(d));
    }
}