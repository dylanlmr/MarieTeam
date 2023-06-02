package fr.xkgd.marieteam;

import java.text.DecimalFormat;

/**
 * Classe permettant de stocker des méthodes utiles
 */
@SuppressWarnings("SpellCheckingInspection")
public class Utils {

    /**
     * Méthode permettant de séparer les lignes
     */
    public static final String LS = System.lineSeparator();

    /**
     * Méthode permettant de formater les doubles en 1 chiffre après la virgule
     */
    public static final DecimalFormat DF = new DecimalFormat("0.0");
}
