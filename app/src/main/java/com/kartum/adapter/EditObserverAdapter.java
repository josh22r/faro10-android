package com.kartum.adapter;

import android.content.Context;
import android.graphics.Paint;
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

public class EditObserverAdapter extends RecyclerView.Adapter<EditObserverAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    Context context;
    Eventlistener mEventlistener;
    public JSONArray data = new JSONArray();

    public EditObserverAdapter(Context c) {
        this.context = c;
        imageLoader = Utils.initImageLoader(context);
    }

    public void add(JSONObject mData) {
        data.put(mData);
        notifyDataSetChanged();
    }

//    public void addAll(JSONObject mData) {
//        data.put(mData);
//        notifyDataSetChanged();
//    }

    public int getObjectId(int position) {
        try {
            return data.getJSONObject(position).getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }


//    public List<SymptomsListRes.Symptom> getAll() {
//        return data;
//    }
//
//    public SymptomsListRes.Symptom getItem(int position) {
//        return data.get(position);
//    }

    public JSONArray getAll() {
        return data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_list_obs, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        try {
            final JSONObject item = data.getJSONObject(position);
            holder.tvObsList.setText(Utils.nullSafe(item.getString("name")));
//            holder.tvGardian.setText(Utils.nullSafe(item.getString("relationship")));

            final String a = (Utils.nullSafe(item.getString("meds")));
            if (a.equalsIgnoreCase("1")) {
                holder.tvGardian.setText("guardian");
            } else {
                holder.tvGardian.setText("guardian");
                holder.tvGardian.setPaintFlags(holder.tvGardian.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.tvGardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventlistener != null) {
                    mEventlistener.onItemviewClick(position);


                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvObsList)
        TextView tvObsList;
        @BindView(R.id.tvGardian)
        TextView tvGardian;
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

    public void setmEventlistener(Eventlistener eventlistener) {
        this.mEventlistener = eventlistener;
    }
}
