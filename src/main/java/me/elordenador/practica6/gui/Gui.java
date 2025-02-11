package me.elordenador.practica6.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Gui extends Application {
    private static Gui instance;
    private Stage primaryStage;

    public Gui() {
        instance = this;
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();

        loadFXML("main.fxml", "Menú Principal");
    }



    public void loadFXML(String fxmlName, String title) throws IOException {
        VBox root = (VBox) FXMLLoader.load(getClass().getClassLoader().getResource("gui/"+fxmlName));

        Scene scene = new Scene(root);
        primaryStage.setTitle(title + " - Gestión de dispositivos");
        primaryStage.setScene(scene);
        if (!primaryStage.isShowing()) primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Gui getInstance() {
        return instance;
    }
}
