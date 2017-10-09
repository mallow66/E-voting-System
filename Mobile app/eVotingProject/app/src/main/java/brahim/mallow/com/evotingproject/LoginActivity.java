package brahim.mallow.com.evotingproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import brahim.mallow.com.evotingproject.Model.Candidat;
import brahim.mallow.com.evotingproject.Model.Electeur;
import brahim.mallow.com.evotingproject.Model.Personne;
import brahim.mallow.com.evotingproject.Requests.LoginRequest;
import brahim.mallow.com.evotingproject.Requests.TokenRequest;
import brahim.mallow.com.evotingproject.SessionManager.MySessionManager;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by brahim on 02/01/17.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button loginButton, registerButton;
    MySessionManager sessionManager;
    private Personne personne;
    private AlertDialog.Builder builder;
    private ProgressBar progressBar;


    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        builder = new AlertDialog.Builder(this);
        progressBar = (ProgressBar) findViewById(R.id.login_progressbar);
        progressBar.setVisibility(View.INVISIBLE);

        sessionManager = new MySessionManager(getApplicationContext());

        email = (EditText) findViewById(R.id.editText_login_email);
        password = (EditText) findViewById(R.id.editText_login_password);
        loginButton = (Button) findViewById(R.id.button_login);
        registerButton = (Button) findViewById(R.id.button_vers_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(LoginActivity.this, Register1Activity.class);
                startActivity(i);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean vide = email.getText().toString().trim().equals("") || password.getText().toString().trim().equals("");
                if(vide){
                    builder.setMessage("Veuillez remplir les champs")
                            .setNegativeButton("Rassayer",null)
                            .create()
                            .show();
                }
                final String md5Password = getMD5(password.getText().toString().trim());
                final String em = email.getText().toString().trim();
                progressBar.setVisibility(View.VISIBLE);


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressBar.setVisibility(View.GONE);

                            JSONObject jsonObject = new JSONObject(response);
                            boolean succes = jsonObject.getBoolean("succes");
                            if(succes){
                                String nom = jsonObject.getString("nom");
                                String prenom = jsonObject.getString("prenom");
                                String cin = jsonObject.getString("cin");
                                String type = jsonObject.getString("type");
                                if(type.equals("electeur")){
                                    personne = new Electeur(cin, nom, prenom, em, md5Password);
                                }
                                else{
                                    personne = new Candidat(cin, nom, prenom, em, md5Password);
                                }

                                Bundle b = new Bundle();
                                b.putSerializable("personne", personne);
                                Intent i = new Intent(LoginActivity.this, UserActivity.class);
                                sessionManager.createSession(personne);
                                i.putExtras(b);
                                //FirebaseMessaging.getInstance().subscribeToTopic("test");
                                //FirebaseInstanceId.getInstance().getToken();


                                Log.d("AAAAAAAAAAA !!!", "Juste avant");
                                Log.d("PERSONNNEE !!!! ", personne.getCin());

                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("HAHAHAHHA", "HAHAHAHAH");
                                    }
                                };
                                Log.d("TOKEN", FirebaseInstanceId.getInstance().getToken());
                                TokenRequest request = new TokenRequest(personne.getCin(), FirebaseInstanceId.getInstance().getToken(), responseListener);
                                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                                queue.add(request);

                                startActivity(i);
                                finish();


                            }
                            else{
                                builder.setMessage("Information erronée")
                                        .setNegativeButton("Rassayer",null)
                                        .create()
                                        .show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };
                Personne p = new Personne(em, md5Password);
                LoginRequest request = new LoginRequest(p, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(request);


            }
        });



    }


}
