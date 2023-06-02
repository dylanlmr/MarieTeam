package fr.xkgd.marieteam.controller;

import fr.xkgd.marieteam.database.JeuEnregistrement;
import fr.xkgd.marieteam.model.BateauVoyageur;
import fr.xkgd.marieteam.model.Equipement;
import fr.xkgd.marieteam.model.TextValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Contrôleur de la fenêtre d'édition d'un bateau voyageur
 */
@SuppressWarnings("SpellCheckingInspection")
public class EditBateauVoyageurController {
    @FXML
    public TextField vitesseTextField;
    @FXML
    public ChoiceBox<String> imageChoiceBox;
    @FXML
    public CheckListView<Equipement> equipementCheckListView;

    private MainController mainController;
    private final JeuEnregistrement jeuEnregistrement;
    private BateauVoyageur bateauVoyageur;

    public EditBateauVoyageurController() {
        jeuEnregistrement = JeuEnregistrement.getInstance();
    }

    /**
     * Méthode appelée automatiquement après le chargement du FXML
     */
    @FXML
    public void initialize() {
        if (mainController == null || jeuEnregistrement == null || bateauVoyageur == null) return;
        vitesseTextField.setText(String.valueOf(bateauVoyageur.getVitesseBatVoy()));
        imageChoiceBox.getItems().addAll(new File("src/main/resources/fr/xkgd/marieteam/image").list());

        if (imageChoiceBox.getItems().size() > 0) {
            imageChoiceBox.getSelectionModel().select(bateauVoyageur.getImageBatVoy());
        }
        equipementCheckListView.getItems().addAll(jeuEnregistrement.chargerTousLesEquipements());
        for (int i = 0; i < bateauVoyageur.getLesEquipements().size(); i++) {
            equipementCheckListView.getCheckModel().check(i);
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Sauvegarder et quitter"
     * Met à jour le bateau voyageur dans la base de données
     * @param actionEvent L'évènement
     */
    @FXML
    public void onDeleteBateauVoyageur(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer le bateau voyageur ?");
        alert.setContentText("La suppression est irréversible.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            jeuEnregistrement.deleteBateauVoyageur(bateauVoyageur);
            mainController.refreshList();
            stage.close();
        } else {
            actionEvent.consume();
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Sauvegarder et quitter"
     * Met à jour le bateau voyageur dans la base de données
     * @param actionEvent L'évènement
     */
    @FXML
    public void onSaveAndExit(ActionEvent actionEvent) {

        if (TextValidation.isTextFieldDouble(vitesseTextField)) return;

        bateauVoyageur.setVitesseBatVoy(Double.parseDouble(vitesseTextField.getText()));
        bateauVoyageur.setImageBatVoy(imageChoiceBox.getValue());
        bateauVoyageur.setLesEquipements(equipementCheckListView.getItems());
        jeuEnregistrement.updateBateauVoyageur(bateauVoyageur);

        List<Equipement> equipements = equipementCheckListView.getCheckModel().getCheckedItems();

        jeuEnregistrement.deleteAllBateauVoyageurEquipements(bateauVoyageur);
        jeuEnregistrement.insertBateauVoyageurEquipement(bateauVoyageur, equipements);

        mainController.refreshList();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setBateauVoyageur(BateauVoyageur bateauVoyageur) {
        this.bateauVoyageur = bateauVoyageur;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
