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
 * limitations under the License.
 */

package com.android.car.settings.security;

import android.app.admin.DevicePolicyManager;
import android.car.drivingstate.CarUxRestrictions;
import android.content.Context;
import android.content.Intent;

import com.android.car.settings.common.FragmentController;

/** Business logic for the lock pattern picker preference. */
public class PatternLockPreferenceController extends LockTypeBasePreferenceController {

    private static final int[] ALLOWED_PASSWORD_QUALITIES =
            new int[]{DevicePolicyManager.PASSWORD_QUALITY_SOMETHING};

    public PatternLockPreferenceController(Context context, String preferenceKey,
            FragmentController fragmentController, CarUxRestrictions uxRestrictions) {
        super(context, preferenceKey, fragmentController, uxRestrictions);
    }

    @Override
    protected Intent activityToOpen() {
        return new Intent(getContext(), ChooseLockPatternActivity.class);
    }

    @Override
    protected int[] allowedPasswordQualities() {
        return ALLOWED_PASSWORD_QUALITIES;
    }
}
