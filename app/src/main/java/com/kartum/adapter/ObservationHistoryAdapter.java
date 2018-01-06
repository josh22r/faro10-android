package com.kartum.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.view.SimpleListDividerDecorator;
import com.kartum.R;
import com.kartum.utils.Debug;
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

public class ObservationHistoryAdapter extends RecyclerView.Adapter<ObservationHistoryAdapter.MyViewHolder> implements Filterable {

    ImageLoader imageLoader;
    public JSONArray data = new JSONArray();
    private JSONArray dataSource = new JSONArray();
    Context context;
    Eventlistener mEventlistener;

    public ObservationHistoryAdapter(Context c) {
        this.context = c;
        imageLoader = Utils.initImageLoader(context);
    }

    //    public void addAll(JsonObject obj) {
//        Iterator iterator = obj.keys();
//        while (iterator.hasNext()) {
//            String key = (String) iterator.next();
//            try {
//                JSONArray array = obj.getJSONArray(key);
//                data.put(key, array);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        notifyDataSetChanged();
//    }
    public void add(JSONObject mData) {
        data.put(mData);
        if (isFilterable) {
            this.dataSource.put(mData);
        }
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_observation_history, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.layoutManager = new LinearLayoutManager(context);
        holder.recyclerView.setLayoutManager(holder.layoutManager);
        holder.recyclerView.addItemDecoration(new SimpleListDividerDecorator(context.getResources().getDrawable(R.drawable.list_divider), true));
        holder.mAdapter = new ObservChildDataAdapter(context);
        holder.recyclerView.setAdapter(holder.mAdapter);

        try {
            final JSONObject item = data.getJSONObject(position);
            holder.tvObsName.setText(Utils.nullSafe(item.getString("name")));

            for (int i = 0; i < item.getJSONArray("array").length(); i++) {
                holder.mAdapter.add(item.getJSONArray("array").getJSONObject(i));
            }
            Debug.e("", "array -->> " + item.getJSONArray("array"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.llUpDowan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.llChild.getVisibility() == View.VISIBLE && holder.imgMsgErrowDow.getVisibility() == View.VISIBLE) {
                    holder.llChild.setVisibility(View.GONE);
                    holder.imgMsgErrowDow.setVisibility(View.GONE);
                    holder.imgMsgErrowUp.setVisibility(View.VISIBLE);
                } else {
                    holder.llChild.setVisibility(View.VISIBLE);
                    holder.imgMsgErrowDow.setVisibility(View.VISIBLE);
                    holder.imgMsgErrowUp.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgMsgErrowUp)
        ImageView imgMsgErrowUp;
        @BindView(R.id.imgMsgErrowDow)
        ImageView imgMsgErrowDow;
        @BindView(R.id.llUpDowan)
        LinearLayout llUpDowan;
        @BindView(R.id.llChild)
        LinearLayout llChild;
        @BindView(R.id.tvObsName)
        TextView tvObsName;
        @BindView(R.id.rvObsData)
        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        public ObservChildDataAdapter mAdapter;
        @BindView(R.id.container)
        View container;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface Eventlistener {
        void OnItemViewClick(int position);
    }

    public void setmEventlistener(Eventlistener eventlistener) {
        this.mEventlistener = eventlistener;
    }

    boolean isFilterable = false;

    public void setFilterable(boolean isFilterable) {
        this.isFilterable = isFilterable;
    }

    @Override
    public Filter getFilter() {

        if (isFilterable) {
            return new PTypeFilter();
        }
        return null;
    }

    private class PTypeFilter extends Filter {
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            // NOTE: this function is *always* called from the UI thread.

//            for (int i = 0; i < data.length(); i++) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    data.remove(i);
//                }
//            }
//            for (int i = 0; i < data.length(); i++) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

//                }
//            }

            if (results != null) {
                data = (JSONArray) results.values;
                if (data != null) {
                    notifyDataSetChanged();
                } else {
                    data = dataSource;
                    notifyDataSetChanged();
                }
            }
        }

        protected FilterResults performFiltering(CharSequence prefix) {
            // NOTE: this function is *always* called from a background thread,
            // and
            // not the UI thread.
            FilterResults results = new FilterResults();
            JSONArray new_res = new JSONArray();
            if (prefix != null && prefix.toString().length() > 0) {
                for (int index = 0; index < dataSource.length(); index++) {

                    try {
                        JSONObject item = dataSource.getJSONObject(index);

                        if (item.getString("name").toLowerCase().contains(
                                prefix.toString().toLowerCase())) {
                            new_res.put(item);
                        } else if (item.getJSONArray("array") != null) {
                            for (int i = 0; i < item.getJSONArray("array").length(); i++) {
                                if (item.getJSONArray("array").getJSONObject(i).getString("journal").toLowerCase().contains(
                                        prefix.toString().toLowerCase())) {
                                    new_res.put(item);
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                results.values = new_res;
                results.count = new_res.length();

            } else {
                Debug.e("", "Called synchronized view");

                results.values = dataSource;
                results.count = dataSource.length();
            }
            return results;
        }
    }
}
