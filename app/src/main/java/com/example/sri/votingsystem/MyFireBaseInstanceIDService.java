package com.example.sri.votingsystem;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Sri on 4/10/2017.
 */

public class MyFireBaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseInsIDService";

    @Override
    public void onTokenRefresh() {
        //get updated token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"New Token: "+ refreshedToken);


    }
}
