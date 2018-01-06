package com.kartum;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kartum.adapter.PhysicalSymptomDragNDropAdapter;
import com.kartum.objects.SymptomTrackRes;
import com.kartum.utils.AsyncResponseHandlerOk;
import com.kartum.utils.Debug;
import com.kartum.utils.EditItemTouchHelperCallback;
import com.kartum.utils.HttpClient;
import com.kartum.utils.OnStartDragListener;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.URLs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.HttpUrl;

public class DragActivity extends BaseActivity implements OnStartDragListener {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    RecyclerView.LayoutManager layoutManager;
    public PhysicalSymptomDragNDropAdapter symAdapter;

    ItemTouchHelper mItemTouchHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
        ButterKnife.bind(this);

        initDrawer(true);
        init();
    }

    private void init() {
        //setTitleText("Faro10");

        List<SymptomTrackRes.Symptom> list = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        symAdapter = new PhysicalSymptomDragNDropAdapter(this, list, this);
        recycler_view.setAdapter(symAdapter);

        recycler_view.setHasFixedSize(true);
        ItemTouchHelper.Callback callback =
                new EditItemTouchHelperCallback(symAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recycler_view);

        symAdapter.setEventlistener(new PhysicalSymptomDragNDropAdapter.Eventlistener() {

            @Override
            public void onItemviewClick(int position) {
                symAdapter.changeSelection(position, true);
            }
        });

        getSymptomTrackedData();

    }

    public void getSymptomTrackedData() {
        try {
            showDialog("");

            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_SYMPTOMS_TRACKED());
            Call call = HttpClient.newRequestGet(getActivity(), body.build().toString());
            call.enqueue(new GetVersionData(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    HashSet<String> selectedId = new HashSet<>();

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
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
                Debug.e("", "getSymptomTrackedData# " + response);
                if (response != null && response.length() > 0) {

                    final SymptomTrackRes symptomTrackRes = new Gson().fromJson(response, new TypeToken<SymptomTrackRes>() {
                    }.getType());


//                    selectedId.clear();
//                    for (SymptomTrackRes.Symptom select : symptomTrackRes.symptoms) {
//                        selectedId.add("" + select.id);
//                    }
//                    Utils.setPref(getActivity(), "pref", response);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

//                            symAdapter.clear();
//                            for (int i = 0; i < symptomTrackRes.symptoms.size(); i++) {
//
//                                if (Utils.parseTimeUTCtoDefault(symptomTrackRes.symptoms.get(i).started, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "MM/dd/yy").equalsIgnoreCase(tvGetDate.getText().toString())) {
//                                    symAdapter.add(symptomTrackRes.symptoms.get(i));
//                                }
//                            }

                            symAdapter.addAll(symptomTrackRes.symptoms);
//                            list.addAll(symptomTrackRes.symptoms);


                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
            Debug.e("", "getSymptomTrackedData onFailure# " + content);
            dismissDialog();
        }
    }
}
