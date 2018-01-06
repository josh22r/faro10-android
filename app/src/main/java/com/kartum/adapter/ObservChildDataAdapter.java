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

public class ObservChildDataAdapter extends RecyclerView.Adapter<ObservChildDataAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    public JSONArray data = new JSONArray();
    Context context;
    Eventlistener mEventlistener;

    public ObservChildDataAdapter(Context c) {
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
        final View v = inflater.inflate(R.layout.item_observ_child_data, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        try {
            final JSONObject item = data.getJSONObject(position);
            holder.tvMood.setText(Utils.nullSafe(item.getString("feeling")));
            holder.tvSocialInt.setText(Utils.nullSafe(item.getString("social_interaction")));
            holder.tvJournal.setText(Utils.nullSafe(item.getString("journal")));
            holder.tvObservedAt.setText(Utils.parseTimeUTCtoDefault(item.getString("observed_at"),"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd MMM yyyy"));
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
        @BindView(R.id.tvMood)
        TextView tvMood;
        @BindView(R.id.tvJournal)
        TextView tvJournal;
        @BindView(R.id.tvSocialInt)
        TextView tvSocialInt;
        @BindView(R.id.tvObservedAt)
        TextView tvObservedAt;

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
