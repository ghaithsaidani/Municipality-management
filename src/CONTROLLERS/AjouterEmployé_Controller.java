package CONTROLLERS;

import DBCONNECTOR.DbConnector;
import MODÉLES.Employé;
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
import javafx.scene.paint.Color;
import EVENTS.Events;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import VALIDATION.Validate;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;


public class AjouterEmployé_Controller extends Events implements Initializable {

    @FXML
    private StackPane parentContainer;
    @FXML
    private JFXTextField tf1;

    @FXML
    private JFXTextField tf3;

    @FXML
    private JFXTextField tf4;

    @FXML
    private JFXTextField tf5;

    @FXML
    private JFXTextField tf6;

    @FXML
    private JFXTextField tf2;

    @FXML
    private JFXTextField tf7;

    @FXML
    private JFXComboBox<String> tf8;

    @FXML
    private Spinner<Integer> sp;

    @FXML
    private DatePicker d;

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
    private Label lbl9;

    @FXML
    private Label lbl8;





    SpinnerValueFactory<Integer> gradevalue=new SpinnerValueFactory.IntegerSpinnerValueFactory(0,50);

    ObservableList<String> et= FXCollections.observableArrayList("Maire","Directeur administratif","Interet technique","Directeur general","Manager de resources humaines","Simple Employé");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tf8.setItems(et);
        sp.setValueFactory(gradevalue);
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
        tf1.setUnFocusColor(Color.BLACK);
        lbl1.setVisible(false);
    }

    @FXML
    void unfocus2() {
        tf2.setUnFocusColor(Color.BLACK);
        lbl2.setVisible(false);
    }

    @FXML
    void unfocus3() {
        tf3.setUnFocusColor(Color.BLACK);
        lbl3.setVisible(false);
    }

    @FXML
    void unfocus4() {
        tf4.setUnFocusColor(Color.BLACK);
        lbl4.setVisible(false);
    }

    @FXML
    void unfocus5() {
        tf5.setUnFocusColor(Color.BLACK);
        lbl5.setVisible(false);
    }

    @FXML
    void unfocus6() {
        tf6.setUnFocusColor(Color.BLACK);
        lbl6.setVisible(false);
    }

    @FXML
    void unfocus7() {
        lbl7.setVisible(false);
    }

    @FXML
    void unfocus8() {
        tf7.setUnFocusColor(Color.BLACK);
        lbl8.setVisible(false);
    }

    @FXML
    void unfocus9() {
        tf8.setUnFocusColor(Color.BLACK);
        lbl9.setVisible(false);
    }


    @FXML
    void add(ActionEvent event){

        try{

            if(Validate.verifierchaine(tf1.getText()) && Validate.verifierchaine(tf2.getText()) && Validate.verifierId(tf3.getText()) && Validate.verifierTel(tf6.getText()) && Validate.verifieremail(tf5.getText()) && Validate.verifierchaine(tf7.getText()) && tf8.getValue()!=null && !tf4.getText().isEmpty() && d.getValue()!=null && Validate.verifierdatenaissance(d.getValue()))
            {

                JFXButton b0 = new JFXButton("OUI");
                JFXButton b1 = new JFXButton("NON");
                b0.setId("jfxbutton");
                b1.setId("jfxbutton");
                JFXDialogLayout l = new JFXDialogLayout();
                l.setBody(new Text("Voulez vous créer cet Employée ?"));
                JFXDialog d1 = new JFXDialog(parentContainer, l, JFXDialog.DialogTransition.CENTER);
                b0.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
                {
                    Connection con= DbConnector.getConnection();
                    try {
                        Employé emp=new Employé(tf1.getText(),tf2.getText(),Integer.parseInt(tf3.getText()), d.getValue(),tf4.getText(),tf5.getText(),Integer.parseInt(tf6.getText()),tf7.getText(),tf8.getSelectionModel().getSelectedItem(),sp.getValue(),"Disponible");
                        PreparedStatement pre= con.prepareStatement("insert into personnel values(?,?,?,(to_date(?,'YYYY-MM-DD')),?,?,?,?,?,?,?)");
                        pre.setString(1,emp.getNom());
                        pre.setString(2,emp.getPrename());
                        pre.setInt(3,emp.getId());
                        pre.setString(4,emp.getDate().toString());
                        pre.setString(5,emp.getAdr());
                        pre.setString(6,emp.getAdr_mail());
                        pre.setInt(7,emp.getTel());
                        pre.setString(8,emp.getDiplome());
                        pre.setString(9,emp.getPoste());
                        pre.setInt(10,emp.getAnne());
                        pre.setString(11,emp.getEtat());
                        pre.executeUpdate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    tf1.clear();
                    tf2.clear();
                    tf3.clear();
                    tf4.clear();
                    tf5.clear();
                    tf6.clear();
                    tf7.clear();
                    tf8.setValue(null);
                    SpinnerValueFactory<Integer> spvf=new SpinnerValueFactory.IntegerSpinnerValueFactory(0,50);
                    sp.setValueFactory(spvf);
                    d.setValue(null);
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
                    Image i=new Image(getClass().getResourceAsStream("../ICONS/icons8_verified_account_96px.png"));
                    tray.setImage(i);
                    tray.setTitle("AJOUT DE L'EMPLOYÉ AVEC SUCCÉS");
                    tray.setAnimationType(AnimationType.POPUP);
                    tray.setRectangleFill(Color.web("#19A05A"));
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
                if(!Validate.verifierchaine(tf1.getText()))
                {
                    tf1.setUnFocusColor(Color.RED);
                    lbl1.setVisible(true);
                }

                if(!Validate.verifierchaine(tf2.getText()))
                {
                    tf2.setUnFocusColor(Color.RED);
                    lbl2.setVisible(true);
                }
                if(!Validate.verifierchaine(tf7.getText()))
                {
                    tf7.setUnFocusColor(Color.RED);
                    lbl8.setVisible(true);
                }
                if(tf8.getValue()==null)
                {
                    tf8.setUnFocusColor(Color.RED);
                    lbl9.setVisible(true);
                }

                if(d.getValue()==null || !Validate.verifierdatenaissance(d.getValue()))
                {
                    lbl7.setVisible(true);
                }

                if(!Validate.verifierId(tf3.getText()))
                {
                    tf3.setUnFocusColor(Color.RED);
                    lbl3.setVisible(true);
                }

                if(!Validate.verifierTel(tf6.getText()))
                {
                    tf6.setUnFocusColor(Color.RED);
                    lbl6.setVisible(true);
                }

                if(!Validate.verifieremail(tf5.getText()))
                {
                    tf5.setUnFocusColor(Color.RED);
                    lbl5.setVisible(true);
                }

                if(tf4.getText().isEmpty())
                {
                    tf4.setUnFocusColor(Color.RED);
                    lbl4.setVisible(true);
                }
            }



        } catch (Exception e) {
            System.out.println("error 2");
        }


    }



    @FXML
    void back(ActionEvent e)throws Exception{
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
