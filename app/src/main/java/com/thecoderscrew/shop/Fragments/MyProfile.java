package com.thecoderscrew.shop.Fragments;

        import android.annotation.SuppressLint;
        import android.os.Build;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.annotation.RequiresApi;
        import androidx.appcompat.widget.AppCompatButton;
        import androidx.appcompat.widget.AppCompatEditText;
        import androidx.appcompat.widget.AppCompatImageView;
        import androidx.appcompat.widget.AppCompatTextView;
        import androidx.fragment.app.Fragment;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.thecoderscrew.shop.R;

        import java.util.HashMap;
        import java.util.Objects;

public class MyProfile extends Fragment {

    private AppCompatEditText first_name, last_name, email_id;
    private AppCompatTextView name;
    private AppCompatImageView edit_profile;
    private HashMap<String, String> transfer = new HashMap<>();

    public MyProfile() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.myprofile_fragment, container, false);
        final AppCompatButton submit = view.findViewById(R.id.MyAccount_button);
        edit_profile = view.findViewById(R.id.edit_profile);
        name = view.findViewById(R.id.myprofile_name);
        first_name = view.findViewById(R.id.myprofile_firstname);
        last_name = view.findViewById(R.id.myprofile_lastname);
        email_id = view.findViewById(R.id.myprofile_emailid);

        submit.setVisibility(View.GONE);
        first_name.setFocusable(false);
        last_name.setFocusable(false);
        email_id.setFocusable(false);

        getdata();


        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.VISIBLE);
                first_name.setFocusableInTouchMode(true);
                last_name.setFocusableInTouchMode(true);
                email_id.setFocusableInTouchMode(true);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.GONE);
                if (Objects.requireNonNull(first_name.getText()).toString().isEmpty()) {

                    first_name.setError("Enter your first name");
                } else if (Objects.requireNonNull(last_name.getText()).toString().isEmpty()) {

                    last_name.setError("Enter your last name");

                } else if (Objects.requireNonNull(email_id.getText()).toString().isEmpty()) {

                    last_name.setError("Enter your email");
                } else {
                    transfer.put("firstname", first_name.getText().toString());
                    transfer.put("lastname", last_name.getText().toString());
                    transfer.put("emailid", email_id.getText().toString());


                    FirebaseDatabase.getInstance().getReference().child("User").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).setValue(transfer).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Submitted Successfully", Toast.LENGTH_SHORT).show();
                                first_name.setFocusable(false);
                                last_name.setFocusable(false);
                                email_id.setFocusable(false);
                            } else {
                                Toast.makeText(getActivity(), "" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        return view;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getdata() {
        FirebaseDatabase.getInstance().getReference().child("User").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                first_name.setText(Objects.requireNonNull(dataSnapshot.child("firstname").getValue()).toString());
                last_name.setText(Objects.requireNonNull(dataSnapshot.child("lastname").getValue()).toString());
                email_id.setText(Objects.requireNonNull(dataSnapshot.child("emailid").getValue()).toString());
                name.setText(Objects.requireNonNull(dataSnapshot.child("firstname").getValue()).toString() + " " + Objects.requireNonNull(dataSnapshot.child("lastname").getValue()).toString());
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "" + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
