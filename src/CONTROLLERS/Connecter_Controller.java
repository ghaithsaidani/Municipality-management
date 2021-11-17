package CONTROLLERS;

import EVENTS.Events;
import INTERFACES_GRAPHIQUES.Interfacegraphique1;
import INTERFACES_GRAPHIQUES.Interfacegraphique2;
import VALIDATION.Validate;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

public class Connecter_Controller extends Events{

    @FXML
    private StackPane stk;

    @FXML
    private TextField tf0;

    @FXML
    private PasswordField pf3;




    @FXML
    void connecter(ActionEvent event)
    {
        try{
            JFXButton b=new JFXButton("Ok");
            b.setId("jfxbutton");
            if(tf0.getText().isEmpty() && !pf3.getText().isEmpty())
            {
                JFXDialogLayout ly=new JFXDialogLayout();
                JFXDialog d=new JFXDialog(stk,ly,JFXDialog.DialogTransition.CENTER);

                b.addEventHandler(MouseEvent.MOUSE_CLICKED ,(MouseEvent ev)->
                {
                    d.close();
                });

                ly.setBody(new Text("vous n'avez pas ecrire votre nom d'utilisateur !"));
                ly.setActions(b);
                d.show();
            }
            else if(pf3.getText().isEmpty() && !tf0.getText().isEmpty())
            {
                JFXDialogLayout ly=new JFXDialogLayout();
                JFXDialog d=new JFXDialog(stk,ly,JFXDialog.DialogTransition.CENTER);

                b.addEventHandler(MouseEvent.MOUSE_CLICKED ,(MouseEvent ev)->
                {
                    d.close();
                });
                ly.setBody(new Text("vous n'avez pas ecrire votre mot de passe !"));
                ly.setActions(b);
                d.show();
            }

            else if(tf0.getText().isEmpty() && pf3.getText().isEmpty())
            {
                JFXDialogLayout ly=new JFXDialogLayout();
                JFXDialog d=new JFXDialog(stk,ly,JFXDialog.DialogTransition.CENTER);
                b.addEventHandler(MouseEvent.MOUSE_CLICKED ,(MouseEvent ev)->
                {
                    d.close();
                });

                ly.setBody(new Text("vous n'avez pas ecrire ni nom d'utilisateur ni mot de passe !"));
                ly.setActions(b);
                d.show();
            }
            else if(Validate.verifierId(tf0.getText()))
            {
                int i;
                for (i = 0; i < Consulter_compte_Controller.cmplist().size(); i++) {
                    if ((Consulter_compte_Controller.cmplist().get(i).getEmp().getId() == Integer.parseInt(tf0.getText())))
                        break;
                }

                if (i < Consulter_compte_Controller.cmplist().size()) {
                    if(Consulter_compte_Controller.cmplist().get(i).getPassword().equals(pf3.getText()))
                    {
                        switch(Consulter_compte_Controller.cmplist().get(i).getPoste())
                        {
                            case("Maire"):
                            {
                                Interfacegraphique2 ig =new Interfacegraphique2();
                                Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                ig.start(st);

                                break;
                            }
                            case("Directeur administratif"):
                            {
                                Interfacegraphique1 ig = new Interfacegraphique1();
                                ig.changerscene("../FXML/Dir_admin.fxml");
                                break;
                            }
                            case("Interet technique"):
                            {
                                Interfacegraphique1 ig = new Interfacegraphique1();
                                ig.changerscene("../FXML/Interet.fxml");
                                break;
                            }
                            case("Directeur general"):
                            {
                                Interfacegraphique1 ig = new Interfacegraphique1();
                                ig.changerscene("../FXML/Dir_general.fxml");
                                break;
                            }
                            case("Manager de resources humaines"):
                            {
                                Interfacegraphique1 ig = new Interfacegraphique1();
                                ig.changerscene("../FXML/Rh.fxml");
                                break;
                            }

                        }
                        TrayNotification tray=new TrayNotification();
                        tray.setTitle("connexion avec succés");
                        Image im=new Image(getClass().getResourceAsStream("../ICONS/icons8_verified_account_96px.png"));
                        tray.setImage(im);
                        tray.setAnimationType(AnimationType.POPUP);
                        tray.setRectangleFill(Color.web("#19A05A"));
                        tray.showAndDismiss(Duration.seconds(1));
                    }
                    else{
                        JFXDialogLayout ly=new JFXDialogLayout();
                        JFXDialog d=new JFXDialog(stk,ly,JFXDialog.DialogTransition.CENTER);
                        b.addEventHandler(MouseEvent.MOUSE_CLICKED ,(MouseEvent ev)->
                        {
                            d.close();
                            pf3.clear();
                        });

                        ly.setBody(new Text("le mot de passe que vous avez saisi(e) est incorrecte !"));
                        ly.setActions(b);
                        d.show();
                    }
                }

                else{
                    JFXDialogLayout ly=new JFXDialogLayout();
                    JFXDialog d=new JFXDialog(stk,ly,JFXDialog.DialogTransition.CENTER);
                    b.addEventHandler(MouseEvent.MOUSE_CLICKED ,(MouseEvent ev)->
                    {
                        d.close();
                        tf0.clear();
                        pf3.clear();
                    });

                    ly.setBody(new Text("L’adresse e-mail que vous avez saisi(e) n'est pas associé(e) à une compte !"));
                    ly.setActions(b);
                    d.show();
                }


            }
            else{
                JFXDialogLayout ly=new JFXDialogLayout();
                JFXDialog d=new JFXDialog(stk,ly,JFXDialog.DialogTransition.CENTER);
                b.addEventHandler(MouseEvent.MOUSE_CLICKED ,(MouseEvent ev)->
                {
                    d.close();
                    tf0.clear();
                    pf3.clear();
                });

                ly.setBody(new Text("L’adresse e-mail que vous avez saisi(e) n'est pas associé(e) à une compte !"));
                ly.setActions(b);
                d.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
