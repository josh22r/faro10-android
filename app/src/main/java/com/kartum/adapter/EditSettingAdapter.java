package com.kartum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.kartum.R;
import com.kartum.SettingActivity;
import com.kartum.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by om on 7/11/2017.
 */

public class EditSettingAdapter extends RecyclerView.Adapter<EditSettingAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    //    public ArrayList<String> data = new ArrayList<>();
//    public JSONArray data = new JSONArray();
    private ArrayList<SettingActivity.ReminderData> data = new ArrayList<SettingActivity.ReminderData>();
    Context context;
    Eventlistener mEventlistener;

    public EditSettingAdapter(Context c) {
        this.context = c;
        imageLoader = Utils.initImageLoader(context);
    }

//    public void add(SettingActivity.ReminderData mData) {
//        data.add(mData);
//        notifyDataSetChanged();
//    }

    public SettingActivity.ReminderData getItem(int pos) {
        try {
            return data.get(pos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addAll(ArrayList<SettingActivity.ReminderData> files) {
        data.clear();
        data.addAll(files);
        notifyDataSetChanged();
    }

    public void selectAll(boolean selectall) {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).isOn = selectall;
        }

        notifyDataSetChanged();
    }

    public void changeSelection(int position, boolean isMultiSel) {

        for (int i = 0; i < data.size(); i++) {
            if (position == i) {
                data.get(i).isOn = !data.get(i).isOn;
            } else if (!isMultiSel) {
                data.get(i).isOn = false;
            }
        }

        notifyDataSetChanged();
    }

//    public int getObjectId(int position) {
//        try {
//            return data.(position).getInt("id");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//    public JSONObject getItem(int position) {
//        return data.get(position);
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_edit_setting, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final SettingActivity.ReminderData item = data.get(position);
        holder.tvTime.setText(Utils.nullSafe("" + item.time));
        holder.tvLable.setText(Utils.nullSafe("" + item.lable));
        holder.mSwitch.setChecked(data.get(position).isOn);
        holder.tvPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventlistener != null) {
                    mEventlistener.OnMenuclick(position, view);
                }
            }
        });
        holder.mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventlistener != null) {
                    mEventlistener.onSwitchClick(position);


                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvLable)
        TextView tvLable;
        @BindView(R.id.tvPopup)
        TextView tvPopup;
        @BindView(R.id.mSwitch)
        Switch mSwitch;
        @BindView(R.id.container)
        View container;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface Eventlistener {
        void OnMenuclick(int position, View view);

        void onSwitchClick(int posion);
    }

    public void setmEventlistener(Eventlistener eventlistener) {
        this.mEventlistener = eventlistener;
    }
}
