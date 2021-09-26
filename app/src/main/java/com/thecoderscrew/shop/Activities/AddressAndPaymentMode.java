package com.thecoderscrew.shop.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.thecoderscrew.shop.Classes.BottomSheetDialog;
import com.thecoderscrew.shop.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class AddressAndPaymentMode extends AppCompatActivity {

    private AppCompatButton add_address, proceed, edit_address, delete_address, submit;
    private Toolbar toolbar, bottomlayouttoolbar;
    private LinearLayout layoutToShow, layoutToGo;
    private AppCompatEditText address_exists_edittext;
    private RadioButton onlinePayment, cashOnDelivery;
    private RadioGroup radioGroup;
    private ProgressDialog progressDialog;
    String firstname_value, lastname_value, mobileno_value, address_value, pincode_value, state_value, concatenate;
    String title, size, color, discount, price, quantity, first_image;


    private boolean exists = false;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_and_payment_mode);

        add_address = findViewById(R.id.add_new_address);
        proceed = findViewById(R.id.filladdress_submit);
        submit = findViewById(R.id.address_submit);
        toolbar = findViewById(R.id.paymnetmode_toolbar);
        bottomlayouttoolbar = findViewById(R.id.filladdress_toolbar);
        layoutToShow = findViewById(R.id.address_exits_page);
        layoutToGo = findViewById(R.id.tohide);
        edit_address = findViewById(R.id.address_exists_editbutton);
        delete_address = findViewById(R.id.address_exists_deletebutton);
        address_exists_edittext = findViewById(R.id.address_exists_edittext);
        onlinePayment = findViewById(R.id.radioButton_OnlinePayment);
        cashOnDelivery = findViewById(R.id.radioButton_CashOnDelivery);
        radioGroup = findViewById(R.id.radioGroup);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        final String itemKey = getIntent().getStringExtra("key");
        final String itemKey2 = getIntent().getStringExtra("key1");
        title = getIntent().getStringExtra("title");
        size = getIntent().getStringExtra("size");
        color = getIntent().getStringExtra("color");
        discount = getIntent().getStringExtra("discount");
        price = getIntent().getStringExtra("price");
        quantity = getIntent().getStringExtra("quantity");
        first_image = getIntent().getStringExtra("first_image");


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        FirebaseDatabase.getInstance().getReference().child("User").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber()))
                .child("userAddress").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    layoutToShow.setVisibility(View.VISIBLE);
                    layoutToGo.setVisibility(View.GONE);
                    exists = true;
                    firstname_value = Objects.requireNonNull(dataSnapshot.child("firstname").getValue()).toString();
                    lastname_value = Objects.requireNonNull(dataSnapshot.child("lastname").getValue()).toString();
                    mobileno_value = Objects.requireNonNull(dataSnapshot.child("mobileno").getValue()).toString();
                    address_value = Objects.requireNonNull(dataSnapshot.child("address").getValue()).toString();
                    pincode_value = Objects.requireNonNull(dataSnapshot.child("pincode").getValue()).toString();
                    state_value = Objects.requireNonNull(dataSnapshot.child("state").getValue()).toString();
                    concatenate = firstname_value + "\t" + lastname_value + "\n" + mobileno_value + "," + "\n" + address_value + "," + pincode_value + "," + "\n" + state_value;
                    address_exists_edittext.setText(concatenate);

                } else {

                    layoutToShow.setVisibility(View.GONE);
                    layoutToGo.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabaseError", "" + databaseError);
                Toast.makeText(getApplicationContext(), "There is an internal error , please try again after sometime.", Toast.LENGTH_SHORT).show();

            }
        });


        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                bottomSheetDialog.show(getSupportFragmentManager(), "bottomsheet");

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int compare = radioGroup.getCheckedRadioButtonId();
                if (exists) {
                    if (compare == R.id.radioButton_OnlinePayment) {

                        //checksumUsingVolley();

                        final HashMap<String, String> transfer = new HashMap<>();
                        final HashMap<String, String> transferAddress = new HashMap<>();
                        final HashMap<String, String> transferProductDetails = new HashMap<>();
                        final String time, date;
                        @SuppressLint("SimpleDateFormat") DateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
                        @SuppressLint("SimpleDateFormat") DateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
                        Date getDate = new Date();

                        date = formatDate.format(getDate);
                        time = formatTime.format(getDate);

                        transfer.put("time", time);
                        transfer.put("date", date);
                        transfer.put("orderID", "d64he9ddnlol9djdueibe");
                        transfer.put("customerID", "242532423");
                        transfer.put("totalPrice", "230");
                        transfer.put("paymentMode", "OnlinePayment");

                        transferAddress.put("firstName", firstname_value);
                        transferAddress.put("lastName", lastname_value);
                        transferAddress.put("mobileNo", mobileno_value);
                        transferAddress.put("address", address_value);
                        transferAddress.put("pincode", pincode_value);
                        transferAddress.put("state", state_value);

                        transferProductDetails.put("title", title);
                        transferProductDetails.put("size", size);
                        transferProductDetails.put("color", color);
                        transferProductDetails.put("discount", discount);
                        transferProductDetails.put("price", price);
                        transferProductDetails.put("quantity", quantity);
                        transferProductDetails.put("first_image", first_image);

                        FirebaseDatabase.getInstance().getReference().child("Orders").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).child(date).child(itemKey + time).child("orderDetails").setValue(transfer).addOnCompleteListener(new OnCompleteListener<Void>() {    //addoncompletelistener will define what will happen on successful transfer
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    FirebaseDatabase.getInstance().getReference().child("Orders").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).child(date).child(itemKey + time).child("deliveryAddress").setValue(transferAddress).addOnCompleteListener(new OnCompleteListener<Void>() {    //addoncompletelistener will define what will happen on successful transfer
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {


                                                FirebaseDatabase.getInstance().getReference().child("Orders").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).child(date).child(itemKey + time).child("productDetails").setValue(transferProductDetails).addOnCompleteListener(new OnCompleteListener<Void>() {    //addoncompletelistener will define what will happen on successful transfer
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful()) {

                                                            FirebaseDatabase.getInstance().getReference().child("Cart").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber()))
                                                                    .child(itemKey).child(itemKey2).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Log.e("Response", "Success");
                                                                    } else {
                                                                        Log.e("Response", "Failure");
                                                                    }
                                                                }
                                                            });
                                                            progressDialog.show();
                                                            startActivity(new Intent(getApplicationContext(), AfterPayment.class));
                                                            finish();
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "There is an error , please try again after sometime.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                            } else {
                                                Toast.makeText(getApplicationContext(), "There is an error , please try again after sometime.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(getApplicationContext(), "There is an error , please try again after sometime.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    } else if (compare == R.id.radioButton_CashOnDelivery) {

                        final HashMap<String, String> transfer = new HashMap<>();
                        final HashMap<String, String> transferAddress = new HashMap<>();
                        final HashMap<String, String> transferProductDetails = new HashMap<>();
                        final String time, date;
                        @SuppressLint("SimpleDateFormat") DateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
                        @SuppressLint("SimpleDateFormat") DateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
                        Date getDate = new Date();

                        date = formatDate.format(getDate);
                        time = formatTime.format(getDate);

                        transfer.put("time", time);
                        transfer.put("date", date);
                        transfer.put("orderID", "d64he9ddnlol9djdueibe");
                        transfer.put("customerID", "242532423");
                        transfer.put("totalPrice", "230");
                        transfer.put("paymentMode", "OnlinePayment");

                        transferAddress.put("firstName", firstname_value);
                        transferAddress.put("lastName", lastname_value);
                        transferAddress.put("mobileNo", mobileno_value);
                        transferAddress.put("address", address_value);
                        transferAddress.put("pincode", pincode_value);
                        transferAddress.put("state", state_value);

                        transferProductDetails.put("title", title);
                        transferProductDetails.put("size", size);
                        transferProductDetails.put("color", color);
                        transferProductDetails.put("discount", discount);
                        transferProductDetails.put("price", price);
                        transferProductDetails.put("quantity", quantity);
                        transferProductDetails.put("first_image", first_image);

                        FirebaseDatabase.getInstance().getReference().child("Orders").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).child(date).child(itemKey + time).child("orderDetails").setValue(transfer).addOnCompleteListener(new OnCompleteListener<Void>() {    //addoncompletelistener will define what will happen on successful transfer
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    FirebaseDatabase.getInstance().getReference().child("Orders").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).child(date).child(itemKey + time).child("deliveryAddress").setValue(transferAddress).addOnCompleteListener(new OnCompleteListener<Void>() {    //addoncompletelistener will define what will happen on successful transfer
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {


                                                FirebaseDatabase.getInstance().getReference().child("Orders").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).child(date).child(itemKey + time).child("productDetails").setValue(transferProductDetails).addOnCompleteListener(new OnCompleteListener<Void>() {    //addoncompletelistener will define what will happen on successful transfer
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful()) {
                                                            FirebaseDatabase.getInstance().getReference().child("Cart").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber()))
                                                                    .child(itemKey).child(itemKey2).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Log.e("Response", "Success");
                                                                    } else {
                                                                        Log.e("Response", "Failure");
                                                                    }
                                                                }
                                                            });
                                                            progressDialog.show();
                                                            startActivity(new Intent(getApplicationContext(), AfterPayment.class));
                                                            finish();
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "There is an error , please try again after sometime.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                            } else {
                                                Toast.makeText(getApplicationContext(), "There is an error , please try again after sometime.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(getApplicationContext(), "There is an error , please try again after sometime.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    } else {
                        Toast.makeText(getApplicationContext(), "Please select the mode of payment", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddressAndPaymentMode.this, "Address can't be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                bottomSheetDialog.show(getSupportFragmentManager(), "bottomsheet");
            }
        });

        delete_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("User").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber()))
                        .child("userAddress").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            exists = false;
                            Toast.makeText(getApplicationContext(), "Address Deleted.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }


    private void checksumUsingVolley() {

        final String M_ID = "AvLxMo41506505028905";
        final String cust_ID = FirebaseAuth.getInstance().getUid();
        final String order_ID = "OD" + UUID.randomUUID().toString().substring(0, 28);

        //final String callBackUrl = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=" + order_ID;
        String url = "https://rk5422686.000webhostapp.com/paytm/generateChecksum.php";   //link to your server

        //String callBackUrl = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=" + order_ID;  //for production
        // final String callBackUrl = "https://pguat-stage.paytm.com/paytmchecksum/paytmCallback.jsp";

        final String callBackUrl = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=" + order_ID;  //for test

        final RequestQueue requestQueue = Volley.newRequestQueue(AddressAndPaymentMode.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String s) {

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.has("CHECKSUMHASH")) {

                        final String CHECKSUMHASH = jsonObject.getString("CHECKSUMHASH");

                        PaytmPGService paytmPGService = PaytmPGService.getStagingService();    //for test
                        //PaytmPGService paytmPGService = PaytmPGService.getProductionService();  //for production

                        HashMap<String, String> paramMap = new HashMap<String, String>();
                        paramMap.put("MID", M_ID);
                        paramMap.put("ORDER_ID", order_ID);

                        paramMap.put("CUST_ID", Objects.requireNonNull(cust_ID));
                        //       paramMap.put( "CHANNEL_ID" , "WAP");   //for app and web
                        paramMap.put("CHANNEL_ID", "WEB");   //for only web
                        paramMap.put("TXN_AMOUNT", price);
                        paramMap.put("WEBSITE", "WEBSTAGING");
                        paramMap.put("INDUSTRY_TYPE_ID", "Retail");
                        paramMap.put("CALLBACK_URL", callBackUrl);
                        paramMap.put("CHECKSUMHASH", CHECKSUMHASH);

                        PaytmOrder paytmOrder = new PaytmOrder(paramMap);

                        paytmPGService.initialize(paytmOrder, null);
                        paytmPGService.startPaymentTransaction(AddressAndPaymentMode.this, true, true, new PaytmPaymentTransactionCallback() {

                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            public void onTransactionResponse(Bundle inResponse) {
                                Log.d("roy", "onTransactionResponse: " + inResponse.toString());

                                if (Objects.equals(inResponse.getString("STATUS"), "TXN_SUCCESS")) {

                                    final HashMap<String, String> transfer = new HashMap<>();
                                    final String time, date;
                                    @SuppressLint("SimpleDateFormat") DateFormat formatDate = new SimpleDateFormat("dd/MM/yy");
                                    @SuppressLint("SimpleDateFormat") DateFormat formatTime = new SimpleDateFormat("HH:mm");
                                    Date getDate = new Date();
                                    date = formatDate.format(getDate);
                                    time = formatTime.format(getDate);
                                    transfer.put("time", time);
                                    transfer.put("orderID", order_ID);
                                    transfer.put("customerID", cust_ID);
                                    transfer.put("totalPrice", "23");
                                    FirebaseDatabase.getInstance().getReference().child("Orders").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).child(date).setValue(transfer).addOnCompleteListener(new OnCompleteListener<Void>() {    //addoncompletelistener will define what will happen on successful transfer
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {

                                                final HashMap<String, String> transferAddress = new HashMap<>();
                                                transferAddress.put("firstName", firstname_value);
                                                transferAddress.put("lastName", lastname_value);
                                                transferAddress.put("mobileNo", mobileno_value);
                                                transferAddress.put("address", address_value);
                                                transferAddress.put("pincode", pincode_value);
                                                transferAddress.put("state", state_value);
                                                FirebaseDatabase.getInstance().getReference().child("Orders").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).child(date).child("deliveryAddress").setValue(transferAddress).addOnCompleteListener(new OnCompleteListener<Void>() {    //addoncompletelistener will define what will happen on successful transfer
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful()) {
                                                            startActivity(new Intent(getApplicationContext(), AfterPayment.class));
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "There is an error , please try again after sometime.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(getApplicationContext(), "There is an error , please try again after sometime.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    // do whatever you want after amount is paid

                                } else {
                                    Toast.makeText(getApplicationContext(), "Transaction Failed. Please try again later", Toast.LENGTH_SHORT).show();
                                }


                                Log.d("lets see", "onTransactionResponse: " + inResponse.toString());

                            }

                            public void networkNotAvailable() {
                                Toast.makeText(getApplicationContext(), "Network connection error: Check your internet connectivity", Toast.LENGTH_LONG).show();
                            }


                            public void clientAuthenticationFailed(String inErrorMessage) {

                                Toast.makeText(getApplicationContext(), "Authentication failed: Server error" + inErrorMessage.toString(), Toast.LENGTH_LONG).show();
                            }

                            public void someUIErrorOccurred(String inErrorMessage) {
                                Toast.makeText(getApplicationContext(), "UI Error " + inErrorMessage, Toast.LENGTH_LONG).show();
                            }


                            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                                Toast.makeText(getApplicationContext(), "Unable to load webpage " + inErrorMessage.toString(), Toast.LENGTH_LONG).show();

                            }

                            public void onBackPressedCancelTransaction() {
                                Toast.makeText(getApplicationContext(), "Transaction cancelled", Toast.LENGTH_LONG).show();

                            }


                            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                                Toast.makeText(getApplicationContext(), "Transaction cancelled", Toast.LENGTH_LONG).show();
                            }
                        });


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> paramMap = new HashMap<String, String>();
                paramMap.put("MID", M_ID);
                // Key in your staging and production MID available in your dashboard
                paramMap.put("ORDER_ID", order_ID);
                paramMap.put("CUST_ID", Objects.requireNonNull(cust_ID));
                paramMap.put("CHANNEL_ID", "WEB");
                paramMap.put("TXN_AMOUNT", getIntent().getStringExtra("price"));
                paramMap.put("WEBSITE", "WEBSTAGING");
                paramMap.put("INDUSTRY_TYPE_ID", "Retail");
                paramMap.put("CALLBACK_URL", callBackUrl);

                return paramMap;
            }
        };

        requestQueue.add(stringRequest);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
