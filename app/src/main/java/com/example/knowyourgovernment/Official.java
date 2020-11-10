package com.example.knowyourgovernment;

import java.util.ArrayList;
import java.util.HashMap;

public class Official {

    String title;
    String name;
    String party;
    String photoUrl;
    String addressesString;

    ArrayList<HashMap<String, String>> addresses;

    String phoneNumber;
    String website;
    String email;
    ArrayList<HashMap<String, String>> socialAccounts;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }


    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public ArrayList<HashMap<String, String>> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<HashMap<String, String>> addresses) {
        this.addresses = addresses;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<HashMap<String, String>> getSocialAccounts() {
        return socialAccounts;
    }

    public void setSocialAccounts(ArrayList<HashMap<String, String>> socialAccounts) {
        this.socialAccounts = socialAccounts;
    }

    public String getAddressesString() {
        return addressesString;
    }

    public void setAddressesString(String addressesString) {
        this.addressesString = addressesString;
    }
}
