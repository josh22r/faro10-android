package com.kartum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kartum.R;
import com.kartum.objects.Spinner;
import com.kartum.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by om on 7/11/2017.
 */

public class SymptomsSideEffectAdapter extends RecyclerView.Adapter<SymptomsSideEffectAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    Context context;
    Eventlistener mEventlitener;

    // private Context mContext;
    private LayoutInflater infalter;
    private ArrayList<Spinner> data = new ArrayList<Spinner>();
    ArrayList<Spinner> dataSource = new ArrayList<Spinner>();

    public SymptomsSideEffectAdapter(Context c) {
        this.context = c;
        imageLoader = Utils.initImageLoader(context);
        infalter = (LayoutInflater) c
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    boolean isFilterable = false;

    public void setFilterable(boolean isFilterable) {
        this.isFilterable = isFilterable;
    }

    public void addAll(ArrayList<Spinner> files) {

        try {
            this.data.clear();
            this.data.addAll(files);

            if (isFilterable) {
                this.dataSource.clear();
                this.dataSource.addAll(files);
            }

        } catch (Exception e) {
            Utils.sendExceptionReport(e);
        }

        notifyDataSetChanged();
    }

    public ArrayList<Spinner> getSelectedAll() {
        ArrayList<Spinner> data = new ArrayList<Spinner>();

        for (Spinner spinner : this.data) {
            if (spinner.isSelected) {
                data.add(spinner);
            }
        }

        return data;
    }

    public String getSelectedIds() {
        String str = "";

        for (Spinner spinner : data) {
            if (spinner.isSelected) {
                str = str + spinner.ID + ",";
            }
        }

        if (str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }

        return str;
    }

    public ArrayList<String> getSelectedIdList() {
        ArrayList<String> data = new ArrayList<String>();

        for (Spinner spinner : this.data) {
            if (spinner.isSelected) {
                data.add(spinner.ID);
            }
        }

        return data;
    }

    public JSONArray getSelectedIdArray() {
        JSONArray data = new JSONArray();

        try {
            for (Spinner spinner : this.data) {
                if (spinner.isSelected) {
                    data.put(spinner.ID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public String getSelectedTitle() {
        String str = "";

        for (Spinner spinner : data) {
            if (spinner.isSelected) {
                str = str + spinner.title + ", ";
            }
        }

        str = str.trim();
        if (str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }

        return str;
    }

    public ArrayList<Spinner> getAll() {
        return data;
    }

    public boolean isSelectedAll() {

        for (Spinner spinner : this.data) {
            if (!spinner.isSelected) {
                return false;
            }
        }

        return true;
    }

    public int getSelectedCount() {

        int cnt = 0;

        for (Spinner spinner : this.data) {
            if (spinner.isSelected) {
                cnt = cnt + 1;
            }
        }

        return cnt;
    }

    public boolean isSelectedAtleastOne() {

        for (Spinner spinner : this.data) {
            if (spinner.isSelected) {
                return true;
            }
        }

        return false;
    }

    public boolean isSelected(int position) {
        return data.get(position).isSelected;
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

    public void setSelection(int position) {
        for (int i = 0; i < data.size(); i++) {
            if (position == i) {
                data.get(i).isSelected = true;
            } else {
                data.get(i).isSelected = false;
            }
        }

        notifyDataSetChanged();
    }

    public void selectAll(boolean selectall) {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).isSelected = selectall;
        }

        notifyDataSetChanged();
    }

//    public void addAll(List<ResponseData.Slider> mData) {
//        sliderData.addAll(mData);
//        notifyDataSetChanged();
//    }
//
//    public ResponseData.Slider getItem(int position) {
//        return sliderData.get(position);
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_list_symptoms_sede_effects, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.checkBox.setChecked(data.get(position).isSelected);
        holder.tvSymptomList.setText(Utils.nullSafe("" + data.get(position).title));

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
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvSymptomList)
        TextView tvSymptomList;
        @BindView(R.id.checkBox)
        CheckBox checkBox;
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
