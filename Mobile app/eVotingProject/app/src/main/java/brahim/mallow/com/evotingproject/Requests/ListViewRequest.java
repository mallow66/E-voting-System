package brahim.mallow.com.evotingproject.Requests;

import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import brahim.mallow.com.evotingproject.Model.Personne;
import brahim.mallow.com.evotingproject.SplashScreenActivity;

/**
 * Created by brahim on 02/01/17.
 */

public class ListViewRequest extends JsonArrayRequest {

    public static final String URL = SplashScreenActivity.DOMAINE+"Web_Service/userActivity/user_interface.php";

    public Map<String, String > params;


    public ListViewRequest(Response.Listener<JSONArray> listener, Response.ErrorListener errorListener){
        super(Method.POST,URL,null,listener, errorListener);



    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        params = new HashMap<>();
        params.put("cin", "testtesttest");
        return params;
    }


    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONArray(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

}
