package me.elordenador.practica6.gui.controllers;

import me.elordenador.practica6.gui.panels.AboutPanel;
import javafx.fxml.FXML;

public class AboutController {

    @FXML
    public void close() {
        AboutPanel.getInstance().close();
    }
}
