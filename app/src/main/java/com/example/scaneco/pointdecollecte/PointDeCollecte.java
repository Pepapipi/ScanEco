package com.example.scaneco.pointdecollecte;

public class PointDeCollecte {
    private float longitude;
    private float latitude;
    private String type;
    private String adresse;
    private String ville;


    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getType() {
        return type;
    }

    public String getVille() {
        return ville;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
