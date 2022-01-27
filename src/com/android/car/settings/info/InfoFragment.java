package com.android.car.settings.info;

import com.android.car.settings.common.SettingsFragment;
import androidx.annotation.XmlRes;
import com.android.car.settings.R;

public class InfoFragment extends SettingsFragment{

    @Override
    @XmlRes
    protected int getPreferenceScreenResId() {
        return R.xml.info_fragment;
    }
        
}
