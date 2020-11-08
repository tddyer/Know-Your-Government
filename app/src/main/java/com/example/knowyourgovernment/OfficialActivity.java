package com.example.knowyourgovernment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class OfficialActivity extends AppCompatActivity {

    private TextView officialNameTextView;
    private TextView officialTitleTextView;
    private TextView officialPartyTextView;
    int pos = -1;

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

        officialNameTextView = findViewById(R.id.officialNameTextViewOfficial);
        officialTitleTextView = findViewById(R.id.officialTitleTextViewOfficial);
        officialPartyTextView = findViewById(R.id.officialPartyTextViewOfficial);

        // populating official data
        bundle = getIntent().getExtras();
        if (bundle != null) {
            officialNameTextView.setText(bundle.getString("OFFICIAL_NAME"));
            officialTitleTextView.setText(bundle.getString("OFFICIAL_TITLE"));
            officialPartyTextView.setText(String.format("(%s Party)", bundle.getString("OFFICIAL_PARTY")));
        }
    }
}