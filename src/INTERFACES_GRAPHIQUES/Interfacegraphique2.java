package INTERFACES_GRAPHIQUES;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Interfacegraphique2 extends Application {
    private static Stage st;
    @Override
    public void start(Stage stage) throws Exception {
        st=stage;
        Parent root= FXMLLoader.load(getClass().getResource("../FXML/Consulter.fxml"));
        Scene sc=new Scene(root);

        stage.setScene(sc);
        stage.show();
    }

    public void changerscene(String fxml) throws Exception
    {
        Parent root=FXMLLoader.load(getClass().getResource(fxml));
        st.setScene(new Scene(root));
    }
}
