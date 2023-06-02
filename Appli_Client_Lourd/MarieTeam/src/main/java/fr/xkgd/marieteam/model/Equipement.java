package fr.xkgd.marieteam.model;

/**
 * Classe Equipement
 */
@SuppressWarnings("SpellCheckingInspection")
public class Equipement {

    private int idEquip;
    private String libEquip;

    /**
     * Constructeur de la classe Equipement
     * @param idEquip l'identifiant de l'équipement
     * @param libEquip le libellé de l'équipement
     */
    public Equipement(int idEquip, String libEquip) {
        this.idEquip = idEquip;
        this.libEquip = libEquip;
    }

    /**
     * Méthode toString de la classe Equipement
     * @return les informations de l'équipement
     */
    @Override
    public String toString() {
        return " - " + libEquip;
    }

    public int getIdEquip() {
        return idEquip;
    }

    public String getLibEquip() {
        return libEquip;
    }
}