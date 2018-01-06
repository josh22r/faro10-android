package com.kartum;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.kartum.adapter.SpinnerAdapter;
import com.kartum.objects.Country;
import com.kartum.objects.Spinner;
import com.kartum.utils.AsyncProgressDialog;
import com.kartum.utils.AsyncResponseHandlerOk;
import com.kartum.utils.Constant;
import com.kartum.utils.HttpClient;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.SpinnerCallback;
import com.kartum.utils.URLs;
import com.kartum.utils.Utils;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.RequestBody;


public class BaseActivity extends AppCompatActivity {
    AsyncProgressDialog ad;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);

        if (!(getActivity() instanceof LoginActivity)) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Constant.FINISH_ACTIVITY);

            commonReciever = new MyEventServiceReciever();
            LocalBroadcastManager.getInstance(this).registerReceiver(
                    commonReciever, intentFilter);
        }
    }

    Drawer result;

    public void initDrawer(final boolean b) {

        if (b) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            ViewGroup drawerFooterView = (ViewGroup) getLayoutInflater().inflate(R.layout.nav_footer_main, null, false);
            drawerFooterView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hideMenu(true);
                    confirmLogout();
                }
            });

//create the drawer and remember the `Drawer` result object
            result = new DrawerBuilder()

                    .withActivity(this).withCloseOnClick(true).withSelectedItemByPosition(-1)
//                    .withHeader(R.layout.nav_header_main)
                    .withDrawerWidthDp(280)
                    .withStickyFooter(drawerFooterView)
                    .withStickyFooterShadow(false)
                    .withStickyFooterDivider(true)
                    .addDrawerItems(
                            new PrimaryDrawerItem().withName("Help").withSelectable(false).withIcon(R.mipmap.ic_help_),
                            new PrimaryDrawerItem().withName("My Team").withSelectable(false).withIcon(R.mipmap.ic_draw_myteam),
//                            new PrimaryDrawerItem().withName("Find a Clinician").withSelectable(false).withIcon(R.mipmap.ic_location_menu),
                            new PrimaryDrawerItem().withName("Dashboard").withSelectable(false).withIcon(R.mipmap.ic_draw_dashboard),
                            new PrimaryDrawerItem().withName("Call Suicide Hotline").withSelectable(false).withIcon(R.mipmap.ic_draw_call),
                            new PrimaryDrawerItem().withName("About Faro10").withSelectable(false).withIcon(R.mipmap.ic_draw_about_),
                            new PrimaryDrawerItem().withName("Feedback").withSelectable(false).withIcon(R.mipmap.ic_draw_feedback),
                            new PrimaryDrawerItem().withName("Invite").withSelectable(false).withIcon(R.mipmap.ic_draw_invite),
                            new PrimaryDrawerItem().withName("Settings").withSelectable(false).withIcon(R.mipmap.ic_draw_setting)

                    )
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            if (position == 0) {

//                                String url = "https://www.faro10.com/assigned_exercises";
//                                Intent i = new Intent(Intent.ACTION_VIEW);
//                                i.setData(Uri.parse(url));
//                                startActivity(i);

                                Intent intent = new Intent(getActivity(),
                                        HelpFAQActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                hideMenu(false);
                                finishActivity();

                            } else if (position == 1) {
                                if (getActivity() instanceof MyTeamActivity) {
                                    hideMenu(true);
                                } else {
                                    Intent intent = new Intent(getActivity(),
                                            MyTeamActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                            | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                    hideMenu(false);
                                    finishActivity();
                                }
//                            } else if (position == 2) {
//                                if (getActivity() instanceof MyTeamActivity) {
//                                    hideMenu(true);
//                                } else {
//                                    Intent intent = new Intent(getActivity(),
//                                            MyTeamActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                                            | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                                    startActivity(intent);
//                                    hideMenu(false);
//                                    finishActivity();
//                                }
                            } else if (position == 2) {

                                if (getActivity() instanceof MainActivity) {
                                    hideMenu(true);
                                } else {
                                    Intent intent = new Intent(getActivity(),
                                            MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                            | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                    hideMenu(false);
                                    finishActivity();
                                }

                            } else if (position == 3) {

//                                if (getActivity() instanceof MainActivity) {
//                                    hideMenu(true);
//                                } else {
//                                    Intent intent = new Intent(getActivity(),
//                                            MainActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                                            | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                                    startActivity(intent);
                                showCallDialog();
                                hideMenu(false);
//                                    finishActivity();

//                                }

                            } else if (position == 4) {
                                if (getActivity() instanceof AboutActivity) {
                                    hideMenu(true);
                                } else {
                                    Intent intent = new Intent(getActivity(),
                                            AboutActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                            | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                    hideMenu(false);
                                    finishActivity();
                                }

                            } else if (position == 5) {
                                if (getActivity() instanceof FeedbackActivity) {
                                    hideMenu(true);
                                } else {
                                    Intent intent = new Intent(getActivity(),
                                            FeedbackActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                            | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                    hideMenu(false);
                                    finishActivity();
                                }

                            } else if (position == 6) {
                                if (getActivity() instanceof InviteActivity) {
                                    hideMenu(true);
                                } else {
                                    Intent intent = new Intent(getActivity(),
                                            InviteActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                            | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                    hideMenu(false);
                                    finishActivity();
                                }
                            } else if (position == 7) {

                                if (getActivity() instanceof SettingActivity) {
                                    hideMenu(true);
                                } else {
                                    Intent intent = new Intent(getActivity(),
                                            SettingActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                            | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                    hideMenu(false);
                                    finishActivity();
                                }
                            } else if (position == 8) {

                                if (getActivity() instanceof SettingActivity) {
                                    hideMenu(true);
                                } else {
                                    Intent intent = new Intent(getActivity(),
                                            SettingActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                            | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                    hideMenu(false);
                                    finishActivity();
                                }
                            }
                            return true;
                        }
                    })
                    .build();

            ImageView imgMenu = (ImageView) findViewById(R.id.imgMenu);

            if (imgMenu != null) {
                imgMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (result.isDrawerOpen()) {
                            result.closeDrawer();
                        } else {
                            result.openDrawer();
                        }
                    }
                });
            }

            ImageView imgChartData = (ImageView) findViewById(R.id.imgChartData);

            if (imgChartData != null) {
                imgChartData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getActivity(),
                                AggregateChartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        hideMenu(false);
//                        finishActivity();
                    }
                });
            }

            final ImageView imgDashboard = (ImageView) findViewById(R.id.imgDashboard);

            if (imgDashboard != null) {
                imgDashboard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(),
                                MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        hideMenu(false);
//                        finishActivity();
                    }
                });
            }

            final ImageView imgMyProfile = (ImageView) findViewById(R.id.imgMyProfile);
            final ImageView imgMyProfile2 = (ImageView) findViewById(R.id.imgMyProfile2);

            if (imgMyProfile != null) {
                imgMyProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        if (imgMyProfile.isClickable() == true)
//                        {
//                        }
//                        if (imgMyProfile.getVisibility() == View.VISIBLE)
//                        {
//                            imgMyProfile.setVisibility(view.GONE);
//                            imgMyProfile2.setVisibility(view.VISIBLE);
//                        }
//                        else {
//                            imgMyProfile.setVisibility(view.VISIBLE);
//                            imgMyProfile2.setVisibility(view.GONE);
//                        }
                        Intent intent = new Intent(getActivity(),
                                MyProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        hideMenu(false);
//                        finishActivity();
                    }
                });
            }

//            if (imgMyProfile.isClickable() == false)
//            {
//                imgMyProfile.setImageDrawable(getResources().getDrawable(R.drawable.ic_user_blue));
//            }
//            else {
//                imgMyProfile.setImageDrawable(getResources().getDrawable(R.drawable.ic_user_primary_dark_24dp));
//            }

        } else {
            ImageView imgMenu = (ImageView) findViewById(R.id.imgMenu);
            imgMenu.setVisibility(View.GONE);
        }

//        initMenuItems();
//        fillProfileData();
    }

    private void confirmLogout() {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title(R.string.logout_title)
                .content(R.string.logout_msg)
                .positiveText(R.string.btn_yes)
                .negativeText(R.string.btn_no)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {

                        logout();
                        showToast("Logged Out", Toast.LENGTH_SHORT);
//                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(Constant.FINISH_ACTIVITY));
                        Utils.clearLoginCredetials(getActivity());

                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {

                    }
                });

        MaterialDialog dialog = builder.build();
        dialog.show();
    }

    Dialog dialog1;

    public void showCallDialog() {

        dialog1 = new Dialog(getActivity());
        dialog1.setCanceledOnTouchOutside(true);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.popup_call_suicide);

        TextView tvNo = (TextView) dialog1.findViewById(R.id.tvNo);
        TextView tvYes = (TextView) dialog1.findViewById(R.id.tvYes);

        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:18002738255"));
                startActivity(callIntent);
                dialog1.dismiss();
            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }

    Dialog dialog;

    private void hideMenu(boolean b) {
        try {
//            if (b)
            result.closeDrawer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void finishActivity() {
        if ((getActivity() instanceof MainActivity)) {

        } else {
            getActivity().finish();
        }

    }

    ImageLoader imageLoader;


    MyEventServiceReciever commonReciever;

    class MyEventServiceReciever extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction().equalsIgnoreCase(
                        Constant.FINISH_ACTIVITY)) {
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public BaseActivity getActivity() {
        return this;
    }

    private TextView tvTitleText;

    public void setTitleText(String text) {
        try {

            if (tvTitleText == null)
                tvTitleText = (TextView) findViewById(R.id.tvTitleText);
            tvTitleText.setText(text);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean handled = super.onKeyDown(keyCode, event);

        // Eat the long press event so the keyboard doesn't come up.
        if (keyCode == KeyEvent.KEYCODE_MENU && event.isLongPress()) {
            return true;
        }

        return handled;
    }

    Toast toast;

    public void showToast(final String text, final int duration) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                toast.setText(text);
                toast.setDuration(duration);
                toast.show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        try {
            LocalBroadcastManager.getInstance(getApplicationContext())
                    .unregisterReceiver(commonReciever);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        try {
            if (result.isDrawerOpen()) {
                result.closeDrawer();
            } else {
                super.onBackPressed();
            }
        } catch (Exception e) {
            super.onBackPressed();
        }
    }


    BaseCallback baseCallback;

    public void setBaseCallback(BaseCallback baseCallback) {
        this.baseCallback = baseCallback;
    }

    interface BaseCallback {
        void onMasterDataLoad();
    }

    public void logout() {
        try {

//            RequestParams params = RequestParamsUtils.newRequestParams(getActivity());
//
//            AsyncHttpClient clientPhotos = AsyncHttpRequest.newRequest(getActivity());
//            clientPhotos.delete(URLs.LOGOUT(), params, new AsyncResponseHandler(getActivity()) {
//                @Override
//                public void onSuccess(String content) {
//
//                }
//
//                @Override
//                public void onFailure(Throwable e, String content) {
//
//                }
//            });


            RequestBody body = RequestParamsUtils.newRequestBody(getActivity(), "");
            Call call = HttpClient.newRequestDelete(getActivity(), body, URLs.LOGOUT());
            call.enqueue(new AsyncResponseHandlerOk(getActivity()) {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(String content) {

                }

                @Override
                public void onFinish() {

                }

                @Override
                public void onFailure(Throwable e, String content) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Spinner> getCountryCodes() {
        ArrayList<Spinner> data = new ArrayList<>();
        List<Country> countryList = Utils.getAllCountries(getActivity());

        for (Country country : countryList) {
// String[] countryFullName = country.getName().trim().split(":");
            data.add(new Spinner("" + country.getCode(), country.getName()));
        }

        return data;

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

                tv.setText(adapter.getItem(position).title);
                tv.setTag(adapter.getItem(position).ID);

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
