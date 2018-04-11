package com.example.android.messages;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.messages.Models.MsgModel;
import com.example.android.messages.Models.TimeInfo;
import com.example.android.messages.Preferences.PreferencesManager;
import com.example.android.messages.Receivers.Receiver;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
/*
Vo ova ak
 */

public class AddMessageActivity extends AppCompatActivity {
    @BindView(R.id.date_and_time)
    Button mDateTimeBtn;
    @BindView(R.id.schedule_btn)
    Button mScheduleBtn;
    @BindView(R.id.send_btn)
    Button mSendBtn;
    @BindView(R.id.date_and_time_txtView)
    TextView mDateTime;
    @BindView(R.id.phone_numer)
    EditText mPhoneNumberEdittext;
    @BindView(R.id.msg_edittext)
    EditText mMsgTxtEdittext;
    int year, month, day, hour, minute;
    int yearSet, monthSet, daySey, hourSet, minuteSet;
    String mPhoneNumber;
    String mSmsTxt;
    String SENT = "com.android.RECEIVER_ACTION";
    TimeInfo timeInfo;
    MsgModel msgModel = new MsgModel();
    PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);
        ButterKnife.bind(this);

        mDateTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setmDateTime();

            }
        });
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestSmsPermission();

                AlertDialog.Builder builder = new AlertDialog.Builder(AddMessageActivity.this);
                builder.setTitle("Send Message");
                builder.setMessage("You are sending an sms message. Do you want to continue?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPhoneNumber = mPhoneNumberEdittext.getText().toString();
                        mSmsTxt = mMsgTxtEdittext.getText().toString();

                        SmsManager.getDefault().sendTextMessage(mPhoneNumber, null, mSmsTxt, null, null);

                    }
                });
                builder.create().show();


            }
        });
        mScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar1 = Calendar.getInstance();

                calendar1.set(Calendar.YEAR, yearSet);
                calendar1.set(Calendar.MONTH, monthSet);
                calendar1.set(Calendar.DAY_OF_MONTH, daySey);
                calendar1.set(Calendar.HOUR_OF_DAY, hourSet);
                calendar1.set(Calendar.MINUTE, minuteSet);
                msgModel.setUserPhone(mPhoneNumberEdittext.getText().toString());
                msgModel.setMessageTxt(mMsgTxtEdittext.getText().toString());
                msgModel.setSendAt(calendar1.getTime());
                PreferencesManager.userInfo(msgModel, AddMessageActivity.this);
                PreferencesManager.addTxtMsg(mMsgTxtEdittext.getText().toString(), AddMessageActivity.this);
                PreferencesManager.addPhoneNumber(mPhoneNumberEdittext.getText().toString(), AddMessageActivity.this);
                long cal = calendar1.getTimeInMillis();
                PreferencesManager.addDate(cal, AddMessageActivity.this);
                Toast.makeText(AddMessageActivity.this, "Sms scheduled", Toast.LENGTH_LONG).show();


                Intent myIntent = new Intent(AddMessageActivity.this, Receiver.class);
                myIntent.setAction(SENT);
                pendingIntent = PendingIntent.getBroadcast(AddMessageActivity.this, 0, myIntent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal, pendingIntent);


            }
        });


    }

    private void requestSmsPermission() {
        String permission = Manifest.permission.SEND_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(AddMessageActivity.this, "permission granted", Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(AddMessageActivity.this, "permission not granted", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void setmDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddMessageActivity.this);
        datePickerDialog.show();
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                yearSet = i;
                monthSet = i1;
                daySey = i2;
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddMessageActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        hourSet = i;
                        minuteSet = i1;
                        mDateTime.setText("Date :   " + daySey + "/" + monthSet + "/" + yearSet + "\n" + "Time :    " + hourSet + ":" + minuteSet);

                    }
                }, hour, minute, true);
                timePickerDialog.show();
                timeInfo = new TimeInfo(yearSet, monthSet, daySey, hourSet, minuteSet);
                PreferencesManager.addTimeInfo(timeInfo, AddMessageActivity.this);
                msgModel.setUserPhone(mPhoneNumberEdittext.getText().toString());
                msgModel.setMessageTxt(mMsgTxtEdittext.getText().toString());
                PreferencesManager.userInfo(msgModel, AddMessageActivity.this);

            }
        });

    }


}

