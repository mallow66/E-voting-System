package brahim.mallow.com.evotingproject.Model;

import java.io.Serializable;

/**
 * Created by brahim on 24/12/16.
 */

public class Admin extends Personne implements Serializable {

    public Admin(String cin, String nom, String prenom, String adresseMail, String password) {
        super(cin, nom, prenom, adresseMail, password);
    }

    public Admin() {
    }
}
