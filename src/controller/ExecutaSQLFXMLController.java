/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import modelo.Campo;

/**
 * FXML Controller class
 *
 * @author osmar.frota
 */
public class ExecutaSQLFXMLController implements Initializable {

    @FXML
    private TableView<String[]> tableviewExecute;

    List<String> campos;
    List<Campo> camposSelecionados;
    private String matriz[][];

    public void setTableViewExecute() {

        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(matriz));
        for (int i = 0; i < matriz[0].length; i++) {
            TableColumn tc = new TableColumn(camposSelecionados.get(i).getNome());
            final int colNo = i;
            tc.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tableviewExecute.getColumns().add(tc);
        }
        tableviewExecute.setItems(data);
    }

    public String[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(String[][] matriz) {
        this.matriz = matriz;
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
