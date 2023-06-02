package fr.xkgd.marieteam.bateau;

import fr.xkgd.marieteam.database.DbConnection;
import fr.xkgd.marieteam.database.JeuEnregistrement;
import fr.xkgd.marieteam.model.BateauVoyageur;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static fr.xkgd.marieteam.Utils.DF;
import static fr.xkgd.marieteam.Utils.LS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("SpellCheckingInspection")
class BateauVoyageurTest {

    private static JeuEnregistrement jeuEnregistrement;

    /**
     * Méthode exécutée avant tous les tests
     * Création de la connexion à la base de données
     * Création de l'objet JeuEnregistrement pour effectuer les requêtes
     */
    @BeforeAll
    static void setUp() {
        jeuEnregistrement = JeuEnregistrement.getInstance();
    }

    /**
     * Test de la méthode toString() de la classe BateauVoyageur
     * sur les bateaux voyageurs en base de données
     */
    @Test
    void testToStringSurLesBateauxVoyageursEnBaseDeDonnee() {
        List<BateauVoyageur> bateauxVoyageurs = jeuEnregistrement.chargerLesBateauxVoyageurs();
        for (BateauVoyageur bateauVoyageur : bateauxVoyageurs) {
            assertEquals("Nom du bateau : " + bateauVoyageur.getNomBat() + LS +
                            " - Longueur : " + DF.format(bateauVoyageur.getLongueurBat()) + " mètres" + LS +
                            " - Largeur : " + DF.format(bateauVoyageur.getLargeurBat()) + " mètres" + LS +
                            " - Vitesse : " + DF.format(bateauVoyageur.getVitesseBatVoy()) + " noeuds" + LS +
                            "Liste des equipements du bateau : " + LS +
                            bateauVoyageur.getLesEquipements().toString()
                                    .replace("[", "")
                                    .replace("]", "")
                                    .replace(", ", LS),
                    bateauVoyageur.toString());
        }
    }
}