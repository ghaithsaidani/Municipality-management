package CONTROLLERS;

import DBCONNECTOR.DbConnector;
import MODÉLES.Outil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ConsulterOutil_Controller_Admin implements Initializable {

    @FXML
    private TableView<Outil> out_table;

    @FXML
    private TableColumn<Outil, Integer> num;

    @FXML
    private TableColumn<Outil, String> type;

    @FXML
    private TableColumn<Outil, String> etat;

    @FXML
    private TableColumn<Outil, Integer> qte;

    @FXML
    private TextField filtred_field;

    @FXML
    private ComboBox<String> cmb;

    @FXML
    private Button supp;

    @FXML
    private Button mod;

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

    public void consulter()
    {
        num.setCellValueFactory(new PropertyValueFactory<Outil,Integer>("num"));
        type.setCellValueFactory(new PropertyValueFactory<Outil,String>("type"));
        etat.setCellValueFactory(new PropertyValueFactory<Outil,String>("etat"));
        qte.setCellValueFactory(new PropertyValueFactory<Outil,Integer>("quantité"));

        oo=outlist();
        out_table.setItems(oo);
    }

    String combo() {
        return cmb.getSelectionModel().getSelectedItem().toString();
    }

    public void rechercher() {
        ObservableList<Outil> dataList = outlist();
        out_table.setItems(dataList);
        FilteredList<Outil> filteredData = new FilteredList<>(dataList, b -> true);
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

                } else if (combo().equals("type")) {
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
                    if (String.valueOf(person.getQuantité()).indexOf(lowerCaseFilter) != -1)
                        return true;
                    else
                        return false;

                }
                else {
                    return false;
                }

            });
        });
        SortedList<Outil> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(out_table.comparatorProperty());
        out_table.setItems(sortedData);

    }

    ObservableList cat=FXCollections.observableArrayList("numero","type","etat","quantité");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmb.setItems(cat);
        consulter();
        rechercher();
        supp.setVisible(false);
        mod.setVisible(false);
        out_table.getSelectionModel().clearSelection();
    }




}
