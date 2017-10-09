package brahim.mallow.com.evotingproject.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brahim.mallow.com.evotingproject.Model.Abonnement;
import brahim.mallow.com.evotingproject.SplashScreenActivity;

/**
 * Created by brahim on 01/01/17.
 */

public class SubsribeNewElectionRequest extends StringRequest {

    public static final String NEW_ELECTION_SUBSRIBE_URL = SplashScreenActivity.DOMAINE+"Web_Service/SubsribeNewElection/new_election.php";
    private Map<String, String> params;

    public SubsribeNewElectionRequest(Abonnement abonnement, Response.Listener listener){
        super(Method.POST, NEW_ELECTION_SUBSRIBE_URL, listener, null);

        params = new HashMap<>();
        params.put("cin", abonnement.getElecteur().getCin());
        params.put("code_election", abonnement.getElection().getIdElection());

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
