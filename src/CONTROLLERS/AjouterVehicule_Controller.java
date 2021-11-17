package CONTROLLERS;

import DBCONNECTOR.DbConnector;
import MODÉLES.Vehicule;
import VALIDATION.Validate;
import com.jfoenix.controls.*;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.ResourceBundle;

public class AjouterVehicule_Controller implements Initializable {

    @FXML
    private JFXTextField matricule;

    @FXML
    private JFXTextField type;

    @FXML
    private JFXComboBox<String> etat;

    @FXML
    private JFXTextField annee;

    @FXML
    private AnchorPane container;

    @FXML
    private StackPane parentContainer;

    @FXML
    private Button ba;

    @FXML
    private Label lbl1;

    @FXML
    private Label lbl4;

    @FXML
    private Label lbl2;

    @FXML
    private Label lbl3;


   ObservableList<String> et= FXCollections.observableArrayList("nouvelle","en panne","bon occasion","ancienne");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        etat.setItems(et);
        lbl1.setVisible(false);
        lbl2.setVisible(false);
        lbl3.setVisible(false);
        lbl4.setVisible(false);
    }


    @FXML
    void unfocus1() {
        matricule.setUnFocusColor(Color.BLACK);
        lbl1.setVisible(false);
    }

    @FXML
    void unfocus2() {
        type.setUnFocusColor(Color.BLACK);
        lbl2.setVisible(false);
    }

    @FXML
    void unfocus3() {
        etat.setUnFocusColor(Color.BLACK);
        lbl3.setVisible(false);
    }

    @FXML
    void unfocus4() {
        annee.setUnFocusColor(Color.BLACK);
        lbl4.setVisible(false);
    }

    @FXML
    void add() {
        try {
            if(!Validate.verifiermatricule(matricule.getText()))
            {
                lbl1.setVisible(true);
                matricule.setUnFocusColor(Color.RED);
            }
            if(!Validate.verifierchaine(type.getText()))
            {
                lbl2.setVisible(true);
                type.setUnFocusColor(Color.RED);
            }
            if(etat.getValue()==null)
            {
                lbl3.setVisible(true);
                etat.setUnFocusColor(Color.RED);
            }
            if(!Validate.verifierint(annee.getText()))
            {
                lbl4.setVisible(true);
                annee.setUnFocusColor(Color.RED);
            }



            else if(etat.getValue()!=null && Validate.verifierchaine(type.getText()) && Validate.verifierint(annee.getText()) && Validate.verifiermatricule(matricule.getText())){
                JFXButton b2 = new JFXButton("OUI");
                JFXButton b1 = new JFXButton("NON");
                b1.setId("jfxbutton");
                b2.setId("jfxbutton");
                JFXDialogLayout l = new JFXDialogLayout();
                l.setBody(new Text("Voulez vous Ajouter cet vehicule ?"));
                JFXDialog d = new JFXDialog(parentContainer, l, JFXDialog.DialogTransition.CENTER);
                b2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
                {

                    Connection con= DbConnector.getConnection();
                    try {
                        Vehicule v=new Vehicule(matricule.getText(),type.getText(),etat.getSelectionModel().getSelectedItem(),Integer.parseInt(annee.getText()));
                        assert con != null;
                        PreparedStatement pr=con.prepareStatement("insert into vehicule values(?,?,?,?,?)");
                        pr.setInt(1,v.getNum());
                        pr.setString(2,v.getMatricule());
                        pr.setString(3,v.getType());
                        pr.setString(4,v.getEtat());
                        pr.setInt(5,v.getAnnee());
                        pr.executeUpdate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    type.clear();
                    annee.clear();
                    matricule.clear();
                    etat.setValue(null);
                    d.close();
                    unfocus1();
                    unfocus2();
                    unfocus3();
                    unfocus4();
                    TrayNotification tray=new TrayNotification();
                    Image i=new Image(Objects.requireNonNull(getClass().getResourceAsStream("../ICONS/icons8_verified_account_96px.png")));
                    tray.setImage(i);
                    tray.setTitle("AJOUT D'UN VEHICULE AVEC SUCCÉS");
                    tray.setAnimationType(AnimationType.POPUP);
                    tray.setRectangleFill(Color.web("#19A05A"));
                    tray.showAndDismiss(Duration.seconds(1));
                });

                b1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
                        d.close());
                l.setActions(b2, b1);
                d.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void back()throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/Consulter_Vehicule.fxml")));

        Scene scene = ba.getScene();

        root.translateYProperty().set(scene.getHeight());
        StackPane parentContainer = (StackPane) scene.getRoot();
        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> parentContainer.getChildren().remove(container));
        timeline.play();
    }
}
