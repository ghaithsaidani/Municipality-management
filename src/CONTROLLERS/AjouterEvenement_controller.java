package CONTROLLERS;

import MODÉLES.Evenement;
import VALIDATION.Validate;
import com.jfoenix.controls.*;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;



public class AjouterEvenement_controller implements Initializable {

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    private JFXTextField nom;

    @FXML
    private JFXTextField adresse;

    @FXML
    private JFXTextField budget;

    @FXML
    private JFXComboBox<String> type;

    @FXML
    private DatePicker date_debut;

    @FXML
    private DatePicker date_fin;

    @FXML
    private TextArea desc;

    @FXML
    private Button ba;

    @FXML
    private Label lbl1;

    @FXML
    private Label lbl3;



    @FXML
    private Label lbl4;

    @FXML
    private Label lbl5;

    @FXML
    private Label lbl6;

    @FXML
    private Label lbl7;


    ObservableList<String> types= FXCollections.observableArrayList("conférence et congrès","séminaire","festival");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        type.setItems(types);
        lbl1.setVisible(false);
        lbl3.setVisible(false);
        lbl4.setVisible(false);
        lbl5.setVisible(false);
        lbl6.setVisible(false);
        lbl7.setVisible(false);
    }

    @FXML
    void unfocus1() {
        nom.setUnFocusColor(Color.BLACK);
        lbl1.setVisible(false);
    }


    @FXML
    void unfocus3() {
        adresse.setUnFocusColor(Color.BLACK);
        lbl3.setVisible(false);
    }

    @FXML
    void unfocus4() {
        type.setUnFocusColor(Color.BLACK);
        lbl4.setVisible(false);
    }

    @FXML
    void unfocus5() {

        lbl5.setVisible(false);
    }

    @FXML
    void unfocus6() {
        lbl6.setVisible(false);
    }

    @FXML
    void unfocus7() {
        budget.setUnFocusColor(Color.BLACK);
        lbl7.setVisible(false);
    }



    public Evenement eventcreated()
    {
        Evenement ev= new Evenement(nom.getText(), "En Attente", type.getSelectionModel().getSelectedItem(), date_debut.getValue(), date_fin.getValue(), adresse.getText(),Float.parseFloat(budget.getText()),desc.getText());
        return ev;
    }

    @FXML
    void open_ajouter_personnel() {
        try {
            if (Validate.verifierchaine(nom.getText()) && type.getValue() != null && date_debut.getValue() != null && date_fin.getValue() != null && date_debut.getValue().isBefore(date_fin.getValue())) {

                Parent root = FXMLLoader.load(getClass().getResource("../FXML/Ajouter_PersonnelEvenement.fxml"));

                Scene scene = ba.getScene();

                root.translateXProperty().set(scene.getWidth());
                parentContainer.getChildren().add(root);

                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1 -> {
                    parentContainer.getChildren().remove(container);
                });
                timeline.play();
                unfocus1();
                unfocus3();
                unfocus4();
                unfocus5();
                unfocus6();

            }
             else {
                if (!Validate.verifierchaine(nom.getText())) {
                    nom.setUnFocusColor(Color.RED);
                    lbl1.setVisible(true);
                }


                if (type.getValue() == null) {
                    type.setUnFocusColor(Color.RED);
                    lbl4.setVisible(true);
                }

                if (date_debut.getValue() == null || date_debut.getValue().isAfter(date_fin.getValue())) {
                    lbl5.setVisible(true);
                }

                if (date_fin.getValue() == null || date_debut.getValue().isAfter(date_fin.getValue())) {
                    lbl6.setVisible(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void back(ActionEvent e)throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Consulter_Evenement.fxml"));

        Scene scene = ba.getScene();

        root.translateYProperty().set(scene.getHeight());
        StackPane parentContainer = (StackPane) scene.getRoot();
        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            parentContainer.getChildren().remove(container);
        });
        timeline.play();
    }

}
