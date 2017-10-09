package brahim.mallow.com.evotingproject.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import brahim.mallow.com.evotingproject.SplashScreenActivity;

/**
 * Created by brahim on 05/01/17.
 */

public class VoterRequest extends StringRequest {

    private Map<String,String> params;
    private static final String URL= SplashScreenActivity.DOMAINE+"Web_Service/userActivity/voter_interface.php";


    public VoterRequest(String electeur, String election, String candidat, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        params = new HashMap<>();
        params.put("electeur", electeur);
        params.put("election", election);
        params.put("candidat", candidat);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }


}
