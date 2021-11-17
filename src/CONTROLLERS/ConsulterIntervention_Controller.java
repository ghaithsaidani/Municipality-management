package CONTROLLERS;

import DBCONNECTOR.DbConnector;
import MODÉLES.Intervention;
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

public class ConsulterIntervention_Controller implements Initializable {
    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    private TableView<Intervention> rec_table;

    @FXML
    private TableColumn<Intervention,Integer> num;

    @FXML
    private TableColumn<Intervention,String> nom;

    @FXML
    private TableColumn<Intervention,String> prenom;

    @FXML
    private TableColumn<Intervention,Integer> telephone;

    @FXML
    private TableColumn<Intervention,Integer> cin;

    @FXML
    private TableColumn<Intervention,String> categorie;

    @FXML
    private TableColumn<Intervention,String> sujet;

    @FXML
    private TableColumn<Intervention,String> date_reclamation;

    @FXML
    private TextField filtred_field;

    @FXML
    private ComboBox<String> cmb;

    @FXML
    private Button supp;

    @FXML
    private Button mod;

    @FXML
    private Button add;

    public static ObservableList<Intervention> Rec_list() {

        Connection con = DbConnector.getConnection();
        ObservableList<Intervention> list = FXCollections.observableArrayList();
        try {

            ResultSet rs = con.createStatement().executeQuery("select * from reclamation order by num asc");
            while (rs.next()) {

                Intervention E = new Intervention(rs.getInt("num"),rs.getNString("nom"), rs.getString("prenom"), rs.getInt("cin"), rs.getInt("tel"), rs.getString("categorie"),rs.getString("sujet"),rs.getDate("date_reclamation").toLocalDate());
                list.add(E);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    ObservableList<Intervention> rec=FXCollections.observableArrayList();

    public void consulter()
    {
        num.setCellValueFactory(new PropertyValueFactory<Intervention,Integer>("num"));
        nom.setCellValueFactory(new PropertyValueFactory<Intervention,String>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<Intervention,String>("prenom"));
        cin.setCellValueFactory(new PropertyValueFactory<Intervention,Integer>("cin"));
        telephone.setCellValueFactory(new PropertyValueFactory<Intervention,Integer>("tel"));
        categorie.setCellValueFactory(new PropertyValueFactory<Intervention,String>("categorie"));
        sujet.setCellValueFactory(new PropertyValueFactory<Intervention,String>("sujet"));
        date_reclamation.setCellValueFactory(new PropertyValueFactory<Intervention,String>("date_reclamation"));

        rec= Rec_list();
        rec_table.setItems(rec);
    }

    String combo() {
        return cmb.getSelectionModel().getSelectedItem();
    }

    public void rechercher() {
        ObservableList<Intervention> dataList = Rec_list();
        rec_table.setItems(dataList);
        FilteredList<Intervention> filteredData = new FilteredList<>(dataList, b -> true);
        filtred_field.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();


                if (combo().equals("numero")) {
                    return String.valueOf(person.getNum()).toLowerCase().indexOf(lowerCaseFilter) != -1;

                } else if (combo().equals("nom")) {
                    return person.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1;

                } else if (combo().equals("prenom")) {
                    return person.getPrenom().toLowerCase().indexOf(lowerCaseFilter) != -1;

                } else if (combo().equals("cin")) {
                    return String.valueOf(person.getCin()).toLowerCase().indexOf(lowerCaseFilter) != -1;

                }

                else if (combo().equals("telephone")) {
                    return String.valueOf(person.getTel()).toLowerCase().indexOf(lowerCaseFilter) != -1;

                }
                else {
                    return false;
                }

            });
        });
        SortedList<Intervention> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(rec_table.comparatorProperty());
        rec_table.setItems(sortedData);

    }

    ObservableList cat=FXCollections.observableArrayList("numero","nom","prenom","cin","telephone");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmb.setItems(cat);
        supp.setVisible(false);
        mod.setVisible(false);
        consulter();
        rechercher();

        rec_table.getSelectionModel().clearSelection();
    }

    @FXML
    void open_ajouter(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Ajouter_Reclamation.fxml"));

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
            Intervention row = rec_table.getSelectionModel().getSelectedItem();
            FXMLLoader fxml_loader = new FXMLLoader(getClass().getResource("../FXML/Modifier_Reclamation.fxml"));
            Parent root=fxml_loader.load();
            ModifierIntervention_Controller am = fxml_loader.getController();
            am.showinfo(row.getNom(),row.getPrenom(),String.valueOf(row.getCin()),row.getDate_reclamation().toString(),String.valueOf(row.getTel()),row.getCategorie(),row.getSujet());
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
            l.setBody(new Text("Voulez vous supprimer cet reclamation ?"));
            JFXDialog d = new JFXDialog(parentContainer, l, JFXDialog.DialogTransition.CENTER);
            b2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
            {
                Connection con= DbConnector.getConnection();
                try{
                    Intervention r = new Intervention(rec_table.getSelectionModel().getSelectedItem().getNum());
                    PreparedStatement pre= con.prepareStatement("delete from reclamation where num=?");
                    pre.setInt(1,r.getNum());
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

    @FXML
    void selected()
    {
        if(rec_table.getSelectionModel().getSelectedItem()!=null) {
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
        rec_table.getSelectionModel().clearSelection();
        supp.setVisible(false);
        mod.setVisible(false);

    }
}
