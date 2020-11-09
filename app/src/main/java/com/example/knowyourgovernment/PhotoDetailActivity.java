package com.example.knowyourgovernment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class PhotoDetailActivity extends AppCompatActivity {

    // layout elements
    ScrollView photoView;
    private TextView officialNameTextView;
    private TextView officialTitleTextView;
    private ImageView officialImage;

    private Bundle bundle;

    Official official = new Official();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        photoView = findViewById(R.id.photoView);
        officialNameTextView = findViewById(R.id.officialNameTextViewPhoto);
        officialTitleTextView = findViewById(R.id.officialTitleTextViewPhoto);

        // populating official data from the received intent
        bundle = getIntent().getExtras();
        if (bundle != null) {
            // handle official data
            official = OfficialActivity.handleOfficialData(bundle);
            officialNameTextView.setText(official.getName());
            officialTitleTextView.setText(official.getTitle());

            // set background color
            photoView.setBackgroundColor(bundle.getInt("BG_COLOR"));
            
        }
    }
}