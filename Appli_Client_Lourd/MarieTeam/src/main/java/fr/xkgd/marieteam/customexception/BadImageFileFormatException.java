package fr.xkgd.marieteam.customexception;

/**
 * Classe BadImageFileFormatException.
 * Renvoie un message d'erreur si le format de l'image n'est pas bon.
 */
@SuppressWarnings("SpellCheckingInspection")
public class BadImageFileFormatException extends RuntimeException {

    /**
     * Constructeur de la classe BadImageFileFormatException.
     * Renvoie un message d'erreur si le format de l'image n'est pas bon.
     */
    public BadImageFileFormatException() {
        System.out.println("Le format de l'image n'est pas bon.");
    }

    /**
     * Constructeur de la classe BadImageFileFormatException avec un message d'erreur.
     * Renvoie un message d'erreur si le format de l'image n'est pas bon.
     * @param message le message d'erreur
     */
    public BadImageFileFormatException(String message) {
        System.out.println(message);
    }
}
