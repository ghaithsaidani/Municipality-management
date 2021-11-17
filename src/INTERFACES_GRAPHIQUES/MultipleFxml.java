package INTERFACES_GRAPHIQUES;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.net.URL;

public class MultipleFxml {
    private StackPane stk;

    public StackPane stack(String fxml){
        try{
            URL fileurl = Interfacegraphique2.class.getResource(fxml);
            stk= FXMLLoader.load(fileurl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stk;
    }
}
