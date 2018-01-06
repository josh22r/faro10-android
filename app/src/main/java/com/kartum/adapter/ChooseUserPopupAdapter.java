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

public class ChooseUserPopupAdapter extends RecyclerView.Adapter<ChooseUserPopupAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    public JSONArray data = new JSONArray();
    Context context;
    Eventlistener mEventlitener;

    public ChooseUserPopupAdapter(Context c) {
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
            return data.getJSONObject(position).getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_choose_user, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        try {
            final JSONObject item = data.getJSONObject(position);
            holder.tvUserName.setText(Utils.nullSafe(item.getString("name")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventlitener != null) {
                    mEventlitener.onItemviewClick(position);
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
        //        @BindView(R.id.checkBox)
//        CheckBox checkBox;
        @BindView(R.id.container)
        View container;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface Eventlistener {

        void onItemviewClick(int position);
    }

    public void setEventlistener(Eventlistener eventlistener) {
        this.mEventlitener = eventlistener;
    }
}
