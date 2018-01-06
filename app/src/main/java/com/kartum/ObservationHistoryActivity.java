package com.kartum;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.common.view.SimpleListDividerDecorator;
import com.kartum.adapter.ObservationHistoryAdapter;
import com.kartum.utils.AsyncResponseHandlerOk;
import com.kartum.utils.Debug;
import com.kartum.utils.HttpClient;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.HttpUrl;

import static com.kartum.R.id.rvObservationHistory;

public class ObservationHistoryActivity extends BaseActivity {

    @BindView(rvObservationHistory)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    public ObservationHistoryAdapter mAdapter;

    @BindView(R.id.tvBackArrow)
    TextView tvBackArrow;
    @BindView(R.id.editSearch)
    EditText editSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_history);
        ButterKnife.bind(this);

        initDrawer(true);
        init();
    }

    private void init() {
        //setTitleText("Faro10");

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
        mAdapter = new ObservationHistoryAdapter(this);
        mAdapter.setFilterable(true);
        mRecyclerView.setAdapter(mAdapter);

        tvBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObservationHistoryActivity.super.onBackPressed();
            }
        });

//        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
////                    performSearch(editSearch.getText().toString().trim());
//                    mAdapter.getFilter().filter(editSearch.getText().toString().trim());
//                    return true;
//                }
//                return false;
//            }
//        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() >= 1) {
                    mAdapter.getFilter().filter(editable.toString().trim());
                } else {
                    mAdapter.getFilter().filter("");
                }

            }
        });

        getObservationHistoryData();
    }


    public void getObservationHistoryData() {
        try {
            showDialog("");

            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_HISTORY());
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
                dismissDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSuccess(final String response) {

            try {
                Debug.e("", "getObservationHistoryData# " + response);
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
                                    object.put("isExpanded", 1);
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
            Debug.e("", "onFailure# " + content);
            dismissDialog();
        }
    }
}
