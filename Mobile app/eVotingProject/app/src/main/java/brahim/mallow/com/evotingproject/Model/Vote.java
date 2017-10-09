package brahim.mallow.com.evotingproject.Model;

import android.app.Activity;
import android.net.wifi.WifiManager;

import java.util.Date;
import java.util.UUID;

import brahim.mallow.com.evotingproject.MainActivity;

/**
 * Created by brahim on 24/12/16.
 */

public class Vote {

    private Candidat candidat;
    private Election election;
    private int nbreVote;

    public Vote(){

    }

    public Vote(Candidat candidat, Election election, int nbreVote) {
        this.candidat = candidat;
        this.election = election;
        this.nbreVote = nbreVote;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    public int getNbreVote() {
        return nbreVote;
    }

    public void setNbreVote(int nbreVote) {
        this.nbreVote = nbreVote;
    }
}
