package fr.xkgd.marieteam.bateau;

import fr.xkgd.marieteam.database.DbConnection;
import fr.xkgd.marieteam.model.Bateau;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static fr.xkgd.marieteam.Utils.DF;
import static fr.xkgd.marieteam.Utils.LS;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BateauTest {

    private static Connection connection;

    /**
     * Méthode exécutée avant tous les tests
     * Création de la connexion à la base de données
     */
    @BeforeAll
    static void setUp() {
        connection = DbConnection.getInstance().getConnection();
    }

    /**
     * Test de la méthode toString() de la classe Bateau sur les bateaux en base de données
     */
    @Test
    void testToStringSurLesBateauxEnBaseDeDonnee() {
        List<Bateau> bateaux = chargerLesBateaux();
        for (Bateau bateau : bateaux) {
            assertEquals("Nom du bateau : " + bateau.getNomBat() + LS +
                            " - Longueur : " + DF.format(bateau.getLongueurBat()) + " mètres" + LS +
                            " - Largeur : " + DF.format(bateau.getLargeurBat()) + " mètres" + LS,
                    bateau.toString());
        }
    }

    private List<Bateau> chargerLesBateaux() {
        List<Bateau> bateaux = new ArrayList<>();

        try {
            String sql = "SELECT * FROM bateau";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bateaux.add(new Bateau(
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
}