package fr.xkgd.marieteam.database;

import fr.xkgd.marieteam.model.Bateau;
import fr.xkgd.marieteam.model.BateauVoyageur;
import fr.xkgd.marieteam.model.Equipement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui gère les enregistrements de la base de données
 */
@SuppressWarnings("SpellCheckingInspection")
public class JeuEnregistrement {
    private Connection connection;
    private static JeuEnregistrement INSTANCE;

    /**
     * Constructeur de la classe. Positionne le curseur sur le premier enregistrement.
     */
    private JeuEnregistrement() {
        connection = DbConnection.getInstance().getConnection();
    }

    public static synchronized JeuEnregistrement getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JeuEnregistrement();
        }
        return INSTANCE;
    }

    /**
     * Méthode qui charge les bateaux voyageurs
     * @return la liste des bateaux voyageurs
     */
    public List<BateauVoyageur> chargerLesBateauxVoyageurs() {
        List<BateauVoyageur> bateauxVoyageurs = new ArrayList<>();

        try {
            String sql = "SELECT b.id, b.nom, b.longueur, b.largeur, bv.id, bv.vitesse, bv.img FROM bateauvoyageur bv INNER JOIN bateau b ON bv.id_bateau = b.id";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bateauxVoyageurs.add(new BateauVoyageur(
                        rs.getInt("b.id"),
                        rs.getString("nom"),
                        rs.getFloat("longueur"),
                        rs.getFloat("largeur"),
                        rs.getInt("bv.id"),
                        rs.getFloat("vitesse"),
                        rs.getString("img"),
                        chargerLesEquipements(rs.getInt("bv.id"))
                ));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la connexion à la base de données : " + ex.getMessage());
        }
        return bateauxVoyageurs;
    }

    /**
     * Méthode qui charge les équipements d'un bateau voyageur
     * @param idBateauVoyageur l'id du bateau voyageur
     * @return la liste des équipements du bateau voyageur
     */
    public List<Equipement> chargerLesEquipements(int idBateauVoyageur) {
        List<Equipement> equipements = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM equipement e INNER JOIN bateauvoyageur_equipement bve ON e.id = bve.id_equipement WHERE bve.id_bateauvoyageur = ?");
            stmt.setInt(1, idBateauVoyageur);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                equipements.add(new Equipement(
                        rs.getInt("id"),
                        rs.getString("libelle")));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la connexion à la base de données : " + ex.getMessage());
        }
        return equipements;
    }

    /**
     * Méthode qui charge tous les équipements
     * @return la liste de tous les équipements
     */
    public List<Equipement> chargerTousLesEquipements() {
        List<Equipement> equipements = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM equipement");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                equipements.add(new Equipement(
                        rs.getInt("id"),
                        rs.getString("libelle")));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la connexion à la base de données : " + ex.getMessage());
        }
        return equipements;
    }

    /**
     * Méthode qui met à jour un bateau voyageur dans la base de données
     * @param bateauVoyageur l'id du bateau voyageur
     */
    public void updateBateauVoyageur(BateauVoyageur bateauVoyageur) {
        try {
            //vitesse, image
            PreparedStatement stmt = connection.prepareStatement("UPDATE bateauvoyageur SET vitesse = ?, img = ? WHERE id = ?");
            stmt.setFloat(1, (float) bateauVoyageur.getVitesseBatVoy());
            stmt.setString(2, bateauVoyageur.getImageBatVoy());
            stmt.setInt(3, bateauVoyageur.getIdBatVoy());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la connexion à la base de données (calle la mgl) : " + ex.getMessage());
        }
    }

    /**
     * Méthode qui ajoute un équipement à un bateau voyageur dans la base de données
     * @param bateauVoyageur l'id du bateau voyageur
     */
    public void insertBateauVoyageurEquipement(BateauVoyageur bateauVoyageur, List<Equipement> equipements) {
        try {
            for (Equipement equipement : equipements) {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO bateauvoyageur_equipement (id_bateauvoyageur, id_equipement) VALUES (?, ?)");
                stmt.setInt(1, bateauVoyageur.getIdBatVoy());
                stmt.setInt(2, equipement.getIdEquip());
                stmt.executeUpdate();

                bateauVoyageur.getLesEquipements().add(equipement);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la connexion à la base de données : " + ex.getMessage());
        }
    }

    /**
     * Méthode qui supprime un bateau voyageur dans la base de données
     * @param bateauVoyageur l'id du bateau voyageur
     */
    public void deleteBateauVoyageur(BateauVoyageur bateauVoyageur) {
        deleteAllBateauVoyageurEquipements(bateauVoyageur);
        deleteBateauVoyageurTraversees(bateauVoyageur);
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM bateauvoyageur WHERE id = ?");
            stmt.setInt(1, bateauVoyageur.getIdBatVoy());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la connexion à la base de données : " + ex.getMessage());
        }
    }

    private void deleteBateauVoyageurTraversees(BateauVoyageur bateauVoyageur) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM traversee WHERE id_bateauvoyageur = ?");
            stmt.setInt(1, bateauVoyageur.getIdBatVoy());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la connexion à la base de données : " + ex.getMessage());
        }
    }

    /**
     * Méthode qui supprime tous les équipements d'un bateau voyageur dans la base de données
     * @param bateauVoyageur l'id du bateau voyageur
     */
    public void deleteAllBateauVoyageurEquipements(BateauVoyageur bateauVoyageur) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM bateauvoyageur_equipement WHERE id_bateauvoyageur = ?");
            stmt.setInt(1, bateauVoyageur.getIdBatVoy());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la connexion à la base de données (celle que je soupçonne) : " + ex.getMessage());
        }
    }

    public List<Bateau> chargerTousLesBateaux() {
        List<Bateau> bateaux = new ArrayList<>();

        try {
            String sql = "SELECT * FROM bateau";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bateaux.add(new Bateau(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getFloat("longueur"),
                        rs.getFloat("largeur")
                ));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la connexion à la base de données : " + ex.getMessage());
        }
        return bateaux;
    }

    public Bateau chargerBateau(String nom) {
        Bateau bateau = null;

        try {
            String sql = "SELECT * FROM bateau WHERE nom = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bateau = new Bateau(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getFloat("longueur"),
                        rs.getFloat("largeur")
                );
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la connexion à la base de données : " + ex.getMessage());
        }
        return bateau;
    }

    public void createBateauVoyageur(int idBat, double vitesse, String image) {
        try {
            String sql = "INSERT INTO bateauvoyageur (id_bateau, vitesse, img) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idBat);
            stmt.setFloat(2, (float) vitesse);
            stmt.setString(3, image);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la connexion à la base de données : " + ex.getMessage());
        }
    }

    public BateauVoyageur getLastCreatedBateauVoyageur() {
        BateauVoyageur bateauVoyageur = null;

        try {
            String sql = "SELECT * FROM bateauvoyageur bv JOIN bateau b ON bv.id_bateau = b.id ORDER BY bv.id DESC LIMIT 1";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bateauVoyageur = new BateauVoyageur(
                        rs.getInt("b.id"),
                        rs.getString("b.nom"),
                        rs.getFloat("b.longueur"),
                        rs.getFloat("b.largeur"),
                        rs.getInt("bv.id"),
                        rs.getFloat("bv.vitesse"),
                        rs.getString("bv.img"),
                        new ArrayList<>());
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la connexion à la base de données : " + ex.getMessage());
        }
        return bateauVoyageur;
    }
}
