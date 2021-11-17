package CONTROLLERS;

import INTERFACES_GRAPHIQUES.Interfacegraphique1;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Deconnecter_Controller {
    @FXML
    void deconnecter(ActionEvent event) throws Exception {

        Alert a=new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Voulez vous deconnectez ?");
        Optional<ButtonType> result=a.showAndWait();
        if(result.get()==ButtonType.OK)
        {
            Interfacegraphique1 ig=new Interfacegraphique1();
            ig.changerscene("../FXML/Connecter.fxml");
        }
    }
}
