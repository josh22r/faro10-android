package com.kartum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kartum.R;
import com.kartum.objects.CliniciansRes;
import com.kartum.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by om on 7/11/2017.
 */

public class CliniciansListAdapter extends RecyclerView.Adapter<CliniciansListAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    public List<CliniciansRes.Membership> data = new ArrayList<>();
    Context context;
    Eventlistener mEventlistener;

    public CliniciansListAdapter(Context c) {
        this.context = c;
        imageLoader = Utils.initImageLoader(context);
    }

    public void addAll(List<CliniciansRes.Membership> mData) {
        data.clear();
        data.addAll(mData);
        notifyDataSetChanged();
    }

    public List<CliniciansRes.Membership> getAll() {
        return data;
    }

    public CliniciansRes.Membership getItem(int position) {
        return data.get(position);
    }
//    public void updateTime(int pos, String i) {
//        data.get(pos).totalTimesTaken = i;
//        notifyDataSetChanged();
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_myclinians, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final CliniciansRes.Membership item = data.get(position);
        holder.tvUserName.setText(Utils.nullSafe("" + item.userName));
        holder.tvClinic.setText(Utils.nullSafe("" + item.clinicName));
        holder.tvStatus.setText(Utils.nullSafe("" + item.status));
//        holder.tvSelect.setText(Utils.nullSafe(item.totalTimesTaken));

        holder.tvPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventlistener != null) {
                    mEventlistener.OnMenuclick(position, view);
                }
            }
        });
//        holder.tvSelect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mEventlistener != null) {
//                    mEventlistener.OnTimeBoxclick(position);
//                }
//            }
//        });
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
    }

    public void setmEventlistener(Eventlistener eventlistener) {
        this.mEventlistener = eventlistener;
    }
}
