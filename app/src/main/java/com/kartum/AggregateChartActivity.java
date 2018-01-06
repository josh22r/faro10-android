package com.kartum;

import android.app.Activity;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kartum.objects.AggregateChartRes;
import com.kartum.objects.DashboardStatsRes;
import com.kartum.utils.AsyncResponseHandlerOk;
import com.kartum.utils.Debug;
import com.kartum.utils.HttpClient;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.URLs;
import com.kartum.utils.Utils;

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

public class AggregateChartActivity extends BaseActivity implements OnChartValueSelectedListener {

    @BindView(R.id.tvPrev)
    TextView tvPrev;
    @BindView(R.id.tvTital)
    TextView tvTital;
    @BindView(R.id.tvNext)
    TextView tvNext;

    @BindView(R.id.tvLifeScale)
    TextView tvLifeScale;
    @BindView(R.id.llLifeScale)
    LinearLayout llLifeScale;

    @BindView(R.id.tvLastEntery)
    TextView tvLastEntery;
    @BindView(R.id.tvMyEntries)
    TextView tvMyEntries;
    @BindView(R.id.tvSelfHarm)
    TextView tvSelfHarm;
    @BindView(R.id.tvAttended)
    TextView tvAttended;

    @BindView(R.id.cbSocialLife)
    CheckBox cbSocialLife;
    @BindView(R.id.cbFamilyLife)
    CheckBox cbFamilyLife;
    @BindView(R.id.cbWorkLife)
    CheckBox cbWorkLife;

    @BindView(R.id.cbEveryOne)
    CheckBox cbEveryOne;
    @BindView(R.id.cbMe)
    CheckBox cbMe;

    @BindView(R.id.cbAppetite)
    CheckBox cbAppetite;
    @BindView(R.id.cbInitiative)
    CheckBox cbInitiative;
    @BindView(R.id.cbPassimism)
    CheckBox cbPassimism;
    @BindView(R.id.cbJoy)
    CheckBox cbJoy;

    @BindView(R.id.cbAnxiety)
    CheckBox cbAnxiety;
    @BindView(R.id.cbSleep)
    CheckBox cbSleep;
    @BindView(R.id.cbMood)
    CheckBox cbMood;
    @BindView(R.id.cbEnergy)
    CheckBox cbEnergy;
    @BindView(R.id.cbConcentration)
    CheckBox cbConcentration;

    @BindView(R.id.cbuicidalThoughts)
    CheckBox cbuicidalThoughts;
    @BindView(R.id.cbHeadache)
    CheckBox cbHeadache;
    @BindView(R.id.cbNightmares)
    CheckBox cbNightmares;
    @BindView(R.id.cbPanicAttacks)
    CheckBox cbPanicAttacks;

    @BindView(R.id.lineChart)
    LineChart mLifeScallingChart;

    @BindView(R.id.lineChartMood)
    LineChart moodChart;
    @BindView(R.id.tvMood)
    TextView tvMood;
    @BindView(R.id.llMood)
    LinearLayout llMood;

    @BindView(R.id.lineChartWeight)
    LineChart weightChart;
    @BindView(R.id.tvWeight)
    TextView tvWeight;
    @BindView(R.id.llSymptom)
    LinearLayout llSymptom;

    @BindView(R.id.lineChartSymptomMood)
    LineChart symptomMoodChart;
    @BindView(R.id.tvSymptomMood)
    TextView tvSymptomMood;
    @BindView(R.id.llSymptomMood)
    LinearLayout llSymptomMood;

    @BindView(R.id.lineChartSymptom)
    LineChart SymptomChart;
    @BindView(R.id.tvSymptom)
    TextView tvSymptom;
    @BindView(R.id.llWeight)
    LinearLayout llWeight;

    @BindView(R.id.barChartSideEffects)
    BarChart sideEffectsChart;
    @BindView(R.id.tvSideEffects)
    TextView tvSideEffects;
    @BindView(R.id.llSideEffects)
    LinearLayout llSideEffects;

    @BindView(R.id.tvNausea)
    TextView tvNausea;
    @BindView(R.id.tvRestlessness)
    TextView tvRestlessness;
    @BindView(R.id.tvDryMouth)
    TextView tvDryMouth;
    @BindView(R.id.tvPanicAttack)
    TextView tvPanicAttack;

    int index = 2;
    int week = 2;
    private Typeface mTfLight;

//    public DashboardStatsRes resp;

    public AggregateChartRes res;
    public List<List<String>> workLife = new ArrayList<List<String>>();
    public List<List<String>> socialLife = new ArrayList<List<String>>();
    public List<List<String>> familyLife = new ArrayList<List<String>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggregate_chart);
        ButterKnife.bind(this);

        initDrawer(true);
        init();
    }

    private void init() {
        getDashboardData();
        fillData();

        getChartData();
        initChart();
        initMoodChart();
        initWeightChart();
        initSymptomChart();
        initSymptomMoodChart();
        initsideEffectsChart();

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
                getChartData();

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
                getChartData();

//                if (index == 1) {
//                    tvPrev.setClickable(false);
//                } else {
//                    getChartData();
//                }

            }
        });

        tvLifeScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llLifeScale.getVisibility() == View.VISIBLE) {
                    llLifeScale.setVisibility(View.GONE);
                    tvLifeScale.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_gray_28dp, 0);
                } else {
                    llLifeScale.setVisibility(View.VISIBLE);
                    tvLifeScale.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_gray, 0);
                }
            }
        });

        tvMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llMood.getVisibility() == View.GONE) {
                    llMood.setVisibility(View.VISIBLE);
                    tvMood.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_gray, 0);
                } else {
                    llMood.setVisibility(View.GONE);
                    tvMood.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_gray_28dp, 0);
                }
            }
        });

        tvWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llWeight.getVisibility() == View.GONE) {
                    llWeight.setVisibility(View.VISIBLE);
                    tvWeight.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_gray, 0);
                } else {
                    llWeight.setVisibility(View.GONE);
                    tvWeight.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_gray_28dp, 0);
                }
            }
        });

        tvSymptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llSymptom.getVisibility() == View.GONE) {
                    llSymptom.setVisibility(View.VISIBLE);
                    tvSymptom.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_gray, 0);
                } else {
                    llSymptom.setVisibility(View.GONE);
                    tvSymptom.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_gray_28dp, 0);
                }
            }
        });

        tvSymptomMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llSymptomMood.getVisibility() == View.GONE) {
                    llSymptomMood.setVisibility(View.VISIBLE);
                    tvSymptomMood.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_gray, 0);
                } else {
                    llSymptomMood.setVisibility(View.GONE);
                    tvSymptomMood.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_gray_28dp, 0);
                }
            }
        });

        tvSideEffects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llSideEffects.getVisibility() == View.GONE) {
                    llSideEffects.setVisibility(View.VISIBLE);
                    tvSideEffects.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_gray, 0);
                } else {
                    llSideEffects.setVisibility(View.GONE);
                    tvSideEffects.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_gray_28dp, 0);
                }
            }
        });


        mLifeScallingChart.setOnChartValueSelectedListener(AggregateChartActivity.this);
        mLifeScallingChart.getDescription().setEnabled(false);
        mLifeScallingChart.setMaxVisibleValueCount(60);

        mLifeScallingChart.setGridBackgroundColor(R.color.accent);
        mLifeScallingChart.setPinchZoom(false);
        mLifeScallingChart.setDrawGridBackground(true);
        mLifeScallingChart.setHorizontalScrollBarEnabled(true);
        mLifeScallingChart.setVerticalScrollBarEnabled(false);

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mLifeScallingChart);
        XAxis xAxis = mLifeScallingChart.getXAxis();

        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setCenterAxisLabels(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);


        YAxis yAxis = mLifeScallingChart.getAxisLeft();
        yAxis.setEnabled(true);
        yAxis.setDrawGridLines(true);
        yAxis.setCenterAxisLabels(true);
        yAxis.setAxisMaximum(10);
        yAxis.setGranularity(1f); // only intervals of 1 day
//        yAxis.setLabelCount(7);
//        yAxis.setValueFormatter(xAxisFormatter);


        IAxisValueFormatter custom = new MyAxisValueFormatter();
        YAxis rightAxis = mLifeScallingChart.getAxisRight();
        rightAxis.setDrawGridLines(true);
        rightAxis.setCenterAxisLabels(true);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setEnabled(false);

        Legend l = mLifeScallingChart.getLegend();
        l.setEnabled(false);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        // Mood Chart

        moodChart.setOnChartValueSelectedListener(AggregateChartActivity.this);
        moodChart.getDescription().setEnabled(false);
        moodChart.setMaxVisibleValueCount(60);

        moodChart.setGridBackgroundColor(R.color.accent);
        moodChart.setPinchZoom(false);
        moodChart.setDrawGridBackground(true);
        moodChart.setHorizontalScrollBarEnabled(true);
        moodChart.setVerticalScrollBarEnabled(false);

        IAxisValueFormatter xAxisFormattermoodChart = new DayAxisValueFormatter(moodChart);
        XAxis xAxismoodChart = moodChart.getXAxis();

        xAxismoodChart.setEnabled(true);
        xAxismoodChart.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxismoodChart.setDrawGridLines(true);
        xAxismoodChart.setCenterAxisLabels(false);
        xAxismoodChart.setGranularity(1f); // only intervals of 1 day
        xAxismoodChart.setLabelCount(7);
        xAxismoodChart.setValueFormatter(xAxisFormattermoodChart);


        YAxis yAxismoodChart = moodChart.getAxisLeft();
        yAxismoodChart.setEnabled(true);
        yAxismoodChart.setDrawGridLines(true);
        yAxismoodChart.setCenterAxisLabels(true);
//        yAxismoodChart.setAxisMaximum(10);
        yAxismoodChart.setAxisMaximum(6);
        yAxismoodChart.setGranularity(1f); // only intervals of 1 day
//        yAxis.setLabelCount(7);
//        yAxis.setValueFormatter(xAxisFormatter);


        IAxisValueFormatter customMood = new MyAxisValueFormatter();
        YAxis rightAxisMood = moodChart.getAxisRight();
        rightAxisMood.setDrawGridLines(true);
        rightAxisMood.setCenterAxisLabels(true);
        rightAxisMood.setLabelCount(8, false);
        rightAxisMood.setValueFormatter(customMood);
        rightAxisMood.setSpaceTop(15f);
        rightAxisMood.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxisMood.setEnabled(false);

        Legend lMoodChart = moodChart.getLegend();
        lMoodChart.setEnabled(false);
        lMoodChart.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        lMoodChart.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        lMoodChart.setOrientation(Legend.LegendOrientation.VERTICAL);
        lMoodChart.setDrawInside(false);
        lMoodChart.setForm(Legend.LegendForm.SQUARE);
        lMoodChart.setFormSize(9f);
        lMoodChart.setTextSize(11f);
        lMoodChart.setXEntrySpace(4f);

        // Weight lineChart

        weightChart.setOnChartValueSelectedListener(AggregateChartActivity.this);
        weightChart.getDescription().setEnabled(false);
        weightChart.setMaxVisibleValueCount(60);

        weightChart.setGridBackgroundColor(R.color.accent);
        weightChart.setPinchZoom(false);
        weightChart.setDrawGridBackground(true);
        weightChart.setHorizontalScrollBarEnabled(true);
        weightChart.setVerticalScrollBarEnabled(false);

        IAxisValueFormatter xAxisFormatterWeight = new DayAxisValueFormatter(weightChart);
        XAxis xAxisWeight = weightChart.getXAxis();

        xAxisWeight.setEnabled(true);
        xAxisWeight.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisWeight.setDrawGridLines(true);
        xAxisWeight.setCenterAxisLabels(false);
        xAxisWeight.setGranularity(1f); // only intervals of 1 day
        xAxisWeight.setLabelCount(7);
        xAxisWeight.setValueFormatter(xAxisFormatterWeight);

        YAxis yAxisxAxisWeight = weightChart.getAxisLeft();
        yAxisxAxisWeight.setEnabled(true);
        yAxisxAxisWeight.setDrawGridLines(true);
        yAxisxAxisWeight.setCenterAxisLabels(true);
//        yAxisxAxisWeight.setAxisMaximum(10);
        yAxisxAxisWeight.setAxisMinimum(10);
        yAxisxAxisWeight.setGranularity(1f); // only intervals of 1 day
//        yAxis.setLabelCount(7);
//        yAxis.setValueFormatter(xAxisFormatter);


        IAxisValueFormatter customWeight = new MyAxisValueFormatter();
        YAxis rightAxisWeight = weightChart.getAxisRight();
        rightAxisWeight.setDrawGridLines(true);
        rightAxisWeight.setCenterAxisLabels(true);
        rightAxisWeight.setLabelCount(8, false);
        rightAxisWeight.setValueFormatter(customWeight);
        rightAxisWeight.setSpaceTop(15f);
        rightAxisWeight.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxisWeight.setEnabled(false);

        Legend lgnWeight = weightChart.getLegend();
        lgnWeight.setEnabled(false);
        lgnWeight.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        lgnWeight.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        lgnWeight.setOrientation(Legend.LegendOrientation.VERTICAL);
        lgnWeight.setDrawInside(false);
        lgnWeight.setForm(Legend.LegendForm.SQUARE);
        lgnWeight.setFormSize(9f);
        lgnWeight.setTextSize(11f);
        lgnWeight.setXEntrySpace(4f);

//        // Symptom Chart

        SymptomChart.setOnChartValueSelectedListener(AggregateChartActivity.this);
        SymptomChart.getDescription().setEnabled(false);
        SymptomChart.setMaxVisibleValueCount(60);

        SymptomChart.setGridBackgroundColor(R.color.accent);
        SymptomChart.setPinchZoom(false);
        SymptomChart.setDrawGridBackground(true);
        SymptomChart.setHorizontalScrollBarEnabled(true);
        SymptomChart.setVerticalScrollBarEnabled(false);

        IAxisValueFormatter xAxisFormatterSymptom = new DayAxisValueFormatter(SymptomChart);
        XAxis xAxisSymptom = SymptomChart.getXAxis();

        xAxisSymptom.setEnabled(true);
        xAxisSymptom.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisSymptom.setDrawGridLines(true);
        xAxisSymptom.setCenterAxisLabels(false);
        xAxisSymptom.setGranularity(1f); // only intervals of 1 day
        xAxisSymptom.setLabelCount(7);
        xAxisSymptom.setValueFormatter(xAxisFormatterSymptom);


        YAxis yAxisSymptom = SymptomChart.getAxisLeft();
        yAxisSymptom.setEnabled(true);
        yAxisSymptom.setDrawGridLines(true);
        yAxisSymptom.setCenterAxisLabels(true);
        yAxisSymptom.setAxisMaximum(6);
        yAxisSymptom.setGranularity(1f); // only intervals of 1 day
//        yAxis.setLabelCount(7);
//        yAxis.setValueFormatter(xAxisFormatter);


        IAxisValueFormatter customSymptom = new MyAxisValueFormatter();
        YAxis rightAxisSymptom = SymptomChart.getAxisRight();
        rightAxisSymptom.setDrawGridLines(true);
        rightAxisSymptom.setCenterAxisLabels(true);
        rightAxisSymptom.setLabelCount(8, false);
        rightAxisSymptom.setValueFormatter(customSymptom);
        rightAxisSymptom.setSpaceTop(15f);
        rightAxisSymptom.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxisSymptom.setEnabled(false);

        Legend lgnSymptom = SymptomChart.getLegend();
        lgnSymptom.setEnabled(false);
        lgnSymptom.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        lgnSymptom.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        lgnSymptom.setOrientation(Legend.LegendOrientation.VERTICAL);
        lgnSymptom.setDrawInside(false);
        lgnSymptom.setForm(Legend.LegendForm.SQUARE);
        lgnSymptom.setFormSize(9f);
        lgnSymptom.setTextSize(11f);
        lgnSymptom.setXEntrySpace(4f);

        // SymptomMood Chart

        symptomMoodChart.setOnChartValueSelectedListener(AggregateChartActivity.this);
        symptomMoodChart.getDescription().setEnabled(false);
        symptomMoodChart.setMaxVisibleValueCount(60);

        symptomMoodChart.setGridBackgroundColor(R.color.accent);
        symptomMoodChart.setPinchZoom(false);
        symptomMoodChart.setDrawGridBackground(true);
        symptomMoodChart.setHorizontalScrollBarEnabled(true);
        symptomMoodChart.setVerticalScrollBarEnabled(false);

        IAxisValueFormatter xAxisSymptomMoodChartFormet = new DayAxisValueFormatter(symptomMoodChart);
        XAxis xAxisSymptomMoodChart = symptomMoodChart.getXAxis();

        xAxisSymptomMoodChart.setEnabled(true);
        xAxisSymptomMoodChart.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisSymptomMoodChart.setDrawGridLines(true);
        xAxisSymptomMoodChart.setCenterAxisLabels(false);
        xAxisSymptomMoodChart.setGranularity(1f); // only intervals of 1 day
        xAxisSymptomMoodChart.setLabelCount(7);
        xAxisSymptomMoodChart.setValueFormatter(xAxisSymptomMoodChartFormet);


        YAxis yAxisSymMoodChart = symptomMoodChart.getAxisLeft();
        yAxisSymMoodChart.setEnabled(true);
        yAxisSymMoodChart.setDrawGridLines(true);
        yAxisSymMoodChart.setCenterAxisLabels(true);
        yAxisSymMoodChart.setAxisMaximum(6);
        yAxisSymMoodChart.setGranularity(1f); // only intervals of 1 day
//        yAxis.setLabelCount(7);
//        yAxis.setValueFormatter(xAxisFormatter);


        IAxisValueFormatter customSymMoodChart = new MyAxisValueFormatter();
        YAxis SymMoodChart = symptomMoodChart.getAxisRight();
        SymMoodChart.setDrawGridLines(true);
        SymMoodChart.setCenterAxisLabels(true);
        SymMoodChart.setLabelCount(8, false);
        SymMoodChart.setValueFormatter(customSymMoodChart);
        SymMoodChart.setSpaceTop(15f);
        SymMoodChart.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        SymMoodChart.setEnabled(false);

        Legend lgnSymMoodChart = symptomMoodChart.getLegend();
        lgnSymMoodChart.setEnabled(false);
        lgnSymMoodChart.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        lgnSymMoodChart.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        lgnSymMoodChart.setOrientation(Legend.LegendOrientation.VERTICAL);
        lgnSymMoodChart.setDrawInside(false);
        lgnSymMoodChart.setForm(Legend.LegendForm.SQUARE);
        lgnSymMoodChart.setFormSize(9f);
        lgnSymMoodChart.setTextSize(11f);
        lgnSymMoodChart.setXEntrySpace(4f);
//
//        // sideEffects Chart

        sideEffectsChart.setOnChartValueSelectedListener(AggregateChartActivity.this);
        sideEffectsChart.getDescription().setEnabled(false);
        sideEffectsChart.setMaxVisibleValueCount(60);

        sideEffectsChart.setPinchZoom(false);
        sideEffectsChart.setDrawGridBackground(true);
        sideEffectsChart.setHorizontalScrollBarEnabled(true);
        sideEffectsChart.setVerticalScrollBarEnabled(false);

        IAxisValueFormatter xAxisFormatterSideEffects = new DayAxisValueFormatter(sideEffectsChart);
        XAxis xAxisSideEffects = sideEffectsChart.getXAxis();

        xAxisSideEffects.setEnabled(true);
        xAxisSideEffects.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisSideEffects.setDrawGridLines(true);
        xAxisSideEffects.setCenterAxisLabels(false);
        xAxisSideEffects.setGranularity(1f); // only intervals of 1 day
        xAxisSideEffects.setLabelCount(7);
        xAxisSideEffects.setValueFormatter(xAxisFormatterSideEffects);


        YAxis yAxisSideEffects = sideEffectsChart.getAxisLeft();
        yAxisSideEffects.setEnabled(true);
        yAxisSideEffects.setDrawGridLines(true);
        yAxisSideEffects.setCenterAxisLabels(true);
        yAxisSideEffects.setAxisMaximum(10);
        yAxisSideEffects.setGranularity(1f); // only intervals of 1 day
//        yAxis.setLabelCount(7);
//        yAxis.setValueFormatter(xAxisFormatter);


        IAxisValueFormatter customSideEffects = new MyAxisValueFormatter();
        YAxis rightAxisSideEffects = sideEffectsChart.getAxisRight();
        rightAxisSideEffects.setDrawGridLines(true);
        rightAxisSideEffects.setCenterAxisLabels(true);
        rightAxisSideEffects.setLabelCount(8, false);
        rightAxisSideEffects.setValueFormatter(customSideEffects);
        rightAxisSideEffects.setSpaceTop(15f);
        rightAxisSideEffects.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxisSideEffects.setEnabled(false);

        Legend lgnSideEffects = sideEffectsChart.getLegend();
        lgnSideEffects.setEnabled(false);
        lgnSideEffects.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        lgnSideEffects.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        lgnSideEffects.setOrientation(Legend.LegendOrientation.VERTICAL);
        lgnSideEffects.setDrawInside(false);
        lgnSideEffects.setForm(Legend.LegendForm.SQUARE);
        lgnSideEffects.setFormSize(9f);
        lgnSideEffects.setTextSize(11f);
        lgnSideEffects.setXEntrySpace(4f);
    }

    private void initChart() {
        yVals.clear();

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

        mLifeScallingChart.setOnChartValueSelectedListener(this);
        mLifeScallingChart.getDescription().setEnabled(false);
        mLifeScallingChart.setMaxVisibleValueCount(60);

        mLifeScallingChart.setPinchZoom(false);
        mLifeScallingChart.setDrawGridBackground(false);
        mLifeScallingChart.setHorizontalScrollBarEnabled(true);
        mLifeScallingChart.setVerticalScrollBarEnabled(false);

        XAxis xAxis = mLifeScallingChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true); //
        xAxis.setDrawAxisLine(true); //

        xAxis.setGranularity(1f); // only intervals of 1 da
        xAxis.setLabelCount(7);
        xAxis.setCenterAxisLabels(false);

        final int finalK = k;


        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, -1 * (finalK - ((int) value)));
//                dateArray.add(cal.getTime());
//                Debug.e("date lable : ", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM"));
                return Utils.parseTime(cal.getTimeInMillis(), "dd MMM");
            }
        });


//        IAxisValueFormatter custom = new MyAxisValueFormatter();
        YAxis leftAxis = mLifeScallingChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
//        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setCenterAxisLabels(true);
        leftAxis.setGranularity(1f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawAxisLine(true);

        YAxis rightAxis = mLifeScallingChart.getAxisRight();
        rightAxis.setEnabled(false);

        Legend l = mLifeScallingChart.getLegend();
        l.setEnabled(false);
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

    List<Float> yVals = new ArrayList<>();

    class MyBarData {
        public List<Entry> barEntries = new ArrayList<>();
        public int color;

        public MyBarData(List<Entry> barEntries, int color) {
            this.barEntries = barEntries;
            this.color = color;
        }
    }

    public static final String SOCIAL_LIFE = "Social Life";
    public static final String WORK_LIFE = "Work Life";
    public static final String FAMILY_LIFE = "Family Life";

    String startDate;

    private void setBarChartData(String label, int finalK) {

        HashSet<String> selectedAll = new HashSet<>();
        selectedAll.add(SOCIAL_LIFE);
        selectedAll.add(WORK_LIFE);
        selectedAll.add(FAMILY_LIFE);

        final List<MyBarData> valueData = new ArrayList<MyBarData>();

        for (int i = 0; i < valueData.size(); i++) {
//            Debug.e("valueData", "" + valueData);
        }

        Iterator<String> iterator = selectedAll.iterator();

//        Debug.e("iterator", "" + iterator);

        while (iterator.hasNext()) {

            String userId = iterator.next();

            if (hm.containsKey(userId) && hm.get(userId).size() > 0) {
                List<Float> temp = new ArrayList<>();

                for (int i = 0; i < finalK; i++) {
                    Calendar cal = Calendar.getInstance();
//                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    cal.add(Calendar.DAY_OF_MONTH, -1 * (finalK - i));

                    if (i == 0) {
                        startDate = Utils.parseTime(cal.getTimeInMillis(), "MM/dd/yyyy"); //MM/dd/yyyy
                    }

                    boolean isFound = false;
                    for (int k = 0; k < hm.get(userId).size(); k++) {
                        List<List<String>> medsTaken = hm.get(userId);
                        String time = medsTaken.get(k).get(0);

                        String first = time.substring(0, 1);
                        if (!first.equalsIgnoreCase("-")) {

//                            Debug.e("date-->>", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy") + " = " + Utils.parseTime(Utils.parseTimeUTCtoDefault(1 * Long.valueOf("" + time)).getTime(), "dd MMM yyyy"));
//                            if (Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy").
//                                    equalsIgnoreCase(Utils.parseTime(Utils.parseTimeUTCtoDefault(1 * Long.valueOf("" + time)).getTime(), "dd MMM yyyy"))) {
//
//                                isFound = true;
//                                temp.add(Float.valueOf("" + medsTaken.get(k).get(1)));
//                                break;
//                            }

                            if (Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy").
                                    equalsIgnoreCase(Utils.parseTime(Long.valueOf(medsTaken.get(k).get(0)), "dd MMM yyyy"))) {

                                Debug.e("date lable : ", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM") + " " + medsTaken.get(k).get(1));
//                                Debug.e("date-->>", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy") + " = " + Utils.parseTime(Long.valueOf(medsTaken.get(k).get(0)), "dd MMM yyyy"));

                                isFound = true;
                                temp.add(Float.valueOf("" + medsTaken.get(k).get(1)));
                                break;
                            }
                        }
                    }
//
                    if (!isFound) {
                        temp.add(Float.valueOf("0"));
                    }
                }

                List<Entry> barEntries = new ArrayList<>();
                float start = 1f;
                for (int l = (int) start; l <= temp.size(); l++) {
                    Entry barEntry = new Entry(l, temp.get(l - 1));
                    barEntries.add(barEntry);

                }

//                Debug.e("data", "" + barEntries);

//                ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
//                int color2 = generator.getColor("" + userId);
//                int color = getResources().getColor(R.color.theme_red);

//                Debug.e("userId", "" + userId + " :  " + "color2" + "" + color2);

                if (userId.equalsIgnoreCase(SOCIAL_LIFE)) {
                    int color = getResources().getColor(R.color.theme_red);
                    valueData.add(new MyBarData(barEntries, color));
                }
                if (userId.equalsIgnoreCase(FAMILY_LIFE)) {
                    int color = getResources().getColor(R.color.theme_blue);
                    valueData.add(new MyBarData(barEntries, color));
                }
                if (userId.equalsIgnoreCase(WORK_LIFE)) {
                    int color = getResources().getColor(R.color.theme_green);
                    valueData.add(new MyBarData(barEntries, color));
                }
            }
//            Debug.e("setData", "" + valueData.size());
        }

        BarDataSet set, set1, set2, set3;


        if (mLifeScallingChart.getData() != null && mLifeScallingChart.getData().getDataSetCount() > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLifeScallingChart.clearValues();

                    List<ILineDataSet> barDataSets = new ArrayList<>();
                    for (int i = 0; i < valueData.size(); i++) {
                        LineDataSet dataSet = new LineDataSet(valueData.get(i).barEntries, "worklife");
                        dataSet.setColor(valueData.get(i).color);
                        barDataSets.add(dataSet);
                    }

                    LineData data = new LineData(barDataSets);
                    data.setValueFormatter(new LargeValueFormatter());
                    mLifeScallingChart.setData(data);


                    mLifeScallingChart.getData().notifyDataChanged();
                    mLifeScallingChart.notifyDataSetChanged();
                }
            });

        } else {

            List<ILineDataSet> barDataSets = new ArrayList<>();
            for (int i = 0; i < valueData.size(); i++) {
                LineDataSet dataSet = new LineDataSet(valueData.get(i).barEntries, "");
                dataSet.setColor(valueData.get(i).color);
                dataSet.setCircleColor(valueData.get(i).color);
                barDataSets.add(dataSet);
            }

//            Debug.e("barDataSets", "" + barDataSets.size());

            LineData data = new LineData(barDataSets);
            data.setValueFormatter(new LargeValueFormatter());
            mLifeScallingChart.setData(data);
//        }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLifeScallingChart.getData().notifyDataChanged(); // let the data know a dataSet changed
                    mLifeScallingChart.notifyDataSetChanged(); // let the chart know it's data changed
                    mLifeScallingChart.invalidate(); // refresh
                }
            });

        }
    }

    private void initMoodChart() {
        yValsMood.clear();

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

        moodChart.setOnChartValueSelectedListener(this);
        moodChart.getDescription().setEnabled(false);
        moodChart.setMaxVisibleValueCount(60);

        moodChart.setPinchZoom(false);
        moodChart.setDrawGridBackground(false);
        moodChart.setHorizontalScrollBarEnabled(true);
        moodChart.setVerticalScrollBarEnabled(false);

        XAxis xAxis = moodChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);
//        xAxis.setAxisMinimum(0);
        xAxis.setGranularity(1f); // only intervals of 1 da
        xAxis.setLabelCount(7);
        xAxis.setCenterAxisLabels(false);

        final int finalK = k;


        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, -1 * (finalK - ((int) value)));
//                dateArray.add(cal.getTime());
//                Debug.e("date lable : ", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM"));
                return Utils.parseTime(cal.getTimeInMillis(), "dd MMM");
            }
        });

        YAxis leftAxis = moodChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setCenterAxisLabels(true);
        leftAxis.setGranularity(1f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawAxisLine(true);


//        IAxisValueFormatter customMood = new MyAxisValueFormatter();
        YAxis rightAxisMood = moodChart.getAxisRight();
        rightAxisMood.setEnabled(false);

//        rightAxisMood.setLabelCount(8, false);
//        rightAxisMood.setValueFormatter(customMood);
//        rightAxisMood.setSpaceTop(15f);
//        rightAxisMood.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        rightAxisMood.setEnabled(false);

        Legend lgdMood = moodChart.getLegend();
        lgdMood.setEnabled(false);
        lgdMood.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        lgdMood.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        lgdMood.setOrientation(Legend.LegendOrientation.VERTICAL);
        lgdMood.setDrawInside(false);
        lgdMood.setForm(Legend.LegendForm.SQUARE);
        lgdMood.setFormSize(9f);
        lgdMood.setTextSize(11f);
        lgdMood.setXEntrySpace(4f);

        setBarChartMoodData("", finalK);
    }

    List<Float> yValsMood = new ArrayList<>();

    class MyBarDataMood {
        public List<Entry> barEntries = new ArrayList<>();
        public int color;

        public MyBarDataMood(List<Entry> barEntries, int color) {
            this.barEntries = barEntries;
            this.color = color;
        }
    }

    public static final String EVERYONE = "everyone";
    public static final String ME = "me";

    //    String startDate;
    private void setBarChartMoodData(String label, int finalK) {

        HashSet<String> selectedAll = new HashSet<>();
        selectedAll.add(EVERYONE);
        selectedAll.add(ME);

        final List<MyBarDataMood> valueData = new ArrayList<MyBarDataMood>();

        for (int i = 0; i < valueData.size(); i++) {
//            Debug.e("valueData", "" + valueData);
        }

        Iterator<String> iterator = selectedAll.iterator();

        while (iterator.hasNext()) {

            String userId = iterator.next();

            if (hm.containsKey(userId) && hm.get(userId).size() > 0) {
                List<Float> temp = new ArrayList<>();

                for (int i = 0; i < finalK; i++) {
                    Calendar cal = Calendar.getInstance();
//                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    cal.add(Calendar.DAY_OF_MONTH, -1 * (finalK - i));

                    if (i == 0) {
                        startDate = Utils.parseTime(cal.getTimeInMillis(), "MM/dd/yyyy"); //MM/dd/yyyy
                    }

                    boolean isFound = false;
                    for (int k = 0; k < hm.get(userId).size(); k++) {
                        List<List<String>> medsTaken = hm.get(userId);
                        String time = medsTaken.get(k).get(0);

                        String first = time.substring(0, 1);
                        if (!first.equalsIgnoreCase("-")) {

                      if (Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy").
                            equalsIgnoreCase(Utils.parseTime(Long.valueOf(medsTaken.get(k).get(0)), "dd MMM yyyy"))) {

                                Debug.e("date lable : ", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM") + " " + medsTaken.get(k).get(1));
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

                List<Entry> barEntries = new ArrayList<>();
                float start = 1f;
                for (int l = (int) start; l <= temp.size(); l++) {
                    Entry barEntry = new Entry(l, temp.get(l - 1));
                    barEntries.add(barEntry);

                }

//                Debug.e("data", "" + barEntries);

//                ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
//                int color2 = generator.getColor("" + userId);
//                valueData.add(new MyBarDataMood(barEntries, color2));

                if (userId.equalsIgnoreCase(EVERYONE)) {
                    int color = getResources().getColor(R.color.theme_blue);
                    valueData.add(new MyBarDataMood(barEntries, color));
                }
                if (userId.equalsIgnoreCase(ME)) {
                    int color = getResources().getColor(R.color.theme_green);
                    valueData.add(new MyBarDataMood(barEntries, color));
                }
            }
//            Debug.e("setData", "" + valueData.size());
        }

        BarDataSet set, set1, set2, set3;


        if (moodChart.getData() != null && moodChart.getData().getDataSetCount() > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    moodChart.clearValues();

                    List<ILineDataSet> barDataSets = new ArrayList<>();
                    for (int i = 0; i < valueData.size(); i++) {
                        LineDataSet dataSet = new LineDataSet(valueData.get(i).barEntries, "worklife");
                        dataSet.setColor(valueData.get(i).color);
                        barDataSets.add(dataSet);
                    }

                    LineData data = new LineData(barDataSets);
                    data.setValueFormatter(new LargeValueFormatter());
                    moodChart.setData(data);


                    moodChart.getData().notifyDataChanged();
                    moodChart.notifyDataSetChanged();
                }
            });


        } else {

            List<ILineDataSet> barDataSets = new ArrayList<>();
            for (int i = 0; i < valueData.size(); i++) {
                LineDataSet dataSet = new LineDataSet(valueData.get(i).barEntries, "");
                dataSet.setColor(valueData.get(i).color);
                dataSet.setCircleColor(valueData.get(i).color);
                barDataSets.add(dataSet);
            }

//            Debug.e("barDataSets", "" + barDataSets.size());

            LineData data = new LineData(barDataSets);
            data.setValueFormatter(new LargeValueFormatter());
            moodChart.setData(data);
//        }


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    moodChart.getData().notifyDataChanged(); // let the data know a dataSet changed
                    moodChart.notifyDataSetChanged(); // let the chart know it's data changed
                    moodChart.invalidate(); // refresh
                }
            });
        }
    }

    private void initWeightChart() {
        yValsWeight.clear();

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

        weightChart.setOnChartValueSelectedListener(this);
        weightChart.getDescription().setEnabled(false);
        weightChart.setMaxVisibleValueCount(60);

        weightChart.setPinchZoom(false);
        weightChart.setDrawGridBackground(false);
        weightChart.setHorizontalScrollBarEnabled(true);
        weightChart.setVerticalScrollBarEnabled(false);

        XAxis xAxis = weightChart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setGranularity(1f); // only intervals of 1 da
        xAxis.setLabelCount(7);
        xAxis.setCenterAxisLabels(false);

        final int finalK = k;


        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, -1 * (finalK - ((int) value)));
//                dateArray.add(cal.getTime());
//                Debug.e("date lable : ", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM"));
                return Utils.parseTime(cal.getTimeInMillis(), "dd MMM");
            }
        });

//        IAxisValueFormatter customWeight = new MyAxisValueFormatter();
        YAxis rightAxisWeight = weightChart.getAxisRight();
        rightAxisWeight.setEnabled(false);

        YAxis leftAxis = weightChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setCenterAxisLabels(true);
        leftAxis.setGranularity(1f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawAxisLine(true);

        Legend lgdWeight = weightChart.getLegend();
        lgdWeight.setEnabled(false);
        lgdWeight.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        lgdWeight.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        lgdWeight.setOrientation(Legend.LegendOrientation.VERTICAL);
        lgdWeight.setDrawInside(false);
        lgdWeight.setForm(Legend.LegendForm.SQUARE);
        lgdWeight.setFormSize(9f);
        lgdWeight.setTextSize(11f);
        lgdWeight.setXEntrySpace(4f);

        setWeightData("", finalK);
    }

    List<Float> yValsWeight = new ArrayList<>();

    class MyDataWeight {
        public List<Entry> barEntries = new ArrayList<>();
        public int color;

        public MyDataWeight(List<Entry> barEntries, int color) {
            this.barEntries = barEntries;
            this.color = color;
        }
    }

    public static final String WEIGHT = "weight";

    //    String startDate;
    private void setWeightData(String label, int finalK) {

        HashSet<String> selectedAll = new HashSet<>();
        selectedAll.add(WEIGHT);

        final List<MyDataWeight> valueData = new ArrayList<MyDataWeight>();

        for (int i = 0; i < valueData.size(); i++) {
//            Debug.e("valueData", "" + valueData);
        }

        Iterator<String> iterator = selectedAll.iterator();

//        Debug.e("iterator", "" + iterator);

        while (iterator.hasNext()) {

            String userId = iterator.next();
//            Debug.e("Selected userId", "" + userId);

            if (hm.containsKey(userId) && hm.get(userId).size() > 0) {
                List<Float> temp = new ArrayList<>();

                for (int i = 0; i < finalK; i++) {
                    Calendar cal = Calendar.getInstance();
//                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    cal.add(Calendar.DAY_OF_MONTH, -1 * (finalK - i));

                    if (i == 0) {
                        startDate = Utils.parseTime(cal.getTimeInMillis(), "MM/dd/yyyy"); //MM/dd/yyyy
//                        Debug.e("first time-->>", "" + Utils.parseTime(cal.getTimeInMillis(), "MM/dd/yyyy"));
                    }

                    boolean isFound = false;
                    for (int k = 0; k < hm.get(userId).size(); k++) {
                        List<List<String>> medsTaken = hm.get(userId);
                        String time = medsTaken.get(k).get(0);

//                        Debug.e("time-->>", "" + time);
//                        Debug.e("date-->>", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy") + " = " + Utils.parseTime(Utils.parseTimeUTCtoDefault(1 * Long.valueOf("" + time)).getTime(), "dd MMM yyyy"));

                        String first = time.substring(0, 1);
                        if (!first.equalsIgnoreCase("-")) {

                          if (Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy").
                            equalsIgnoreCase(Utils.parseTime(Long.valueOf(medsTaken.get(k).get(0)), "dd MMM yyyy"))) {

                                Debug.e("date lable : ", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM") + " " + medsTaken.get(k).get(1));
//                                Debug.e("date-->>", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy") + " = " + Utils.parseTime(Long.valueOf(medsTaken.get(k).get(0)), "dd MMM yyyy"));

                                isFound = true;
                                temp.add(Float.valueOf("" + medsTaken.get(k).get(1)));
                                break;
                            }
                        }
                    }
//
                    if (!isFound) {
                        temp.add(Float.valueOf("0"));
                    }
                }

//                Debug.e("temp", "" + temp);

                List<Entry> barEntries = new ArrayList<>();
                float start = 1f;
                for (int l = (int) start; l <= temp.size(); l++) {
                    Entry barEntry = new Entry(l, temp.get(l - 1));
                    barEntries.add(barEntry);
                }

//                Debug.e("data", "" + barEntries);

//                ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
//                int color2 = generator.getColor("" + userId);
                int color = getResources().getColor(R.color.theme_green);
                valueData.add(new MyDataWeight(barEntries, color));

            }
//            Debug.e("setData", "" + valueData.size());
        }

        BarDataSet set, set1, set2, set3;


        if (weightChart.getData() != null && weightChart.getData().getDataSetCount() > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    weightChart.clearValues();

                    List<ILineDataSet> barDataSets = new ArrayList<>();
                    for (int i = 0; i < valueData.size(); i++) {
                        LineDataSet dataSet = new LineDataSet(valueData.get(i).barEntries, "worklife");
                        dataSet.setColor(valueData.get(i).color);
                        barDataSets.add(dataSet);
                    }

                    LineData data = new LineData(barDataSets);
                    data.setValueFormatter(new LargeValueFormatter());
                    weightChart.setData(data);


                    weightChart.getData().notifyDataChanged();
                    weightChart.notifyDataSetChanged();
                }
            });
        } else {

            List<ILineDataSet> barDataSets = new ArrayList<>();
            for (int i = 0; i < valueData.size(); i++) {
                LineDataSet dataSet = new LineDataSet(valueData.get(i).barEntries, "");
                dataSet.setColor(valueData.get(i).color);
                dataSet.setCircleColor(valueData.get(i).color);
                barDataSets.add(dataSet);
            }

//            Debug.e("barDataSets", "" + barDataSets.size());

            LineData data = new LineData(barDataSets);
            data.setValueFormatter(new LargeValueFormatter());
            weightChart.setData(data);
//        }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    weightChart.getData().notifyDataChanged(); // let the data know a dataSet changed
                    weightChart.notifyDataSetChanged(); // let the chart know it's data changed
                    weightChart.invalidate(); // refresh
                }
            });

        }
    }

    private void initSymptomChart() {
        yValsSymptom.clear();

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

        SymptomChart.setOnChartValueSelectedListener(this);
        SymptomChart.getDescription().setEnabled(false);
        SymptomChart.setMaxVisibleValueCount(60);

        SymptomChart.setPinchZoom(false);
        SymptomChart.setDrawGridBackground(false);
        SymptomChart.setHorizontalScrollBarEnabled(true);
        SymptomChart.setVerticalScrollBarEnabled(false);

        XAxis xAxis = SymptomChart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setGranularity(1f); // only intervals of 1 da
        xAxis.setLabelCount(7);
        xAxis.setCenterAxisLabels(false);

        final int finalK = k;


        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, -1 * (finalK - ((int) value)));
//                dateArray.add(cal.getTime());
//                Debug.e("date lable : ", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM"));
                return Utils.parseTime(cal.getTimeInMillis(), "dd MMM");
            }
        });


//        IAxisValueFormatter customSymptom = new MyAxisValueFormatter();
        YAxis rightAxisSymptom = SymptomChart.getAxisRight();
        rightAxisSymptom.setEnabled(false);

//        IAxisValueFormatter custom = new MyAxisValueFormatter();
        YAxis leftAxis = SymptomChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
//        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setCenterAxisLabels(true);
        leftAxis.setGranularity(1f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawAxisLine(true);

        Legend lgdSymptom = SymptomChart.getLegend();
        lgdSymptom.setEnabled(false);
        lgdSymptom.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        lgdSymptom.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        lgdSymptom.setOrientation(Legend.LegendOrientation.VERTICAL);
        lgdSymptom.setDrawInside(false);
        lgdSymptom.setForm(Legend.LegendForm.SQUARE);
        lgdSymptom.setFormSize(9f);
        lgdSymptom.setTextSize(11f);
        lgdSymptom.setXEntrySpace(4f);

        setSymptomData("", finalK);
    }

    List<Float> yValsSymptom = new ArrayList<>();

    class MySymptomData {
        public List<Entry> barEntries = new ArrayList<>();
        public int color;

        public MySymptomData(List<Entry> barEntries, int color) {
            this.barEntries = barEntries;
            this.color = color;
        }
    }

    public static final String APPETITE = "Appetite";
    public static final String INITIATIVE = "Initiative";
    public static final String PASSIMISM = "Passimism";
    public static final String JOY = "joy";

    //    String startDate;
    private void setSymptomData(String label, int finalK) {

        HashSet<String> selectedAll = new HashSet<>();
        selectedAll.add(APPETITE);
        selectedAll.add(INITIATIVE);
        selectedAll.add(PASSIMISM);
        selectedAll.add(JOY);

        final List<MySymptomData> valueData = new ArrayList<MySymptomData>();

        for (int i = 0; i < valueData.size(); i++) {
//            Debug.e("valueData", "" + valueData);
        }

        Iterator<String> iterator = selectedAll.iterator();

//        Debug.e("iterator", "" + iterator);

        while (iterator.hasNext()) {

            String userId = iterator.next();
//            Debug.e("Selected userId", "" + userId);

            if (hm.containsKey(userId) && hm.get(userId).size() > 0) {
                List<Float> temp = new ArrayList<>();

                for (int i = 0; i < finalK; i++) {
                    Calendar cal = Calendar.getInstance();
//                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    cal.add(Calendar.DAY_OF_MONTH, -1 * (finalK - i));

                    if (i == 0) {
                        startDate = Utils.parseTime(cal.getTimeInMillis(), "MM/dd/yyyy"); //MM/dd/yyyy
//                        Debug.e("first time-->>", "" + Utils.parseTime(cal.getTimeInMillis(), "MM/dd/yyyy"));
                    }

                    boolean isFound = false;
                    for (int k = 0; k < hm.get(userId).size(); k++) {
                        List<List<String>> medsTaken = hm.get(userId);
                        String time = medsTaken.get(k).get(0);

//                        Debug.e("time-->>", "" + time);
//                Debug.e("date-->>", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy") + " = " + Utils.parseTime(Utils.parseTimeUTCtoDefault(1 * Long.valueOf("" + time)).getTime(), "dd MMM yyyy"));

                        String first = time.substring(0, 1);
                        if (!first.equalsIgnoreCase("-")) {

                            if (Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy").
                                    equalsIgnoreCase(Utils.parseTime(Long.valueOf(medsTaken.get(k).get(0)), "dd MMM yyyy"))) {

                                Debug.e("date lable : ", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM") + " " + medsTaken.get(k).get(1));
//                                Debug.e("date-->>", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy") + " = " + Utils.parseTime(Long.valueOf(medsTaken.get(k).get(0)), "dd MMM yyyy"));

                                isFound = true;
                                temp.add(Float.valueOf("" + medsTaken.get(k).get(1)));
                                break;
                            }
                        }
                    }
//
                    if (!isFound) {
                        temp.add(Float.valueOf("0"));
                    }
                }

//                Debug.e("temp", "" + temp);

                List<Entry> barEntries = new ArrayList<>();
                float start = 1f;
                for (int l = (int) start; l <= temp.size(); l++) {
                    Entry barEntry = new Entry(l, temp.get(l - 1));
                    barEntries.add(barEntry);

                }

//                Debug.e("data", "" + barEntries);

//                ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
//                int color2 = generator.getColor("" + userId);
//                valueData.add(new MySymptomData(barEntries, color2));

                if (userId.equalsIgnoreCase(APPETITE)) {
                    int color = getResources().getColor(R.color.theme_red);
                    valueData.add(new MySymptomData(barEntries, color));
                }
                if (userId.equalsIgnoreCase(INITIATIVE)) {
                    int color = getResources().getColor(R.color.theme_blue);
                    valueData.add(new MySymptomData(barEntries, color));
                }
                if (userId.equalsIgnoreCase(PASSIMISM)) {
                    int color = getResources().getColor(R.color.theme_green);
                    valueData.add(new MySymptomData(barEntries, color));
                }
                if (userId.equalsIgnoreCase(JOY)) {
                    int color = getResources().getColor(R.color.theme_orange);
                    valueData.add(new MySymptomData(barEntries, color));
                }
            }
//            Debug.e("setData", "" + valueData.size());
        }

        BarDataSet set, set1, set2, set3;


        if (SymptomChart.getData() != null && SymptomChart.getData().getDataSetCount() > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SymptomChart.clearValues();

                    List<ILineDataSet> barDataSets = new ArrayList<>();
                    for (int i = 0; i < valueData.size(); i++) {
                        LineDataSet dataSet = new LineDataSet(valueData.get(i).barEntries, "worklife");
                        dataSet.setColor(valueData.get(i).color);
                        barDataSets.add(dataSet);
                    }

                    LineData data = new LineData(barDataSets);
                    data.setValueFormatter(new LargeValueFormatter());
                    SymptomChart.setData(data);

                    SymptomChart.getData().notifyDataChanged();
                    SymptomChart.notifyDataSetChanged();
                }
            });
        } else {

            List<ILineDataSet> barDataSets = new ArrayList<>();
            for (int i = 0; i < valueData.size(); i++) {
                LineDataSet dataSet = new LineDataSet(valueData.get(i).barEntries, "");
                dataSet.setColor(valueData.get(i).color);
                dataSet.setCircleColor(valueData.get(i).color);
                barDataSets.add(dataSet);
            }

//            Debug.e("barDataSets", "" + barDataSets.size());

            LineData data = new LineData(barDataSets);
            data.setValueFormatter(new LargeValueFormatter());
            SymptomChart.setData(data);
//        }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SymptomChart.getData().notifyDataChanged(); // let the data know a dataSet changed
                    SymptomChart.notifyDataSetChanged(); // let the chart know it's data changed
                    SymptomChart.invalidate(); // refresh
                }
            });
        }
    }

    private void initSymptomMoodChart() {
        yValsSymptomMood.clear();

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

        symptomMoodChart.setOnChartValueSelectedListener(this);
        symptomMoodChart.getDescription().setEnabled(false);
        symptomMoodChart.setMaxVisibleValueCount(60);

        symptomMoodChart.setPinchZoom(false);
        symptomMoodChart.setDrawGridBackground(false);
        symptomMoodChart.setHorizontalScrollBarEnabled(true);
        symptomMoodChart.setVerticalScrollBarEnabled(false);

        XAxis xAxis = symptomMoodChart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setGranularity(1f); // only intervals of 1 da
        xAxis.setLabelCount(7);
        xAxis.setCenterAxisLabels(false);

        final int finalK = k;

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, -1 * (finalK - ((int) value)));
//                dateArray.add(cal.getTime());
//                Debug.e("date lable : ", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM"));
                return Utils.parseTime(cal.getTimeInMillis(), "dd MMM");
            }
        });

//        IAxisValueFormatter customSymptomMood = new MyAxisValueFormatter();
        YAxis rightAxisSymptomMood = symptomMoodChart.getAxisRight();
        rightAxisSymptomMood.setEnabled(false);

//        IAxisValueFormatter custom = new MyAxisValueFormatter();
        YAxis leftAxis = symptomMoodChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
//        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setCenterAxisLabels(true);
        leftAxis.setGranularity(1f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawAxisLine(true);

        Legend lgdSymptomMood = symptomMoodChart.getLegend();
        lgdSymptomMood.setEnabled(false);
        lgdSymptomMood.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        lgdSymptomMood.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        lgdSymptomMood.setOrientation(Legend.LegendOrientation.VERTICAL);
        lgdSymptomMood.setDrawInside(false);
        lgdSymptomMood.setForm(Legend.LegendForm.SQUARE);
        lgdSymptomMood.setFormSize(9f);
        lgdSymptomMood.setTextSize(11f);
        lgdSymptomMood.setXEntrySpace(4f);


        setSymptomMoodData("", finalK);
    }

    List<Float> yValsSymptomMood = new ArrayList<>();

    class MySymptomMoodData {
        public List<Entry> barEntries = new ArrayList<>();
        public int color;

        public MySymptomMoodData(List<Entry> barEntries, int color) {
            this.barEntries = barEntries;
            this.color = color;
        }
    }

    public static final String ANXIETY = "Anxiety";
    public static final String SLEEP = "Sleep";
    public static final String MOOD = "Mood";
    public static final String ENERGY = "Energy";
    public static final String CONCENTRATION = "Concentration";

    //    String startDate;
    private void setSymptomMoodData(String label, int finalK) {

        HashSet<String> selectedAll = new HashSet<>();
        selectedAll.add(ANXIETY);
        selectedAll.add(SLEEP);
        selectedAll.add(MOOD);
        selectedAll.add(ENERGY);
        selectedAll.add(CONCENTRATION);

        final List<MySymptomMoodData> valueData = new ArrayList<MySymptomMoodData>();

        for (int i = 0; i < valueData.size(); i++) {
//            Debug.e("valueData", "" + valueData);
        }

        Iterator<String> iterator = selectedAll.iterator();

//        Debug.e("iterator", "" + iterator);

        while (iterator.hasNext()) {

            String userId = iterator.next();
            Debug.e("Selected userId", "" + userId);

            if (hm.containsKey(userId) && hm.get(userId).size() > 0) {
                List<Float> temp = new ArrayList<>();

                for (int i = 0; i < finalK; i++) {
                    Calendar cal = Calendar.getInstance();
//                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    cal.add(Calendar.DAY_OF_MONTH, -1 * (finalK - i));

                    if (i == 0) {
                        startDate = Utils.parseTime(cal.getTimeInMillis(), "MM/dd/yyyy"); //MM/dd/yyyy
                    }

                    boolean isFound = false;
                    for (int k = 0; k < hm.get(userId).size(); k++) {
                        List<List<String>> medsTaken = hm.get(userId);
                        String time = medsTaken.get(k).get(0);

//                        Debug.e("time-->>", "" + time);
//                Debug.e("date-->>", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy") + " = " + Utils.parseTime(Utils.parseTimeUTCtoDefault(1 * Long.valueOf("" + time)).getTime(), "dd MMM yyyy"));

                        String first = time.substring(0, 1);
                        if (!first.equalsIgnoreCase("-")) {

                            if (Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy").
                                    equalsIgnoreCase(Utils.parseTime(Long.valueOf(medsTaken.get(k).get(0)), "dd MMM yyyy"))) {

                                Debug.e("date lable : ", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM") + " " + medsTaken.get(k).get(1));
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

//                Debug.e("temp", "" + temp);

                List<Entry> barEntries = new ArrayList<>();
                float start = 1f;
                for (int l = (int) start; l <= temp.size(); l++) {
                    Entry barEntry = new Entry(l, temp.get(l - 1));
                    barEntries.add(barEntry);

                }

//                Debug.e("data", "" + barEntries);

//                ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
//                int color2 = generator.getColor("" + userId);
//                valueData.add(new MySymptomMoodData(barEntries, color2));

                if (userId.equalsIgnoreCase(ANXIETY)) {
                    int color = getResources().getColor(R.color.theme_red);
                    valueData.add(new MySymptomMoodData(barEntries, color));
                }
                if (userId.equalsIgnoreCase(SLEEP)) {
                    int color = getResources().getColor(R.color.theme_blue);
                    valueData.add(new MySymptomMoodData(barEntries, color));
                }
                if (userId.equalsIgnoreCase(MOOD)) {
                    int color = getResources().getColor(R.color.theme_red_color);
                    valueData.add(new MySymptomMoodData(barEntries, color));
                }
                if (userId.equalsIgnoreCase(ENERGY)) {
                    int color = getResources().getColor(R.color.theme_green);
                    valueData.add(new MySymptomMoodData(barEntries, color));
                }
                if (userId.equalsIgnoreCase(CONCENTRATION)) {
                    int color = getResources().getColor(R.color.theme_orange);
                    valueData.add(new MySymptomMoodData(barEntries, color));
                }
            }
//            Debug.e("setData", "" + valueData.size());
        }

        BarDataSet set, set1, set2, set3;


        if (symptomMoodChart.getData() != null && symptomMoodChart.getData().getDataSetCount() > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    symptomMoodChart.clearValues();

                    List<ILineDataSet> barDataSets = new ArrayList<>();
                    for (int i = 0; i < valueData.size(); i++) {
                        LineDataSet dataSet = new LineDataSet(valueData.get(i).barEntries, "worklife");
                        dataSet.setColor(valueData.get(i).color);
                        barDataSets.add(dataSet);
                    }

                    LineData data = new LineData(barDataSets);
                    data.setValueFormatter(new LargeValueFormatter());
                    symptomMoodChart.setData(data);


                    symptomMoodChart.getData().notifyDataChanged();
                    symptomMoodChart.notifyDataSetChanged();
                }
            });

        } else {

            List<ILineDataSet> barDataSets = new ArrayList<>();
            for (int i = 0; i < valueData.size(); i++) {
                LineDataSet dataSet = new LineDataSet(valueData.get(i).barEntries, "");
                dataSet.setColor(valueData.get(i).color);
                dataSet.setCircleColor(valueData.get(i).color);
                barDataSets.add(dataSet);
            }

//            Debug.e("barDataSets", "" + barDataSets.size());

            LineData data = new LineData(barDataSets);
            data.setValueFormatter(new LargeValueFormatter());
            symptomMoodChart.setData(data);
//        }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    symptomMoodChart.getData().notifyDataChanged(); // let the data know a dataSet changed
                    symptomMoodChart.notifyDataSetChanged(); // let the chart know it's data changed
                    symptomMoodChart.invalidate(); // refresh
                }
            });

        }
    }

    private void initsideEffectsChart() {
        yValsSideEffects.clear();

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

        sideEffectsChart.setOnChartValueSelectedListener(this);
        sideEffectsChart.getDescription().setEnabled(false);
        sideEffectsChart.setMaxVisibleValueCount(60);

        sideEffectsChart.setPinchZoom(false);
        sideEffectsChart.setDrawGridBackground(false);
        sideEffectsChart.setHorizontalScrollBarEnabled(true);
        sideEffectsChart.setVerticalScrollBarEnabled(false);

        XAxis xAxis = sideEffectsChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 da
        xAxis.setLabelCount(7);
        xAxis.setCenterAxisLabels(false);

        final int finalK = k;


        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, -1 * (finalK - ((int) value)));
//                dateArray.add(cal.getTime());
//                Debug.e("date lable : ", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM"));
                return Utils.parseTime(cal.getTimeInMillis(), "dd MMM");
            }
        });


//        IAxisValueFormatter customSideEffects = new MyAxisValueFormatter();
        YAxis rightAxisSideEffects = sideEffectsChart.getAxisRight();
        rightAxisSideEffects.setEnabled(false);

//        IAxisValueFormatter custom = new MyAxisValueFormatter();
        YAxis leftAxis = sideEffectsChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
//        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setCenterAxisLabels(true);
        leftAxis.setGranularity(1f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawAxisLine(true);

        Legend lgdSideEffects = sideEffectsChart.getLegend();
        lgdSideEffects.setEnabled(false);
        lgdSideEffects.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        lgdSideEffects.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        lgdSideEffects.setOrientation(Legend.LegendOrientation.VERTICAL);
        lgdSideEffects.setDrawInside(false);
        lgdSideEffects.setForm(Legend.LegendForm.SQUARE);
        lgdSideEffects.setFormSize(9f);
        lgdSideEffects.setTextSize(11f);
        lgdSideEffects.setXEntrySpace(4f);


        setSideEffectsData("", finalK);
    }

    List<Float> yValsSideEffects = new ArrayList<>();

    class MySideEffectsData {

        public List<BarEntry> barEntries = new ArrayList<>();
        public int color;

        public MySideEffectsData(List<BarEntry> barEntries, int color) {
            this.barEntries = barEntries;
            this.color = color;
        }
    }


    public static final String SUICIDALTHOUGHTS = "Suicidal Thoughts";
    public static final String HEADACHE = "Headache";
    public static final String NIGHTMARES = "Nightmares";
    public static final String PANICATTACKS = "PanicAttacks";

    //    String startDate;
    private void setSideEffectsData(String label, int finalK) {

        HashSet<String> selectedAll = new HashSet<>();
        selectedAll.add(SUICIDALTHOUGHTS);
        selectedAll.add(HEADACHE);
        selectedAll.add(NIGHTMARES);
        selectedAll.add(PANICATTACKS);

        final List<MySideEffectsData> valueData = new ArrayList<MySideEffectsData>();

        for (int i = 0; i < valueData.size(); i++) {
//            Debug.e("valueData", "" + valueData);
        }

        Iterator<String> iterator = selectedAll.iterator();

//        Debug.e("iterator", "" + iterator);

        while (iterator.hasNext()) {

            String userId = iterator.next();
//            Debug.e("Selected userId", "" + userId);

            if (hm.containsKey(userId) && hm.get(userId).size() > 0) {
                List<Float> temp = new ArrayList<>();

                for (int i = 0; i < finalK; i++) {
                    Calendar cal = Calendar.getInstance();
//                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    cal.add(Calendar.DAY_OF_MONTH, -1 * (finalK - i));

                    if (i == 0) {
                        startDate = Utils.parseTime(cal.getTimeInMillis(), "MM/dd/yyyy"); //MM/dd/yyyy
                    }

                    boolean isFound = false;
                    for (int k = 0; k < hm.get(userId).size(); k++) {
                        List<List<String>> medsTaken = hm.get(userId);
                        String time = medsTaken.get(k).get(0);


                        String first = time.substring(0, 1);
                        if (!first.equalsIgnoreCase("-")) {

                            if (Utils.parseTime(cal.getTimeInMillis(), "dd MMM yyyy").
                                    equalsIgnoreCase(Utils.parseTime(Long.valueOf(medsTaken.get(k).get(0)), "dd MMM yyyy"))) {

                                Debug.e("date lable : ", "" + Utils.parseTime(cal.getTimeInMillis(), "dd MMM") + " " + medsTaken.get(k).get(1));
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


                List<BarEntry> barEntries = new ArrayList<>();
                float start = 1f;
                for (int l = (int) start; l <= temp.size(); l++) {
                    BarEntry barEntry = new BarEntry(l, temp.get(l - 1));
                    barEntries.add(barEntry);
                }

//                Debug.e("data", "" + barEntries);
//                ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
//                int color2 = generator.getColor("" + userId);
//                valueData.add(new MySideEffectsData(barEntries, color2));

                if (userId.equalsIgnoreCase(SUICIDALTHOUGHTS)) {
                    int color = getResources().getColor(R.color.theme_red);
                    valueData.add(new MySideEffectsData(barEntries, color));
                }
                if (userId.equalsIgnoreCase(HEADACHE)) {
                    int color = getResources().getColor(R.color.theme_blue);
                    valueData.add(new MySideEffectsData(barEntries, color));
                }
                if (userId.equalsIgnoreCase(NIGHTMARES)) {
                    int color = getResources().getColor(R.color.theme_green);
                    valueData.add(new MySideEffectsData(barEntries, color));
                }
                if (userId.equalsIgnoreCase(PANICATTACKS)) {
                    int color = getResources().getColor(R.color.theme_orange);
                    valueData.add(new MySideEffectsData(barEntries, color));
                }

            }
//            Debug.e("setData", "" + valueData.size());
        }

        BarDataSet set, set1, set2, set3;


        if (sideEffectsChart.getData() != null && sideEffectsChart.getData().getDataSetCount() > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    sideEffectsChart.clearValues();
                    List<IBarDataSet> barDataSets = new ArrayList<>();
                    for (int i = 0; i < valueData.size(); i++) {
                        BarDataSet dataSet = new BarDataSet(valueData.get(i).barEntries, "");
                        dataSet.setColor(valueData.get(i).color);
                        barDataSets.add(dataSet);
                    }
                    BarData data = new BarData(barDataSets);
                    data.setValueFormatter(new LargeValueFormatter());
                    sideEffectsChart.setData(data);

                    sideEffectsChart.getData().notifyDataChanged();
                    sideEffectsChart.notifyDataSetChanged();
                }
            });

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
            sideEffectsChart.setData(data);


            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    sideEffectsChart.getData().notifyDataChanged(); // let the data know a dataSet changed
                    sideEffectsChart.notifyDataSetChanged(); // let the chart know it's data changed
                    sideEffectsChart.invalidate(); // refresh
                }
            });

        }
//            sideEffectsChart.getData().notifyDataChanged(); // let the data know a dataSet changed
//            sideEffectsChart.notifyDataSetChanged(); // let the chart know it's data changed
//            sideEffectsChart.invalidate(); // refresh


    }

    protected RectF mOnValueSelectedRectF = new RectF();

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;

        MPPointF position = mLifeScallingChart.getPosition(e, YAxis.AxisDependency.LEFT);
        Debug.i("bounds", bounds.toString());
        Debug.i("position", position.toString());

        Debug.i("x-index",
                "low: " + mLifeScallingChart.getLowestVisibleX() + ", high: "
                        + mLifeScallingChart.getHighestVisibleX());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {
    }

    Map<String, List<List<String>>> hm = new HashMap<>();

    public void getChartData() {
        try {
            showDialog("");
            Calendar cal = Calendar.getInstance();
            Debug.e("end time-->>", "" + Utils.parseTime(cal.getTimeInMillis(), "MM/dd/yyyy"));

            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.CHART());
            body.addQueryParameter(RequestParamsUtils.END_DATE, Utils.parseTime(cal.getTimeInMillis(), "MM/dd/yyyy"));
            body.addQueryParameter(RequestParamsUtils.START_DATE, startDate); //MM/dd/yyyy
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
                Debug.e("", "getChartData# " + response);
                if (response != null && response.length() > 0) {
                    hm.clear();

                    res = new Gson().fromJson(response, new TypeToken<AggregateChartRes>() {
                    }.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvNausea.setText("" + res.user.nausea);
                            tvRestlessness.setText("" + res.user.restlessness);
                            tvDryMouth.setText("" + res.user.dryMouth);
                            tvPanicAttack.setText("" + res.user.panicAttack);
                        }
                    });

                    hm.put(SOCIAL_LIFE, res.user.chart.socialLife);
                    hm.put(FAMILY_LIFE, res.user.chart.familyLife);
                    hm.put(WORK_LIFE, res.user.chart.workLife);

                    hm.put(EVERYONE, res.all.chart.feeling);
                    hm.put(ME, res.user.chart.feeling);

                    hm.put(WEIGHT, res.user.chart.weight);

                    hm.put(APPETITE, res.user.chart.appetite);
                    hm.put(INITIATIVE, res.user.chart.initiative);
                    hm.put(PASSIMISM, res.user.chart.pessimism);
                    hm.put(JOY, res.user.chart.zest);

                    hm.put(ANXIETY, res.user.chart.anxiety);
                    hm.put(SLEEP, res.user.chart.sleep);
                    hm.put(ENERGY, res.user.chart.energy);
                    hm.put(MOOD, res.user.chart.feeling);
                    hm.put(CONCENTRATION, res.user.chart.concentration);

                    hm.put(SUICIDALTHOUGHTS, res.user.chart.suicideThought);
                    hm.put(HEADACHE, res.user.chart.headache);
                    hm.put(NIGHTMARES, res.user.chart.nightmare);
                    hm.put(PANICATTACKS, res.user.chart.panicAttack);

//                            for (int i = 0; i < res.user.chart.workLife.size(); i++) {
//
//                                for (int j = 0; j < workLife.get(i).size(); j++) {
//                                    workLife.add(workLife.get(i));
//                                }
//                            }

                    initChart();
                    initMoodChart();
                    initWeightChart();
                    initSymptomChart();
                    initSymptomMoodChart();
                    initsideEffectsChart();

                    dismissDialog();
//


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

                    final DashboardStatsRes resp = new Gson().fromJson(response, new TypeToken<DashboardStatsRes>() {
                    }.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tvLastEntery.setText(Utils.parseTimeUTCtoDefault(resp.lastEntry, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd MMM"));
//        tvLastEntery.setText(resp.lastEntry);
                            tvMyEntries.setText(resp.myEntryTotal);
                            tvSelfHarm.setText(resp.mySelfHarmTotal);
                            tvAttended.setText(resp.myAttendedSessionsTotal);
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

    private void fillData() {


        cbSocialLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbSocialLife.isChecked()) {
                    hm.put(SOCIAL_LIFE, res.user.chart.socialLife);
                    cbSocialLife.setTextColor(getResources().getColor(R.color.theme_red));
                } else {
                    hm.remove(SOCIAL_LIFE);
                    cbSocialLife.setTextColor(getResources().getColor(R.color.theme_gray));

                }
                initChart();
            }
        });

        cbFamilyLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbFamilyLife.isChecked()) {
                    hm.put(FAMILY_LIFE, res.user.chart.familyLife);
                    cbFamilyLife.setTextColor(getResources().getColor(R.color.theme_blue));
                } else {
                    hm.remove(FAMILY_LIFE);
                    cbFamilyLife.setTextColor(getResources().getColor(R.color.theme_gray));

                }
                initChart();
            }
        });

        cbWorkLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbWorkLife.isChecked()) {
                    hm.put(WORK_LIFE, res.user.chart.workLife);
                    cbWorkLife.setTextColor(getResources().getColor(R.color.theme_green));
                } else {
                    hm.remove(WORK_LIFE);
                    cbWorkLife.setTextColor(getResources().getColor(R.color.theme_gray));

                }
                initChart();
            }
        });

        cbEveryOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbEveryOne.isChecked()) {
                    hm.put(EVERYONE, res.all.chart.feeling);
                    cbEveryOne.setTextColor(getResources().getColor(R.color.theme_blue));
                } else {
                    hm.remove(EVERYONE);
                    cbEveryOne.setTextColor(getResources().getColor(R.color.theme_gray));

                }
                initMoodChart();
            }
        });

        cbMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbMe.isChecked()) {
                    hm.put(ME, res.user.chart.feeling);
                    cbMe.setTextColor(getResources().getColor(R.color.theme_green));
                } else {
                    hm.remove(ME);
                    cbMe.setTextColor(getResources().getColor(R.color.theme_gray));

                }
                initMoodChart();
            }
        });

        cbAppetite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbAppetite.isChecked()) {
                    hm.put(APPETITE, res.user.chart.appetite);
                    cbAppetite.setTextColor(getResources().getColor(R.color.theme_red));
                } else {
                    hm.remove(APPETITE);
                    cbAppetite.setTextColor(getResources().getColor(R.color.theme_gray));

                }
                initSymptomChart();
            }
        });

        cbInitiative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbInitiative.isChecked()) {
                    hm.put(INITIATIVE, res.user.chart.initiative);
                    cbInitiative.setTextColor(getResources().getColor(R.color.theme_blue));
                } else {
                    hm.remove(INITIATIVE);
                    cbInitiative.setTextColor(getResources().getColor(R.color.theme_gray));

                }
                initSymptomChart();
            }
        });

        cbPassimism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbPassimism.isChecked()) {
                    hm.put(PASSIMISM, res.user.chart.pessimism);
                    cbPassimism.setTextColor(getResources().getColor(R.color.theme_green));
                } else {
                    hm.remove(PASSIMISM);
                    cbPassimism.setTextColor(getResources().getColor(R.color.theme_gray));

                }
                initSymptomChart();
            }
        });

        cbJoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbJoy.isChecked()) {
                    hm.put(JOY, res.user.chart.zest);
                    cbJoy.setTextColor(getResources().getColor(R.color.theme_orange));
                } else {
                    hm.remove(JOY);
                    cbJoy.setTextColor(getResources().getColor(R.color.theme_gray));

                }
                initSymptomChart();
            }
        });

        cbAnxiety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbAnxiety.isChecked()) {
                    hm.put(ANXIETY, res.user.chart.anxiety);
                    cbAnxiety.setTextColor(getResources().getColor(R.color.theme_red));
                } else {
                    hm.remove(ANXIETY);
                    cbAnxiety.setTextColor(getResources().getColor(R.color.theme_gray));

                }
                initSymptomMoodChart();
            }
        });

        cbSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbSleep.isChecked()) {
                    hm.put(SLEEP, res.user.chart.sleep);
                    cbSleep.setTextColor(getResources().getColor(R.color.theme_blue));
                } else {
                    hm.remove(SLEEP);
                    cbSleep.setTextColor(getResources().getColor(R.color.theme_gray));

                }
                initSymptomMoodChart();
            }
        });

        cbMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbMood.isChecked()) {
                    hm.put(MOOD, res.user.chart.feeling);
                    cbMood.setTextColor(getResources().getColor(R.color.theme_red_color));
                } else {
                    hm.remove(MOOD);
                    cbMood.setTextColor(getResources().getColor(R.color.theme_gray));

                }
                initSymptomMoodChart();
            }
        });

        cbEnergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbEnergy.isChecked()) {
                    hm.put(ENERGY, res.user.chart.energy);
                    cbEnergy.setTextColor(getResources().getColor(R.color.theme_green));
                } else {
                    hm.remove(ENERGY);
                    cbEnergy.setTextColor(getResources().getColor(R.color.theme_gray));

                }
                initSymptomMoodChart();
            }
        });

        cbConcentration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbConcentration.isChecked()) {
                    hm.put(CONCENTRATION, res.user.chart.concentration);
                    cbConcentration.setTextColor(getResources().getColor(R.color.theme_orange));
                } else {
                    hm.remove(CONCENTRATION);
                    cbConcentration.setTextColor(getResources().getColor(R.color.theme_gray));

                }
                initSymptomMoodChart();
            }
        });

        cbuicidalThoughts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbuicidalThoughts.isChecked()) {
                    hm.put(SUICIDALTHOUGHTS, res.user.chart.suicideThought);
                    cbuicidalThoughts.setTextColor(getResources().getColor(R.color.theme_red));
                } else {
                    hm.remove(SUICIDALTHOUGHTS);
                    cbuicidalThoughts.setTextColor(getResources().getColor(R.color.theme_gray));

                }
                initsideEffectsChart();
            }
        });

        cbHeadache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbHeadache.isChecked()) {
                    hm.put(HEADACHE, res.user.chart.headache);
                    cbHeadache.setTextColor(getResources().getColor(R.color.theme_blue));
                } else {
                    hm.remove(HEADACHE);
                    cbHeadache.setTextColor(getResources().getColor(R.color.theme_gray));

                }
                initsideEffectsChart();
            }
        });

        cbNightmares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbNightmares.isChecked()) {
                    hm.put(NIGHTMARES, res.user.chart.nightmare);
                    cbNightmares.setTextColor(getResources().getColor(R.color.theme_green));
                } else {
                    hm.remove(NIGHTMARES);
                    cbNightmares.setTextColor(getResources().getColor(R.color.theme_gray));

                }
                initsideEffectsChart();
            }
        });

        cbPanicAttacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbPanicAttacks.isChecked()) {
                    hm.put(PANICATTACKS, res.user.chart.panicAttack);
                    cbPanicAttacks.setTextColor(getResources().getColor(R.color.theme_orange));
                } else {
                    hm.remove(PANICATTACKS);
                    cbPanicAttacks.setTextColor(getResources().getColor(R.color.theme_gray));

                }
                initsideEffectsChart();
            }
        });
    }
}
