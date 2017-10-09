package brahim.mallow.com.evotingproject.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by brahim on 26/01/17.
 */

public class TokenRequest extends StringRequest {

    private static final String URL="http://192.168.1.25/Web_Service/notifications/register2.php";
    private Map<String, String> params;

    public TokenRequest(String cin, String token, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        params = new HashMap<>();
        params.put("cin", cin);
        params.put("token", token);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
