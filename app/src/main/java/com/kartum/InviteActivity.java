package com.kartum;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kartum.adapter.EmailListAdapter;
import com.kartum.objects.EmailData;
import com.kartum.utils.Debug;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.Utils;
import com.sendgrid.SendGrid;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InviteActivity extends BaseActivity {

    //    @BindView(R.id.editEmail)
//    EditText editEmail;
    @BindView(R.id.tvAddEmail)
    TextView tvAddEmail;
    @BindView(R.id.btnInvites)
    Button btnInvites;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    public EmailListAdapter mAdapter;

    private static final String SENDGRID_USERNAME = "jroberto@neptunescloud.com";
    private static final String SENDGRID_PASSWORD = "P@55word";
    private static final int ADD_ATTACHMENT = 1;

    private Uri selectedImageURI;
    private String attachmentName;

//    ArrayList<EmailData> data = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        ButterKnife.bind(this);

        initDrawer(true);
        init();
    }

    private void init() {

        initFillData();
        tvAddEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    mAdapter.add();
                }

            }
        });
        mAdapter.add();

        btnInvites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//                sendIntent.setType("text/plain");
//                startActivity(sendIntent);

                Intent textShareIntent = new Intent(Intent.ACTION_SEND);
                textShareIntent.putExtra(Intent.EXTRA_TEXT, "Checkout Faro10 - download now");
                textShareIntent.putExtra(Intent.EXTRA_SUBJECT, "Faro10 Invitation");
                textShareIntent.setType("text/plain");

                if (textShareIntent.resolveActivity(getPackageManager()) != null)
                    startActivity(Intent.createChooser(textShareIntent, "Share"));

            }
        });

//        btnInvites.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (validate()) {
//                    List<EmailData> data = mAdapter.getAll();
//
//                    String[] emailArray = new String[data.size()];
//
//                    for (int i = 0; i < data.size(); i++) {
//                        emailArray[i] = data.get(i).mEmail;
//                    }
//
//                    String str = new Gson().toJson(data);
//                    Debug.e("mail", "" + emailArray);
//                    Debug.e("user Email", "" + Utils.getPref(getActivity(), RequestParamsUtils.EMAIL, ""));
//
//                    String htmlString = "<div class=\"adn ads\"> <div class=\"gs\"><div id=':o5' class='ii gt adP adO'> <div id=':o6' class='a3s aXjCH m156d9b3648d7a502'><div><p>Hello,</p><p>You have been invited by " + Utils.getPref(getActivity(), RequestParamsUtils.EMAIL, "") + " to assist in their treatment. As a Clinician within Faro10, you will have access to health data and realtime insights into their well-being.<br /><br />Some of the capabilities include:</p><ul><li>Patient List with quick status of health in real-time</li>\n" +
//                            "                  <li>Dynamic Charts, Trends and Graphs of Patient wellness in real-time</li>\n" +
//                            "                  <li>Real-time status of Patient symptoms and adverse events</li>\n" +
//                            "                  <li>Observer Journals and notes for each Patient</li>\n" +
//                            "                  <li>Track Patient prescriptions and medication success</li>\n" +
//                            "                  <li>Digitally share Patient notes with other Clinicians</li>\n" +
//                            "                  <li>1-way Message Center for quick messages to Patients</li>\n" +
//                            "                  <li>Digitally signed consent and release forms</li>\n" +
//                            "                  <li>Evidence-based Clinic success metrics</li>\n" +
//                            "                  </ul>\n" +
//                            "                  <p>Once you have created an account, you can log in and choose the 'Add Patient' option and use <a href='mailto:" + Utils.getPref(getActivity(), RequestParamsUtils.EMAIL, "") + "' target='_blank'>" + Utils.getPref(getActivity(), RequestParamsUtils.EMAIL, "") + "</a> to add " + Utils.getPref(getActivity(), RequestParamsUtils.EMAIL, "") + " to your list of patients.</p>\n" +
//                            "                  <p>Follow this link to our registration page.</p>\n" +
//                            "                  <a href='https://shrouded-hamlet-2906.herokuapp.com/Register Clinician' target='_blank' data-saferedirecturl='https://shrouded-hamlet-2906.herokuapp.com/Register Clinician'>Register</a><p>Thank you for your help!</p><h1>Faro10</h1>\n" +
//                            "                  <div class='yj6qo'>&nbsp;</div>\n" +
//                            "                  </div>\n" +
//                            "                  </div>\n" +
//                            "                  </div>\n" +
//                            "                  <div class='hi'>&nbsp;</div>\n" +
//                            "                  </div>\n" +
//                            "                  <div class='ajx'>&nbsp;</div>\n" +
//                            "                  </div>\n" +
//                            "                  <div class='gA gt acV'>&nbsp;</div>\n" +
//                            "                  <p>&nbsp;</p>";
//
//                    SendEmailASyncTask task = new SendEmailASyncTask(InviteActivity.this,
//                            "dimple.techark@gmail.com", emailArray, "noreply@faro10.com", "You have received an invitation from one of your Patients to join Faro10", "",
//                            selectedImageURI,
//                            htmlString);
//                    task.execute();
//
////                    SendEmailASyncTask tasks = new SendEmailASyncTask()
//                }
//            }
//        });
    }

    private void initFillData() {

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new EmailListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
//        data = new ArrayList<>();
    }

    private boolean validate() {
        List<EmailData> data = mAdapter.getAll();
        Debug.e("array", new Gson().toJson(data));

        for (int i = 0; i < data.size(); i++) {

            if (data.get(i).mEmail == null || data.get(i).mEmail.isEmpty()) {
                showToast(getString(R.string.err_email), Toast.LENGTH_SHORT);
                return false;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(data.get(i).mEmail).matches()) {
                showToast(getString(R.string.err_email_invalid), Toast.LENGTH_SHORT);
                return false;
            }

        }
//        for (EmailData a : mAdapter.getAll()) {
//            if (StringUtils.isEmpty(a.mEmail)) {
//                showToast(getString(R.string.err_email), Toast.LENGTH_SHORT);
//                return false;
//            }
//
//        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_ATTACHMENT) {
            if (resultCode == RESULT_OK) {
                // Get image from result intent
                selectedImageURI = data.getData();
                ContentResolver contentResolver = getContentResolver();
                Debug.d("SendAppExample", "Image Uri: " + selectedImageURI);

                // Get image attachment filename
                attachmentName = "";
                Cursor c = contentResolver.query(selectedImageURI, null, null, null, null);
                if (c != null && c.moveToFirst()) {
                    attachmentName = c.getString(c.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                }
            }
        }
    }

    public class SendEmailASyncTask extends AsyncTask<Void, Void, Void> {

        private Context mAppContext;
        private String mMsgResponse;
        private String mTo;
        private String[] mBcc;

        private String mFrom;
        private String mSubject;
        private String mText;
        private Uri mUri;
        private String mAttachmentName;
        private String html;

        public SendEmailASyncTask(Context context, String mTo, String[] mBcc, String mFrom, String mSubject,
                                  String mText, Uri mUri, String html) {
            this.mAppContext = context.getApplicationContext();
            this.mTo = mTo;
            this.mBcc = mBcc;
            this.mFrom = mFrom;
            this.mSubject = mSubject;
            this.mText = mText;
//            this.mUri = mUri;
            this.html = html;
        }

        protected Void doInBackground(Void... params) {

            try {
                SendGrid sendgrid = new SendGrid(SENDGRID_USERNAME, SENDGRID_PASSWORD);
                SendGrid.Email email = new SendGrid.Email();
                // Get values from edit text to compose email
                // TODO: Validate edit texts
                email.addTo(mTo);
                email.setBcc(mBcc);
                email.setFrom(mFrom);
                email.setSubject(mSubject);
                email.setText(mText);
                email.setHtml(html);

//                 Attach image
//            if (mUri != null) {
//                email.addAttachment(mAttachmentName, mAppContext.getContentResolver().openInputStream(mUri));
//            }
                // Send email, execute http request
                SendGrid.Response response = sendgrid.send(email);
                mMsgResponse = response.getMessage();
                Debug.d("SendAppExample", mMsgResponse);

            } catch (Exception e) {
                e.printStackTrace();
                Debug.e("SendAppExample", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            Toast.makeText(mAppContext, mMsgResponse, Toast.LENGTH_SHORT).show();
            showToast("Success", Toast.LENGTH_LONG);
            init();
//            editEmail.setText("");
        }
    }
}
