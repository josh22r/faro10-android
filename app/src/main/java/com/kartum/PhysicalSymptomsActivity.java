package com.kartum;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kartum.adapter.SymptomsAdapter;
import com.kartum.adapter.SymptomsTrackListAdapter;
import com.kartum.objects.SymptomErrorRes;
import com.kartum.objects.SymptomTrackRes;
import com.kartum.objects.SymptomsListRes;
import com.kartum.utils.AsyncResponseHandlerOk;
import com.kartum.utils.Debug;
import com.kartum.utils.HttpClient;
import com.kartum.utils.OnStartDragListener;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.URLs;
import com.kartum.utils.Utils;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;

public class PhysicalSymptomsActivity extends BaseActivity implements OnStartDragListener {

    @BindView(R.id.rvListPhysicalSymtomp)
    RecyclerView rvListPhysicalSymtomp;
    RecyclerView rvSymptomTrackPopup;
    RecyclerView.LayoutManager layoutManager;
    public SymptomsAdapter mAdapter;
    public SymptomsTrackListAdapter symAdapter;

//    public PhysicalSymptomDragNDropAdapter symAdapter;
    ItemTouchHelper mItemTouchHelper;

    @BindView(R.id.tvEditSymptomList)
    TextView tvEditSymptomList;
    @BindView(R.id.tvGetDate)
    TextView tvGetDate;
    @BindView(R.id.tvBackErrow)
    TextView tvBackErrow;
    @BindView(R.id.tvHelp)
    TextView tvHelp;

    @BindView(R.id.imgPrevDate)
    ImageView imgPrevDate;
    @BindView(R.id.imgNextDate)
    ImageView imgNextDate;
    @BindView(R.id.imgSelectDate)
    ImageView imgSelectDate;
    @BindView(R.id.btnSave)
    Button btnSave;
    Calendar calendar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_symptoms);
        ButterKnife.bind(this);

        initDrawer(true);
        init();
    }

    private void init() {
        //setTitleText("Faro10");

        layoutManager = new LinearLayoutManager(this);
        rvListPhysicalSymtomp.setLayoutManager(layoutManager);
        symAdapter = new SymptomsTrackListAdapter(this);
        rvListPhysicalSymtomp.setAdapter(symAdapter);

        symAdapter.setEventlistener(new SymptomsTrackListAdapter.Eventlistener() {

            @Override
            public void onItemviewClick(int position) {
                symAdapter.changeSelection(position, true);
            }
        });

        // Physical Symptom Drag N Drop Adapter

//        List<SymptomTrackRes.Symptom> list = new ArrayList<>();
//        layoutManager = new LinearLayoutManager(this);
//        rvListPhysicalSymtomp.setLayoutManager(layoutManager);
//        symAdapter = new PhysicalSymptomDragNDropAdapter(this, list, this);
//        rvListPhysicalSymtomp.setAdapter(symAdapter);
//
//        rvListPhysicalSymtomp.setHasFixedSize(true);
//        ItemTouchHelper.Callback callback =
//                new EditItemTouchHelperCallback(symAdapter);
//        mItemTouchHelper = new ItemTouchHelper(callback);
//        mItemTouchHelper.attachToRecyclerView(rvListPhysicalSymtomp);

//        symAdapter.setEventlistener(new PhysicalSymptomDragNDropAdapter.Eventlistener() {

//            @Override
//            public void onItemviewClick(int position) {
//                symAdapter.changeSelection(position, true);
//            }
//        });

        tvBackErrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhysicalSymptomsActivity.super.onBackPressed();
            }
        });

        imgSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(tvGetDate);
            }
        });

        tvHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpDialog();
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
            }
        });
        imgPrevDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar.setTimeInMillis(calendar.getTimeInMillis());
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                tvGetDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "MM/dd/yy"));
            }
        });

        tvEditSymptomList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveParam();
//                if (validate() && validate2())
                if (Symptomsvalidate())
                    setPhysicalSymptomData();
            }
        });

        getSymptomTrackedData();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

//    private boolean validate() {
//        if (tvSelfHarm.getText().toString().equalsIgnoreCase("null")) {
//            showToast(getString(R.string.err_symptom), Toast.LENGTH_SHORT);
//            return false;
//        }
//        return true;
//    }

    public class setData {
        int id;
    }

    private boolean Symptomsvalidate() {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).id > 0) {
                return true;
            }
        }
        showToast(getString(R.string.err_select), Toast.LENGTH_SHORT);
        return false;
    }

    ArrayList<setData> data = new ArrayList<>();

    public void saveParam() {
        List<SymptomTrackRes.Symptom> adpdata = symAdapter.getSelectedAll();
        data.clear();
        for (int i = 0; i < adpdata.size(); i++) {
            setData tData = new setData();
            tData.id = adpdata.get(i).id;
            data.add(tData);
        }
//        Debug.e("mydata", new Gson().toJson(data));
    }

    public void symptomUpdateValidation() {
        List<SymptomsListRes.Symptom> adpdata = mAdapter.getSelectedAll();
        data.clear();
        for (int i = 0; i < adpdata.size(); i++) {
            setData tData = new setData();
            tData.id = adpdata.get(i).id;
            data.add(tData);
        }
//        Debug.e("mydata", new Gson().toJson(data));
    }

//    ArrayList<setData> data2 = new ArrayList<>();
//    public boolean isSelected(int id) {
//        List<SymptomsListRes.Symptom> data = mAdapter.getSelectedAll();
//        for (int i = 0; i < data.size(); i++) {
//            setData tData = new setData();
//            tData.id = data.get(i).id;
////            if (data.get(i).isSelected == true) {
////                return true;
////            }
//        }
//        return false;
//    }

    public void helpDialog() {
        dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_physical_symptoms_help);

        TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showDatePicker(final TextView textView) {
        DatePickerDialog.Builder builder = null;
        builder = new DatePickerDialog.Builder(R.style.Material_App_Dialog_DatePicker_Light) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
//                String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                textView.setText(Utils.parseTime(dialog.getDate(), "MM/dd/yy"));
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
        fragment.show(getSupportFragmentManager(), null);
    }

    Dialog dialog;

    public void showEditDialog() {

        dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_symptom_tracked);

        rvSymptomTrackPopup = (RecyclerView) dialog.findViewById(R.id.rvSymptomTrackPopup);
        TextView tvCustom = (TextView) dialog.findViewById(R.id.tvCustom);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tvOk);

        layoutManager = new LinearLayoutManager(this);
        rvSymptomTrackPopup.setLayoutManager(layoutManager);
        mAdapter = new SymptomsAdapter(this);
        rvSymptomTrackPopup.setAdapter(mAdapter);

//        ArrayList<String> iDs = new ArrayList<>();
//        try {
//            iDs = Utils.asList(tvEditSymptomList.getTag().toString());
//        } catch (Exception e) {
//        }

//        mAdapter.setEventlistener(new SymptomsAdapter.Eventlistener() {
//
//            @Override
//            public void onItemviewClick(int position) {
//                SymptomsListRes.Symptom data = mAdapter.getItem(position);
////                showToast(data.name,Toast.LENGTH_LONG);
//                mAdapter.changeSelection(position, true);
//            }
//        });

        getSymptoms();

        mAdapter.setEventlistener(new SymptomsAdapter.Eventlistener() {
            @Override
            public void onItemviewClick(int position) {
                SymptomsListRes.Symptom data = mAdapter.getItem(position);
                mAdapter.changeSelection(position, true);
            }

        });

        tvCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symptomUpdateValidation();
                if (Symptomsvalidate())
                    setSymptomUpdateData();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    Dialog dialog1;

    public void showCustomDialog() {

        dialog1 = new Dialog(getActivity());
        dialog1.setCanceledOnTouchOutside(true);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.popup_add_custom);

        final EditText editName = (EditText) dialog1.findViewById(R.id.editName);
        TextView tvCancel = (TextView) dialog1.findViewById(R.id.tvCancel);
        TextView tvSave = (TextView) dialog1.findViewById(R.id.tvSave);

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editName.getText().toString().trim().length() <= 0) {
                    showToast(getString(R.string.err_name), Toast.LENGTH_SHORT);
                } else {
                    setCustomData(editName.getText().toString());
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }

    public void setPhysicalSymptomData() {
        try {
            showDialog("");

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
//            body.addEncoded(RequestParamsUtils.UPDATED_AT, tvGetDate.getText().toString().trim());

            for (int i = 0; i < data.size(); i++) {
                body.addEncoded("entry[entries_symptoms_attributes][" + i + "][symptom_id]", String.valueOf(data.get(i).id));
            }

            for (int i = 0; i < body.build().size(); i++) {
                Debug.e("setPhysicalSymptomData", "" + body.build().name(i) + " = " + body.build().value(i));
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
                Debug.e("", "setPhysicalSymptomData# " + response);
                if (response != null && response.length() > 0) {
                    showToast("success", Toast.LENGTH_LONG);
                    finish();
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

    public void setSymptomUpdateData() {
        try {
            showDialog("");

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());

            for (int i = 0; i < data.size(); i++) {
                Debug.e("", "SYMPTOM_IDS# " + data.get(i).id);
                body.addEncoded(RequestParamsUtils.SYMPTOM_IDS, String.valueOf(data.get(i).id));
            }

            for (int i = 0; i < body.build().size(); i++) {
                Debug.e("setSymptomUpdateData ", "" + body.build().name(i) + " = " + body.build().value(i));
            }
            Call call = HttpClient.newRequestPut(getActivity(), body.build(), URLs.ADD_SYMPTOMS_UPDATE());
            call.enqueue(new GetVersionDataHandle(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersionDataHandle extends AsyncResponseHandlerOk {

        public GetVersionDataHandle(Activity context) {
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
                Debug.e("", "setSymptomUpdateData# " + response);

                showToast("Success", Toast.LENGTH_LONG);
                getSymptomTrackedData();
                dialog.dismiss();

                if (response != null && response.length() > 0) {
                    SymptomErrorRes res = new Gson().fromJson(response, new TypeToken<SymptomErrorRes>() {
                    }.getType());

                    if (res.errors != null && !res.success) {
                        showToast("Email " + res.errors.symptom.get(0), Toast.LENGTH_LONG);
                    } else {
                        showToast("Success", Toast.LENGTH_LONG);
                        getSymptomTrackedData();
                        dialog.dismiss();
                    }

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

    public void setCustomData(String name) {
        try {
            showDialog("");

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
            body.addEncoded(RequestParamsUtils.SYMPTOM_NAME, name);

            Call call = HttpClient.newRequestPost(getActivity(), body.build(), URLs.PUT_ADD_CUSTOM_SYMPTOM());
            call.enqueue(new GetVersiondata1(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersiondata1 extends AsyncResponseHandlerOk {

        public GetVersiondata1(Activity context) {
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
                Debug.e("", "setCustomData# " + response);
                if (response != null && response.length() > 0) {

//                    SymptomErrorRes res = new Gson().fromJson(response, new TypeToken<SymptomErrorRes>() {
//                    }.getType());

                    showToast("success", Toast.LENGTH_LONG);
                    getSymptoms();
                    dialog1.dismiss();

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


    public void getSymptoms() {
        try {
            showDialog("");

            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_SYMPTOMS());
            Call call = HttpClient.newRequestGet(getActivity(), body.build().toString());
            call.enqueue(new GetVersionDataHand(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersionDataHand extends AsyncResponseHandlerOk {

        public GetVersionDataHand(Activity context) {
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
                Debug.e("", "getSymptoms# " + response);
                if (response != null && response.length() > 0) {

                    final SymptomsListRes res = new Gson().fromJson(response, new TypeToken<SymptomsListRes>() {
                    }.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < res.symptoms.size(); i++) {
                                if (selectedId.contains("" + res.symptoms.get(i).id)) {
                                    res.symptoms.get(i).isSelected = true;
                                } else {
                                    res.symptoms.get(i).isSelected = false;
                                }
                            }
                            mAdapter.addAll(res.symptoms);
                        }
                    });

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
            Debug.e("", "getSymptoms # " + content);
            dismissDialog();
        }
    }

    public void getSymptomTrackedData() {
        try {
            showDialog("");

            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_SYMPTOMS_TRACKED());
            Call call = HttpClient.newRequestGet(getActivity(), body.build().toString());
            call.enqueue(new GetVersionData(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    HashSet<String> selectedId = new HashSet<>();

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
                dismissDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSuccess(String response) {

            try {
                Debug.e("", "getSymptomTrackedData# " + response);
                if (response != null && response.length() > 0) {

                    final SymptomTrackRes symptomTrackRes = new Gson().fromJson(response, new TypeToken<SymptomTrackRes>() {
                    }.getType());

                    selectedId.clear();
                    for (SymptomTrackRes.Symptom select : symptomTrackRes.symptoms) {
                        selectedId.add("" + select.id);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

//                            symAdapter.clear();
//                            for (int i = 0; i < symptomTrackRes.symptoms.size(); i++) {
//
//                                if (Utils.parseTimeUTCtoDefault(symptomTrackRes.symptoms.get(i).started, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "MM/dd/yy").equalsIgnoreCase(tvGetDate.getText().toString())) {
//                                    symAdapter.add(symptomTrackRes.symptoms.get(i));
//                                }
//                            }

                            symAdapter.addAll(symptomTrackRes.symptoms);

                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
            Debug.e("", "getSymptomTrackedData# " + content);
            dismissDialog();
        }
    }
}
