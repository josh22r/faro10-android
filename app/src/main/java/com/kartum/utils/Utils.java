package com.kartum.utils;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.SeekBar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amulyakhare.textdrawable.TextDrawable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kartum.R;
import com.kartum.SettingActivity;
import com.kartum.objects.Country;
import com.kartum.objects.DrugsRes;
import com.kartum.objects.TimeZOne;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import me.tankery.lib.circularseekbar.CircularSeekBar;

public class Utils {

    public static void setPref(Context c, String pref, String val) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putString(pref, val);
        e.commit();

    }

    public static String getPref(Context c, String pref, String val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getString(pref,
                val);
    }

    public static Boolean getPref(Context c, String pref, Boolean val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(pref,
                val);
    }

    public static void setPref(Context c, String pref, boolean val) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putBoolean(pref, val);
        e.commit();

    }

    public static boolean getPref(Context c, String pref, boolean val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(
                pref, val);
    }

    public static void delPref(Context c, String pref) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.remove(pref);
        e.commit();
    }

    public static void setPref(Context c, String pref, int val) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putInt(pref, val);
        e.commit();

    }

    public static int getPref(Context c, String pref, int val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getInt(pref,
                val);
    }

    public static void setPref(Context c, String pref, long val) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putLong(pref, val);
        e.commit();
    }

    public static long getPref(Context c, String pref, long val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getLong(pref,
                val);
    }

    public static void setPref(Context c, String file, String pref, String val) {
        SharedPreferences settings = c.getSharedPreferences(file,
                Context.MODE_PRIVATE);
        Editor e = settings.edit();
        e.putString(pref, val);
        e.commit();

    }

    public static String getPref(Context c, String file, String pref, String val) {
        return c.getSharedPreferences(file, Context.MODE_PRIVATE).getString(
                pref, val);
    }

    public static boolean validateEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    public static boolean validate(String target, String pattern) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            Pattern r = Pattern.compile(pattern);
            return r.matcher(target)
                    .matches();
        }
    }

    public static boolean isAlphaNumeric(String target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            Pattern r = Pattern.compile("^[a-zA-Z0-9]*$");
            return r.matcher(target)
                    .matches();
        }
    }

    public static boolean isNumeric(String target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            Pattern r = Pattern.compile("^[0-9]*$");
            return r.matcher(target)
                    .matches();
        }
    }

    public static int getDeviceWidth(Context context) {
        try {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return metrics.widthPixels;
        } catch (Exception e) {
            Utils.sendExceptionReport(e);
        }

        return 480;
    }

    public static int getDeviceHeight(Context context) {
        try {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return metrics.heightPixels;
        } catch (Exception e) {
            Utils.sendExceptionReport(e);
        }

        return 800;
    }

    public static boolean isInternetConnected(Context mContext) {
        boolean outcome = false;

        try {
            if (mContext != null) {
                ConnectivityManager cm = (ConnectivityManager) mContext
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo[] networkInfos = cm.getAllNetworkInfo();

                for (NetworkInfo tempNetworkInfo : networkInfos) {
                    if (tempNetworkInfo.isConnected()) {
                        outcome = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outcome;
    }

    public static String getDeviceId(Context c) {
        String aid;
        try {
            aid = "";
            aid = Settings.Secure.getString(c.getContentResolver(),
                    "android_id");

            if (aid == null) {
                aid = "No DeviceId";
            } else if (aid.length() <= 0) {
                aid = "No DeviceId";
            }

        } catch (Exception e) {
            Utils.sendExceptionReport(e);
            aid = "No DeviceId";
        }

        return aid;

    }

    public static float random(float min, float max) {
        return (float) (min + (Math.random() * ((max - min) + 1)));
    }

    public static int random(int min, int max) {
        return Math.round((float) (min + (Math.random() * ((max - min) + 1))));
    }

    public static boolean hasFlashFeature(Context context) {
        return context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH);
    }

    public static boolean hasCameraFeature(Context context) {
        return context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }

    public static void hideKeyBoard(Context c, View v) {
        InputMethodManager imm = (InputMethodManager) c
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    public static Typeface getBold(Context c) {
        try {
            return Typeface.createFromAsset(c.getAssets(),
                    "RobotoCondensed-Bold.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Typeface getNormal(Context c) {
        try {
            return Typeface.createFromAsset(c.getAssets(),
                    "RobotoCondensed-Regular.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean passwordValidator(String password) {
        Pattern pattern;
        Matcher matcher;
//        String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z]).{6,15}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static String formatNo(String str) {
        String number = removeComma(nullSafe(str));
        if (!StringUtils.isEmpty(number)) {

            String finalStr = formatToComma(number);

//            if (!finalStr.startsWith("$"))
//                finalStr = "$" + finalStr;
            return finalStr;
        }

        return number;

    }

    public static String formatNo$(String str) {
        String number = removeComma(nullSafe(str));
        if (!StringUtils.isEmpty(number)) {

            String finalStr = formatToComma(number);

            if (!finalStr.startsWith("$"))
                finalStr = "$" + finalStr;
            return finalStr;
        }

        return number;

    }

    public static String formatToComma(String str) {
        String number = removeComma(nullSafe(str));
        if (!StringUtils.isEmpty(number)) {

            String finalStr;
            if (number.contains(".")) {
                number = truncateUptoTwoDecimal(number);
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                finalStr = decimalFormat.format(new BigDecimal(number));
            } else {
                finalStr = number;
            }

            finalStr = NumberFormat.getNumberInstance(Locale.US).format(Double.valueOf(finalStr));
            return finalStr;
        }

        return number;
    }

    public static String truncateUptoTwoDecimal(String doubleValue) {
        String value = String.valueOf(doubleValue);
        if (value != null) {
            String result = value;
            int decimalIndex = result.indexOf(".");
            if (decimalIndex != -1) {
                String decimalString = result.substring(decimalIndex + 1);
                if (decimalString.length() > 2) {
                    result = value.substring(0, decimalIndex + 3);
                } else if (decimalString.length() == 1) {
//                    result = String.format(Locale.ENGLISH, "%.2f",
//                            Double.parseDouble(value));
                }
            }
            return result;
        }

        return value;
    }

    public static String removeComma(String str) {
        try {
            if (!StringUtils.isEmpty(str)) {
                str = str.replaceAll(",", "");
                try {
                    NumberFormat format = NumberFormat.getCurrencyInstance();
                    Number number = format.parse(str);
                    return number.toString();
                } catch (ParseException e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Debug.e("removeComma", "" + str);
        return str;

    }

    public static LayoutAnimationController getRowFadeSpeedAnimation(Context c) {
        AlphaAnimation anim = (AlphaAnimation) AnimationUtils.loadAnimation(c,
                R.anim.raw_fade);
        LayoutAnimationController controller = new LayoutAnimationController(
                anim, 0.3f);

        return controller;
    }

    public static void sendExceptionReport(Exception e) {
        e.printStackTrace();

        try {
            // Writer result = new StringWriter();
            // PrintWriter printWriter = new PrintWriter(result);
            // e.printStackTrace(printWriter);
            // String stacktrace = result.toString();
            // new CustomExceptionHandler(c, URLs.URL_STACKTRACE)
            // .sendToServer(stacktrace);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }


    public static String getAndroidId(Context c) {
        String aid;
        try {
            aid = "";
            aid = Settings.Secure.getString(c.getContentResolver(),
                    "android_id");

            if (aid == null) {
                aid = "No DeviceId";
            } else if (aid.length() <= 0) {
                aid = "No DeviceId";
            }
        } catch (Exception e) {
            e.printStackTrace();
            aid = "No DeviceId";
        }

        Debug.e("", "aid : " + aid);

        return aid;

    }

    public static int getAppVersionCode(Context c) {
        try {
            return c.getPackageManager().getPackageInfo(c.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            Utils.sendExceptionReport(e);
        }
        return 0;

    }

    public static String getPhoneModel(Context c) {

        try {
            return Build.MODEL;
        } catch (Exception e) {
            Utils.sendExceptionReport(e);
        }

        return "";
    }

    public static String getPhoneBrand(Context c) {

        try {
            return Build.BRAND;
        } catch (Exception e) {
            Utils.sendExceptionReport(e);
        }

        return "";
    }

    public static String getOsVersion(Context c) {

        try {
            return Build.VERSION.RELEASE;
        } catch (Exception e) {
            Utils.sendExceptionReport(e);
        }

        return "";
    }

    public static int getOsAPILevel(Context c) {

        try {
            return Build.VERSION.SDK_INT;
        } catch (Exception e) {
            Utils.sendExceptionReport(e);
        }

        return 0;
    }

    public static String parseCalendarFormat(Calendar c, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,
                Locale.getDefault());
        return sdf.format(c.getTime());
    }

    public static String parseTime(long time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,
                Locale.getDefault());
        return sdf.format(new Date(time));
    }

    public static Date parseTime(String time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,
                Locale.getDefault());
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public static String parseTime(String time, String fromPattern,
                                   String toPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(fromPattern,
                Locale.getDefault());
        try {
            Date d = sdf.parse(time);
            sdf = new SimpleDateFormat(toPattern, Locale.getDefault());
            return sdf.format(d);
        } catch (Exception e) {
            Log.i("parseTime", "" + e.getMessage());
        }

        return "";
    }

    public static Date parseTimeUTCtoDefault(String time, String pattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(pattern,
                Locale.getDefault());
        try {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date d = sdf.parse(time);
            sdf = new SimpleDateFormat(pattern, Locale.getDefault());
            sdf.setTimeZone(Calendar.getInstance().getTimeZone());
            return sdf.parse(sdf.format(d));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public static Date parseTimeUTCtoDefault(long time) {

        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            cal.setTimeInMillis(time);
            Date d = cal.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.setTimeZone(Calendar.getInstance().getTimeZone());
            return sdf.parse(sdf.format(d));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public static String parseTimeUTCtoDefault(String time, String fromPattern,
                                               String toPattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(fromPattern,
                Locale.getDefault());
        try {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date d = sdf.parse(time);
            sdf = new SimpleDateFormat(toPattern, Locale.getDefault());
            sdf.setTimeZone(Calendar.getInstance().getTimeZone());
            return sdf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static long daysBetween(Date startDate, Date endDate) {
        Calendar sDate = getDatePart(startDate);
        Calendar eDate = getDatePart(endDate);

        long daysBetween = 0;
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }

    public static Calendar getDatePart(Date date) {
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millisecond in second

        return cal;                                  // return the date part
    }

    public static String nullSafe(String content) {
        if (content == null) {
            return "";
        }

        if (content.equalsIgnoreCase("null")) {
            return "";
        }

        return content;
    }

    public static String nullSafe(String content, String defaultStr) {
        if (StringUtils.isEmpty(content)) {
            return defaultStr;
        }

        if (content.equalsIgnoreCase("null")) {
            return defaultStr;
        }

        return content;
    }

    public static String nullSafeDash(String content) {
        if (StringUtils.isEmpty(content)) {
            return "-";
        }

        if (content.equalsIgnoreCase("null")) {
            return "-";
        }

        return content;
    }

    public static String nullSafe(int content, String defaultStr) {
        if (content == 0) {
            return defaultStr;
        }

        return "" + content;
    }

    public static ImageLoader initImageLoader(Context mContext) {
        ImageLoader imageLoader = null;
        try {
            File cacheDir = StorageUtils.getOwnCacheDirectory(mContext,
                    Constant.CACHE_DIR);

            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true).cacheInMemory(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
            ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                    mContext).defaultDisplayImageOptions(defaultOptions)
                    .diskCache(new UnlimitedDiskCache(cacheDir))
                    .memoryCache(new WeakMemoryCache());

            ImageLoaderConfiguration config = builder.build();
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(config);
            L.writeLogs(false);

            return imageLoader;
        } catch (Exception e) {
            Utils.sendExceptionReport(e);
        }

        try {
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
            ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                    mContext).defaultDisplayImageOptions(defaultOptions)
                    .memoryCache(new WeakMemoryCache());

            ImageLoaderConfiguration config = builder.build();
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(config);
            L.writeLogs(false);
        } catch (Exception e) {
            Utils.sendExceptionReport(e);
        }

        return imageLoader;
    }

    public static boolean isSDcardMounted() {

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)
                && !state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            return true;
        }

        return false;
    }

    public static boolean isGPSProviderEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean isNetworkProviderEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        return locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static boolean isLocationProviderEnabled(Context context) {
        return (isGPSProviderEnabled(context) || isNetworkProviderEnabled(context));
    }

    public static boolean isLocationProviderRequired(Context context) {
        String lang = getPref(context, Constant.USER_LONGITUDE, "");
        String lat = getPref(context, Constant.USER_LATITUDE, "");

        if (lat.length() > 0 && lang.length() > 0) {
            return false;
        }

        return true;
    }

    public static boolean isUserLoggedIn(Context c) {
        return (getUid(c).length() > 0) ? true : false;
    }

    public static String getUid(Context c) {
        return Utils.getPref(c, RequestParamsUtils.EMAIL, "");
    }

    public static TimeZOne getTimeZone(Context c) {
        String response = Utils.getPref(c, Constant.TIME_ZONE, "");

        if (response != null && response.length() > 0) {

            TimeZOne login = new Gson().fromJson(
                    response, new TypeToken<TimeZOne>() {
                    }.getType());

//            if (login.) {
            return login;
//            }

        }
        return null;
    }

    public static DrugsRes getDrugsData(Context c) {
        String response = Utils.getPref(c, Constant.DRUGS_DATA, "");

        if (response != null && response.length() > 0) {

            DrugsRes drugsRes = new Gson().fromJson(
                    response, new TypeToken<DrugsRes>() {
                    }.getType());
            return drugsRes;
        }
        return null;
    }


    public static void clearLoginCredetials(Activity c) {
        Utils.delPref(c, RequestParamsUtils.EMAIL);
        Utils.delPref(c, RequestParamsUtils.TOKEN);
        Utils.delPref(c, Constant.LOGIN_INFO);
        Utils.delPref(c, Constant.USER_LATITUDE);
        Utils.delPref(c, Constant.USER_LONGITUDE);


        NotificationManager nMgr = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();
    }

    public static void showDialog(final Activity c, String title, String message) {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(c)
                .title(title)
                .content(message)
                .positiveText(R.string.btn_ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {

                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {

                    }
                });

        MaterialDialog dialog = builder.build();
        dialog.show();
    }

    public static void showDialog(Context c, String title, String message,
                                  final View.OnClickListener onClickListener) {

        Dialog.Builder builder = null;

        builder = new SimpleDialog.Builder(R.style.SimpleDialogLight) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                super.onPositiveActionClicked(fragment);
                onClickListener.onClick(null);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        ((SimpleDialog.Builder) builder).message("" + message)
                .title("" + title)
                .positiveAction("" + c.getString(R.string.btn_ok));

        builder.build(c).show();

    }

    public static void showDialog(Context c, String title, String message,
                                  String btnPos, String btnNeg,
                                  final View.OnClickListener onPosClickListener,
                                  final View.OnClickListener onNegClickListener) {

        Dialog.Builder builder = null;

        builder = new SimpleDialog.Builder(R.style.SimpleDialogLight) {

            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                super.onPositiveActionClicked(fragment);
                onPosClickListener.onClick(null);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
                onNegClickListener.onClick(null);
            }

        };

        ((SimpleDialog.Builder) builder).message("" + message)
                .title("" + title)
                .positiveAction("" + btnPos)
                .negativeAction("" + btnNeg);

        builder.build(c).show();
    }

    public static ArrayList<String> asList(String str) {
        ArrayList<String> items = new ArrayList<String>(Arrays.asList(str
                .split("\\s*,\\s*")));

        return items;
    }

    public static String implode(ArrayList<String> data) {
        try {
            String devices = "";
            for (String iterable_element : data) {
                devices = devices + "," + iterable_element;
            }

            if (devices.length() > 0 && devices.startsWith(",")) {
                devices = devices.substring(1);
            }

            return devices;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Create a File for saving an image or video
     *
     * @return Returns file reference
     */
    public static File getOutputMediaFile() {
        try {
            // To be safe, you should check that the SDCard is mounted
            // using Environment.getExternalStorageState() before doing this.
            File mediaStorageDir;
            if (isSDcardMounted()) {
                mediaStorageDir = new File(Constant.FOLDER_RIDEINN_PATH);
            } else {
                mediaStorageDir = new File(Environment.getRootDirectory(),
                        Constant.FOLDER_NAME);
            }

            // This location works best if you want the created images to be
            // shared
            // between applications and persist after your app has been
            // uninstalled.

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null;
                }
            }

            // Create a media file name

            File mediaFile = new File(mediaStorageDir.getPath()
                    + File.separator + new Date().getTime() + ".jpg");
            mediaFile.createNewFile();

            return mediaFile;
        } catch (Exception e) {
            return null;
        }
    }

    public static void scanMediaForFile(Context context, String filePath) {
        resetExternalStorageMedia(context, filePath);
        notifyMediaScannerService(context, filePath);
    }

    public static boolean resetExternalStorageMedia(Context context,
                                                    String filePath) {
        try {
            if (Environment.isExternalStorageEmulated())
                return (false);
            Uri uri = Uri.parse("file://" + new File(filePath));
            Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, uri);

            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }

        return (true);
    }

    public static void notifyMediaScannerService(Context context,
                                                 String filePath) {

        MediaScannerConnection.scanFile(context, new String[]{filePath},
                null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Debug.i("ExternalStorage", "Scanned " + path + ":");
                        Debug.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }

    public static void cancellAllNotication(Context context) {

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static String toInitCap(String param) {
        try {
            if (param != null && param.length() > 0) {
                char[] charArray = param.toCharArray(); // convert into char
                // array
                charArray[0] = Character.toUpperCase(charArray[0]); // set
                // capital
                // letter to
                // first
                // position
                return new String(charArray); // return desired output
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return param;
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Debug.e("LOOK", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static String getExtenstion(String urlPath) {
        if (urlPath.contains(".")) {
            String extension = urlPath.substring(urlPath.lastIndexOf(".") + 1);
            return extension;
        }

        return "";
    }

    public static String getFileName(String urlPath) {
        if (urlPath.contains(".")) {
            return urlPath.substring(urlPath.lastIndexOf("/") + 1);
        }

        return "";
    }

    public static float dpToPx(Context context, int val) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, val, r.getDisplayMetrics());
    }

    public static float spToPx(Context context, int val) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, val, r.getDisplayMetrics());
    }

    public static void noInternet(Activity a) {
        showDialog(a, a.getString(R.string.connection_title), a.getString(R.string.connection_not_available));
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        Debug.e("type", "" + type);
        return type;
    }

    public static boolean isJPEGorPNG(String url) {
        try {
            String type = getMimeType(url);
            String ext = type.substring(type.lastIndexOf("/") + 1);
            if (ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("png")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }

        return false;
    }

    public static double getFileSize(String url) {
        File file = new File(url);

        // Get length of file in bytes
        double fileSizeInBytes = file.length();
        // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        double fileSizeInKB = fileSizeInBytes / 1024;
        // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        double fileSizeInMB = fileSizeInKB / 1024;

        Debug.e("fileSizeInMB", "" + fileSizeInMB);
        return fileSizeInMB;
    }

    public static String getAsteriskName(String str) {
        int n = 4;

        str = Utils.nullSafe(str);
        StringBuilder fStr = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            if (str.length() > n) {
                fStr.append(str.substring(0, n));
                for (int i = 0; i < str.length() - n; i++) {
                    fStr.append("*");
                }

                return fStr.toString();
            } else {
                fStr.append(str.substring(0, str.length() - 1));
            }
        }
        return str;
    }

    public static TextDrawable initTextDrawable(Context c, int color) {
        return TextDrawable.builder()
                .beginConfig()
                .withBorder(4).width(50)
                .height(50)
                .fontSize(30) /* size in px */
                .bold()
                .textColor(color)
                .endConfig()
                .buildRound("0", Color.WHITE);
    }

    public static TextDrawable initDynamicTextDrawable(Context c, int color, String count) {
        return TextDrawable.builder()
                .beginConfig()
                .withBorder(4).width(50)
                .height(50)
                .fontSize(30) /* size in px */
                .bold()
                .textColor(color)
                .endConfig()
                .buildRound(count, Color.WHITE);
    }

    public static void initSeekBar(final Context c, SeekBar seekBar, final CompletionHandler onProgressChangedComplete) {

        TextDrawable drawable1 = Utils.initTextDrawable(c, c.getResources().getColor(R.color.theme_blue));
        seekBar.setThumb(drawable1);
        seekBar.getProgressDrawable().setColorFilter(c.getResources().getColor(R.color.theme_blue), PorterDuff.Mode.SRC_IN);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresss, boolean b) {

                if (progresss <= 3) {
                    TextDrawable drawable1 = Utils.initDynamicTextDrawable(c, c.getResources().getColor(R.color.theme_blue), String.valueOf(progresss));
                    seekBar.setThumb(drawable1);
                    seekBar.getProgressDrawable().setColorFilter(c.getResources().getColor(R.color.theme_blue), PorterDuff.Mode.SRC_IN);

                } else if (progresss > 3 && progresss <= 7) {
                    TextDrawable drawable1 = Utils.initDynamicTextDrawable(c, c.getResources().getColor(R.color.theme_green), String.valueOf(progresss));
                    seekBar.setThumb(drawable1);
                    seekBar.getProgressDrawable().setColorFilter(c.getResources().getColor(R.color.theme_green), PorterDuff.Mode.SRC_IN);


                } else {
                    TextDrawable drawable1 = Utils.initDynamicTextDrawable(c, c.getResources().getColor(R.color.theme_red), String.valueOf(progresss));
                    seekBar.setThumb(drawable1);
                    seekBar.getProgressDrawable().setColorFilter(c.getResources().getColor(R.color.theme_red), PorterDuff.Mode.SRC_IN);
                }

                onProgressChangedComplete.onComplete();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public static void initSeekBarSidEffects(final Context c, SeekBar seekBar) {

        TextDrawable drawable1 = Utils.initTextDrawable(c, c.getResources().getColor(R.color.theme_blue));
        seekBar.setThumb(drawable1);
        seekBar.getProgressDrawable().setColorFilter(c.getResources().getColor(R.color.theme_blue), PorterDuff.Mode.SRC_IN);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresss, boolean b) {

                if (progresss <= 2) {
                    TextDrawable drawable1 = Utils.initDynamicTextDrawable(c, c.getResources().getColor(R.color.theme_blue), String.valueOf(progresss));
                    seekBar.setThumb(drawable1);
                    seekBar.getProgressDrawable().setColorFilter(c.getResources().getColor(R.color.theme_blue), PorterDuff.Mode.SRC_IN);

                } else if (progresss > 2 && progresss <= 4) {
                    TextDrawable drawable1 = Utils.initDynamicTextDrawable(c, c.getResources().getColor(R.color.theme_green), String.valueOf(progresss));
                    seekBar.setThumb(drawable1);
                    seekBar.getProgressDrawable().setColorFilter(c.getResources().getColor(R.color.theme_green), PorterDuff.Mode.SRC_IN);


                } else {
                    TextDrawable drawable1 = Utils.initDynamicTextDrawable(c, c.getResources().getColor(R.color.theme_red), String.valueOf(progresss));
                    seekBar.setThumb(drawable1);
                    seekBar.getProgressDrawable().setColorFilter(c.getResources().getColor(R.color.theme_red), PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public static void initNewSeekBar(final Context c, SeekBar seekBar) {

        seekBar.setThumb(c.getResources().getDrawable(R.mipmap.tooltip_green_2x));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresss, boolean b) {

                if (progresss <= 2) {
                    seekBar.setThumb(c.getResources().getDrawable(R.mipmap.tooltip_green_2x));
                    seekBar.setProgress(progresss);

                } else if (progresss >= 3 && progresss <= 4) {
                    seekBar.setThumb(c.getResources().getDrawable(R.mipmap.tooltip_orange_2x));
                    seekBar.setProgress(progresss);

                } else {
                    seekBar.setThumb(c.getResources().getDrawable(R.mipmap.tooltip_pink_2x));
                    seekBar.setProgress(progresss);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public static void initCircularSeekBar(final Context c, final CircularSeekBar seekBar) {

        seekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
                seekBar.setProgress((int) Math.ceil(progress));
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {
            }
        });
    }

    public static List<Country> getAllCountries(Context context) {

        List<Country> allCountriesList = new ArrayList<>();

        try {

            String[] countries = context.getResources().getStringArray(R.array.country_array);

            for (String country1 : countries) {

                Country country = new Country();
                try {
                    country.setCode("" + country1.split(":")[1]);
                    country.setName("" + country1.split(":")[0]);
                } catch (Exception e) {
                    country.setCode("");
                    country.setName("");
                }
                allCountriesList.add(country);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return allCountriesList;
    }

    public static ArrayList<SettingActivity.ReminderData> getDefaultReminders(Context context) {
        ArrayList<SettingActivity.ReminderData> data = new ArrayList<>();

        SettingActivity.ReminderData data1 = getDefaultReminder(1);
        data.add(data1);
        SettingActivity.ReminderData data2 = getDefaultReminder(2);
        data.add(data2);

        return data;
    }

    private static SettingActivity.ReminderData getDefaultReminder(int n) {
        SettingActivity.ReminderData tData = new SettingActivity.ReminderData();

        Calendar calendar = Calendar.getInstance();

        if (n == 1) {
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.AM_PM, Calendar.AM);

            tData.time = "08:30 AM";
            tData.lable = "Have you tracked your mood this morning?";
            tData.timeMin = (8 * 60) + 30;
            tData.timestamp = calendar.getTimeInMillis();
            tData.reqCode = 12345677;
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, 20);
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.AM_PM, Calendar.PM);

            tData.time = "08:30 PM";
            tData.lable = "Have you tracked your mood today?";
            tData.timeMin = (20 * 60) + 30;
            tData.timestamp = calendar.getTimeInMillis();
            tData.reqCode = 12345678;
        }


        tData.sunday = "1";
        tData.monday = "1";
        tData.tuesday = "1";
        tData.wednesday = "1";
        tData.thursday = "1";
        tData.friday = "1";
        tData.saturday = "1";
        tData.isOn = true;

        return tData;
    }

    public static void restartReminders(Context context) {
        String pref = Utils.getPref(context, Constant.REMINDER + "_" + Utils.getUid(context), "");
        if (!StringUtils.isEmpty(pref)) {
            ArrayList<SettingActivity.ReminderData> data = new Gson().fromJson(pref, new TypeToken<ArrayList<SettingActivity.ReminderData>>() {
            }.getType());

            Utils.stopAllReminders(context, data);
            Utils.setLatestReminder(context, data);
        }
    }

    public static void stopAllReminders(Context context, List<SettingActivity.ReminderData> data) {
        for (SettingActivity.ReminderData reminderData : data) {
            ReminderServiceFunction.AppointmentReminder reminder = new ReminderServiceFunction.AppointmentReminder();
            reminder.reqCode = reminderData.reqCode;
            ReminderServiceFunction.stopAllAlarms(context, reminder);
        }
    }

    public static void setLatestReminder(Context context, List<SettingActivity.ReminderData> data) {
        List<SettingActivity.ReminderData> latestReminder = Utils.getLatestReminder(context, data);

        for (SettingActivity.ReminderData reminderData : latestReminder) {
            if (reminderData.isOn) {
                ReminderServiceFunction.AppointmentReminder reminder = new ReminderServiceFunction.AppointmentReminder();
                reminder.time = reminderData.timestamp;
                reminder.reqCode = reminderData.reqCode;
                reminder.title = "" + reminderData.lable;
                reminder.message = "";
                ReminderServiceFunction.scheduleAppointmentReminder(context, reminder, 0, false);
            }
        }
    }

    public static List<SettingActivity.ReminderData> getLatestReminder(Context context, List<SettingActivity.ReminderData> reminderDataList) {
        List<SettingActivity.ReminderData> temp = new ArrayList<>();

        for (SettingActivity.ReminderData reminderData : reminderDataList) {
            if (reminderData.timestamp > new Date().getTime() &&
                    !reminderData.sunday.equalsIgnoreCase("1") &&
                    !reminderData.monday.equalsIgnoreCase("1") &&
                    !reminderData.tuesday.equalsIgnoreCase("1") &&
                    !reminderData.wednesday.equalsIgnoreCase("1") &&
                    !reminderData.thursday.equalsIgnoreCase("1") &&
                    !reminderData.friday.equalsIgnoreCase("1") &&
                    !reminderData.saturday.equalsIgnoreCase("1")) {

                temp.add(reminderData);
            }
        }

//        List<SettingActivity.ReminderData> reminderForDay = getReminderForDay(reminderDataList);
        temp.addAll(getReminderForDay(reminderDataList));

        return temp;
    }

    private static List<SettingActivity.ReminderData> getReminderForDay(List<SettingActivity.ReminderData> reminderDataList) {
        List<SettingActivity.ReminderData> temp = new ArrayList<>();

        Calendar curCal = Calendar.getInstance();
        int day = curCal.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:
                for (SettingActivity.ReminderData reminderData : reminderDataList) {
                    if (reminderData.sunday.equalsIgnoreCase("1")) {
                        Calendar calT = Calendar.getInstance();
                        calT.set(Calendar.MINUTE, 0);
                        calT.set(Calendar.HOUR_OF_DAY, 0);
                        calT.set(Calendar.SECOND, 0);
                        calT.set(Calendar.MILLISECOND, 0);

                        calT.add(Calendar.MINUTE, reminderData.timeMin);
                        reminderData.timestamp = calT.getTimeInMillis();
                        if (reminderData.timestamp > Calendar.getInstance().getTimeInMillis()) {
                            temp.add(reminderData);
                        }
                    }
                }
                break;
            case Calendar.MONDAY:
                for (SettingActivity.ReminderData reminderData : reminderDataList) {
                    if (reminderData.monday.equalsIgnoreCase("1")) {
                        Calendar calT = Calendar.getInstance();
                        calT.set(Calendar.MINUTE, 0);
                        calT.set(Calendar.HOUR_OF_DAY, 0);
                        calT.set(Calendar.SECOND, 0);
                        calT.set(Calendar.MILLISECOND, 0);

                        calT.add(Calendar.MINUTE, reminderData.timeMin);
                        reminderData.timestamp = calT.getTimeInMillis();
                        if (reminderData.timestamp > Calendar.getInstance().getTimeInMillis()) {
                            temp.add(reminderData);
                        }
                    }
                }
                break;
            case Calendar.TUESDAY:
                for (SettingActivity.ReminderData reminderData : reminderDataList) {
                    if (reminderData.tuesday.equalsIgnoreCase("1")) {
                        Calendar calT = Calendar.getInstance();
                        calT.set(Calendar.MINUTE, 0);
                        calT.set(Calendar.HOUR_OF_DAY, 0);
                        calT.set(Calendar.SECOND, 0);
                        calT.set(Calendar.MILLISECOND, 0);

                        calT.add(Calendar.MINUTE, reminderData.timeMin);
                        reminderData.timestamp = calT.getTimeInMillis();
                        if (reminderData.timestamp > Calendar.getInstance().getTimeInMillis()) {
                            temp.add(reminderData);
                        }
                    }
                }
                break;
            case Calendar.WEDNESDAY:
                for (SettingActivity.ReminderData reminderData : reminderDataList) {
                    if (reminderData.wednesday.equalsIgnoreCase("1")) {
                        Calendar calT = Calendar.getInstance();
                        calT.set(Calendar.MINUTE, 0);
                        calT.set(Calendar.HOUR_OF_DAY, 0);
                        calT.set(Calendar.SECOND, 0);
                        calT.set(Calendar.MILLISECOND, 0);

                        calT.add(Calendar.MINUTE, reminderData.timeMin);
                        reminderData.timestamp = calT.getTimeInMillis();
                        if (reminderData.timestamp > Calendar.getInstance().getTimeInMillis()) {
                            temp.add(reminderData);
                        }
                    }
                }
                break;
            case Calendar.THURSDAY:
                for (SettingActivity.ReminderData reminderData : reminderDataList) {
                    if (reminderData.thursday.equalsIgnoreCase("1")) {
                        Calendar calT = Calendar.getInstance();
                        calT.set(Calendar.MINUTE, 0);
                        calT.set(Calendar.HOUR_OF_DAY, 0);
                        calT.set(Calendar.SECOND, 0);
                        calT.set(Calendar.MILLISECOND, 0);

                        calT.add(Calendar.MINUTE, reminderData.timeMin);
                        reminderData.timestamp = calT.getTimeInMillis();
                        if (reminderData.timestamp > Calendar.getInstance().getTimeInMillis()) {
                            temp.add(reminderData);
                        }
                    }
                }
                break;
            case Calendar.FRIDAY:
                for (SettingActivity.ReminderData reminderData : reminderDataList) {
                    if (reminderData.friday.equalsIgnoreCase("1")) {
                        Calendar calT = Calendar.getInstance();
                        calT.set(Calendar.MINUTE, 0);
                        calT.set(Calendar.HOUR_OF_DAY, 0);
                        calT.set(Calendar.SECOND, 0);
                        calT.set(Calendar.MILLISECOND, 0);

                        calT.add(Calendar.MINUTE, reminderData.timeMin);
                        reminderData.timestamp = calT.getTimeInMillis();
                        if (reminderData.timestamp > Calendar.getInstance().getTimeInMillis()) {
                            temp.add(reminderData);
                        }
                    }
                }
                break;
            case Calendar.SATURDAY:
                for (SettingActivity.ReminderData reminderData : reminderDataList) {
                    if (reminderData.saturday.equalsIgnoreCase("1")) {
                        Calendar calT = Calendar.getInstance();
                        calT.set(Calendar.MINUTE, 0);
                        calT.set(Calendar.HOUR_OF_DAY, 0);
                        calT.set(Calendar.SECOND, 0);
                        calT.set(Calendar.MILLISECOND, 0);

                        calT.add(Calendar.MINUTE, reminderData.timeMin);
                        reminderData.timestamp = calT.getTimeInMillis();
                        if (reminderData.timestamp > Calendar.getInstance().getTimeInMillis()) {
                            temp.add(reminderData);
                        }
                    }
                }
                break;
            default:
        }

        return temp;
    }

    public static SSLContext getSslContext() {

        TrustManager[] byPassTrustManagers = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        }};

        SSLContext sslContext = null;

        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, byPassTrustManagers, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return sslContext;

    }

}
