package com.example.android.messages.Fragments;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.messages.ContactsActivity;
import com.example.android.messages.Models.MsgModel;
import com.example.android.messages.Models.TimeInfo;
import com.example.android.messages.Preferences.PreferencesManager;
import com.example.android.messages.R;
import com.example.android.messages.Receivers.Receiver;
import com.philliphsu.bottomsheetpickers.date.DatePickerDialog;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;
import com.philliphsu.bottomsheetpickers.time.grid.GridTimePickerDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Created by pc on 4/22/2018.
 */

public class AddMessageFragment extends android.support.v4.app.Fragment implements com.philliphsu.bottomsheetpickers.date.DatePickerDialog.OnDateSetListener, BottomSheetTimePickerDialog.OnTimeSetListener {
    private Unbinder mUnnbinder;
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
    @BindView(R.id.contactsBTN)
    Button mContactsBtn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_message_fragment, null);
        mUnnbinder = ButterKnife.bind(this, view);



        mContactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent i = new Intent(getActivity(), ContactsActivity.class);
              startActivityForResult(i, 1000);
            }
        });



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

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                msgModel.setPhone_ID(mPhoneNumberEdittext.getText().toString());
                msgModel.setNotice_text(mMsgTxtEdittext.getText().toString());
                msgModel.setDate(calendar1.getTime());
                PreferencesManager.userInfo(msgModel, getActivity());
                PreferencesManager.addTxtMsg(mMsgTxtEdittext.getText().toString(), getActivity());
                PreferencesManager.addPhoneNumber(mPhoneNumberEdittext.getText().toString(), getActivity());
                final long cal = calendar1.getTimeInMillis();
                PreferencesManager.addDate(cal, getActivity());

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Schedule Message");
                builder.setMessage("You are scheduling a sms message. Do you want to continue?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestSmsPermission();
                        Toast.makeText(getActivity(), "Sms scheduled", Toast.LENGTH_LONG).show();


                        Intent myIntent = new Intent(getActivity(), Receiver.class);
                        myIntent.setAction(SENT);
                        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, myIntent, 0);
                        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, cal, pendingIntent);

                    }
                });
                builder.create().show();


            }
        });

        return view;
    }

    private void requestSmsPermission() {
        String permission = Manifest.permission.SEND_SMS;
        int grant = ContextCompat.checkSelfPermission(getActivity(), permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(getActivity(), permission_list, 1);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "permission granted", Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(getActivity(), "permission not granted", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void setmDateTime() {
        Calendar now = Calendar.getInstance();

        GridTimePickerDialog grid = new GridTimePickerDialog.Builder(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(getActivity()))
    /* ... Set additional options ... */
                .build();
        grid.show(getFragmentManager(), "");



    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
     //   mText.setText("Date set: " + DateFormat.getDateFormat(this).format(cal.getTime()));
        yearSet = cal.get(Calendar.YEAR);
        monthSet = cal.get(Calendar.MONTH);;
        daySey = cal.get(Calendar.DAY_OF_MONTH);;

        mDateTime.setText("Date :   " + daySey + "/" + monthSet + "/" + yearSet + "\n" + "Time :    " + hourSet + ":" + minuteSet);
    }

    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute) {
        Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        DatePickerDialog date = new DatePickerDialog.Builder(
             this,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH))
    /* ... Set additional options ... */
                .build();
        date.show(getFragmentManager(), "");
        hourSet = cal.get(Calendar.HOUR_OF_DAY);
        minuteSet = cal.get(Calendar.MINUTE);
       // mText.setText("Time set: " + DateFormat.getTimeFormat(this).format(cal.getTime()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000 && resultCode == RESULT_OK) {


            if (data != null) {
                mPhoneNumberEdittext.setText(data.getStringExtra("phoneNumber"));

            }
        }
    }
}
