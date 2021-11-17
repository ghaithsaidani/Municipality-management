package CONTROLLERS;

import DBCONNECTOR.DbConnector;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ConsulterOutil_Controller_DG extends ConsulterOutil_Controller_Admin {
    @FXML
    private TableView<Outil> out_table;

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;



    @FXML
    private Button b;

    @FXML
    private Button supp;

    @FXML
    private Button mod;


    @FXML
    void selected()
    {
        if(out_table.getSelectionModel().getSelectedItem()!=null) {
            supp.setVisible(true);
            mod.setVisible(true);
        }
        else {
            supp.setVisible(false);
            mod.setVisible(false);
        }
    }

    @FXML
    void exited() {
        out_table.getSelectionModel().clearSelection();
        supp.setVisible(false);
        mod.setVisible(false);

    }


    @FXML
    void open_modifier(ActionEvent event) throws Exception {
        try {
            Outil row = out_table.getSelectionModel().getSelectedItem();
            FXMLLoader fxml_loader = new FXMLLoader(getClass().getResource("../FXML/Modifier_Outil.fxml"));
            Parent root=fxml_loader.load();
            ModifierOutil_Controller am = fxml_loader.getController();
            am.showinfo(row.getType(), row.getEtat(), String.valueOf(row.getQuantité()));
            am.setN(row.getNum());

            Scene scene = mod.getScene();

            root.translateXProperty().set(scene.getWidth());
            parentContainer.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event1 -> {
                parentContainer.getChildren().remove(container);
            });
            timeline.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    void open_ajouter(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Ajouter_outil.fxml"));

        Scene scene = b.getScene();

        root.translateXProperty().set(scene.getWidth());
        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            parentContainer.getChildren().remove(container);
        });
        timeline.play();
    }


    @FXML
    void delete(ActionEvent event) {
        try {
            JFXButton b2 = new JFXButton("OUI");
            JFXButton b1 = new JFXButton("NON");
            b1.setId("jfxbutton");
            b2.setId("jfxbutton");
            JFXDialogLayout l = new JFXDialogLayout();
            l.setBody(new Text("Voulez vous supprimer cet outil ?"));
            JFXDialog d = new JFXDialog(parentContainer, l, JFXDialog.DialogTransition.CENTER);
            b2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
            {

                Connection con= DbConnector.getConnection();
                try{
                    Outil c = new Outil(out_table.getSelectionModel().getSelectedItem().getNum());
                    PreparedStatement pre= con.prepareStatement("delete from outil where num=?");
                    pre.setInt(1,c.getNum());
                    pre.executeUpdate();
                } catch (Exception e) {
                    System.out.println("connection not done!");
                }
                d.close();
                consulter();
                rechercher();
                mod.setVisible(false);
                supp.setVisible(false);
                TrayNotification tray=new TrayNotification();
                Image i=new Image(getClass().getResourceAsStream("../ICONS/icons8_remove_96px_1.png"));
                tray.setImage(i);
                tray.setTitle("Suppression avec succés");
                tray.setAnimationType(AnimationType.POPUP);
                tray.setRectangleFill(Color.web("#007AD9"));
                tray.showAndDismiss(Duration.seconds(1));
            });

            b1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
            {
                d.close();
            });
            l.setActions(b2, b1);
            d.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
