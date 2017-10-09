package brahim.mallow.com.evotingproject;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import brahim.mallow.com.evotingproject.Model.Abonnement;
import brahim.mallow.com.evotingproject.Model.Electeur;
import brahim.mallow.com.evotingproject.Model.Election;
import brahim.mallow.com.evotingproject.Model.Personne;
import brahim.mallow.com.evotingproject.Requests.RecupererCodesRequest;
import brahim.mallow.com.evotingproject.Requests.SubsribeNewElectionRequest;

/**
 * Created by brahim on 01/01/17.
 */

public class NewElectionActivity extends AppCompatActivity {

    private Personne personne;
    private EditText editTextCodeElection;
    private Button subscribeCodeElection, recupererCodesButton;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_election);
        builder = new AlertDialog.Builder(NewElectionActivity.this);

        Bundle b = getIntent().getExtras();
        personne =(Personne) b.getSerializable("personne");
        editTextCodeElection = (EditText)findViewById(R.id.editText_code);
        subscribeCodeElection = (Button)findViewById(R.id.button_code_election);
        recupererCodesButton = (Button) findViewById(R.id.recuperer_codes_button);


        subscribeCodeElection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean vide = editTextCodeElection.getText().toString().trim().equals("");
                if(vide){
                    builder.setMessage("Veuillez remplir le champ du code d'election")
                            .setNegativeButton("Rassayer",null)
                            .create()
                            .show();
                }

                else{
                    Election e = new Election(editTextCodeElection.getText().toString().trim());
                    Electeur el =(Electeur)personne;
                    Abonnement a = new Abonnement(el, e);

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean succes = jsonObject.getBoolean("succes");
                                String info = jsonObject.getString("info");
                                if(succes){
                                    builder.setMessage("Inscription reussite a l'election")
                                            .setNegativeButton("Fermer",null)
                                            .create()
                                            .show();
                                    setResult(RESULT_OK, null);
                                }
                                else{
                                    builder.setMessage(info)
                                            .setNegativeButton("Rassayer",null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    };


                    SubsribeNewElectionRequest request = new SubsribeNewElectionRequest(a, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(NewElectionActivity.this);
                    queue.add(request);
                }


            }
        });


        recupererCodesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean succes = jsonObject.getBoolean("succes");
                            if(succes){
                                AlertDialog.Builder builder = new AlertDialog.Builder(NewElectionActivity.this);
                                builder.setMessage("Veuillez consulter votre boite email")
                                        .setNegativeButton("Fermer",null)
                                        .create()
                                        .show();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(NewElectionActivity.this);
                                builder.setMessage("Un probleme est survenu, veuillez ressayer ")
                                        .setNegativeButton("Ressayer",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                RecupererCodesRequest request  = new RecupererCodesRequest(personne, responseListener);
                RequestQueue queue = Volley.newRequestQueue(NewElectionActivity.this);
                queue.add(request);
            }
        });

    }
}
