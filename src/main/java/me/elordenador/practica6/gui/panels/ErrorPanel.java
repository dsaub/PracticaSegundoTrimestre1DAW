package me.elordenador.practica6.gui.panels;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ErrorPanel {
    private String error;
    public ErrorPanel(String error) throws IOException {
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("gui/error.fxml"));


        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
