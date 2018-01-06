/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kartum.gcm;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.kartum.utils.Debug;

import java.io.IOException;


/**
 * Helper class providing methods and constants common to other classes in the
 * app.
 */
public final class GCMUtilities {

    Activity activity;

    public GCMUtilities(Activity activity) {
        this.activity = activity;
    }

    /**
     * Google API project id registered to use GCM.
     */
    public static final String SENDER_ID = "437806439696";
    public static final String REG_PREF = "reg_pref";
    public static final String WINAPP_REG_ID = "winapp_reg_id";
    public static final String WINAPP_APP_VERSION = "winapp_app_version";

    public void initGCM() {
        String regId = getRegistrationId();
        if (regId == null || regId.isEmpty()) {
            new RegisterInBackground().execute();
        }
    }

    /**
     * Gets the current registration ID for application on GCM service.
     * <p/>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     * registration ID.
     */
    public String getRegistrationId() {
        final SharedPreferences prefs = getGCMPreferences();
        String registrationId = prefs.getString(WINAPP_REG_ID, "");
        if (registrationId.isEmpty()) {
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(WINAPP_APP_VERSION,
                Integer.MIN_VALUE);
        int currentVersion = getAppVersion();
        if (registeredVersion != currentVersion) {
            return "";
        }
        return registrationId;
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGCMPreferences() {
        // This sample app persists the registration ID in shared preferences,
        // but
        // how you store the regID in your app is up to you.

        return activity.getSharedPreferences(REG_PREF, Context.MODE_PRIVATE);
    }

    private int getAppVersion() {
        try {
            PackageInfo packageInfo = activity.getPackageManager()
                    .getPackageInfo(activity.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private void storeRegistrationId(String regId) {
        final SharedPreferences prefs = getGCMPreferences();
        int appVersion = getAppVersion();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(WINAPP_REG_ID, regId);
        editor.putInt(WINAPP_APP_VERSION, appVersion);
        editor.commit();
    }

    private void clearRegistrationId() {
        final SharedPreferences prefs = getGCMPreferences();
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    OnUpdatedAddDeviceListener onUpdatedAddDevice;

    public static interface OnUpdatedAddDeviceListener {
        void onComplete();
    }

    public class RegisterInBackground extends AsyncTask<Object, Object, Object> {
        String msg = "";

        @Override
        protected Object doInBackground(Object... params) {
            try {
                GoogleCloudMessaging gcm = null;
                String regid;

                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(activity);
                }

                regid = gcm.register(SENDER_ID);
                msg = "Device registered, registration ID=" + regid;
                Debug.e("", msg);


                // For this demo: we don't need to send it because the device
                // will send upstream messages to a server that echo back the
                // message using the 'from' address in the message.

                // Persist the regID - no need to register again.
                storeRegistrationId(regid);


                // You should send the registration ID to your server over HTTP,
                // so it can use GCM/HTTP or CCS to send messages to your app.
                // The request to your server should be authenticated if your
                // app
                // is using accounts.
                if (regid != null && !regid.isEmpty()) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }

            } catch (IOException ex) {
                msg = "Error :" + ex.getMessage();
                // If there is an error, don't just keep trying to register.
                // Require the user to click a button again, or perform
                // exponential back-off.
            }
            return msg;
        }

    }

    public void unRegister() {
        try {

            GoogleCloudMessaging gcm = null;

            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(activity);
            }

            gcm.unregister();

            clearRegistrationId();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
