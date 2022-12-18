package br.com.alura.hotel.controller;

import br.com.alura.hotel.RegistroHospedes;

import br.com.alura.hotel.dao.ReservasDao;
import br.com.alura.hotel.dao.HospedesDao;

import br.com.alura.hotel.model.Hospedes;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class RegistroHospedesController {
    @FXML
    private TextField nomeHospede;
    @FXML
    private TextField sobrenomeHospede;
    @FXML
    private TextField telefoneHospede;
    @FXML
    private TextField numeroReserva;
    @FXML
    private DatePicker dataNascimentoHospede;
    @FXML
    private ComboBox<String> nacaoHospede;
    @FXML
    private Button btFinalizar;

    public void initialize() {

        CountryController.Country[] items = CountryController.Country.values();
        for (CountryController.Country item : items) {
            nacaoHospede.getItems().add(String.valueOf(item.getName()));
        }
        btFinalizar.setOnMouseClicked((MouseEvent e) -> {
            validaHospede();
        });

        numeroReserva.setText(String.valueOf(idReserva));
    }

    public void validaHospede() {
        if (!nomeHospede.getText().isBlank() && !sobrenomeHospede.getText().isBlank() &&
                !telefoneHospede.getText().isBlank() && !nacaoHospede.getSelectionModel().isEmpty() &&
                dataNascimentoHospede.getValue() != null) {
            registraHospede();
        } else {
            System.out.println("Erro ao validar");
        }
    }

    public void registraHospede() {

        String nascimento = dataNascimentoHospede.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                nome = nomeHospede.getText(),
                sobrenome = sobrenomeHospede.getText(),
                nacionalidade = nacaoHospede.getValue(),
                telefone = telefoneHospede.getText();

        Hospedes h = new Hospedes(nome, sobrenome, nascimento, nacionalidade, telefone, idReserva);
        HospedesDao dao = null;
        try {
            dao = new HospedesDao();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        dao.add(h);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText("Reserva realizada com sucesso");
        Navigation n = new Navigation();
        n.options();
        a.show();
        RegistroHospedes.getStage().close();
    }

    int idReserva;
    ReservasDao dao;

    {
        try {
            dao = new ReservasDao();
            idReserva = dao.buscaId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
