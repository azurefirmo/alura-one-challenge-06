package br.com.alura.hotel.controller;

import br.com.alura.hotel.Options;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class OptionsController {
    @FXML
    private Button btRegistrar;
    @FXML
    private Button btHospedes;
    @FXML
    private Button btReservas;
    @FXML
    private Button btLogout;

    public void initialize() {
        btRegistrar.setOnMouseClicked((MouseEvent e) -> {
            Navigation n = new Navigation();
            n.registro();
            Options.getStage().close();
        });
        btHospedes.setOnMouseClicked((MouseEvent e) -> {
            Navigation n = new Navigation();
            n.hospedes();
            Options.getStage().close();
        });
        btReservas.setOnMouseClicked((MouseEvent e) -> {
            Navigation n = new Navigation();
            n.reservas();
            Options.getStage().close();
        });
        btLogout.setOnMouseClicked((MouseEvent e) -> {
            Navigation n = new Navigation();
            n.login();
            Options.getStage().close();
        });
    }
}
