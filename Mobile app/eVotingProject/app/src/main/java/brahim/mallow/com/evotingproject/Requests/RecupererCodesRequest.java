package brahim.mallow.com.evotingproject.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import brahim.mallow.com.evotingproject.Model.Personne;
import brahim.mallow.com.evotingproject.SplashScreenActivity;

/**
 * Created by brahim on 23/01/17.
 */

public class RecupererCodesRequest extends StringRequest {

    private static final String URL=SplashScreenActivity.DOMAINE+"Web_Service/notifications/send_emails.php";
    private Map<String, String> params;

    public RecupererCodesRequest(Personne personne, Response.Listener listener){
        super(Method.POST, URL, listener, null);

        params = new HashMap<>();
        params.put("cin", personne.getCin());
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
