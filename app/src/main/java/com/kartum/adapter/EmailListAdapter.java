package com.kartum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kartum.R;
import com.kartum.objects.EmailData;
import com.kartum.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by om on 7/11/2017.
 */

public class EmailListAdapter extends RecyclerView.Adapter<EmailListAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    public List<EmailData> data = new ArrayList<>();
    Context context;
    Eventlistener mEventlistener;

    public EmailListAdapter(Context c) {
        this.context = c;
        setHasStableIds(true);
        imageLoader = Utils.initImageLoader(context);
    }

    public void addAll(List<EmailData> mData) {
        data.addAll(mData);
        notifyDataSetChanged();
    }

    public List<EmailData> getAll() {
        return data;
    }

    public void add() {
        data.add(new EmailData());
//        Debug.e("add","item aded");
        notifyDataSetChanged();
    }

//    public String getItem(int position) {
//        return data.get(position);
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_invite_email, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

//        String item = data.get(position);


        holder.editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                data.get(position).mEmail = editable.toString();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.editEmail)
        EditText editEmail;
        @BindView(R.id.container)
        View container;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface Eventlistener {
        void OnMenuclick(int position);
    }

    public void setmEventlistener(Eventlistener eventlistener) {
        this.mEventlistener = eventlistener;
    }
}
