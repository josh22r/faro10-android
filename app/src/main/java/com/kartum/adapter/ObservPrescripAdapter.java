package com.kartum.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.view.SimpleListDividerDecorator;
import com.kartum.R;
import com.kartum.utils.Debug;
import com.kartum.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by om on 7/11/2017.
 */

public class ObservPrescripAdapter extends RecyclerView.Adapter<ObservPrescripAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    public JSONArray data = new JSONArray();
    Context context;
    Eventlistener mEventlistener;

    public ObservPrescripAdapter(Context c) {
        this.context = c;
        imageLoader = Utils.initImageLoader(context);
    }

//    public void addAll(JsonObject obj) {
//        Iterator iterator = obj.keys();
//        while (iterator.hasNext()) {
//            String key = (String) iterator.next();
//            try {
//                JSONArray array = obj.getJSONArray(key);
//                data.put(key, array);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        notifyDataSetChanged();
//    }

    public void add(JSONObject mData) {
        data.put(mData);
        notifyDataSetChanged();
    }

//    public void toggleExpand(int position) {
//        for (int i = 0; i < getItemCount(); i++) {
//            if (position == i) {
//                try {
//                    data.getJSONObject(i).getString("isExpanded").equalsIgnoreCase("1") {
//                        data.getJSONObject(i).getString("isExpanded") = "0";
//                    }
//                    notifyDataSetChanged();
//                    break;
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

//    public JSONArray getAll() {
//        return data;
//    }

//    public int getObjectId(int position) {
//        try {
//            return data.getJSONObject(position).getInt("id");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_obs_prescrip, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.layoutManager = new LinearLayoutManager(context);
        holder.mRecyclerView.setLayoutManager(holder.layoutManager);
        holder.mRecyclerView.addItemDecoration(new SimpleListDividerDecorator(context.getResources().getDrawable(R.drawable.list_divider), true));
        holder.mAdapter = new PrescripChildDataAdapter(context);
        holder.mRecyclerView.setAdapter(holder.mAdapter);
//
        try {
            final JSONObject item = data.getJSONObject(position);
            holder.tvObsName.setText(Utils.nullSafe(item.getString("name")));

            for (int i = 0; i < item.getJSONArray("array").length(); i++) {
                holder.mAdapter.add(item.getJSONArray("array").getJSONObject(i));
            }
            Debug.e("", "array -->> " + item.getJSONArray("array"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.llUpDowan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.llChild.getVisibility() == View.VISIBLE && holder.imgMsgErrowDow.getVisibility() == View.VISIBLE) {
                    holder.llChild.setVisibility(View.GONE);
                    holder.imgMsgErrowDow.setVisibility(View.GONE);
                    holder.imgMsgErrowUp.setVisibility(View.VISIBLE);
                } else {
                    holder.llChild.setVisibility(View.VISIBLE);
                    holder.imgMsgErrowDow.setVisibility(View.VISIBLE);
                    holder.imgMsgErrowUp.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgMsgErrowUp)
        ImageView imgMsgErrowUp;
        @BindView(R.id.imgMsgErrowDow)
        ImageView imgMsgErrowDow;
        @BindView(R.id.llUpDowan)
        LinearLayout llUpDowan;
        @BindView(R.id.llChild)
        LinearLayout llChild;
        @BindView(R.id.tvObsName)
        TextView tvObsName;
        @BindView(R.id.mRecyclerView)
        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager layoutManager;
        public PrescripChildDataAdapter mAdapter;
        @BindView(R.id.container)
        View container;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface Eventlistener {
        void OnItemViewClick(int position);
    }

    public void setmEventlistener(Eventlistener eventlistener) {
        this.mEventlistener = eventlistener;
    }
}
