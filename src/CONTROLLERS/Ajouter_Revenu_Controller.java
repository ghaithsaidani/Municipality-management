package CONTROLLERS;

import DBCONNECTOR.DbConnector;
import MODÉLES.Depense;
import MODÉLES.Revenu;
import VALIDATION.Validate;
import com.jfoenix.controls.*;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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

public class Ajouter_Revenu_Controller implements Initializable {
    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    private JFXTextField montant;

    @FXML
    private JFXTextField rev;

    @FXML
    private DatePicker date;

    @FXML
    private JFXTextArea description;

    @FXML
    private Label lbl3;

    @FXML
    private Label lbl1;

    @FXML
    private Label lbl2;

    @FXML
    private Button ba;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl1.setVisible(false);
        lbl2.setVisible(false);
        lbl3.setVisible(false);
    }

    @FXML
    void unfocus1() {
        montant.setUnFocusColor(Color.BLACK);
        lbl1.setVisible(false);
    }

    @FXML
    void unfocus2() {
        rev.setUnFocusColor(Color.BLACK);
        lbl2.setVisible(false);
    }

    @FXML
    void unfocus3() {
        lbl3.setVisible(false);
    }
    @FXML
    void ajouter(ActionEvent event) {
        try {
            if ( Validate.verifierint(rev.getText())) {

                JFXButton b2 = new JFXButton("OUI");
                JFXButton b1 = new JFXButton("NON");
                b1.setId("jfxbutton");
                b2.setId("jfxbutton");
                JFXDialogLayout l = new JFXDialogLayout();
                l.setBody(new Text("Voulez vous ajouter cette Revenu ?"));
                JFXDialog d = new JFXDialog(parentContainer, l, JFXDialog.DialogTransition.CENTER);
                b2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
                {

                    Connection con= DbConnector.getConnection();
                    try{
                        Revenu dep=new Revenu(Float.parseFloat(montant.getText()),Float.parseFloat(rev.getText()),date.getValue(),description.getText());
                        PreparedStatement pre = con.prepareStatement("insert into revenu values(?,?,?,(to_date(?,'YYYY-MM-DD')),?)");
                        pre.setInt(1,dep.getNum());
                        pre.setFloat(2,dep.getMontant());
                        pre.setFloat(3, dep.getRev());
                        pre.setString(4, dep.getDate().toString());
                        pre.setString(5, dep.getDescription());
                        pre.execute();
                    } catch (Exception ex) {
                        ex.printStackTrace();

                    }
                        d.close();
                        montant.clear();
                        rev.clear();
                        description.clear();
                        date.setValue(null);
                        unfocus1();
                        unfocus2();
                        unfocus3();

                        TrayNotification tray = new TrayNotification();
                        Image i = new Image(getClass().getResourceAsStream("../ICONS/icons8_verified_account_96px.png"));
                        tray.setImage(i);
                        tray.setTitle("AJOUT DE REVENU AVEC SUCCÉS");
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

            else{

                if (date.getValue() == null) {
                    lbl3.setVisible(true);
                }
                if (rev.getText() == null) {
                    rev.setUnFocusColor(Color.RED);
                    lbl2.setVisible(true);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void back(ActionEvent e)throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Consulter_Revenu.fxml"));

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
