package com.kartum.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.kartum.receiver.ReminderBroadcastReceiver;

import java.util.Date;

/*
 * Class that will handle the functionality related to the user profile creation
 * 
 */
public class ReminderServiceFunction {

    static public class AppointmentReminder {
        public long time;
        public String title;
        public String message;
        public int reqCode;
    }

    /**
     * Set the service start up at midnight 12 and will start the service named
     * TurnonService Repeating daily at 12 midnight
     */
    public static void scheduleAppointmentReminder(Context context, AppointmentReminder reminder, int interval, boolean isRepeating) {
        try {
//            Calendar cal = Calendar.getInstance();
//            cal.add(Calendar.DAY_OF_MONTH, 1);
//            cal.set(Calendar.HOUR_OF_DAY, 00);
//            cal.set(Calendar.MINUTE, 00);
//            cal.set(Calendar.SECOND, 00);

            AlarmManager am = (AlarmManager) context
                    .getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(context, ReminderBroadcastReceiver.class);
            i.putExtra("data", "" + new Gson().toJson(reminder));
            PendingIntent pI = PendingIntent.getBroadcast(context, reminder.reqCode, i,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            boolean alarmUp = (PendingIntent.getBroadcast(context, reminder.reqCode, i,
                    PendingIntent.FLAG_NO_CREATE) != null);

            if (alarmUp) {
                Debug.e("Init Alarm", "Alarm is active");
            } else {
                Debug.e("Init Alarm", "Alarm is not active");
            }

            try {
                am.cancel(pI);
                pI.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }

            alarmUp = (PendingIntent.getBroadcast(context, reminder.reqCode, i,
                    PendingIntent.FLAG_NO_CREATE) != null);

            if (alarmUp) {
                Debug.e("", "After stopping : Alarm is active");
            } else {
                Debug.e("", "After stopping : Alarm is not active");
            }

            pI = PendingIntent.getBroadcast(context, reminder.reqCode, i,
                    PendingIntent.FLAG_UPDATE_CURRENT);

//            Date d = new Date(cal.getTimeInMillis());
            // Debugger.debugI("Final Start Time of alarm " + d.toString());

//            am.setRepeating(AlarmManager.RTC_WAKEUP, reminder.time, 10 * 1000, pI);
            if (isRepeating) {
                Debug.e("", "Setting up alarm : " + isRepeating + " " + new Date(reminder.time).toString());
                am.setRepeating(AlarmManager.RTC_WAKEUP, reminder.time, interval, pI);
            } else {
                Debug.e("", "Setting up alarm : " + isRepeating + " " + new Date(reminder.time).toString());
                am.set(AlarmManager.RTC_WAKEUP, reminder.time, pI);
            }


            alarmUp = (PendingIntent.getBroadcast(context, reminder.reqCode, i,
                    PendingIntent.FLAG_NO_CREATE) != null);

            if (alarmUp) {
                Debug.e("", "After restarting : Alarm is active");
            } else {
                Debug.e("", "After restarting : Alarm is not active");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopAllAlarms(Context context, AppointmentReminder reminder) {
        AlarmManager am = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, ReminderBroadcastReceiver.class);
        // i.setData(Uri.parse("timer:" + id));
        i.putExtra("data", "" + new Gson().toJson(reminder));

        PendingIntent pI = PendingIntent.getBroadcast(context, reminder.reqCode, i,
                PendingIntent.FLAG_UPDATE_CURRENT);
//		CommonFunctions.setPref(context, Constants.PREF_CURRENT_PROFILE, "");

        boolean alarmUp = (PendingIntent.getBroadcast(context, reminder.reqCode, i,
                PendingIntent.FLAG_UPDATE_CURRENT) != null);

        if (alarmUp) {
            Debug.e("stopAllAlarms", "Alarm is already active");
        } else {
            Debug.e("stopAllAlarms", "Alarm is not active : no need to stop");
            return;
        }

        try {
            am.cancel(pI);
            pI.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        alarmUp = (PendingIntent.getBroadcast(context, reminder.reqCode, i,
                PendingIntent.FLAG_UPDATE_CURRENT) != null);

        if (alarmUp) {
            Debug.e("stopAllAlarms", "after stopping : Alarm is already active");
        } else {
            Debug.e("stopAllAlarms", "after stopping : Alarm is not active");
        }
    }

    public static void checkForAnyAlarmServiceisActive(Context context) {

        Intent ip = new Intent(context, ReminderBroadcastReceiver.class);
        boolean alarmProfile = (PendingIntent.getService(context, 0, ip,
                PendingIntent.FLAG_NO_CREATE) != null);

        if (alarmProfile) {
            Debug.e("", "profile is already active");
        } else {
            Debug.e("", "profile is not active");
        }
    }

    /**
     * Start the service that will set notification on the status bar
     *
     * @param : id = Id of the reminder
     * @param : interval = repeating interval(can be null)
     * @param : startTime = Starting time of the reminder(notnull)(hh:mm in
     *          seconds only)
     * @param : endTime = Ending time of the reminder(can be null)(hh:mm in
     *          seconds only)
     */
    // Start Activity
//    public static void startReadingService(Context context, String id,
//                                           long intervalto, long startTime, long endtime) {
//        try {
//            Debug.e("", "Start the Profile..with interval : " + intervalto
//                    + " starttime : " + startTime + " endtime : " + endtime);
//
//            Calendar start_cal = Calendar.getInstance();
//
//            start_cal.set(Calendar.HOUR_OF_DAY, getHourOfRepeates(startTime));
//            start_cal.set(Calendar.MINUTE, getMinOfRepeates(startTime));
//            start_cal.set(Calendar.SECOND, 0);
//
//            AlarmManager am = (AlarmManager) context
//                    .getSystemService(Context.ALARM_SERVICE);
//            Intent i = new Intent(context, ProfileService.class);
//            // i.setData(Uri.parse("timer:" + id));
////            CommonFunctions
////                    .setPref(context, Constants.PREF_CURRENT_PROFILE, id);
//
//            if (endtime != -1 && endtime != 0) {
//                Calendar endCal = Calendar.getInstance();
//
//                endCal.set(Calendar.HOUR_OF_DAY, getHourOfRepeates(endtime));
//                endCal.set(Calendar.MINUTE, getMinOfRepeates(endtime));
//                endCal.set(Calendar.SECOND, 0);
//
//                i.putExtra("endTime", endCal.getTimeInMillis());
//                i.putExtra("interval", intervalto);
//
//                Debug.e("start Time", start_cal.getTime().toString());
//                Debug.e("End Time", endCal.getTime().toString());
//
//                if (endCal.getTimeInMillis() > new Date().getTime()) {
//                    // Calendar new_cal = cal;
//
//                    if (start_cal.getTimeInMillis() < new Date().getTime()) {
//                        while (start_cal.getTimeInMillis() < new Date()
//                                .getTime()) {
//                            start_cal.add(Calendar.MINUTE,
//                                    (int) ((intervalto / 1000) / 60));
//                            Debug.e("jump to Time : ", start_cal
//                                    .getTime().toString());
//
//                            if (start_cal.getTimeInMillis() > endCal
//                                    .getTimeInMillis()) {
//
//                                stopRepeatingActivity(context);
//                                StartTimerService(context, true);
//                                return;
//                            }
//
//                        }
//                        // cal.add(Calendar.MILLISECOND, -(int) intervalto);
//                        // Date d = new Date(new_cal.getTimeInMillis());
//
//                        Date d = new Date(start_cal.getTimeInMillis());
//                        Debugger.debugI("Final Start Time of alarm "
//                                + d.toString());
//
//                        stopRepeatingActivity(context);
//
//                        PendingIntent pI = PendingIntent.getService(context, 0,
//                                i, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                        am.setRepeating(AlarmManager.RTC_WAKEUP, d.getTime(),
//                                intervalto, pI);
//                    } else {
//                        PendingIntent pI = PendingIntent.getService(context, 0,
//                                i, PendingIntent.FLAG_UPDATE_CURRENT);
//                        am.setRepeating(AlarmManager.RTC_WAKEUP, start_cal
//                                .getTime().getTime(), intervalto, pI);
//                    }
//                } else {
//                    Debug.e("", "The time interval is finished", "for "
//                            + id);
//                    return;
//                }
//            } else {
//
//                Debug.e("", "Alarm can not start as youe end time is set");
//
//                // PendingIntent pI = PendingIntent.getService(context, 0, i,
//                // Intent.FLAG_GRANT_READ_URI_PERMISSION
//                // | PendingIntent.FLAG_UPDATE_CURRENT);
//                // try {
//                // am.cancel(pI);
//                // } catch (Exception e) {
//                // Debug.e("","Exception",
//                // "In setting the timer...of once " + e.toString());
//                // }
//                // am.set(AlarmManager.RTC_WAKEUP, cal.getTime().getTime(), pI);
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//    }

    /**
     * Start the service that will set notification on the status bar
     *
     * @param : id = Id of the reminder
     *
     * @param : interval = repeating interval(can be null)
     *
     * @param : startTime = Starting time of the reminder(notnull)(hh:mm in
     *        seconds only)
     *
     * @param : endTime = Ending time of the reminder(can be null)(hh:mm in
     *        seconds only)
     */
    // Start Activity
    // public static void StartAlarmOnce(Context context, String id, long
    // startTime) {
    // try {
    // Calendar cur_cal = Calendar.getInstance();
    // Calendar cal = Calendar.getInstance(Locale.getDefault());
    // cal.set(Calendar.HOUR_OF_DAY, getHourOfRepeates(startTime));
    // cal.set(Calendar.MINUTE, getMinOfRepeates(startTime));
    // cal.set(Calendar.SECOND, cur_cal.get(Calendar.SECOND));
    // cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
    // cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));
    // Debug.e("","Set Time", cal.getTime().toLocaleString());
    // Debug.e("","Current Time",
    // new Date(System.currentTimeMillis()).toLocaleString());
    // AlarmManager am = (AlarmManager) context
    // .getSystemService(Context.ALARM_SERVICE);
    // Intent i = new Intent(context, ProfileService.class);
    // i.setData(Uri.parse("timer:" + id));
    // CommonFunctions
    // .setPref(context, Constants.PREF_CURRENT_PROFILE, id);
    // PendingIntent pI = PendingIntent.getService(context, 0, i,
    // Intent.FLAG_GRANT_READ_URI_PERMISSION);
    // try {
    // am.cancel(pI);
    // } catch (Exception e) {
    // Debug.e("","Exception", "In setting the timer...of once "
    // + e.toString());
    // }
    // am.set(AlarmManager.RTC_WAKEUP, cal.getTime().getTime(), pI);
    //
    // } catch (Exception e) {
    // // TODO: handle exception
    // Debug.e("","Exception while creating the time", "yes");
    // }
    // }

    /**
     * It will wakeup the current reminder after some time as defined in the
     * settings
     */

    // public static void AskMeAfterSomeTime(Context context) {
    // try {
    // // long interval = getPref(context, READING_TIME, 1) * 60 * 1000;
    // Calendar cur_cal = Calendar.getInstance();
    // cur_cal.add(Calendar.MILLISECOND,
    // Constants.REMIND_AFFTER_MIN * 60 * 1000);
    // AlarmManager am = (AlarmManager) context
    // .getSystemService(Context.ALARM_SERVICE);
    // Intent i = new Intent(context, ProfileService.class);
    // i.setData(Uri.parse("timer:" + new Random(10).nextInt()));
    // PendingIntent pI = PendingIntent.getService(context, 0, i,
    // Intent.FLAG_GRANT_READ_URI_PERMISSION);
    // try {
    // am.cancel(pI);
    // } catch (Exception e) {
    // Debug.e("","Exception",
    // "In setting the timer...of once of ask me after some time"
    // + e.toString());
    // }
    // am.set(AlarmManager.RTC_WAKEUP, cur_cal.getTime().getTime(), pI);
    //
    // } catch (Exception e) {
    // // TODO: handle exception
    // Debug.e("","Exception while creating the time",
    // "yes" + e.toString());
    //
    // }
    // }

    // Stop Activity


}
