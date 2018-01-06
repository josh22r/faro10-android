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
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kartum.R;
import com.kartum.objects.PriscriptionRes;
import com.kartum.utils.AsyncResponseHandlerOk;
import com.kartum.utils.DayAxisValueFormatterNew;
import com.kartum.utils.Debug;
import com.kartum.utils.HttpClient;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.URLs;
import com.kartum.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.HttpUrl;

public class PrescriptionConsistFrag extends BaseFragment implements OnChartValueSelectedListener {

    @BindView(R.id.chart1)
    BarChart mChart;
    @BindView(R.id.tvPrev)
    TextView tvPrev;
    @BindView(R.id.tvTital)
    TextView tvTital;
    @BindView(R.id.tvNext)
    TextView tvNext;

    public PriscriptionRes res;

    int index = 2;
    int week = 2;
//    int type;

    public PrescriptionConsistFrag() {
        super();
    }

    public static PrescriptionConsistFrag newInstance(int type) {

        PrescriptionConsistFrag pane = new PrescriptionConsistFrag();
        Bundle args = new Bundle();
        args.putInt("type", type);
        pane.setArguments(args);
        return pane;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.frag_prescription_consist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
//        type = getArguments().getInt("type", 0);

        getPresChartData(week);

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
                getPresChartData(week);
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

                getPresChartData(week);
            }
        });

    }

    public void reload() {
        getPresChartData(week);
    }

    public void initChart() {
        yVals.clear();

        int k = 14;
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

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatterNew(mChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(8);
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

        for (int i = 0; i < finalK; i++) {
            Calendar cal = Calendar.getInstance();
//            cal.add(Calendar.DAY_OF_MONTH, 1);
            cal.add(Calendar.DAY_OF_MONTH, -1 * (finalK - i));

            boolean isFound = false;
            for (int j = 0; j < res.entriesPrescriptionsChart.medsTaken.size(); j++) {
                String time = res.entriesPrescriptionsChart.medsTaken.get(j).get(0);

                String first = time.substring(0, 1);
                if (!first.equalsIgnoreCase("-")) {

//                    if (Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy").
//                            equalsIgnoreCase(Utils.parseTime(Utils.parseTimeUTCtoDefault(1 * Long.valueOf("" + time)).getTime(), "dd MMM yyyy"))) {
//
//                        Debug.e("date lable : ", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM") + " " + res.entriesPrescriptionsChart.medsTaken.get(j).get(1));
//                        Debug.e("date-->>", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy") + " = " + Utils.parseTime(Utils.parseTimeUTCtoDefault(1 * Long.valueOf("" + time)).getTime(), "dd MMM yyyy"));
//
//                        isFound = true;
//                        yVals.add(Float.valueOf("" + res.entriesPrescriptionsChart.medsTaken.get(j).get(1)));
//                        break;
//                    }
//                    Debug.e("date lable : ", "" + Utils.parseTimeUTCtoDefault("" + time, "dd MMM yyyy"));

                    if (Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy").
                            equalsIgnoreCase(Utils.parseTime(Long.valueOf(res.entriesPrescriptionsChart.medsTaken.get(j).get(0)), "dd MMM yyyy"))) {

                        Debug.e("date set lable : ", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM") + " " + res.entriesPrescriptionsChart.medsTaken.get(j).get(1));
//                        Debug.e("date-->>", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy") + " = " + Utils.parseTime(Long.valueOf(res.entriesPrescriptionsChart.medsTaken.get(j).get(0)), "dd MMM yyyy"));

                        isFound = true;
                        yVals.add(Float.valueOf("" + res.entriesPrescriptionsChart.medsTaken.get(j).get(1)));
                        break;
                    }

                }
            }

            if (!isFound) {
                yVals.add(Float.valueOf("0"));
            }
        }

        Debug.e("yVals", "" + yVals.size());

//        IAxisValueFormatter custom = new MyAxisValueFormatter();
        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        leftAxis.setLabelCount(8, false);
        leftAxis.setCenterAxisLabels(true);
        leftAxis.setGranularity(1f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawAxisLine(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setEnabled(false);

        Legend lgdSideEffects = mChart.getLegend();
        lgdSideEffects.setEnabled(false);
        lgdSideEffects.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        lgdSideEffects.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        lgdSideEffects.setOrientation(Legend.LegendOrientation.VERTICAL);
        lgdSideEffects.setDrawInside(false);
        lgdSideEffects.setForm(Legend.LegendForm.SQUARE);
        lgdSideEffects.setFormSize(9f);
        lgdSideEffects.setTextSize(11f);
        lgdSideEffects.setXEntrySpace(4f);

//        Legend l = mChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setForm(Legend.LegendForm.SQUARE);
//        l.setFormSize(9f);
//        l.setTextSize(11f);
//        l.setXEntrySpace(4f);

        setBarChartData(yVals, "");

    }

    List<String> xVals = new ArrayList<>();
    List<Float> yVals = new ArrayList<>();
//    List<Date> dateArray = new ArrayList<>();

    private void setBarChartData(List<Float> yVals, String label) {

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
//        for (int i = xVals.size(); i > 0; i--) {
//            yVals1.add(new BarEntry(i, yVals.get(i)));
//        }

        float start = 1f;
        for (int i = (int) start; i <= yVals.size(); i++) {
            BarEntry barEntry = new BarEntry(i, yVals.get(i - 1));
            yVals1.add(barEntry);
//            Debug.e("BarEntry", barEntry.toString());
        }

        BarDataSet set;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {

            set = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();

        } else {
            set = new BarDataSet(yVals1, "");
            set.setColors(getResources().getColor(R.color.theme_blue));
//            BarData data = new BarData(set1, set2, set3, set4);
            BarData data = new BarData(set);
//            data.setValueFormatter(new LargeValueFormatter());

            mChart.setData(data);
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mChart.getData().notifyDataChanged(); // let the data know a dataSet changed
                mChart.notifyDataSetChanged(); // let the chart know it's data changed
                mChart.invalidate(); // refresh
            }
        });
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

    public void getPresChartData(int week) {
        try {
            showDialog("");
            Debug.e("API index ", "" + index);
            Debug.e("API week ", "" + week);
            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_PRESCRIPTION());
            body.addQueryParameter(RequestParamsUtils.WEEKS, String.valueOf(week));
            Call call = HttpClient.newRequestGet(getActivity(), body.build().toString());
            call.enqueue(new GetVersionDataHandler(getActivity()));
            Debug.e("getPresChartData ", body.build().toString());

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
                Debug.e("", "getPresChartData # " + response);
                if (response != null && response.length() > 0) {

                    res = new Gson().fromJson(response, new TypeToken<PriscriptionRes>() {
                    }.getType());

                    initChart();
//                    Debug.e("", "result # " + res.entriesPrescriptionsChart.medsTaken);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
            Debug.e("", "getPresChartData onFailure # " + content);
            dismissDialog();
        }
    }
}

