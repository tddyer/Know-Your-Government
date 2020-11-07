package com.example.knowyourgovernment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity
    implements View.OnClickListener, View.OnLongClickListener {

    // officials recyclerview vars
    private List<Official> officialList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OfficialsAdapter officialsAdapter;

    // location service vars
    private LocationManager locationManager;
    private Criteria criteria;
    Location desiredLocation = null; // starts as device location, can be updated to alternate locations
    private String locationZipCode = null;
    private String locationCity = null;
    private String locationState = null;
    private static int LOCATION_REQUEST_CODE = 1001;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setting initial location to location of the device
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);

        geocoder = new Geocoder(this);

        // location permissions
        //   - if already granted => set location
        //   - else, request permissions and execute onRequestPermissionsResult()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION
                },
                LOCATION_REQUEST_CODE);
        } else {
            setLocation();
        }


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

    // location services

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_REQUEST_CODE) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PERMISSION_GRANTED) {
                setLocation();
                return;
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void setLocation() {
        String bestProvider = locationManager.getBestProvider(criteria, true);

        if (bestProvider != null) {
            try {
                desiredLocation = locationManager.getLastKnownLocation(bestProvider);

                // converting coordinates to address
                if (desiredLocation != null) {
                    List<Address> addresses = geocoder.getFromLocation(desiredLocation.getLatitude(),
                            desiredLocation.getLongitude(),
                            1);
                    locationZipCode = addresses.get(0).getPostalCode();
                    locationState = addresses.get(0).getAdminArea();
                    locationCity = addresses.get(0).getLocality();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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