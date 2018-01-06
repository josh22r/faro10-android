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

public class ObserversListAdapter extends RecyclerView.Adapter<ObserversListAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    public JSONArray data ;
    Context context;
    Eventlistener mEventlistener;

    public ObserversListAdapter(Context c) {
        data = new JSONArray();
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

    public JSONArray getAll() {
        return data;
    }

    public void clear() {
//        for (int i = 0; i < data.length(); i++) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                data.remove(i);
//            }
//        }
        data = new JSONArray();
        notifyDataSetChanged();
    }

    public int getObjectId(int position) {
        try {
            return data.getJSONObject(position).getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getName(int position) {
        try {
            return data.getJSONObject(position).getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_myclinians, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
            final JSONObject item = data.getJSONObject(position);
            holder.tvUserName.setText(Utils.nullSafe(item.getString("name")));
            holder.tvClinic.setText(Utils.nullSafe(item.getString("relationship")));
            holder.tvStatus.setText(Utils.nullSafe(item.getString("status")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.tvPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventlistener != null) {
                    mEventlistener.OnMenuclick(position, view);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvUserName)
        TextView tvUserName;
        @BindView(R.id.tvClinic)
        TextView tvClinic;
        @BindView(R.id.tvPopup)
        TextView tvPopup;
        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.container)
        View container;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface Eventlistener {

        void OnMenuclick(int position, View view);

//        void OnTimeBoxclick(int position);
//
//        void OnItemDetailtviewclick(int position);
    }

    public void setmEventlistener(Eventlistener eventlistener) {
        this.mEventlistener = eventlistener;
    }
}
