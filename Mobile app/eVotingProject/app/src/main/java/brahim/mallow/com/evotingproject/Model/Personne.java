package brahim.mallow.com.evotingproject.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by brahim on 24/12/16.
 */

public class Personne implements Serializable {

    private String cin;
    private String nom;
    private String prenom;
    private String adresseMail;
    private String password;


    public Personne(String cin) {
        this.cin = cin;
    }




    public Personne(String adresseMail, String password) {
        this.adresseMail = adresseMail;
        this.password = password;
    }



    public Personne(String cin, String nom, String prenom, String adresseMail, String password) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.adresseMail = adresseMail;
        this.password = password;
    }
    public Personne(){

    }

    public Personne(String cin, String nom, String prenom) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "Personne{" +
                "cin='" + cin + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresseMail='" + adresseMail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
