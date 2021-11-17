package CONTROLLERS;

import DBCONNECTOR.DbConnector;
import INTERFACES_GRAPHIQUES.MultipleFxml;
import INTERFACES_GRAPHIQUES.Interfacegraphique1;
import MODÉLES.Outil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import java.net.URL;
import java.util.ResourceBundle;
import EVENTS.Events;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;


public class Consulter_Controller extends Events implements Initializable {


    @FXML
    private StackPane stk;


    @FXML
    private AnchorPane container;

    @FXML
    private StackPane parentContainer;

    private StackPane stk1;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            MultipleFxml obj = new MultipleFxml();
            stk1 = obj.stack("../FXML/Consulter1.fxml");
            stk.setAlignment(Pos.TOP_RIGHT);
            stk.getChildren().add(stk1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void home(ActionEvent event) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Consulter1.fxml"));
            Scene scene = container.getScene();
                root.translateXProperty().set(scene.getWidth());
                stk.getChildren().add(root);
                    Timeline timeline = new Timeline();
                    KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
                    KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                    timeline.getKeyFrames().add(kf);
                    timeline.setOnFinished(event1 -> {
                        stk.getChildren().remove(stk1);
                    });
                    timeline.play();





    }



        @FXML
        void open_comptes(ActionEvent event) throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/Consulter_comptes.fxml"));

                Scene scene = container.getScene();

                root.translateXProperty().set(scene.getWidth());
                stk.getChildren().add(root);
                    Timeline timeline = new Timeline();
                    KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
                    KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                    timeline.getKeyFrames().add(kf);
                    timeline.setOnFinished(event1 -> {
                        stk.getChildren().remove(stk1);
                    });
                    timeline.play();


        }

    @FXML
    void open_vehicules(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Consulter_Vehicule.fxml"));

            Scene scene = container.getScene();

            root.translateXProperty().set(scene.getWidth());
            stk.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event1 -> {
                parentContainer.getChildren().remove(stk1);
            });
            timeline.play();
        }

    @FXML
    void open_outils(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Consulter_Outil.fxml"));
            Scene scene = container.getScene();

            root.translateXProperty().set(scene.getWidth());
            stk.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event1 -> {
                parentContainer.getChildren().remove(stk1);
            });
            timeline.play();

    }

    @FXML
    void open_evenements(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Consulter_Evenement.fxml"));

        Scene scene = container.getScene();

        root.translateXProperty().set(scene.getWidth());
        stk.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            parentContainer.getChildren().remove(stk1);
        });
        timeline.play();

    }

    @FXML
    void open_reclamations(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Consulter_Reclamations.fxml"));

        Scene scene = container.getScene();

        root.translateXProperty().set(scene.getWidth());
        stk.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            parentContainer.getChildren().remove(stk1);
        });
        timeline.play();
    }


    @FXML
    void open_demandes(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Consulter_Demande.fxml"));

        Scene scene = container.getScene();

        root.translateXProperty().set(scene.getWidth());
        stk.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            parentContainer.getChildren().remove(stk1);
        });
        timeline.play();
    }

    @FXML
    void open_doleances(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Consulter_Doleance.fxml"));

        Scene scene = container.getScene();

        root.translateXProperty().set(scene.getWidth());
        stk.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            parentContainer.getChildren().remove(stk1);
        });
        timeline.play();
    }

    @FXML
    void open_depense(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Consulter_Depense.fxml"));

        Scene scene = container.getScene();

        root.translateXProperty().set(scene.getWidth());
        stk.getChildren().add(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            stk.getChildren().remove(stk1);
        });
        timeline.play();


    }


    @FXML
    void open_revenu(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Consulter_Revenu.fxml"));

        Scene scene = container.getScene();

        root.translateXProperty().set(scene.getWidth());
        stk.getChildren().add(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            stk.getChildren().remove(stk1);
        });
        timeline.play();


    }





    @FXML
    void deconnecter(ActionEvent event){
        JFXButton b2 = new JFXButton("OUI");
        JFXButton b1 = new JFXButton("NON");
        b1.setId("jfxbutton");
        b2.setId("jfxbutton");
        JFXDialogLayout l = new JFXDialogLayout();
        l.setBody(new Text("Voulez vous deconnecter ?"));
        JFXDialog d = new JFXDialog(stk, l, JFXDialog.DialogTransition.CENTER);
        b2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
        {

            try {
                Interfacegraphique1 ig = new Interfacegraphique1();
                ig.changerscene("../FXML/Connecter.fxml");
                TrayNotification tray=new TrayNotification();
                tray.setTitle("Deconnexion avec succés");
                Image im=new Image(getClass().getResourceAsStream("../ICONS/icons8_disconnected_96px.png"));
                tray.setImage(im);
                tray.setAnimationType(AnimationType.POPUP);
                tray.setRectangleFill(Color.web("#A1AAB3"));
                tray.showAndDismiss(Duration.seconds(1));

            } catch (Exception e) {
                e.printStackTrace();
            }
            d.close();
        });

        b1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev1) ->
        {
            d.close();
        });
        l.setActions(b2, b1);
        d.show();


    }
}















    








