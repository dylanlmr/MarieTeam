package fr.xkgd.marieteam.controller;

import fr.xkgd.marieteam.Pdf;
import fr.xkgd.marieteam.model.BateauVoyageur;
import fr.xkgd.marieteam.database.JeuEnregistrement;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static fr.xkgd.marieteam.Utils.LS;
import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

/**
 * Classe MainController (Controleur principal)
 * S'occupe de lancer la connexion à la base de données et de charger les bateaux voyageurs
 */
@SuppressWarnings("SpellCheckingInspection")
public class MainController {

    @FXML
    public ListView<BateauVoyageur> listContainer;
    @FXML
    public Button exportToPdfBtn;
    @FXML
    public CheckBox selectAllCheckBox;
    @FXML
    public Label isPdfExportedLabel;

    private final JeuEnregistrement jeuEnregistrement;
    private boolean isTimeElapsed = true;

    /**
     * Constructeur de la classe MainController (Controleur principal)
     * S'occupe de lancer la connexion à la base de données et de charger les bateaux voyageurs
     */
    public MainController() {
        jeuEnregistrement = JeuEnregistrement.getInstance();
    }

    /**
     * Methode permettant d'initialiser les ressources fxml et d'ajouter les bateaux voyageurs dans la listview
     */
    @FXML
    public void initialize() {
        listContainer.getItems().addAll(jeuEnregistrement.chargerLesBateauxVoyageurs());
        listContainer.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(BateauVoyageur bateauVoyageur, boolean empty) {
                super.updateItem(bateauVoyageur, empty);

                if (empty || bateauVoyageur == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    imageView.setImage(new Image(new File("src/main/resources/fr/xkgd/marieteam/image/" + bateauVoyageur.getImageBatVoy()).toURI().toString()));
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(100);
                    setGraphic(imageView);
                    setText(bateauVoyageur.toString());
                }
            }
        });

        listContainer.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        listContainer.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener<BateauVoyageur>) change -> {
                    ObservableList<BateauVoyageur> selectedItems = listContainer.getSelectionModel().getSelectedItems();

                    exportToPdfBtn.setDisable(selectedItems.size() == 0 || !isTimeElapsed);
                    selectAllCheckBox.setSelected(selectedItems.size() == listContainer.getItems().size());
                    isPdfExportedLabel.setText("");
                }
        );
    }

    /**
     * Methode permettant de selectionner ou non tous les bateaux
     * @param actionEvent L'evenement
     */
    @FXML
    public void onSelectAll(ActionEvent actionEvent) {
        if (selectAllCheckBox.isSelected()) {
            listContainer.getSelectionModel().selectAll();
        } else {
            listContainer.getSelectionModel().clearSelection();
        }
    }

    /**
     * Methode permettant d'exporter les bateaux selectionnés en pdf
     * @param actionEvent L'evenement
     */
    @FXML
    public void onExportToPdf(ActionEvent actionEvent) {
        exportToPdfBtn.setDisable(true);
        isTimeElapsed = false;

        ScheduledExecutorService scheduledExecutorService = newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(() -> {
            isTimeElapsed = true;
            exportToPdfBtn.setDisable(false);
        }, 3, TimeUnit.SECONDS);

        ObservableList<BateauVoyageur> selectedItems = listContainer.getSelectionModel().getSelectedItems();
        Pdf pdf = new Pdf("src/main/resources/fr/xkgd/marieteam/pdf/BateauxVoyageurs.pdf");

        for(int i = 0; i < selectedItems.size(); i++) {
            pdf.addImageInPdf("src/main/resources/fr/xkgd/marieteam/image/" + selectedItems.get(i).getImageBatVoy());
            pdf.addTextInPdf(selectedItems.get(i).toString());

            if (i != selectedItems.size() - 1) {
                if (i % 2 == 0) {
                    pdf.addTextInPdf(LS);
                } else {
                    pdf.newPageToPdf();
                }
            }
        }
        pdf.closePdf();
        isPdfExportedLabel.setStyle("-fx-text-fill: #006400; -fx-font-weight: bold;");
        isPdfExportedLabel.setText("PDF Exporté !");
    }

    /**
     * Methode permettant d'éditer un bateau voyageur
     * @param mouseEvent L'evenement
     */
    @FXML
    public void onEditBateauVoyageur(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() != 2 || listContainer.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/xkgd/marieteam/view/EditBateauVoyageurView.fxml"));
            Parent root = loader.load();
            EditBateauVoyageurController editBateauVoyageurController = loader.getController();
            editBateauVoyageurController.setMainController(this);
            editBateauVoyageurController.setBateauVoyageur(listContainer.getSelectionModel().getSelectedItem());
            editBateauVoyageurController.initialize();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setTitle("Edition d'un bateau voyageur");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception ex) {
            System.out.println("Erreur lors du chargement de la vue EditBateauVoyageur.fxml");
        }
    }

    /**
     * Methode permettant d'ajouter un bateau voyageur
     * @param actionEvent L'evenement
     */
    public void onAddBateauVoyageur(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/xkgd/marieteam/view/AjoutBateauVoyageurView.fxml"));
            Parent root = loader.load();
            AjoutBateauVoyageurController ajoutBateauVoyageurController = loader.getController();
            ajoutBateauVoyageurController.setMainController(this);
            ajoutBateauVoyageurController.initialize();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setTitle("Edition d'un bateau voyageur");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception ex) {
            System.out.println("Erreur lors du chargement de la vue EditBateauVoyageur.fxml");
        }
    }

    /**
     * Methode permettant de rafraichir la liste des bateaux voyageurs
     */
    public void refreshList() {
        listContainer.getItems().clear();
        listContainer.getItems().addAll(jeuEnregistrement.chargerLesBateauxVoyageurs());
        System.out.println("Liste rafraichie");
    }
}