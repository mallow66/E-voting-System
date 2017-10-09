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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import brahim.mallow.com.evotingproject.Model.Personne;
import brahim.mallow.com.evotingproject.Requests.Register1Request;
import brahim.mallow.com.evotingproject.Requests.Register2Request;
import brahim.mallow.com.evotingproject.Requests.RegisterExistRequest;

/**
 * Created by brahim on 31/12/16.
 */

public class Register1Activity extends AppCompatActivity {

    private EditText cin, nom, prenom, email, password1, password2, codeEmailUser;
    private Button suivant, register;
    private boolean exist, actif;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        exist = false;
        actif = false;

        cin = (EditText) findViewById(R.id.cin_register1);
        suivant = (Button) findViewById(R.id.button_register1);
        nom = (EditText) findViewById(R.id.nom_register);
        prenom = (EditText) findViewById(R.id.prenom_register);
        email= (EditText) findViewById(R.id.email_register);
        password1 = (EditText) findViewById(R.id.password1_register);
        password2 = (EditText) findViewById(R.id.password2_register);
        register = (Button) findViewById(R.id.button_register2) ;
        codeEmailUser = (EditText) findViewById(R.id.code_email_register1);

        nom.setVisibility(View.INVISIBLE);
        prenom.setVisibility(View.INVISIBLE);
        email.setVisibility(View.INVISIBLE);
        password1.setVisibility(View.INVISIBLE);
        password2.setVisibility(View.INVISIBLE);
        register.setVisibility(View.INVISIBLE);
        codeEmailUser.setVisibility(View.INVISIBLE);

        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cin.getText().toString().trim().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register1Activity.this);
                    builder.setMessage("S'il vous plait Entrer un CIN")
                            .setNegativeButton("Rassayer",null)
                            .create()
                            .show();
                }

                else{
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                 exist = jsonResponse.getBoolean("exist");
                                 actif = jsonResponse.getBoolean("actif");
                                Log.d("MESSAGE", jsonResponse.getString("succes"));
                                if(exist)
                                Log.d("MESAAAGE !!!!!!!!","TRUE !!!!!!!!!!!!!!" );
                                else
                                    Log.d("MESAAAGE !!!!!!!!","FALSE !!!!!!!!!!!!!!" );

                                if(actif)
                                    Log.d("MESAAAGE !!!!!!!!","TRUE !!!!!!!!!!!!!!" );
                                else
                                    Log.d("MESAAAGE !!!!!!!!","FALSE !!!!!!!!!!!!!!" );





                                if(actif){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Register1Activity.this);
                                    builder.setMessage("Vous etes deja inscrit")
                                            .setNegativeButton("Rassayer",null)
                                            .create()
                                            .show();
                                }


                                else if(exist){
                                    codeEmailUser.setVisibility(View.VISIBLE);
                                    nom.setVisibility(View.VISIBLE);
                                    prenom.setVisibility(View.VISIBLE);
                                    email.setVisibility(View.VISIBLE);
                                    password1.setVisibility(View.VISIBLE);
                                    password2.setVisibility(View.VISIBLE);
                                    register.setVisibility(View.VISIBLE);
                                    suivant.setVisibility(View.INVISIBLE);

                                    Toast.makeText(getBaseContext(), "EXIST", Toast.LENGTH_LONG).show();
                                    String nomRecu= jsonResponse.getString("nom");
                                    String prenomRecu = jsonResponse.getString("prenom");
                                    String emailRecu = jsonResponse.getString("email");
                                    cin.setEnabled(false);

                                    nom.setText(nomRecu);
                                    nom.setEnabled(false);

                                    prenom.setText(prenomRecu);
                                    prenom.setEnabled(false);

                                    email.setText(emailRecu);
                                    email.setEnabled(false);



                                }

                                else{
                                    nom.setVisibility(View.VISIBLE);
                                    prenom.setVisibility(View.VISIBLE);
                                    email.setVisibility(View.VISIBLE);
                                    password1.setVisibility(View.VISIBLE);
                                    password2.setVisibility(View.VISIBLE);
                                    register.setVisibility(View.VISIBLE);
                                    suivant.setVisibility(View.INVISIBLE);


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    //Request
                    Register1Request request  = new Register1Request(cin.getText().toString().trim(), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Register1Activity.this);
                    queue.add(request);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean pass = password1.getText().toString().equals(password2.getText().toString());
                if(!pass){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register1Activity.this);
                    builder.setMessage("Verifier votre mot de passe ! ")
                            .setNegativeButton("Rassayer",null)
                            .create()
                            .show();
                }
                if(nom.getText().toString().trim().equals("") || prenom.getText().toString().trim().equals("") || email.getText().toString().trim().equals("") || password1.getText().toString().trim().equals("") || password2.getText().toString().trim().equals("") || codeEmailUser.getText().toString().trim().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register1Activity.this);
                    builder.setMessage("Veuillez remplir tout les champs")
                            .setNegativeButton("Rassayer",null)
                            .create()
                            .show();
                }
                //incription avant l'import di fichier excel
                if(!exist && !actif && pass ){
                    Personne p = new Personne(cin.getText().toString().trim(),nom.getText().toString().trim(), prenom.getText().toString().trim(), email.getText().toString().trim(),LoginActivity.getMD5(password1.getText().toString().trim()) );


                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject responseObject = new JSONObject(response);
                                boolean succes = responseObject.getBoolean("succes");
                                if(succes){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Register1Activity.this);
                                    builder.setMessage("Inscription Reussite")
                                            .setNegativeButton("Connect",null)
                                            .create()
                                            .show();
                                }

                                else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Register1Activity.this);
                                    builder.setMessage("Echec de l'inscription")
                                            .setNegativeButton("Rassayer",null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    Register2Request register2Request = new Register2Request(p, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Register1Activity.this);
                    queue.add(register2Request);
                }

                else if(exist && !actif && pass){
                    Personne p = new Personne(cin.getText().toString().trim(), nom.getText().toString().trim(), prenom.getText().toString().trim(), email.getText().toString().trim(), LoginActivity.getMD5(password1.getText().toString().trim()));
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean succes = jsonObject.getBoolean("succes");
                                if(succes){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Register1Activity.this);
                                    builder.setMessage("Inscription reussite")
                                            .create()
                                            .show();


                                }
                                else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Register1Activity.this);
                                    builder.setMessage("echec de l'inscription")
                                            .setNegativeButton("Rassayer",null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    };

                    RegisterExistRequest request = new RegisterExistRequest(p,codeEmailUser.getText().toString().trim(), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Register1Activity.this);
                    queue.add(request);
                }

                else if(actif){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register1Activity.this);
                    builder.setMessage("Vous etes deja insrit dans le systeme")
                            .setNegativeButton("Fermer",null)
                            .create()
                            .show();
                }


            }
        });


    }


}
