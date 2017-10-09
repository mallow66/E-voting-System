package brahim.mallow.com.evotingproject.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import brahim.mallow.com.evotingproject.SplashScreenActivity;

/**
 * Created by brahim on 07/01/17.
 */

public class ResultRequest extends StringRequest {

    private static final String URL = SplashScreenActivity.DOMAINE+"Web_Service/resultElection/result_election.php";
    Map<String, String> params;


    public ResultRequest(String codeElection, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        params = new HashMap<>();
        params.put("code_election", codeElection);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
