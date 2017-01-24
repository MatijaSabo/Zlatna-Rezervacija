package com.foi.air.zlatnarezervacija;

import android.widget.Toast;

import com.foi.air.zlatnarezervacija.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by masrnec on 24.1.2017..
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();

    }
}
