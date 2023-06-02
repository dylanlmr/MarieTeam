package fr.xkgd.marieteam.model;

import static fr.xkgd.marieteam.Utils.DF;
import static fr.xkgd.marieteam.Utils.LS;

/**
 * Classe Bateau
 */
@SuppressWarnings("SpellCheckingInspection")
public class Bateau {

    private int idBat;
    private String nomBat;
    private double longueurBat;
    private double largeurBat;

    /**
     * Constructeur de la classe Bateau
     * @param nomBat le nom du bateau
     * @param longueurBat la longueur du bateau
     * @param largeurBat la largeur du bateau
     */
    public Bateau(String nomBat, double longueurBat, double largeurBat) {
        this.nomBat = nomBat;
        this.longueurBat = longueurBat;
        this.largeurBat = largeurBat;
    }

    /**
     * Constructeur de la classe Bateau avec l'id du bateau
     * @param idBat l'id du bateau
     * @param nomBat le nom du bateau
     * @param longueurBat la longueur du bateau
     * @param largeurBat la largeur du bateau
     */
    public Bateau(int idBat, String nomBat, double longueurBat, double largeurBat) {
        this.idBat = idBat;
        this.nomBat = nomBat;
        this.longueurBat = longueurBat;
        this.largeurBat = largeurBat;
    }

    /**
     * Méthode toString() de la classe Bateau
     * @return les informations du bateau
     */
    @Override
    public String toString() {
        return "Nom du bateau : " + nomBat + LS +
                " - Longueur : " + DF.format(longueurBat) + " mètres" + LS +
                " - Largeur : " + DF.format(largeurBat) + " mètres" + LS;
    }

    public int getIdBat() {
        return idBat;
    }

    public String getNomBat() {
        return nomBat;
    }

    public double getLongueurBat() {
        return longueurBat;
    }

    public double getLargeurBat() {
        return largeurBat;
    }
}
