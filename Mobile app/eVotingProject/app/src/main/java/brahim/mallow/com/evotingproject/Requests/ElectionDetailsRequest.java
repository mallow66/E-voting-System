package brahim.mallow.com.evotingproject.Requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import brahim.mallow.com.evotingproject.SplashScreenActivity;

/**
 * Created by brahim on 04/01/17.
 */

public class ElectionDetailsRequest extends StringRequest{

    public static final String URL= SplashScreenActivity.DOMAINE+"Web_Service/userActivity/election_details_interface.php";
    private Map<String, String> params;

    public ElectionDetailsRequest(String codeElection, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);

        params = new HashMap<>();
        params.put("code_election", codeElection);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
