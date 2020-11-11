package com.example.knowyourgovernment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class OfficialActivity extends AppCompatActivity {

    // layout elements
    ScrollView officialView;
    private TextView locationTextView;
    private TextView officialNameTextView;
    private TextView officialTitleTextView;
    private TextView officialPartyTextView;
    private TextView officialAddressTextView;
    private TextView officialPhoneTextView;
    private TextView officialEmailTextView;
    private TextView officialWebsiteTextView;

    private ImageView officialImage;
    private ImageView partyImage;
    private ImageView twitterImage;
    private ImageView fbImage;
    private ImageView ytImage;

    Official official = new Official();

    int bgColor;

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            officialView = findViewById(R.id.officialView);
        else
            officialView = findViewById(R.id.officialLayoutView);

        locationTextView = findViewById(R.id.locationTextViewOfficial);
        officialNameTextView = findViewById(R.id.officialNameTextViewOfficial);
        officialTitleTextView = findViewById(R.id.officialTitleTextViewOfficial);
        officialPartyTextView = findViewById(R.id.officialPartyTextViewOfficial);
        officialAddressTextView = findViewById(R.id.addressTextViewOfficial);
        officialPhoneTextView = findViewById(R.id.phoneTextViewOfficial);
        officialEmailTextView = findViewById(R.id.emailTextViewOfficial);
        officialWebsiteTextView = findViewById(R.id.websiteTextViewOfficial);

        officialImage = findViewById(R.id.officialImageView);
        partyImage = findViewById(R.id.partyImageView);
        twitterImage = findViewById(R.id.twitterImageView);
        fbImage = findViewById(R.id.facebookImageView);
        ytImage = findViewById(R.id.youtubeImageView);


        // handling received data from bundle
        bundle = getIntent().getExtras();
        if (bundle != null) {
            locationTextView.setText(bundle.getString("LOCATION_STRING"));

            // populating official data from the received intent
            official = handleOfficialData(bundle);
            officialNameTextView.setText(official.getName());
            officialTitleTextView.setText(official.getTitle());
            officialPartyTextView.setText(String.format("(%s)", official.getParty()));

            if (official.getAddressesString() != null) {
                officialAddressTextView.setText(official.getAddressesString());
                Linkify.addLinks(officialAddressTextView, Linkify.MAP_ADDRESSES);
            } else {
                TextView addrPlaceholder = findViewById(R.id.addressPlaceholderTextViewOfficial);
                addrPlaceholder.setVisibility(View.GONE);
                officialAddressTextView.setVisibility(View.GONE);
            }

            if (official.getPhoneNumber() != null) {
                officialPhoneTextView.setText(official.getPhoneNumber());
                Linkify.addLinks(officialPhoneTextView, Linkify.PHONE_NUMBERS);
            } else {
                TextView phonePlaceholder = findViewById(R.id.phonePlaceholderTextViewOfficial);
                phonePlaceholder.setVisibility(View.GONE);
                officialPhoneTextView.setVisibility(View.GONE);
            }

            if (official.getEmail() != null) {
                officialEmailTextView.setText(official.getEmail());
                Linkify.addLinks(officialEmailTextView, Linkify.EMAIL_ADDRESSES);
            } else {
                TextView emailPlaceholder = findViewById(R.id.emailPlaceholderTextViewOfficial);
                emailPlaceholder.setVisibility(View.GONE);
                officialEmailTextView.setVisibility(View.GONE);
            }

            if (official.getWebsite() != null) {
                officialWebsiteTextView.setText(official.getWebsite());
                Linkify.addLinks(officialWebsiteTextView, Linkify.WEB_URLS);
            } else {
                TextView websitePlaceholder = findViewById(R.id.websitePlaceholderTextViewOfficial);
                websitePlaceholder.setVisibility(View.GONE);
                officialWebsiteTextView.setVisibility(View.GONE);
            }

            if (official.getSocialAccounts() != null) {
                // fb b1 twitter b2 yt
                ArrayList<HashMap<String, String>> socials = official.getSocialAccounts();
                for (HashMap<String, String> social : socials) {

                    // TODO: add social link to images

                    for (Map.Entry<String, String> entry : social.entrySet()) {
                        String label = entry.getKey();
                        String link = entry.getValue();
                        if (label.equals("Facebook")) {
                            fbImage.setVisibility(View.VISIBLE);

                            fbImage.setOnClickListener(v -> fbOnClick(link));

                            // sets padding between social icons
                            ImageView b1 = findViewById(R.id.blankImageView);
                            b1.setVisibility(View.INVISIBLE);
                        }
                        if (label.equals("Twitter")) {
                            twitterImage.setVisibility(View.VISIBLE);

                            twitterImage.setOnClickListener(v -> twitterOnClick(link));

                            // sets padding between social icons
                            ImageView b2 = findViewById(R.id.blankImageView2);
                            b2.setVisibility(View.INVISIBLE);
                        }
                        if (label.equals("YouTube")) {
                            ytImage.setVisibility(View.VISIBLE);

                            ytImage.setOnClickListener(v -> ytOnClick(link));
                        }
                    }
                }
            }

            // image download
            String imageURL = official.getPhotoUrl();
            if (imageURL != null) {
                loadImage(imageURL);
            }

        }

        // set background color
        setBackgroundColor();
        officialView.setBackgroundColor(bgColor);

        // set official party image
        if (official.getParty().contains("Republican") || official.getParty().contains("Democrat")) {
            partyImage.setImageResource(getPartyImage(official.getParty()));
            partyImage.setOnClickListener(v -> startActivity(partyOnClick(official.getParty())));
        } else {
            partyImage.setVisibility(View.GONE);
        }
    }

    // handle received official data
    public static Official handleOfficialData(Bundle bundle) {
        Official temp = new Official();
        temp.setName(bundle.getString("OFFICIAL_NAME"));
        temp.setTitle(bundle.getString("OFFICIAL_TITLE"));
        temp.setParty(bundle.getString("OFFICIAL_PARTY"));

        if (bundle.getString("OFFICIAL_PHONE") != null) {
            temp.setPhoneNumber(bundle.getString("OFFICIAL_PHONE"));
        }

        if (bundle.getString("OFFICIAL_EMAIL") != null) {
            temp.setEmail(bundle.getString("OFFICIAL_EMAIL"));
        }

        if (bundle.getString("OFFICIAL_WEBSITE") != null) {
            temp.setWebsite(bundle.getString("OFFICIAL_WEBSITE"));
        }

        if (bundle.getString("OFFICIAL_ADDRESSES") != null) {
            temp.setAddressesString(bundle.getString("OFFICIAL_ADDRESSES"));
        }

        if (bundle.getString("OFFICIAL_PHOTO") != null) {
            temp.setPhotoUrl(bundle.getString("OFFICIAL_PHOTO"));
        }

        ArrayList<HashMap<String, String>> socials = new ArrayList<>();

        if (bundle.getString("Twitter") != null) {
            HashMap<String, String> twitter = new HashMap<>();
            twitter.put("Twitter", bundle.getString("Twitter"));
            socials.add(twitter);
        }

        if (bundle.getString("Facebook") != null) {
            HashMap<String, String> fb = new HashMap<>();
            fb.put("Facebook", bundle.getString("Facebook"));
            socials.add(fb);
        }

        if (bundle.getString("YouTube") != null) {
            HashMap<String, String> yt = new HashMap<>();
            yt.put("YouTube", bundle.getString("YouTube"));
            socials.add(yt);
        }

        temp.setSocialAccounts(socials);

        return temp;
    }

    // set party image based off of official's political affiliation
    public static int getPartyImage(String party) {
        if (party.contains("Republican"))
            return R.drawable.rep_logo;
        else
            return R.drawable.dem_logo;
    }

    // sets background color based off of the Official's political affiliation
    public void setBackgroundColor() {
        if (official.getParty().contains("Republican"))
            bgColor = Color.parseColor("#FFFF0000");
        else if (official.getParty().contains("Democrat"))
            bgColor = Color.parseColor("#FF0000FF");
        else
            bgColor = Color.parseColor("#FF000000");
    }

    // social media intents

    void fbOnClick(String link) {

        String fbURL = "https://www.facebook.com/" + link;
        Intent intent;
        String usageURL;

        try {
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            int versionCode = getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) {
                usageURL = "fb://facewebmodal/f?href=" + fbURL;
            } else {
                usageURL = "fb://page/" + link;
            }
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(usageURL));
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(fbURL));
        }
        startActivity(intent);
    }

    void twitterOnClick(String link) {

        String twitterAppUrl = "twitter://user?screen_name=" + link;
        String twitterWebUrl = "https://twitter.com/" + link;
        Intent intent;

        try {
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterAppUrl));
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterWebUrl));
        }
        startActivity(intent);
    }

    void ytOnClick(String link) {

        String ytUrl = "https://youtube.com/" + link;
        Intent intent;

        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.goodle.android.youtube");
            intent.setData(Uri.parse(ytUrl));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
             startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ytUrl)));
        }
    }

    public static Intent partyOnClick(String party) {
        String url;
        if (party.contains("Republican")) {
            url = "https://www.gop.com";
        } else {
            url = "https://democrats.org";
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        return intent;
    }

    // Picasso image download
    public void loadImage(final String url) {
        Picasso.get().load(url)
                .error(R.drawable.brokenimage)
                .placeholder(R.drawable.placeholder)
                .into(officialImage,
                        new Callback() {
                            @Override
                            public void onSuccess() {

                                // if an image is succesfully loaded, set navigation to photo detil activity
                                officialImage.setOnClickListener(v -> {
                                    Intent intent = new Intent(getApplicationContext(), PhotoDetailActivity.class);
                                    intent.putExtra("OFFICIAL_NAME", official.getName());
                                    intent.putExtra("OFFICIAL_TITLE", official.getTitle());
                                    intent.putExtra("OFFICIAL_PARTY", official.getParty());
                                    intent.putExtra("OFFICIAL_PHOTO", url);
                                    intent.putExtra("BG_COLOR", bgColor);
                                    startActivity(intent);
                                });
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.d("IMG DOWNLOAD", "onError: " + e.getMessage());
                            }
                        });
    }
}