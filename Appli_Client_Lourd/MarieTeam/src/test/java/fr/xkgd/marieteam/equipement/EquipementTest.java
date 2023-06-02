package fr.xkgd.marieteam.equipement;

import fr.xkgd.marieteam.database.DbConnection;
import fr.xkgd.marieteam.database.JeuEnregistrement;
import fr.xkgd.marieteam.model.Equipement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EquipementTest {

    private static Connection connection;
    private static JeuEnregistrement jeuEnregistrement;

    @BeforeAll
    static void setUp() {
        DbConnection dbConnection = new DbConnection("marieteamm", "localhost", "root", "");
        dbConnection.startConnection();
        connection = dbConnection.getConnection();
        jeuEnregistrement = new JeuEnregistrement(connection);
    }

    @Test
    void testToStringSurLesEquipementsEnBaseDeDonnee() {
        for (Equipement equipement : chargerLesEquipements()) {
            assertEquals(" - " + equipement.getLibEquip(),
                    equipement.toString());
        }
    }

    private List<Equipement> chargerLesEquipements() {
        List<Equipement> equipements = new ArrayList<>();

        try {
            String sql = "SELECT * FROM equipement";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                equipements.add(new Equipement(
                        rs.getInt("id"),
                        rs.getString("libelle")
                ));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la connexion à la base de données : " + ex.getMessage());
        }
        return equipements;
    }
}