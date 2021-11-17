package CONTROLLERS;

import DBCONNECTOR.DbConnector;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
import java.time.LocalDate;
import java.util.ResourceBundle;


public class ModifierEvenement_controller implements Initializable {
    int n;

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    private JFXTextField nom;

    @FXML
    private JFXTextField adresse;

    @FXML
    private JFXComboBox<String> type;

    @FXML
    private DatePicker date_debut;

    @FXML
    private DatePicker date_fin;

    @FXML
    private JFXComboBox<String> etat;

    @FXML
    private TextArea desc;

    @FXML
    private JFXTextField budget;


    @FXML
    private Button ba;

    @FXML
    private Label lbl1;

    @FXML
    private Label lbl3;

    @FXML
    private Label lbl2;

    @FXML
    private Label lbl4;

    @FXML
    private Label lbl5;

    @FXML
    private Label lbl6;

    @FXML
    private Label lbl7;

    ObservableList<String> etats = FXCollections.observableArrayList("En Cours ","En Attente","Terminer");
    ObservableList<String> types= FXCollections.observableArrayList("conférence et congrès","séminaire","festival");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        etat.setItems(etats);
        type.setItems(types);
        lbl1.setVisible(false);
        lbl2.setVisible(false);
        lbl3.setVisible(false);
        lbl4.setVisible(false);
        lbl5.setVisible(false);
        lbl6.setVisible(false);
        lbl7.setVisible(false);
    }

    public void showinfo(String s1,String s2,String s3,String s4,String s5,String s6,String s7,String s8)
    {
        nom.setText(s1);
        etat.setValue(s2);
        type.setValue(s3);
        LocalDate ld=LocalDate.of(Integer.parseInt(s4.substring(0,4)),Integer.parseInt(s4.substring(5,7)),Integer.parseInt(s4.substring(8,10)));
        date_debut.setValue(ld);
        LocalDate ld1=LocalDate.of(Integer.parseInt(s5.substring(0,4)),Integer.parseInt(s5.substring(5,7)),Integer.parseInt(s5.substring(8,10)));
        date_fin.setValue(ld1);
        adresse.setText(s6);
        desc.setText(s7);
        budget.setText(s8);

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
        etat.setUnFocusColor(Color.BLACK);
        lbl2.setVisible(false);
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

    @FXML
    void modifier() {
        try {
            if (Validate.verifierchaine(nom.getText()) && etat.getValue() != null && type.getValue() != null && date_debut.getValue() != null && date_fin.getValue() != null && date_debut.getValue().isBefore(date_fin.getValue())) {
                JFXButton b0 = new JFXButton("OUI");
                JFXButton b1 = new JFXButton("NON");
                b0.setId("jfxbutton");
                b1.setId("jfxbutton");
                JFXDialogLayout l = new JFXDialogLayout();
                l.setBody(new Text("Voulez vous modifier cet evenement ?"));
                JFXDialog d1 = new JFXDialog(parentContainer, l, JFXDialog.DialogTransition.CENTER);
                b0.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
                {
                    Connection con = DbConnector.getConnection();
                    try {
                        Evenement e = new Evenement(getN(),nom.getText(), etat.getSelectionModel().getSelectedItem(), type.getSelectionModel().getSelectedItem(), date_debut.getValue(), date_fin.getValue(), adresse.getText(),Float.parseFloat(budget.getText()),desc.getText());
                        PreparedStatement Evenement = con.prepareStatement("update evenement set nom=?,etat=?,type=?,date_debut=to_date(?,'YYYY-MM-DD'),date_fin=to_date(?,'YYYY-MM-DD'),adresse=?,description=?,budget=? where num=?");
                        Evenement.setString(1, e.getNom());
                        Evenement.setString(2, e.getEtat());
                        Evenement.setString(3, e.getType());
                        Evenement.setString(4, e.getDate_debut().toString());
                        Evenement.setString(5, e.getDate_fin().toString());
                        Evenement.setString(6, e.getAdresse());
                        Evenement.setString(7, e.getDescription());
                        Evenement.setFloat(8, e.getBudget());
                        Evenement.setInt(9, e.getNum());
                        Evenement.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    d1.close();
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
            }
            else {
                if (!Validate.verifierchaine(nom.getText())) {
                    nom.setUnFocusColor(Color.RED);
                    lbl1.setVisible(true);
                }

                if (etat.getValue() == null) {
                    etat.setUnFocusColor(Color.RED);
                    lbl2.setVisible(true);
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
