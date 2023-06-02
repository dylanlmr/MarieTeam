package fr.xkgd.marieteam.database;

import java.sql.*;

/**
 * Classe permettant de se connecter à la base de donnée
 */
@SuppressWarnings("SpellCheckingInspection")
public class DbConnection {
    private static final DbConnection INSTANCE = new DbConnection();
    private Connection connection;
    private String dbName;
    private String dbHost;
    private String dbUser;
    private String dbPassword;

    /**
     * Constructeur de la classe JeuEnregistrement
     */
    private DbConnection() {
        dbName = "marieteam";
        dbHost = "localhost";
        dbUser = "root";
        dbPassword = "";
        startConnection();
    }

    /**
     * Getter de l'instance de DbConnection
     */
    public static DbConnection getInstance() {
        return INSTANCE;
    }

    /**
     * Méthode permettant de se connecter à la base de donnée
     */
    public void startConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":3306/" + dbName, dbUser, dbPassword);
        } catch (SQLException e) {
            System.out.println("Erreur : Impossible de se connecter à la base de donnée !");
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
