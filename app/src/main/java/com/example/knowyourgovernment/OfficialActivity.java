package com.example.knowyourgovernment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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

        // populating official data from the received intent
        bundle = getIntent().getExtras();
        if (bundle != null) {
            official = handleOfficialData(bundle);
            officialNameTextView.setText(official.getName());
            officialTitleTextView.setText(official.getTitle());
            officialPartyTextView.setText(String.format("(%s Party)", official.getParty()));
        }

        setBackgroundColor();
        officialView.setBackgroundColor(bgColor);

        // creating navigation to PhotoDetailActivity using onClickListener
        officialImage = findViewById(R.id.officialImageView);
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