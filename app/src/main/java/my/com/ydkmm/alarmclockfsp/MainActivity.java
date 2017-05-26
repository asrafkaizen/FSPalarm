package my.com.ydkmm.alarmclockfsp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    final String TAG = getClass().getSimpleName();

    Button btnSet, btnCancel;
    TimePicker timePicker;
    TextView textView;
    String timeString = "", hour = "", minute = "", alarmInfo = "";
    Date time = new Date();
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(TAG, "onCreate");
        btnSet = (Button) findViewById(R.id.btnSet);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        textView = (TextView) findViewById(R.id.textView);

        alarmInfo = textView.getText().toString();

        //this method get time from timePicker and assign it to a string & a date
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hour = Integer.toString(timePicker.getHour()); //todo(1) for API 23 and above only
                //minute = Integer.toString(timePicker.getMinute());
                //timeString = hour + minute;

                time = timeToDate(timePicker.getHour(), timePicker.getMinute());
                timeString = dateToTimeString(time);

                alarmInfo = getText(R.string.alarm_info) + " " + timeString;
                textView.setText(alarmInfo);

               // Toast.makeText(getApplicationContext(), alarmInfo, Toast.LENGTH_SHORT).show();

                //alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                //Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
                //pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);
               // alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(time.getTime(), pendingIntent), pendingIntent);


            }
        });
    }

    public String dateToTimeString (Date d){
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        return (timeFormat.format(d));
    }

    //convert int of time to Date of today / tomorrow, also calculate time left until alarm
    public Date timeToDate (int h, int m){

        Date date = new Date();
        int currentHours = date.getHours(), currentMinutes = date.getMinutes(), hoursLeft = 0, minuteLeft = 0;

        date.setHours(h);
        date.setMinutes(m);

        Log.i(TAG, "set : " + date.getHours() + ",now : " + currentHours);
        if (date.getHours() <= currentHours){
            date.setDate(date.getDate() + 1);
            currentHours -= 24;
        }

        minuteLeft = date.getMinutes() - currentMinutes;
        if (minuteLeft < 0){
            minuteLeft += 60;
            hoursLeft = hoursLeft - 1;
        }
        hoursLeft += date.getHours() - currentHours;

        String dateString = new SimpleDateFormat("hh:mm a dd/MM/yyyy").format(date);
        Toast.makeText(getApplicationContext(), getText(R.string.alarm_info) + " " + dateString + ", in another " + (hoursLeft) + " hours and " + minuteLeft + " minutes", Toast.LENGTH_SHORT).show();
        return date;
    }
}
