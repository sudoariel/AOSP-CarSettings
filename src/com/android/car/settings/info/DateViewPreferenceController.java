package com.android.car.settings.info;

import android.car.drivingstate.CarUxRestrictions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.preference.Preference;
import com.android.car.settings.common.FragmentController;
import com.android.car.settings.common.Logger;
import com.android.car.settings.common.PreferenceController;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateViewPreferenceController extends PreferenceController<Preference> {

    private final Logger LOG = new Logger(DateViewPreferenceController.class);
    private final IntentFilter mIntentFilter;
    private final BroadcastReceiver mTimeChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LOG.d("TimeChangeReceiver - " + intent.getAction());
            refreshUi();
        }
    };

   

    public DateViewPreferenceController(Context context, String preferenceKey,
            FragmentController fragmentController, CarUxRestrictions uxRestrictions) {
        super(context, preferenceKey, fragmentController, uxRestrictions);

        LOG.d("Constructor called");

        // Filtering intents related to possible date changes.
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
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(currentTime); 
        preference.setSummary(date.substring(0, 1).toUpperCase() + date.substring(1));
    }
}
