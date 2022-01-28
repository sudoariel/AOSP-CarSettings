package com.android.car.settings.info;

import android.car.drivingstate.CarUxRestrictions;
import android.content.Context;
import androidx.preference.Preference;
import com.android.car.settings.common.FragmentController;
import com.android.car.settings.common.PreferenceController;

public class TimeViewPreferenceController extends PreferenceController<Preference> {

    public TimeViewPreferenceController(Context context, String preferenceKey,
            FragmentController fragmentController, CarUxRestrictions uxRestrictions) {
        super(context, preferenceKey, fragmentController, uxRestrictions);
    }
    
    @Override
    protected Class<Preference> getPreferenceType() {
        return Preference.class;
    }

    @Override
    public void updateState(Preference preference) {
        preference.setSummary("Testing summary for time.");
    }
}
