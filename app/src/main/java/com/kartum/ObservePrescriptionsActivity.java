package com.kartum;

import android.app.Activity;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.common.view.SimpleListDividerDecorator;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kartum.adapter.BarChartAdapter;
import com.kartum.adapter.ObservPrescripAdapter;
import com.kartum.objects.ObservPresChartRes;
import com.kartum.utils.AsyncResponseHandlerOk;
import com.kartum.utils.ChartColor;
import com.kartum.utils.Debug;
import com.kartum.utils.HttpClient;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.URLs;
import com.kartum.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.HttpUrl;

public class ObservePrescriptionsActivity extends BaseActivity implements OnChartValueSelectedListener {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.rvObsChart)
    RecyclerView rvObsChart;
    RecyclerView.LayoutManager layoutManager;
    public ObservPrescripAdapter mAdapter;
    public BarChartAdapter mBarChartAdapter;
    //
    @BindView(R.id.tvBackArrow)
    TextView tvBackArrow;
//    @BindView(R.id.llObservHistory)
//    LinearLayout llObservHistory;
//    @BindView(R.id.llPrescription)
//    LinearLayout llPrescription;

    @BindView(R.id.chart1)
    BarChart mChart;
    @BindView(R.id.tvPrev)
    TextView tvPrev;
    @BindView(R.id.tvTital)
    TextView tvTital;
    @BindView(R.id.tvNext)
    TextView tvNext;

//    public ObservPresChartRes res;

    int index = 2;
    int week = 2;

    public List<List<String>> medsTaken = new ArrayList<List<String>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observ_pres);
        ButterKnife.bind(this);

        initDrawer(true);
        init();
    }

    private void init() {

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
        mAdapter = new ObservPrescripAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        rvObsChart.setLayoutManager(layoutManager);
        rvObsChart.setHasFixedSize(true);
        mBarChartAdapter = new BarChartAdapter(this);
        rvObsChart.setAdapter(mBarChartAdapter);
        rvObsChart.setNestedScrollingEnabled(true);

        tvBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObservePrescriptionsActivity.super.onBackPressed();
            }
        });

        initChart();

        mBarChartAdapter.setmEventListener(new BarChartAdapter.EventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClickedView(int position) {
                mBarChartAdapter.changeSelection(position, true);
                medsTaken.clear();
                ArrayList<ObservPresChartRes.Observee> mData = mBarChartAdapter.getSelectedAll();
                for (int i = 0; i < mData.size(); i++) {
//                    for (int j = 0; j < mData.get(i).medsTaken.size(); j++) {
//                        medsTaken.add(mData.get(i).medsTaken.get(j));
//                    }
                }
//                Debug.e("setpossition", "" + data.medsTaken);
                initChart();
            }
        });

        getObsPrescriptionsData();
        getPrescripChartData(week);

        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPrev.setClickable(true);
                if (index == 1) {
                    tvTital.setText("2 Weeks");
                    index = 2;
                    week = 2;
                } else if (index == 2) {
                    tvTital.setText("1 Month");
                    week = 4;
                    index = 3;
                } else if (index == 3) {
                    tvTital.setText("2 Months");
                    week = 8;
                    index = 4;
                    tvNext.setClickable(false);
                }
                getPrescripChartData(week);
            }
        });

        tvPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvNext.setClickable(true);
                if (index == 4) {
                    tvTital.setText("1 Month");
                    week = 4;
                    index = 3;
                } else if (index == 3) {
                    tvTital.setText("2 Weeks");
                    week = 2;
                    index = 2;
                } else if (index == 2) {
                    tvTital.setText("1 Week");
                    week = 1;
                    index = 1;
                    tvPrev.setClickable(false);
                }
                getPrescripChartData(week);
            }
        });

        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        mChart.setHorizontalScrollBarEnabled(true);
        mChart.setVerticalScrollBarEnabled(false);
        mChart.setFitBars(false);

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        IAxisValueFormatter custom = new MyAxisValueFormatter();
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setEnabled(false);

        Legend l = mChart.getLegend();
        l.setEnabled(false);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
    }

    public void initChart() {
//        yVals.clear();

        int k = 7;
        if (index == 1) {
            k = 7;
        } else if (index == 2) {
            k = 14;
        } else if (index == 3) {
            k = 30;
        } else if (index == 4) {
            k = 60;
        }

        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        mChart.setHorizontalScrollBarEnabled(true);
        mChart.setVerticalScrollBarEnabled(false);
        mChart.setFitBars(true);

//        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatterNew(mLifeScallingChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setCenterAxisLabels(false);

//        xAxis.setLabelCount(k, true);
//        xAxis.setLabelRotationAngle(-45);
//        xAxis.setValueFormatter(xAxisFormatter);
        final int finalK = k;

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
//                Debug.e("getFormattedValue", "" + value);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, -1 * (finalK - ((int) value)));
//                dateArray.add(cal.getTime());
//                Debug.e("date lable : ", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM"));
                return Utils.parseTime(cal.getTimeInMillis(), "dd MMM");
            }
        });

        IAxisValueFormatter custom = new MyAxisValueFormatter();
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setEnabled(false);
        rightAxis.setCenterAxisLabels(true);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        setBarChartData("", finalK);

    }
    //    List<String> xVals = new ArrayList<>();
//    List<Float> yVals = new ArrayList<>();
//    List<Date> dateArray = new ArrayList<>();

    class MyBarData {
        public List<BarEntry> barEntries = new ArrayList<>();
        public int color;

        public MyBarData(List<BarEntry> barEntries, int color) {
            this.barEntries = barEntries;
            this.color = color;
        }
    }

    private void setBarChartData(String label, int finalK) {
        HashSet<String> selectedAll = mBarChartAdapter.getSelectedAllUserId();

        List<MyBarData> valueData = new ArrayList<MyBarData>();

        Iterator<String> iterator = selectedAll.iterator();
        while (iterator.hasNext()) {

            String userId = iterator.next();

            if (hm.containsKey(userId) && hm.get(userId).size() > 0) {
                List<Float> temp = new ArrayList<>();

                for (int i = 0; i < finalK; i++) {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DAY_OF_MONTH, -1 * (finalK - i));

                    boolean isFound = false;
                    for (int k = 0; k < hm.get(userId).size(); k++) {
                        List<List<String>> medsTaken = hm.get(userId);

                        String time = medsTaken.get(k).get(0);

//                Debug.e("date-->>", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy") + " = " + Utils.parseTime(Utils.parseTimeUTCtoDefault(1 * Long.valueOf("" + time)).getTime(), "dd MMM yyyy"));

                        String first = time.substring(0, 1);
                        if (!first.equalsIgnoreCase("-")) {

//                            if (Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy").
//                                    equalsIgnoreCase(Utils.parseTime(Utils.parseTimeUTCtoDefault(1 * Long.valueOf("" + time)).getTime(), "dd MMM yyyy"))) {
//
//                                isFound = true;
//                                temp.add(Float.valueOf("" + medsTaken.get(k).get(1)));
//                                break;
//                            }

                            if (Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy").
                                    equalsIgnoreCase(Utils.parseTime(Long.valueOf(medsTaken.get(k).get(0)), "dd MMM yyyy"))) {

                                Debug.e("date set lable : ", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM") + " " + medsTaken.get(k).get(1));
//                                Debug.e("date-->>", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy") + " = " + Utils.parseTime(Long.valueOf(medsTaken.get(k).get(0)), "dd MMM yyyy"));

                                isFound = true;
                                temp.add(Float.valueOf("" + medsTaken.get(k).get(1)));
                                break;
                            }
                        }
                    }

                    if (!isFound) {
                        temp.add(Float.valueOf("0"));
                    }

                }

                List<BarEntry> barEntries = new ArrayList<BarEntry>();
                float start = 1f;
                for (int l = (int) start; l <= temp.size(); l++) {
                    BarEntry barEntry = new BarEntry(l, temp.get(l - 1));
                    barEntries.add(barEntry);
                }

//                ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
                ChartColor generator = ChartColor.MATERIAL; // or use DEFAULT
                int color2 = generator.getColor("" + userId);
//                Debug.e("userId", "" + userId + " " + color2);
                valueData.add(new MyBarData(barEntries, color2));


            }
        }

        BarDataSet set, set1, set2, set3;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            mChart.clearValues();

            List<IBarDataSet> barDataSets = new ArrayList<>();
            for (int i = 0; i < valueData.size(); i++) {
                BarDataSet dataSet = new BarDataSet(valueData.get(i).barEntries, "");
                dataSet.setColor(valueData.get(i).color);
                barDataSets.add(dataSet);
            }

            BarData data = new BarData(barDataSets);
            data.setValueFormatter(new LargeValueFormatter());
            mChart.setData(data);

            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {

            List<IBarDataSet> barDataSets = new ArrayList<>();
            for (int i = 0; i < valueData.size(); i++) {
                BarDataSet dataSet = new BarDataSet(valueData.get(i).barEntries, "");
                dataSet.setColor(valueData.get(i).color);
                barDataSets.add(dataSet);
            }

//            Debug.e("barDataSets", "" + barDataSets.size());

            BarData data = new BarData(barDataSets);
            data.setValueFormatter(new LargeValueFormatter());
            mChart.setData(data);
//        }
            mChart.getData().notifyDataChanged(); // let the data know a dataSet changed
            mChart.notifyDataSetChanged(); // let the chart know it's data changed
            mChart.invalidate(); // refresh
        }
    }


    protected RectF mOnValueSelectedRectF = new RectF();

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;
        mChart.getBarBounds((BarEntry) e, bounds);
        MPPointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);

        Debug.i("bounds", bounds.toString());
        Debug.i("position", position.toString());

        Debug.i("x-index",
                "low: " + mChart.getLowestVisibleX() + ", high: "
                        + mChart.getHighestVisibleX());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {

    }

    Map<String, List<List<String>>> hm = new HashMap<>();

    public void getObsPrescriptionsData() {
        try {
//            showEditDialog("");

            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_OBS_PRES_HIST());
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
        public void onSuccess(final String response) {
            try {
                Debug.e("", "getObsPrescriptionsData# " + response);
                if (response != null && response.length() > 0) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONArray array = new JSONArray();
                            JSONObject obj = null;
                            try {
                                obj = new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Iterator iterator = obj.keys();

                            while (iterator.hasNext()) {
                                String key = (String) iterator.next();
                                try {
                                    JSONArray array1 = obj.getJSONArray(key);
                                    JSONObject object = new JSONObject();
                                    object.put("name", key);
                                    object.put("array", array1);
                                    array.put(object);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Debug.e("", "name -->> " + array);
                            for (int i = 0; i < array.length(); i++) {
                                try {
                                    mAdapter.add(array.getJSONObject(i));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
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

    public void getPrescripChartData(int week) {
        try {
            showDialog("");

//            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_OBS_PRES_GRAPH());
//            Call call = HttpClient.newRequestGet(getActivity(), body.build().toString());
//            call.enqueue(new GetVersionDataHandl(getActivity()));

            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_OBS_PRES_GRAPH());
            body.addQueryParameter(RequestParamsUtils.WEEKS, String.valueOf(week));
            Call call = HttpClient.newRequestGet(getActivity(), body.build().toString());
            call.enqueue(new GetVersionDataHandl(getActivity()));
            Debug.e("getPrescripChartData ", body.build().toString());
            Debug.e("week ", "" + week);

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
                Debug.e("", "getPrescripChartData# " + response);
                if (response != null && response.length() > 0) {
                    hm.clear();
                    final ObservPresChartRes res = new Gson().fromJson(response, new TypeToken<ObservPresChartRes>() {
                    }.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

//                            mBarChartAdapter.clear();
                            mBarChartAdapter.addAll(res.observees);

                            for (int i = 0; i < res.observees.size(); i++) {
                                mBarChartAdapter.changeSelection(i, true);
//                                for (int j = 0; j < res.observees.get(i).medsTaken.size(); j++) {
//                                    medsTaken.add(res.observees.get(i).medsTaken.get(j));
//                                }

                                hm.put(res.observees.get(i).userId, res.observees.get(i).medsTaken);
                            }

                            initChart();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
            Debug.e("", "getPrescripChartData onFailure # " + content);
            dismissDialog();
        }
    }
}
