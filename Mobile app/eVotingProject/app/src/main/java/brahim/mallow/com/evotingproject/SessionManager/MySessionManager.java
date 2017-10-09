package brahim.mallow.com.evotingproject.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import brahim.mallow.com.evotingproject.Model.Candidat;
import brahim.mallow.com.evotingproject.Model.Electeur;
import brahim.mallow.com.evotingproject.Model.Personne;

/**
 * Created by brahim on 05/01/17.
 */

public class MySessionManager  {

    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private Context context;

    private static final String PREF_NAME = "eVotingApp";
    private static  final int PRIVATE_MODE = 0;
    private static final String CIN = "shared_cin";
    private static final String NOM = "shared_nom";
    private static final String PRENOM = "shared_prenom" ;
    private static final String IS_LOGIN = "is_login";
    private static final String TYPE_USER = "type_user";
    private static final String ELECTEUR ="electeur";
    private static final String CANDIDAT = "candidat";




    public MySessionManager(Context context){
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void  createSession(Personne personne){

        editor.putString(CIN, personne.getCin());
        editor.putString(NOM, personne.getNom());
        editor.putString(PRENOM, personne.getPrenom());
        editor.putBoolean(IS_LOGIN, true);
        if(personne instanceof Electeur)
            editor.putString(TYPE_USER,ELECTEUR);
        else
            editor.putString(TYPE_USER, CANDIDAT);
        editor.commit();
    }


    public Personne getUserDetails(){
        Personne user;
        if(sharedPreferences.getString(TYPE_USER, null).equals(ELECTEUR))
            user = new Electeur();
        else user = new Candidat();
        user.setCin(sharedPreferences.getString(CIN, null));
        user.setNom(sharedPreferences.getString(NOM, null));
        user.setPrenom(sharedPreferences.getString(PRENOM, null));
        return user;
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }
}
