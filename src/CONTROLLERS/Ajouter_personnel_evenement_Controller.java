package CONTROLLERS;

import DBCONNECTOR.DbConnector;
import MODÉLES.Demande;

import MODÉLES.Employé;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Ajouter_personnel_evenement_Controller implements Initializable{
    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    private TextField filterField;

    @FXML
    private Button next;

    @FXML
    private ComboBox<String> combo;

    @FXML
    private TableView<Employé> table;

    @FXML
    private TableColumn<Employé, Integer> Id;

    @FXML
    private TableColumn<Employé, String> nom;

    @FXML
    private TableColumn<Employé, String> Prenom;

    @FXML
    private TableColumn<Employé, Integer> tel;

    @FXML
    private TableColumn<Employé, String> poste;

    @FXML
    private TableView<Employé> table1;

    @FXML
    private TableColumn<Employé, Integer> Id1;

    @FXML
    private TableColumn<Employé, String> nom1;

    @FXML
    private TableColumn<Employé, String> Prenom1;

    @FXML
    private TableColumn<Employé, Integer> tel1;

    @FXML
    private TableColumn<Employé, String> poste1;

    public static ObservableList<Employé> emplist(){

        Connection con= DbConnector.getConnection();
        ObservableList<Employé> list= FXCollections.observableArrayList();
        try{

            ResultSet rs=con.createStatement().executeQuery("select * from personnel");
            while(rs.next())
            {
                Employé emp=new Employé(rs.getInt("id"),rs.getString("nom"), rs.getNString("prenom"),rs.getInt("num_tel"),rs.getString("poste"),rs.getString("etat"));
                list.add(emp);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    ObservableList<Employé> ListE = FXCollections.observableArrayList();
    ObservableList<Employé> ListE1 = FXCollections.observableArrayList();

    public void consulter() {
        Id.setCellValueFactory(new PropertyValueFactory<Employé, Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<Employé, String>("nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<Employé, String>("prename"));
        tel.setCellValueFactory(new PropertyValueFactory<Employé, Integer>("tel"));
        poste.setCellValueFactory(new PropertyValueFactory<Employé, String>("poste"));


        ListE = emplist();
        table.setItems(ListE);


    }


    @FXML
    void add(ActionEvent event) {
        Id1.setCellValueFactory(new PropertyValueFactory<Employé, Integer>("id"));
        nom1.setCellValueFactory(new PropertyValueFactory<Employé, String>("nom"));
        Prenom1.setCellValueFactory(new PropertyValueFactory<Employé, String>("prename"));
        tel1.setCellValueFactory(new PropertyValueFactory<Employé, Integer>("tel"));
        poste1.setCellValueFactory(new PropertyValueFactory<Employé, String>("poste"));
        ListE1.add(table.getSelectionModel().getSelectedItem());
        table1.setItems(ListE1);
    }


    ObservableList<String> search_cat = FXCollections.observableArrayList("Id", "Name", "Prename", "Tel");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combo.setItems(search_cat);
        consulter();

        table.getSelectionModel().clearSelection();
        table1.getSelectionModel().clearSelection();
    }

    public ObservableList<Employé> getListE1() {
        return ListE1;
    }

    @FXML
    void suiv(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Ajouter_OutilEvenement.fxml"));

        Scene scene = next.getScene();

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
}
