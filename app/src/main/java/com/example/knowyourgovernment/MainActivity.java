package com.example.knowyourgovernment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements View.OnClickListener, View.OnLongClickListener {

    private List<Official> officialList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OfficialsAdapter officialsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // recycler setup
        recyclerView = findViewById(R.id.officialsRecyclerView);
        officialsAdapter = new OfficialsAdapter(officialList, this);
        recyclerView.setAdapter(officialsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // testing recycler
        for (int i = 0; i < 20; i++) {
            Official temp = new Official();
            temp.setName("Random Official");
            temp.setTitle("Vice-President of the United States");
            if (i % 2 == 0)
                temp.setParty("Republican");
            else
                temp.setParty("Democratic");

            officialList.add(temp);
        }
        officialsAdapter.notifyDataSetChanged();
    }

    // Overriding onClick methods

    @Override
    public void onClick(View v) {
        // do on click stuff here
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}