package com.kartum;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kartum.adapter.ChooseUserPopupAdapter;
import com.kartum.utils.AsyncResponseHandlerOk;
import com.kartum.utils.CompletionHandler;
import com.kartum.utils.Constant;
import com.kartum.utils.Debug;
import com.kartum.utils.HttpClient;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.URLs;
import com.kartum.utils.Utils;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;

public class MakeObserveActivity extends BaseActivity {

    RecyclerView rvListChooseUser;
    RecyclerView.LayoutManager layoutManager;
    public ChooseUserPopupAdapter mAdapter;

    @BindView(R.id.tvBackErrow)
    TextView tvBackErrow;
    @BindView(R.id.tvChooseUser)
    TextView tvChooseUser;
    @BindView(R.id.tvGetDate)
    TextView tvGetDate;

    @BindView(R.id.editJournalEntry)
    EditText editJournalEntry;

    @BindView(R.id.btnSave)
    Button btnSave;

    @BindView(R.id.sbMood)
    SeekBar sbMood;
    @BindView(R.id.sbSocialInteraction)
    SeekBar sbSocialInteraction;
    @BindView(R.id.sbHopelesssness)
    SeekBar sbHopelesssness;
    @BindView(R.id.sbHyperactive)
    SeekBar sbHyperactive;
    @BindView(R.id.sbEnergy)
    SeekBar sbEnergy;
    @BindView(R.id.sbActivity)
    SeekBar sbActivity;
    @BindView(R.id.sbWorkSchool)
    SeekBar sbWorkSchool;
    @BindView(R.id.sbFamilyLife)
    SeekBar sbFamilyLife;
    @BindView(R.id.sbDagerous)
    SeekBar sbDagerous;
    @BindView(R.id.sbSubstanceAbuse)
    SeekBar sbSubstanceAbuse;
    @BindView(R.id.sbDelusional)
    SeekBar sbDelusional;
    @BindView(R.id.sbHallucinations)
    SeekBar sbHallucinations;
    Calendar calendar;

    JSONObject obj;
    String userId = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observe);
        ButterKnife.bind(this);

        initIntentParams();
        initDrawer(true);
        init();
    }

    private void initIntentParams() {

        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().containsKey("data")) {
                    obj = new Gson().fromJson(getIntent().getStringExtra("data"), new TypeToken<JSONObject>() {
                    }.getType());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        //setTitleText("Faro10");

//        Utils.initSeekBar(getActivity(), sbMood);
        Utils.initSeekBar(getActivity(), sbSocialInteraction, new CompletionHandler() {
            @Override
            public void onComplete() {
                // do nothing
            }
        });
//        Utils.initSeekBar(getActivity(), sbHopelesssness);
//        Utils.initSeekBar(getActivity(), sbHyperactive);
//        Utils.initSeekBar(getActivity(), sbEnergy);
//        Utils.initSeekBar(getActivity(), sbActivity);
        Utils.initSeekBar(getActivity(), sbWorkSchool, new CompletionHandler() {
            @Override
            public void onComplete() {
                //do nothing
            }
        });
        Utils.initSeekBar(getActivity(), sbFamilyLife, new CompletionHandler() {
            @Override
            public void onComplete() {
                // do nothing
            }
        });
//        Utils.initSeekBar(getActivity(), sbDagerous);
//        Utils.initSeekBar(getActivity(), sbSubstanceAbuse);
//        Utils.initSeekBar(getActivity(), sbDelusional);
//        Utils.initSeekBar(getActivity(), sbHallucinations);

        Utils.initSeekBarSidEffects(getActivity(), sbMood);
        Utils.initSeekBarSidEffects(getActivity(), sbHopelesssness);
        Utils.initSeekBarSidEffects(getActivity(), sbHyperactive);
        Utils.initSeekBarSidEffects(getActivity(), sbActivity);
        Utils.initSeekBarSidEffects(getActivity(), sbDagerous);
        Utils.initSeekBarSidEffects(getActivity(), sbSubstanceAbuse);
        Utils.initSeekBarSidEffects(getActivity(), sbDelusional);
        Utils.initSeekBarSidEffects(getActivity(), sbHallucinations);
        Utils.initSeekBarSidEffects(getActivity(), sbEnergy);

        tvBackErrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MakeObserveActivity.super.onBackPressed();
            }
        });
        calendar = Calendar.getInstance();
        tvGetDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "MM/dd/yy"));
        tvGetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDatePicker(tvGetDate);
            }
        });

        try {
            tvChooseUser.setText(obj.getString("name"));
            userId = obj.getString("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvChooseUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectUserDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate())
                    setObserveData();
            }
        });
    }

    private boolean validate() {

        if (tvGetDate.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_select_date), Toast.LENGTH_SHORT);
            return false;
        }
        if (tvChooseUser.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_choose_user), Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    Dialog dialog;

    public void selectUserDialog() {

        dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_choose_user);

        rvListChooseUser = (RecyclerView) dialog.findViewById(R.id.rvListChooseUser);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tvOk);

        layoutManager = new LinearLayoutManager(this);
        rvListChooseUser.setLayoutManager(layoutManager);
        mAdapter = new ChooseUserPopupAdapter(this);
        rvListChooseUser.setAdapter(mAdapter);

        String response = Utils.getPref(getActivity(), Constant.USERDATA, "");

        JSONObject issueObj = null;
        try {
            issueObj = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator iterator = issueObj.keys();
        JSONArray array = new JSONArray();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            JSONObject object = null;
            try {
                object = issueObj.getJSONObject(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                object.put("name", key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(object);

        }
        for (int i = 0; i < array.length(); i++) {
            try {
                mAdapter.add(array.getJSONObject(i));
                Debug.e("user Data", "" + array.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mAdapter.setEventlistener(new ChooseUserPopupAdapter.Eventlistener() {
            @Override
            public void onItemviewClick(int position) {
                JSONObject obj = mAdapter.getItem(position);
                try {
//                    Debug.e("user id", "" + obj.getString("id"));
//                    Debug.e("user name", "" + obj.getString("name"));
                    userId = obj.getString("id");
                    tvChooseUser.setText("" + obj.getString("name"));
                    dialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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

    public void showDatePicker(final TextView textView) {
        DatePickerDialog.Builder builder = null;
        builder = new DatePickerDialog.Builder(R.style.Material_App_Dialog_DatePicker_Light) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
//                String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                textView.setText(Utils.parseTime(dialog.getDate(), "MM/dd/yy"));
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

    public void setObserveData() {
        try {
            showDialog("");

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
            body.addEncoded(RequestParamsUtils.OBSERVATION_ID, userId);
            Debug.e("user userId", "" + userId);
//            body.addEncoded(RequestParamsUtils.OBSERVATION_ID, obj.getString("id"));
            body.addEncoded(RequestParamsUtils.OBSERVED_AT, Utils.parseTimeUTCtoDefault(tvGetDate.getText().toString(), "MM/dd/yy", "YYYYMMdd"));
            body.addEncoded(RequestParamsUtils.OBS_FEELING, String.valueOf(sbMood.getProgress()));
            body.addEncoded(RequestParamsUtils.OBS_SOCIAL_INTERACTION, String.valueOf(sbSocialInteraction.getProgress()));
            body.addEncoded(RequestParamsUtils.OBS_HYPERACTIVE, String.valueOf(sbHyperactive.getProgress()));
            body.addEncoded(RequestParamsUtils.OBS_ENERGY, String.valueOf(sbEnergy.getProgress()));
            body.addEncoded(RequestParamsUtils.OBS_ACTIVITY, String.valueOf(sbActivity.getProgress()));
            body.addEncoded(RequestParamsUtils.OBS_WORK_LIFE, String.valueOf(sbWorkSchool.getProgress()));
            body.addEncoded(RequestParamsUtils.OBS_FAMILY_LIFE, String.valueOf(sbFamilyLife.getProgress()));
            body.addEncoded(RequestParamsUtils.OBS_DANGEROUS_BEHAVIOR, String.valueOf(sbDagerous.getProgress()));
            body.addEncoded(RequestParamsUtils.OBS_SUBSTANCE_ABUSE, String.valueOf(sbSubstanceAbuse.getProgress()));
            body.addEncoded(RequestParamsUtils.OBS_DELUSIONAL, String.valueOf(sbDelusional.getProgress()));
            body.addEncoded(RequestParamsUtils.OBS_HALLUCINATION, String.valueOf(sbHallucinations.getProgress()));
            body.addEncoded(RequestParamsUtils.OBS_JOURNAL, editJournalEntry.getText().toString().trim());

            for (int i = 0; i < body.build().size(); i++) {
                Debug.e("setObserveData", "" + body.build().name(i) + " = " + body.build().value(i));
            }

            Debug.e("setMoodEntryData", "" + body);
            Call call = HttpClient.newRequestPost(getActivity(), body.build(), URLs.SAVE_OBSERVATION());
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
                Debug.e("", "setObserveData# " + response);
                if (response != null && response.length() > 0) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("success", Toast.LENGTH_LONG);
                            Intent intent = new Intent(getApplicationContext(), ObservingPeopleActivity.class);
                            startActivity(intent);
                        }
                    });

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

    public void getUserData() {
        try {
//            showEditDialog("");

//            RequestParams params = RequestParamsUtils.newRequestParams(getActivity());
//
//            Debug.e("getUserData-->>", "" + params.toString());
//            AsyncHttpClient client = AsyncHttpRequest.newRequest(getActivity());
//            client.get(URLs.GET_OBSERVEES(), params, new GetVersionDataHandle(getActivity()));

            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_OBSERVEES());
            Call call = HttpClient.newRequestGet(getActivity(), body.build().toString());
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
//                dismissDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSuccess(String response) {

            try {
                Debug.e("", "getUserData# " + response);
                if (response != null && response.length() > 0) {

                    JSONObject issueObj = new JSONObject(response);
                    Iterator iterator = issueObj.keys();
                    JSONArray array = new JSONArray();

                    while (iterator.hasNext()) {
                        String key = (String) iterator.next();
                        JSONObject object = issueObj.getJSONObject(key);
                        object.put("name", key);
                        array.put(object);
                        //  get id from  issue
//                        String _pubKey = issue.optString("id");
                    }
                    for (int i = 0; i < array.length(); i++) {
//                        mAdapter.add(array.getJSONObject(i));
                    }

                    Debug.e("", "name -->> " + array);
//                    ObservUserRes res = new Gson().fromJson(response, new TypeToken<ObservUserRes>() {
//                    }.getType());
//
//                    if (res != null) {
//                        mAdapter.addAll(res.observationsOfOthers);
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
//            dismissDialog();
        }
    }
}
