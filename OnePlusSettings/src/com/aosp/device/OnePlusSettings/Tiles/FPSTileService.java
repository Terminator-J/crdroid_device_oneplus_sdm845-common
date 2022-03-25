/**
 * Copyright (C) 2020 Omni ROM
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

package com.aosp.device.OnePlusSettings.Tiles;

import com.aosp.device.OnePlusSettings.Services.FPSInfoService;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Handler;
import android.os.UserHandle;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import com.aosp.device.OnePlusSettings.Services.FPSInfoService;

public class FPSTileService extends TileService {

  private final String KEY_FPS_INFO = "fps_info";

  private boolean isShowing = false;

  @Override
  public void onStartListening() {
      super.onStartListening();
      ActivityManager manager =
              (ActivityManager) getSystemService(this.ACTIVITY_SERVICE);
      for (ActivityManager.RunningServiceInfo service :
              manager.getRunningServices(Integer.MAX_VALUE)) {
          if (FPSInfoService.class.getName().equals(
                  service.service.getClassName())) {
              isShowing = true;
          }
      }
      updateTile();
  }

  @Override
  public void onClick() {
      Intent fpsinfo = new Intent(this, FPSInfoService.class);
      if (!isShowing)
          this.startServiceAsUser(fpsinfo, UserHandle.CURRENT);
      else
          this.stopServiceAsUser(fpsinfo, UserHandle.CURRENT);
      isShowing = !isShowing;
      updateTile();
  }

  private void updateTile() {
      final Tile tile = getQsTile();
      tile.setState(isShowing ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
      tile.updateTile();
  }
}
