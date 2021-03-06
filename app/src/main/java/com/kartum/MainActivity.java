package com.kartum;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kartum.objects.DashboardStatsRes;
import com.kartum.objects.Spinner;
import com.kartum.objects.TimeZOne;
import com.kartum.utils.AsyncResponseHandlerOk;
import com.kartum.utils.Constant;
import com.kartum.utils.Debug;
import com.kartum.utils.ExitStrategy;
import com.kartum.utils.HttpClient;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.URLs;
import com.kartum.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.HttpUrl;

public class MainActivity extends BaseActivity {


    @BindView(R.id.llMood)
    LinearLayout llMood;
    @BindView(R.id.llPrescriptions)
    LinearLayout llPrescriptions;
    @BindView(R.id.llPhysicalSymptoms)
    LinearLayout llPhysicalSymptoms;
    @BindView(R.id.llSymptomSideEffect)
    LinearLayout llSymptomSideEffect;
    @BindView(R.id.llJournal)
    LinearLayout llJournal;
    @BindView(R.id.llObserve)
    LinearLayout llObserve;

    @BindView(R.id.tvMoodDate)
    TextView tvMoodDate;
    @BindView(R.id.tvPrescriptionDate)
    TextView tvPrescriptionDate;
    @BindView(R.id.tvSideEffectsDate)
    TextView tvSideEffectsDate;
    @BindView(R.id.tvPhysicalSympDate)
    TextView tvPhysicalSympDate;
    @BindView(R.id.tvJournalDate)
    TextView tvJournalDate;
    @BindView(R.id.tvObserveDate)
    TextView tvObserveDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initDrawer(true);
        init();
    }

    private void init() {
        //setTitleText("Faro10");

        llMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MoodVitalsActivity.class);
                startActivity(intent);
            }
        });

        llPrescriptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PrescriptionsActivity.class);
                startActivity(intent);
            }
        });
        llPhysicalSymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PhysicalSymptomsActivity.class);
                startActivity(intent);
            }
        });
        llSymptomSideEffect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SymptomsSideEffectsActivity.class);
                startActivity(intent);
            }
        });
        llJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JournalActivity.class);
                startActivity(intent);
            }
        });
        llObserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ObservingPeopleActivity.class);
                startActivity(intent);
            }
        });
        getTimeZOne();
    }


    public void getTimeZOne() {
        try {
//            showDialog("");

            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_USER_TIMEZONE());
            body.addQueryParameter(RequestParamsUtils.TOKEN, Utils.getPref(getActivity(), RequestParamsUtils.TOKEN, ""));
            body.addQueryParameter(RequestParamsUtils.EMAIL, Utils.getPref(getActivity(), RequestParamsUtils.EMAIL, ""));
            Debug.e("getProfile", "" + body.build().toString());
            Call call = HttpClient.newRequestGet(getActivity(), body.build().toString());
            call.enqueue(new GetVersionaDataHandler(getActivity()));

        } catch (
                Exception e)

        {
            e.printStackTrace();
        }
    }

    ArrayList<Spinner> timezoneList = new ArrayList<>();

    private class GetVersionaDataHandler extends AsyncResponseHandlerOk {

        public GetVersionaDataHandler(Activity context) {
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
                Debug.e("", "getTimeZOne# " + response);
                if (response != null && response.length() > 0) {

                    TimeZOne res = new Gson().fromJson(response, new TypeToken<TimeZOne>() {
                    }.getType());

//                    timezoneList.clear();
//                    for (String s : res.timeZones) {
////                        timezonelist.add(new spinner(s));
//                     Utils.setPref(getActivity(), Constant.TIME_ZONE, );
//                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utils.setPref(getActivity(), Constant.TIME_ZONE, response);
//                            fillData(res);
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen for landscape and portrait and set portrait mode always
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onResume() {
//        getDashboardData();

        Calendar calendar = Calendar.getInstance();
//        tvMoodDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "dd/MM/yy  hh:mm a"));
//        tvPrescriptionDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "dd/MM/yy  hh:mm a"));
//        tvSideEffectsDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "dd/MM/yy  hh:mm a"));
//        tvPhysicalSympDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "dd/MM/yy  hh:mm a"));
//        tvJournalDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "dd/MM/yy  hh:mm a"));
//        tvObserveDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "dd/MM/yy  hh:mm a"));

        tvMoodDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "MM/dd/yy  hh:mm a"));
        tvPrescriptionDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "MM/dd/yy  hh:mm a"));
        tvSideEffectsDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "MM/dd/yy  hh:mm a"));
        tvPhysicalSympDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "MM/dd/yy  hh:mm a"));
        tvJournalDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "MM/dd/yy  hh:mm a"));
        tvObserveDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "MM/dd/yy  hh:mm a"));

//        tvMoodDate.setText(Utils.parseTimeUTCtoDefault(res.lastEntry, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd/MM/yy  hh:mm a"));
//        tvPrescriptionDate.setText(Utils.parseTimeUTCtoDefault(res.lastEntry, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd/MM/yy  hh:mm a"));
//        tvSideEffectsDate.setText(Utils.parseTimeUTCtoDefault(res.lastEntry, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd/MM/yy  hh:mm a"));
//        tvPhysicalSympDate.setText(Utils.parseTimeUTCtoDefault(res.lastEntry, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd/MM/yy  hh:mm a"));
//        tvJournalDate.setText(Utils.parseTimeUTCtoDefault(res.lastEntry, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd/MM/yy  hh:mm a"));
//        tvObserveDate.setText(Utils.parseTimeUTCtoDefault(res.lastEntry, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd/MM/yy  hh:mm a"));

        super.onResume();
    }

    @Override
    public void onBackPressed() {

        try {
            if (result.isDrawerOpen()) {
                result.closeDrawer();
            } else {
                if (ExitStrategy.canExit()) {
                    super.onBackPressed();
                } else {
                    ExitStrategy.startExitDelay(2000);
                    Toast.makeText(getActivity(), getString(R.string.exit_msg),
                            Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
        }
    }

    public void getDashboardData() {
        try {
//            showEditDialog("");

//            RequestParams params = RequestParamsUtils.newRequestParams(getActivity());
//            Debug.e("getDashboardData-->>", "" + params.toString());
//            AsyncHttpClient client = AsyncHttpRequest.newRequest(getActivity());
//            client.get(URLs.GET_DASHBOARD(), params, new GetVersionDataHandle(getActivity()));

            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_DASHBOARD());
            Call call = HttpClient.newRequestGet(getActivity(), body.build().toString());
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
//                dismissDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSuccess(String response) {

            try {
                Debug.e("", "getDashboardData# " + response);
                if (response != null && response.length() > 0) {

                    final DashboardStatsRes res = new Gson().fromJson(response, new TypeToken<DashboardStatsRes>() {
                    }.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fillData(res);
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
        }
    }

    public void fillData(DashboardStatsRes res) {

        tvMoodDate.setText(Utils.parseTimeUTCtoDefault(res.lastEntry, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "MM/dd/yy  hh:mm a"));
        tvPrescriptionDate.setText(Utils.parseTimeUTCtoDefault(res.lastEntry, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "MM/dd/yy  hh:mm a"));
        tvSideEffectsDate.setText(Utils.parseTimeUTCtoDefault(res.lastEntry, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "MM/dd/yy  hh:mm a"));
        tvPhysicalSympDate.setText(Utils.parseTimeUTCtoDefault(res.lastEntry, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "MM/dd/yy  hh:mm a"));
        tvJournalDate.setText(Utils.parseTimeUTCtoDefault(res.lastEntry, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "MM/dd/yy  hh:mm a"));
        tvObserveDate.setText(Utils.parseTimeUTCtoDefault(res.lastEntry, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "MM/dd/yy  hh:mm a"));
    }
}
