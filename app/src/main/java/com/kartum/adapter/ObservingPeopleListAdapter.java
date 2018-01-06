package com.kartum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ObservingPeopleListAdapter extends RecyclerView.Adapter<ObservingPeopleListAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    public JSONArray data = new JSONArray();
    Context context;
    Eventlistener mEventlistener;

    public ObservingPeopleListAdapter(Context c) {
        this.context = c;
        imageLoader = Utils.initImageLoader(context);
    }

    public void addAll(JSONArray mData) {

        data.put(mData);
        notifyDataSetChanged();
    }


    public void add(JSONObject mData) {
        data.put(mData);
        notifyDataSetChanged();
    }

    public JSONObject getItem(int pos) {
        try {
            return data.getJSONObject(pos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public int getObjectId(int position) {
        try {
            return data.getJSONObject(position).getInt("member_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_observing_people, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        try {
            final JSONObject item = data.getJSONObject(position);
            holder.tvUserName.setText(Utils.nullSafe(item.getString("name")));
            holder.tvRole.setText(Utils.nullSafe(item.getString("role")));
            holder.tvLastEntry.setText(Utils.nullSafe(item.getString("last_entry")));
            holder.tvTotalEntry.setText(Utils.nullSafe(item.getString("total_entries")));

            if (item.get("role").toString().equalsIgnoreCase("Guardian")) {
                holder.llCareTeam.setVisibility(View.VISIBLE);
            } else {
                holder.llCareTeam.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.llObserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventlistener != null) {
                    mEventlistener.OnObservclick(position);
                }
            }
        });
        holder.llCareTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventlistener != null) {
                    mEventlistener.OnCareTeamClick(position);
                }
            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventlistener != null) {
                    mEventlistener.OnDeleteclick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.llObserver)
        LinearLayout llObserver;
        @BindView(R.id.llCareTeam)
        LinearLayout llCareTeam;
        @BindView(R.id.tvUserName)
        TextView tvUserName;
        @BindView(R.id.tvRole)
        TextView tvRole;
        @BindView(R.id.tvLastEntry)
        TextView tvLastEntry;
        @BindView(R.id.tvTotalEntry)
        TextView tvTotalEntry;
        @BindView(R.id.imgObserver)
        ImageView imgObserver;
        //        @BindView(R.id.tvEdit)
//        TextView tvEdit;
        @BindView(R.id.tvDelete)
        TextView tvDelete;
        @BindView(R.id.container)
        View container;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface Eventlistener {

        void OnObservclick(int position);

        void OnDeleteclick(int position);

        //
        void OnCareTeamClick(int position);
    }

    public void setmEventlistener(Eventlistener eventlistener) {
        this.mEventlistener = eventlistener;
    }
}
