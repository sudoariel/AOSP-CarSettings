package com.android.car.settings.custom;

import com.android.car.settings.R;

import androidx.annotation.XmlRes;

import com.android.car.settings.common.SettingsFragment;

public class CustomSettingsFragment extends SettingsFragment{

    @Override
    @XmlRes
    protected int getPreferenceScreenResId() {
        return R.xml.custom_settings_fragment;
    }
}
