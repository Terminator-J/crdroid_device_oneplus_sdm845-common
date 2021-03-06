/*
* Copyright (C) 2016 The OmniROM Project
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
package org.lineageos.device.DeviceSettings;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceManager;

import org.lineageos.device.DeviceSettings.DeviceSettings;

public class DCModeSwitch implements OnPreferenceChangeListener {

    private static final String DIMLAYER_FILE = "/sys/devices/platform/soc/ae00000.qcom,mdss_mdp/drm/card0/card0-DSI-1/dimlayer_bl_en";
    private static final String DITHER_FILE = "/sys/devices/platform/soc/ae00000.qcom,mdss_mdp/drm/card0/card0-DSI-1/dither_en";

    static String getFileIfWritable(String file) {
        if (Utils.fileWritable(file)) {
            return file;
        }
        return null;
    }

    public static boolean isSupported() {
        return Utils.fileWritable(DIMLAYER_FILE) && Utils.fileWritable(DITHER_FILE);
    }

    public static boolean isCurrentlyEnabled(Context context) {
        return Utils.getFileValueAsBoolean(getFileIfWritable(DIMLAYER_FILE), false);
    }

    public static void setEnabled(boolean enabled) {
        Utils.writeValue(getFileIfWritable(DIMLAYER_FILE), enabled ? "1" : "0");
        Utils.writeValue(getFileIfWritable(DITHER_FILE), enabled ? "1" : "0");
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Boolean enabled = (Boolean) newValue;
        DCModeSwitch.setEnabled(enabled);
        return true;
    }
}
