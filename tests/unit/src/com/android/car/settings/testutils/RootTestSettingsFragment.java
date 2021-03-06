/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.car.settings.testutils;

import android.os.Bundle;

import com.android.car.settings.R;
import com.android.car.ui.toolbar.NavButtonMode;
import com.android.car.ui.toolbar.ToolbarController;

/**
 * Empty root fragment.
 */
public class RootTestSettingsFragment extends BaseTestSettingsFragment {
    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.test_base_settings_fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ToolbarController toolbar = getToolbar();
        if (toolbar != null) {
            // If the fragment is root, change the back button to settings icon.
            if (getContext().getResources().getBoolean(R.bool.config_is_root_fragment_root)) {
                toolbar.setNavButtonMode(NavButtonMode.DISABLED);
                toolbar.setLogo(getContext().getResources()
                        .getBoolean(R.bool.config_show_settings_root_exit_icon)
                        ? R.drawable.ic_launcher_settings
                        : 0);
            } else {
                toolbar.setNavButtonMode(NavButtonMode.BACK);
            }
        }
    }
}
