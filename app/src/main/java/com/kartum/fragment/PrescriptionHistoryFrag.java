/*
 *    Copyright (C) 2015 Haruki Hasegawa
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.kartum.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kartum.PrescriptionsActivity;
import com.kartum.R;
import com.kartum.adapter.PrescripHistoryAdapter;
import com.kartum.objects.PriscriptionRes;
import com.kartum.objects.Spinner;
import com.kartum.utils.AsyncResponseHandlerOk;
import com.kartum.utils.Debug;
import com.kartum.utils.HttpClient;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.SpinnerCallback;
import com.kartum.utils.URLs;
import com.kartum.utils.Utils;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;

public class PrescriptionHistoryFrag extends BaseFragment {

    @BindView(R.id.rvListPrescriHist)
    RecyclerView rvListPrescriHist;
    RecyclerView.LayoutManager layoutManager;
    public PrescripHistoryAdapter mAdapter;
    @BindView(R.id.btnSave)
    Button btnSave;

    @BindView(R.id.imgSelectDate)
    ImageView imgSelectDate;
    @BindView(R.id.imgBackDate)
    ImageView imgBackDate;
    @BindView(R.id.imgNextDate)
    ImageView imgNextDate;

    @BindView(R.id.tvGetDate)
    TextView tvGetDate;

    TextView tvSelectDate;
    EditText editInputString;
    int type;
    Calendar calendar;

    public PrescriptionHistoryFrag() {
        super();
    }

    public static PrescriptionHistoryFrag newInstance(int type) {

        PrescriptionHistoryFrag pane = new PrescriptionHistoryFrag();
        Bundle args = new Bundle();
        args.putInt("type", type);
        pane.setArguments(args);
        return pane;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_prescription_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        type = getArguments().getInt("type", 0);

        layoutManager = new LinearLayoutManager(getActivity());
        rvListPrescriHist.setLayoutManager(layoutManager);

        if (type == PrescriptionsActivity.TYPE_ONE) {

            mAdapter = new PrescripHistoryAdapter(getActivity());
            rvListPrescriHist.setAdapter(mAdapter);
            getPriscriptionData();
        }

        mAdapter.setmEventlistener(new PrescripHistoryAdapter.Eventlistener() {

            @Override
            public void OnMenuclick(int position, View view) {

                PriscriptionRes.Prescription data = mAdapter.getObjectId(position);
                showPopupMenu(view, data.id);
//                showPopup(view);
//                showToast("" + data.id, Toast.LENGTH_LONG);
            }

            @Override
            public void OnTimeBoxclick(final int position) {

                ArrayList<Spinner> data = new ArrayList<>();
                data.add(new Spinner("0", "0"));
                data.add(new Spinner("1", "1"));
                showSpinner("Select times taken", data, false, new SpinnerCallback() {
                    @Override
                    public void onDone(ArrayList<Spinner> list) {
                        mAdapter.updateTime(position, list.get(0).ID);
                    }
                });
            }
        });

        imgSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(tvGetDate);

            }
        });

        calendar = Calendar.getInstance();
        tvGetDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "MM/dd/yy"));

        imgNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar.setTimeInMillis(calendar.getTimeInMillis());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                tvGetDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "MM/dd/yy"));
                getPriscriptionData();
            }
        });
        imgBackDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar.setTimeInMillis(calendar.getTimeInMillis());
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                tvGetDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "MM/dd/yy"));
                getPriscriptionData();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveParam();
                if (validate())
                    setUpdatePriscriptionData();

            }
        });
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 2 && resultCode == RESULT_OK) {
//            String getString = data.getStringExtra("msg");
//            if (getString.equalsIgnoreCase("1")) {
//                getPriscriptionData();
//                showToast("hello", Toast.LENGTH_LONG);
//            }
//        }
//    }


    public class setData {
        String prescription_id;
        String times_taken;
    }

    private boolean validate() {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).times_taken.length() > 0) {
                return true;
            }
        }
        showToast(getString(R.string.err_time_taken), Toast.LENGTH_SHORT);
        return false;
    }

    ArrayList<setData> data = new ArrayList<>();

    public void saveParam() {
        List<PriscriptionRes.Prescription> adpdata = mAdapter.getAll();
        data.clear();

        for (int i = 0; i < adpdata.size(); i++) {
            setData tData = new setData();
            tData.prescription_id = "" + adpdata.get(i).id;
            tData.times_taken = Utils.nullSafe(adpdata.get(i).time, "");
            if (!tData.times_taken.isEmpty()) {
                data.add(tData);
            }
        }
        Debug.e("data", new Gson().toJson(data));
    }

    public void showPopupMenu(View v, final int id) {
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(getContext(), v, Gravity.RIGHT);
        // popup.getMenu().add(groupid, itemid, orderid, "Title");
//        popup.getMenuInflater().inflate(R.menu.menu_item, popup.getMenu());
        popup.getMenu().add(0, 0, 0, "STOP TAKING").setIcon(getResources().getDrawable(R.drawable.ic_do_not_disturb_black_24dp));

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
//        prepareMenu(popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == 0) {
                    stopTakingDialog(id);
                }
                return true;
            }
        });
        popup.show();
    }


    Dialog dialog;

    public void stopTakingDialog(final int id) {
        dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_stop_taking_presc);

        tvSelectDate = (TextView) dialog.findViewById(R.id.tvSelectDate);
        editInputString = (EditText) dialog.findViewById(R.id.editInputString);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnStopTaking = (Button) dialog.findViewById(R.id.btnStopTaking);

        final Calendar calendar = Calendar.getInstance();
        tvSelectDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "MM/dd/yy"));
        tvSelectDate.setTag(calendar.getTimeInMillis());

        tvSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(tvSelectDate);
            }
        });

        btnStopTaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateValidate()) {
//                    setStopTakingEndDate(tvSelectDate.getText().toString(), id);
                    setStopTakingEndDate(id);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private boolean dateValidate() {
        if (tvSelectDate.getText().toString().equalsIgnoreCase("Select Date")) {
            showToast(getString(R.string.err_date), Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

//
//    private void priscriptionDelete(final int id) {
//
//        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
////                .title(R.string.logout_title)
//                .content(R.string.prisc_delete_msg)
//                .positiveText(R.string.btn_ok)
//                .negativeText(R.string.btn_cancel)
//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
//                        setEndDate(id);
////                        Intent i = new Intent(getActivity(), MainActivity.class);
////                        startActivity(i);
//                    }
//                }).onNegative(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
//
//                    }
//                });
//
//        MaterialDialog dialog = builder.build();
//        dialog.show();
//    }

    public void showDatePicker(final TextView textView) {
        DatePickerDialog.Builder builder = null;
        builder = new DatePickerDialog.Builder(R.style.Material_App_Dialog_DatePicker_Light) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
//                String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                textView.setText(Utils.parseTime(dialog.getDate(), "MM/dd/yy"));
                textView.setTag(dialog.getDate());
                getPriscriptionData();
                calendar.setTimeInMillis(dialog.getDate());
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.positiveAction("" + getString(R.string.btn_ok))
                .negativeAction("" + getString(R.string.btn_cancel));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -60);

        Calendar calendarNext = Calendar.getInstance();
        calendarNext.add(Calendar.YEAR, 60);

        builder.dateRange(calendar.getTimeInMillis(), calendarNext.getTimeInMillis());
//        if (editText.getText().length() > 0) {
//            builder.date(Utils.parseTime(editText.getText().toString(), DATE_FORMAT).getTime());
//        }

        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getFragmentManager(), null);
    }

    public void getPriscriptionData() {
        try {
//            showDialog("");

            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_PRESCRIPTION());
            Call call = HttpClient.newRequestGet(getActivity(), body.build().toString());
            call.enqueue(new GetVersionData(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersionData extends AsyncResponseHandlerOk {

        public GetVersionData(Activity context) {
            super(context);
        }

        @Override
        public void onStart() {
//            super.onStart();
        }

        @Override
        public void onFinish() {
//            super.onFinish();
            try {
//                dismissDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSuccess(String response) {

            try {
                Debug.e("", "getPriscriptionData# " + response);
                if (response != null && response.length() > 0) {

                    final PriscriptionRes res = new Gson().fromJson(response, new TypeToken<PriscriptionRes>() {
                    }.getType());

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            dismissDialog();
                            mAdapter.clear();
                            for (int i = 0; i < res.prescriptions.size(); i++) {

                                // Edited By Bhavesh
//                                if (Utils.parseTimeUTCtoDefault(res.prescriptions.get(i).started, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "MM/dd/yy").equalsIgnoreCase(tvGetDate.getText().toString())) {
                                mAdapter.add(res.prescriptions.get(i));
//                                }
                            }
//                            mAdapter.addAll(res.prescriptions);
                        }
                    });

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
            Debug.e("", "onFailure# " + content);
//            dismissDialog();
        }
    }

    public void setUpdatePriscriptionData() {
        try {
            showDialog("");

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
            for (int i = 0; i < data.size(); i++) {
                body.addEncoded("entry[entries_prescriptions_attributes][" + i + "][prescription_id]", data.get(i).prescription_id);
                body.addEncoded("entry[entries_prescriptions_attributes][" + i + "][times_taken]", data.get(i).times_taken);
//                body.addEncoded("entry[entries_prescriptions_attributes][" + i + "][day_taken]", tvGetDate.getText().toString().trim());
//                body.addEncoded("entry[entries_prescriptions_attributes][" + i + "][day_taken]", Utils.parseTimeUTCtoDefault(tvGetDate.getText().toString(), "MM/dd/yy", "yyyy-MM-dd"));
                body.addEncoded("entry[entries_prescriptions_attributes][" + i + "][day_taken]", Utils.parseTime(tvGetDate.getText().toString(), "MM/dd/yy", "yyyy-MM-dd"));
            }
//            body.addEncoded(RequestParamsUtils.UPDATED_AT, tvGetDate.getText().toString().trim());

            for (int i = 0; i < body.build().size(); i++) {
                Debug.e("setUpdatePriscriptionData", "" + body.build().name(i) + " = " + body.build().value(i));
            }

            Call call = HttpClient.newRequestPost(getActivity(), body.build(), URLs.ADD_ENTRY());
            call.enqueue(new GetVersionDataHandler(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersionDataHandler extends AsyncResponseHandlerOk {

        public GetVersionDataHandler(Activity context) {
            super(context);
        }

        @Override
        public void onStart() {
//            super.onStart();
        }

        @Override
        public void onFinish() {
//            super.onFinish();
            try {
                dismissDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSuccess(String response) {

            try {
                Debug.e("", "setUpdatePriscriptionData# " + response);
                if (response != null && response.length() > 0) {

                    showToast("success", Toast.LENGTH_LONG);
//                    PriscriptionRes res = new Gson().fromJson(response, new TypeToken<PriscriptionRes>() {
//                    }.getType());
                    getPriscriptionData();
//                    getActivity().finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
            dismissDialog();
        }
    }

    public void setStopTakingEndDate(int id) {
        try {
            showDialog("");

            Debug.e("setStopTakingEndDate", "" + Utils.parseTime(Utils.parseTimeUTCtoDefault(1 * Long.valueOf("" + tvSelectDate.getTag())).getTime(), "yyyy-MM-dd'T'hh:mm:ss'Z'"));

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
//            body.addEncoded(RequestParamsUtils.ENDED, String.valueOf(Utils.parseTime(date, "yyyy-MM-dd'T'hh:mm:ss'Z'")));  //2016-02-05T15:05:00Z
            body.addEncoded(RequestParamsUtils.REASON, editInputString.getText().toString().trim());
            body.addEncoded(RequestParamsUtils.ENDED, Utils.parseTime(Utils.parseTimeUTCtoDefault(1 * Long.valueOf("" + tvSelectDate.getTag())).getTime(), "yyyy-MM-dd'T'hh:mm:ss'Z'"));

            for (int i = 0; i < body.build().size(); i++) {
                Debug.e("setEndDate", "" + body.build().name(i) + " = " + body.build().value(i));
            }

            Call call = HttpClient.newRequestPut(getActivity(), body.build(), URLs.PUT_PRESCRIPTION() + id);
            call.enqueue(new GetVersionDataHandl(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersionDataHandl extends AsyncResponseHandlerOk {

        public GetVersionDataHandl(Activity context) {
            super(context);
        }

        @Override
        public void onStart() {
//            super.onStart();
        }

        @Override
        public void onFinish() {
//            super.onFinish();
            try {
                dismissDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSuccess(final String response) {

            try {
                Debug.e("", "setEndDate# " + response);
                getPriscriptionData();
                dialog.dismiss();
//                showToast("success", Toast.LENGTH_LONG);
                if (response != null && response.length() > 0) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissDialog();
                            Debug.e("ok", response);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
            Debug.e("", "onFailure# " + content);
            dismissDialog();
        }
    }

}

