package br.com.alura.hotel.controller;

import br.com.alura.hotel.RegistroReservas;

import br.com.alura.hotel.dao.ReservasDao;

import br.com.alura.hotel.model.Reservas;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class RegistroReservasController {
    @FXML
    private DatePicker dataEntrada;
    @FXML
    private DatePicker dataSaida;
    @FXML
    private ToggleGroup pagamento;
    @FXML
    private RadioButton radioCredito;
    @FXML
    private RadioButton radioDebito;
    @FXML
    private RadioButton radioBoleto;
    @FXML
    private ToggleGroup suites;
    @FXML
    private RadioButton radioMaster;
    @FXML
    private RadioButton radioExecutiva;
    @FXML
    private RadioButton radioPresidencial;
    @FXML
    private Button btSimular;
    @FXML
    private Button btContinuar;
    @FXML
    private Label labelAlerta;
    @FXML
    private Label labelSimulado;
    @FXML
    private Button btOptions;
    int valor, valorPercentual, dias, valorTotal, diasDeReserva;
    String suite, mensagemValor, formPag, entrada, saida;

    public void initialize() {
        btSimular.setOnMouseClicked((MouseEvent e) -> {
            validaCamposReserva();
        });
        btContinuar.setOnMouseClicked((MouseEvent e) -> {
            cadastraReserva();
        });
        btOptions.setOnMouseClicked((MouseEvent e) -> {
            Navigation n = new Navigation();
            n.options();
            RegistroReservas.getStage().close();
        });
    }

    public void validaCamposReserva() {

        if (dataEntrada.getValue() == null) {
            labelAlerta.setText("Escolha a data do check-in");
        } else if (dataSaida.getValue() == null) {
            labelAlerta.setText("Escolha a data do check-out");
        } else if (pagamento.selectedToggleProperty().getValue() == null) {
            labelAlerta.setText("Escolha a forma de pagamento");
        } else if (suites.selectedToggleProperty().getValue() == null) {
            labelAlerta.setText("Escolha uma suíte");
        } else {
            labelAlerta.setText("");
            reservaSuite();
        }
    }

    public void reservaSuite() {
        if (radioMaster.isSelected()) {
            valor = 180;
            suite = "Master";
        } else if (radioExecutiva.isSelected()) {
            valor = 220;
            suite = "Executiva";
        } else if (radioPresidencial.isSelected()) {
            valor = 280;
            suite = "Presidencial";
        }
        formaDePag();

        entrada = String.valueOf(dataEntrada.getValue());
        saida = String.valueOf(dataSaida.getValue());

        try {
            DateTime dt1 = new DateTime(entrada);
            DateTime dt2 = new DateTime(saida);

            dias = Days.daysBetween(dt1, dt2).getDays();
            if (dias <= 0) {
                labelAlerta.setText("Informe uma data válida!");
            } else {
                valorTotal = (((dias * valor) * valorPercentual / 100) + dias * valor);
                labelSimulado.setText("Valor sem considerar descontos e acréscimos: R$" + valor * dias + ",00" + "\nVocê ficará hospedado por: " + dias + " dias.\n" +
                        "O valor total de sua hospedagem será de: R$" + valorTotal + ",00 " + mensagemValor);
                diasDeReserva = dias;
                btContinuar.setVisible(true);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void formaDePag() {
        if (radioCredito.isSelected()) {
            valorPercentual = 10;
            mensagemValor = "\nAcréscimo de 10%";
            formPag = " Parcelado";
        } else if (radioDebito.isSelected()) {
            valorPercentual = -5;
            mensagemValor = "\nDesconto de 5%";
            formPag = " Débito";
        } else if (radioBoleto.isSelected()) {
            valorPercentual = -10;
            mensagemValor = "\nDesconto de 10%";
            formPag = " Boleto";
        }
    }

    private void cadastraReserva() {
        String entrada = dataEntrada.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String saida = dataSaida.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Reservas r = new Reservas(entrada, saida, dias, valorTotal, suite, formPag);

        ReservasDao dao = null;
        try {
            dao = new ReservasDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        dao.add(r);

        Navigation n = new Navigation();

        n.registroHospede();

        RegistroReservas.getStage().close();
    }
}
