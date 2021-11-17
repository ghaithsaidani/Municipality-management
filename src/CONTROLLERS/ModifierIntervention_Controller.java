package CONTROLLERS;

import DBCONNECTOR.DbConnector;
import MODÉLES.Intervention;
import VALIDATION.Validate;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifierIntervention_Controller implements Initializable {

    int n;

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    private JFXTextField prenom;

    @FXML
    private JFXTextField nom;

    @FXML
    private DatePicker date_reclamation;

    @FXML
    private Button ba;

    @FXML
    private Label lbl1;

    @FXML
    private Label lbl3;

    @FXML
    private Label lbl2;

    @FXML
    private Label lbl5;

    @FXML
    private Label lbl4;

    @FXML
    private JFXTextField cin;

    @FXML
    private JFXTextField cat;

    @FXML
    private JFXTextField tel;

    @FXML
    private JFXTextField sujet;

    @FXML
    private Label lbl7;

    @FXML
    private Label lbl6;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl1.setVisible(false);
        lbl2.setVisible(false);
        lbl3.setVisible(false);
        lbl4.setVisible(false);
        lbl5.setVisible(false);
        lbl6.setVisible(false);
        lbl7.setVisible(false);
    }

    public void showinfo(String s1,String s2,String s3,String s4,String s5,String s6,String s7)
    {
        nom.setText(s1);
        prenom.setText(s2);
        cin.setText(s3);
        LocalDate ld=LocalDate.of(Integer.parseInt(s4.substring(0,4)),Integer.parseInt(s4.substring(5,7)),Integer.parseInt(s4.substring(8,10)));
        date_reclamation.setValue(ld);
        tel.setText(s5);
        cat.setText(s6);
        sujet.setText(s7);

    }
    public int getN() { return n; }
    public void setN(int n) {
        this.n = n;
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
        lbl4.setVisible(false);
    }

    @FXML
    void unfocus5() {
        tel.setUnFocusColor(Color.BLACK);
        lbl5.setVisible(false);
    }

    @FXML
    void unfocus6() {
        cat.setUnFocusColor(Color.BLACK);
        lbl6.setVisible(false);
    }

    @FXML
    void unfocus7() {
        sujet.setUnFocusColor(Color.BLACK);
        lbl7.setVisible(false);
    }

    @FXML
    void modifier(ActionEvent event) {

        try {
            if (Validate.verifierchaine(nom.getText()) && Validate.verifierchaine(prenom.getText()) && Validate.verifierId(cin.getText()) && Validate.verifierTel(tel.getText()) && Validate.verifierchaine(cat.getText()) && Validate.verifierchaine(sujet.getText()) && date_reclamation.getValue()!=null) {

                JFXButton b0 = new JFXButton("OUI");
                JFXButton b1 = new JFXButton("NON");
                b0.setId("jfxbutton");
                b1.setId("jfxbutton");
                JFXDialogLayout l = new JFXDialogLayout();
                l.setBody(new Text("Voulez vous modifier cet Reclamation ?"));
                JFXDialog d1 = new JFXDialog(parentContainer, l, JFXDialog.DialogTransition.CENTER);
                b0.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
                {
                    Connection con = DbConnector.getConnection();
                    try {
                        Intervention r = new Intervention(getN(),nom.getText(), prenom.getText(), Integer.parseInt(cin.getText()), Integer.parseInt(tel.getText()), cat.getText(), sujet.getText(), date_reclamation.getValue());
                        PreparedStatement pre = con.prepareStatement("update reclamation set nom=?,prenom=?,tel=?,categorie=?,sujet=?,date_reclamation=to_date(?,'YYYY-MM-DD'),description=? where num=?");
                        pre.setString(1, r.getNom());
                        pre.setString(2, r.getPrenom());
                        pre.setInt(3, r.getTel());
                        pre.setString(4, r.getCategorie());
                        pre.setString(5, r.getSujet());
                        pre.setString(6, r.getDate_reclamation().toString());
                        pre.setString(7, r.getDescription());
                        pre.setInt(8, r.getNum());
                        pre.executeUpdate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    d1.close();
                    unfocus1();
                    unfocus2();
                    unfocus3();
                    unfocus4();
                    unfocus5();
                    unfocus6();
                    unfocus7();
                    TrayNotification tray=new TrayNotification();
                    Image i=new Image(getClass().getResourceAsStream("../ICONS/icons8_edit_96px.png"));
                    tray.setImage(i);
                    tray.setTitle("Modification avec succés");
                    tray.setAnimationType(AnimationType.POPUP);
                    tray.setRectangleFill(Color.web("#FF9800"));
                    tray.showAndDismiss(Duration.seconds(1));
                });

                b1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
                {
                    d1.close();
                });
                l.setActions(b0, b1);
                d1.show();

            } else {
                if (!Validate.verifierchaine(nom.getText())) {
                    nom.setUnFocusColor(Color.RED);
                    lbl1.setVisible(true);
                }

                if (!Validate.verifierchaine(prenom.getText())) {
                    prenom.setUnFocusColor(Color.RED);
                    lbl2.setVisible(true);
                }

                if (!Validate.verifierId(cin.getText())) {
                    cin.setUnFocusColor(Color.RED);
                    lbl3.setVisible(true);
                }

                if (date_reclamation.getValue()==null) {
                    lbl4.setVisible(true);
                }

                if (!Validate.verifierTel(tel.getText())) {
                    tel.setUnFocusColor(Color.RED);
                    lbl5.setVisible(true);
                }

                if (!Validate.verifierchaine(cat.getText())) {
                    cat.setUnFocusColor(Color.RED);
                    lbl6.setVisible(true);
                }

                if (!Validate.verifierchaine(sujet.getText())) {
                    sujet.setUnFocusColor(Color.RED);
                    lbl7.setVisible(true);
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void back(ActionEvent e)throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Consulter_Reclamations.fxml"));

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
