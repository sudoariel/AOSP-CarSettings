package com.android.car.settings.info;

import android.car.drivingstate.CarUxRestrictions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.preference.Preference;
import com.android.car.settings.common.FragmentController;
import com.android.car.settings.common.PreferenceController;

public class DateViewPreferenceController extends PreferenceController<Preference> {

    private final IntentFilter mIntentFilter;
    private Integer mCounter = 0;
    private final BroadcastReceiver mTimeChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mCounter++;
            refreshUi();
        }
    };

   

    public DateViewPreferenceController(Context context, String preferenceKey,
            FragmentController fragmentController, CarUxRestrictions uxRestrictions) {
        super(context, preferenceKey, fragmentController, uxRestrictions);

        // Filtering intents related to possible date changes.
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        mIntentFilter.addAction(Intent.ACTION_TIME_TICK);
        mIntentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
    }

    @Override
    protected void onStartInternal() {
        // Restarting counter
        mCounter = 0;
        getContext().registerReceiver(mTimeChangeReceiver, mIntentFilter);
    }

    @Override
    protected void onStopInternal() {
        getContext().unregisterReceiver(mTimeChangeReceiver);
    }
    
    @Override
    protected Class<Preference> getPreferenceType() {
        return Preference.class;
    }

    @Override
    public void updateState(Preference preference) {
        preference.setSummary(mCounter.toString());
    }
}
