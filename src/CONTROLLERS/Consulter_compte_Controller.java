package CONTROLLERS;

import DBCONNECTOR.DbConnector;
import MODÉLES.Compte;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Consulter_compte_Controller implements Initializable {

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    private TableColumn<Compte,Integer> Username;

    @FXML
    private TableColumn<Compte,String> E_mail;

    @FXML
    private TableColumn<Compte,String> Poste;


    @FXML
    private TableView<Compte> Comptes;

    @FXML
    private ComboBox<String> cmb;

    @FXML
    private TextField filtred_field;

    @FXML
    private Button mod;

    @FXML
    private Button supp;

    @FXML
    private Button b;

    public static ObservableList<Compte> cmplist()
    {
        Connection c=DbConnector.getConnection();
        ObservableList<Compte> list = FXCollections.observableArrayList();
        try {
            PreparedStatement pr=c.prepareStatement("select * from comptes");
            ResultSet rs=pr.executeQuery();
            while(rs.next())
            {
                Compte cmp=new Compte(new Employé(rs.getInt("id")),rs.getString("e_mail"),rs.getString("poste"),rs.getString("password"));
                list.add(cmp);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    ObservableList<Compte> olc= FXCollections.observableArrayList();

    public void consulter()
    {
        Username.setCellValueFactory(new PropertyValueFactory<Compte,Integer>("x"));
        E_mail.setCellValueFactory(new PropertyValueFactory<Compte,String>("e_mail"));
        Poste.setCellValueFactory(new PropertyValueFactory<Compte,String>("poste"));

        olc=cmplist();
        Comptes.setItems(olc);

    }

    String combo() {
        return cmb.getSelectionModel().getSelectedItem().toString();
    }

    public void rechercher() {
        ObservableList<Compte> dataList =cmplist();
        Comptes.setItems(dataList);
        FilteredList<Compte> filteredData = new FilteredList<>(dataList, b -> true);
        filtred_field.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();


                if (combo().equals("Nom D'utilisateur")) {
                    if (String.valueOf(person.getEmp().getId()).toLowerCase().indexOf(lowerCaseFilter) != -1)
                        return true;
                    else
                        return false;

                } else if (combo().equals("E_Mail")) {
                    if (person.getE_mail().toLowerCase().indexOf(lowerCaseFilter) != -1)
                        return true;
                    else
                        return false;

                } else if (combo().equals("Poste")) {
                    if (person.getPoste().toLowerCase().indexOf(lowerCaseFilter) != -1)
                        return true;
                    else
                        return false;

                }
                else {
                    return false;
                }

            });
        });
        SortedList<Compte> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(Comptes.comparatorProperty());
        Comptes.setItems(sortedData);

    }

    ObservableList cat=FXCollections.observableArrayList("Nom D'utilisateur","E_Mail","Poste");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmb.setItems(cat);
        consulter();
        rechercher();
        supp.setVisible(false);
        mod.setVisible(false);
        Comptes.getSelectionModel().clearSelection();
    }

    @FXML
    void open_modifier(ActionEvent event) throws Exception {
        try {
            Compte row = Comptes.getSelectionModel().getSelectedItem();
            FXMLLoader fxml_loader = new FXMLLoader(getClass().getResource("../FXML/Modifier_compte.fxml"));
            Parent root=fxml_loader.load();
            ModifierCompte_Controller am = fxml_loader.getController();
            am.showinfo( String.valueOf(row.getEmp().getId()), row.getE_mail(),row.getPoste(),row.getPassword(),row.getPassword());

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
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Ajouter_Compte.fxml"));

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
            l.setBody(new Text("Voulez vous supprimer ce compte ?"));
            JFXDialog d = new JFXDialog(parentContainer, l, JFXDialog.DialogTransition.CENTER);
            b2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) ->
            {

                Connection con= DbConnector.getConnection();
                try{
                    PreparedStatement pre= con.prepareStatement("delete from comptes where id=?");
                    pre.setInt(1,Comptes.getSelectionModel().getSelectedItem().getEmp().getId());
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
        if(Comptes.getSelectionModel().getSelectedItem()!=null) {
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
        Comptes.getSelectionModel().clearSelection();
        supp.setVisible(false);
        mod.setVisible(false);

    }

    /*@FXML
    public void back(ActionEvent event) throws Exception
    {
        Interfacegraphique2 ig=new Interfacegraphique2();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ig.start(st);
    }*/
}
