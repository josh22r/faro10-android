package com.kartum.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.kartum.R;
import com.kartum.objects.SymptomTrackRes;
import com.kartum.utils.Debug;
import com.kartum.utils.ItemTouchHelperAdapter;
import com.kartum.utils.ItemTouchHelperViewHolder;
import com.kartum.utils.OnStartDragListener;
import com.kartum.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kartum Infotech (Bhavesh Hirpara) on 10/7/2017.
 */
public class PhysicalSymptomDragNDropAdapter extends RecyclerView.Adapter<PhysicalSymptomDragNDropAdapter.MyViewHolder> implements ItemTouchHelperAdapter {

    public List<SymptomTrackRes.Symptom> data = new ArrayList<>();
    //    private List<ItemModel> mPersonList;
    OnItemClickListener mItemClickListener;
    private static final int TYPE_ITEM = 0;
    private final LayoutInflater mInflater;
    private final OnStartDragListener mDragStartListener;
    private Context mContext;
    Eventlistener mEventlitener;

    public PhysicalSymptomDragNDropAdapter(Context context, List<SymptomTrackRes.Symptom> list, OnStartDragListener dragListner) {
        this.data = list;
        this.mInflater = LayoutInflater.from(context);
        mDragStartListener = dragListner;
        mContext = context;
    }

    public void addAll(List<SymptomTrackRes.Symptom> mData) {
        data.clear();
        data.addAll(mData);
        notifyDataSetChanged();
    }

    public void add(SymptomTrackRes.Symptom mData) {
        data.clear();
        data.add(mData);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public List<SymptomTrackRes.Symptom> getAll() {
        return data;
    }

    public SymptomTrackRes.Symptom getItem(int position) {
        return data.get(position);
    }

    public List<SymptomTrackRes.Symptom> getSelectedAll() {
        List<SymptomTrackRes.Symptom> data = new ArrayList<>();

        for (SymptomTrackRes.Symptom select : this.data) {
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

//        if (viewType == TYPE_ITEM) {
//            //inflate your layout and pass it to view holder
//            View v = mInflater.inflate(R.layout.item_drag_drop_physical_symptoms, parent, false);
//            return new MyViewHolder(v);
//        }
//        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_drag_drop_physical_symptoms, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final SymptomTrackRes.Symptom item = data.get(position);
        holder.checkBox.setChecked(item.isSelected);
        holder.tvSymptomName.setText(Utils.nullSafe("" + item.name));

        holder.imgMenu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }

                return false;
            }
        });

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
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {
        @BindView(R.id.tvSymptom)
        TextView tvSymptomName;
        @BindView(R.id.checkBoxSym)
        CheckBox checkBox;
        @BindView(R.id.imgMenu)
        ImageView imgMenu;
        @BindView(R.id.container)
        View container;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    @Override
    public void onItemDismiss(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Debug.e("", "from position  " + fromPosition + " " + "  " + "to position  " + toPosition);
        if (fromPosition < data.size() && toPosition < data.size()) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(data, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(data, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
        }
        return true;
    }

    public void updateList(List<SymptomTrackRes.Symptom> list) {
        data = list;
        notifyDataSetChanged();
    }

    public interface Eventlistener {

        void onItemviewClick(int position);
    }

    public void setEventlistener(Eventlistener eventlistener) {
        this.mEventlitener = eventlistener;
    }
}
