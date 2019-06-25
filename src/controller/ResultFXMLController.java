/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import modelo.Campo;

/**
 * FXML Controller class
 *
 * @author Junnio
 */
public class ResultFXMLController implements Initializable {

    @FXML
    private TableView<Campo> tabelaAlteracao;
    @FXML
    private TableColumn<Campo, String> colunacampo;
    @FXML
    private TableColumn<Campo, String> colunaOperador;
    @FXML
    private TableColumn<Campo, String> colunaCriterio;
    @FXML
    private TableColumn<Campo, String> colunaFiltro;

    private ObservableList<Campo> observableCampos;
    private List<Campo> filtrosSelecionados;
    HomeFXMLController controller;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializaTabelaAlteracao();

    }

    public void inicializaTabelaAlteracao() {
        colunacampo.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaFiltro.setCellValueFactory(new PropertyValueFactory<>("comboboxFiltro"));
        colunaOperador.setCellValueFactory(new PropertyValueFactory<>("comboboxOperador"));
        colunaCriterio.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colunaCriterio.setCellFactory(TextFieldTableCell.forTableColumn());
        tabelaAlteracao.setEditable(true);

    }

    public void setListFiltrosSelecionados(List<Campo> filtrosSelecionados) {
        this.filtrosSelecionados = filtrosSelecionados;
        observableCampos = FXCollections.observableArrayList(filtrosSelecionados);
        tabelaAlteracao.setItems(observableCampos);
        selecionaFiltro();
        selecionaOperador();
        alteraFiltro();
        alterarOperador();

    }

    public void selecionaFiltro() {
        for (Campo campo : filtrosSelecionados) {
            String filtro = campo.getFiltro();
            campo.getComboboxFiltro().getSelectionModel().select(filtro);
        }
    }

    public void selecionaOperador() {
        for (Campo campo : filtrosSelecionados) {
            String operador = campo.getOperador();
            campo.getComboboxOperador().getSelectionModel().select(operador);
        }
    }

    public void alterarCriterio(TableColumn.CellEditEvent<Campo, String> cellEditEvent) {
        Campo campo = tabelaAlteracao.getSelectionModel().getSelectedItem();
        for (int i = 0; i < filtrosSelecionados.size(); i++) {
            if (filtrosSelecionados.get(i).getValor().equals(campo.getValor())) {
                campo.setValor(cellEditEvent.getNewValue());
                filtrosSelecionados.get(i).setValor(campo.getValor());
            }
        }
        controller.setResultado();
    }

    public void alteraFiltro() {
        for (int i = 0; i < filtrosSelecionados.size(); i++) {
            Campo campo = filtrosSelecionados.get(i);
            campo.getComboboxFiltro().setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    String nome = (String) campo.getComboboxFiltro().getSelectionModel().getSelectedItem();
                    if (!campo.getFiltro().equals(nome)) {
                        campo.setFiltro(nome);
                        controller.setResultado();
                    }
                }       
            });
            filtrosSelecionados.get(i).setFiltro(campo.getFiltro());
        }
    }
    
    public void alterarOperador(){
        for (int i = 0; i < filtrosSelecionados.size(); i++) {
            Campo campo = filtrosSelecionados.get(i);
            campo.getComboboxOperador().setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                 String nome = (String) campo.getComboboxOperador().getSelectionModel().getSelectedItem();
                 if (!campo.getOperador().equals(nome)) {
                        campo.setOperador(nome);
                        controller.setResultado();
                    }
                }
            });
            filtrosSelecionados.get(i).setOperador(campo.getOperador());

        }
    }

    public void setControllerHome(HomeFXMLController controller) {
        this.controller = controller;
    }
}
