/*
 * Copyright (C) 2021 The Android Open Source Project
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

import static junit.framework.Assert.assertFalse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.annotation.Nullable;

/**
 * Test listener that can be used to spy on, control responses from,
 * and make assertions against values tested.
 */
public class TestEventListener<T> {

    private @Nullable T mLastValue;
    private boolean mWasCalled;

    public void accept(T event) {
        mWasCalled = true;
        mLastValue = event;
    }

    public void assertLastArgument(@Nullable T expected) {
        assertEquals(expected, mLastValue);
    }

    public void assertCalled() {
        assertTrue(mWasCalled);
    }

    public void assertNotCalled() {
        assertFalse(mWasCalled);
    }

    @Nullable
    public T getLastValue() {
        return mLastValue;
    }
}
