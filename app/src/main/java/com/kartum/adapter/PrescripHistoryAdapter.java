package com.kartum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kartum.R;
import com.kartum.objects.PriscriptionRes;
import com.kartum.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by om on 7/11/2017.
 */

public class PrescripHistoryAdapter extends RecyclerView.Adapter<PrescripHistoryAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    public List<PriscriptionRes.Prescription> data = new ArrayList<>();
    Context context;
    Eventlistener mEventlistener;

    public PrescripHistoryAdapter(Context c) {
        this.context = c;
        imageLoader = Utils.initImageLoader(context);
    }

    public void addAll(List<PriscriptionRes.Prescription> mData) {
        data.clear();
        data.addAll(mData);
        notifyDataSetChanged();
    }

    public void add(PriscriptionRes.Prescription mData) {
        data.add(mData);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public List<PriscriptionRes.Prescription> getAll() {
        return data;
    }

    public PriscriptionRes.Prescription getItem(int position) {
        return data.get(position);
    }

    public PriscriptionRes.Prescription getObjectId(int position) {
        return data.get(position);
    }

    public void updateTime(int pos, String i) {
        data.get(pos).time = i;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_list_prescrip_history, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final PriscriptionRes.Prescription item = data.get(position);
        holder.tvDrug.setText(Utils.nullSafe("" + item.drug.friendlyName));
        holder.tvDosage.setText(Utils.nullSafe("" + item.dosage));

        if (item.asNeeded) {
            holder.tvConsistency.setText(Utils.nullSafe("As needed"));
        } else {
            holder.tvConsistency.setText(Utils.nullSafe("Consistency :" + item.consistency + "%"));
        }

        holder.tvSelect.setText(Utils.nullSafe(item.time, "Select"));

        holder.tvPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventlistener != null) {
                    mEventlistener.OnMenuclick(position, view);
                }
            }
        });
        holder.tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventlistener != null) {
                    mEventlistener.OnTimeBoxclick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDrug)
        TextView tvDrug;
        @BindView(R.id.tvDosage)
        TextView tvDosage;
        @BindView(R.id.tvConsistency)
        TextView tvConsistency;
        @BindView(R.id.tvPopup)
        TextView tvPopup;
        @BindView(R.id.tvSelect)
        TextView tvSelect;
        @BindView(R.id.container)
        View container;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface Eventlistener {

        void OnMenuclick(int position, View view);

        void OnTimeBoxclick(int position);
//
//        void OnItemDetailtviewclick(int position);
    }

    public void setmEventlistener(Eventlistener eventlistener) {
        this.mEventlistener = eventlistener;
    }
}
