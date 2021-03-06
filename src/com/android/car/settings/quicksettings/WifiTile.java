/*
 * Copyright (C) 2018 The Android Open Source Project
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
 * limitations under the License
 */

package com.android.car.settings.quicksettings;

import android.annotation.DrawableRes;
import android.annotation.Nullable;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.android.car.settings.R;
import com.android.car.settings.common.CarSettingActivities;
import com.android.car.settings.wifi.CarWifiManager;
import com.android.car.settings.wifi.WifiUtil;
import com.android.wifitrackerlib.WifiEntry;

/**
 * Controls Wifi tile on quick setting page.
 */
public class WifiTile implements QuickSettingGridAdapter.Tile, CarWifiManager.Listener {
    private final StateChangedListener mStateChangedListener;
    private final CarWifiManager mCarWifiManager;
    private final Context mContext;

    private final View.OnLongClickListener mLaunchWifiSettings;

    @DrawableRes
    private int mIconRes = R.drawable.ic_settings_wifi;

    private String mText;

    private State mState = State.OFF;

    WifiTile(
            Context context,
            StateChangedListener stateChangedListener,
            Lifecycle lifecycle) {
        mContext = context;
        mLaunchWifiSettings = v -> {
            context.startActivity(new Intent(context,
                    CarSettingActivities.WifiSettingsActivity.class));
            return true;
        };
        mCarWifiManager = new CarWifiManager(context, lifecycle);
        mStateChangedListener = stateChangedListener;
        // init icon and text etc.
        updateWifiEntrySsid();
        onWifiStateChanged(mCarWifiManager.getWifiState());
    }

    @Nullable
    public View.OnLongClickListener getOnLongClickListener() {
        return mLaunchWifiSettings;
    }

    @Override
    public boolean isAvailable() {
        return WifiUtil.isWifiAvailable(mContext);
    }

    @Override
    public Drawable getIcon() {
        return mContext.getDrawable(mIconRes);
    }

    @Override
    @Nullable
    public String getText() {
        return mText;
    }

    @Override
    public State getState() {
        return mState;
    }

    @Override
    public void start() {
        mCarWifiManager.addListener(this);
    }

    @Override
    public void stop() {
        mCarWifiManager.removeListener(this);
    }

    @Override
    public void onWifiEntriesChanged() {
        if (updateWifiEntrySsid()) {
            mStateChangedListener.onStateChanged();
        }
    }

    @Override
    public void onWifiStateChanged(int state) {
        mIconRes = WifiUtil.getIconRes(state);
        int stringId = WifiUtil.getStateDesc(state);
        if (stringId != 0) {
            mText = mContext.getString(stringId);
        } else if (!updateWifiEntrySsid()) {
            if (wifiEnabledNotConnected()) {
                mText = mContext.getString(R.string.wifi_settings);
            }
        }
        mState = WifiUtil.isWifiOn(state) ? State.ON : State.OFF;
        mStateChangedListener.onStateChanged();
    }

    @Override
    public void onClick(View v) {
        mCarWifiManager.setWifiEnabled(!mCarWifiManager.isWifiEnabled());
    }

    private boolean wifiEnabledNotConnected() {
        return mCarWifiManager.isWifiEnabled() && mCarWifiManager.getConnectedWifiEntry() == null;
    }

    /**
     * Updates the text with Wi-Fi entry connected, if any
     *
     * @return {@code true} if the text was updated, {@code false} otherwise
     */
    private boolean updateWifiEntrySsid() {
        WifiEntry wifiEntry = mCarWifiManager.getConnectedWifiEntry();
        if (wifiEntry != null) {
            mText = wifiEntry.getSsid();
            return true;
        }
        return false;
    }
}
