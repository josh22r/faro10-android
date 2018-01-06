package com.kartum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kartum.R;
import com.kartum.objects.CareTeamRes;
import com.kartum.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by om on 7/11/2017.
 */

public class CareTeamAdapter extends RecyclerView.Adapter<CareTeamAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    //    public JSONArray data = new JSONArray();
    public List<CareTeamRes.Clinician> data = new ArrayList<>();
    Context context;
    Eventlistener mEventlistener;

    public CareTeamAdapter(Context c) {
        this.context = c;
        imageLoader = Utils.initImageLoader(context);
    }

    public void addAll(List<CareTeamRes.Clinician> mData) {
        data.addAll(mData);
        notifyDataSetChanged();
    }

    public void add(CareTeamRes.Clinician mData) {
        data.add(mData);
        notifyDataSetChanged();
    }

    public CareTeamRes.Clinician getItem(int pos) {
        return data.get(pos);
    }

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
        final View v = inflater.inflate(R.layout.item_care_team, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        try {
            final CareTeamRes.Clinician item = data.get(position);
            holder.tvUserName.setText(Utils.nullSafe(item.userId));
//            holder.tvClinic.setText(Utils.nullSafe(item.clinicName + ", " + item.clinicStreet + ", " + item.clinicCity + ", " + item.clinicState + ", " + item.clinicZip));
            holder.tvNumber.setText(Utils.nullSafe("" + item.clinicPhone));

            String addressString = "";
            if (!item.clinicStreet.isEmpty()) {
                addressString = item.clinicStreet;
            }

            if (!item.clinicCity.isEmpty()) {
                if (addressString.isEmpty()) {
                    addressString = item.clinicCity;
                } else {
                    addressString = addressString + ", " + item.clinicCity;
                }
            }
            if (!item.clinicState.isEmpty()) {
                if (addressString.isEmpty()) {
                    addressString = item.clinicState;
                } else {
                    addressString = addressString + ", " + item.clinicState;
                }
            }
            if (item.clinicZip == 0) {
                if (addressString.isEmpty()) {
                    addressString = "" + item.clinicZip;
                } else {
                    addressString = addressString + ", " + item.clinicZip;
                }
            }

            holder.tvClinic.setText(Utils.nullSafe(item.clinicName + ", " + addressString));


        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.tvNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventlistener != null) {
                    mEventlistener.OnNumberClick(position);
                }
            }
        });

        holder.tvClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventlistener != null) {
                    mEventlistener.OnAddresClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvUserName)
        TextView tvUserName;
        @BindView(R.id.tvClinic)
        TextView tvClinic;
        @BindView(R.id.tvNumber)
        TextView tvNumber;
        @BindView(R.id.container)
        View container;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface Eventlistener {

        void OnNumberClick(int position);

        void OnAddresClick(int position);

    }

    public void setmEventlistener(Eventlistener eventlistener) {
        this.mEventlistener = eventlistener;
    }
}
