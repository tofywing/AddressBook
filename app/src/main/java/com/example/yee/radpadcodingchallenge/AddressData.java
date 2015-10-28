package com.example.yee.radpadcodingchallenge;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yee on 10/26/15.
 */
public class AddressData {
    public String street = "";
    public String city = "";
    public String state = "";
    public String rent = "";
    public String landlord = "";
    public String moveIn = "";
    public String moveOut = "";
    public String company = "";
    public String firstName = "";
    public String lastName = "";
    public static AddressData DEFAULT = new AddressData();

    private AddressData() {

    }

    public static AddressData saveAddress(String street, String city, String state, String rent, String landlord, String
            moveIn, String moveOut, String company, String firstName, String lastName) {
        AddressData address = new AddressData();
        address.street = street;
        address.city = city;
        address.state = state;
        address.rent = rent;
        address.landlord = landlord;
        address.moveIn = moveIn;
        address.moveOut = moveOut;
        address.company = company;
        address.firstName = firstName;
        address.lastName = lastName;
        return address;
    }
    // Try to implement parcelable to make this data set couble be put into "outState" but don't have time to do so.
    //
    //    public static final Parcelable.Creator<AddressData> CREATOR = new Parcelable.Creator<AddressData>() {
    //        public AddressData createFromParcel(Parcel in) {
    //            return new AddressData(in);
    //        }
    //    };
    //
    //    @Override
    //    public int describeContents() {
    //        return 0;
    //    }
    //
    //    @Override
    //    public void writeToParcel(Parcel dest, int flags) {
    //
    //    }
}
