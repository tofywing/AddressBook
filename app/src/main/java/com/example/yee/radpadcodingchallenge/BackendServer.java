package com.example.yee.radpadcodingchallenge;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yee on 10/26/15.
 * Fictional backend server
 */
public class BackendServer {

    public static ArrayList<AddressData> mDataSet = new ArrayList<>();


    private BackendServer(String username) {
        //If the user's data is empty, the first element of the data set will be the default one.
        //Once the dataSet create the  index0 will always the default one.
    }

    public static BackendServer login(String username) {

        return new BackendServer(username);
    }

    public void toServer(AddressData address) {
        mDataSet.add(address);
    }

    public AddressData fromServer(int index) {
        return mDataSet.get(index);
    }
}
