package com.kartum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kartum.objects.Spinner;
import com.kartum.utils.AppReviewManager;
import com.kartum.utils.AsyncResponseHandlerOk;
import com.kartum.utils.CompletionHandler;
import com.kartum.utils.Debug;
import com.kartum.utils.HttpClient;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.URLs;
import com.kartum.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;

import static com.kartum.R.id.seekbar;

public class MoodVitalsActivity extends BaseActivity {

    @BindView(R.id.sbWorkLife)
    SeekBar sbWorkLife;
    @BindView(R.id.sbSocialLife)
    SeekBar sbSocialLife;
    @BindView(R.id.sbFamilyLife)
    SeekBar sbFamilyLife;

//    @BindView(R.id.cirsbMood)
//    CircularSeekBar cirsbMood;
//    @BindView(R.id.cirsbAnxiety)
//    CircularSeekBar cirsbAnxiety;
//    @BindView(R.id.cirsbEnergy)
//    CircularSeekBar cirsbEnergy;
//    @BindView(R.id.cirsbPessimism)
//    CircularSeekBar cirsbPessimism;
//    @BindView(R.id.cirsbConcentration)
//    CircularSeekBar cirsbConcentration;
//    @BindView(R.id.cirsbInitiative)
//    CircularSeekBar cirsbInitiative;

    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.tvBackMood)
    TextView tvBackMood;
    @BindView(R.id.tvMoodDescription)
    TextView tvMoodDescription;

    @BindView(R.id.tvSelfHarm)
    TextView tvSelfHarm;

    @BindView(R.id.tvMood)
    TextView tvMood;
    @BindView(R.id.tvAnxiety)
    TextView tvAnxiety;
    @BindView(R.id.tvEnergy)
    TextView tvEnergy;
    @BindView(R.id.tvPessimism)
    TextView tvPessimism;
    @BindView(R.id.tvConcentration)
    TextView tvConcentration;
    @BindView(R.id.tvInitiative)
    TextView tvInitiative;

    @BindView(seekbar)
    SeekBar sbMood;
    @BindView(R.id.sbAnxiety)
    SeekBar sbAnxiety;
    @BindView(R.id.sbEnergy)
    SeekBar sbEnergy;
    @BindView(R.id.sbPessimism)
    SeekBar sbPessimism;
    @BindView(R.id.sbConcentration)
    SeekBar sbConcentration;
    @BindView(R.id.sbInitiative)
    SeekBar sbInitiative;
    @BindView(R.id.imgMood)
    ImageView imgMood;
    @BindView(R.id.imgAnxiety)
    ImageView imgAnxiety;
    @BindView(R.id.imgConcentration)
    ImageView imgConcentration;
    @BindView(R.id.imgInitiative)
    ImageView imgInitiative;
    @BindView(R.id.imgEnergy)
    ImageView imgEnergy;
    @BindView(R.id.imgPessimism)
    ImageView imgPessimism;

    private boolean hasMoodBeenSelected = false;
    private boolean hasAnxietyBeenSelected = false;
    private boolean hasEnergyBeenSelected = false;
    private boolean hasPessimismBeenSelected = false;
    private boolean hasConcentrationBeenSelected = false;
    private boolean hasInitiativeBeenSelected = false;
    private boolean hasWorkLifeSelected = false;
    private boolean hasSocialLifeSelected = false;
    private boolean hasFamilyLifeSelected = false;

    float[] radii;

    private int i = 0;
    private ProgressBar firstBar = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_vitals);
        ButterKnife.bind(this);

        initDrawer(true);
        init();
    }

    private void init() {
        //setTitleText("Faro10");

        tvBackMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoodVitalsActivity.super.onBackPressed();
            }
        });
        tvMoodDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoodVitalsActivity.this, MoodDescriptionActivity.class);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate())
                    setMoodEntryData();
            }
        });


        tvSelfHarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Spinner> data = new ArrayList<>();
                data.add(new Spinner("Cutting", "Cutting"));
                data.add(new Spinner("Scratching", "Scratching"));
                data.add(new Spinner("Burning", "Burning"));
                data.add(new Spinner("Banging Head", "Banging Head"));
                data.add(new Spinner("Banging Other", "Banging Other"));
                data.add(new Spinner("Hair Pulling", "Hair Pulling"));
                data.add(new Spinner("Skin Picking", "Skin Picking"));
                data.add(new Spinner("Self-Poisoning", "Self-Poisoning"));
                data.add(new Spinner("Other", "Other"));
                showSpinner("Self-Harm", tvSelfHarm, data, false, null);
            }
        });
//

        // add completion handlers to indicate when user has selected a value
        Utils.initSeekBar(getActivity(), sbSocialLife, new CompletionHandler() {
            @Override
            public void onComplete() {
                hasSocialLifeSelected = true;
            }
        });

        Utils.initSeekBar(getActivity(), sbWorkLife, new CompletionHandler() {
            @Override
            public void onComplete() {
                hasWorkLifeSelected = true;
            }
        });

        Utils.initSeekBar(getActivity(), sbFamilyLife, new CompletionHandler() {
            @Override
            public void onComplete() {
                hasFamilyLifeSelected = true;
            }
        });

        ProgressDrawable bgProgress1 = new ProgressDrawable(0xdd00ff00, 0x4400ff00);
        sbMood.setProgressDrawable(bgProgress1);

        sbMood.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresss, boolean b) {
                hasMoodBeenSelected = true;

                if (sbMood.getProgress() == 0) {
                    imgMood.setImageResource(R.mipmap.mood_icon_0);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_green_2x));
                    tvMood.setText("No sadness");
                    sbMood.setProgress(progresss);
                } else if (sbMood.getProgress() == 1) {
                    imgMood.setImageResource(R.mipmap.mood_icon_1);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_green_2x));
                    tvMood.setText("Occasional sadness in keeping with the circumstances");
                    sbMood.setProgress(progresss);
                } else if (sbMood.getProgress() == 2) {
                    imgMood.setImageResource(R.mipmap.mood_icon_2);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_green_2x));
                    tvMood.setText("Sad or low but brightens up without difficulty");
                    sbMood.setProgress(progresss);
                } else if (sbMood.getProgress() == 3) {
                    imgMood.setImageResource(R.mipmap.mood_icon_3);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_orange_2x));
                    tvMood.setText("Sad and lowness, with difficulty brightening up");
                    sbMood.setProgress(progresss);
                } else if (sbMood.getProgress() == 4) {
                    imgMood.setImageResource(R.mipmap.mood_icon_4);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_orange_2x));
                    tvMood.setText("Pervasive feelings of sadness or gloominess. Mood influenced by external circumstances.");
                    sbMood.setProgress(progresss);
                } else if (sbMood.getProgress() == 5) {
                    imgMood.setImageResource(R.mipmap.mood_icon_5);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_pink_2x));
                    tvMood.setText("pervasive sadness originating internally and externally");
                    sbMood.setProgress(progresss);
                } else if (sbMood.getProgress() == 6) {
                    imgMood.setImageResource(R.mipmap.mood_icon_6);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_pink_2x));
                    tvMood.setText("Continuous or unvarying sadness, misery or despondency");
                    sbMood.setProgress(progresss);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ProgressDrawable pbAnxiety = new ProgressDrawable(0xdd00ff00, 0x4400ff00);
        sbAnxiety.setProgressDrawable(pbAnxiety);

        sbAnxiety.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresss, boolean b) {
                hasAnxietyBeenSelected = true;

                if (sbAnxiety.getProgress() == 0) {
                    imgAnxiety.setImageResource(R.mipmap.anxiety_icon_0);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_green_2x));
                    tvAnxiety.setText("No anxiety");
                    sbAnxiety.setProgress(progresss);
                } else if (sbAnxiety.getProgress() == 1) {
                    imgAnxiety.setImageResource(R.mipmap.anxiety_icon_1);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_green_2x));
                    tvAnxiety.setText("Mild. Only fleeting inner tension");
                    sbAnxiety.setProgress(progresss);
                } else if (sbAnxiety.getProgress() == 2) {
                    imgAnxiety.setImageResource(R.mipmap.anxiety_icon_2);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_green_2x));
                    tvAnxiety.setText("Occasional feelings of edginess and ill-defined discomfort");
                    sbAnxiety.setProgress(progresss);
                } else if (sbAnxiety.getProgress() == 3) {
                    imgAnxiety.setImageResource(R.mipmap.anxiety_icon_3);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_orange_2x));
                    tvAnxiety.setText("Common Feelings of tension and intermittent panic which can be mastered with some difficulty");
                    sbAnxiety.setProgress(progresss);
                } else if (sbAnxiety.getProgress() == 4) {
                    imgAnxiety.setImageResource(R.mipmap.anxiety_icon_4);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_orange_2x));
                    tvAnxiety.setText("Continuous feelings of inner tension or intermittent panic which can be mastered with some difficulty");
                    sbAnxiety.setProgress(progresss);
                } else if (sbAnxiety.getProgress() == 5) {
                    imgAnxiety.setImageResource(R.mipmap.anxiety_icon_5);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_pink_2x));
                    tvAnxiety.setText("Continual dread or anguish");
                    sbAnxiety.setProgress(progresss);
                } else if (sbAnxiety.getProgress() == 6) {
                    imgAnxiety.setImageResource(R.mipmap.anxiety_icon_6);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_pink_2x));
                    tvAnxiety.setText("Unrelenting dread or anguish. Overwhelming panic.");
                    sbAnxiety.setProgress(progresss);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        ProgressDrawable pbEnergy = new ProgressDrawable(0xdd00ff00, 0x4400ff00);
        sbEnergy.setProgressDrawable(pbEnergy);

        sbEnergy.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresss, boolean b) {
                hasEnergyBeenSelected = true;

                if (sbEnergy.getProgress() == 0) {
                    imgEnergy.setImageResource(R.mipmap.energy_icon_0);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_green_2x));
                    tvEnergy.setText("Enjoys life or takes it as it comes.");
                    sbEnergy.setProgress(progresss);
                } else if (sbEnergy.getProgress() == 1) {
                    imgEnergy.setImageResource(R.mipmap.energy_icon_1);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_green_2x));
                    tvEnergy.setText("Enjoys life or takes it as it comes.");
                    sbEnergy.setProgress(progresss);
                } else if (sbEnergy.getProgress() == 2) {
                    imgEnergy.setImageResource(R.mipmap.energy_icon_2);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_green_2x));
                    tvEnergy.setText("Enjoys life or takes it as it comes.");
                    sbEnergy.setProgress(progresss);
                } else if (sbEnergy.getProgress() == 3) {
                    imgEnergy.setImageResource(R.mipmap.energy_icon_3);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_orange_2x));
                    tvEnergy.setText("Weary of life. Only fleeting suicidal thoughts");
                    sbEnergy.setProgress(progresss);
                } else if (sbEnergy.getProgress() == 4) {
                    imgEnergy.setImageResource(R.mipmap.energy_icon_4);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_orange_2x));
                    tvEnergy.setText("Weary of life. Only fleeting suicidal thoughts");
                    sbEnergy.setProgress(progresss);
                } else if (sbEnergy.getProgress() == 5) {
                    imgEnergy.setImageResource(R.mipmap.energy_icon_5);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_pink_2x));
                    tvEnergy.setText("Probably better off dead. Suicidal thoughts are common, and suicide is considered a possible solutions, but without specific plans or intention");
                    sbEnergy.setProgress(progresss);
                } else if (sbEnergy.getProgress() == 6) {
                    imgEnergy.setImageResource(R.mipmap.energy_icon_6);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_pink_2x));
                    tvEnergy.setText("Unrelenting dread or anguish. Overwhelming panic.");
                    sbEnergy.setProgress(progresss);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        ProgressDrawable pbPessimism = new ProgressDrawable(0xdd00ff00, 0x4400ff00);
        sbPessimism.setProgressDrawable(pbPessimism);

        sbPessimism.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresss, boolean b) {
                hasPessimismBeenSelected = true;

                if (sbPessimism.getProgress() == 0) {
                    imgPessimism.setImageResource(R.mipmap.pessimism_icon_0);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_green_2x));
                    tvPessimism.setText("No pessimistic thoughts");
                    sbPessimism.setProgress(progresss);
                } else if (sbPessimism.getProgress() == 1) {
                    imgPessimism.setImageResource(R.mipmap.pessimism_icon_1);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_green_2x));
                    tvPessimism.setText("No pessimistic thoughts");
                    sbPessimism.setProgress(progresss);
                } else if (sbPessimism.getProgress() == 2) {
                    imgPessimism.setImageResource(R.mipmap.pessimism_icon_2);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_green_2x));
                    tvPessimism.setText("No pessimistic thoughts");
                    sbPessimism.setProgress(progresss);
                } else if (sbPessimism.getProgress() == 3) {
                    imgPessimism.setImageResource(R.mipmap.pessimism_icon_3);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_orange_2x));
                    tvPessimism.setText("Fluctuating ideas of failure, self-reproach or self-depreciation");
                    sbPessimism.setProgress(progresss);
                } else if (sbPessimism.getProgress() == 4) {
                    imgPessimism.setImageResource(R.mipmap.pessimism_icon_4);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_orange_2x));
                    tvPessimism.setText("Fluctuating ideas of failure, self-reproach or self-depreciation");
                    sbPessimism.setProgress(progresss);
                } else if (sbPessimism.getProgress() == 5) {
                    imgPessimism.setImageResource(R.mipmap.pessimism_icon_5);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_pink_2x));
                    tvPessimism.setText("Persistent self-accusations, or definite but still rational ideas of guilt or sin. Increasingly pessimistic about the future");
                    sbPessimism.setProgress(progresss);
                } else if (sbPessimism.getProgress() == 6) {
                    imgPessimism.setImageResource(R.mipmap.pessimism_icon_6);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_pink_2x));
                    tvPessimism.setText("Delusion of ruin, remorse, or unredeemable sin. Self accusations which are absurd and unshakeable");
                    sbPessimism.setProgress(progresss);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ProgressDrawable pbConcentration = new ProgressDrawable(0xdd00ff00, 0x4400ff00);
        sbConcentration.setProgressDrawable(pbConcentration);

        sbConcentration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresss, boolean b) {
                hasConcentrationBeenSelected = true;

                if (sbConcentration.getProgress() == 0) {
                    imgConcentration.setImageResource(R.mipmap.concentration_icon_0);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_green_2x));
                    tvConcentration.setText("No difficulties in concentration");
                    sbConcentration.setProgress(progresss);
                } else if (sbConcentration.getProgress() == 1) {
                    imgConcentration.setImageResource(R.mipmap.concentration_icon_1);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_green_2x));
                    tvConcentration.setText("No difficulties in concentration");
                    sbConcentration.setProgress(progresss);
                } else if (sbConcentration.getProgress() == 2) {
                    imgConcentration.setImageResource(R.mipmap.concentration_icon_2);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_green_2x));
                    tvConcentration.setText("No difficulties in concentration");
                    sbConcentration.setProgress(progresss);
                } else if (sbConcentration.getProgress() == 3) {
                    imgConcentration.setImageResource(R.mipmap.concentration_icon_3);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_orange_2x));
                    tvConcentration.setText("Occasional difficulties in collecting your thoughts");
                    sbConcentration.setProgress(progresss);
                } else if (sbConcentration.getProgress() == 4) {
                    imgConcentration.setImageResource(R.mipmap.concentration_icon_4);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_orange_2x));
                    tvConcentration.setText("Occasional difficulties in collecting your thoughts");
                    sbConcentration.setProgress(progresss);
                } else if (sbConcentration.getProgress() == 5) {
                    imgConcentration.setImageResource(R.mipmap.concentration_icon_5);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_pink_2x));
                    tvConcentration.setText("Difficulties in concentrating and sustaining thoughts which reduces ability to read or hold a conversation");
                    sbConcentration.setProgress(progresss);
                } else if (sbConcentration.getProgress() == 6) {
                    imgConcentration.setImageResource(R.mipmap.concentration_icon_6);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_pink_2x));
                    tvConcentration.setText("Unable to read or converse without great difficulty");
                    sbConcentration.setProgress(progresss);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ProgressDrawable pbInitiative = new ProgressDrawable(0xdd00ff00, 0x4400ff00);
        sbInitiative.setProgressDrawable(pbInitiative);

        sbInitiative.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresss, boolean b) {
                hasInitiativeBeenSelected = true;

                if (sbInitiative.getProgress() == 0) {
                    imgInitiative.setImageResource(R.mipmap.initiative_icon_0);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_green_2x));
                    tvInitiative.setText("Hardly any difficulty getting started. No sluggishness.");
                    sbInitiative.setProgress(progresss);
                } else if (sbInitiative.getProgress() == 1) {
                    imgInitiative.setImageResource(R.mipmap.initiative_icon_1);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_green_2x));
                    tvInitiative.setText("Hardly any difficulty getting started. No sluggishness.");
                    sbInitiative.setProgress(progresss);
                } else if (sbInitiative.getProgress() == 2) {
                    imgInitiative.setImageResource(R.mipmap.initiative_icon_2);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_green_2x));
                    tvInitiative.setText("Hardly any difficulty getting started. No sluggishness.");
                    sbInitiative.setProgress(progresss);
                } else if (sbInitiative.getProgress() == 3) {
                    imgInitiative.setImageResource(R.mipmap.initiative_icon_3);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_orange_2x));
                    tvInitiative.setText("Difficulties in starting activities");
                    sbInitiative.setProgress(progresss);
                } else if (sbInitiative.getProgress() == 4) {
                    imgInitiative.setImageResource(R.mipmap.initiative_icon_4);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_orange_2x));
                    tvInitiative.setText("Difficulties in starting activities");
                    sbInitiative.setProgress(progresss);
                } else if (sbInitiative.getProgress() == 5) {
                    imgInitiative.setImageResource(R.mipmap.initiative_icon_5);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_pink_2x));
                    tvInitiative.setText("Difficulties in simple routine activities which should be carried out with effort");
                    sbInitiative.setProgress(progresss);
                } else if (sbInitiative.getProgress() == 6) {
                    imgInitiative.setImageResource(R.mipmap.initiative_icon_6);
                    seekBar.setThumb(getActivity().getResources().getDrawable(R.mipmap.tooltip_pink_2x));
                    tvInitiative.setText("Complete lassitude. Unable to do anything without help");
                    sbInitiative.setProgress(progresss);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    //    private boolean validate() {
//        if (tvSelfHarm.getText().toString().equalsIgnoreCase("null")) {
//            showToast(getString(R.string.err_symptom), Toast.LENGTH_SHORT);
//            return false;
//        }
//        return true;
//    }

    private boolean validate() {
//        if (cirsbMood.getProgress() <= 0) {
//            showToast(getString(R.string.err_mood), Toast.LENGTH_SHORT);
//            return false;
//        }
        return true;
    }

    public void setMoodEntryData() {
        try {
            showDialog("");
            
            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());

            if (hasMoodBeenSelected)
                body.addEncoded(RequestParamsUtils.FEELING, String.valueOf(sbMood.getProgress()));

            if (hasAnxietyBeenSelected)
                body.addEncoded(RequestParamsUtils.ANXIETY, String.valueOf(sbAnxiety.getProgress()));

            if (hasEnergyBeenSelected)
                body.addEncoded(RequestParamsUtils.ENERGY, String.valueOf(sbEnergy.getProgress()));

            if (hasPessimismBeenSelected)
                body.addEncoded(RequestParamsUtils.PESSIMISM, String.valueOf(sbPessimism.getProgress()));

            if (hasConcentrationBeenSelected)
                body.addEncoded(RequestParamsUtils.CONCENTRATION, String.valueOf(sbConcentration.getProgress()));

            if (hasInitiativeBeenSelected)
                body.addEncoded(RequestParamsUtils.INITIATIVE, String.valueOf(sbInitiative.getProgress()));

            if (hasWorkLifeSelected)
                body.addEncoded(RequestParamsUtils.WORK_LIFE, String.valueOf(sbWorkLife.getProgress()));

            if (hasSocialLifeSelected)
                body.addEncoded(RequestParamsUtils.SOCIAL_LIFE, String.valueOf(sbSocialLife.getProgress()));

            if (hasFamilyLifeSelected)
                body.addEncoded(RequestParamsUtils.FAMILY_LIFE, String.valueOf(sbFamilyLife.getProgress()));

            if (tvSelfHarm.getText().toString().equalsIgnoreCase("Select")) {
                body.addEncoded(RequestParamsUtils.SELF_HARM, "");
            } else {
                body.addEncoded(RequestParamsUtils.SELF_HARM, tvSelfHarm.getText().toString().trim());
            }

            for (int i = 0; i < body.build().size(); i++) {
                Debug.e("setMoodEntryData", "" + body.build().name(i) + " = " + body.build().value(i));
            }

            Debug.e("setMoodEntryData", "" + body);
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
                Debug.e("", "setMoodEntryData# " + response);
                if (response != null && response.length() > 0) {
                    showToast("success", Toast.LENGTH_LONG);

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("shouldTryPromptingForReview", true); //This is just an example extra.
                    setResult(RESULT_OK, returnIntent); //This is the important part.

                    finish();
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

}
