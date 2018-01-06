package com.kartum;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kartum.objects.LoginRes;
import com.kartum.utils.AsyncResponseHandlerOk;
import com.kartum.utils.Constant;
import com.kartum.utils.Debug;
import com.kartum.utils.HttpClient;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.URLs;
import com.kartum.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;

public class LoginActivity extends BaseActivity {

    Handler handler = new Handler();
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.editLoginEmail)
    EditText editLoginEmail;
    @BindView(R.id.editLoginPass)
    EditText editLoginPass;

    @BindView(R.id.tvRegister)
    TextView tvRegister;
    @BindView(R.id.tvForgotPass)
    TextView tvForgotPass;
    @BindView(R.id.chkRememberMe)
    CheckBox chkRememberMe;
    EditText editForgotEmail;
    boolean isRider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        initImageLoader();

//        if (Debug.DEBUG) {
//            editLoginEmail.setText("sunil.techark@gmail.com");
//            editLoginPass.setText("St@123456");
//        }
        if (Debug.DEBUG) {
            editLoginEmail.setText("nilesh.mer@kartuminfotech.com");
            editLoginPass.setText("nilesh@123");
        }

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), RegisterActivity.class);
                startActivity(i);
            }
        });

        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(getActivity(), ForgetPasswordActivity.class);
//                startActivity(i);
                showDialog();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    login();
                }
            }
        });

        chkRememberMe.setChecked(Utils.getPref(getActivity(), Constant.REMEMBER_ME, false));
        Utils.setPref(getActivity(), Constant.REMEMBER_ME, chkRememberMe.isChecked());

        if (chkRememberMe.isChecked()) {
            editLoginEmail.setText(Utils.getPref(getActivity(), Constant.USER_EMAIL, ""));
            editLoginPass.setText(Utils.getPref(getActivity(), Constant.USER_PASS, ""));
        }
    }


    private void initCheckBox() {
        Utils.setPref(getActivity(), Constant.REMEMBER_ME, chkRememberMe.isChecked());

        if (chkRememberMe.isChecked()) {
            Utils.setPref(getActivity(), Constant.USER_EMAIL, editLoginEmail.getText().toString().trim());
            Utils.setPref(getActivity(), Constant.USER_PASS, editLoginPass.getText().toString().trim());
        } else {
            Utils.delPref(getActivity(), Constant.USER_EMAIL);
            Utils.delPref(getActivity(), Constant.USER_PASS);
        }
    }

    ImageLoader imageLoader;

    private void initImageLoader() {
        try {
            imageLoader = Utils.initImageLoader(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validate() {
        if (editLoginEmail.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_email), Toast.LENGTH_SHORT);
//            emailErrorPopup();
            return false;
        }

        if (editLoginEmail.getText().toString().startsWith(" ") || editLoginEmail.getText().toString().endsWith(" ")) {
            showToast(getString(R.string.err_valid_email), Toast.LENGTH_SHORT);
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(editLoginEmail.getText().toString().trim()).matches()) {
            showToast(getString(R.string.err_email_invalid), Toast.LENGTH_SHORT);
//            invalidEmailErrorPopup();
            return false;
        }

        if (editLoginPass.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_password), Toast.LENGTH_SHORT);
//            invalidPassPopup();
            return false;
        }

        return true;
    }

    Dialog dialog1;

    public void emailErrorPopup() {
        dialog1 = new Dialog(getActivity());
        dialog1.setCanceledOnTouchOutside(true);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.popup_msg_enter_email);

        TextView btnOk = (TextView) dialog1.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }

    public void invalidEmailErrorPopup() {
        dialog1 = new Dialog(getActivity());
        dialog1.setCanceledOnTouchOutside(true);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.popup_invalid_email);

        TextView btnOk = (TextView) dialog1.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }

    public void invalidPassPopup() {
        dialog1 = new Dialog(getActivity());
        dialog1.setCanceledOnTouchOutside(true);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.popup_invalid_email);

        TextView btnOk = (TextView) dialog1.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }

    public void login() {
        try {
            showDialog("");
            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
            body.addEncoded(RequestParamsUtils.EMAIL, editLoginEmail.getText().toString());
            body.addEncoded(RequestParamsUtils.PASSWORD, editLoginPass.getText().toString());

            Call call = HttpClient.newRequestPost(getActivity(), body.build(), URLs.LOGIN());
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
                Debug.e("", "login# " + response);
                if (response != null && response.length() > 0) {

                    LoginRes res = new Gson().fromJson(response,
                            new TypeToken<LoginRes>() {
                            }.getType());

                    if (res.success) {
                        Utils.setPref(getActivity(), RequestParamsUtils.TOKEN, res.token);
                        Utils.setPref(getActivity(), RequestParamsUtils.EMAIL, editLoginEmail.getText().toString().trim());

                        initCheckBox();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        showToast(res.message, Toast.LENGTH_SHORT);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable e, String content) {
            dismissDialog();
            Debug.e("", "onFailure# " + content);
            if (content != null && content.length() > 0) {

                LoginRes res = new Gson().fromJson(content,
                        new TypeToken<LoginRes>() {
                        }.getType());

                showToast(res.message, Toast.LENGTH_SHORT);

            }
        }
    }

    Dialog dialog;

    public void showDialog() {

        dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_forgot_password);

        TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        TextView tvSend = (TextView) dialog.findViewById(R.id.tvSend);
        editForgotEmail = (EditText) dialog.findViewById(R.id.editForgotEmail);


        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailValidate()) {
                    sendRequestForgotPassword();
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

    public void sendRequestForgotPassword() {
        try {
            showDialog("");

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
            body.addEncoded(RequestParamsUtils.FORGOT_PASSWORD, editForgotEmail.getText().toString());
            Call call = HttpClient.newRequestPost(getActivity(), body.build(), URLs.FORGETPIN());
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
                Debug.e("", "sendRequestForgotPassword# " + response);

                LoginRes res = new Gson().fromJson(response, new TypeToken<LoginRes>() {
                }.getType());

                if (res.success) {
                    dialog.dismiss();
                    showToast(res.message, Toast.LENGTH_SHORT);
                } else {
                    showToast(res.message, Toast.LENGTH_SHORT);
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

    private boolean emailValidate() {

        if (editForgotEmail.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_email), Toast.LENGTH_SHORT);
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(editForgotEmail.getText()).matches()) {
            showToast(getString(R.string.err_email_invalid), Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }
}
