package com.android.car.settings.info;

import android.car.drivingstate.CarUxRestrictions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.preference.Preference;
import com.android.car.settings.common.FragmentController;
import com.android.car.settings.common.PreferenceController;
import com.android.car.settings.common.Logger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeViewPreferenceController extends PreferenceController<Preference> {
    private final Logger LOG = new Logger(TimeViewPreferenceController.class);
    private final IntentFilter mIntentFilter;
    private final BroadcastReceiver mTimeChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LOG.d("TimeChangeReceiver - " + intent.getAction());
            refreshUi();
        }
    };

    public TimeViewPreferenceController(Context context, String preferenceKey,
            FragmentController fragmentController, CarUxRestrictions uxRestrictions) {
        super(context, preferenceKey, fragmentController, uxRestrictions);
        // ACTION_TIME_CHANGED listens to changes to the autoDatetime toggle to update the time.
        // ACTION_TIME_TICK listens to the minute changes to update the shown time.
        // ACTION_TIMEZONE_CHANGED listens to time zone changes to update the shown time.
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        mIntentFilter.addAction(Intent.ACTION_TIME_TICK);
        mIntentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
    }

    @Override
    protected void onStartInternal() {
        LOG.d("onStartInternal()");
        getContext().registerReceiver(mTimeChangeReceiver, mIntentFilter);
    }

    @Override
    protected void onStopInternal() {
        LOG.d("onStopInternal()");
        getContext().unregisterReceiver(mTimeChangeReceiver);
    }
    
    @Override
    protected Class<Preference> getPreferenceType() {
        return Preference.class;
    }

    @Override
    public void updateState(Preference preference) {
        Date currentTime = Calendar.getInstance().getTime();
        LOG.d("updateState()");
        String pattern = "h:mm a";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String time = simpleDateFormat.format(currentTime); 
        preference.setSummary(time);
    }
}
