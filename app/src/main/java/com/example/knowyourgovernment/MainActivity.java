package com.example.knowyourgovernment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        int pos = recyclerView.getChildLayoutPosition(v);
        Official o = officialList.get(pos);
        Toast.makeText(this, "You selected official " + pos, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    // menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuInfoItem:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuLocationItem:
                // open alert dialog that allows user to enter city/state or zip to update location
                updateLocation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // alert dialogs

    public void updateLocation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
        input.setGravity(Gravity.CENTER);
        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, id) -> {
            // TODO: Update location here
        });
        builder.setNegativeButton("CANCEL", (dialog, id) -> {
            // do nothing
        });
//        builder.setTitle("Stock Selection");
        builder.setMessage("Enter a City, State or a Zip Code: ");

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}