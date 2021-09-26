package com.thecoderscrew.shop.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.thecoderscrew.shop.R;

import java.util.HashMap;
import java.util.Objects;

public class Register extends AppCompatActivity {

    HashMap<String, String> transfer = new HashMap<>();
    TextInputEditText register_edittext1, register_edittext2, register_edittext3, register_edittext4;
    AppCompatCheckBox checkBox;
    boolean initial_checkbox = false;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        register_edittext1 = findViewById(R.id.register_edittext1);
        register_edittext2 = findViewById(R.id.register_edittext2);
        register_edittext3 = findViewById(R.id.register_edittext3);
        register_edittext4 = findViewById(R.id.register_edittext4);
        checkBox = findViewById(R.id.register_checkbox);
        register_edittext3.setText(Objects.requireNonNull(getIntent().getExtras()).getString("num"));
        register_edittext3.setFocusable(false);

        findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String box1, box2, box3, box4;

                box1 = Objects.requireNonNull(register_edittext1.getText()).toString();
                box2 = Objects.requireNonNull(register_edittext2.getText()).toString();
                box3 = Objects.requireNonNull(register_edittext3.getText()).toString();
                box4 = Objects.requireNonNull(register_edittext4.getText()).toString();

                if (box1.isEmpty()) {
                    register_edittext1.setError("Please enter your name");
                } else if (box2.isEmpty()) {
                    register_edittext2.setError("Please enter your last name");
                } else if (box3.isEmpty()) {
                    register_edittext3.setError("Please enter your mobile no.");
                } else if (box3.length() < 12) {
                    register_edittext3.setError("Please enter correct mobile no.");
                } else if (box4.isEmpty()) {
                    register_edittext4.setError("Please enter your email id.");
                } else if (checkBox.isChecked()) {
                    initial_checkbox = true;
                }
                if (initial_checkbox) {
                    transfer.put("firstname", box1);
                    transfer.put("lastname", box2);
                    transfer.put("mobileno", box3);
                    transfer.put("emailid", box4);
                    send(transfer);
                } else {
                    Toast.makeText(getApplicationContext(), "Please accept the terms and conditions.", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void send(HashMap hashMap) {
        FirebaseDatabase.getInstance().getReference().child("User").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {    //addoncompletelistener will define what will happen on successful transfer
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    startActivity(new Intent(Register.this, Home.class));
                    finishAffinity();
                } else {
                    Toast.makeText(Register.this, "Please try again after sometime", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}
