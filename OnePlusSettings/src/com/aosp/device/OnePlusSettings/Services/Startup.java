/*
* Copyright (C) 2013 The OmniROM Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package com.aosp.device.OnePlusSettings.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.UserHandle;
import android.provider.Settings;
import androidx.preference.PreferenceManager;

import com.aosp.device.OnePlusSettings.OnePlusSettings;
import com.aosp.device.OnePlusSettings.ModeSwitch.DCModeSwitch;
import com.aosp.device.OnePlusSettings.ModeSwitch.HBMModeSwitch;
import com.aosp.device.OnePlusSettings.Utils.FileUtils;
import com.aosp.device.OnePlusSettings.Utils.DozeUtils;

public class Startup extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent bootintent) {
        boolean enabled = false;
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        enabled = sharedPrefs.getBoolean(OnePlusSettings.KEY_MUTE_MEDIA, false);
        VolumeService.setEnabled(context, enabled);

        enabled = sharedPrefs.getBoolean(OnePlusSettings.KEY_DC_SWITCH, false);
        restore(DCModeSwitch.getFile(), enabled);

        enabled = sharedPrefs.getBoolean(OnePlusSettings.KEY_HBM_SWITCH, false);
        restore(HBMModeSwitch.getFile(), enabled);

        enabled = sharedPrefs.getBoolean(OnePlusSettings.KEY_FPS_INFO, false);
        if (enabled) context.startServiceAsUser(new Intent(context, FPSInfoService.class), UserHandle.CURRENT);

        DozeUtils.checkDozeService(context);
        OnePlusSettings.restoreVibStrengthSetting(context);
    }

    private void restore(String file, boolean enabled) {
        if (file == null) {
            return;
        }
        if (enabled) {
            FileUtils.writeLine(file, "1");
        }
    }
}
