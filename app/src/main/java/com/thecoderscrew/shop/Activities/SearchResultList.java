package com.thecoderscrew.shop.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.thecoderscrew.shop.Model.Home_Model1;
import com.thecoderscrew.shop.R;
import com.thecoderscrew.shop.viewHolders.MyViewHolder;

public class SearchResultList extends AppCompatActivity {

    String searchedText;
    private AppCompatEditText SearchText;
    private RecyclerView result_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_list);

        searchedText = getIntent().getStringExtra("searchedtext");

        result_list = findViewById(R.id.result_list);

        result_list.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        result_list.setHasFixedSize(true);

        SearchText=findViewById(R.id.searchText);

        SearchText.setText(searchedText);

        firebaseItemSearch(searchedText);
    }

    private void firebaseItemSearch(String searchedText) {            //Searching in Firebase Database
        Query firebaseSearchQuery = FirebaseDatabase.getInstance().getReference("Item").child("TeeShirts").orderByChild("productname").startAt(searchedText).endAt(searchedText + "\uf8ff");
        FirebaseRecyclerAdapter<Home_Model1, MyViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Home_Model1, MyViewHolder>(

                Home_Model1.class,
                R.layout.card,
                MyViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(MyViewHolder viewHolder, Home_Model1 model, int position) {
                viewHolder.setData(model, "kjl");
            }
        };


        result_list.setAdapter(firebaseRecyclerAdapter);



    }
}
