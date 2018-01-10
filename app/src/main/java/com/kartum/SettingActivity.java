package com.kartum;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kartum.adapter.EditSettingAdapter;
import com.kartum.utils.Constant;
import com.kartum.utils.Debug;
import com.kartum.utils.Utils;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.TimePickerDialog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.switchPushNotific)
    Switch switchPushNotific;
    @BindView(R.id.switchLocation)
    Switch switchLocation;
    @BindView(R.id.switchAlertClin)
    Switch switchAlertClin;
    @BindView(R.id.switchContact)
    Switch switchContact;

    @BindView(R.id.tvAddReminders)
    TextView tvAddReminders;
    CheckBox cbSunday;
    RecyclerView.LayoutManager layoutManager;
    public EditSettingAdapter mAdapter;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    NotificationManager mNotification;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        initDrawer(true);
        init();
    }

    ArrayList<ReminderData> data;

    private void init() {

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new EditSettingAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        tvAddReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReminderDialog();
            }
        });

//        String pref = Utils.getPref(getActivity(), Constant.REMINDER + "_" + Utils.getUid(getActivity()), "");
////        Utils.delPref(getActivity(), Constant.REMINDER);
//
//        if (pref.isEmpty()) {
//            data = Utils.getDefaultReminders(getActivity());
//            Utils.setPref(getActivity(), Constant.REMINDER + "_" + Utils.getUid(getActivity()), new Gson().toJson(data));
//        } else {
//            data = new Gson().fromJson(pref, new TypeToken<ArrayList<ReminderData>>() {
//            }.getType());
//        }

        mAdapter.setmEventlistener(new EditSettingAdapter.Eventlistener() {
            @Override
            public void OnMenuclick(int position, View view) {
                ReminderData obj = mAdapter.getItem(position);
                showPopupMenu(view, position, obj);
            }

            @Override
            public void onSwitchClick(int position) {
                mAdapter.changeSelection(position, true);
                ReminderData obj = mAdapter.getItem(position);
                editData(obj);
            }
        });

        mAdapter.addAll(data);

//        boolean b = Utils.getPref(getActivity(), Constant.NOTIFICATION + "_" + Utils.getUid(getActivity()), switchPushNotific.isChecked());
//        Debug.e("boolean", "" + b);
        switchPushNotific.setChecked(Utils.getPref(getActivity(), Constant.NOTIFICATION, true));
        if (Utils.getPref(getActivity(), Constant.NOTIFICATION + "_" + Utils.getUid(getActivity()), switchPushNotific.isChecked())) {
            switchPushNotific.setChecked(true);
        }
        switchPushNotific.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (switchPushNotific.isChecked()) {
                    Utils.setPref(getActivity(), Constant.NOTIFICATION + "_" + Utils.getUid(getActivity()), switchPushNotific.isChecked());
                } else {
                    Utils.delPref(getActivity(), Constant.NOTIFICATION + "_" + Utils.getUid(getActivity()));
                }
            }
        });

        if (Utils.getPref(getActivity(), Constant.LOCATION + "_" + Utils.getUid(getActivity()), switchLocation.isChecked())) {
            switchLocation.setChecked(true);
        }
        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchLocation.isChecked()) {
                    Utils.setPref(getActivity(), Constant.LOCATION + "_" + Utils.getUid(getActivity()), switchLocation.isChecked());
                } else {
                    Utils.delPref(getActivity(), Constant.LOCATION + "_" + Utils.getUid(getActivity()));
                }
            }
        });


        if (Utils.getPref(getActivity(), Constant.CLINICIAN + "_" + Utils.getUid(getActivity()), switchAlertClin.isChecked())) {
            switchAlertClin.setChecked(true);
        }
        switchAlertClin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchAlertClin.isChecked()) {
                    Utils.setPref(getActivity(), Constant.CLINICIAN + "_" + Utils.getUid(getActivity()), switchAlertClin.isChecked());
                } else {
                    Utils.delPref(getActivity(), Constant.CLINICIAN + "_" + Utils.getUid(getActivity()));
                }
            }
        });

        if (Utils.getPref(getActivity(), Constant.CONTACT + "_" + Utils.getUid(getActivity()), switchContact.isChecked())) {
            switchContact.setChecked(true);
        }
        switchContact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchContact.isChecked()) {
                    Utils.setPref(getActivity(), Constant.CONTACT + "_" + Utils.getUid(getActivity()), switchContact.isChecked());
                } else {
                    Utils.delPref(getActivity(), Constant.CONTACT + "_" + Utils.getUid(getActivity()));
                }
            }
        });

//        switchPushNotific.setChecked(Utils.getPref(getActivity(), Constant.NOTIFICATION, false));
//        Utils.setPref(getActivity(), Constant.NOTIFICATION + "_" + Utils.getUid(getActivity()), switchPushNotific.isChecked());
//
//        if (switchPushNotific.isChecked()) {
//
//        }

        setReminders();
    }

    private void printData() {
        Debug.e("printData", "" + new Gson().toJson(data));
        for (int i = 0; i < data.size(); i++) {
            Debug.e("Alarms ", data.get(i).lable + " " + new Date(data.get(i).timestamp).toString());
        }
    }

    private void editData(ReminderData reminderData) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).reqCode == reminderData.reqCode) {
                data.set(i, reminderData);
                mAdapter.addAll(data);
                Debug.e("isOnData", "" + new Gson().toJson(data));
                Utils.setPref(getActivity(), Constant.REMINDER + "_" + Utils.getUid(getActivity()), new Gson().toJson(data));
                setReminders();
                break;
            }
        }
    }

    private void deleteData(ReminderData reminderData) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).reqCode == reminderData.reqCode) {
                Utils.stopAllReminders(this, data);
                Debug.e("deletData", "" + new Gson().toJson(data.get(i)));
                data.remove(i);
//                if (data.size() > 0) {
////                    Debug.e("deletData", "" + new Gson().toJson(data.get(i)));
//                }
                Utils.setPref(getActivity(), Constant.REMINDER + "_" + Utils.getUid(getActivity()), new Gson().toJson(data));
                mAdapter.addAll(data);
                setReminders();
                break;
            }
        }

    }

    private void setReminders() {
        printData();

        if (data.size() > 0) {
            Utils.stopAllReminders(this, data);
            Utils.setLatestReminder(this, data);
        }

//        for (int i = 0; i < data.size(); i++) {
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(Calendar.MINUTE, 0);
//            calendar.set(Calendar.HOUR_OF_DAY, 0);
//            calendar.set(Calendar.SECOND, 0);
//            calendar.set(Calendar.MILLISECOND, 0);
//
//            calendar.set(Calendar.MINUTE, data.get(i).timeMin);
//
//            ReminderServiceFunction.AppointmentReminder reminder = new ReminderServiceFunction.AppointmentReminder();
////        reminder.time = calendar.getTimeInMillis();
//            reminder.time = calendar.getTimeInMillis();
//            reminder.reqCode = data.get(i).reqCode;
//            reminder.title = "" + data.get(i).lable;
//            reminder.message = "";
//
//            ReminderServiceFunction.scheduleAppointmentReminder(this, reminder, 0, false);
//        }
    }

    public static class ReminderData {
        public int reqCode;
        public String time;
        public String lable;
        public int timeMin;
        public long timestamp;
        public String sunday;
        public String monday;
        public String tuesday;
        public String wednesday;
        public String thursday;
        public String friday;
        public String saturday;
        public boolean isOn;
    }

    Dialog dialog;

    public void addReminderDialog() {

        dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_reminders);

//        Button btnSave = (Button) dialog.findViewById(R.id.btnSave);
        final EditText editTime = (EditText) dialog.findViewById(R.id.editeSelectTime);
        final TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        final TextView tvSave = (TextView) dialog.findViewById(R.id.tvSave);
        final EditText editLable = (EditText) dialog.findViewById(R.id.editLable);
        final CheckBox cbSunday = (CheckBox) dialog.findViewById(R.id.cbSunday);
        final CheckBox cbMonday = (CheckBox) dialog.findViewById(R.id.cbMonday);
        final CheckBox cbTuesday = (CheckBox) dialog.findViewById(R.id.cbTuesday);
        final CheckBox cbWednesday = (CheckBox) dialog.findViewById(R.id.cbWednesday);
        final CheckBox cbThuesday = (CheckBox) dialog.findViewById(R.id.cbThuesday);
        final CheckBox cbFriday = (CheckBox) dialog.findViewById(R.id.cbFriday);
        final CheckBox cbSaturday = (CheckBox) dialog.findViewById(R.id.cbSaturday);

        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker(editTime, null);
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTime.getText().toString().trim().length() <= 0 || editLable.getText().toString().trim().length() <= 0) {
                    showToast(getString(R.string.err_reminder), Toast.LENGTH_SHORT);
                } else {
                    ReminderData tData = new ReminderData();
                    tData.time = editTime.getText().toString();
                    tData.lable = editLable.getText().toString();
                    tData.timeMin = (int) editTime.getTag(R.id.editeSelectTime + 100);
                    tData.timestamp = (long) editTime.getTag(R.id.editeSelectTime + 101);
                    tData.reqCode = (int) new Date().getTime();

                    if (cbSunday.isChecked()) {
                        tData.sunday = "1";
                    } else {
                        tData.sunday = "0";
                    }

                    if (cbMonday.isChecked()) {
                        tData.monday = "1";
                    } else {
                        tData.monday = "0";
                    }
                    if (cbTuesday.isChecked()) {
                        tData.tuesday = "1";
                    } else {
                        tData.tuesday = "0";
                    }
                    if (cbWednesday.isChecked()) {
                        tData.wednesday = "1";
                    } else {
                        tData.wednesday = "0";
                    }
                    if (cbThuesday.isChecked()) {
                        tData.thursday = "1";
                    } else {
                        tData.thursday = "0";
                    }
                    if (cbFriday.isChecked()) {
                        tData.friday = "1";
                    } else {
                        tData.friday = "0";
                    }
                    if (cbSaturday.isChecked()) {
                        tData.saturday = "1";
                    } else {
                        tData.saturday = "0";
                    }
                    tData.isOn = true;
                    data.add(tData);

//                    Debug.e("okkkk", new Gson().toJson(data));
                    Utils.setPref(getActivity(), Constant.REMINDER + "_" + Utils.getUid(getActivity()), new Gson().toJson(data));
                    mAdapter.addAll(data);
                    setReminders();
                    dialog.dismiss();
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showEditDialog(final ReminderData tData) {

        dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_reminders);

//        Button btnSave = (Button) dialog.findViewById(btnSave);
        final TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        final TextView tvSave = (TextView) dialog.findViewById(R.id.tvSave);
        final EditText editTime = (EditText) dialog.findViewById(R.id.editeSelectTime);
        final EditText editLable = (EditText) dialog.findViewById(R.id.editLable);
        final CheckBox cbSunday = (CheckBox) dialog.findViewById(R.id.cbSunday);
        final CheckBox cbMonday = (CheckBox) dialog.findViewById(R.id.cbMonday);
        final CheckBox cbTuesday = (CheckBox) dialog.findViewById(R.id.cbTuesday);
        final CheckBox cbWednesday = (CheckBox) dialog.findViewById(R.id.cbWednesday);
        final CheckBox cbThuesday = (CheckBox) dialog.findViewById(R.id.cbThuesday);
        final CheckBox cbFriday = (CheckBox) dialog.findViewById(R.id.cbFriday);
        final CheckBox cbSaturday = (CheckBox) dialog.findViewById(R.id.cbSaturday);

        try {
            editTime.setText(tData.time);
            editLable.setText(tData.lable);
//            editTime.getTag(tData.timeMin);
//            editTime.getTag((int) tData.timestamp);

            if (tData.sunday.equalsIgnoreCase("1")) {
                cbSunday.setChecked(true);
            } else {
                cbSunday.setChecked(false);
            }
            if (tData.monday.equalsIgnoreCase("1")) {
                cbMonday.setChecked(true);
            } else {
                cbMonday.setChecked(false);
            }
            if (tData.tuesday.equalsIgnoreCase("1")) {
                cbTuesday.setChecked(true);
            } else {
                cbTuesday.setChecked(false);
            }
            if (tData.wednesday.equalsIgnoreCase("1")) {
                cbWednesday.setChecked(true);
            } else {
                cbWednesday.setChecked(false);
            }
            if (tData.thursday.equalsIgnoreCase("1")) {
                cbThuesday.setChecked(true);
            } else {
                cbThuesday.setChecked(false);
            }
            if (tData.friday.equalsIgnoreCase("1")) {
                cbFriday.setChecked(true);
            } else {
                cbFriday.setChecked(false);
            }
            if (tData.saturday.equalsIgnoreCase("1")) {
                cbSaturday.setChecked(true);
            } else {
                cbSaturday.setChecked(false);
            }

            Calendar calendar = Calendar.getInstance();
            editTime.setTag(R.id.editeSelectTime + 100, ((Calendar.HOUR * 60) + (Calendar.MINUTE)));
            editTime.setTag(R.id.editeSelectTime + 101, calendar.getTimeInMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }

        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker(editTime, tData);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTime.getText().toString().trim().length() <= 0 || editLable.getText().toString().trim().length() <= 0) {
                    showToast(getString(R.string.err_reminder), Toast.LENGTH_SHORT);
                } else {
                    tData.time = editTime.getText().toString();
                    tData.lable = editLable.getText().toString();
                    tData.timeMin = (int) editTime.getTag(R.id.editeSelectTime + 100);
                    tData.timestamp = (long) editTime.getTag(R.id.editeSelectTime + 101);

                    if (cbSunday.isChecked()) {
                        tData.sunday = "1";
                    } else {
                        tData.sunday = "0";
                    }

                    if (cbMonday.isChecked()) {
                        tData.monday = "1";
                    } else {
                        tData.monday = "0";
                    }
                    if (cbTuesday.isChecked()) {
                        tData.tuesday = "1";
                    } else {
                        tData.tuesday = "0";
                    }
                    if (cbWednesday.isChecked()) {
                        tData.wednesday = "1";
                    } else {
                        tData.wednesday = "0";
                    }
                    if (cbThuesday.isChecked()) {
                        tData.thursday = "1";
                    } else {
                        tData.thursday = "0";
                    }
                    if (cbFriday.isChecked()) {
                        tData.friday = "1";
                    } else {
                        tData.friday = "0";
                    }
                    if (cbSaturday.isChecked()) {
                        tData.saturday = "1";
                    } else {
                        tData.saturday = "0";
                    }
                    tData.isOn = true;
                    editData(tData);

//                Debug.e("okkkk", new Gson().toJson(tData));
//                    Utils.setPref(getActivity(), Constant.REMINDER, new Gson().toJson(data));
//                    init();

                    printData();
                    setReminders();

                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public void showPopupMenu(View v, final int id, final ReminderData obj) {
        PopupMenu popup = new PopupMenu(getActivity(), v, Gravity.RIGHT);
        popup.getMenu().add(0, 0, 0, "Edit").setIcon(getResources().getDrawable(R.drawable.ic_edit_red_26dp));
        popup.getMenu().add(1, 1, 1, "Delete").setIcon(getResources().getDrawable(R.drawable.ic_do_not_disturb_black_24dp));

        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == 0) {
                    showEditDialog(obj);
                }
                if (item.getItemId() == 1) {
                    reminderDeletePopup(obj);
                }
                return true;
            }
        });
        popup.show();
    }

    private void reminderDeletePopup(final ReminderData tData) {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
//                .title(R.string.logout_title)
                .content(R.string.msg_reminder)
                .positiveText(R.string.btn_ok)
                .negativeText(R.string.btn_cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        deleteData(tData);
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {

                    }
                });

        MaterialDialog dialog = builder.build();
        dialog.show();
    }

    public void showTimePicker(final EditText editText, ReminderData obj) {
        TimePickerDialog.Builder builder = new TimePickerDialog.Builder(true ? R.style.Material_App_Dialog_TimePicker_Light : R.style.Material_App_Dialog_TimePicker, 12, 60) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                TimePickerDialog dialog = (TimePickerDialog) fragment.getDialog();

//                editText.setText(dialog.getFormattedTime(SimpleDateFormat.getTimeInstance()));
                editText.setText(Utils.parseTime(dialog.getFormattedTime(SimpleDateFormat.getTimeInstance()), "hh:mm:ss a", "hh:mm a"));
//                Debug.e("Utils.parseTime : ", "" + Utils.parseTime(dialog.getFormattedTime(SimpleDateFormat.getTimeInstance()), "hh:mm:ss a", "hh:mm a"));

                editText.setTag(R.id.editeSelectTime + 100, ((dialog.getHour() * 60) + (dialog.getMinute())));
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, dialog.getHour());
                calendar.set(Calendar.MINUTE, dialog.getMinute());
                calendar.set(Calendar.SECOND, -1);

                editText.setTag(R.id.editeSelectTime + 101, calendar.getTimeInMillis());
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
//                Toast.makeText(mActivity, "Cancelled" , Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.positiveAction("OK").negativeAction("CANCEL");
        if (obj != null) {
            builder.hour(Integer.valueOf(obj.timeMin / 60));
            builder.minute(obj.timeMin % 60);
        }

//        if (editText.getTag(R.id.editeSelectTime + 100) != null) {
//
//            int timeMin = (int) editText.getTag(R.id.editeSelectTime + 100);
//
//            builder.hour(Integer.valueOf(timeMin / 60));
//            builder.minute(timeMin % 60);
//        }

        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);

    }
}
