package me.elordenador.practica6.gui.panels;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import javax.swing.text.LabelView;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErrorPanel {
    private String error;
    public ErrorPanel(String error) {
        VBox vbox = new VBox();
        Label label = new Label("Ha ocurrido un error " + error);
        ButtonBar
        vbox.getChildren().add(label);


        Stage stage = new Stage();
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }
}
