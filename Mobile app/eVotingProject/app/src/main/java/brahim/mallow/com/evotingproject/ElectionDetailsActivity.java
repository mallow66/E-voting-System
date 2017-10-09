package brahim.mallow.com.evotingproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.test.suitebuilder.TestMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import brahim.mallow.com.evotingproject.Model.Abonnement;
import brahim.mallow.com.evotingproject.Model.Candidat;
import brahim.mallow.com.evotingproject.Model.Vote;
import brahim.mallow.com.evotingproject.Requests.CurrentDateRequest;
import brahim.mallow.com.evotingproject.Requests.ElectionDetailsRequest;
import brahim.mallow.com.evotingproject.Requests.VoterRequest;

/**
 * Created by brahim on 04/01/17.
 */

public class ElectionDetailsActivity extends AppCompatActivity {

    private Abonnement abonnement;
    private Vote vote;
    TextView electionnom;
    TextView electionInfo;
    TextView aVote;
    boolean electionActif;
    LinkedList<Candidat> candidats ;
    ListView listViewCandidats;
    int index;
    private Date currentDate, debut, fin;
    private CountDownTimer countDownTimer, debutDans;
    private TextView chronometer;

    private Button buttonResult;



    Response.Listener<String> responseListener;
    ElectionDetailsRequest request;
    RequestQueue queue;

    private class CandidatAdapter extends ArrayAdapter<Candidat>{

        public class ViewHolder{
            Button voterButton;
            TextView nomPrenom;
        }


        public CandidatAdapter(Context context, LinkedList<Candidat> candidats){
            super(context, 0,candidats );
        }

        @NonNull
        @Override
        public View getView( final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewHodler;
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.candidat_list_item,parent, false);
                 final ViewHolder viewHolder = new ViewHolder();
                Candidat c = getItem(position);
                viewHolder.nomPrenom = (TextView)convertView.findViewById(R.id.text_view_candidat_item);
                viewHolder.nomPrenom.setText(c.getNom()+" "+c.getPrenom());
                viewHolder.voterButton = (Button)convertView.findViewById(R.id.voter_button_item);
                if(abonnement.isaVote() || ! electionActif ){
                    viewHolder.voterButton.setEnabled(false);
                }
                viewHolder.voterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getBaseContext(), "Ca marche"+getItem(position).toString(), Toast.LENGTH_LONG).show();

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // informer que le vote est bien passé
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean  succes = jsonObject.getBoolean("succes");
                                    if(succes){
                                        Log.d("Message !!! ", "le vote est bien passé");
                                        abonnement.setaVote(true);
                                        //activity for result..
                                        viewHolder.voterButton.setEnabled(false);

                                        Intent data = new Intent();
                                        Bundle extra = new Bundle();
                                        extra.putSerializable("modifiedAbonnement", abonnement);
                                        extra.putInt("index",index);
                                        data.putExtras(extra);
                                        setResult(RESULT_OK, data);

                                        CandidatAdapter adapter = (CandidatAdapter)(listViewCandidats.getAdapter());
                                        adapter.clear();
                                        queue.add(request);
                                        adapter.notifyDataSetChanged();

                                    }
                                    else{
                                        Log.d("MESSAGE !! ", "Echec du vote");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }




                            }
                        };

                        VoterRequest request = new VoterRequest(abonnement.getElecteur().getCin(), abonnement.getElection().getIdElection(), getItem(position).getCin(), responseListener);
                        RequestQueue queue = Volley.newRequestQueue(ElectionDetailsActivity.this);
                        queue.add(request);
                    }
                });
                convertView.setTag(viewHolder);
            }

            else{
                mainViewHodler = (ViewHolder)convertView.getTag();
                Candidat c = getItem(position);
                mainViewHodler.nomPrenom.setText(c.getNom()+" "+c.getPrenom());

            }

            return convertView;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election_details);
        candidats = new LinkedList<>();
        electionnom = (TextView)findViewById(R.id.details_non_election);
        electionInfo = (TextView)findViewById(R.id.abonne_details);
        aVote = (TextView)findViewById(R.id.a_vote_details);
        chronometer = (TextView)findViewById(R.id.chrono_text_view);
        chronometer.setText("");
        listViewCandidats = (ListView)findViewById(R.id.candidats_list_view);
        buttonResult = (Button) findViewById(R.id.resultat_submit_button);



        Bundle b = getIntent().getExtras();
        abonnement = (Abonnement) b.getSerializable("abonnement");
        index = b.getInt("index");
        debut = abonnement.getElection().getDateDebut() ;
        fin = abonnement.getElection().getDateFin();


        Response.Listener<String> responseListener2= new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String s = jsonObject.getString("current_date");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
                    currentDate = formatter.parse(s) ;
                    Log.d("CURRENT DATE HERE  ! ", currentDate.toString());


                    if(currentDate.after(debut) && currentDate.before(fin)){
                        electionActif = true;
                        electionInfo.setText("L'election est active");
                        countDownTimer = new CountDownTimer(fin.getTime() - currentDate.getTime(), 1000){

                            @Override
                            public void onTick(long l) {
                                Date date = new Date(l);
                                DateFormat formatter = new SimpleDateFormat("+ dd HH:mm:ss:");
                                String dateFormatted = formatter.format(date);
                                chronometer.setText(dateFormatted);
                                chronometer.setTextColor(getResources().getColor(R.color.green_vote));
                                //Log.d("CHRONO", l+"");
                            }

                            @Override
                            public void onFinish() {
                                chronometer.setText("delais de vote expiré !");
                                chronometer.setTextColor(getResources().getColor(R.color.red_non_vote));
                                electionInfo.setText("Election exprirée");
                                electionActif = false;
                                CandidatAdapter adapter = (CandidatAdapter)(listViewCandidats.getAdapter());
                                adapter.clear();
                               // adapter.notifyDataSetChanged();
                                queue.add(request);
                                adapter.notifyDataSetChanged();


                            }
                        }.start();
                    }
                    else{
                        if(currentDate.before(debut)){
                            electionActif = false;
                            debutDans = new CountDownTimer(debut.getTime() - currentDate.getTime(), 1000) {
                                @Override
                                public void onTick(long l) {
                                    DateFormat formatter = new SimpleDateFormat("+ dd HH:mm:ss:");
                                    Date date = new Date(l);
                                    String dateFormatted = formatter.format(date);
                                    electionInfo.setText("l'election n a pas encore commencé, il commencera dans :"+dateFormatted);
                                    electionInfo.setTextColor(getResources().getColor(R.color.green_vote));
                                }

                                @Override
                                public void onFinish() {
                                    electionInfo.setText("l'election est actif maintenant !");
                                    CandidatAdapter adapter = (CandidatAdapter)(listViewCandidats.getAdapter());
                                    electionActif = true;
                                    adapter.clear();
                                   // adapter.notifyDataSetChanged();
                                    queue.add(request);
                                    adapter.notifyDataSetChanged();




                                }
                            }.start();

                        }
                        else{
                            electionActif = false;
                            electionInfo.setText("le delais de vote est expiré");
                        }

                    }

                    if(abonnement.isaVote()){
                        aVote.setText("Vous avez déja voté pour cette election");
                    }
                    else{
                        if(electionActif){
                            aVote.setText("s'il vous plait, votez pour candidat si dessus");

                        }

                        else{
                            aVote.setText("Attendez jusqu'a ce que l'election soit valide pour voter !");
                        }

                    }
                    electionnom.setText(abonnement.getElection().getNomElection());




                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        };
        CurrentDateRequest request2 = new CurrentDateRequest(responseListener2);
        //RequestQueue queue2 = Volley.newRequestQueue(this);
        //queue2.add(request2);







        Log.d("LA DATE  ::: ", abonnement.getElection().getDateDebut().toString());







         responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Candidat cc = new Candidat(jsonObject.getString("candidat"), jsonObject.getString("nom"), jsonObject.getString("prenom"));
                        //candidats.add(cc);
                        ((CandidatAdapter)listViewCandidats.getAdapter()).add(cc);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
         request = new ElectionDetailsRequest(abonnement.getElection().getIdElection(), responseListener);
        queue = Volley.newRequestQueue(ElectionDetailsActivity.this);
       queue.add(request2);
        queue.add(request);




        listViewCandidats.setAdapter(new CandidatAdapter(this, candidats));

        buttonResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ElectionDetailsActivity.this, ResultActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("election", abonnement.getElection());
                i.putExtras(b);
                startActivity(i);
            }
        });

    }
}
