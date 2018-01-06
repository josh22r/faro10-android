package com.kartum;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.common.view.CustomViewPager;
import com.kartum.adapter.SpinnerAdapter;
import com.kartum.fragment.PrescriptionConsistFrag;
import com.kartum.fragment.PrescriptionHistoryFrag;
import com.kartum.objects.DrugsRes;
import com.kartum.objects.Spinner;
import com.kartum.utils.AsyncResponseHandlerOk;
import com.kartum.utils.Constant;
import com.kartum.utils.Debug;
import com.kartum.utils.HttpClient;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.SpinnerCallback;
import com.kartum.utils.URLs;
import com.kartum.utils.Utils;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;

public class PrescriptionsActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    CustomViewPager pager;

    @BindView(R.id.tvBackArrow)
    TextView tvBackArrow;
    @BindView(R.id.tvAddPriscription)
    TextView tvAddPriscription;

    LinearLayout llAddDays;

    ScreenSlidePagerAdapter pagerAdapter;
    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;

    EditText editDrugName, editeChemical, editDosage, editeSelectDate;
    TextView tvReminder, tvOR;
    CheckBox cbAsNeeded;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescriptions);
        ButterKnife.bind(this);

        initDrawer(true);
        init();
    }

    private void init() {
        //setTitleText("Faro10");

        tvBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrescriptionsActivity.super.onBackPressed();
            }
        });

        tvAddPriscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPrescriptionDialog();
            }
        });


        tabLayout.addTab(tabLayout.newTab().setText("PRESCRIPTION HISTORY"));
        tabLayout.addTab(tabLayout.newTab().setText("PRESCRIPTION CONSISTENCY"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                refreshTabSelection();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    tabLayout.getTabAt(0).select();
                } else if (position == 1) {
                    tabLayout.getTabAt(1).select();
                    reload();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setPagingEnabled(false);
        pager.setOffscreenPageLimit(pagerAdapter.getCount());

        getDrugData();
    }

    public void reload() {
        PrescriptionConsistFrag page = (PrescriptionConsistFrag) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + 1);
        // based on the current position you can then cast the page to the correct
        // class and call the method:
        if (page != null) {
            ((PrescriptionConsistFrag) page).reload();
        }
    }

    public void refreshTabSelection() {

        if (tabLayout.getSelectedTabPosition() == 0) {
            pager.setCurrentItem(0);
        } else if (tabLayout.getSelectedTabPosition() == 1) {

            pager.setCurrentItem(1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment tp = null;
            switch (position) {
                case 0:
                    tp = PrescriptionHistoryFrag.newInstance(TYPE_ONE);
                    break;
                case 1:
                    tp = new PrescriptionConsistFrag();
                    break;
            }

            Bundle b = tp.getArguments();
            tp.setArguments(b);
            return tp;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    Dialog dialog;

    public void addPrescriptionDialog() {

        dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_add_priscription);

        TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        TextView tvSave = (TextView) dialog.findViewById(R.id.tvSave);
        tvOR = (TextView) dialog.findViewById(R.id.tvOR);
        tvReminder = (TextView) dialog.findViewById(R.id.tvReminder);
        cbAsNeeded = (CheckBox) dialog.findViewById(R.id.cbAsNeeded);

        editDrugName = (EditText) dialog.findViewById(R.id.editDrugName);
        editeChemical = (EditText) dialog.findViewById(R.id.editeChemical);
        editDosage = (EditText) dialog.findViewById(R.id.editDosage);
        editeSelectDate = (EditText) dialog.findViewById(R.id.editeSelectDate);

        final Calendar calendar = Calendar.getInstance();
        editeSelectDate.setText(Utils.parseTime(calendar.getTimeInMillis(), "MM/dd/yy"));
        editeSelectDate.setTag(calendar.getTimeInMillis());

        cbAsNeeded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbAsNeeded.isChecked()) {
                    tvReminder.setVisibility(View.GONE);
                    tvOR.setVisibility(View.GONE);
                } else {
                    tvReminder.setVisibility(View.VISIBLE);
                    tvOR.setVisibility(View.VISIBLE);
                }
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    setPriscriptionData();
                }
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        editDrugName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DrugsRes res = Utils.getDrugsData(getActivity());
                if (res != null) {
                    ArrayList<Spinner> data = new ArrayList<Spinner>();
                    for (int i = 0; i < res.drugs.size(); i++) {
                        data.add(new Spinner("" + res.drugs.get(i).id, res.drugs.get(i).friendlyName, res.drugs.get(i).scientificName));
                    }
                    showSpinner("Select Drugs", editDrugName, editeChemical, data, true, null);
                }
            }
        });
        editeSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(editeSelectDate);
            }
        });

        tvReminder.setTag("24");
        tvReminder.setText("Daily");

        tvReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Spinner> data = new ArrayList<>();
                data.add(new Spinner("1", "Hourly"));
                data.add(new Spinner("24", "Daily"));
                data.add(new Spinner("12", "Twice a Day"));
                showSpinner("Reminder", tvReminder, data, false, null);
            }
        });
        dialog.show();
    }

    public void showSpinner(final String title, final TextView tv, final TextView tv2,
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

                tv.setText(adapter.getItem(position).title);
                tv.setTag(adapter.getItem(position).ID);
                tv2.setText(adapter.getItem(position).desc);
                tv2.setTag(adapter.getItem(position).ID);

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

    public void showDatePicker(final EditText editText) {
        DatePickerDialog.Builder builder = null;
        builder = new DatePickerDialog.Builder(R.style.Material_App_Dialog_DatePicker_Light) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
//                String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                editText.setText(Utils.parseTime(dialog.getDate(), "MM/dd/yy"));
                editText.setTag(dialog.getDate());
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.positiveAction("" + getString(R.string.btn_ok))
                .negativeAction("" + getString(R.string.btn_cancel));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -60);

        Calendar calendarNext = Calendar.getInstance();
        calendarNext.add(Calendar.YEAR, 60);

        builder.dateRange(calendar.getTimeInMillis(), calendarNext.getTimeInMillis());
//        if (editText.getText().length() > 0) {
//            builder.date(Utils.parseTime(editText.getText().toString(), DATE_FORMAT).getTime());
//        }

        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);
    }

    private boolean validate() {
        if (editDrugName.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_drug_name), Toast.LENGTH_SHORT);
            return false;
        }
        if (editDosage.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_dosage), Toast.LENGTH_SHORT);
            return false;
        }
        if (editeSelectDate.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_select_date), Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    public void setPriscriptionData() {
        try {
            showDialog("");
            Debug.e("date", "" + Utils.parseTime(Utils.parseTimeUTCtoDefault(1 * Long.valueOf("" + editeSelectDate.getTag())).getTime(), "yyyy-MM-dd'T'hh:mm:ss'Z'"));

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
            body.addEncoded(RequestParamsUtils.DRUG_ID, editDrugName.getTag().toString().trim());
            body.addEncoded(RequestParamsUtils.DOSAGE, editDosage.getText().toString().trim());
            body.addEncoded(RequestParamsUtils.STARTED, Utils.parseTime(Utils.parseTimeUTCtoDefault(1 * Long.valueOf("" + editeSelectDate.getTag())).getTime(), "yyyy-MM-dd'T'hh:mm:ss'Z'"));

            if (cbAsNeeded.isChecked()) {
                body.addEncoded(RequestParamsUtils.AS_NEEDED, "1");
            } else {
                body.addEncoded(RequestParamsUtils.AS_NEEDED, "0");
                body.addEncoded(RequestParamsUtils.REMINDER, tvReminder.getTag().toString().trim());
            }

            for (int i = 0; i < body.build().size(); i++) {
                Debug.e("setPriscriptionData", "" + body.build().name(i) + " = " + body.build().value(i));
            }
            Call call = HttpClient.newRequestPost(getActivity(), body.build(), URLs.GET_PRESCRIPTION());
            call.enqueue(new GetVersionDataHandler(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersionDataHandler extends AsyncResponseHandlerOk {

        public GetVersionDataHandler(Activity context) {
            super(context);
        }

        @Override
        public void onStart() {
//            super.onStart();
        }

        @Override
        public void onFinish() {
//            super.onFinish();
            try {
                dismissDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSuccess(String response) {

            try {
                Debug.e("", "setPriscriptionData# " + response);
                if (response != null && response.length() > 0) {
                    dismissDialog();
                    showToast(getString(R.string.success), Toast.LENGTH_SHORT);
                    dialog.dismiss();

                    filterService();
//                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
            dismissDialog();
        }
    }

    public void filterService() {
//        for (int i = 0; i < pagerAdapter.getCount(); i++) {
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + 0);
        if (page != null) {
            ((PrescriptionHistoryFrag) page).getPriscriptionData();
//                ((ServicesFragment) page).filterService(res);
        }

//        Fragment page1 = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + 1);
//        if (page1 != null) {
//            ((ServicesFragment) page1).filterService(res);
////                ((ServicesFragment) page).filterService(res);
//        }
//        }
    }

    public void getDrugData() {
        try {

            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_DRUGS());

            Call call = HttpClient.newRequestGet(getActivity(), body.build().toString());
            call.enqueue(new GetVersionDataHan(getActivity()));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersionDataHan extends AsyncResponseHandlerOk {

        public GetVersionDataHan(Activity context) {
            super(context);
        }

        @Override
        public void onStart() {
//            super.onStart();
        }

        @Override
        public void onFinish() {
//            super.onFinish();
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSuccess(String response) {

            try {
                Debug.e("", "getDrugData# " + response);
                if (response != null && response.length() > 0) {
                    Utils.setPref(getActivity(), Constant.DRUGS_DATA, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
        }
    }
}
