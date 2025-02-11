package me.elordenador.practica6.gui.controllers;

import javafx.fxml.FXML;
import me.elordenador.practica6.gui.Gui;
import me.elordenador.practica6.gui.panels.AboutPanel;
import me.elordenador.practica6.gui.panels.ErrorPanel;

import java.io.IOException;

public class MainController {

    /**
     * Closes the app
     */
    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void openAbout() throws IOException {
        try {
            AboutPanel ap = new AboutPanel();
            ap.show();
        } catch (IOException e) {
            ErrorPanel panel = new ErrorPanel("Error abriendo AboutPanel: " + e.getMessage());
        }

    }
}
