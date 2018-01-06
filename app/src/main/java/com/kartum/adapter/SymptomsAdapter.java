package com.kartum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kartum.R;
import com.kartum.objects.SymptomsListRes;
import com.kartum.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by om on 7/11/2017.
 */

public class SymptomsAdapter extends RecyclerView.Adapter<SymptomsAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    public List<SymptomsListRes.Symptom> data = new ArrayList<>();
    Context context;
    Eventlistener mEventlitener;

    public SymptomsAdapter(Context c) {
        this.context = c;
        imageLoader = Utils.initImageLoader(context);
    }

    public void addAll(List<SymptomsListRes.Symptom> mData) {
        HashSet<String> selectedId = new HashSet<>();
        for (SymptomsListRes.Symptom select : this.data) {
            if (select.isSelected) {
                selectedId.add("" + select.id);
            }
        }

        data.clear();
        data.addAll(mData);

        for (int i = 0; i < data.size(); i++) {
            if (selectedId.contains("" + data.get(i).id)) {
                data.get(i).isSelected = true;
            }
//            else {
//                data.get(i).isSelected = false;
//            }

//            if (res.symptoms.get(i).id != data.get(i).id) {
//                data.get(i).isSelected = true;
//                Debug.e("isSelected", "" + res.symptoms.get(i).id);
//            }
        }

//        String getShardPref = Utils.getPref(context, "pref", "");
//        SymptomTrackRes res = new Gson().fromJson(getShardPref, new TypeToken<SymptomTrackRes>() {
//        }.getType());

        notifyDataSetChanged();
    }

    public List<SymptomsListRes.Symptom> getAll() {
        return data;
    }

    public SymptomsListRes.Symptom getItem(int position) {
        return data.get(position);
    }

    public List<SymptomsListRes.Symptom> getSelectedAll() {
        List<SymptomsListRes.Symptom> data = new ArrayList<>();

        for (SymptomsListRes.Symptom select : this.data) {
            if (select.isSelected) {
                data.add(select);
            }
        }
        return data;
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

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_list_symptoms_sede_effects, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final SymptomsListRes.Symptom item = data.get(position);
        holder.checkBox.setChecked(item.isSelected);
        holder.tvSymptomList.setText(Utils.nullSafe("" + item.name));

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
