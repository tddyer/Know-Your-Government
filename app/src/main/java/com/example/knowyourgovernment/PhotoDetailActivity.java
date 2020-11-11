package com.example.knowyourgovernment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends AppCompatActivity {

    // layout elements
    ScrollView photoView;
    private TextView officialNameTextView;
    private TextView officialTitleTextView;
    private ImageView officialImage;
    private ImageView partyImage;

    private Bundle bundle;

    Official official = new Official();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            photoView = findViewById(R.id.photoView);
        else
            photoView = findViewById(R.id.photoLayoutView);

        officialNameTextView = findViewById(R.id.officialNameTextViewPhoto);
        officialTitleTextView = findViewById(R.id.officialTitleTextViewPhoto);
        officialImage = findViewById(R.id.officialImageView);
        partyImage = findViewById(R.id.partyImageViewPhoto);

        // populating official data from the received intent
        bundle = getIntent().getExtras();
        if (bundle != null) {

            // set background color
            photoView.setBackgroundColor(bundle.getInt("BG_COLOR"));

            // handle official data
            official = OfficialActivity.handleOfficialData(bundle);
            officialNameTextView.setText(official.getName());
            officialTitleTextView.setText(official.getTitle());

            System.out.println(official.getPhotoUrl());

            loadImage(official.getPhotoUrl());

            // set official party image
            if (official.getParty().contains("Republican") || official.getParty().contains("Democrat")) {
                partyImage.setImageResource(OfficialActivity.getPartyImage(official.getParty()));
                partyImage.setOnClickListener(v -> startActivity(OfficialActivity.partyOnClick(official.getParty())));
            } else {
                partyImage.setVisibility(View.GONE);
            }

        }
    }

    // Picasso image download
    public void loadImage(final String url) {
        Picasso.get().load(url)
                .error(R.drawable.brokenimage)
                .placeholder(R.drawable.placeholder)
                .into(officialImage);
    }
}