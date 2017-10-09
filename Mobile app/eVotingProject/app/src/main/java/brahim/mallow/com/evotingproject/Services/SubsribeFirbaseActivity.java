package brahim.mallow.com.evotingproject.Services;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import brahim.mallow.com.evotingproject.R;

/**
 * Created by brahim on 13/01/17.
 */

public class SubsribeFirbaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_firbase);

        //FirebaseMessaging.getInstance().subscribeToTopic("test");
        String s = FirebaseInstanceId.getInstance().getToken();
        Log.d("LLLLLLL !! ", s);

    }
}
