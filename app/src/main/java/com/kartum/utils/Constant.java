package com.kartum.utils;

import android.os.Environment;

import java.io.File;

public class Constant {
    public static final String FOLDER_NAME = ".faro10";
    public static final String CACHE_DIR = ".faro10/Cache";

    public static final String TMP_DIR = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + FOLDER_NAME + "/tmp";

    public static final String PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + File.separator + "" + FOLDER_NAME;

    public static final String FOLDER_RIDEINN_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + "faro10";


    public static final String API_KEY = "123";

    public static final String USER_LATITUDE = "lat";
    public static final String USER_LONGITUDE = "longi";

    public static final String LOGIN_INFO = "login_info";
    public static final String DRUGS_DATA = "drugs_data";
    public static final String OBS_USER_DATA = "obs_user_data";
    public static final String COUNTRY = "country";
    public static final String REMEMBER_ME = "remember";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PASS = "user_pass";

    public static final String ON_OFF = "notif_on_off";
    public static final String OFF = "off";
    public static final String LOCATION = "location";
    public static final String CLINICIAN = "clinician";
    public static final String CONTACT = "contact";

    public static final String NOTIFICATION = "notification";

    public static final String DATE_FORMAT_SERVER = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
//    2017-09-26T06:14:06.559Z

    public static final String ROLE = "role";
    public static final int ROLE_SELLER = 1;
    public static final int ROLE_BUYER = 2;
    public static final int ROLE_GENERAL = 3;

    public static final String FINISH_ACTIVITY = "finish_activity";

    public static final String REMINDER = "reminder";
    public static final String USERDATA = "userdata";

    public static final int ANDROID_TYPE = 1;

    public static final int TIMEOUT = 5 * 60 * 1000;

    //
    public static final String LOCATION_UPDATED = "location_updated";

    public static final int REQ_CODE_SETTING = 555;

    public static final String TIME_ZONE = "time_zone";
    public static final String TAG_CANCEL = "cancel";
    public static final String TAG_REMOVE = "remove";
    public static final String TAG_REJECT = "reject";
    public static final String TAG_BLOCK = "block";
    public static final String TAG_UNBLOCK = "unblock";
    public static final String TAG_ACCEPT = "accept";
}
