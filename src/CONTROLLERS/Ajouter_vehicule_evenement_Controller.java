package CONTROLLERS;

import MODÉLES.Employé;
import MODÉLES.Vehicule;
import VALIDATION.Validate;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.Initializable;

import DBCONNECTOR.DbConnector;

import MODÉLES.Outil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
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

public class Ajouter_vehicule_evenement_Controller implements Initializable {

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    private TableView<Vehicule> veh_table;

    @FXML
    private TableColumn<Vehicule, Integer> num;

    @FXML
    private TableColumn<Vehicule, String> matricule;

    @FXML
    private TableColumn<Vehicule, String> type;

    @FXML
    private TableColumn<Vehicule, String> etat;

    @FXML
    private TableColumn<Vehicule, Integer> annee;

    @FXML
    private TableView<Vehicule> veh_table1;

    @FXML
    private TableColumn<Vehicule, Integer> num1;

    @FXML
    private TableColumn<Vehicule, String> matricule1;

    @FXML
    private TableColumn<Vehicule, String> type1;

    @FXML
    private TableColumn<Vehicule, String> etat1;

    @FXML
    private TableColumn<Vehicule, Integer> annee1;

    @FXML
    private TextField filtred_field;

    @FXML
    private ComboBox<String> combo;

    public static ObservableList<Vehicule> vehlist(){

        Connection con= DbConnector.getConnection();
        ObservableList<Vehicule> list= FXCollections.observableArrayList();
        try{

            ResultSet rs=con.createStatement().executeQuery("select * from vehicule  order by num asc");
            while(rs.next())
            {
                Vehicule o=new Vehicule(rs.getInt("num"),rs.getString("matricule"),rs.getString("type"), rs.getString("etat"), rs.getInt("annee"));
                list.add(o);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    ObservableList<Vehicule> oo=FXCollections.observableArrayList();
    ObservableList<Vehicule> oo1=FXCollections.observableArrayList();

    public void consulter()
    {
        num.setCellValueFactory(new PropertyValueFactory<Vehicule,Integer>("num"));
        matricule.setCellValueFactory(new PropertyValueFactory<Vehicule,String>("matricule"));
        type.setCellValueFactory(new PropertyValueFactory<Vehicule,String>("type"));
        etat.setCellValueFactory(new PropertyValueFactory<Vehicule,String>("etat"));
        annee.setCellValueFactory(new PropertyValueFactory<Vehicule,Integer>("annee"));

        oo= vehlist();
        veh_table.setItems(oo);
    }

    @FXML
    void add(ActionEvent event) {
        num1.setCellValueFactory(new PropertyValueFactory<Vehicule,Integer>("num"));
        matricule1.setCellValueFactory(new PropertyValueFactory<Vehicule,String>("matricule"));
        type1.setCellValueFactory(new PropertyValueFactory<Vehicule,String>("type"));
        etat1.setCellValueFactory(new PropertyValueFactory<Vehicule,String>("etat"));
        annee1.setCellValueFactory(new PropertyValueFactory<Vehicule,Integer>("annee"));

        oo1.add(veh_table.getSelectionModel().getSelectedItem());
        veh_table1.setItems(oo1);
    }


    ObservableList cat=FXCollections.observableArrayList("numero","matricule","type","etat","quantité");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combo.setItems(cat);
        consulter();
        veh_table.getSelectionModel().clearSelection();
        veh_table1.getSelectionModel().clearSelection();
    }

    @FXML
    void term(ActionEvent event) {
        try{

                JFXButton b0 = new JFXButton("OUI");
                JFXButton b1 = new JFXButton("NON");
                b0.setId("jfxbutton");
                b1.setId("jfxbutton");
                JFXDialogLayout l = new JFXDialogLayout();
                l.setBody(new Text("Voulez vous créer cet Evenement ?"));
                JFXDialog d1 = new JFXDialog(parentContainer, l, JFXDialog.DialogTransition.CENTER);
                b0.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
                {
                    Connection con= DbConnector.getConnection();
                    try {
                        AjouterEvenement_controller aec=new AjouterEvenement_controller();
                        Ajouter_personnel_evenement_Controller apec=new Ajouter_personnel_evenement_Controller();
                        Ajouter_outil_evenement_Controller aoec=new Ajouter_outil_evenement_Controller();
                        System.out.println(21+" "+apec.getListE1().get(0).getId()+" "+aoec.getOo1().get(0).getNum()+" "+oo1.get(0).getNum());
                        /*PreparedStatement pre= con.prepareStatement("insert into planif_event values(?,?,?,?)");
                        pre.setInt(1,aec.eventcreated().getNum());
                        pre.setInt(2,apec.getListE1().get(1).getId());
                        pre.setInt(3,aoec.getOo1().get(1).getNum());
                        pre.setInt(4,oo1.get(1).getNum());
                        pre.executeUpdate();*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SpinnerValueFactory<Integer> spvf=new SpinnerValueFactory.IntegerSpinnerValueFactory(0,50);
                    d1.close();
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

        } catch (Exception e) {
            System.out.println("error 2");
        }
    }
}
