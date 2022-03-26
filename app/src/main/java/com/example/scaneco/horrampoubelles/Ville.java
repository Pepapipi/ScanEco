package com.example.scaneco.horrampoubelles;



public class Ville  {

    private String nom;
    private String codePostal;
    private String joursSelectifs;
    private String heuresSelectifs;
    private String joursMenagers;
    private String heuresMenagers;


    public String getNom() {
        return nom;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public String getHeuresMenagers() {
        return heuresMenagers;
    }

    public String getHeuresSelectifs() {
        return heuresSelectifs;
    }

    public String getJoursMenagers() {
        return joursMenagers;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getJoursSelectifs() {
        return joursSelectifs;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public void setHeuresMenagers(String heuresMenagers) {
        this.heuresMenagers = heuresMenagers;
    }

    public void setHeuresSelectifs(String heuresSelectifs) {
        this.heuresSelectifs = heuresSelectifs;
    }

    public void setJoursMenagers(String joursMenagers) {
        this.joursMenagers = joursMenagers;
    }

    public void setJoursSelectifs(String joursSelectifs) {
        this.joursSelectifs = joursSelectifs;
    }



}
