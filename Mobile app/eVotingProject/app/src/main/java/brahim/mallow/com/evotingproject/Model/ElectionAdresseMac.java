package brahim.mallow.com.evotingproject.Model;

/**
 * Created by brahim on 31/12/16.
 */

public class ElectionAdresseMac {

    private Election election;
    private String adresseMac;


    public ElectionAdresseMac(Election election, String adresseMac) {
        this.election = election;
        this.adresseMac = adresseMac;
    }

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    public String getAdresseMac() {
        return adresseMac;
    }

    public void setAdresseMac(String adresseMac) {
        this.adresseMac = adresseMac;
    }
}
