package CONTROLLERS;

import DBCONNECTOR.DbConnector;
import MODÉLES.Compte;
import MODÉLES.Employé;
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
import EVENTS.Events;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class AjouterCompte_Controller implements Initializable {

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXTextField mail;

    @FXML
    private JFXComboBox<String> poste;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField confirm_password;

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

    @FXML
    private Label lbl5;

    @FXML
    private Label lbl6;


   ObservableList<String> et= FXCollections.observableArrayList("Maire","Directeur administratif","Interet technique","Directeur general","Manager de resources humaines");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        poste.setItems(et);
        lbl1.setVisible(false);
        lbl2.setVisible(false);
        lbl3.setVisible(false);
        lbl4.setVisible(false);
        lbl5.setVisible(false);
        lbl6.setVisible(false);
    }

    private boolean exist()
    {
        int i;
        for (i=0;i<ConsulterEmployé_Controller.emplist().size();i++)
        {
            if (Integer.parseInt(username.getText())==ConsulterEmployé_Controller.emplist().get(i).getId())
                break;
        }
        return i < ConsulterEmployé_Controller.emplist().size();

    }

    @FXML
    void unfocus1() {
        username.setUnFocusColor(Color.BLACK);
        lbl1.setVisible(false);
    }

    @FXML
    void unfocus2() {
        mail.setUnFocusColor(Color.BLACK);
        lbl2.setVisible(false);
    }

    @FXML
    void unfocus3() {
        poste.setUnFocusColor(Color.BLACK);
        lbl3.setVisible(false);
    }

    @FXML
    void unfocus4() {
        password.setUnFocusColor(Color.BLACK);
        lbl4.setVisible(false);
        if(lbl5.getParent().isVisible())
        {
            lbl5.setVisible(false);
            confirm_password.setUnFocusColor(Color.BLACK);
        }
    }

    @FXML
    void unfocus5() {
        confirm_password.setUnFocusColor(Color.BLACK);
        lbl5.setVisible(false);
    }



    @FXML
    void add(ActionEvent event) {
        try {
            /*JFXButton b = new JFXButton("ok");
            b.setId("jfxbutton");*/
            if(!exist())
            {
                if(!Validate.verifierId(username.getText()))
                {
                    lbl6.setVisible(true);
                    username.setUnFocusColor(Color.RED);

                }
                else {
                    lbl1.setVisible(true);
                    username.setUnFocusColor(Color.RED);
                }
            }

            if(!Validate.verifieremail(mail.getText()) )
            {
                lbl2.setVisible(true);
                mail.setUnFocusColor(Color.RED);
            }
            if(poste.getValue()==null){
                lbl3.setVisible(true);
                poste.setUnFocusColor(Color.RED);
            }

            if(!Validate.verifierpassword(password.getText()))
            {

                lbl4.setVisible(true);
                password.setUnFocusColor(Color.RED);
            }

            if(Validate.verifierpassword(password.getText()) && !password.getText().equals(confirm_password.getText()))
            {
                lbl5.setVisible(true);
                confirm_password.setUnFocusColor(Color.RED);
            }



            else if (Validate.verifierId(username.getText()) && Validate.verifieremail(mail.getText()) && poste.getValue()!=null && Validate.verifierpassword(password.getText()) && password.getText().equals(confirm_password.getText()) && exist()) {
                JFXButton b0 = new JFXButton("OUI");
                JFXButton b1 = new JFXButton("NON");
                b0.setId("jfxbutton");
                b1.setId("jfxbutton");
                JFXDialogLayout l = new JFXDialogLayout();
                l.setBody(new Text("Voulez vous créer ce compte ?"));
                JFXDialog d = new JFXDialog(parentContainer, l, JFXDialog.DialogTransition.CENTER);
                b0.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
                {
                    Connection con= DbConnector.getConnection();
                    try {
                        /*Employé emp=new Employé(Integer.parseInt(username.getText()))*/
                        Compte c = new Compte(new Employé(Integer.parseInt(username.getText())), mail.getText(), poste.getSelectionModel().getSelectedItem(), password.getText());
                        PreparedStatement pr=con.prepareStatement("insert into comptes values(?,?,?,?)");
                        pr.setInt(1,c.getEmp().getId());
                        pr.setString(2,c.getE_mail());
                        pr.setString(3,c.getPoste());
                        pr.setString(4,c.getPassword());
                        pr.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    username.clear();
                    mail.clear();
                    password.clear();
                    confirm_password.clear();
                    poste.setValue(null);
                    d.close();
                    unfocus1();
                    unfocus2();
                    unfocus3();
                    unfocus4();
                    unfocus5();
                    TrayNotification tray=new TrayNotification();
                    Image i=new Image(getClass().getResourceAsStream("../ICONS/icons8_verified_account_96px.png"));
                    tray.setImage(i);
                    tray.setTitle("CREATION DU COMPTE AVEC SUCCÉS");
                    tray.setAnimationType(AnimationType.POPUP);
                    tray.setRectangleFill(Color.web("#19A05A"));
                    tray.showAndDismiss(Duration.seconds(1));
                });

                b1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
                {
                    d.close();
                });
                l.setActions(b0, b1);
                d.show();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void back(ActionEvent e)throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Consulter_comptes.fxml"));

        Scene scene = ba.getScene();

        root.translateYProperty().set(-scene.getHeight());
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
