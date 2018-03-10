package com.kartum;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kartum.MyTeamActivity.APPROVE_CLINICIAN_REQUEST;

public class ApproveClinicianActivity extends BaseActivity {

    @BindView(R.id.lblClinicianName)
    TextView lblClinicianName;

    @BindView(R.id.lblClinicName)
    TextView lblClinicName;

    @BindView(R.id.btnReset)
    Button btnResetSignature;

    @BindView(R.id.signature_pad)
    SignaturePad signaturePad;

    @BindView(R.id.btnCancelApproveClinician)
    Button btnCancelApproveClinician;

    @BindView(R.id.btnConfirmApproveClinician)
    Button btnConfirmApproveClinician;

    private Integer clinicianID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_clinician);
        ButterKnife.bind(this);

        btnResetSignature.setOnClickListener(resetSignature);
        btnCancelApproveClinician.setOnClickListener(cancel);
        btnConfirmApproveClinician.setOnClickListener(approve);

        // load clinician data
        Intent intent = getIntent();
        String clinicianName = intent.getStringExtra("Name");
        String clinicName = intent.getStringExtra("Clinic_Name");
        clinicianID = intent.getIntExtra("ID", 0);

        lblClinicianName.setText(clinicianName);
        lblClinicName.setText(clinicName);
    }

    OnClickListener resetSignature = new OnClickListener() {
        @Override
        public void onClick(View v) {
            signaturePad.clear();
        }
    };

    OnClickListener cancel = new OnClickListener() {
        @Override
        public void onClick(View v) {
            setResult(RESULT_CANCELED);
            finish();
        }
    };

    OnClickListener approve = new OnClickListener() {
        @Override
        public void onClick(View v) {

            if (signaturePad.isEmpty()) {
                showToast(getString(R.string.err_signature), Toast.LENGTH_SHORT);
                return;
            }

            // convert signature
            Bitmap signature = signaturePad.getSignatureBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            signature.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            String encoded = "data:image/png;base64," + Base64.encodeToString(byteArray, Base64.DEFAULT);

            Intent data = new Intent();
            data.putExtra("ID", clinicianID);
            data.putExtra("Signature", encoded);

            setResult(RESULT_OK, data);
            finish();
        }
    };


}
