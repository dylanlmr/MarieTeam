package fr.xkgd.marieteam.customexception;

/**
 * Exception levée lorsqu'un fichier n'est pas un PDF
 */
@SuppressWarnings("SpellCheckingInspection")
public class BadPdfFileFormatException extends RuntimeException {

    /**
     * Exception levée lorsqu'un fichier n'est pas un PDF
     */
    public BadPdfFileFormatException() {
        System.out.println("Le fichier n'est pas un PDF !");
    }

    /**
     * Exception levée avec un message d'erreur lorsqu'un fichier n'est pas un PDF
     * @param message Message d'erreur
     */
    public BadPdfFileFormatException(String message) {
        System.out.println(message);
    }
}
