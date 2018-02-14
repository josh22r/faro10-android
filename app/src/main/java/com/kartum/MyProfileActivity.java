package com.kartum;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kartum.objects.GoalCenterRes;
import com.kartum.objects.ProfileRes;
import com.kartum.objects.Spinner;
import com.kartum.objects.TimeZOne;
import com.kartum.utils.AsyncResponseHandlerOk;
import com.kartum.utils.Debug;
import com.kartum.utils.HttpClient;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.URLs;
import com.kartum.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;

public class MyProfileActivity extends BaseActivity {

    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvEditProf)
    TextView tvEditProf;
    @BindView(R.id.tvEntriesPer)
    TextView tvEntriesPer;
    @BindView(R.id.tvExercisePer)
    TextView tvExercisePer;
    @BindView(R.id.tvMadicationPer)
    TextView tvMadicationPer;
    @BindView(R.id.tvSponsorPer)
    TextView tvSponsorPer;
    @BindView(R.id.imgErrow)
    ImageView imgErrow;
    @BindView(R.id.imgErrowDow)
    ImageView imgErrowDow;
    @BindView(R.id.imgErrowAccom)
    ImageView imgErrowAccom;
    @BindView(R.id.imgErrowDowAccom)
    ImageView imgErrowDowAccom;

    @BindView(R.id.imgEntries)
    ImageView imgEntries;
    @BindView(R.id.imgExercise)
    ImageView imgExercise;
    @BindView(R.id.imgMedication)
    ImageView imgMedication;
    @BindView(R.id.imgSponsored)
    ImageView imgSponsored;

    @BindView(R.id.editGender)
    EditText editGender;
    @BindView(R.id.editRace)
    EditText editRace;
    @BindView(R.id.editWeight)
    EditText editWeight;
    @BindView(R.id.editNationality)
    EditText editNationality;
    @BindView(R.id.editCountry)
    EditText editCountry;
    @BindView(R.id.editDiagnosis)
    EditText editDiagnosis;
    @BindView(R.id.editZipCode)
    EditText editZipCode;
    @BindView(R.id.editBirthDate)
    EditText editBirthDate;
    @BindView(R.id.editTimeZone)
    EditText editTimeZone;
    @BindView(R.id.cbPaidTrials)
    CheckBox cbPaidTrails;

    @BindView(R.id.pbEntries)
    ProgressBar pbEntries;
    @BindView(R.id.pbExercise)
    ProgressBar pbExercise;
    @BindView(R.id.pbMedication)
    ProgressBar pbMedication;
    @BindView(R.id.pbSponsored)
    ProgressBar pbSponsored;

    @BindView(R.id.llMyProfile)
    LinearLayout llMyProfile;
    @BindView(R.id.llDetail)
    LinearLayout llDetail;
    @BindView(R.id.llAcomplish)
    LinearLayout llAcomplish;
    @BindView(R.id.llAcomplishDetail)
    LinearLayout llAcomplishDetail;

    public ImageLoader imageLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        ButterKnife.bind(this);

        initDrawer(true);
        init();
    }

    private void init() {

        imageLoader = Utils.initImageLoader(MyProfileActivity.this);
        setEnable(false);

        tvEditProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tvEditProf.getText().toString().equalsIgnoreCase("Edit")) {
                    setEnable(true);
                    tvEditProf.setText("Save");
//                    getTimeZOne();
//                    setUserProfileData();

                } else {
                    if (validate()) {
                        tvEditProf.setText("Edit");
                        setEnable(false);
                        editProfileData();
                    }

                }

            }
        });

        llMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llDetail.getVisibility() == view.VISIBLE && imgErrow.getVisibility() == view.VISIBLE) {

                    llDetail.setVisibility(view.GONE);
                    imgErrow.setVisibility(view.GONE);
                    imgErrowDow.setVisibility(view.VISIBLE);

                } else {
                    llDetail.setVisibility(view.VISIBLE);
                    imgErrow.setVisibility(view.VISIBLE);
                    imgErrowDow.setVisibility(view.GONE);
                }

            }
        });
        llAcomplish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llAcomplishDetail.getVisibility() == view.VISIBLE && imgErrowAccom.getVisibility() == view.VISIBLE) {

                    llAcomplishDetail.setVisibility(view.GONE);
                    imgErrowAccom.setVisibility(view.GONE);
                    imgErrowDowAccom.setVisibility(view.VISIBLE);

                } else {
                    llAcomplishDetail.setVisibility(view.VISIBLE);
                    imgErrowAccom.setVisibility(view.VISIBLE);
                    imgErrowDowAccom.setVisibility(view.GONE);
                }

            }
        });

        editGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Spinner> data = new ArrayList<>();
                data.add(new Spinner("Male", "Male"));
                data.add(new Spinner("Female", "Female"));
                showSpinner("Gender", editGender, data, false, null);
            }
        });

        editRace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Spinner> data = new ArrayList<>();
                data.add(new Spinner("Caucasian", "Caucasian"));
                data.add(new Spinner("African American", "African American"));
                data.add(new Spinner("Hispanic", "Hispanic"));
                data.add(new Spinner("Asian", "Asian"));
                data.add(new Spinner("Other", "Other"));
                showSpinner("Race", editRace, data, false, null);
            }
        });

        editDiagnosis.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                ArrayList<Spinner> data = new ArrayList<>();
                data.add(new Spinner("Major Depressive Disorder (MDD)", "Major Depressive Disorder (MDD)"));
                data.add(new Spinner("Bipolar Depression", "Bipolar Depression"));
                data.add(new Spinner("Bipolar Mania", "Bipolar Mania"));
                data.add(new Spinner("Borderline", "Borderline"));
                data.add(new Spinner("Schizophrenia", "Schizophrenia"));
                data.add(new Spinner("Unknown", "Unknown"));
                data.add(new Spinner("No Disorder", "No Disorder"));
                showSpinner("Diagnosis", editDiagnosis, data, false, null);
            }
        });

        editCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpinner(getString(R.string.choose_country), editCountry, getCountryCodes(), true, null);
            }
        });

        editTimeZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ArrayList<Spinner> spinnerData = new ArrayList<>();
                TimeZOne time = Utils.getTimeZone(getActivity());
                 for (String s : time.timeZones) {
                     spinnerData.add(new Spinner(s));
                    }

//                for (String s : res.timeZones) {
//                        timezoneList.add(new Spinner(s));
//                    }

                showSpinner("TimeZone", editTimeZone, spinnerData, true, null);
//
//                showSpinner(getString(R.string.choose_timezone), editTimeZone, getCountryCodes(), true, null);
//                GETTIMEZONE();
            }
        });

        editBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(editBirthDate);
            }
        });

        getProfile();
        getGoalCenterData();

    }

    public void getProfile() {
        try {
            showDialog("");

            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_USER());
            Debug.e("getProfile", "" + body.build().toString());
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

        //        @Override
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
                Debug.e("", "getProfile# " + response);
                if (response != null && response.length() > 0) {

                    final ProfileRes res = new Gson().fromJson(response, new TypeToken<ProfileRes>() {
                    }.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utils.setPref(getActivity(), RequestParamsUtils.EMAIL, res.user.email);
                            fillData(res);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String onFailure) {
            Debug.e("", "onFailure# " + onFailure);
            dismissDialog();
        }
    }

//    public void getTimeZOne() {
//        try {
////            showDialog("");
//
//            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_USER_TIMEZONE());
//            body.addQueryParameter(RequestParamsUtils.TOKEN, Utils.getPref(getActivity(), RequestParamsUtils.TOKEN, ""));
//            body.addQueryParameter(RequestParamsUtils.EMAIL, Utils.getPref(getActivity(), RequestParamsUtils.EMAIL, ""));
//            Debug.e("getProfile", "" + body.build().toString());
//            Call call = HttpClient.newRequestGet(getActivity(), body.build().toString());
//            call.enqueue(new GetVersionaDataHandler(getActivity()));
//
//        } catch (
//                Exception e)
//
//        {
//            e.printStackTrace();
//        }
//    }
//
//    ArrayList<Spinner> timezoneList = new ArrayList<>();
//
//    private class GetVersionaDataHandler extends AsyncResponseHandlerOk {
//
//        public GetVersionaDataHandler(Activity context) {
//            super(context);
//        }
//
//        @Override
//        public void onStart() {
////            super.onStart();
//        }
//
//        @Override
//        public void onFinish() {
////            super.onFinish();
//            try {
//                dismissDialog();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onSuccess(final String response) {
//
//            try {
//                Debug.e("", "getTimeZOne# " + response);
//                if (response != null && response.length() > 0) {
//
//                    TimeZOne res = new Gson().fromJson(response, new TypeToken<TimeZOne>() {
//                    }.getType());
//
//                    timezoneList.clear();
//                    for (String s : res.timeZones) {
//                        timezoneList.add(new Spinner(s));
//                    }
//
//
////                    runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
//////                            Utils.setPref(getActivity(), Constant.TIME_ZONE, response);
//////                            fillData(res);
////                        }
////                    });
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(Throwable e, String content) {
//            Debug.e("", "onFailure# " + content);
//            dismissDialog();
//        }
//    }

//    public void getTimeZOne() {
//        try {
////            showDialog("");
//
//            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
//            body.addEncoded(RequestParamsUtils.TOKEN, Utils.getPref(getActivity(), RequestParamsUtils.TOKEN,""));
//            body.addEncoded(RequestParamsUtils.EMAIL, Utils.getPref(getActivity(), RequestParamsUtils.EMAIL,""));
//
//            Call call = HttpClient.newRequestPost(getActivity(), body.build(), URLs.GET_USER_TIMEZONE());
//            call.enqueue(new GetVersionDataHandler(getActivity()));
//
////            String Token = Utils.getPref(getActivity(), RequestParamsUtils.TOKEN,"");
////            HttpUrl.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
//////            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
////            body.addEncoded(RequestParamsUtils.SESSION, Token);
//////            params.put("session[email]", "chirag@gmail.com");
////            Debug.e("getTimeZOne", "" + body.build().toString());
////            Call call = HttpClient.newRequestGet(getActivity(), body.build(), URLs.GET_USER_TIMEZONE());
////            Call call = HttpClient.newRequestPost(getActivity(), body.build(), URLs.LOGIN());
////            call.enqueue(new GetVersionWeDataHandler(getActivity()));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private class GetVersionWeDataHandler extends AsyncResponseHandlerOk {
//
//        public GetVersionWeDataHandler(Activity context) {
//            super(context);
//        }
//
//        //        @Override
//        public void onStart() {
////            super.onStart();
//        }
//
//        @Override
//        public void onFinish() {
////            super.onFinish();
//            try {
//                dismissDialog();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onSuccess(String response) {
//
//            try {
//                Debug.e("", "getTimeZOne# " + response);
//                if (response != null && response.length() > 0) {
//
//                     Response res = new Gson().fromJson(response, new TypeToken<Response>() {
//                    }.getType());
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            fillData(res);
//                        }
//                    });
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(Throwable e, String onFailure) {
//            Debug.e("", "onFailure# " + onFailure);
//            dismissDialog();
//        }
//    }


    public void fillData(ProfileRes res) {

        tvUserName.setText(Utils.nullSafe(res.user.userId));
        editGender.setText(Utils.nullSafe(res.user.gender));
        editRace.setText(Utils.nullSafe(res.user.race));
        editNationality.setText(Utils.nullSafe(res.user.nationality));
        editCountry.setText(Utils.nullSafe(res.user.country));
        editDiagnosis.setText(Utils.nullSafe(res.user.diagnosis));
        editWeight.setText(Utils.nullSafe("" + res.user.weight));
        editZipCode.setText(Utils.nullSafe("" + res.user.zipCode));
        editTimeZone.setText(Utils.nullSafe("" + res.user.time_zone));
        editBirthDate.setText(Utils.parseTime("" + res.user.dob, "yyyy-MM-dd", "MM/dd/yy"));
        cbPaidTrails.setChecked(res.user.trialInterested);

    }

    public void setEnable(boolean b) {

        editGender.setEnabled(b);
        editRace.setEnabled(b);
        editCountry.setEnabled(b);
        editDiagnosis.setEnabled(b);
        editDiagnosis.setEnabled(b);
        editBirthDate.setEnabled(b);
        editWeight.setEnabled(b);
        editNationality.setEnabled(b);
        editZipCode.setEnabled(b);
        editTimeZone.setEnabled(b);
        cbPaidTrails.setEnabled(b);

    }

    private boolean validate() {

        if (editZipCode.getText().toString().length() <= 4) {
            showToast(getString(R.string.err_zipcode), Toast.LENGTH_SHORT);
            return false;
        }
        if (editZipCode.getText().toString().length() >= 7) {
            showToast(getString(R.string.err_zipcodes), Toast.LENGTH_SHORT);
            return false;
        }

        if (editNationality.getText().toString().length() <= 0) {
            showToast(getString(R.string.err_nationality), Toast.LENGTH_SHORT);
            return false;
        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(editNationality.getText()).matches()) {
//            showToast(getString(R.string.err_email_invalid), Toast.LENGTH_SHORT);
////            invalidEmailErrorPopup();
//            return false;
//        }

        return true;
    }

    public void editProfileData() {
        try {
            showDialog("");

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
            body.addEncoded(RequestParamsUtils.GENDER, editGender.getText().toString().trim());
            body.addEncoded(RequestParamsUtils.RACE, editRace.getText().toString().trim());
            body.addEncoded(RequestParamsUtils.WEIGHT, editWeight.getText().toString().trim());
            body.addEncoded(RequestParamsUtils.NATIONALITY, editNationality.getText().toString().trim());
            body.addEncoded(RequestParamsUtils.COUNTRY, editCountry.getText().toString().trim());
            body.addEncoded(RequestParamsUtils.DIAGNOSIS, editDiagnosis.getText().toString().trim());
//            body.addEncoded(RequestParamsUtils.BIRTHDATE, editBirthDate.getText().toString().trim());
            body.addEncoded(RequestParamsUtils.BIRTHDATE, Utils.parseTime("" + editBirthDate.getText(), "MM/dd/yy", "yyyy-MM-dd"));
            body.addEncoded(RequestParamsUtils.ZIPCODE, editZipCode.getText().toString().trim());
            body.addEncoded(RequestParamsUtils.USER_TIMEZONE, editTimeZone.getText().toString().trim());
            body.addEncoded(RequestParamsUtils.PAID_TRIAL, cbPaidTrails.isChecked() ? "1" : "0");

            Debug.e("editProfileData", "" + body);
            Call call = HttpClient.newRequestPut(getActivity(), body.build(), URLs.GET_USER());
            call.enqueue(new GetVersionData(getActivity()));

        } catch (
                Exception e)

        {
            e.printStackTrace();
        }
    }

    private class GetVersionData extends AsyncResponseHandlerOk {

        public GetVersionData(Activity context) {
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
                Debug.e("", "editProfileData# " + response);
                if (response != null && response.length() > 0) {

                    final ProfileRes res = new Gson().fromJson(response, new TypeToken<ProfileRes>() {
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
            dismissDialog();
        }
    }

    public void showDatePicker(final EditText editText) {
        DatePickerDialog.Builder builder = null;
        builder = new DatePickerDialog.Builder(R.style.Material_App_Dialog_DatePicker_Light) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
//                String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
//                editText.setText(Utils.parseTime(dialog.getDate(), "yyyy-MM-dd"));
                editText.setText(Utils.parseTime(dialog.getDate(), "MM/dd/yy"));
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


    public void getGoalCenterData() {
        try {
//            showEditDialog("");

//            RequestParams params = RequestParamsUtils.newRequestParams(getActivity());
//            Debug.e("getGoalCenterData-->>", "" + params.toString());
//            AsyncHttpClient client = AsyncHttpRequest.newRequest(getActivity());
//            client.get(URLs.GET_GOALCENTER(), params, new GetVersionDataHandle(getActivity()));


            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_GOALCENTER());
//            Debug.e("getGoalCenterData", "" + body);
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
                Debug.e("", "getGoalCenterData# " + response);
                if (response != null && response.length() > 0) {

                    final GoalCenterRes res = new Gson().fromJson(response, new TypeToken<GoalCenterRes>() {
                    }.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fillGoalData(res);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
            Debug.e("onFailure", "" + content);
//            dismissDialog();
        }

    }

    public void fillGoalData(GoalCenterRes res) {

//        int progress = 10;
        pbEntries.setProgress(Math.round(res.entries.goalProgression));
        LayerDrawable stars = (LayerDrawable) pbEntries.getProgressDrawable();

        if (Math.round(res.entries.goalProgression) <= 25) {

            stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.theme_red), PorterDuff.Mode.SRC_ATOP);

        } else if (Math.round(res.entries.goalProgression) > 25 && Math.round(res.entries.goalProgression) <= 50) {

            stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.theme_orange), PorterDuff.Mode.SRC_ATOP);
        } else if (Math.round(res.entries.goalProgression) > 50 && Math.round(res.entries.goalProgression) <= 75) {

            stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.theme_green), PorterDuff.Mode.SRC_ATOP);
        } else {
            stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.theme_blue), PorterDuff.Mode.SRC_ATOP);
        }

        tvEntriesPer.setText(Utils.nullSafe(res.entries.goalProgression + "% " + "" + "/ " + res.entries.target));
        if (!StringUtils.isEmpty(res.entries.medal)) {
            imageLoader.displayImage(res.entries.medal, imgEntries);
        }

        pbExercise.setProgress(Math.round(res.exercise.goalProgression));
        LayerDrawable stars1 = (LayerDrawable) pbExercise.getProgressDrawable();

        if (Math.round(res.exercise.goalProgression) <= 25) {

            stars1.getDrawable(2).setColorFilter(getResources().getColor(R.color.theme_red), PorterDuff.Mode.SRC_ATOP);

        } else if (Math.round(res.exercise.goalProgression) > 25 && Math.round(res.exercise.goalProgression) <= 50) {

            stars1.getDrawable(2).setColorFilter(getResources().getColor(R.color.theme_orange), PorterDuff.Mode.SRC_ATOP);
        } else if (Math.round(res.exercise.goalProgression) > 50 && Math.round(res.exercise.goalProgression) <= 75) {

            stars1.getDrawable(2).setColorFilter(getResources().getColor(R.color.theme_green), PorterDuff.Mode.SRC_ATOP);
        } else {
            stars1.getDrawable(2).setColorFilter(getResources().getColor(R.color.theme_blue), PorterDuff.Mode.SRC_ATOP);
        }
        tvExercisePer.setText(Utils.nullSafe(res.exercise.goalProgression + "% " + "" + "/ " + res.exercise.target));
        if (!StringUtils.isEmpty(res.exercise.medal)) {
            imageLoader.displayImage(res.exercise.medal, imgExercise);
        }

        pbMedication.setProgress(Math.round(res.medication.avgConsistency));
        LayerDrawable stars2 = (LayerDrawable) pbMedication.getProgressDrawable();

        if (Math.round(res.medication.avgConsistency) <= 25) {

            stars2.getDrawable(2).setColorFilter(getResources().getColor(R.color.theme_red), PorterDuff.Mode.SRC_ATOP);

        } else if (Math.round(res.medication.avgConsistency) > 25 && Math.round(res.medication.avgConsistency) <= 50) {

            stars2.getDrawable(2).setColorFilter(getResources().getColor(R.color.theme_orange), PorterDuff.Mode.SRC_ATOP);
        } else if (Math.round(res.medication.avgConsistency) > 50 && Math.round(res.medication.avgConsistency) <= 75) {

            stars2.getDrawable(2).setColorFilter(getResources().getColor(R.color.theme_green), PorterDuff.Mode.SRC_ATOP);
        } else {
            stars2.getDrawable(2).setColorFilter(getResources().getColor(R.color.theme_blue), PorterDuff.Mode.SRC_ATOP);
        }
        tvMadicationPer.setText(Utils.nullSafe(res.medication.avgConsistency + "% " + "" + "/ " + res.medication.target));
        if (!StringUtils.isEmpty(res.medication.medal)) {
            imageLoader.displayImage(res.medication.medal, imgMedication);
        }

        pbSponsored.setProgress(Math.round(res.sponsored.goalProgression));
        LayerDrawable stars3 = (LayerDrawable) pbSponsored.getProgressDrawable();

        if (Math.round(res.sponsored.goalProgression) <= 25) {

            stars3.getDrawable(2).setColorFilter(getResources().getColor(R.color.theme_red), PorterDuff.Mode.SRC_ATOP);

        } else if (Math.round(res.sponsored.goalProgression) > 25 && Math.round(res.sponsored.goalProgression) <= 50) {

            stars3.getDrawable(2).setColorFilter(getResources().getColor(R.color.theme_orange), PorterDuff.Mode.SRC_ATOP);
        } else if (Math.round(res.sponsored.goalProgression) > 50 && Math.round(res.sponsored.goalProgression) <= 75) {

            stars3.getDrawable(2).setColorFilter(getResources().getColor(R.color.theme_green), PorterDuff.Mode.SRC_ATOP);
        } else {
            stars3.getDrawable(2).setColorFilter(getResources().getColor(R.color.theme_blue), PorterDuff.Mode.SRC_ATOP);
        }
        tvSponsorPer.setText(Utils.nullSafe(res.sponsored.goalProgression + "% " + "" + "/ " + res.sponsored.target));
        if (!StringUtils.isEmpty(res.sponsored.medal)) {
            imageLoader.displayImage(res.sponsored.medal, imgSponsored);
        }
    }
}

