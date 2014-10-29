package com.msevgi.dictionary.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Button;

import com.msevgi.dictionary.R;
import com.msevgi.dictionary.service.AlarmService;

import java.util.Calendar;

import butterknife.InjectView;
import butterknife.OnClick;

public class FragmentSettings extends BaseFragment {

    @InjectView(R.id.button_set_alarm)
    public Button mButtonSetAlarm;
    @InjectView(R.id.button_cancel_alarm)
    public Button mButtonCancelAlarm;

    PendingIntent mPendingIntent;

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_settings;
    }

    @OnClick(R.id.button_set_alarm)
    public void onAlarmButtonClick() {
        Intent mIntent = new Intent(getActivity(), AlarmService.class);
        mPendingIntent = PendingIntent.getService(getActivity(), 0, mIntent, 0);

        AlarmManager mAlarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
//        mCalendar.add(Calendar.SECOND, 10);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), 2*1000,mPendingIntent);
    }

    @OnClick(R.id.button_cancel_alarm)
    public void onCancelAlarmButtonClick() {
        if (mPendingIntent != null) {
            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
            alarmManager.cancel(mPendingIntent);
        }
    }
}
