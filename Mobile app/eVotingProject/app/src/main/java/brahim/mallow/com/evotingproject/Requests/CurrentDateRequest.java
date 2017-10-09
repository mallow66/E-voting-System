package brahim.mallow.com.evotingproject.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import brahim.mallow.com.evotingproject.SplashScreenActivity;

/**
 * Created by brahim on 07/01/17.
 */

public class CurrentDateRequest extends StringRequest {

    private static final String URL= SplashScreenActivity.DOMAINE+"Web_Service/currentDate/current_date.php";

    public CurrentDateRequest(Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);


    }
}
