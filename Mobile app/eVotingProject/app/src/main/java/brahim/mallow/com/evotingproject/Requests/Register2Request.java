package brahim.mallow.com.evotingproject.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import brahim.mallow.com.evotingproject.Model.Personne;
import brahim.mallow.com.evotingproject.SplashScreenActivity;

/**
 * Created by brahim on 31/12/16.
 */

public class Register2Request extends StringRequest {

    private static final String  REGISTER2_URL_REQUEST= SplashScreenActivity.DOMAINE+"Web_Service/Inscription/normal_registration.php";

    private Map<String, String> params;

    public Register2Request(Personne p , Response.Listener listener){
        super(Method.POST, REGISTER2_URL_REQUEST, listener, null);

        params = new HashMap<>();
        params.put("cin", p.getCin());
        params.put("nom", p.getNom());
        params.put("prenom", p.getPrenom());
        params.put("email", p.getAdresseMail());
        params.put("password", p.getPassword());
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
