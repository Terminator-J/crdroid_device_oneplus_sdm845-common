/*
 * Copyright (C) 2018 The LineageOS Project
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

package com.aosp.device.OnePlusSettings.Services;

import android.content.Context;
import android.media.AudioManager;
import android.os.VibrationEffect;
import android.view.KeyEvent;

import androidx.annotation.Keep;

import com.android.internal.os.DeviceKeyHandler;

import com.aosp.device.OnePlusSettings.Services.VolumeService;
import com.aosp.device.OnePlusSettings.Utils.VibrationUtils;

@Keep
public class KeyHandler implements DeviceKeyHandler {
    private static final String TAG = KeyHandler.class.getSimpleName();

    // Slider key codes
    private static final int MODE_NORMAL = 601;
    private static final int MODE_VIBRATION = 602;
    private static final int MODE_SILENCE = 603;

    private final Context mContext;
    private final AudioManager mAudioManager;

    private int lastCode;

    public KeyHandler(Context context) {
        mContext = context;

        mAudioManager = mContext.getSystemService(AudioManager.class);

        lastCode = MODE_NORMAL;
    }

    public KeyEvent handleKeyEvent(KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_DOWN) {
            return event;
        }

        int scanCode = event.getScanCode();

        switch (scanCode) {
            case MODE_NORMAL:
                mAudioManager.setRingerModeInternal(AudioManager.RINGER_MODE_NORMAL);
                VibrationUtils.doHapticFeedback(mContext, VibrationEffect.EFFECT_HEAVY_CLICK, true);
                if (lastCode == MODE_SILENCE) VolumeService.changeMediaVolume(mAudioManager, mContext);
                break;
            case MODE_VIBRATION:
                mAudioManager.setRingerModeInternal(AudioManager.RINGER_MODE_VIBRATE);
                VibrationUtils.doHapticFeedback(mContext, VibrationEffect.EFFECT_DOUBLE_CLICK, true);
                if (lastCode == MODE_SILENCE) VolumeService.changeMediaVolume(mAudioManager, mContext);
                break;
            case MODE_SILENCE:
                mAudioManager.setRingerModeInternal(AudioManager.RINGER_MODE_SILENT);
                VolumeService.changeMediaVolume(mAudioManager, mContext);
                break;
            default:
                return event;
        }

        lastCode = scanCode;

        return null;
    }
}
