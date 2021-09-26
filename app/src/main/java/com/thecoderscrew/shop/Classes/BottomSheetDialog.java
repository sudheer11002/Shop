package com.thecoderscrew.shop.Classes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.thecoderscrew.shop.Activities.AddressAndPaymentMode;
import com.thecoderscrew.shop.R;

import java.util.HashMap;
import java.util.Objects;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    private TextInputEditText first_name, last_name, mobile_no, address, pincode, townorcity, state;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_dialog, container, false);

        Button button = view.findViewById(R.id.filladdress_submit);
        first_name = view.findViewById(R.id.filladdress_firstname);
        last_name = view.findViewById(R.id.filladdress_lastname);
        mobile_no = view.findViewById(R.id.filladdress_mobile);
        address = view.findViewById(R.id.filladdress_address);
        pincode = view.findViewById(R.id.filladdress_pincode);
        state = view.findViewById(R.id.filladdress_state);

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                final String first_name_value, last_name_value, mobile_no_value, address_value, pincode_value, townorcity_value, state_value;
                first_name_value = Objects.requireNonNull(first_name.getText()).toString();
                last_name_value = Objects.requireNonNull(last_name.getText()).toString();
                mobile_no_value = Objects.requireNonNull(mobile_no.getText()).toString();
                address_value = Objects.requireNonNull(address.getText()).toString();
                pincode_value = Objects.requireNonNull(pincode.getText()).toString();
                state_value = Objects.requireNonNull(state.getText()).toString();

                if (first_name_value.isEmpty()) {
                    first_name.setError("Please enter your first name.");
                } else if (last_name_value.isEmpty()) {
                    last_name.setError("Please enter your last name.");
                } else if (mobile_no_value.isEmpty() || mobile_no_value.length() < 10) {
                    mobile_no.setError("Please enter your mobile no.");
                } else if (address_value.isEmpty()) {
                    address.setError("Please enter your address");
                } else if (pincode_value.isEmpty()) {
                    pincode.setError("Please enter your mobile no.");
                } else if (state_value.isEmpty()) {
                    state.setError("Please enter your state");
                } else {
                    HashMap<String, String> transfer = new HashMap<>();
                    transfer.put("firstname", first_name_value);
                    transfer.put("lastname", last_name_value);
                    transfer.put("mobileno", mobile_no_value);
                    transfer.put("address", address_value);
                    transfer.put("pincode", pincode_value);
                    transfer.put("state", state_value);

                    FirebaseDatabase.getInstance().getReference().child("User").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).child("userAddress").setValue(transfer).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Information added successfully.", Toast.LENGTH_SHORT).show();
                                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                                Intent intent = new Intent(getContext(), AddressAndPaymentMode.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getContext(), "There might be an issue with your internet , please try again after sometime.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        return view;
    }
}
