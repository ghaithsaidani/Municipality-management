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
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class ModifierVehicule_Controller implements Initializable {

    int n;
    @FXML
    private StackPane parentContainer;

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


    public void showinfo(String s1,String s2,String s3,String s4)
    {
        matricule.setText(s1);
        type.setText(s2);
        etat.setValue(s3);
        annee.setText(s4);

    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
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
    void modify(ActionEvent event) {
        try{
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
                l.setBody(new Text("Voulez vous Modifier cet vehicule ?"));
                JFXDialog d = new JFXDialog(parentContainer, l, JFXDialog.DialogTransition.CENTER);
                b2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
                {

                    Connection con= DbConnector.getConnection();
                    try {
                        Vehicule v=new Vehicule(getN(),matricule.getText(),type.getText(),etat.getSelectionModel().getSelectedItem(),Integer.parseInt(annee.getText()));
                        PreparedStatement pre = con.prepareStatement("update vehicule set  type=?,etat=?,annee=? where num=?");
                        pre.setString(1, v.getType());
                        pre.setString(2,v.getEtat());
                        pre.setInt(3, v.getAnnee());
                        pre.setInt(4,v.getNum());
                        pre.executeUpdate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    d.close();
                    unfocus1();
                    unfocus2();
                    unfocus3();
                    unfocus4();
                    TrayNotification tray = new TrayNotification();
                    Image i = new Image(getClass().getResourceAsStream("../ICONS/icons8_edit_96px.png"));
                    tray.setImage(i);
                    tray.setTitle("Modification avec succés");
                    tray.setAnimationType(AnimationType.POPUP);
                    tray.setRectangleFill(Color.web("#FF9800"));
                    tray.showAndDismiss(Duration.seconds(1));
                });

                b1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
                        d.close());
                l.setActions(b2, b1);
                d.show();

            }
        }
        catch (Exception e) {
            System.out.println("probleme de modification");
        }
    }

    @FXML
    void back(ActionEvent e)throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Consulter_Vehicule.fxml"));

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
