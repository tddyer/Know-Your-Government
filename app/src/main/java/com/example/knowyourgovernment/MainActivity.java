package com.example.knowyourgovernment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
    implements View.OnClickListener, View.OnLongClickListener {

    private RecyclerView recyclerView;
//    private NotesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.officialsRecyclerView);
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