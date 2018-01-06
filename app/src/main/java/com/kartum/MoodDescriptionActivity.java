package com.kartum;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoodDescriptionActivity extends BaseActivity {

    @BindView(R.id.tvBackErrow)
    TextView tvBackErrow;

    @BindView(R.id.imgMoodUp)
    ImageView imgMoodUp;
    @BindView(R.id.imgMoodDow)
    ImageView imgMoodDow;

    @BindView(R.id.imgAnxietyUp)
    ImageView imgAnxietyUp;
    @BindView(R.id.imgAnxietyDow)
    ImageView imgAnxietyDow;

    @BindView(R.id.imgEnergyUp)
    ImageView imgEnergyUp;
    @BindView(R.id.imgEnergyDow)
    ImageView imgEnergyDow;

    @BindView(R.id.imgPessimismUp)
    ImageView imgPessimismUp;
    @BindView(R.id.imgPessimismDow)
    ImageView imgPessimismDow;

    @BindView(R.id.imgConcentrationUp)
    ImageView imgConcentrationUp;
    @BindView(R.id.imgConcentrationDow)
    ImageView imgConcentrationDow;

    @BindView(R.id.imgInitiativeUp)
    ImageView imgInitiativeUp;
    @BindView(R.id.imgInitiativeDow)
    ImageView imgInitiativeDow;

    @BindView(R.id.llMoodUpDowan)
    LinearLayout llMoodUpDowan;
    @BindView(R.id.llMoodDescription)
    LinearLayout llMoodDescription;

    @BindView(R.id.llAnxietyUpDowan)
    LinearLayout llAnxietyUpDowan;
    @BindView(R.id.llAnxietyDescrip)
    LinearLayout llAnxietyDescrip;

    @BindView(R.id.llEnergyUpDowan)
    LinearLayout llEnergyUpDowan;
    @BindView(R.id.llEnergyDescrip)
    LinearLayout llEnergyDescrip;

    @BindView(R.id.llPessimismUpDowan)
    LinearLayout llPessimismUpDowan;
    @BindView(R.id.llPessimismDescrip)
    LinearLayout llPessimismDescrip;

    @BindView(R.id.llConcentrationUpDowan)
    LinearLayout llConcentrationUpDowan;
    @BindView(R.id.llConcentrationDescrip)
    LinearLayout llConcentrationDescrip;

    @BindView(R.id.llInitiativeUpDowan)
    LinearLayout llInitiativeUpDowan;
    @BindView(R.id.llInitiativeDescrip)
    LinearLayout llInitiativeDescrip;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_description);
        ButterKnife.bind(this);

        initDrawer(true);
        init();
    }


    private void init() {
        //setTitleText("Faro10");

        tvBackErrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoodDescriptionActivity.super.onBackPressed();
            }
        });

        llMoodUpDowan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llMoodDescription.getVisibility() == View.VISIBLE && llMoodDescription.getVisibility() == View.VISIBLE) {
                    llMoodDescription.setVisibility(View.GONE);
                    imgMoodDow.setVisibility(View.VISIBLE);
                    imgMoodUp.setVisibility(View.GONE);
                } else {
                    llMoodDescription.setVisibility(View.VISIBLE);
                    imgMoodDow.setVisibility(View.GONE);
                    imgMoodUp.setVisibility(View.VISIBLE);
                }
            }
        });

        llAnxietyUpDowan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llAnxietyDescrip.getVisibility() == View.GONE && llAnxietyDescrip.getVisibility() == View.GONE) {
                    llAnxietyDescrip.setVisibility(View.VISIBLE);
                    imgAnxietyDow.setVisibility(View.GONE);
                    imgAnxietyUp.setVisibility(View.VISIBLE);
                } else {
                    llAnxietyDescrip.setVisibility(View.GONE);
                    imgAnxietyDow.setVisibility(View.VISIBLE);
                    imgAnxietyUp.setVisibility(View.GONE);
                }
            }
        });

        llEnergyUpDowan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llEnergyDescrip.getVisibility() == View.GONE && llEnergyDescrip.getVisibility() == View.GONE) {
                    llEnergyDescrip.setVisibility(View.VISIBLE);
                    imgEnergyDow.setVisibility(View.GONE);
                    imgEnergyUp.setVisibility(View.VISIBLE);
                } else {
                    llEnergyDescrip.setVisibility(View.GONE);
                    imgEnergyDow.setVisibility(View.VISIBLE);
                    imgEnergyUp.setVisibility(View.GONE);
                }
            }
        });

        llPessimismUpDowan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llPessimismDescrip.getVisibility() == View.GONE && llPessimismDescrip.getVisibility() == View.GONE) {
                    llPessimismDescrip.setVisibility(View.VISIBLE);
                    imgPessimismDow.setVisibility(View.GONE);
                    imgPessimismUp.setVisibility(View.VISIBLE);
                } else {
                    llPessimismDescrip.setVisibility(View.GONE);
                    imgPessimismDow.setVisibility(View.VISIBLE);
                    imgPessimismUp.setVisibility(View.GONE);
                }
            }
        });

        llConcentrationUpDowan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llConcentrationDescrip.getVisibility() == View.GONE && llConcentrationDescrip.getVisibility() == View.GONE) {
                    llConcentrationDescrip.setVisibility(View.VISIBLE);
                    imgConcentrationDow.setVisibility(View.GONE);
                    imgConcentrationUp.setVisibility(View.VISIBLE);
                } else {
                    llConcentrationDescrip.setVisibility(View.GONE);
                    imgConcentrationDow.setVisibility(View.VISIBLE);
                    imgConcentrationUp.setVisibility(View.GONE);
                }
            }
        });

        llInitiativeUpDowan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llInitiativeDescrip.getVisibility() == View.GONE && llInitiativeDescrip.getVisibility() == View.GONE) {
                    llInitiativeDescrip.setVisibility(View.VISIBLE);
                    imgInitiativeDow.setVisibility(View.GONE);
                    imgInitiativeUp.setVisibility(View.VISIBLE);
                } else {
                    llInitiativeDescrip.setVisibility(View.GONE);
                    imgInitiativeDow.setVisibility(View.VISIBLE);
                    imgInitiativeUp.setVisibility(View.GONE);
                }
            }
        });

    }
}
