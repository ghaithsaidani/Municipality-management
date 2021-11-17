package CONTROLLERS;

import DBCONNECTOR.DbConnector;
import MODÉLES.Vehicule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ConsulterVehicule_Controller_Admin implements Initializable {

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
    private TextField filtred_field;

    @FXML
    private ComboBox<String> cmb;

    @FXML
    private Button supp;

    @FXML
    private Button mod;

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

    String combo() {
        return cmb.getSelectionModel().getSelectedItem().toString();
    }

    public void rechercher() {
        ObservableList<Vehicule> dataList =vehlist();
        veh_table.setItems(dataList);
        FilteredList<Vehicule> filteredData = new FilteredList<>(dataList, b -> true);
        filtred_field.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();


                if (combo().equals("numero")) {
                    if (String.valueOf(person.getNum()).toLowerCase().indexOf(lowerCaseFilter) != -1)
                        return true;
                    else
                        return false;

                }else if (combo().equals("matricule")) {
                    if (String.valueOf(person.getNum()).toLowerCase().indexOf(lowerCaseFilter) != -1)
                        return true;
                    else
                        return false;

                }
                else if (combo().equals("type")) {
                    if (person.getType().toLowerCase().indexOf(lowerCaseFilter) != -1)
                        return true;
                    else
                        return false;

                } else if (combo().equals("etat")) {
                    if (person.getEtat().toLowerCase().indexOf(lowerCaseFilter) != -1)
                        return true;
                    else
                        return false;

                } else if (combo().equals("quantité")) {
                    if (String.valueOf(person.getAnnee()).indexOf(lowerCaseFilter) != -1)
                        return true;
                    else
                        return false;

                }
                else {
                    return false;
                }

            });
        });
        SortedList<Vehicule> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(veh_table.comparatorProperty());
        veh_table.setItems(sortedData);

    }

    ObservableList cat=FXCollections.observableArrayList("numero","matricule","type","etat","quantité");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmb.setItems(cat);
        consulter();
        rechercher();
        supp.setVisible(false);
        mod.setVisible(false);
        veh_table.getSelectionModel().clearSelection();
    }




}
