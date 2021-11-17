package CONTROLLERS;

import DBCONNECTOR.DbConnector;
import MODÉLES.Revenu;
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

public class Consulter_Revenu_Controller implements Initializable{
    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    private TableView<Revenu> rev_table;

    @FXML
    private TableColumn<Revenu,Integer> num;

    @FXML
    private TableColumn<Revenu,Float> montant;

    @FXML
    private TableColumn<Revenu,Float> rev;

    @FXML
    private TableColumn<Revenu,String> date;

    @FXML
    private Button supp;

    @FXML
    private Button mod;

    @FXML
    private Button add;

    @FXML
    private TextField filtred_field;

    @FXML
    private ComboBox<String> cmb;

    public static ObservableList<Revenu> Revenu_list() {

        Connection con = DbConnector.getConnection();
        ObservableList<Revenu> list = FXCollections.observableArrayList();
        try {

            ResultSet rs = con.createStatement().executeQuery("select * from revenu order by num asc");
            while (rs.next()) {

                Revenu d = new Revenu(rs.getInt("num"),rs.getFloat("montant") ,rs.getFloat("rev"),rs.getDate("date").toLocalDate());
                list.add(d);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    ObservableList<Revenu> d=FXCollections.observableArrayList();

    public void consulter()
    {
        num.setCellValueFactory(new PropertyValueFactory<Revenu,Integer>("num"));
        montant.setCellValueFactory(new PropertyValueFactory<Revenu,Float>("montant"));
        rev.setCellValueFactory(new PropertyValueFactory<Revenu,Float>("rev"));
        date.setCellValueFactory(new PropertyValueFactory<Revenu,String>("date"));






        d= Revenu_list();
        rev_table.setItems(d);
    }

    String combo() {
        return cmb.getSelectionModel().getSelectedItem();
    }

    public void rechercher() {
        ObservableList<Revenu> dataList = Revenu_list();
        rev_table.setItems(dataList);
        FilteredList<Revenu> filteredData = new FilteredList<>(dataList, b -> true);
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

                } else if (combo().equals("montant")) {
                    if (String.valueOf(person.getMontant()).toLowerCase().indexOf(lowerCaseFilter) != -1)
                        return true;
                    else
                        return false;

                }
                else if (combo().equals("revenu")) {
                    if (String.valueOf(person.getRev()).toLowerCase().indexOf(lowerCaseFilter) != -1)
                        return true;
                    else
                        return false;

                }
                else {
                    return false;
                }

            });
        });
        SortedList<Revenu> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(rev_table.comparatorProperty());
        rev_table.setItems(sortedData);

    }

    @FXML
    void open_ajouter(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Ajouter_Revenu.fxml"));

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




            Revenu row = rev_table.getSelectionModel().getSelectedItem();
            FXMLLoader fxml_loader = new FXMLLoader(getClass().getResource("../FXML/Modifier_Revenu.fxml"));
            Parent root=fxml_loader.load();
            Modifier_Revenu_Controller am = fxml_loader.getController();
            am.showinfo(String.valueOf(row.getMontant()),String.valueOf(row.getRev()),row.getDate().toString(),row.getDescription());
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
            l.setBody(new Text("Voulez vous supprimer cet doleance ?"));
            JFXDialog d = new JFXDialog(parentContainer, l, JFXDialog.DialogTransition.CENTER);
            b2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
            {
                Connection con= DbConnector.getConnection();
                try{
                    PreparedStatement pre= con.prepareStatement("delete from revenu where num=?");
                    pre.setInt(1,rev_table.getSelectionModel().getSelectedItem().getNum());
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


    ObservableList cat=FXCollections.observableArrayList("numero","montant","revenu");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmb.setItems(cat);
        supp.setVisible(false);
        mod.setVisible(false);
        consulter();
        rechercher();

        rev_table.getSelectionModel().clearSelection();

    }

    @FXML
    void selected()
    {
        if(rev_table.getSelectionModel().getSelectedItem()!=null) {
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
        rev_table.getSelectionModel().clearSelection();
        supp.setVisible(false);
        mod.setVisible(false);

    }
}
