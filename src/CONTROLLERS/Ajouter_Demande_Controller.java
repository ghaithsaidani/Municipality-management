package CONTROLLERS;

import DBCONNECTOR.DbConnector;
import MODÉLES.Demande;
import VALIDATION.Validate;
import com.jfoenix.controls.*;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class Ajouter_Demande_Controller implements Initializable {

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    private JFXComboBox<String> type;

    @FXML
    private JFXTextField nom;

    @FXML
    private DatePicker date_debut;

    @FXML
    private Button ba;

    @FXML
    private Label lbl1;

    @FXML
    private Label lbl6;

    @FXML
    private Label lbl8;

    @FXML
    private TextArea desc;

    @FXML
    private JFXTextField adresse;

    @FXML
    private JFXTextField cin;

    @FXML
    private JFXTextField prenom;

    @FXML
    private JFXTextField tel;

    @FXML
    private DatePicker date_fin;

    @FXML
    private Label lbl7;

    @FXML
    private Label lbl2;

    @FXML
    private Label lbl3;

    @FXML
    private Label lbl4;

    @FXML
    private Label lbl5;

    ObservableList<String> types= FXCollections.observableArrayList("permission de voirie","permis de station");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        type.setItems(types);
        lbl1.setVisible(false);
        lbl2.setVisible(false);
        lbl3.setVisible(false);
        lbl4.setVisible(false);
        lbl5.setVisible(false);
        lbl6.setVisible(false);
        lbl7.setVisible(false);
        lbl8.setVisible(false);
    }

    @FXML
    void unfocus1() {
        nom.setUnFocusColor(Color.BLACK);
        lbl1.setVisible(false);
    }

    @FXML
    void unfocus2() {
        prenom.setUnFocusColor(Color.BLACK);
        lbl2.setVisible(false);
    }

    @FXML
    void unfocus3() {
        cin.setUnFocusColor(Color.BLACK);
        lbl3.setVisible(false);
    }

    @FXML
    void unfocus4() {
        adresse.setUnFocusColor(Color.BLACK);
        lbl4.setVisible(false);
    }

    @FXML
    void unfocus5() {
        tel.setUnFocusColor(Color.BLACK);
        lbl5.setVisible(false);
    }

    @FXML
    void unfocus6() {
        type.setUnFocusColor(Color.BLACK);
        lbl6.setVisible(false);
    }

    @FXML
    void unfocus7() {
        lbl7.setVisible(false);
    }

    @FXML
    void unfocus8() {
        lbl8.setVisible(false);
    }


    @FXML
    void ajouter(ActionEvent event) {
        try {
            if (Validate.verifierchaine(nom.getText()) && Validate.verifierchaine(prenom.getText()) &&  Validate.verifierId(cin.getText()) && Validate.verifierTel(tel.getText()) && !adresse.getText().isEmpty() && type.getValue() != null && date_debut.getValue() != null && date_fin.getValue() != null && date_debut.getValue().isBefore(date_fin.getValue())) {

                JFXButton b2 = new JFXButton("OUI");
                JFXButton b1 = new JFXButton("NON");
                b1.setId("jfxbutton");
                b2.setId("jfxbutton");
                JFXDialogLayout l = new JFXDialogLayout();
                l.setBody(new Text("Voulez vous ajouter cette Demande ?"));
                JFXDialog d = new JFXDialog(parentContainer, l, JFXDialog.DialogTransition.CENTER);
                b2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
                {
                    Connection con= DbConnector.getConnection();
                    try{
                        Demande dem=new Demande(nom.getText(),prenom.getText(),Integer.parseInt(cin.getText()),adresse.getText(),Integer.parseInt(tel.getText()),type.getSelectionModel().getSelectedItem(),date_debut.getValue(),date_fin.getValue(),desc.getText());
                        PreparedStatement pre = con.prepareStatement("insert into demande values(?,?,?,?,?,?,?,(to_date(?,'YYYY-MM-DD')),(to_date(?,'YYYY-MM-DD')),?)");
                        pre.setInt(1, dem.getNum());
                        pre.setString(2, dem.getNom());
                        pre.setString(3, dem.getPrenom());
                        pre.setInt(4, dem.getCin());
                        pre.setString(5, dem.getAdresse());
                        pre.setInt(6, dem.getTel());
                        pre.setString(7, dem.getType());
                        pre.setString(8, dem.getDate_debut().toString());
                        pre.setString(9, dem.getDate_fin().toString());
                        pre.setString(10, dem.getDescription());
                        pre.execute();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    d.close();
                    unfocus1();
                    unfocus2();
                    unfocus3();
                    unfocus4();
                    unfocus5();
                    unfocus6();
                    unfocus7();
                    unfocus8();
                    nom.clear();
                    prenom.clear();
                    cin.clear();
                    adresse.clear();
                    tel.clear();
                    type.setValue(null);
                    date_debut.setValue(null);
                    date_fin.setValue(null);
                    desc.clear();
                    TrayNotification tray = new TrayNotification();
                    Image i = new Image(getClass().getResourceAsStream("../ICONS/icons8_verified_account_96px.png"));
                    tray.setImage(i);
                    tray.setTitle("AJOUT DE L'EVENEMENT AVEC SUCCÉS");
                    tray.setAnimationType(AnimationType.POPUP);
                    tray.setRectangleFill(Color.web("#19A05A"));
                    tray.showAndDismiss(Duration.seconds(1));
                });

                b1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
                {
                    d.close();
                });
                l.setActions(b2, b1);
                d.show();
            }

            else {
                if (!Validate.verifierchaine(nom.getText())) {
                    nom.setUnFocusColor(Color.RED);
                    lbl1.setVisible(true);
                }

                if (!Validate.verifierId(prenom.getText())) {
                    prenom.setUnFocusColor(Color.RED);
                    lbl2.setVisible(true);
                }

                if (!Validate.verifierId(cin.getText())) {
                    cin.setUnFocusColor(Color.RED);
                    lbl3.setVisible(true);
                }

                if (adresse.getText().isEmpty()) {
                    adresse.setUnFocusColor(Color.RED);
                    lbl4.setVisible(true);
                }

                if (!Validate.verifierTel(tel.getText())) {
                    tel.setUnFocusColor(Color.RED);
                    lbl5.setVisible(true);
                }


                if (type.getValue() == null) {
                    type.setUnFocusColor(Color.RED);
                    lbl6.setVisible(true);
                }

                if (date_debut.getValue() == null || date_debut.getValue().isAfter(date_fin.getValue())) {
                    lbl7.setVisible(true);
                }

                if (date_fin.getValue() == null || date_debut.getValue().isAfter(date_fin.getValue())) {
                    lbl8.setVisible(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void back(ActionEvent e)throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Consulter_Demande.fxml"));

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
