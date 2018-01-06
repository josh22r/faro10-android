package com.kartum;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpFAQActivity extends BaseActivity {

    @BindView(R.id.tvBackErrow)
    TextView tvBackErrow;

    @BindView(R.id.tvTrackStatus)
    TextView tvTrackStatus;
    @BindView(R.id.llTrackStatus)
    LinearLayout llTrackStatus;

    @BindView(R.id.tvEntered)
    TextView tvEntered;
    @BindView(R.id.llEntered)
    LinearLayout llEntered;

    @BindView(R.id.tvObsever)
    TextView tvObsever;
    @BindView(R.id.llObsever)
    LinearLayout llObsever;

    @BindView(R.id.tvGuardian)
    TextView tvGuardian;
    @BindView(R.id.llGuardian)
    LinearLayout llGuardian;

    @BindView(R.id.tvBecomeObsever)
    TextView tvBecomeObsever;
    @BindView(R.id.llBecomeObsever)
    LinearLayout llBecomeObsever;

    @BindView(R.id.tvClinician)
    TextView tvClinician;
    @BindView(R.id.llClinician)
    LinearLayout llClinician;

    @BindView(R.id.tvobserving)
    TextView tvobserving;
    @BindView(R.id.llobserving)
    LinearLayout llobserving;

    @BindView(R.id.tvReport)
    TextView tvReport;
    @BindView(R.id.llReport)
    LinearLayout llReport;

    @BindView(R.id.tvObservations)
    TextView tvObservations;
    @BindView(R.id.llObservations)
    LinearLayout llObservations;

    @BindView(R.id.tvObservee)
    TextView tvObservee;
    @BindView(R.id.llObservee)
    LinearLayout llObservee;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_faq);
        ButterKnife.bind(this);

        initDrawer(true);
        init();
    }

    private void init() {
        //setTitleText("Faro10");

        tvBackErrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelpFAQActivity.super.onBackPressed();
            }
        });

        tvTrackStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llTrackStatus.getVisibility() == View.GONE) {
                    llTrackStatus.setVisibility(View.VISIBLE);
                    tvTrackStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_gray, 0);
                } else {
                    llTrackStatus.setVisibility(View.GONE);
                    tvTrackStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_gray_28dp, 0);
                }
            }
        });

        tvEntered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llEntered.getVisibility() == View.GONE) {
                    llEntered.setVisibility(View.VISIBLE);
                    tvEntered.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_gray, 0);
                } else {
                    llEntered.setVisibility(View.GONE);
                    tvEntered.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_gray_28dp, 0);
                }
            }
        });

        tvObsever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llObsever.getVisibility() == View.GONE) {
                    llObsever.setVisibility(View.VISIBLE);
                    tvObsever.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_gray, 0);
                } else {
                    llObsever.setVisibility(View.GONE);
                    tvObsever.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_gray_28dp, 0);
                }
            }
        });

        tvGuardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llGuardian.getVisibility() == View.GONE) {
                    llGuardian.setVisibility(View.VISIBLE);
                    tvGuardian.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_gray, 0);
                } else {
                    llGuardian.setVisibility(View.GONE);
                    tvGuardian.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_gray_28dp, 0);
                }
            }
        });

        tvBecomeObsever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llBecomeObsever.getVisibility() == View.GONE) {
                    llBecomeObsever.setVisibility(View.VISIBLE);
                    tvBecomeObsever.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_gray, 0);
                } else {
                    llBecomeObsever.setVisibility(View.GONE);
                    tvBecomeObsever.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_gray_28dp, 0);
                }
            }
        });

        tvClinician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llClinician.getVisibility() == View.GONE) {
                    llClinician.setVisibility(View.VISIBLE);
                    tvClinician.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_gray, 0);
                } else {
                    llClinician.setVisibility(View.GONE);
                    tvClinician.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_gray_28dp, 0);
                }
            }
        });

        tvobserving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llobserving.getVisibility() == View.GONE) {
                    llobserving.setVisibility(View.VISIBLE);
                    tvobserving.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_gray, 0);
                } else {
                    llobserving.setVisibility(View.GONE);
                    tvobserving.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_gray_28dp, 0);
                }
            }
        });

        tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llReport.getVisibility() == View.GONE) {
                    llReport.setVisibility(View.VISIBLE);
                    tvReport.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_gray, 0);
                } else {
                    llReport.setVisibility(View.GONE);
                    tvReport.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_gray_28dp, 0);
                }
            }
        });

        tvObservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llObservations.getVisibility() == View.GONE) {
                    llObservations.setVisibility(View.VISIBLE);
                    tvObservations.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_gray, 0);
                } else {
                    llObservations.setVisibility(View.GONE);
                    tvObservations.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_gray_28dp, 0);
                }
            }
        });

        tvObservee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llObservee.getVisibility() == View.GONE) {
                    llObservee.setVisibility(View.VISIBLE);
                    tvObservee.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_gray, 0);
                } else {
                    llObservee.setVisibility(View.GONE);
                    tvObservee.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_gray_28dp, 0);
                }
            }
        });

    }
}
