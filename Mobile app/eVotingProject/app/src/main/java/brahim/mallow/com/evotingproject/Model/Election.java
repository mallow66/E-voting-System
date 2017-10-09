package brahim.mallow.com.evotingproject.Model;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by brahim on 24/12/16.
 */

public class Election  implements Serializable{

    private String idElection;
    private String nomElection;
    private Date dateDebut;
    private Date dateFin;
    private Admin adminElection;
    private boolean resultatDisponible;

    public Election(){

    }
    public Election(String idElection, String nomElection, String dateDebut, String dateFin) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");

        try {

            Date date = formatter.parse(dateDebut);
            this.dateDebut = date;

            date = formatter.parse(dateFin);
            this.dateFin = date;


        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.idElection = idElection;
        this.nomElection = nomElection;
    }

    public Election(String election, String nom_election, int date_debut, int date_fin) {

    }


    public boolean isResultatDisponible() {
        return resultatDisponible;
    }

    public void setResultatDisponible(boolean resultatDisponible) {
        this.resultatDisponible = resultatDisponible;
    }

    public Election(String idElection) {
        this.idElection = idElection;
    }

    public Election(String idElection, String nomElection, Date dateDebut, Date dateFin, Admin adminElection) {
        this.idElection = idElection;
        this.nomElection = nomElection;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.adminElection = adminElection;
    }

    public String getIdElection() {
        return idElection;
    }

    public void setIdElection(String idElection) {
        this.idElection = idElection;
    }

    public String getNomElection() {
        return nomElection;
    }

    public void setNomElection(String nomElection) {
        this.nomElection = nomElection;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Admin getAdminElection() {
        return adminElection;
    }

    public void setAdminElection(Admin adminElection) {
        this.adminElection = adminElection;
    }
}
