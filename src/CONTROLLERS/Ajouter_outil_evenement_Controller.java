package CONTROLLERS;

import DBCONNECTOR.DbConnector;

import MODÉLES.Outil;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Ajouter_outil_evenement_Controller implements Initializable{

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
    private TableView<Outil> out_table;

    @FXML
    private TableColumn<Outil,Integer> num;

    @FXML
    private TableColumn<Outil,String> type;

    @FXML
    private TableColumn<Outil,String> etat;

    @FXML
    private TableColumn<Outil,Integer> qte;

    @FXML
    private TableView<Outil> out_table1;

    @FXML
    private TableColumn<Outil,Integer> num1;

    @FXML
    private TableColumn<Outil,String> type1;

    @FXML
    private TableColumn<Outil,String> etat1;

    @FXML
    private TableColumn<Outil,Integer> qte1;

    public static ObservableList<Outil> outlist(){

        Connection con= DbConnector.getConnection();
        ObservableList<Outil> list= FXCollections.observableArrayList();
        try{

            ResultSet rs=con.createStatement().executeQuery("select * from outil  order by num asc");
            while(rs.next())
            {
                Outil o=new Outil(rs.getInt("num"),rs.getString("type"), rs.getString("etat"), rs.getInt("quantité"));
                list.add(o);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    ObservableList<Outil> oo=FXCollections.observableArrayList();
    ObservableList<Outil> oo1=FXCollections.observableArrayList();

    public void consulter()
    {
        num.setCellValueFactory(new PropertyValueFactory<Outil,Integer>("num"));
        type.setCellValueFactory(new PropertyValueFactory<Outil,String>("type"));
        etat.setCellValueFactory(new PropertyValueFactory<Outil,String>("etat"));
        qte.setCellValueFactory(new PropertyValueFactory<Outil,Integer>("quantité"));

        oo=outlist();
        out_table.setItems(oo);
    }

    @FXML
    void add(ActionEvent event) {
        num1.setCellValueFactory(new PropertyValueFactory<Outil,Integer>("num"));
        type1.setCellValueFactory(new PropertyValueFactory<Outil,String>("type"));
        etat1.setCellValueFactory(new PropertyValueFactory<Outil,String>("etat"));
        qte1.setCellValueFactory(new PropertyValueFactory<Outil,Integer>("quantité"));

        oo1.add(out_table.getSelectionModel().getSelectedItem());
        out_table1.setItems(oo1);
    }

    ObservableList cat=FXCollections.observableArrayList("numero","type","etat","quantité");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combo.setItems(cat);
        consulter();
        out_table.getSelectionModel().clearSelection();
        out_table1.getSelectionModel().clearSelection();
    }

    public ObservableList<Outil> getOo1() {
        return oo1;
    }

    @FXML
    void suiv(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Ajouter_VehEvenement.fxml"));

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
