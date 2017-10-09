package brahim.mallow.com.evotingproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import brahim.mallow.com.evotingproject.Model.Personne;
import brahim.mallow.com.evotingproject.Requests.TokenRequest;
import brahim.mallow.com.evotingproject.SessionManager.MySessionManager;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by brahim on 05/01/17.
 */

public class SplashScreenActivity extends Activity {

    private static final int SPLASH_TIME_OUT = 3000;
    public static final String DOMAINE="http://192.168.1.25/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {



            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                MySessionManager sessionManager = new MySessionManager(getApplicationContext());
                if(sessionManager.isLoggedIn()){
                    Bundle b = new Bundle();
                    Personne pe = sessionManager.getUserDetails();
                    b.putSerializable("personne", pe);


                    //FirebaseMessaging.getInstance().subscribeToTopic("test");
                    //FirebaseInstanceId.getInstance().getToken();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("HAHAHAHHA", "HAHAHAHAH");
                        }
                    };
                    Log.d("TOKEN", FirebaseInstanceId.getInstance().getToken());
                    TokenRequest request = new TokenRequest(pe.getCin(), FirebaseInstanceId.getInstance().getToken(), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(SplashScreenActivity.this);
                    queue.add(request);

                    Intent i = new Intent(SplashScreenActivity.this, UserActivity.class);
                    i.putExtras(b);
                    startActivity(i);
                }
                else{
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                }


                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
