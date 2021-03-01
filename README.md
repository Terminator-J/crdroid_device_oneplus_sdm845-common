## Hola amiguitos

Rather than carrying forward the 10.0 crDroid branch, this is based on cloning vanilla lineage-18.1 branch from LineageOS, then merging in the the unmerged unofficial bringup patchset from their gerrit.

Then on that base, applying appropriate commits to get rid of some Lineage-specific things that we have from crDroid base instead, and reimplementing DeviceSettings for Android 11 based on code from the crdroidandroid/android_device_oneplus_sm8250 and AOSiP/device_oneplus_sdm845-common device trees.
