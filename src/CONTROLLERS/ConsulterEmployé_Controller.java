package CONTROLLERS;

import DBCONNECTOR.DbConnector;
import EVENTS.Events;
import MODÉLES.Employé;
import MODÉLES.Outil;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ConsulterEmployé_Controller extends Events implements Initializable {

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<Employé> table;

    @FXML
    private TableColumn<Employé, String> nom;

    @FXML
    private TableColumn<Employé, String> Prenom;

    @FXML
    private TableColumn<Employé, Integer> Id;

    @FXML
    private TableColumn<Employé, String> Adress;

    @FXML
    private TableColumn<Employé, String> mail_adress;

    @FXML
    private TableColumn<Employé, Integer> Phone_number;

    @FXML
    private TableColumn<Employé, String> Diplome;

    @FXML
    private TableColumn<Employé, Integer> année;

    @FXML
    private TableColumn<Employé, String> poste;

    @FXML
    private TableColumn<Employé, String> date_of_birth;

    @FXML
    private ComboBox combo;

    @FXML
    private Button supp;

    @FXML
    private Button mod;

    @FXML
    private Button b;

    public static ObservableList<Employé> emplist(){

        Connection con= DbConnector.getConnection();
        ObservableList<Employé> list= FXCollections.observableArrayList();
        try{

            ResultSet rs=con.createStatement().executeQuery("select * from personnel");
            while(rs.next())
            {
                Employé emp=new Employé(rs.getString("nom"), rs.getNString("prenom"), rs.getInt("id"),rs.getDate("date_naissance").toLocalDate(),rs.getString("adr"),rs.getString("adr_mail"), rs.getInt("num_tel"), rs.getString("diplome"),rs.getString("poste"),rs.getInt("annee_exp"),rs.getString("etat"));
                list.add(emp);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }


    ObservableList<Employé> ListE = FXCollections.observableArrayList();

    public void consulter() {
        Id.setCellValueFactory(new PropertyValueFactory<Employé, Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<Employé, String>("nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<Employé, String>("prename"));
        date_of_birth.setCellValueFactory(new PropertyValueFactory<Employé, String>("date"));
        Adress.setCellValueFactory(new PropertyValueFactory<Employé, String>("adr"));
        mail_adress.setCellValueFactory(new PropertyValueFactory<Employé, String>("adr_mail"));
        Phone_number.setCellValueFactory(new PropertyValueFactory<Employé, Integer>("tel"));
        Diplome.setCellValueFactory(new PropertyValueFactory<Employé, String>("diplome"));
        poste.setCellValueFactory(new PropertyValueFactory<Employé, String>("poste"));
        année.setCellValueFactory(new PropertyValueFactory<Employé, Integer>("anne"));

        ListE = emplist();
        table.setItems(ListE);


    }

    String combo() {
        return combo.getSelectionModel().getSelectedItem().toString();
    }

    public void rechercher() {

        ObservableList<Employé> dataList = emplist();
        table.setItems(dataList);
        FilteredList<Employé> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if(combo.getValue()==null){
                    JFXButton b1 = new JFXButton("ok");
                    b1.setId("jfxbutton");
                    JFXDialogLayout l = new JFXDialogLayout();
                    l.setBody(new Text("vous pouvez selectionnez une categorie avant de rechercher !"));
                    JFXDialog d = new JFXDialog(parentContainer, l, JFXDialog.DialogTransition.CENTER);
                    b1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
                    {
                        d.close();
                    });
                    l.setActions( b1);
                    d.show();

                    System.out.println(filterField.isFocused());
                }
                else if(combo.getValue()!=null){
                    if (combo().equals("Id")) {
                        if (String.valueOf(person.getId()).toLowerCase().indexOf(lowerCaseFilter) != -1)
                            return true;
                        else
                            return false;

                    } else if (combo().equals("Name")) {
                        if (person.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1)
                            return true;
                        else
                            return false;

                    } else if (combo().equals("Prename")) {
                        if (person.getPrename().toLowerCase().indexOf(lowerCaseFilter) != -1)
                            return true;
                        else
                            return false;

                    } else if (combo().equals("Tel")) {
                        if (String.valueOf(person.getTel()).indexOf(lowerCaseFilter) != -1)
                            return true;
                        else
                            return false;

                    }



                }
                    return false;
            });
        });

        SortedList<Employé> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

    }

    ObservableList<String> search_cat = FXCollections.observableArrayList("Id", "Name", "Prename", "Tel");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combo.setItems(search_cat);
        supp.setVisible(false);
        mod.setVisible(false);
        consulter();
        rechercher();

        table.getSelectionModel().clearSelection();

    }




    @FXML
    void open_ajouter(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Ajouter.fxml"));

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
    void selected()
    {
        if(table.getSelectionModel().getSelectedItem()!=null) {
            supp.setVisible(true);
            mod.setVisible(true);
        }

    }

    @FXML
    void exited() {
        table.getSelectionModel().clearSelection();
        supp.setVisible(false);
        mod.setVisible(false);

    }


    @FXML
    void open_modifier(ActionEvent event) throws Exception {
        try {
            Employé row = table.getSelectionModel().getSelectedItem();
            FXMLLoader fxml_loader = new FXMLLoader(getClass().getResource("../FXML/Modifier.fxml"));
            Parent root=fxml_loader.load();
            ModifierEmployé_Controller am = fxml_loader.getController();
            am.showinfo(row.getNom(),row.getPrename(),String.valueOf(row.getId()),row.getAdr(),row.getAdr_mail(),String.valueOf(row.getTel()),row.getDiplome(),row.getPoste(),row.getAnne(),row.getDate().toString());

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
    void delete(ActionEvent event) {
        try {
            JFXButton b2 = new JFXButton("OUI");
            JFXButton b1 = new JFXButton("NON");
            b1.setId("jfxbutton");
            b2.setId("jfxbutton");
            JFXDialogLayout l = new JFXDialogLayout();
            l.setId("dialogue-layout");
            l.setBody(new Text("Voulez vous supprimer cet employé ?"));
            JFXDialog d = new JFXDialog(parentContainer, l, JFXDialog.DialogTransition.CENTER);
            b2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
            {

                Connection con= DbConnector.getConnection();
                try{
                    Employé c = new Employé(table.getSelectionModel().getSelectedItem().getId());
                    PreparedStatement pre= con.prepareStatement("delete from personnel where id=?");
                    pre.setInt(1,c.getId());
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
