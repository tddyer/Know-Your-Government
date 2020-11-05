package com.example.knowyourgovernment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import java.util.regex.Pattern;

public class AboutActivity extends AppCompatActivity {

    private final String link = "https://developers.google.com/civic-information/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // add link to google civic api
        TextView googleLink = findViewById(R.id.googleAPITextView);
        googleLink.setMovementMethod(LinkMovementMethod.getInstance());
//        Linkify.addLinks(googleLink, 0);
//
    }
}