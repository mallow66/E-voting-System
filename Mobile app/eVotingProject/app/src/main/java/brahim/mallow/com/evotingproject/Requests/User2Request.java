package brahim.mallow.com.evotingproject.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import brahim.mallow.com.evotingproject.SplashScreenActivity;

/**
 * Created by brahim on 05/01/17.
 */

public class User2Request extends StringRequest {

    private static final String URL= SplashScreenActivity.DOMAINE+"Web_Service/userActivity/user_interface.php";
    private Map<String, String> params;

    public User2Request(String cin, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        params = new HashMap<>();
        params.put("cin", cin);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
