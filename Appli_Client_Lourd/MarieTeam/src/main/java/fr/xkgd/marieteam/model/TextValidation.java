package fr.xkgd.marieteam.model;

import javafx.scene.control.TextField;

/**
 * Classe TextValidation
 */
@SuppressWarnings("SpellCheckingInspection")
public class TextValidation {

    /**
     * Vérifie si le texte d'un TextField est un double
     * @param textField Le TextField à vérifier
     * @return true si le texte est un double, false sinon
     */
    public static boolean isTextFieldDouble(TextField textField) {
        boolean isValid = isDouble(textField.getText());
        if (!isValid || Double.parseDouble(textField.getText()) < 0 || Double.parseDouble(textField.getText()) > 100) {
            textField.setStyle("-fx-text-fill: red ; -fx-border-color: red ;");
            textField.setText("Valeur incorrecte");

            textField.onMouseClickedProperty().set(mouseEvent -> {
                textField.setStyle("-fx-text-fill: black ; -fx-border-color: null ;");
                textField.setText("");
                textField.onMouseClickedProperty().set(null);
            });
            return true;
        }
        return false;
    }
    private static boolean isDouble(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
