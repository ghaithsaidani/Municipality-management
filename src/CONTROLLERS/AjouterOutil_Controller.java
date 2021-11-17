package CONTROLLERS;

import DBCONNECTOR.DbConnector;
import MODÉLES.Outil;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import VALIDATION.Validate;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

public class AjouterOutil_Controller implements Initializable {

    @FXML
    private JFXTextField type;

    @FXML
    private JFXTextField quantité;

    @FXML
    private JFXComboBox<String> etat;

    @FXML
    private Button ba;

    @FXML
    private AnchorPane container;

    @FXML
    private StackPane parentContainer;

    @FXML
    private Label lbl1;

    @FXML
    private Label lbl2;

    @FXML
    private Label lbl3;


   ObservableList<String> et= FXCollections.observableArrayList("nouvel","en panne","bon occasion","ancien");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        etat.setItems(et);
        lbl1.setVisible(false);
        lbl2.setVisible(false);
        lbl3.setVisible(false);
    }


    @FXML
    void unfocus1() {
        type.setUnFocusColor(Color.BLACK);
        lbl1.setVisible(false);

    }

    @FXML
    void unfocus2() {
        quantité.setUnFocusColor(Color.BLACK);
        lbl2.setVisible(false);
    }

    @FXML
    void unfocus3() {
        etat.setUnFocusColor(Color.BLACK);
        lbl3.setVisible(false);
    }

    @FXML
    void add(ActionEvent event) {
        try {
            JFXButton b = new JFXButton("ok");
            b.setId("jfxbutton");
            if(!Validate.verifierint(quantité.getText()))
            {
                lbl2.setVisible(true);
                quantité.setUnFocusColor(Color.RED);
            }
            if(etat.getValue()==null)
            {
                lbl3.setVisible(true);
                etat.setUnFocusColor(Color.RED);
            }
            if(!Validate.verifierchaine(type.getText()))
            {
                lbl1.setVisible(true);
                type.setUnFocusColor(Color.RED);
            }
            else if(etat.getValue()!=null && Validate.verifierint(quantité.getText()) && Validate.verifierchaine(type.getText())){
                JFXButton b2 = new JFXButton("OUI");
                JFXButton b1 = new JFXButton("NON");
                b1.setId("jfxbutton");
                b2.setId("jfxbutton");
                JFXDialogLayout l = new JFXDialogLayout();
                l.setBody(new Text("VOULEZ VOUS AJOUTER CET OUTIL ?"));
                JFXDialog d = new JFXDialog(parentContainer, l, JFXDialog.DialogTransition.CENTER);
                b2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
                {

                    Connection con= DbConnector.getConnection();
                    try {
                        Outil o=new Outil(type.getText(),etat.getSelectionModel().getSelectedItem(),Integer.parseInt(quantité.getText()));
                        PreparedStatement pr=con.prepareStatement("insert into outil values(?,?,?,?)");
                        pr.setInt(1,o.getNum());
                        pr.setString(2,o.getType());
                        pr.setString(3,o.getEtat());
                        pr.setInt(4,o.getQuantité());
                        pr.executeUpdate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    type.clear();
                    quantité.clear();
                    etat.setValue(null);
                    d.close();
                    unfocus1();
                    unfocus2();
                    unfocus3();
                    TrayNotification tray=new TrayNotification();
                    Image i=new Image(getClass().getResourceAsStream("../ICONS/icons8_verified_account_96px.png"));
                    tray.setImage(i);
                    tray.setTitle("AJOUT D'UN OUTIL AVEC SUCCÉS");
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void back(ActionEvent e)throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Consulter_Outil.fxml"));

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
