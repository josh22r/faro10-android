package com.kartum;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kartum.objects.RegisterRes;
import com.kartum.utils.AsyncResponseHandlerOk;
import com.kartum.utils.Debug;
import com.kartum.utils.HttpClient;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.URLs;
import com.kartum.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.btnRegister)
    Button btnRegister;

    @BindView(R.id.editUserName)
    EditText editUserName;
    @BindView(R.id.editPassword)
    EditText editPassword;
    @BindView(R.id.editRegEmail)
    EditText editRegEmail;
    @BindView(R.id.tvCancel)
    TextView tvCancel;
    @BindView(R.id.tvLegalDisclaime)
    TextView tvLegalDisclaime;

    @BindView(R.id.cbMale)
    CheckBox cbMale;
    @BindView(R.id.cbFemale)
    CheckBox cbFemale;
    @BindView(R.id.cbIHaveRead)
    CheckBox cbIHaveRead;
    @BindView(R.id.cbPaidTrial)
    CheckBox cbPaidTrial;

    @BindView(R.id.lblCharacterLength)
    TextView lblCharacterLength;
    @BindView(R.id.lblLowercase)
    TextView lblLowercase;
    @BindView(R.id.lblUppercase)
    TextView lblUppercase;
    @BindView(R.id.lblNumber)
    TextView lblNumber;
    @BindView(R.id.lblSymbol)
    TextView lblSymbol;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        init();
    }

    private void init() {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate()) {
                    registerData();
                }
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cbMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cbFemale.setChecked(false);
            }
        });
        cbFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cbMale.setChecked(false);
            }
        });
        cbIHaveRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tvLegalDisclaime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TermsnCondActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        editPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateRequirementLabels(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK) {
            String getString = data.getStringExtra("msg");
            if (getString.equalsIgnoreCase("1")) {
                cbIHaveRead.setChecked(true);
            }
        }
    }

    private void updateRequirementLabels(String passwordText) {

        if (passwordText.length() >= 8 && passwordText.length() <= 15) {
            lblCharacterLength.setTextColor(Color.parseColor("#4FA59F"));
        } else {
            lblCharacterLength.setTextColor(Color.GRAY);
        }

        if (passwordText.matches(".*\\d.*")) {
            lblNumber.setTextColor(Color.parseColor("#4FA59F"));
        } else {
            lblNumber.setTextColor(Color.GRAY);
        }

        if (passwordText.matches(".*[a-z].*")) {
            lblLowercase.setTextColor(Color.parseColor("#4FA59F"));
        } else {
            lblLowercase.setTextColor(Color.GRAY);
        }

        if (passwordText.matches(".*[A-Z].*")) {
            lblUppercase.setTextColor(Color.parseColor("#4FA59F"));
        } else {
            lblUppercase.setTextColor(Color.GRAY);
        }

        if (passwordText.matches(".*[!@#$%^&*+=?-].*")) {
            lblSymbol.setTextColor(Color.parseColor("#4FA59F"));
        } else {
            lblSymbol.setTextColor(Color.GRAY);
        }
    }

    private boolean validate() {
        if (editUserName.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_firstname), Toast.LENGTH_SHORT);
            return false;
        }

        if (editUserName.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_lastname), Toast.LENGTH_SHORT);
            return false;
        }

        if (editPassword.getText().toString().length() <= 0) {
            showToast(getString(R.string.err_password), Toast.LENGTH_SHORT);
            return false;
        }
//        if (editPassword.getText().toString().length() <= 7 && !Utils.passwordValidator(editPassword.getText().toString())) {
//            Utils.showDialog(getActivity(), "Invalid Password", getString(R.string.invalid_password));
//            return false;
//        }

        if (editPassword.getText().toString().length() < 8 || editPassword.getText().toString().length() >= 15) {
            Utils.showDialog(getActivity(), "Invalid Password", getString(R.string.invalid_password));
            return false;
        }

        if (!editPassword.getText().toString().matches(".*\\d.*")) {
            Utils.showDialog(getActivity(), "Invalid Password", getString(R.string.invalid_password));
            return false;
        }

        if (!editPassword.getText().toString().matches(".*[a-z].*")) {
            Utils.showDialog(getActivity(), "Invalid Password", getString(R.string.invalid_password));
            return false;
        }
        if (!editPassword.getText().toString().matches(".*[A-Z].*")) {
            Utils.showDialog(getActivity(), "Invalid Password", getString(R.string.invalid_password));
            return false;
        }
        if (!editPassword.getText().toString().matches(".*[!@#$%^&*+=?-].*")) {
            Utils.showDialog(getActivity(), "Invalid Password", getString(R.string.invalid_password));
            return false;
        }

        if (editRegEmail.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_email), Toast.LENGTH_SHORT);
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(editRegEmail.getText()).matches()) {
            showToast(getString(R.string.err_email_invalid), Toast.LENGTH_SHORT);
            return false;
        }

        if (cbMale.isChecked() == false && cbFemale.isChecked() == false) {
            showToast(getString(R.string.err_gender), Toast.LENGTH_SHORT);
            return false;
        }

        if (cbIHaveRead.isChecked() == false) {
            showToast(getString(R.string.err_disclaimer), Toast.LENGTH_SHORT);
            return false;
        }

        return true;
    }

    public void registerData() {
        try {
            showDialog("");

//            RequestParams params = new RequestParams();
//            params.put(RequestParamsUtils.USERNAME, userName.trim());
//            params.put(RequestParamsUtils.USER_EMAIL, editRegEmail.getText().toString().trim());
//            params.put(RequestParamsUtils.USER_PASSWORD, editPassword.getText().toString().trim());
//
//
//            Debug.e("registerData", "" + params.toString());
//            AsyncHttpClient client = AsyncHttpRequest.newRequest(getActivity());
//            client.post(URLs.REGISTER(), params, new GetVersionDataHandler(getActivity()));

            String strUser = editRegEmail.getText().toString();
            String result = strUser.substring(0, strUser.indexOf("@"));
            String userName = result.replace(".", "_");

            FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
            body.addEncoded(RequestParamsUtils.USERNAME, userName.trim());
            body.add(RequestParamsUtils.USER_EMAIL, editRegEmail.getText().toString().trim());
            body.addEncoded(RequestParamsUtils.USER_PASSWORD, editPassword.getText().toString().trim());
            body.addEncoded(RequestParamsUtils.PAID_TRIAL, cbPaidTrial.isChecked() ? "1" : "0");

            if (cbMale.isChecked()) {
                body.addEncoded(RequestParamsUtils.GENDER, "Male");
            } else {
                body.addEncoded(RequestParamsUtils.GENDER, "Female");
            }
            Debug.e("registerData", "" + body.toString());
            Call call = HttpClient.newRequestPost(getActivity(), body.build(), URLs.REGISTER());
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
                Debug.e("", "registerData# " + response);
                if (response != null && response.length() > 0) {

                    final RegisterRes res = new Gson().fromJson(response, new TypeToken<RegisterRes>() {
                    }.getType());

                    if (res.errors != null && !res.success) {

                        showToast("Email " + res.errors.email.get(0), Toast.LENGTH_LONG);
                        showToast("" + res.errors.password.get(0), Toast.LENGTH_LONG);

                    } else if (res.success == true) {

                        showToast("Please check your email to activate your account.", Toast.LENGTH_LONG);
                        finish();
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


}
