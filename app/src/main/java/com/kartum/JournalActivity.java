package com.kartum;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kartum.adapter.JournalAttendedPopupAdapter;
import com.kartum.adapter.JournalEntryListAdapter;
import com.kartum.objects.JournalEntryRes;
import com.kartum.objects.Spinner;
import com.kartum.utils.AsyncResponseHandlerOk;
import com.kartum.utils.Debug;
import com.kartum.utils.HttpClient;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.URLs;
import com.kartum.utils.Utils;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.recruit_mp.android.lightcalendarview.LightCalendarView;
import jp.co.recruit_mp.android.lightcalendarview.MonthView;
import jp.co.recruit_mp.android.lightcalendarview.accent.Accent;
import jp.co.recruit_mp.android.lightcalendarview.accent.DotAccent;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;

public class JournalActivity extends BaseActivity {


    RecyclerView rvListAttendedPopup, mRvListJournalEntery;
    RecyclerView.LayoutManager layoutManager;
    public JournalAttendedPopupAdapter mAdapter;
    public JournalEntryListAdapter mJournalEntryListAdapter;

    @BindView(R.id.editSearch)
    EditText editSearch;
    @BindView(R.id.editMinutes)
    EditText editMinutes;

    @BindView(R.id.tvBackErrow)
    TextView tvBackErrow;
    @BindView(R.id.tvAttended)
    TextView tvAttended;
    @BindView(R.id.tvGetDate)
    TextView tvGetDate;
    @BindView(R.id.editJournalEntry)
    TextView editJournalEntry;

    @BindView(R.id.imgPrevDate)
    ImageView imgPrevDate;
    @BindView(R.id.imgNextDate)
    ImageView imgNextDate;
    @BindView(R.id.imgSelectDate)
    ImageView imgSelectDate;

    @BindView(R.id.btnSave)
    Button btnSave;
    Calendar calendar;
    String selectedId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        ButterKnife.bind(this);

        initDrawer(true);
        init();
    }

    private void init() {
        //setTitleText("Faro10");

        tvBackErrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JournalActivity.super.onBackPressed();
            }
        });

        imgSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(tvGetDate);
//                showCalendar(new Date());
            }
        });
        imgPrevDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });
        imgNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

//        editEmployee.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EmployeeRes data = Utils.getEmployees(getActivity());
//                ArrayList<Spinner> spinnerData = new ArrayList<Spinner>();
//                ArrayList<String> iDs = new ArrayList<>();
//                try {
//                    iDs = Utils.asList(editEmployee.getTag().toString());
//                } catch (Exception e) {
//                }
//                if (data != null) {
//                    for (EmployeeRes.Result a : data.result) {
//                        spinnerData.add(new Spinner("" + a.employeeID, a.badgeNumber).setSelected(iDs.contains("" + a.employeeID)));
//                    }
//                    showSpinnerSel("Select Employees", editEmployee, spinnerData, false);
//                }
//            }
//        });


        tvAttended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        imgSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showDatePicker(tvGetDate);
                performSearch("");
            }
        });

        calendar = Calendar.getInstance();
        tvGetDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "MM/dd/yy"));
        tvGetDate.setTag(calendar.getTimeInMillis());

        imgNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar.setTimeInMillis(calendar.getTimeInMillis());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                tvGetDate.setTag(calendar.getTimeInMillis());
                tvGetDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "MM/dd/yy"));
            }
        });
        imgPrevDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar.setTimeInMillis(calendar.getTimeInMillis());
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                tvGetDate.setTag(calendar.getTimeInMillis());
                tvGetDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "MM/dd/yy"));
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate())
                    setJournalData();
            }
        });

        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                editSearch.getText().toString().trim();
                if (editSearch.getText().toString().trim().length() == 0 || editSearch.getText().toString().trim().equalsIgnoreCase("")) {
//                    showToast("Please Enter some text to search", Toast.LENGTH_LONG);
                    return true;
                } else {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        performSearch(editSearch.getText().toString().trim());
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private boolean validate() {

        if (editJournalEntry.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_journal), Toast.LENGTH_SHORT);
            return false;
        }
        if (editMinutes.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_minute), Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    Dialog dialog;

    public void showDialog() {

        dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_sessions_attended);

        rvListAttendedPopup = (RecyclerView) dialog.findViewById(R.id.rvListAttendedPopup);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tvOk);

        layoutManager = new LinearLayoutManager(this);
        rvListAttendedPopup.setLayoutManager(layoutManager);
        mAdapter = new JournalAttendedPopupAdapter(this);
        rvListAttendedPopup.setAdapter(mAdapter);

        ArrayList<String> iDs = new ArrayList<>();
        try {
            iDs = Utils.asList(tvAttended.getTag().toString());
        } catch (Exception e) {
        }

        ArrayList<Spinner> data = new ArrayList<>();
        data.add(new Spinner("Support Group", "Support Group").setSelected(iDs.contains("Support Group")));
        data.add(new Spinner("Professional Consultant", "Professional Consultant").setSelected(iDs.contains("Professional Consultant")));
        data.add(new Spinner("Counseling", "Counseling").setSelected(iDs.contains("Counseling")));
        data.add(new Spinner("Cognitive Behavior Therapy(CBT)", "Cognitive Behavior Therapy(CBT)").setSelected(iDs.contains("Cognitive Behavior Therapy(CBT)")));
        data.add(new Spinner("Computerized Cognitive Behavior Therapy", "Computerized Cognitive Behavior Therapy").setSelected(iDs.contains("Computerized Cognitive Behavior Therapy")));
        data.add(new Spinner("Psychotherapy", "Psychotherapy").setSelected(iDs.contains("Psychotherapy")));
        data.add(new Spinner("Dialection Behavior Therapy(DBT)", "Dialection Behavior Therapy(DBT)").setSelected(iDs.contains("Dialection Behavior Therapy(DBT)")));
        data.add(new Spinner("Psychiatry", "Psychiatry").setSelected(iDs.contains("Psychiatry")));

        mAdapter.addAll(data);

        mAdapter.setEventlistener(new JournalAttendedPopupAdapter.Eventlistener() {
            @Override
            public void onItemviewClick(int position) {
                mAdapter.changeSelection(position, true);
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                selectedId = mAdapter.getSelectedIds();
                tvAttended.setText(selectedId);
                tvAttended.setTag(selectedId);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedId = "";
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
                textView.setTag(calendar.getTimeInMillis());
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

    Dialog searchDialog;

    public void SearchPopup(final ArrayList<JournalData> array) {

        searchDialog = new Dialog(getActivity());
        searchDialog.setCanceledOnTouchOutside(true);
        searchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        searchDialog.setCancelable(true);
        searchDialog.setContentView(R.layout.popup_calendar);

        TextView tvCancel = (TextView) searchDialog.findViewById(R.id.tvCancel);
        final TextView tvtitle = (TextView) searchDialog.findViewById(R.id.tvtitle);
        final LightCalendarView calendarView = (LightCalendarView) searchDialog.findViewById(R.id.calendarView);

        Calendar calFrom = Calendar.getInstance();
        Calendar calTo = Calendar.getInstance();
        Calendar calNow = Calendar.getInstance();
        calFrom.set(Calendar.MONTH, 0);
        calTo.set(Calendar.MONTH, 11);

        calendarView.setMonthFrom(calFrom.getTime());
        calendarView.setMonthTo(calTo.getTime());
//        calendarView.setMonthCurrent(calNow.getTime());

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchDialog.dismiss();
            }
        });

        if (array.size() == 0) {
//            showToast("no data", Toast.LENGTH_LONG);
            calendarView.setMonthCurrent(calNow.getTime());
        } else {
            calendarView.setMonthCurrent(array.get(array.size() - 1).date);
        }
        final SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());

        calendarView.setOnStateUpdatedListener(new LightCalendarView.OnStateUpdatedListener() {
            @Override
            public void onMonthSelected(@NotNull final Date date, @NotNull final MonthView monthView) {
                tvtitle.setText("" + formatter.format(date));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        HashMap<Date, List<Accent>> map = new HashMap<>();
//                        HashMap<String, List<Accent>> mapTemp = new HashMap<>();

                        for (int i = 0; i < array.size(); i++) {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(array.get(i).date);
                            cal.add(Calendar.HOUR_OF_DAY, 22);

                            DotAccent dotAccent = new DotAccent(10f, getResources().getColor(R.color.theme_blue), "-" + i);
//                            accents.add();
                            Debug.e("date", cal.getTime().toString());

                            if (map.containsKey(cal.getTime())) {
                                List<Accent> accentsT = map.get(cal.getTime());
                                accentsT.add(dotAccent);
                                map.put(cal.getTime(), accentsT);
                            } else {
                                List<Accent> accents = new ArrayList<>();
                                accents.add(dotAccent);
                                map.put(cal.getTime(), accents);
                            }
                        }
                        monthView.setAccents(map);

                    }
                }, 1000);
            }

            @Override
            public void onDateSelected(@NotNull Date date) {
                ArrayList<String> data = new ArrayList<String>();
                data.clear();
                for (int i = 0; i < array.size(); i++) {
                    if (array.get(i).date.getDate() == date.getDate()) {
                        data.add(array.get(i).journal);
                    }
                }
                Debug.e("onDateSelected", date.toString());
                showJournalEntryDialog(data, date);
                dismissDialog();
            }
        });
        searchDialog.show();
    }

    public void showJournalEntryDialog(ArrayList<String> data, Date d) {

        dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_view_journal_entry);

        TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tvOk);
        TextView tvDate = (TextView) dialog.findViewById(R.id.tvDate);
//        Tue Aug 22 12:37:59 GMT+05:30 2017
        tvDate.setText(Utils.parseTimeUTCtoDefault("" + d, "EEE MMM dd HH:mm:ss z yyyy", "MM/dd/yy"));

        mRvListJournalEntery = (RecyclerView) dialog.findViewById(R.id.rvListJournalEntery);
        layoutManager = new LinearLayoutManager(this);
        mRvListJournalEntery.setLayoutManager(layoutManager);
        mJournalEntryListAdapter = new JournalEntryListAdapter(this);
        mRvListJournalEntery.setAdapter(mJournalEntryListAdapter);

        mJournalEntryListAdapter.addAll(data);
//        Debug.e("", "showJournalEntryDialog-->>># " + data);
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

    public void jpurnalEntryDialog() {
        dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_save);

        TextView tvClose = (TextView) dialog.findViewById(R.id.tvClose);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void setJournalData() {
        try {
            showDialog("");
            Debug.e("date", "" + Utils.parseTime(Utils.parseTimeUTCtoDefault(1 * Long.valueOf("" + tvGetDate.getTag())).getTime(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
            body.addEncoded(RequestParamsUtils.ATTENDED_SESSION, selectedId);
            body.addEncoded(RequestParamsUtils.JOURNAL, editJournalEntry.getText().toString().trim());
            body.addEncoded(RequestParamsUtils.EXERCISE, editMinutes.getText().toString().trim());
//            body.addEncoded(RequestParamsUtils.UPDATED_AT, tvGetDate.getText().toString().trim());
//            body.addEncoded(RequestParamsUtils.UPDATED_AT, Utils.parseTime(Utils.parseTimeUTCtoDefault(1 * Long.valueOf("" + tvGetDate.getTag())).getTime(), "yyyy-MM-dd'T'hh:mm:ss'Z'"));//2017-10-27T05:26:23.813Z
            body.addEncoded(RequestParamsUtils.UPDATED_AT, Utils.parseTime(Utils.parseTimeUTCtoDefault(1 * Long.valueOf("" + tvGetDate.getTag())).getTime(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));//2017-10-27T05:26:23.813Z

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
                Debug.e("", "setJournalData# " + response);
                if (response != null && response.length() > 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            jpurnalEntryDialog();
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

    public void performSearch(String word) {
        try {
            showDialog("");

//            RequestParams params = RequestParamsUtils.newRequestParams(getActivity());
//            Debug.e("performSearch-->>", "" + params.toString());
//            AsyncHttpClient client = AsyncHttpRequest.newRequest(getActivity());
//            client.get(URLs.GET_JOURNALS(), params, new GetVersionDataHandle(getActivity(), word));

            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_JOURNALS());
            Call call = HttpClient.newRequestGet(getActivity(), body.build().toString());
            call.enqueue(new GetVersionDataHandle(getActivity(), word));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersionDataHandle extends AsyncResponseHandlerOk {
        String word = "";

        public GetVersionDataHandle(Activity context, String word) {
            super(context);
            this.word = word;
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
                Debug.e("", "performSearch# " + response);
                if (response != null && response.length() > 0) {

                    final JournalEntryRes res = new Gson().fromJson(response, new TypeToken<JournalEntryRes>() {
                    }.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<JournalData> d = new ArrayList<>();
                            for (int i = 0; i < res.journals.size(); i++) {
                                if (res.journals.get(i).text.toLowerCase().contains(word.toLowerCase())) {
                                    d.add(new JournalData(res.journals.get(i).text, Utils.parseTime(res.journals.get(i).date, "yyyy-MM-dd")));
                                }
                            }

                            if (d != null) {
                                SearchPopup(d);
                            }

                            Debug.e("journal array", new Gson().toJson(d));
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

    public class JournalData {
        public JournalData(String journal, Date date) {
            this.journal = journal;
            this.date = date;
        }

        String journal;
        Date date;
    }

}
