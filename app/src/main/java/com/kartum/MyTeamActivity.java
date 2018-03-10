package com.kartum;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Patterns;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.common.view.SimpleListDividerDecorator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kartum.adapter.CliniciansListAdapter;
import com.kartum.adapter.EditObserverAdapter;
import com.kartum.adapter.MyMsgSwipeAdapter;
import com.kartum.adapter.ObserversListAdapter;
import com.kartum.objects.CliniciansRes;
import com.kartum.objects.MessageReadRes;
import com.kartum.objects.MessagesRes;
import com.kartum.objects.Response;
import com.kartum.utils.AsyncResponseHandlerOk;
import com.kartum.utils.Constant;
import com.kartum.utils.Debug;
import com.kartum.utils.HttpClient;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.URLs;
import com.kartum.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;

public class MyTeamActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    RecyclerView mRecyclerView;

    @BindView(R.id.rvObserverList)
    RecyclerView rvObserverList;
    @BindView(R.id.rvCliciansList)
    RecyclerView rvCliciansList;
    RecyclerView.LayoutManager layoutManager;
    public MyMsgSwipeAdapter myMsgSwipeAdapter;
    public ObserversListAdapter mObserversListAdapter;
    public CliniciansListAdapter mCliniciansListAdapter;
    public EditObserverAdapter mEditObsAdapter;

    @BindView(R.id.imgAddObsr)
    ImageView imgAddObsr;
    @BindView(R.id.imgErrowUp)
    TextView imgErrowUp;
    @BindView(R.id.tvErrorMsg)
    TextView tvErrorMsg;

    @BindView(R.id.llPlaceHolder)
    View llPlaceHolder;

    @BindView(R.id.imgMsgErrowUp)
    ImageView imgMsgErrowUp;
    @BindView(R.id.imgMsgErrowDow)
    ImageView imgMsgErrowDow;
    @BindView(R.id.imgCliniErrowUp)
    ImageView imgCliniErrowUp;
    @BindView(R.id.imgCliniErrowDow)
    ImageView imgCliniErrowDow;
    @BindView(R.id.imgErrow)
    ImageView imgErrow;
    @BindView(R.id.imgErrowDow)
    ImageView imgErrowDow;
    @BindView(R.id.imgErrowU)
    ImageView imgErrowU;
    @BindView(R.id.imgErrowD)
    ImageView imgErrowD;
    @BindView(R.id.imgNext)
    ImageView imgNext;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.imgBackFalse)
    ImageView imgBackFalse;
    @BindView(R.id.imgNextFalse)
    ImageView imgNextFalse;

    @BindView(R.id.llMyMsgDetail)
    LinearLayout llMyMsgDetail;
    @BindView(R.id.llMyMsg)
    LinearLayout llMyMsg;
    @BindView(R.id.llMyClinian)
    LinearLayout llMyClinian;
    @BindView(R.id.llMyCliniDetail)
    LinearLayout llMyCliniDetail;
    @BindView(R.id.llCliniList)
    LinearLayout llCliniList;
    @BindView(R.id.llMySubClini)
    LinearLayout llMySubClini;
    @BindView(R.id.llMyObserverList)
    LinearLayout llMyObserverList;

    @BindView(R.id.llArrows)
    LinearLayout llArrows;

    //    @BindView(R.id.llNewest)
//    LinearLayout llNewest;
    @BindView(R.id.flMsgData)
    LinearLayout flMsgData;

    EditText editEmail, editRelationship, editName;
    Switch switchStatus;
    CheckBox cbGuardian;

    int count = 0;

    static final int APPROVE_CLINICIAN_REQUEST = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myteam);
        ButterKnife.bind(this);

        initDrawer(true);
        init();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (myMsgSwipeAdapter != null) {
            myMsgSwipeAdapter.saveStates(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (myMsgSwipeAdapter != null) {
            myMsgSwipeAdapter.restoreStates(savedInstanceState);
        }
    }

    private void init() {
        //setTitleText("Faro10");

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
        myMsgSwipeAdapter = new MyMsgSwipeAdapter(this);
        recyclerView.setAdapter(myMsgSwipeAdapter);

        layoutManager = new LinearLayoutManager(this);
        rvCliciansList.setLayoutManager(layoutManager);
        mCliniciansListAdapter = new CliniciansListAdapter(this);
        rvCliciansList.setAdapter(mCliniciansListAdapter);

        layoutManager = new LinearLayoutManager(this);
        rvObserverList.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));
        mObserversListAdapter = new ObserversListAdapter(this);
        rvObserverList.setAdapter(mObserversListAdapter);

//        llNewest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (llMyMsgDetail.getVisibility() == view.VISIBLE && imgMsgErrowUp.getVisibility() == view.VISIBLE) {
//                    llMyMsgDetail.setVisibility(view.GONE);
//                    imgMsgErrowUp.setVisibility(view.GONE);
//                    imgMsgErrowDow.setVisibility(view.VISIBLE);
//
//                } else {
//                    llMyMsgDetail.setVisibility(view.VISIBLE);
//                    imgMsgErrowUp.setVisibility(view.VISIBLE);
//                    imgMsgErrowDow.setVisibility(view.GONE);
//                }
//            }
//        });

        llMyMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (myRes.size() == 0) {

                    if (tvErrorMsg.getVisibility() == view.VISIBLE && imgMsgErrowUp.getVisibility() == view.VISIBLE) {
                        tvErrorMsg.setVisibility(view.GONE);
                        imgMsgErrowUp.setVisibility(view.GONE);
                        imgMsgErrowDow.setVisibility(view.VISIBLE);

                    } else {
                        tvErrorMsg.setVisibility(view.VISIBLE);
                        imgMsgErrowUp.setVisibility(view.VISIBLE);
                        imgMsgErrowDow.setVisibility(view.GONE);
                    }

                } else {
                    if (llMyMsgDetail.getVisibility() == view.VISIBLE && imgMsgErrowUp.getVisibility() == view.VISIBLE) {
                        llMyMsgDetail.setVisibility(view.GONE);
                        imgMsgErrowUp.setVisibility(view.GONE);
                        imgMsgErrowDow.setVisibility(view.VISIBLE);

                    } else {
                        llMyMsgDetail.setVisibility(view.VISIBLE);
                        imgMsgErrowUp.setVisibility(view.VISIBLE);
                        imgMsgErrowDow.setVisibility(view.GONE);
                    }
                }


            }
        });

        llMyClinian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llMyCliniDetail.getVisibility() == view.VISIBLE && imgCliniErrowUp.getVisibility() == view.VISIBLE) {

                    llMyCliniDetail.setVisibility(view.GONE);
                    imgCliniErrowUp.setVisibility(view.GONE);
                    imgCliniErrowDow.setVisibility(view.VISIBLE);
                } else {
                    llMyCliniDetail.setVisibility(view.VISIBLE);
                    imgCliniErrowUp.setVisibility(view.VISIBLE);
                    imgCliniErrowDow.setVisibility(view.GONE);
                }
            }
        });

        imgErrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flMsgData.getVisibility() == View.VISIBLE) {
                    flMsgData.setVisibility(View.GONE);
                    imgErrowUp.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_black, 0);
                } else {
                    flMsgData.setVisibility(View.VISIBLE);
                    imgErrowUp.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_black_, 0);
                }
            }
        });

//        llNewest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (llNewest.getVisibility() == view.VISIBLE && imgCliniErrowUp.getVisibility() == view.VISIBLE) {
//
//                    llMyCliniDetail.setVisibility(view.GONE);
//                    imgCliniErrowUp.setVisibility(view.GONE);
//                    imgCliniErrowDow.setVisibility(view.VISIBLE);
//                } else {
//                    llMyCliniDetail.setVisibility(view.VISIBLE);
//                    imgCliniErrowUp.setVisibility(view.VISIBLE);
//                    imgCliniErrowDow.setVisibility(view.GONE);
//                }
//            }
//        });
        imgErrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llCliniList.getVisibility() == view.VISIBLE && imgErrow.getVisibility() == view.VISIBLE) {
                    imgErrow.setVisibility(view.GONE);
                    imgErrowDow.setVisibility(view.VISIBLE);
                    llCliniList.setVisibility(view.GONE);
                }
            }
        });

        imgErrowDow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llCliniList.getVisibility() == view.GONE && imgErrow.getVisibility() == view.GONE) {
                    imgErrowDow.setVisibility(view.GONE);
                    imgErrow.setVisibility(view.VISIBLE);
                    llCliniList.setVisibility(view.VISIBLE);
                }
            }
        });

        imgErrowU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (llMyObserverList.getVisibility() == view.VISIBLE && imgErrowU.getVisibility() == view.VISIBLE) {
                    imgErrowU.setVisibility(view.GONE);
                    imgErrowD.setVisibility(view.VISIBLE);
                    llMyObserverList.setVisibility(view.GONE);
                }
            }
        });

        imgErrowD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llMyObserverList.getVisibility() == view.GONE && imgErrowU.getVisibility() == view.GONE) {
                    imgErrowD.setVisibility(view.GONE);
                    imgErrowU.setVisibility(view.VISIBLE);
                    llMyObserverList.setVisibility(view.VISIBLE);
                }
            }
        });


        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<MessagesRes.Message> data = getPage(++pageNumber);
                if (data != null) {
                    imgBack.setImageResource(R.drawable.ic_arrow_blue_24dp);
                    myMsgSwipeAdapter.addAll(data);
                } else {

                    --pageNumber;

                }
            }
        });
        imgBack.setImageResource(R.drawable.ic_arrow_back_);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<MessagesRes.Message> data = getPage(--pageNumber);

                if (data != null) {
                    imgNext.setImageResource(R.drawable.ic_arrow_next_24dp);
                    myMsgSwipeAdapter.addAll(data);
                } else {
                    imgBack.setImageResource(R.drawable.ic_arrow_back_);
                    ++pageNumber;
                }
            }
        });


        mObserversListAdapter.setmEventlistener(new ObserversListAdapter.Eventlistener() {
            @Override
            public void OnMenuclick(int position, View view) {
                showPopupMenu(view, mObserversListAdapter.getObjectId(position), mObserversListAdapter.getName(position));

//                showPopupMenu(view, mObserversListAdapter.getObjectId(position));
//                Debug.e("deleteid", "" + mObserversListAdapter.getObjectId(position));
            }
        });

        mCliniciansListAdapter.setmEventlistener(new CliniciansListAdapter.Eventlistener() {
            @Override
            public void OnMenuclick(int position, View view) {
                CliniciansRes.Membership data = mCliniciansListAdapter.getItem(position);
                showClinicPopupMenu(view, data);
            }
        });

        myMsgSwipeAdapter.setEventlistener(new MyMsgSwipeAdapter.Eventlistener() {
            @Override
            public void onItemReceiveClick(int position) {
                MessagesRes.Message data = myMsgSwipeAdapter.getItem(position);
                readMessageData(data.id);
                Debug.e("read", "" + data.id);
            }

            @Override
            public void onItemDeleteClick(int position) {
                MessagesRes.Message data = myMsgSwipeAdapter.getItem(position);
                Debug.e("Delete Data ID", "" + data.id);
                msgonDeletePopup(data.id);
            }

            @Override
            public void onItemViewClick(int position) {
                MessagesRes.Message data = myMsgSwipeAdapter.getItem(position);
                showMsgDialog(data);
                Debug.e("readdata", "" + data.id);
            }
        });


        imgAddObsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addObserverDialog();
            }
        });

        getCliniciansData();
        getObserversData();
        getMessagesData();
    }

    int totalCount = 4;
    int pageNumber = 0;

    public List<MessagesRes.Message> getPage(int pageNo) {
        int fromIndex = pageNo * totalCount;
        int toIndex = fromIndex + totalCount;

        if (pageNo == 0) {
            imgBack.setImageResource(R.drawable.ic_arrow_back_);
            imgNext.setImageResource(R.drawable.ic_arrow_forward_);
        }

        if (fromIndex >= 0 && toIndex > fromIndex && toIndex < myRes.size()) {
            imgNext.setImageResource(R.drawable.ic_arrow_next_24dp);
            return myRes.subList(fromIndex, toIndex);
        } else if (toIndex >= myRes.size() && fromIndex < myRes.size()) {

            imgNext.setImageResource(R.drawable.ic_arrow_forward_);

            if (fromIndex == -4) {
                imgBack.setClickable(false);
            } else {
                return myRes.subList(fromIndex, myRes.size());
            }


        }
        return null;
    }

    Dialog dialog;

    public void addObserverDialog() {
        dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_add_observer);

        TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        TextView tvSave = (TextView) dialog.findViewById(R.id.tvSave);
        editEmail = (EditText) dialog.findViewById(R.id.editEmail);
        editRelationship = (EditText) dialog.findViewById(R.id.editRelationship);
//        switchStatus = (Switch) dialog.findViewById(R.id.switchStatus);
        cbGuardian = (CheckBox) dialog.findViewById(R.id.cbGuardian);

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailValidate()) {
                    addObserverData();
                }
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    Dialog msgDialog;

    public void showMsgDialog(final MessagesRes.Message data) {
        msgDialog = new Dialog(getActivity());
        msgDialog.setCanceledOnTouchOutside(true);
        msgDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        msgDialog.setCancelable(true);
        msgDialog.setContentView(R.layout.popup_message);

        TextView tvCancel = (TextView) msgDialog.findViewById(R.id.tvCancel);
        TextView tvDate = (TextView) msgDialog.findViewById(R.id.tvDate);
        TextView tvUserName = (TextView) msgDialog.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) msgDialog.findViewById(R.id.tvBody);
        final Button tvMsgRead = (Button) msgDialog.findViewById(R.id.tvMsgRead);

        tvDate.setText(Utils.nullSafe(Utils.parseTimeUTCtoDefault(data.updatedAt, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd/MM/yy")));
        tvUserName.setText(Utils.nullSafe("" + data.clinician.userId));
        tvBody.setText(Utils.nullSafe("" + data.body));

//        tvDate.setText(Utils.nullSafe(Utils.parseTimeUTCtoDefault(res.message.updatedAt, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd/MM/yy")));
//        tvUserName.setText(Utils.nullSafe("" + res.message.clinician.userId));
//        tvBody.setText(Utils.nullSafe("" + res.message.body));

        if (data.read) {
            tvMsgRead.setText("Mark As Unread");

        } else {
            tvMsgRead.setText("Mark As Read");
        }

        tvMsgRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (data.read) {
                    unReadMessageData(data.id);

                } else {
                    readMessageData(data.id);
                }
//                unReadMessageData(res.message.id);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgDialog.dismiss();
            }
        });

        msgDialog.show();
    }

    private boolean emailValidate() {

        if (editEmail.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_email), Toast.LENGTH_SHORT);
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText()).matches()) {
            showToast(getString(R.string.err_email_invalid), Toast.LENGTH_SHORT);
            return false;
        }

        if (editRelationship.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_relationship), Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    public void showPopupMenu(View v, final int id, final String user) {
        PopupMenu popup = new PopupMenu(getActivity(), v, Gravity.RIGHT);
        popup.getMenu().add(0, 0, 0, "Edit").setIcon(getResources().getDrawable(R.drawable.ic_edit_red_26dp));
        popup.getMenu().add(1, 1, 1, "Delete").setIcon(getResources().getDrawable(R.drawable.ic_do_not_disturb_black_24dp));
//        popup.getMenu().add(2, 2, 2, "Approve").setIcon(getResources().getDrawable(R.drawable.ic_done_red_24dp));

        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == 0) {
                    showDialogPopup(user);
                }
                if (item.getItemId() == 1) {
                    deleteObsPopup(id);
                }
//                if (item.getItemId() == 2) {
//                }
                return true;
            }
        });
        popup.show();
    }


    public void showClinicPopupMenu(final View v, final CliniciansRes.Membership data) {
        final PopupMenu popup = new PopupMenu(getActivity(), v, Gravity.RIGHT);
//        popup.getMenu().add(1, 1, 1, "Delete").setIcon(getResources().getDrawable(R.drawable.ic_do_not_disturb_black_24dp));

        if (data.status.equalsIgnoreCase("pending")) {
            popup.getMenu().add(0, 0, 0, "Approve").setIcon(getResources().getDrawable(R.drawable.ic_done_red_24dp));
            popup.getMenu().add(1, 1, 1, "Delete").setIcon(getResources().getDrawable(R.drawable.ic_do_not_disturb_black_24dp));

        } else {
            popup.getMenu().add(0, 0, 0, "Approved").setIcon(getResources().getDrawable(R.drawable.ic_done_red_24dp)).setEnabled(false);
            popup.getMenu().add(1, 1, 1, "Delete").setIcon(getResources().getDrawable(R.drawable.ic_do_not_disturb_black_24dp));
        }

//        popup.getMenu().add(0, 0, 0, "Approve").setIcon(getResources().getDrawable(R.drawable.ic_done_red_24dp));
//        popup.getMenu().add(1, 1, 1, "Delete").setIcon(getResources().getDrawable(R.drawable.ic_do_not_disturb_black_24dp));

        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == 0) {
                    Intent intent = new Intent(getApplicationContext(), ApproveClinicianActivity.class);

                    intent.putExtra("ID", data.clinicianId);
                    intent.putExtra("Name", data.userName);
                    intent.putExtra("Clinic_Name", data.clinicName);

                    startActivityForResult(intent, APPROVE_CLINICIAN_REQUEST);

//                    approveClinicData(data.clinicianId);
                }
                if (item.getItemId() == 1) {
                    clinicianMsgDeletePopup(data.clinicianId);

                }
                return true;
            }
        });
        popup.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == APPROVE_CLINICIAN_REQUEST) {
            if (resultCode == RESULT_OK) {

                Integer clinicianID = data.getIntExtra("ID", -1);
                if (clinicianID != -1) {
                    String base64signature = data.getStringExtra("Signature");
                    approveClinicData(clinicianID, base64signature);
                }
            }
        }
    }

    Dialog mDialog;

    public void showDialogPopup(String user) {
        mDialog = new Dialog(getActivity());
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCancelable(true);
        mDialog.setContentView(R.layout.popup_edit_obs);

        mRecyclerView = (RecyclerView) mDialog.findViewById(R.id.mRecyclerView);
        TextView tvCancel = (TextView) mDialog.findViewById(R.id.tvCancel);
//        TextView tvSave = (TextView) dialog.findViewById(R.id.tvSave);

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mEditObsAdapter = new EditObserverAdapter(this);
        mRecyclerView.setAdapter(mEditObsAdapter);

        mEditObsAdapter.setmEventlistener(new EditObserverAdapter.Eventlistener() {
            @Override
            public void onItemviewClick(int position) {
//                setEditObserverData(mEditObsAdapter.getObjectId(position));
                JSONArray data = mEditObsAdapter.getAll();
                try {
                    data.getJSONObject(position).getInt("meds");
                    setEditObserverData(mEditObsAdapter.getObjectId(position), data.getJSONObject(position).getInt("meds"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mDialog.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        getObsData(user);

        mDialog.show();
    }

    private void deleteObsPopup(final int id) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
//                .title(R.string.logout_title)
                .content(R.string.obs_delete)
                .positiveText(R.string.btn_ok)
                .negativeText(R.string.btn_cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        DeleteObserverData(id);

                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {

                    }
                });

        MaterialDialog dialog = builder.build();
        dialog.show();
    }

    private void msgonDeletePopup(final int id) {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
//                .title(R.string.logout_title)
                .content(R.string.msg_delete)
                .positiveText(R.string.btn_ok)
                .negativeText(R.string.btn_cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        deleteMsgData(id);
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {

                    }
                });

        MaterialDialog dialog = builder.build();
        dialog.show();
    }

    private void clinicianMsgDeletePopup(final int id) {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
//                .title(R.string.logout_title)
                .content(R.string.msg_delete_clinician)
                .positiveText(R.string.btn_ok)
                .negativeText(R.string.btn_cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        deleteClinicData(id);
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {

                    }
                });

        MaterialDialog dialog = builder.build();
        dialog.show();
    }

    private boolean editObseValidate() {
        if (editName.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_name), Toast.LENGTH_SHORT);
            return false;
        }
        if (editRelationship.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_relationship), Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    public void DeleteObserverData(int id) {
        try {
            showDialog("");

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
            Call call = HttpClient.newRequestDelete(getActivity(), body.build(), URLs.DELETE_OBSERVER() + id);
            call.enqueue(new GetVersionDataHandle(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersionDataHandle extends AsyncResponseHandlerOk {

        public GetVersionDataHandle(Activity context) {
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
                Debug.e("", "DeleteObserverData# " + response);
                if (response != null && response.length() > 0) {

                    Response res = new Gson().fromJson(response, new TypeToken<Response>() {
                    }.getType());
                    if (res.status.equalsIgnoreCase("ok")) {
                        showToast("Observer Deleted", Toast.LENGTH_LONG);
//                        Intent i = new Intent(getActivity(), MainActivity.class);
//                        startActivity(i);
//                        finish();
                        getObserversData();
                    } else {
                        showToast("Delete Fail", Toast.LENGTH_LONG);
                        dialog.dismiss();
                    }
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

    public void deleteMsgData(int id) {
        try {
            showDialog("");
//            RequestParams params = RequestParamsUtils.newRequestParams(getActivity());
//            Debug.e("deleteMsgData-->>", "" + params.toString());
//            AsyncHttpClient client = AsyncHttpRequest.newRequest(getActivity());
//            client.delete(URLs.DEL_MESSAGE() + id, params, new GetVersionDataH(getActivity()));

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
            Call call = HttpClient.newRequestDelete(getActivity(), body.build(), URLs.DEL_MESSAGE() + id);
            call.enqueue(new GetVersionDataH(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersionDataH extends AsyncResponseHandlerOk {

        public GetVersionDataH(Activity context) {
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
                Debug.e("", "deleteMsgData# " + response);
                if (response != null && response.length() > 0) {

                    getMessagesData();
                    showToast("Message Deleted", Toast.LENGTH_LONG);

//                    Response res = new Gson().fromJson(response, new TypeToken<Response>() {
//                    }.getType());

//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            getMessagesData();
//                            showToast("Message Deleted", Toast.LENGTH_LONG);
//                        }
//                    });

//                    if (res.status.equalsIgnoreCase("ok")) {
//                        showToast("Message Deleted", Toast.LENGTH_LONG);
////                        getMessagesData();
//                    } else {
//                        showToast("Delete Fail", Toast.LENGTH_LONG);
//                        dialog.dismiss();
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
            Debug.e("", "deleteMsgData onFailure# " + content);
            dismissDialog();
        }
    }

    public void deleteClinicData(int id) {
        try {
            showDialog("");
//            RequestParams params = RequestParamsUtils.newRequestParams(getActivity());
//            Debug.e("deleteClinicData-->>", "" + params.toString());
//            AsyncHttpClient client = AsyncHttpRequest.newRequest(getActivity());
//            client.delete(URLs.DELETE_CLINICIAN() + id, params, new GetVersion(getActivity()));

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
            Call call = HttpClient.newRequestDelete(getActivity(), body.build(), URLs.DELETE_CLINICIAN() + id);
            call.enqueue(new GetVersion(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersion extends AsyncResponseHandlerOk {

        public GetVersion(Activity context) {
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
                Debug.e("", "deleteClinicData# " + response);
                if (response != null && response.length() > 0) {

                    Response res = new Gson().fromJson(response, new TypeToken<Response>() {
                    }.getType());
                    if (res.status.equalsIgnoreCase("ok")) {
                        showToast("Clinician Deleted", Toast.LENGTH_LONG);
                        getCliniciansData();
                    } else {
                        showToast("Delete Fail", Toast.LENGTH_LONG);
//                        dialog.dismiss();
                    }
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

    public void readMessageData(int id) {
        try {
            showDialog("");
//            RequestParams params = RequestParamsUtils.newRequestParams(getActivity());
//            Debug.e("readMessageData-->>", "" + params.toString());
//            AsyncHttpClient client = AsyncHttpRequest.newRequest(getActivity());
//            client.patch(URLs.READ_MESSAGE() + id + "/mark_as_read", params, new GetVersionDataHandler1(getActivity()));

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
            Call call = HttpClient.newRequestPatch(getActivity(), body.build(), URLs.READ_MESSAGE() + id + "/mark_as_read");
            call.enqueue(new GetVersionDataHandler1(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersionDataHandler1 extends AsyncResponseHandlerOk {

        public GetVersionDataHandler1(Activity context) {
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
                Debug.e("", "readMessageData# " + response);
                if (response != null && response.length() > 0) {

                    final MessageReadRes res = new Gson().fromJson(response, new TypeToken<MessageReadRes>() {
                    }.getType());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            showMsgDialog(res);
//                            unReadMessageData(res.message.id);
                        }
                    });

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getMessagesData();
//                            msgDialog.dismiss();
                        }
                    });

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
            Debug.e("readMessageData", "" + content);
            dismissDialog();
        }
    }

    public void approveClinicData(int id, String encodedSignature) {
        try {
            showDialog("");
//            RequestParams params = RequestParamsUtils.newRequestParams(getActivity());
//            Debug.e("approveClinicData-->>", "" + params.toString());
//            AsyncHttpClient client = AsyncHttpRequest.newRequest(getActivity());
//            client.put(URLs.APPROVE_CLINICIAN() + id + "/approve", params, new GetVersionData1(getActivity()));

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
            body.add("params[consent_signature]", encodedSignature);

            Call call = HttpClient.newRequestPut(getActivity(), body.build(), URLs.APPROVE_CLINICIAN() + id + "/approve");
            call.enqueue(new GetVersionData1(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersionData1 extends AsyncResponseHandlerOk {

        public GetVersionData1(Activity context) {
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
                Debug.e("", "approveClinicData# " + response);
                if (response != null && response.length() > 0) {

                    getCliniciansData();
//                    MessageReadRes res = new Gson().fromJson(response, new TypeToken<MessageReadRes>() {
//                    }.getType());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
            Debug.e("", "onFailure# " + content);
            dismissDialog();
        }
    }

    public void unReadMessageData(int id) {
        try {
            showDialog("");
//            RequestParams params = RequestParamsUtils.newRequestParams(getActivity());
//            Debug.e("unReadMessageData-->>", "" + params.toString());
//            AsyncHttpClient client = AsyncHttpRequest.newRequest(getActivity());
//            client.patch(URLs.UNREAD_MESSAGE() + id + "/mark_as_unread", params, new GetVersionDataHandler2(getActivity()));

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
            Call call = HttpClient.newRequestPatch(getActivity(), body.build(), URLs.UNREAD_MESSAGE() + id + "/mark_as_unread");
            call.enqueue(new GetVersionDataHandler2(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersionDataHandler2 extends AsyncResponseHandlerOk {

        public GetVersionDataHandler2(Activity context) {
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
                Debug.e("", "unReadMessageData# " + response);
                if (response != null && response.length() > 0) {
//
//                    MessageReadRes res = new Gson().fromJson(response, new TypeToken<MessageReadRes>() {
//                    }.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getMessagesData();
                            msgDialog.dismiss();
                        }
                    });


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
            Debug.e("", "unReadMessageData onFailure# " + content);
            dismissDialog();
        }
    }


    public void setEditObserverData(int selectedID, int meds) {
        try {
            showDialog("");
//            RequestParams params = RequestParamsUtils.newRequestParams(getActivity());
//            Debug.e("setEditObserverData-->>", "" + params.toString());
//            AsyncHttpClient client = AsyncHttpRequest.newRequest(getActivity());
//            client.patch(URLs.EDIT_OBSERVERS() + selectedID, params, new GetVersionDataHandler(getActivity()));

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());

            if (meds == 0) {
                body.addEncoded(RequestParamsUtils.OBS_MEDS, "1");
            } else {
                body.addEncoded(RequestParamsUtils.OBS_MEDS, "0");
            }
            Call call = HttpClient.newRequestPatch(getActivity(), body.build(), URLs.EDIT_OBSERVERS() + selectedID);
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
                Debug.e("", "setEditObserverData# " + response);
                if (response != null && response.length() > 0) {

                    mDialog.dismiss();
//                    Response res = new Gson().fromJson(response, new TypeToken<Response>() {
//                    }.getType());
//                    if (res.status.equalsIgnoreCase("not_found")) {
//                        showToast("No Member found with Email", Toast.LENGTH_LONG);
//                    } else {
//                        showToast("Success", Toast.LENGTH_LONG);
//                        dialog.dismiss();
//                    }
//                    dialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
            Debug.e("", "onFailure# " + content);
            dismissDialog();
        }
    }

    public void addObserverData() {
        try {
            showDialog("");

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
            body.addEncoded(RequestParamsUtils.OBS_EMAIL, editEmail.getText().toString().trim());
            body.addEncoded(RequestParamsUtils.RELATIONSHIP, editRelationship.getText().toString().trim());

            if (cbGuardian.isChecked()) {
                body.addEncoded(RequestParamsUtils.MEDS, "1");
                Debug.e("cbGuardian", "1");
            } else {
                body.addEncoded(RequestParamsUtils.MEDS, "2");
                Debug.e("cbGuardian", "2");
            }
            Call call = HttpClient.newRequestPost(getActivity(), body.build(), URLs.ADD_OBSERVERS());
            call.enqueue(new GetVersionDataHandl(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersionDataHandl extends AsyncResponseHandlerOk {

        public GetVersionDataHandl(Activity context) {
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
                Debug.e("", "addObserverData# " + response);
                dialog.dismiss();
                if (response != null && response.length() > 0) {

                    Response res = new Gson().fromJson(response, new TypeToken<Response>() {
                    }.getType());

                    if (res.status != null && res.status.equalsIgnoreCase("not_found")) {
                        showToast("No Member found with Email", Toast.LENGTH_LONG);
                    } else {
                        showToast("Success", Toast.LENGTH_LONG);
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(intent);
                        getObserversData();
                        dialog.dismiss();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
            Debug.e("", "onFailure# " + content);
            dismissDialog();
        }
    }

    public void getObserversData() {
        try {
            showDialog("");

            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_OBSERVERS());
            Call call = HttpClient.newRequestGet(getActivity(), body.build().toString());
            call.enqueue(new GetVersionData(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersionData extends AsyncResponseHandlerOk {

        public GetVersionData(Activity context) {
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
        public void onSuccess(final String response) {

            try {
                Debug.e("", "getObserversData# " + response);
                if (response != null && response.length() > 0) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            JSONObject issueObj = null;
                            try {
                                issueObj = new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Iterator iterator = issueObj.keys();
                            JSONArray array = new JSONArray();

                            while (iterator.hasNext()) {
                                String key = (String) iterator.next();
                                JSONObject object = null;

                                try {
                                    object = issueObj.getJSONObject(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    object.put("name", key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                array.put(object);
                            }

                            mObserversListAdapter.clear();
                            for (int i = 0; i < array.length(); i++) {
                                try {
                                    mObserversListAdapter.add(array.getJSONObject(i));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

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

    public void getObsData(String n) {
        try {
//            showEditDialog("");
//            RequestParams params = RequestParamsUtils.newRequestParams(getActivity());
//            Debug.e("getObsData-->>", "" + params.toString());
//            AsyncHttpClient client = AsyncHttpRequest.newRequest(getActivity());
//            client.get(URLs.GET_OBSERVERS(), params, new GetVersionDataler(getActivity()));

            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_OBSERVERS());
            Call call = HttpClient.newRequestGet(getActivity(), body.build().toString());
            call.enqueue(new GetVersionDataHan(getActivity(), n));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersionDataHan extends AsyncResponseHandlerOk {

        String n;

        public GetVersionDataHan(Activity context, String n) {
            super(context);
            this.n = n;
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
        public void onSuccess(final String response) {

            try {
                Debug.e("", "getObsData# " + response);
                if (response != null && response.length() > 0) {

//                    PriscriptionRes res = new Gson().fromJson(response, new TypeToken<PriscriptionRes>() {
//                    }.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            JSONObject issueObj = null;
                            try {
                                issueObj = new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Iterator iterator = issueObj.keys();
                            JSONArray array = new JSONArray();

                            while (iterator.hasNext()) {
                                String key = (String) iterator.next();
                                JSONObject object = null;
                                try {
                                    object = issueObj.getJSONObject(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    object.put("name", key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                array.put(object);
                            }
                            for (int i = 0; i < array.length(); i++) {
                                try {
                                    if (n.equalsIgnoreCase(array.getJSONObject(i).getString("name")))
                                        mEditObsAdapter.add(array.getJSONObject(i));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
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

    public void getCliniciansData() {
        try {
//            showEditDialog("");
//            RequestParams params = RequestParamsUtils.newRequestParams(getActivity());
//            Debug.e("getCliniciansData-->>", "" + params.toString());
//            AsyncHttpClient client = AsyncHttpRequest.newRequest(getActivity());
//            client.get(URLs.GET_CLINICIANS(), params, new GetVersionDataHand(getActivity()));

            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_CLINICIANS());
            Call call = HttpClient.newRequestGet(getActivity(), body.build().toString());
            call.enqueue(new GetVersionDataHand(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersionDataHand extends AsyncResponseHandlerOk {

        public GetVersionDataHand(Activity context) {
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
//                dismissDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSuccess(String response) {

            try {
                Debug.e("", "getCliniciansData# " + response);
                if (response != null && response.length() > 0) {

                    final CliniciansRes res = new Gson().fromJson(response, new TypeToken<CliniciansRes>() {
                    }.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCliniciansListAdapter.addAll(res.memberships);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
//            dismissDialog();
        }
    }


    ArrayList<MessagesRes.Message> myRes = new ArrayList<>();

    public MessagesRes res;

    public void getMessagesData() {
        count = 0;
        try {
//            showEditDialog("");
//            RequestParams params = RequestParamsUtils.newRequestParams(getActivity());
//            Debug.e("getMessagesData-->>", "" + params.toString());
//            AsyncHttpClient client = AsyncHttpRequest.newRequest(getActivity());
//            client.get(URLs.GET_MESSAGES(), params, new GetVersionDataHandls(getActivity()));

            HttpUrl.Builder body = RequestParamsUtils.newRequestUrlBuilder(getActivity(), URLs.GET_MESSAGES());
            Call call = HttpClient.newRequestGet(getActivity(), body.build().toString());
            call.enqueue(new GetVersionDataHandls(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVersionDataHandls extends AsyncResponseHandlerOk {

        public GetVersionDataHandls(Activity context) {
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
//                dismissDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSuccess(String response) {

            try {
                Debug.e("", "getMessagesData# " + response);
                if (response != null && response.length() > 0) {

                    res = new Gson().fromJson(response, new TypeToken<MessagesRes>() {
                    }.getType());

//                     myRes = new Gson().fromJson(response, new TypeToken<MessagesRes>() {
//                    }.getType());

                    try {
                        myRes.clear();
                        myRes.addAll(res.messages);
                        Collections.sort(myRes, new Comparator<MessagesRes.Message>() {
                            public int compare(MessagesRes.Message s1, MessagesRes.Message s2) {
                                return (Utils.parseTime(s2.createdAt, Constant.DATE_FORMAT_SERVER)).compareTo(Utils.parseTime(s1.createdAt, Constant.DATE_FORMAT_SERVER));
                            }

                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Debug.e("size", "" + myRes.size());

                                if (myRes.size() == 0) {
                                    llMyMsgDetail.setVisibility(View.GONE);
                                    tvErrorMsg.setVisibility(View.VISIBLE);

                                } else {
                                    llMyMsgDetail.setVisibility(View.VISIBLE);
                                    myMsgSwipeAdapter.addAll(getPage(pageNumber));

                                    new Thread() {
                                        @Override
                                        public void run() {
                                            super.run();
                                            Looper.prepare();
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            myMsgSwipeAdapter.notifyDataSetChanged();
                                                        }
                                                    });
                                                }
                                            }, 1000);
                                            Looper.loop();
                                        }
                                    }.start();
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                            Debug.e("count", "" + count);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            refreshPlaceHolder();
        }

        @Override
        public void onFailure(Throwable e, String content) {
//            dismissDialog();
        }
    }

    public void refreshPlaceHolder() {
        if (recyclerView.getAdapter().getItemCount() > 0) {
            llPlaceHolder.setVisibility(View.GONE);
        } else {
            llPlaceHolder.setVisibility(View.VISIBLE);
        }

    }

}
