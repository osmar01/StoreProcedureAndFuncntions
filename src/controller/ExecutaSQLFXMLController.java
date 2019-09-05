/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modelo.Campo;

/**
 * FXML Controller class
 *
 * @author osmar.frota
 */
public class ExecutaSQLFXMLController implements Initializable {

    @FXML
    private TableView<String> tableviewExecute;

    List<String> campos;
    List<Campo> camposSelecionados;

    public void setTableViewExecute() {

        TableColumn<String, String> coluna = null;
        for (int i = 0; i < camposSelecionados.size(); i++) {
            coluna = new TableColumn(camposSelecionados.get(i).toString());
            tableviewExecute.getColumns().add(coluna);
            coluna.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        }

        ObservableList<String> observableCampos = FXCollections.observableArrayList(campos);
        tableviewExecute.setItems(observableCampos);

        for (int i = 0; i < campos.size(); i++) {
            System.out.println(campos.get(i).toString());
        }

    }

    public List<String> getCampos() {
        return campos;
    }

    public void setCampos(List<String> campos) {
        this.campos = campos;
    }

    public List<Campo> getCamposSelecionados() {
        return camposSelecionados;
    }

    public void setCamposSelecionados(List<Campo> camposSelecionados) {
        this.camposSelecionados = camposSelecionados;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
