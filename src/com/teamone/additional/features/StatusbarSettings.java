 /*
 * Copyright (C) 2014-2015 The CyanogenMod Project
 * Copyright (C) 2019 The AOSP-CAF-MSM8916 Project
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

package com.teamone.additional.features;

import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.content.ContentResolver;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.teamone.additional.features.preferences.SystemSettingSwitchPreference;
import com.teamone.additional.features.preferences.CustomSeekBarPreference;

import com.android.internal.logging.nano.MetricsProto;

public class StatusbarSettings extends SettingsPreferenceFragment implements OnPreferenceChangeListener{

		private CustomSeekBarPreference mThreshold;
		private SystemSettingSwitchPreference mNetMonitor;

     @Override
	 public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		addPreferencesFromResource(R.xml.extra_settings_statusbar);

         PreferenceScreen prefSet = getPreferenceScreen();

 		final ContentResolver resolver = getActivity().getContentResolver();
		boolean isNetMonitorEnabled = Settings.System.getIntForUser(resolver,
		    Settings.System.NETWORK_TRAFFIC_STATE, 1, UserHandle.USER_CURRENT) == 1;
		mNetMonitor = (SystemSettingSwitchPreference) findPreference("network_traffic_state");
		mNetMonitor.setChecked(isNetMonitorEnabled);
		mNetMonitor.setOnPreferenceChangeListener(this);
		int value = Settings.System.getIntForUser(resolver,
		    Settings.System.NETWORK_TRAFFIC_AUTOHIDE_THRESHOLD, 1, UserHandle.USER_CURRENT);
		mThreshold = (CustomSeekBarPreference) findPreference("network_traffic_autohide_threshold");
		mThreshold.setValue(value);
		mThreshold.setOnPreferenceChangeListener(this);
		mThreshold.setEnabled(isNetMonitorEnabled);

     }

	@Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
         if (preference == mNetMonitor) {
            boolean value = (Boolean) objValue;
            Settings.System.putIntForUser(getActivity().getContentResolver(),
                    Settings.System.NETWORK_TRAFFIC_STATE, value ? 1 : 0,
                    UserHandle.USER_CURRENT);
            mNetMonitor.setChecked(value);
            mThreshold.setEnabled(value);
            return true;
        } else if (preference == mThreshold) {
            int val = (Integer) objValue;
            Settings.System.putIntForUser(getContentResolver(),
                    Settings.System.NETWORK_TRAFFIC_AUTOHIDE_THRESHOLD, val,
                    UserHandle.USER_CURRENT);
            return true;
        }
        return false;
}

     @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.AOSP;
    }
}
