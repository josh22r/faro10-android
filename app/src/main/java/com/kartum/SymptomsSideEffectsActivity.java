package com.kartum;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kartum.adapter.SymptomsSideEffectAdapter;
import com.kartum.objects.Spinner;
import com.kartum.utils.AsyncResponseHandlerOk;
import com.kartum.utils.CompletionHandler;
import com.kartum.utils.Debug;
import com.kartum.utils.HttpClient;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.URLs;
import com.kartum.utils.Utils;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;

public class SymptomsSideEffectsActivity extends BaseActivity {

    @BindView(R.id.rvListSymtompSideEffect)
    RecyclerView rvListSymtompSideEffect;
    RecyclerView.LayoutManager layoutManager;
    public SymptomsSideEffectAdapter mAdapter;

    @BindView(R.id.sbSleep)
    SeekBar sbSleep;
    @BindView(R.id.sbAppetite)
    SeekBar sbAppetite;
    @BindView(R.id.sbJoy)
    SeekBar sbJoy;

    @BindView(R.id.tvBackErrow)
    TextView tvBackErrow;
    @BindView(R.id.tvGetDate)
    TextView tvGetDate;
    @BindView(R.id.tvHelp)
    TextView tvHelp;

    @BindView(R.id.imgPrevDate)
    ImageView imgPrevDate;
    @BindView(R.id.imgNextDate)
    ImageView imgNextDate;
    @BindView(R.id.imgSelectDate)
    ImageView imgSelectDate;

    @BindView(R.id.editeEntWieght)
    EditText editeEntWieght;
    @BindView(R.id.btnSave)
    Button btnSave;
    Calendar calendar;

    private boolean hasSleepSelected = false;
    private boolean hasAppetiteSelected = false;
    private boolean hasJoySelected = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms_side_effects);
        ButterKnife.bind(this);

        initDrawer(true);
        init();
    }

    private void init() {

        layoutManager = new LinearLayoutManager(this);
        rvListSymtompSideEffect.setLayoutManager(layoutManager);
        mAdapter = new SymptomsSideEffectAdapter(this);
        rvListSymtompSideEffect.setAdapter(mAdapter);

        ArrayList<Spinner> data = new ArrayList<>();
        data.add(new Spinner("entry[nausea]", "Nausea"));
        data.add(new Spinner("entry[headache]", "Headache"));
        data.add(new Spinner("entry[restlessness]", "Restless"));

        data.add(new Spinner("entry[nightmare]", "Nightmare/Vivid Dream"));
        data.add(new Spinner("entry[suicide_thought]", "Suicide Thought"));
        data.add(new Spinner("entry[panic_attack]", "Panic Attack"));
//        data.add(new Spinner("entry[hospitalization]", "Hospitalization"));
//        data.add(new Spinner("entry[hallucination]", "Auditory Hallucination"));
//        data.add(new Spinner("6", "Visual Hallucination"));
        mAdapter.addAll(data);

        Utils.initSeekBarSidEffects(getActivity(), sbSleep, new CompletionHandler() {
            @Override
            public void onComplete() {
                hasSleepSelected = true;
            }
        });

        Utils.initSeekBarSidEffects(getActivity(), sbAppetite, new CompletionHandler() {
            @Override
            public void onComplete() {
                hasAppetiteSelected = true;
            }
        });

        Utils.initSeekBarSidEffects(getActivity(), sbJoy, new CompletionHandler() {
            @Override
            public void onComplete() {
                hasJoySelected = true;
            }
        });

        tvBackErrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SymptomsSideEffectsActivity.super.onBackPressed();
            }
        });

        tvHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helpDialog();
            }
        });

        mAdapter.setEventlistener(new SymptomsSideEffectAdapter.Eventlistener() {
            @Override
            public void onItemviewClick(int position) {
                mAdapter.changeSelection(position, true);
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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate())
                    setSideEffectData();
            }
        });
    }

    public boolean isSelected(String id) {
        ArrayList<Spinner> data = mAdapter.getSelectedAll();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).ID.equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean validate() {
        if (editeEntWieght.getText().toString().trim().length() <= 1) {
            showToast(getString(R.string.err_Weight), Toast.LENGTH_SHORT);
            return false;
        }

        if (editeEntWieght.getText().toString().trim().length() >= 5) {
            showToast(getString(R.string.err_Weights), Toast.LENGTH_SHORT);
            return false;
        }


        return true;
    }

    Dialog dialog;
    public void helpDialog() {
        dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_side_effects_help);

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

    public void setSideEffectData() {
        try {
            showDialog("");

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());

            if (isSelected(RequestParamsUtils.NAUSEA)) {
                body.addEncoded(RequestParamsUtils.NAUSEA, String.valueOf(1));
            } else {
                body.addEncoded(RequestParamsUtils.NAUSEA, "nill");
            }

            if (isSelected(RequestParamsUtils.HEADACHE)) {
                body.addEncoded(RequestParamsUtils.HEADACHE, String.valueOf(1));
            } else {
                body.addEncoded(RequestParamsUtils.HEADACHE, "nill");
            }

            if (isSelected(RequestParamsUtils.RESTLESSNESS)) {
                body.addEncoded(RequestParamsUtils.RESTLESSNESS, String.valueOf(1));
            } else {
                body.addEncoded(RequestParamsUtils.RESTLESSNESS, "nill");
            }

            if (isSelected(RequestParamsUtils.NIGHTMARE)) {
                body.addEncoded(RequestParamsUtils.NIGHTMARE, String.valueOf(1));
            } else {
                body.addEncoded(RequestParamsUtils.NIGHTMARE, "nill");
            }

            if (isSelected(RequestParamsUtils.SUICIDE_THOUGHT)) {
                body.addEncoded(RequestParamsUtils.SUICIDE_THOUGHT, String.valueOf(1));
            } else {
                body.addEncoded(RequestParamsUtils.SUICIDE_THOUGHT, "nill");
            }

            if (isSelected(RequestParamsUtils.PANIC_ATTACK)) {
                body.addEncoded(RequestParamsUtils.PANIC_ATTACK, String.valueOf(1));
            } else {
                body.addEncoded(RequestParamsUtils.PANIC_ATTACK, "nill");
            }

//            if (isSelected(RequestParamsUtils.HOSPITALIZATION)) {
//                body.addEncoded(RequestParamsUtils.HOSPITALIZATION, String.valueOf(1));
//            } else {
//                body.addEncoded(RequestParamsUtils.HOSPITALIZATION, "nill");
//            }
//
//            if (isSelected(RequestParamsUtils.HALLUCINATION)) {
//                body.addEncoded(RequestParamsUtils.HALLUCINATION, String.valueOf(1));
//            } else {
//                body.addEncoded(RequestParamsUtils.HALLUCINATION, "nill");
//            }

            if (hasSleepSelected)
                body.addEncoded(RequestParamsUtils.SLEEP, String.valueOf(sbSleep.getProgress()));

            if (hasAppetiteSelected)
                body.addEncoded(RequestParamsUtils.APPETITE, String.valueOf(sbAppetite.getProgress()));

            if (hasJoySelected)
                body.addEncoded(RequestParamsUtils.JOY, String.valueOf(sbJoy.getProgress()));

            body.addEncoded(RequestParamsUtils.SYMP_WEIGHT, editeEntWieght.getText().toString().trim());
            body.addEncoded(RequestParamsUtils.UPDATED_AT, tvGetDate.getText().toString().trim());

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
                Debug.e("", "setSideEffectData# " + response);
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

}
