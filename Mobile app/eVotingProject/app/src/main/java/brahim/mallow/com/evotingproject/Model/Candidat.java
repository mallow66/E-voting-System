package brahim.mallow.com.evotingproject.Model;

import java.io.Serializable;

/**
 * Created by brahim on 24/12/16.
 */

public class Candidat extends Personne implements Serializable{
    public Candidat() {
    }



    public Candidat(String cin, String nom, String prenom, String adresseMail, String password) {
        super(cin, nom, prenom, adresseMail, password);
    }

    public Candidat(String cin, String nom, String prenom) {
        super(cin, nom, prenom);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
