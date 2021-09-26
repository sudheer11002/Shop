package com.thecoderscrew.shop.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thecoderscrew.shop.Classes.ReadSMS;
import com.thecoderscrew.shop.Interface.SmsListener;
import com.thecoderscrew.shop.R;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    PinView OTP_pin;
    String verificationId;
    String mnumber;
    TextView tvOtpno,tvResend,tvTimer;
    ImageView checkOtpImg;
    ProgressDialog progressDialog;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        mnumber = getIntent().getStringExtra("mobile_number");

        checkOtpImg = findViewById(R.id.check_otp_img);
        tvResend = findViewById(R.id.tv_resend);
        tvTimer= findViewById(R.id.tv_timer);
        tvOtpno = findViewById(R.id.tv_otpno);

        tvOtpno.setText("Enter 6 digit code that send to " + mnumber);

        OTP_pin = findViewById(R.id.otp_pin);

        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String code = Objects.requireNonNull(OTP_pin.getText()).toString();
                if (code.isEmpty()) {
                } else {
                    progressDialog.show();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SendOtp(mnumber);

                Toast.makeText(getApplicationContext(), "OTP send again.", Toast.LENGTH_LONG).show();

                tvTimer.setVisibility(View.VISIBLE);
                countDownTimer.start();
                tvResend.setVisibility(View.GONE);
            }
        });

        SendOtp(mnumber);

        ReadSMS.bindListener(new SmsListener() {

            @Override
            public void messageReceived(String messageText) {

                if ((messageText.length() == 33) && (messageText.contains("is your verification code."))) {
                    OTP_pin.setText(messageText.substring(0, 6));
                    checkOtpImg.setVisibility(View.VISIBLE);
                }
            }
        });

        countDownTimer= new CountDownTimer(59000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTimer.setText("Seconds Remaining : "+ millisUntilFinished / 1000);
            }

            public void onFinish()
            {
                OTP_pin.setText("");
                checkOtpImg.setVisibility(View.GONE);
                tvTimer.setVisibility(View.GONE);
                tvResend.setVisibility(View.VISIBLE);
            }

        };

        countDownTimer.start();
    }

    private void SendOtp(String mnumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mnumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                       //signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verificationId = s;
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_LONG).show();

                    }
                });
    }

    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            check();

                        }
                        else
                            {
                                progressDialog.dismiss();

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(OTP.this, "OTP do not match , Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void check() {
        FirebaseDatabase.getInstance().getReference().child("User").child(mnumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                progressDialog.dismiss();

                if (dataSnapshot.exists())
                {
                    startActivity(new Intent(OTP.this, Home.class));
                    finishAffinity();
                }
                else
                {
                    startActivity(new Intent(OTP.this, Register.class).putExtra("num", mnumber));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(OTP.this, "" + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
