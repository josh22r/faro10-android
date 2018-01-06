package com.kartum;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kartum.utils.Debug;
import com.kartum.utils.RequestParamsUtils;
import com.kartum.utils.Utils;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.editFeedback)
    EditText editFeedback;
    @BindView(R.id.btnFeedback)
    Button btnFeedback;

    private static final String SENDGRID_USERNAME = "jroberto@neptunescloud.com";
    private static final String SENDGRID_PASSWORD = "P@55word";
    private static final int ADD_ATTACHMENT = 1;

    private Uri selectedImageURI;
    private String attachmentName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);

        initDrawer(true);
        init();
    }

    private void init() {

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trimFront(editFeedback.getText().toString());
                if (validate()) ;

                String input = editFeedback.getText().toString().trim();
//                input = input.replace("  ", " ");
                editFeedback.setText(input);

                if (input.length() > 0) {
                    SendEmailASyncTask task = new SendEmailASyncTask(FeedbackActivity.this, "JRoberto@apptio.com",
                            Utils.getPref(getActivity(), RequestParamsUtils.EMAIL, ""), "faro10 feedback",
                            editFeedback.getText().toString(),
                            selectedImageURI,
                            attachmentName);
                    task.execute();
                } else {
                    showToast("Please enter feedback!", Toast.LENGTH_LONG);
                }
            }
        });

    }

    public static String trimFront(String input) {
        if (input == null) return input;
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isWhitespace(input.charAt(i)))
                return input.substring(i);
        }
        return "";
    }

    private boolean validate() {

        if (editFeedback.getText().toString().trim().length() <= 0) {
            showToast("Please enter feedback!", Toast.LENGTH_LONG);
            return false;
        }
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
        private String mFrom;
        private String mSubject;
        private String mText;
        private Uri mUri;
        private String mAttachmentName;

        public SendEmailASyncTask(Context context, String mTo, String mFrom, String mSubject,
                                  String mText, Uri mUri, String mAttachmentName) {
            this.mAppContext = context.getApplicationContext();
            this.mTo = mTo;
            this.mFrom = mFrom;
            this.mSubject = mSubject;
            this.mText = mText;
            this.mUri = mUri;
            this.mAttachmentName = mAttachmentName;
        }

        protected Void doInBackground(Void... params) {

            try {
                SendGrid sendgrid = new SendGrid(SENDGRID_USERNAME, SENDGRID_PASSWORD);
                SendGrid.Email email = new SendGrid.Email();
                // Get values from edit text to compose email
                // TODO: Validate edit texts
                email.addTo(mTo);
                email.setFrom(mFrom);
                email.setSubject(mSubject);
                email.setText(mText);

                // Attach image
//            if (mUri != null) {
//                email.addAttachment(mAttachmentName, mAppContext.getContentResolver().openInputStream(mUri));
//            }
                // Send email, execute http request
                SendGrid.Response response = sendgrid.send(email);
                mMsgResponse = response.getMessage();
                Debug.d("SendAppExample", mMsgResponse);

            } catch (SendGridException e) {
                Debug.e("SendAppExample", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            Toast.makeText(mAppContext, mMsgResponse, Toast.LENGTH_SHORT).show();
            showToast("Success", Toast.LENGTH_LONG);
            editFeedback.setText("");
        }
    }
}