package brahim.mallow.com.evotingproject.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import brahim.mallow.com.evotingproject.Model.Personne;
import brahim.mallow.com.evotingproject.SplashScreenActivity;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by brahim on 25/12/16.
 */

public class LoginRequest extends StringRequest {
    private static final String  LOGIN_URL_REQUEST= SplashScreenActivity.DOMAINE+"Web_Service/Login/login.php";

    private Map<String, String> params;

    public LoginRequest(Personne personne, Response.Listener listener){
        super(Method.POST, LOGIN_URL_REQUEST, listener, null);

        params = new HashMap<>();
        params.put("email", personne.getAdresseMail());
        params.put("password", personne.getPassword());


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
