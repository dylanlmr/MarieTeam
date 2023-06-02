package fr.xkgd.marieteam.controller;

import fr.xkgd.marieteam.database.JeuEnregistrement;
import fr.xkgd.marieteam.model.Bateau;
import fr.xkgd.marieteam.model.BateauVoyageur;
import fr.xkgd.marieteam.model.Equipement;
import fr.xkgd.marieteam.model.TextValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;

import java.io.File;
import java.util.List;

/**
 * Contrôleur de la vue AjoutBateauVoyageurView.fxml
 */
@SuppressWarnings("SpellCheckingInspection")
public class AjoutBateauVoyageurController {

    @FXML
    public ChoiceBox<String> bateauChoiceBox;
    @FXML
    public TextField vitesseTextField;
    @FXML
    public ChoiceBox<String> imageChoiceBox;
    @FXML
    public CheckListView<Equipement> equipementCheckListView;

    private MainController mainController;
    private final JeuEnregistrement jeuEnregistrement;

    public AjoutBateauVoyageurController() {
        jeuEnregistrement = JeuEnregistrement.getInstance();
    }

    /**
     * Méthode appelée automatiquement après le chargement du fichier FXML
     */
    @FXML
    public void initialize() {
        if (mainController == null || jeuEnregistrement == null) return;
        System.out.println("Initialisation de la vue AjoutBateauVoyageurController");

        List<Bateau> bateaux = jeuEnregistrement.chargerTousLesBateaux();
        bateauChoiceBox.getItems().addAll(bateaux.stream().map(Bateau::getNomBat).toList());

        if (bateauChoiceBox.getItems().size() > 0) {
            bateauChoiceBox.getSelectionModel().select(0);
        }
        imageChoiceBox.getItems().addAll(new File("src/main/resources/fr/xkgd/marieteam/image").list());

        if (imageChoiceBox.getItems().size() > 0) {
            imageChoiceBox.getSelectionModel().select(0);
        }
        equipementCheckListView.getItems().addAll(jeuEnregistrement.chargerTousLesEquipements());
    }

    /**
     * Méthode appelée lors du clic sur le bouton sauvegarder et quitter
     * @param actionEvent L'évènement
     */
    @FXML
    public void onSaveAndExit(ActionEvent actionEvent) {

        if (TextValidation.isTextFieldDouble(vitesseTextField)) return;

        Bateau bateau = jeuEnregistrement.chargerBateau(bateauChoiceBox.getValue());
        List<Equipement> equipements = equipementCheckListView.getCheckModel().getCheckedItems();

        jeuEnregistrement.createBateauVoyageur(bateau.getIdBat(), Double.parseDouble(vitesseTextField.getText()), imageChoiceBox.getValue());
        BateauVoyageur bateauVoyageur = jeuEnregistrement.getLastCreatedBateauVoyageur();
        jeuEnregistrement.insertBateauVoyageurEquipement(bateauVoyageur, equipements);

        mainController.refreshList();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
