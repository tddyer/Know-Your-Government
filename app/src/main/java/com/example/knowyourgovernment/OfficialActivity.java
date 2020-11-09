package com.example.knowyourgovernment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class OfficialActivity extends AppCompatActivity {

    // layout elements
    ScrollView officialView;
    private TextView officialNameTextView;
    private TextView officialTitleTextView;
    private TextView officialPartyTextView;
    private ImageView officialImage;
    private ImageView partyImage;

    Official official = new Official();

    int bgColor;

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

        officialView = findViewById(R.id.officialView);
        officialNameTextView = findViewById(R.id.officialNameTextViewOfficial);
        officialTitleTextView = findViewById(R.id.officialTitleTextViewOfficial);
        officialPartyTextView = findViewById(R.id.officialPartyTextViewOfficial);
        officialImage = findViewById(R.id.officialImageView);
        partyImage = findViewById(R.id.partyImageView);


        // populating official data from the received intent
        bundle = getIntent().getExtras();
        if (bundle != null) {
            official = handleOfficialData(bundle);
            officialNameTextView.setText(official.getName());
            officialTitleTextView.setText(official.getTitle());
            officialPartyTextView.setText(String.format("(%s Party)", official.getParty()));
        }

        // set background color
        setBackgroundColor();
        officialView.setBackgroundColor(bgColor);

        // set official party image
        if (official.getParty().equals("Republican") || official.getParty().equals("Democratic"))
            partyImage.setImageResource(getPartyImage(official.getParty()));
        else
            partyImage.setVisibility(View.GONE);

        // creating navigation to PhotoDetailActivity using onClickListener
        officialImage.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PhotoDetailActivity.class);
            intent.putExtra("OFFICIAL_NAME", official.getName());
            intent.putExtra("OFFICIAL_TITLE", official.getTitle());
            intent.putExtra("OFFICIAL_PARTY", official.getParty());
            intent.putExtra("BG_COLOR", bgColor);
            startActivity(intent);
        });
    }

    // handle received official data
    public static Official handleOfficialData(Bundle bundle) {
        Official temp = new Official();
        temp.setName(bundle.getString("OFFICIAL_NAME"));
        temp.setTitle(bundle.getString("OFFICIAL_TITLE"));
        temp.setParty(bundle.getString("OFFICIAL_PARTY"));
        return temp;
    }

    // set party image based off of official's political affiliation
    public static int getPartyImage(String party) {
        if (party.equals("Republican"))
            return R.drawable.rep_logo;
        else
            return R.drawable.dem_logo;
    }

    // sets background color based off of the Official's political affiliation
    public void setBackgroundColor() {
        if (official.getParty().equals("Republican"))
            bgColor = Color.parseColor("#FFFF0000");
        else if (official.getParty().equals("Democratic"))
            bgColor = Color.parseColor("#FF0000FF");
        else
            bgColor = Color.parseColor("#FF000000");
    }
}