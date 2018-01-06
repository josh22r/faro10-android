package com.kartum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kartum.R;
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

public class PrescripChildDataAdapter extends RecyclerView.Adapter<PrescripChildDataAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    public JSONArray data = new JSONArray();
    Context context;
    Eventlistener mEventlistener;

    public PrescripChildDataAdapter(Context c) {
        this.context = c;
        imageLoader = Utils.initImageLoader(context);
    }

//    public void addAll(JSONArray array) {
//        this.data.
//        notifyDataSetChanged();
//    }

    public void add(JSONObject mData) {
        data.put(mData);
        notifyDataSetChanged();
    }

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
        final View v = inflater.inflate(R.layout.item_observ_presc, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        try {
            final JSONObject item = data.getJSONObject(position);
            holder.tvDrugName.setText(Utils.nullSafe(item.getJSONObject("drug").getString("friendly_name")));
            holder.tvDosage.setText(Utils.nullSafe(item.getString("dosage")));
//            2017-08-19T00:00:00.000Z
            holder.tvDate.setText(Utils.parseTimeUTCtoDefault(item.getString("started"), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "MM/dd/yy"));
            holder.tvTime.setText(Utils.nullSafe(item.getString("total_times_taken")));
            holder.tvDuration.setText(Utils.nullSafe(item.getString("duration")));
            holder.tvConsistency.setText(Utils.nullSafe(item.getString("consistency")));
//            holder.tvRole.setText(Utils.nullSafe(item.getString("role")));


        } catch (JSONException e) {
            e.printStackTrace();
        }

//        holder.imgObserver.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mEventlistener != null) {
//                    mEventlistener.OnObservclick(position, view);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return data.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDrugName)
        TextView tvDrugName;
        @BindView(R.id.tvDosage)
        TextView tvDosage;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvDuration)
        TextView tvDuration;
        @BindView(R.id.tvConsistency)
        TextView tvConsistency;

//        @BindView(R.id.container)
//        View container;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface Eventlistener {

        void OnObservclick(int position, View view);

//        void OnTimeBoxclick(int position);
//
//        void OnItemDetailtviewclick(int position);
    }

    public void setmEventlistener(Eventlistener eventlistener) {
        this.mEventlistener = eventlistener;
    }
}
