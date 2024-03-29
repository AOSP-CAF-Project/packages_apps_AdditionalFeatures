/*
 * Copyright © 2018 Syberia Project
 * Date: 29.08.2018
 * Time: 21:21
 * Author: @alexxxdev <alexxxdev@ya.ru>
 * Copyright (C) 2019 The AOSP-CAF-MSM8916 Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.teamone.additinal.features;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v14.preference.SwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.teamone.additional.features.preferences.CustomSeekBarPreference;
import com.android.settings.Utils;

import com.android.internal.logging.nano.MetricsProto;

public class DialerSettings extends SettingsPreferenceFragment {

    private static final String INCALL_VIB_OPTIONS = "incall_vib_options";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.dialer_settings);

        PreferenceScreen prefScreen = getPreferenceScreen();

        PreferenceCategory incallVibCategory = (PreferenceCategory) findPreference(INCALL_VIB_OPTIONS);
        if (!Utils.isVoiceCapable(getActivity())) {
            prefScreen.removePreference(incallVibCategory);
        }

   }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.AOSP;
    }
}
