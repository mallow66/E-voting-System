package brahim.mallow.com.evotingproject.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import brahim.mallow.com.evotingproject.Model.Personne;
import brahim.mallow.com.evotingproject.SplashScreenActivity;

/**
 * Created by brahim on 01/01/17.
 */

public class RegisterExistRequest extends StringRequest {


    public static  final String  REGISTER_EXIST_URL= SplashScreenActivity.DOMAINE+"Web_Service/Inscription/register_exist.php";
    public Map<String, String> params;


    public RegisterExistRequest(Personne personne,String code, Response.Listener<String> listener){
        super(Method.POST, REGISTER_EXIST_URL, listener, null);

        params = new HashMap<>();
        params.put("cin", personne.getCin());
        //params.put("email",personne.getAdresseMail());
        params.put("password", personne.getPassword());
        params.put("code", code);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
