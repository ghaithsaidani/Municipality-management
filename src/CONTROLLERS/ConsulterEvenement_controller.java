package CONTROLLERS;

import DBCONNECTOR.DbConnector;
import MODÉLES.Evenement;
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

public class ConsulterEvenement_controller implements Initializable {
    @FXML
    private TableView<Evenement> event_table;

    @FXML
    private TableColumn<Evenement, Integer> num;

    @FXML
    private TableColumn<Evenement, String> nom;

    @FXML
    private TableColumn<Evenement, String> adresse;

    @FXML
    private TableColumn<Evenement, String> type;

    @FXML
    private TableColumn<Evenement, String> etat;

    @FXML
    private TableColumn<Evenement, String> date_debut;

    @FXML
    private TableColumn<Evenement, String> date_fin;


    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    private TextField filtred_field;

    @FXML
    private ComboBox<String> cmb;

    @FXML
    private Button supp;

    @FXML
    private Button add;

    @FXML
    private Button mod;



    public static ObservableList<Evenement> Evenement_list() {

        Connection con = DbConnector.getConnection();
        ObservableList<Evenement> list = FXCollections.observableArrayList();
        try {

            ResultSet rs = con.createStatement().executeQuery("select * from evenement order by num asc");
            while (rs.next()) {

                Evenement E = new Evenement(rs.getInt("num"),rs.getNString("nom"), rs.getString("etat"), rs.getString("type"), rs.getDate("date_debut").toLocalDate(), rs.getDate("date_fin").toLocalDate(), rs.getString("adresse"),rs.getFloat("budget")/*,rs.getString("description")*/);
                list.add(E);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    ObservableList<Evenement> Ev=FXCollections.observableArrayList();

    public void consulter()
    {
        num.setCellValueFactory(new PropertyValueFactory<Evenement,Integer>("num"));
        nom.setCellValueFactory(new PropertyValueFactory<Evenement,String>("nom"));
        etat.setCellValueFactory(new PropertyValueFactory<Evenement,String>("etat"));
        type.setCellValueFactory(new PropertyValueFactory<Evenement,String>("type"));
        date_debut.setCellValueFactory(new PropertyValueFactory<Evenement,String>("date_debut"));
        date_fin.setCellValueFactory(new PropertyValueFactory<Evenement,String>("date_fin"));
        adresse.setCellValueFactory(new PropertyValueFactory<Evenement,String>("adresse"));


        Ev= Evenement_list();
        event_table.setItems(Ev);
    }

    String combo() {
        return cmb.getSelectionModel().getSelectedItem();
    }

    public void rechercher() {
        ObservableList<Evenement> dataList = Evenement_list();
        event_table.setItems(dataList);
        FilteredList<Evenement> filteredData = new FilteredList<>(dataList, b -> true);
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

                }else if (combo().equals("nom")) {
                if (person.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1)
                    return true;
                else
                    return false;

                }else if (combo().equals("type")) {
                    if (person.getType().toLowerCase().indexOf(lowerCaseFilter) != -1)
                        return true;
                    else
                        return false;

                } else if (combo().equals("etat")) {
                    if (person.getEtat().toLowerCase().indexOf(lowerCaseFilter) != -1)
                        return true;
                    else
                        return false;

                } else if (combo().equals("adresse")) {
                    if (person.getAdresse().indexOf(lowerCaseFilter) != -1)
                        return true;
                    else
                        return false;

                }
                else {
                    return false;
                }

            });
        });
        SortedList<Evenement> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(event_table.comparatorProperty());
        event_table.setItems(sortedData);

    }



    @FXML
    void open_ajouter(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Ajouter_Evenement.fxml"));

        Scene scene = add.getScene();

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
    void open_modifier(ActionEvent event) throws Exception {
        try {




            Evenement row = event_table.getSelectionModel().getSelectedItem();
            /*Connection con = DbConnector.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from evenement where num="+row.getNum());
            Evenement E = new Evenement(rs.getInt("num"),rs.getNString("nom"), rs.getString("etat"), rs.getString("type"), rs.getDate("date_debut").toLocalDate(), rs.getDate("date_fin").toLocalDate(), rs.getString("adresse"),rs.getString("description"));*/
            FXMLLoader fxml_loader = new FXMLLoader(getClass().getResource("../FXML/Modifier_Evenement.fxml"));
            Parent root=fxml_loader.load();
            ModifierEvenement_controller am = fxml_loader.getController();
            am.showinfo(row.getNom(),row.getEtat(),row.getType(),row.getDate_debut().toString(),row.getDate_fin().toString(),row.getAdresse(),row.getDescription(),String.valueOf(row.getBudget()));
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
    void supprimer() {
        try {
            JFXButton b2 = new JFXButton("OUI");
            JFXButton b1 = new JFXButton("NON");
            b1.setId("jfxbutton");
            b2.setId("jfxbutton");
            JFXDialogLayout l = new JFXDialogLayout();
            l.setBody(new Text("Voulez vous supprimer cet evenement ?"));
            JFXDialog d = new JFXDialog(parentContainer, l, JFXDialog.DialogTransition.CENTER);
            b2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
            {
                Connection con= DbConnector.getConnection();
                try{
                    Evenement e = new Evenement(event_table.getSelectionModel().getSelectedItem().getNum());
                    PreparedStatement pre= con.prepareStatement("delete from evenement where num=?");
                    pre.setInt(1,e.getNum());
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


    ObservableList cat=FXCollections.observableArrayList("numero","nom","type","etat","adresse");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmb.setItems(cat);
        supp.setVisible(false);
        mod.setVisible(false);
        consulter();
        rechercher();

        event_table.getSelectionModel().clearSelection();

    }

    @FXML
    void selected()
    {
        if(event_table.getSelectionModel().getSelectedItem()!=null) {
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
        event_table.getSelectionModel().clearSelection();
        supp.setVisible(false);
        mod.setVisible(false);

    }

}