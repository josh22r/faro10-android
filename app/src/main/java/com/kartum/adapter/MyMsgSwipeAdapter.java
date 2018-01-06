package com.kartum.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.kartum.R;
import com.kartum.objects.MessagesRes;
import com.kartum.utils.Constant;
import com.kartum.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by om on 7/14/2017.
 */

public class MyMsgSwipeAdapter extends RecyclerView.Adapter<MyMsgSwipeAdapter.ViewHolder> {

    ImageLoader imageLoader;
    public List<MessagesRes.Message> data = new ArrayList<>();
    Context context;
    Eventlistener mEventlitener;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();

    public MyMsgSwipeAdapter(Context c) {
        this.context = c;
        imageLoader = Utils.initImageLoader(context);
        setHasStableIds(true);
    }

    public void addAll(List<MessagesRes.Message> mData) {
        data.clear();
        data.addAll(mData);
//        notifyDataSetChanged();
        sorting();
    }

    public void add(MessagesRes.Message mData) {
        data.add(mData);
//        notifyDataSetChanged();
        sorting();
    }

    public void sorting() {
        Collections.sort(data, new Comparator<MessagesRes.Message>() {
            public int compare(MessagesRes.Message s1, MessagesRes.Message s2) {
                return (Utils.parseTime(s2.createdAt, Constant.DATE_FORMAT_SERVER)).compareTo(Utils.parseTime(s1.createdAt, Constant.DATE_FORMAT_SERVER));
            }

        });
//        Debug.e("sorting", "" + getItemCount());
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
//        for (int i = 0; i < data.size(); i++) {
//            data.remove(i);
//        }
        notifyDataSetChanged();
    }

    public MessagesRes.Message getItem(int position) {
        return data.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_msg_swipe, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final MessagesRes.Message item = data.get(position);
//        binderHelper.bind(holder.swipe_layout, productlist.productname);
        holder.tvUserName.setText(Utils.nullSafe("" + item.clinician.userId));
        holder.tvRemember.setText(Utils.nullSafe("" + item.body));
        holder.tvDateTime.setText(Utils.parseTimeUTCtoDefault(item.createdAt, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd/MM/yy"));
        holder.flReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventlitener != null) {
                    mEventlitener.onItemReceiveClick(position);
                }
            }
        });
        if (item.read) {
            int color = context.getResources().getColor(R.color.menu_list_selector);
            holder.tvRemember.setTextColor(color);
//            holder.tvReceived.setTextColor(context.getResources().getColor(R.color.menu_list_selector));
            holder.flReceived.setClickable(false);

        } else {
            int color = context.getResources().getColor(R.color.black_AA);
            holder.tvRemember.setTextColor(color);
        }
        holder.flDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventlitener != null) {
                    mEventlitener.onItemDeleteClick(position);
                }
            }
        });
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventlitener != null) {
                    mEventlitener.onItemViewClick(position);
                }
            }
        });

        binderHelper.bind(holder.swipe_layout, "" + item.id);
        binderHelper.closeLayout("" + item.id);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.swipe_layout)
        SwipeRevealLayout swipe_layout;
        @BindView(R.id.tvUserName)
        TextView tvUserName;
        @BindView(R.id.tvDateTime)
        TextView tvDateTime;
        @BindView(R.id.tvRemember)
        TextView tvRemember;
        @BindView(R.id.tvReceived)
        TextView tvReceived;
        @BindView(R.id.container)
        LinearLayout container;

        @BindView(R.id.flReceived)
        FrameLayout flReceived;
        @BindView(R.id.flDelete)
        FrameLayout flDelete;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface Eventlistener {

        void onItemReceiveClick(int position);

        void onItemDeleteClick(int position);

        void onItemViewClick(int position);
    }

    public void setEventlistener(Eventlistener eventlistener) {
        this.mEventlitener = eventlistener;
    }
}
