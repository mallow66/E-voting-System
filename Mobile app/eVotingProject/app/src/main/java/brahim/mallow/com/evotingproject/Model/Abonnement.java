package brahim.mallow.com.evotingproject.Model;

import java.io.Serializable;

/**
 * Created by brahim on 31/12/16.
 */

public class Abonnement implements Serializable {

    private Electeur electeur;
    private Election election;
    private boolean abonne;
    private boolean aVote;

    public Abonnement(Electeur electeur, Election election) {
        this.electeur = electeur;
        this.election = election;
    }

    public Abonnement(Electeur electeur, Election election, boolean abonne, boolean aVote) {
        this.electeur = electeur;
        this.election = election;
        this.abonne = abonne;
        this.aVote = aVote;
    }

    public Abonnement(Electeur personne, Election e, int abonne, int a_vote) {
        this.electeur = personne;
        this.election = e;
        this.abonne = (abonne == 1);
        this.aVote = (a_vote == 1);
    }

    public Electeur getElecteur() {
        return electeur;
    }

    public void setElecteur(Electeur electeur) {
        this.electeur = electeur;
    }

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    public boolean isAbonne() {
        return abonne;
    }

    public void setAbonne(boolean abonne) {
        this.abonne = abonne;
    }

    public boolean isaVote() {
        return aVote;
    }

    public void setaVote(boolean aVote) {
        this.aVote = aVote;
    }

    @Override
    public String toString() {
        return electeur.getPrenom()+" "+election.getNomElection();
    }
}
