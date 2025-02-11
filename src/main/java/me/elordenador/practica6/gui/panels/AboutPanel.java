package me.elordenador.practica6.gui.panels;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import me.elordenador.practica6.gui.controllers.AboutController;

import java.io.IOException;

public class AboutPanel {
    private static AboutPanel instance;
    private Stage stage;
    public AboutPanel() throws IOException {
        instance = this;
        stage = new Stage();

        Scene scene = new Scene((GridPane) FXMLLoader.load(getClass().getClassLoader().getResource("gui/about.fxml")));
        stage.setTitle("Acerca de");
        stage.setScene(scene);
    }

    public static AboutPanel getInstance() {
        return instance;
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }
}
