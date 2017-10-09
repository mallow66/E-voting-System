package brahim.mallow.com.evotingproject.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import brahim.mallow.com.evotingproject.SplashScreenActivity;

/**
 * Created by brahim on 31/12/16.
 */

public class Register1Request extends StringRequest {

    private static final String  REGISTER1_URL_REQUEST= SplashScreenActivity.DOMAINE+"Web_Service/Inscription/register1.php";

    private Map<String, String> params;

    public Register1Request(String cin, Response.Listener<String> listener){
        super(Method.POST, REGISTER1_URL_REQUEST, listener, null);

        params = new HashMap<>();
        params.put("cin", cin);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
