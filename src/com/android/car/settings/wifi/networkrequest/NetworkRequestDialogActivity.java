/*
 * Copyright (C) 2020 The Android Open Source Project
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

package com.android.car.settings.wifi.networkrequest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.android.settingslib.core.lifecycle.HideNonSystemOverlayMixin;

/**
 * When other applications request to have a wifi connection, framework will bring up this activity
 * to let user select which wifi ap wanna to connect. This activity is just a door for framework
 * call, and main functional process is at {@code NetworkRequestDialogFragment}.
 */
public class NetworkRequestDialogActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(new HideNonSystemOverlayMixin(this));

        NetworkRequestDialogFragment fragment = NetworkRequestDialogFragment.newInstance();
        fragment.show(getSupportFragmentManager(),
                NetworkRequestDialogFragment.class.getSimpleName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}

