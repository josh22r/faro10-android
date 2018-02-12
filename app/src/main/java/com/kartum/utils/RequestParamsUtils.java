package com.kartum.utils;

import android.content.Context;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestParamsUtils {

    public static final String USER_ID = "user_id";
    public static final String TOKEN = "session[token]";
    public static final String APIKEY = "APIKey";

    public static final String DEVICE_TYPE = "device_type";
    public static final String DEVICE_ID = "device_id";
    public static final String GCMKEY = "gcmkey";
    public static final String DEVICE_TOKEN = "device_token";

    public static final String EMAIL = "session[email]";
    public static final String PASSWORD = "session[password]";

    public static final String USERNAME = "user[user_id]";
    public static final String USER_EMAIL = "user[email]";
    public static final String USER_PASSWORD = "user[password]";
    public static final String GENDER = "user[gender]";
    public static final String PAID_TRIAL = "user[paidTrial]";
    public static final String FORGOT_PASSWORD = "email";

    public static final String RACE = "user[race]";
    public static final String NATIONALITY = "user[nationality]";
    public static final String WEIGHT = "user[weight]";
    public static final String DIAGNOSIS = "user[diagnosis]";
    public static final String ZIPCODE = "user[zip_code]";
    public static final String OCCUPATION = "user[occupation]";
    public static final String COUNTRY = "user[country]";
    public static final String BIRTHDATE = "user[dob]";
    public static final String USER_TIMEZONE = "user[time_zone]";
    public static final String UPDATED_AT = "updated_at";

    public static final String WORK_LIFE = "entry[work_life]";
    public static final String SOCIAL_LIFE = "entry[social_life]";
    public static final String FAMILY_LIFE = "entry[family_life]";
    public static final String ANXIETY = "entry[anxiety]";
    public static final String ENERGY = "entry[energy]";
    public static final String PESSIMISM = "entry[pessimism]";
    public static final String CONCENTRATION = "entry[concentration]";
    public static final String INITIATIVE = "entry[initiative]";
    //    public static final String REPORTED_MOOD = "entry[reported_mood]";
    public static final String FEELING = "entry[feeling]";

    public static final String NAUSEA = "entry[nausea]";
    public static final String HEADACHE = "entry[headache]";
    public static final String RESTLESSNESS = "entry[restlessness]";
    public static final String NIGHTMARE = "entry[nightmare]";
    public static final String SUICIDE_THOUGHT = "entry[suicide_thought]";
    public static final String PANIC_ATTACK = "entry[panic_attack]";
    public static final String HOSPITALIZATION = "entry[hospitalization]";
    public static final String HALLUCINATION = "entry[hallucination]";
    public static final String SYMP_WEIGHT = "entry[weight]";
    public static final String AUDITORY_HALLUCINATION = "";
    public static final String VISUAL_HALLUCINATION = "";

    public static final String SLEEP = "entry[hrs_slept]";
    public static final String APPETITE = "entry[appetite]";
    public static final String JOY = "entry[zest]";

    public static final String SYMPTOM_IDS = "symptom_ids[]";
    public static final String SYMPTOM_NAME = "symptom[name]";

    public static final String MIGRAINES = "";
    public static final String CONSTIPATION = "";
    public static final String WEAKNESS = "";
    public static final String LOWER_BACK_PAIN = "";
    public static final String FATIGUE = "";
    public static final String IBS = "";
    public static final String ATTENDED_SESSION = "entry[attended_session]";
    public static final String SELF_HARM = "entry[self_harm]";

    public static final String JOURNAL = "entry[journal]";
    public static final String EXERCISE = "entry[activity]";

    public static final String REMINDER = "prescription[reminder]";
    public static final String DRUG_ID = "prescription[drug_id]";
    public static final String DOSAGE = "prescription[dosage]";
    public static final String STARTED = "prescription[started]";
    public static final String AS_NEEDED = "prescription[as_needed]";

    public static final String ENDED = "prescription[ended]";
    public static final String REASON = "prescription[reason]";

    public static final String PRESCRIPTION_ID = "prescription_id";
    public static final String TIMES_TAKEN = "times_taken";
    public static final String DAY_TAKEN = "day_taken";

    public static final String OBS_EMAIL = "email";
    public static final String RELATIONSHIP = "relationship";
    public static final String MEDS = "meds";
    public static final String GUARDIAN = "guardian";
    public static final String OBS_MEDS = "observation[meds]";
    public static final String WEEKS = "weeks";

    public static final String END_DATE = "end_date";
    public static final String START_DATE = "start_date";

    public static final String OBS_MOOD = "";
    public static final String OBS_HOPELESSNESS = "";
    public static final String OBSERVATION_ID = "observer_entry[observation_id]";
    public static final String OBSERVED_AT = "observer_entry[observed_at]";
    public static final String OBS_FEELING = "observer_entry[feeling]";
    public static final String OBS_JOURNAL = "observer_entry[journal]";
    public static final String OBS_SOCIAL_INTERACTION = "observer_entry[social_interaction]";
    public static final String OBS_WORK_LIFE = "observer_entry[work_life]";
    public static final String OBS_FAMILY_LIFE = "observer_entry[family_life]";
    public static final String OBS_DELUSIONAL = "observer_entry[delusional]";
    public static final String OBS_HALLUCINATION = "observer_entry[hallucination]";
    public static final String OBS_HYPERACTIVE = "observer_entry[hyperactive]";
    public static final String OBS_ENERGY = "observer_entry[energy]";
    public static final String OBS_ACTIVITY = "observer_entry[activity]";
    public static final String OBS_ZEST = "observer_entry[zest]";
    public static final String OBS_DANGEROUS_BEHAVIOR = "observer_entry[dangerous_behavior]";
    public static final String OBS_SUBSTANCE_ABUSE = "observer_entry[substance_abuse]";

    public static final String REFERENCE_AGENT = "reference_agent";

//    public static RequestParams newRequestParams(Context c) {
//        RequestParams params = new RequestParams();
//        params.put(TOKEN, Utils.getPref(c, RequestParamsUtils.TOKEN, ""));
//        params.put(EMAIL, Utils.getPref(c, RequestParamsUtils.EMAIL, ""));
////        params.put(APIKEY, Constant.API_KEY);
//
//        return params;
//    }

//    public static RequestParams newRequestParamsd(Context c) {
//        FormEncoding fe = new FormEncoding.Builder()
//                .add("name", "Lorem Ipsum")
//                .add("occupation", "Filler Text")
//                .build();
//    }

    public static RequestBody newRequestBody(Context context, String json) {
        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        return body;
    }

    public static FormBody.Builder newRequestFormBody(Context c) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.addEncoded(TOKEN, Utils.getPref(c, RequestParamsUtils.TOKEN, ""));
        builder.add(EMAIL, Utils.getPref(c, RequestParamsUtils.EMAIL, ""));

        return builder;
    }

    public static HttpUrl.Builder newRequestUrlBuilder(Context c, String url) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter(TOKEN, Utils.getPref(c, RequestParamsUtils.TOKEN, ""));
        urlBuilder.addQueryParameter(EMAIL, Utils.getPref(c, RequestParamsUtils.EMAIL, ""));

        return urlBuilder;

    }
}
