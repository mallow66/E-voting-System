package brahim.mallow.com.evotingproject.Services;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

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
 * Created by brahim on 13/01/17.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

  //private MySessionManager sessionManager;
    @Override
    public void onTokenRefresh() {

        String token  = FirebaseInstanceId.getInstance().getToken();
        Log.d("TEEEEEEEEEEEEE!!!", "onRefresh");
        registerToken(token);
    }

    private void registerToken(String token) {
       // sessionManager = new MySessionManager(getApplicationContext());
        //  Personne p = sessionManager.getUserDetails();
      OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("token",token)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.25/Web_Service/notifications/register.php")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


    /*    Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean succes = jsonObject.getBoolean("succes");
                    if(succes){
                        Log.d("TOKEN !!!!!", "SUCCES");
                    }
                    else{
                        Log.d("TOKEN !! ! ", "FAILED");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        TokenRequest request = new TokenRequest("tk21277", token, responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

   */ }
}
