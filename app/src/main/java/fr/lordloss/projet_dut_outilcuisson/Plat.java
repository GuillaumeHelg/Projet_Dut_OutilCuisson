package fr.lordloss.projet_dut_outilcuisson;

import java.io.Serializable;
import java.util.Objects;

public class Plat implements Serializable {

    private String nom;
    private String duree;
    private int deg;

    public Plat(String nom, String duree, int deg) {
        this.nom = nom;
        this.duree = duree;
        this.deg = deg;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }


    public boolean equals(Plat o) {
        return this.nom.equals(o.getNom());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, duree, deg);
    }
}

