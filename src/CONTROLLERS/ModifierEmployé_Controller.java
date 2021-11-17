package CONTROLLERS;

import DBCONNECTOR.DbConnector;
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import EVENTS.Events;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;


public class ModifierEmployé_Controller extends Events implements Initializable{

    @FXML
    private StackPane parentContainer;

    @FXML
    private JFXTextField tf8;

    @FXML
    private JFXTextField tf7;

    @FXML
    private JFXTextField tf10;

    @FXML
    private JFXTextField tf12;

    @FXML
    private JFXTextField tf13;

    @FXML
    private JFXTextField tf14;

    @FXML
    private JFXTextField tf15;

    @FXML
    private JFXTextField tf11;

    @FXML
    private JFXTextField tf16;

    @FXML
    private JFXComboBox<String> tf17;

    @FXML
    private DatePicker date_pick;

    @FXML
    private Spinner<Integer> sp1;

    @FXML
    private AnchorPane container;

    @FXML
    private Button ba;

    @FXML
    private Label lbl1;

    @FXML
    private Label lbl2;

    @FXML
    private Label lbl3;

    @FXML
    private Label lbl4;

    @FXML
    private Label lbl7;

    @FXML
    private Label lbl6;

    @FXML
    private Label lbl5;

    @FXML
    private Label lbl8;

    @FXML
    private Label lbl9;



    SpinnerValueFactory<Integer> gradevalue=new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100);
    ObservableList<String> et= FXCollections.observableArrayList("Maire","Directeur administratif","Interet technique","Directeur general","Manager de resources humaines","Simple Employé");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tf17.setItems(et);
        sp1.setValueFactory(gradevalue);
        lbl1.setVisible(false);
        lbl2.setVisible(false);
        lbl3.setVisible(false);
        lbl4.setVisible(false);
        lbl5.setVisible(false);
        lbl6.setVisible(false);
        lbl7.setVisible(false);
        lbl8.setVisible(false);
        lbl9.setVisible(false);
    }

    @FXML
    void unfocus1() {
        tf10.setUnFocusColor(Color.BLACK);
        lbl1.setVisible(false);
    }

    @FXML
    void unfocus2() {
        tf11.setUnFocusColor(Color.BLACK);
        lbl2.setVisible(false);
    }

    @FXML
    void unfocus3() {
        tf12.setUnFocusColor(Color.BLACK);
        lbl3.setVisible(false);
    }

    @FXML
    void unfocus4() {
        tf13.setUnFocusColor(Color.BLACK);
        lbl4.setVisible(false);
    }

    @FXML
    void unfocus5() {
        tf14.setUnFocusColor(Color.BLACK);
        lbl5.setVisible(false);
    }

    @FXML
    void unfocus6() {
        tf15.setUnFocusColor(Color.BLACK);
        lbl6.setVisible(false);
    }

    @FXML
    void unfocus7() {
        tf16.setUnFocusColor(Color.BLACK);
    }


    @FXML
    void unfocus8() {
        tf16.setUnFocusColor(Color.BLACK);
        lbl8.setVisible(false);
    }

    @FXML
    void unfocus9() {
        tf17.setUnFocusColor(Color.BLACK);
        lbl9.setVisible(false);
    }

    void showinfo(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8, int anne, String s10)
    {
        tf10.setText(s1);
        tf11.setText(s2);
        tf12.setText(s3);
        tf13.setText(s4);
        tf14.setText(s5);
        tf15.setText(s6);
        tf16.setText(s7);
        tf17.setValue(s8);
        SpinnerValueFactory<Integer> spvf=new SpinnerValueFactory.IntegerSpinnerValueFactory(0,50,anne);
        sp1.setValueFactory(spvf);
        LocalDate ld=LocalDate.of(Integer.parseInt(s10.substring(0,4)),Integer.parseInt(s10.substring(5,7)),Integer.parseInt(s10.substring(8,10)));
        date_pick.setValue(ld);

    }

    @FXML
    void modify(ActionEvent event) {
        try {
            if(Validate.verifierchaine(tf10.getText()) && Validate.verifierchaine(tf11.getText()) && Validate.verifierId(tf12.getText()) && Validate.verifierTel(tf15.getText()) && Validate.verifieremail(tf14.getText()) && Validate.verifierchaine(tf16.getText()) && tf17.getValue()!=null)
            {

                JFXButton b0 = new JFXButton("OUI");
                JFXButton b1 = new JFXButton("NON");
                b0.setId("jfxbutton");
                b1.setId("jfxbutton");
                JFXDialogLayout l = new JFXDialogLayout();
                l.setBody(new Text("Voulez vous modifier cet Employée ?"));
                JFXDialog d1 = new JFXDialog(parentContainer, l, JFXDialog.DialogTransition.CENTER);
                b0.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
                {
                    Connection con= DbConnector.getConnection();
                    try {
                        Employé emp=new Employé(tf10.getText(),tf11.getText(),Integer.parseInt(tf12.getText()), date_pick.getValue(),tf13.getText(),tf14.getText(),Integer.parseInt(tf15.getText()),tf16.getText(),tf17.getSelectionModel().getSelectedItem(),sp1.getValue(),"Disponible");
                        PreparedStatement pre = con.prepareStatement("update personnel set nom=?,prenom=?,date_naissance=to_date(?,'YYYY-MM-DD'),adr=?,adr_mail=?,num_tel=?,diplome=?,poste=?,annee_exp=? where id=?");
                        pre.setString(1,emp.getNom());
                        pre.setString(2,emp.getPrename());
                        pre.setString(3,emp.getDate().toString());
                        pre.setString(4,emp.getAdr());
                        pre.setString(5,emp.getAdr_mail());
                        pre.setInt(6,emp.getTel());
                        pre.setString(7,emp.getDiplome());
                        pre.setString(8,emp.getPoste());
                        pre.setInt(9,emp.getAnne());
                        pre.setInt(10,emp.getId());
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
                    unfocus8();
                    unfocus9();
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

            else{
                if(!Validate.verifierchaine(tf10.getText()))
                {
                    tf10.setUnFocusColor(Color.RED);
                    lbl1.setVisible(true);
                }

                if(!Validate.verifierchaine(tf11.getText()))
                {
                    tf11.setUnFocusColor(Color.RED);
                    lbl2.setVisible(true);
                }

                if(!Validate.verifierId(tf12.getText()))
                {
                    tf12.setUnFocusColor(Color.RED);
                    lbl3.setVisible(true);
                }

                if(!Validate.verifierTel(tf15.getText()))
                {
                    tf15.setUnFocusColor(Color.RED);
                    lbl6.setVisible(true);
                }

                if(!Validate.verifieremail(tf14.getText()))
                {
                    tf14.setUnFocusColor(Color.RED);
                    lbl5.setVisible(true);
                }

                if(!Validate.verifierchaine(tf16.getText()))
                {
                    tf16.setUnFocusColor(Color.RED);
                    lbl8.setVisible(true);
                }
                if(tf17.getValue()==null)
                {
                    tf17.setUnFocusColor(Color.RED);
                    lbl9.setVisible(true);
                }
            }
        }
        catch (Exception e) {
            System.out.println("probleme de modification");
        }
    }

    @FXML
    void back(ActionEvent e) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Consulter1.fxml"));

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
