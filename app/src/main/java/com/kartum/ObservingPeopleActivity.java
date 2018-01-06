package com.kartum;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.common.view.SimpleListDividerDecorator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kartum.adapter.CareTeamAdapter;
import com.kartum.adapter.ObservingPeopleListAdapter;
import com.kartum.objects.CareTeamRes;
import com.kartum.objects.Response;
import com.kartum.utils.AsyncResponseHandlerOk;
import com.kartum.utils.Constant;
import com.kartum.utils.Debug;
import com.kartum.utils.HttpClient;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.URLs;
import com.kartum.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;

public class ObservingPeopleActivity extends BaseActivity {

    @BindView(R.id.rvObseringPeople)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    public ObservingPeopleListAdapter mAdapter;
    public CareTeamAdapter mCareTeamAdapter;

    @BindView(R.id.tvBackArrow)
    TextView tvBackArrow;
    @BindView(R.id.tvObsFAQs)
    TextView tvObsFAQs;
    @BindView(R.id.tvObsFAQs1)
    ImageView tvObsFAQs1;
    @BindView(R.id.llObservHistory)
    LinearLayout llObservHistory;
    @BindView(R.id.llPrescription)
    LinearLayout llPrescription;

    @BindView(R.id.llPlaceHolder)
    View llPlaceHolder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observing);
        ButterKnife.bind(this);

        initDrawer(true);
        init();
    }

    private void init() {
        //setTitleText("Faro10");

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
        mAdapter = new ObservingPeopleListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        tvBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ObservingActivity.super.onBackPressed();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        tvObsFAQs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ObserverFAQActivity.class);
                startActivity(intent);
            }
        });
        tvObsFAQs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ObserverFAQActivity.class);
                startActivity(intent);
            }
        });

        llObservHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ObservationHistoryActivity.class);
                startActivity(i);
            }
        });

        llPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ObservePrescriptionsActivity.class);
                startActivity(i);
            }
        });

        mAdapter.setmEventlistener(new ObservingPeopleListAdapter.Eventlistener() {
            @Override
            public void OnObservclick(int position) {
                Intent i = new Intent(getActivity(), MakeObserveActivity.class);
                JSONObject obj = mAdapter.getItem(position);
                i.putExtra("data", new Gson().toJson(obj));
                startActivity(i);
            }

            @Override
            public void OnDeleteclick(int position) {
                JSONObject obj = mAdapter.getItem(position);
                try {
                    deleteObserverPopup(obj.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void OnCareTeamClick(int position) {
                careTeamPopup(mAdapter.getObjectId(position));
                Debug.e("CareTeam id", "" + mAdapter.getObjectId(position));
            }
        });


        getObservingPeopleData();
    }

    Dialog dialog1;

    public void showCallDialog(final CareTeamRes.Clinician data) {

        dialog1 = new Dialog(getActivity());
        dialog1.setCanceledOnTouchOutside(true);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.popup_call_suicide);

        TextView tvNo = (TextView) dialog1.findViewById(R.id.tvNo);
        TextView tvYes = (TextView) dialog1.findViewById(R.id.tvYes);
        TextView tvNumber = (TextView) dialog1.findViewById(R.id.tvNumber);
        tvNumber.setText(data.clinicPhone.toString());

        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + data.clinicPhone));
                startActivity(callIntent);
                dialog1.dismiss();
            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                dialog.dismiss();
            }
        });
        dialog1.show();
    }

    private void deleteObserverPopup(final String id) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
//                .title(R.string.logout_title)
                .content(R.string.obs_delete)
                .positiveText(R.string.btn_ok)
                .negativeText(R.string.btn_cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        deleteObserveData(id);
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {

                    }
                });

        MaterialDialog dialog = builder.build();
        dialog.show();
    }

    public void careTeamPopup(int id) {
        dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_care_team);

        mRecyclerView = (RecyclerView) dialog.findViewById(R.id.mRecyclerView);
        TextView tvClose = (TextView) dialog.findViewById(R.id.tvClose);
//        TextView tvSave = (TextView) dialog.findViewById(R.id.tvSave);

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
        mCareTeamAdapter = new CareTeamAdapter(this);
        mRecyclerView.setAdapter(mCareTeamAdapter);

        getCareTeamData(id);

        mCareTeamAdapter.setmEventlistener(new CareTeamAdapter.Eventlistener() {
            @Override
            public void OnNumberClick(int position) {
                CareTeamRes.Clinician data = mCareTeamAdapter.getItem(position);
                dialog.dismiss();
                showCallDialog(data);
            }

            @Override
            public void OnAddresClick(int position) {
                CareTeamRes.Clinician data = mCareTeamAdapter.getItem(position);

//                Uri gmmIntentUri = Uri.parse("geo:0,0?q=1600 Amphitheatre Parkway, Mountain+View, California");
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                startActivity(mapIntent);

                String addressString = "";
                if (!data.clinicStreet.isEmpty()) {
                    addressString = data.clinicStreet;
                }

                if (!data.clinicCity.isEmpty()) {
                    if (addressString.isEmpty()) {
                        addressString = data.clinicCity;
                    } else {
                        addressString = addressString + "," + data.clinicCity;
                    }
                }
                if (!data.clinicState.isEmpty()) {
                    if (addressString.isEmpty()) {
                        addressString = data.clinicState;
                    } else {
                        addressString = addressString + "," + data.clinicState;
                    }
                }
                if (data.clinicZip == 0) {
                    if (addressString.isEmpty()) {
                        addressString = "" + data.clinicZip;
                    } else {
                        addressString = addressString + "," + data.clinicZip;
                    }
                }

                if (!addressString.isEmpty()) {

//                    try {
//                        String url = "https://www.google.com/maps/search/?api=1&query=" + addressString;
//                        Intent i = new Intent(Intent.ACTION_VIEW);
//                        i.setData(Uri.parse(url));
//                        startActivity(i);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                    try {
                        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + addressString);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    } catch (ActivityNotFoundException e) {
                        String url = "https://www.google.com/maps/search/?api=1&query=" + addressString;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    showToast("Address is not available", Toast.LENGTH_LONG);
                }

//                String url = "https://www.google.com/maps/search/?api=1&query=" + data.clinicStreet + "," + data.clinicCity + "," + data.clinicState + "," + data.clinicZip;
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);

                dialog.dismiss();
            }

        });

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void getObservingPeopleData() {
        try {

            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_OBSERVEES());
            Call call = HttpClient.newRequestGet(getActivity(), body.build().toString());
            call.enqueue(new GetVersionDataHandle(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    JSONArray array = new JSONArray();
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
                dismissDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSuccess(final String response) {

            try {
                Debug.e("", "getObservingPeopleData# " + response);
                if (response != null && response.length() > 0) {

                    Utils.setPref(getActivity(), Constant.USERDATA, response);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject issueObj = null;
                            try {
                                issueObj = new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Iterator iterator = issueObj.keys();
                            JSONArray array = new JSONArray();

                            while (iterator.hasNext()) {
                                String key = (String) iterator.next();
                                JSONObject object = null;
                                try {
                                    object = issueObj.getJSONObject(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    object.put("name", key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                array.put(object);
//                                obj.put(object);
                            }
                            for (int i = 0; i < array.length(); i++) {
                                try {
                                    mAdapter.add(array.getJSONObject(i));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
//                            Debug.e("Array", "" + array);
                        }
                    });
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    refreshPlaceHolder();
                }
            });

        }

        @Override
        public void onFailure(Throwable e, String content) {
            Debug.e("", "onFailure# " + content);
            dismissDialog();
        }
    }

    public void refreshPlaceHolder() {
        if (mRecyclerView.getAdapter().getItemCount() > 0) {
            llPlaceHolder.setVisibility(View.GONE);
        } else {
            llPlaceHolder.setVisibility(View.VISIBLE);
        }

    }

    public void getCareTeamData(int id) {
        try {
            showDialog("");
            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_CARE_TEAM() + id + "/clinicians");
            Call call = HttpClient.newRequestGet(getActivity(), body.build().toString());
            call.enqueue(new GetVersionData(getActivity()));

        } catch (Exception e) {
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
        public void onSuccess(final String response) {

            try {
                Debug.e("", "getCareTeamData# " + response);
                if (response != null && response.length() > 0) {

                    final CareTeamRes res = new Gson().fromJson(response, new TypeToken<CareTeamRes>() {
                    }.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCareTeamAdapter.addAll(res.clinicians);
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

    public void deleteObserveData(String id) {
        try {
            showDialog("");

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
            Call call = HttpClient.newRequestDelete(getActivity(), body.build(), URLs.DELETE_OBSERVER() + id);
            call.enqueue(new GetVersionDataHandl(getActivity()));

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
        public void onSuccess(String response) {

            try {
                Debug.e("", "deleteObserveData# " + response);
                if (response != null && response.length() > 0) {

                    Response res = new Gson().fromJson(response, new TypeToken<Response>() {
                    }.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //
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
}
