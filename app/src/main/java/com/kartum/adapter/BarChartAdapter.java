package com.kartum.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kartum.R;
import com.kartum.objects.ObservPresChartRes;
import com.kartum.utils.ChartColor;
import com.kartum.utils.Debug;
import com.kartum.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by micra solution on 8/25/2017.
 */

public class BarChartAdapter extends RecyclerView.Adapter<BarChartAdapter.MyViewHolder> {
    Context context;
    private List<ObservPresChartRes.Observee> data = new ArrayList<>();
    EventListener mEventListener;
    ImageLoader imageLoader;
    String mData = "";

    public interface EventListener {
        void onItemClickedView(int position);
    }

    public BarChartAdapter(Context context) {
        this.context = context;
        imageLoader = Utils.initImageLoader(context);

    }

    public void addAll(List<ObservPresChartRes.Observee> mData) {
        clear();
        data.addAll(mData);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void remove(int position, int id) {
        mData = mData + "," + id;
        data.remove(position);
        notifyDataSetChanged();
    }

    public ObservPresChartRes.Observee getItem(int pos) {
        return data.get(pos);
    }

    public void changeSelection(int position, boolean isMultiSel) {

        for (int i = 0; i < data.size(); i++) {
            if (position == i) {
                data.get(i).isSelected = !data.get(i).isSelected;
            } else if (!isMultiSel) {
                data.get(i).isSelected = false;
            }
        }

        notifyDataSetChanged();
    }

    public ArrayList<ObservPresChartRes.Observee> getSelectedAll() {
        ArrayList<ObservPresChartRes.Observee> data = new ArrayList<ObservPresChartRes.Observee>();

        for (ObservPresChartRes.Observee spinner : this.data) {
            if (spinner.isSelected) {
                data.add(spinner);
            }
        }

        return data;
    }


    public void selectAll() {

        for (int i = 0; i < data.size(); i++) {
            data.get(i).isSelected = true;
        }
        notifyDataSetChanged();
    }

    public HashSet<String> getSelectedAllUserId() {
        HashSet<String> data = new HashSet<>();

        for (ObservPresChartRes.Observee spinner : this.data) {
            if (spinner.isSelected) {
                data.add(spinner.userId);
            }
        }
        return data;
    }

    public List<Integer> getColorList() {
        List<Integer> data = new ArrayList<Integer>();
        data.clear();

        for (ObservPresChartRes.Observee spinner : this.data) {
//            data.add(-1 * spinner.color);
            data.add(spinner.color);
        }
        return data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_obs_chart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ObservPresChartRes.Observee item = data.get(position);

        holder.tvName.setText(Utils.nullSafe(item.userId));
        holder.checkBox.setChecked(item.isSelected);
//        Debug.e("item.checkBox", "" + item.isSelected);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mEventListener != null) {
                    mEventListener.onItemClickedView(position);
                }
            }
        });

        if (item.isSelected) {
            ChartColor generator = ChartColor.MATERIAL; // or use DEFAULT
            int color2 = generator.getColor(item.userId);
            Debug.e("item.userId", "" + item.userId + " " + color2);
            holder.tvName.setTextColor(color2);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.checkBox.setButtonTintList(ColorStateList.valueOf(color2));
            }
            item.color = color2;

        } else {
            holder.tvName.setTextColor(context.getResources().getColor(R.color.col_666));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.checkBox.setButtonTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.gray_light_)));
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.checkBox)
        CheckBox checkBox;
        @BindView(R.id.container)
        public View container;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setmEventListener(EventListener eventListener) {
        mEventListener = eventListener;
    }
}