package com.example.test3;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bag);

        // Set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.bag_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
}
