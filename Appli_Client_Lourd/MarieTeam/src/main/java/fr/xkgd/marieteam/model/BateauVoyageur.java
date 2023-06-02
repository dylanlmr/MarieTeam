package fr.xkgd.marieteam.model;

import java.util.List;

import static fr.xkgd.marieteam.Utils.DF;
import static fr.xkgd.marieteam.Utils.LS;

/**
 * Classe BateauVoyageur
 */
@SuppressWarnings("SpellCheckingInspection")
public class BateauVoyageur extends Bateau {

    private int idBatVoy;
    private double vitesseBatVoy;
    private String imageBatVoy;
    private List<Equipement> lesEquipements;

    /**
     * Constructeur de la classe BateauVoyageur
     * @param nomBat le nom du bateau
     * @param longueurBat la longueur du bateau
     * @param largeurBat la largeur du bateau
     * @param vitesseBatVoy la vitesse du bateau
     * @param imageBatVoy l'image du bateau
     * @param lesEquipements la liste des équipements du bateau
     */
    public BateauVoyageur(int idBat, String nomBat, double longueurBat, double largeurBat, int idBatVoy, double vitesseBatVoy, String imageBatVoy, List<Equipement> lesEquipements) {
        super(idBat, nomBat, longueurBat, largeurBat);
        this.idBatVoy = idBatVoy;
        this.vitesseBatVoy = vitesseBatVoy;
        this.imageBatVoy = imageBatVoy;
        this.lesEquipements = lesEquipements;
    }

    /**
     * Méthode toString de la classe BateauVoyageur
     * @return les informations du bateau et de ses équipements
     */
    @Override
    public String toString() {
        return super.toString() +
                " - Vitesse : " + DF.format(vitesseBatVoy) + " noeuds" + LS +
                "Liste des equipements du bateau : " + LS +
                lesEquipements.toString()
                        .replace("[", "")
                        .replace("]", "")
                        .replace(", ", LS);
    }

    @Override
    public int getIdBat() {
        return super.getIdBat();
    }

    public int getIdBatVoy() {
        return idBatVoy;
    }

    public double getVitesseBatVoy() {
        return vitesseBatVoy;
    }

    public void setVitesseBatVoy(double vitesseBatVoy) {
        this.vitesseBatVoy = vitesseBatVoy;
    }

    public String getImageBatVoy() {
        return imageBatVoy;
    }

    public void setImageBatVoy(String imageBatVoy) {
        this.imageBatVoy = imageBatVoy;
    }

    public List<Equipement> getLesEquipements() {
        return lesEquipements;
    }

    public void setLesEquipements(List<Equipement> lesEquipements) {
        this.lesEquipements = lesEquipements;
    }
}
