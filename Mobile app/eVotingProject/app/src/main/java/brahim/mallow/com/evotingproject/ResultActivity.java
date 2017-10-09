package brahim.mallow.com.evotingproject;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

import brahim.mallow.com.evotingproject.Model.Candidat;
import brahim.mallow.com.evotingproject.Model.Election;
import brahim.mallow.com.evotingproject.Model.Vote;
import brahim.mallow.com.evotingproject.Requests.ResultRequest;

/**
 * Created by brahim on 07/01/17.
 */

public class ResultActivity  extends AppCompatActivity{

    private ListView listViewResultat;
    private TextView resultInfo;
    private LinkedList<Vote> votes;
    private Election election;


    private class ResultAdapter extends ArrayAdapter<Vote>{

        public class ViewH{
            TextView nomPrenom;
            TextView nbreVote;
        }

        public ResultAdapter(Context context, LinkedList<Vote> votes){
            super(context, 0, votes);


        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewH viewHolder = new ViewH();
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.listview_result_item, null);

            }

            viewHolder.nomPrenom = (TextView) convertView.findViewById(R.id.electeur_textView_result);
            viewHolder.nbreVote = (TextView) convertView.findViewById(R.id.nbre_vote_textView);

            Vote v = getItem(position);
            viewHolder.nomPrenom.setText(v.getCandidat().getNom()+" "+v.getCandidat().getPrenom());
            viewHolder.nbreVote.setText(v.getNbreVote()+"");
            return convertView;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        votes = new LinkedList<>();
        listViewResultat = (ListView) findViewById(R.id.list_view_result);
        listViewResultat.setAdapter(new ResultAdapter(this, votes));
        election  = (Election)getIntent().getExtras().getSerializable("election");
        //election = (Election)getIntent().getSerializableExtra("election");
        Log.d("ELECTION ...", election.getNomElection());


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Vote vote = new Vote(new Candidat(null, jsonObject.getString("nom"), jsonObject.getString("prenom")), election, jsonObject.getInt("nbre_vote"));
                        Log.d("NBRE VOTE", vote.getNbreVote()+"");
                        ((ResultAdapter)listViewResultat.getAdapter()).add(vote);
                        Log.d("AAAAAAAAAA", "aaaaaaaa");
                    }
                    resultInfo.setText("Resultat des elections: "+election.getNomElection());
                    resultInfo.setTextColor(getResources().getColor(R.color.green_vote));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        ResultRequest request = new ResultRequest(election.getIdElection(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


        resultInfo = (TextView) findViewById(R.id.result_info_text_view);
        resultInfo.setText("Resultats indisponible");
        resultInfo.setTextColor(getResources().getColor(R.color.red_non_vote));



    }
}
