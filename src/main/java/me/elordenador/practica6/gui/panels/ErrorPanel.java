package me.elordenador.practica6.gui.panels;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ErrorPanel {
    private String error;
    public ErrorPanel(String error) throws IOException {
        VBox root = (VBox) FXMLLoader.load(getClass().getClassLoader().getResource("gui/error.fxml"));


        Stage stage = new Stage();
        Scene scene = new Scene(root);
        Text label = (Text) scene.lookup("#error");
        label.setText(error);

        stage.setScene(scene);
        stage.show();
    }
}
