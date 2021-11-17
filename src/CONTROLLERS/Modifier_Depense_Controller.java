package CONTROLLERS;

import DBCONNECTOR.DbConnector;
import MODÉLES.Depense;
import VALIDATION.Validate;
import com.jfoenix.controls.*;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import oracle.net.aso.d;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Modifier_Depense_Controller implements Initializable {
    int n;

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    private JFXTextField montant;

    @FXML
    private JFXTextField motif;

    @FXML
    private DatePicker date;

    @FXML
    private JFXTextArea description;

    @FXML
    private Label lbl1;

    @FXML
    private Label lbl2;

    @FXML
    private Label lbl3;

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
        motif.setUnFocusColor(Color.BLACK);
        lbl2.setVisible(false);
    }

    @FXML
    void unfocus3() {
        lbl3.setVisible(false);
    }

    public void showinfo(String s1,String s2,String s3,String s4)
    {
        montant.setText(s1);
        motif.setText(s2);
        LocalDate ld=LocalDate.of(Integer.parseInt(s3.substring(0,4)),Integer.parseInt(s3.substring(5,7)),Integer.parseInt(s3.substring(8,10)));
        date.setValue(ld);
        description.setText(s4);

    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
    @FXML
    void modifier() {

        try {

            if (Validate.verifierchaine(motif.getText())) {

                JFXButton b2 = new JFXButton("OUI");
                JFXButton b1 = new JFXButton("NON");
                b1.setId("jfxbutton");
                b2.setId("jfxbutton");
                JFXDialogLayout l = new JFXDialogLayout();
                l.setBody(new Text("Voulez vous modifier cette reclamation ?"));
                JFXDialog d = new JFXDialog(parentContainer, l, JFXDialog.DialogTransition.CENTER);
                b2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->

                        {

                            Connection con = DbConnector.getConnection();
                            try {
                                Depense dep=new Depense(Float.parseFloat(montant.getText()),motif.getText(),date.getValue(),description.getText());
                                PreparedStatement pre = DbConnector.getConnection().prepareStatement("update Depense set montant=?,motif=?,date=?,description=? where num=?");
                                pre.setFloat(1,dep.getMontant());
                                pre.setString(2, dep.getMotif());
                                pre.setString(3, dep.getDate().toString());
                                pre.setString(4, dep.getDescription());
                                pre.setInt(5,dep.getNum());

                                pre.execute();
                            } catch (Exception e) {
                                System.out.println("Probleme du modification !");
                            }


                            d.close();
                            montant.clear();
                            motif.clear();
                            description.clear();
                            date.setValue(null);
                            unfocus1();
                            unfocus2();
                            unfocus3();

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
                d.close();
            });
            l.setActions(b2, b1);
            d.show();
            }

            else{

                if (date.getValue() == null) {
                    lbl3.setVisible(true);
                }
                if (motif.getText() == null) {
                    motif.setUnFocusColor(Color.RED);
                    lbl2.setVisible(true);
                }

            }

    } catch (Exception e) {
        e.printStackTrace();
    }

    }

    @FXML
    void back(ActionEvent e)throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Consulter_Depense.fxml"));

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







