/*
* Copyright (C) 2018 The OmniROM Project
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
package com.aosp.device.OnePlusSettings.Tiles;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import androidx.preference.PreferenceManager;

import com.aosp.device.OnePlusSettings.OnePlusSettings;
import com.aosp.device.OnePlusSettings.ModeSwitch.DCModeSwitch;
import com.aosp.device.OnePlusSettings.Utils.FileUtils;
import com.aosp.device.OnePlusSettings.R;

@TargetApi(24)
public class DCModeTileService extends TileService {
    private boolean enabled = false;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        enabled = DCModeSwitch.isCurrentlyEnabled(this);
        getQsTile().setIcon(Icon.createWithResource(this,
                    enabled ? R.drawable.ic_dimming_on : R.drawable.ic_dimming_off));
        getQsTile().setState(enabled ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        getQsTile().updateTile();

    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    @Override
    public void onClick() {
        super.onClick();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        enabled = DCModeSwitch.isCurrentlyEnabled(this);
        FileUtils.writeLine(DCModeSwitch.getFile(), enabled ? "0" : "1");
        sharedPrefs.edit().putBoolean(OnePlusSettings.KEY_DC_SWITCH, enabled ? false : true).commit();
        getQsTile().setIcon(Icon.createWithResource(this,
                    enabled ? R.drawable.ic_dimming_off : R.drawable.ic_dimming_on));
        getQsTile().setState(enabled ? Tile.STATE_INACTIVE : Tile.STATE_ACTIVE);
        getQsTile().updateTile();
    }
}
