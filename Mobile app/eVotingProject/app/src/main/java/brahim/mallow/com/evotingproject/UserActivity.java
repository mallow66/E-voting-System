package brahim.mallow.com.evotingproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

import brahim.mallow.com.evotingproject.Model.Abonnement;
import brahim.mallow.com.evotingproject.Model.Electeur;
import brahim.mallow.com.evotingproject.Model.Election;
import brahim.mallow.com.evotingproject.Model.Personne;
import brahim.mallow.com.evotingproject.Requests.ListViewRequest;
import brahim.mallow.com.evotingproject.Requests.User2Request;

/**
 * Created by brahim on 01/01/17.
 */

public class UserActivity extends AppCompatActivity {

    private Personne personne;
    private ListView listView;
    private Button subscribeButton;

    private TextView nomPrenomTextView;

    private LinkedList<Abonnement> abonnements;

    Response.Listener<String> responseListener;
    RequestQueue queue;
    User2Request request;


    private class MyAdapter extends ArrayAdapter<Abonnement>{

        public MyAdapter(Context context, LinkedList<Abonnement> abonnements){
            super(context, 0, abonnements);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.listview_elections_item, null);
            }

            TextView election, vote, resultat;
            Abonnement abonnement = getItem(position);
            election = (TextView)convertView.findViewById(R.id.list_item_nom_election);
            vote = (TextView)convertView.findViewById(R.id.list_item_vote_ou_non);
            resultat = (TextView)convertView.findViewById(R.id.list_item_resultat_ou_non);
            resultat.setText("");
            election.setText(abonnement.getElection().getNomElection());
            if(abonnement.isaVote()){
                vote.setText("Vous avez voté ");
                vote.setTextColor(getResources().getColor(R.color.green_vote));
            }
            else{
                vote.setText("Vous avez pas encore voté ");
                vote.setTextColor(getResources().getColor(R.color.red_non_vote));

            }

            if(abonnement.getElection().isResultatDisponible()){
                resultat.setText("Resultat Disponible");
            }



            return convertView;
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        listView = (ListView) findViewById(R.id.list_view_elections);

        abonnements = new LinkedList<>();
        listView.setAdapter(new MyAdapter(this, abonnements));

        Bundle b = getIntent().getExtras();
        personne = (Personne) b.getSerializable("personne");
        Log.d("MESSAGE !!!!!!!!!!", personne.toString());

        nomPrenomTextView = (TextView)findViewById(R.id.textView_nom_prenom);
        nomPrenomTextView.setText(personne.getNom()+" "+personne.getPrenom());

        subscribeButton = (Button) findViewById(R.id.button_subsribe);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();
                b.putSerializable("personne", personne);

                Intent i = new Intent(UserActivity.this, NewElectionActivity.class);
                i.putExtras(b);
                startActivityForResult(i, 2);
            }
        });



         responseListener  = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length(); i++){
                        try{
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Election e = new Election(jsonObject.getString("election"), jsonObject.getString("nom_election"), jsonObject.getString("date_debut"), jsonObject.getString("date_fin"));
                            Abonnement a = new Abonnement( (Electeur) personne, e, jsonObject.getInt("abonne"), jsonObject.getInt("a_vote"));
                            Log.d("MESSAGE", a.toString());
                            //abonnements.add(a);
                            ((MyAdapter)listView.getAdapter()).add(a);

                        }
                        catch(JSONException e){
                            e.printStackTrace();
                        }

                    }
                } catch (JSONException ee) {
                    ee.printStackTrace();
                }
            }
        };
         request = new User2Request(personne.getCin(), responseListener);
         queue = Volley.newRequestQueue(UserActivity.this);
        queue.add(request);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Abonnement abonnement = (Abonnement) adapterView.getAdapter().getItem(i);

                Bundle b = new Bundle();
                b.putSerializable("abonnement", abonnement);
                b.putInt("index", i);
                Intent intent = new Intent(UserActivity.this, ElectionDetailsActivity.class);
                intent.putExtras(b);
                startActivityForResult(intent, 1);

            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){

            Log.d("MESSAGE !!! ", "ACTIVITY RESULT HERE  !! ");
            if(resultCode == RESULT_OK){
                //succesful vote
                Bundle b = data.getExtras();
                abonnements.set(b.getInt("index"), (Abonnement) b.getSerializable("modifiedAbonnement"));
                MyAdapter ad =  ((MyAdapter)listView.getAdapter());
                Abonnement a = ad.getItem(b.getInt("index"));
                ad.remove(a);
                ad.add((Abonnement) b.getSerializable("modifiedAbonnement"));

            }
        }

        if(requestCode == 2){

            if(resultCode == RESULT_OK){
                MyAdapter ad =  ((MyAdapter)listView.getAdapter());
                ad.clear();
                queue.add(request);
                ad.notifyDataSetChanged();
            }

        }
    }
}
