package com.example.i5.crudlista.Recuerdame;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.i5.crudlista.R;
import com.example.i5.crudlista.Services.AlarmReceiver;
import com.example.i5.crudlista.Services.MiIntentService;
import com.example.i5.crudlista.data.RecuerdameDbHelper;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity{

    //private ProgressBar pbarProgreso;
    public static final String EXTRA_RECUERDAME_ID = "extra_lawyer_id";

    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

        //pbarProgreso = (ProgressBar)findViewById(R.id.pbarProgreso);

        RecuerdameFragment recuerdameFragment = (RecuerdameFragment)
                getSupportFragmentManager().findFragmentById(R.id.recuerdame_container);


        if(recuerdameFragment == null){
            recuerdameFragment = RecuerdameFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.recuerdame_container, recuerdameFragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //cargar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_iniciar:
                Toast.makeText(getApplicationContext(),"Iniciamos Alarma",Toast.LENGTH_SHORT).show();
                startAlarm();
                return true;
            case R.id.menu_detener:
                Toast.makeText(getApplicationContext(),"Detemos Alarma",Toast.LENGTH_SHORT).show();
                cancel();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cancel(){
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Notificacion cancelada", Toast.LENGTH_SHORT).show();
    }


    public void startAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 60;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
    }
}
