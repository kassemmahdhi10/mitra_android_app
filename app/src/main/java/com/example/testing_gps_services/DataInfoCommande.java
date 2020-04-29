package com.example.testing_gps_services;

public class DataInfoCommande {

    private String nom;
    private String prenom;

    private String type;
    private int idCommande;







    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getType() {
        return type;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }



    public void setType(String type  ) {
        this.type = type;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }




    public DataInfoCommande(  String nom, String prenom,    int idCommande,String type) {
        this.nom = nom;
        this.prenom = prenom;

        this.idCommande=idCommande;
        this.type=type;

    }



    public String toString() {
        return "DataInfo{" +
                "nom=" + nom +
                ", prenom=" + prenom +
                ", id='" + idCommande + '\'' +
                ", type='" + type + '\'' +

                '}';
    }
}


