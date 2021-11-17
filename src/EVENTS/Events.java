package EVENTS;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Events {
    double x,y;
    @FXML
    void dragged(MouseEvent event) {
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        st.setX(event.getScreenX()-x);
        st.setY(event.getScreenY()-y);
    }

    @FXML
    void pressed(MouseEvent event) {
        x=event.getSceneX();
        y=event.getSceneY();
    }

    @FXML
    void quite(MouseEvent event) {
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        st.close();
    }

    @FXML
    void minimize(MouseEvent event) {
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        st.setIconified(true);
    }

    @FXML
    void maximize(MouseEvent event) {
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        st.setFullScreen(true);
    }

    @FXML
    void unfocus(MouseEvent event){

    }
}
