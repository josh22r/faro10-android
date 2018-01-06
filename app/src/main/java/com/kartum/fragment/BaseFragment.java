package com.kartum.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kartum.R;
import com.kartum.adapter.SpinnerAdapter;
import com.kartum.objects.Spinner;
import com.kartum.utils.AsyncProgressDialog;
import com.kartum.utils.SpinnerCallback;

import java.util.ArrayList;


public abstract class BaseFragment extends Fragment {

    public static final int TITLE_NONE = -1;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //setTitle();
        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    Toast toast;

    public void showToast(final String text, final int duration) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                toast.setText(text);
                toast.setDuration(duration);
                toast.show();
            }
        });
    }

    AsyncProgressDialog ad;

    public void showDialog(String msg) {
        try {
            if (ad != null && ad.isShowing()) {
                return;
            }

            ad = AsyncProgressDialog.getInstant(getActivity());
            ad.show(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMessage(String msg) {
        try {
            ad.setMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissDialog() {
        try {
            if (ad != null) {
                ad.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSpinner(final String title, final TextView tv,
                            final ArrayList<Spinner> data, boolean isFilterable, final SpinnerCallback callback) {

        final Dialog a = new Dialog(getActivity());
        Window w = a.getWindow();
        a.requestWindowFeature(Window.FEATURE_NO_TITLE);
        a.setContentView(R.layout.spinner);
        w.setBackgroundDrawableResource(android.R.color.transparent);

        TextView lblselect = (TextView) w.findViewById(R.id.dialogtitle);
        lblselect.setText(title.replace("*", "").trim());

        TextView dialogClear = (TextView) w.findViewById(R.id.dialogClear);
        dialogClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("");
                tv.setTag(null);

                a.dismiss();
            }
        });

        final EditText editSearch = (EditText) w.findViewById(R.id.editSearch);
        if (isFilterable) {
            editSearch.setVisibility(View.VISIBLE);
        } else {
            editSearch.setVisibility(View.GONE);
        }

        final SpinnerAdapter adapter = new SpinnerAdapter(getActivity());
        adapter.setFilterable(isFilterable);
        ListView lv = (ListView) w.findViewById(R.id.lvSpinner);
        lv.setAdapter(adapter);
        adapter.addAll(data);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterview, View view,
                                    int position, long l) {
                ArrayList<Spinner> selList = new ArrayList<Spinner>();
                selList.add(adapter.getItem(position));

                if (title.equalsIgnoreCase(getString(R.string.choose_country))) {
                    tv.setText(adapter.getItem(position).ID);
                    tv.setTag(adapter.getItem(position).ID);
                } else {
                    tv.setText(adapter.getItem(position).title);
                    tv.setTag(adapter.getItem(position).ID);
                }

                if (callback != null) {
                    callback.onDone(selList);
                }

                a.dismiss();

            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() >= 1) {
                    adapter.getFilter().filter(editable.toString().trim());
                } else {
                    adapter.getFilter().filter("");
                }

            }
        });

        a.show();
    }

    public void showSpinner(final String title,
                            final ArrayList<Spinner> data, boolean isFilterable, final SpinnerCallback callback) {

        final Dialog a = new Dialog(getActivity());
        Window w = a.getWindow();
        a.requestWindowFeature(Window.FEATURE_NO_TITLE);
        a.setContentView(R.layout.spinner);
        w.setBackgroundDrawableResource(android.R.color.transparent);

        TextView lblselect = (TextView) w.findViewById(R.id.dialogtitle);
        lblselect.setText(title.replace("*", "").trim());

        TextView dialogClear = (TextView) w.findViewById(R.id.dialogClear);

        final EditText editSearch = (EditText) w.findViewById(R.id.editSearch);
        if (isFilterable) {
            editSearch.setVisibility(View.VISIBLE);
        } else {
            editSearch.setVisibility(View.GONE);
        }

        final SpinnerAdapter adapter = new SpinnerAdapter(getActivity());
        adapter.setFilterable(isFilterable);
        ListView lv = (ListView) w.findViewById(R.id.lvSpinner);
        lv.setAdapter(adapter);
        adapter.addAll(data);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterview, View view,
                                    int position, long l) {
                ArrayList<Spinner> selList = new ArrayList<Spinner>();
                selList.add(adapter.getItem(position));

//                if (title.equalsIgnoreCase(getString(R.string.choose_country))) {
//                    tv.setText(adapter.getItem(position).ID);
//                    tv.setTag(adapter.getItem(position).ID);
//                } else {
//                    tv.setText(adapter.getItem(position).title);
//                    tv.setTag(adapter.getItem(position).ID);
//                }

                if (callback != null) {
                    callback.onDone(selList);
                }

                a.dismiss();

            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() >= 1) {
                    adapter.getFilter().filter(editable.toString().trim());
                } else {
                    adapter.getFilter().filter("");
                }

            }
        });

        a.show();
    }
}

