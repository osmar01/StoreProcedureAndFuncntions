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
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
//    private TableView<Map.Entry<String, String>> tableviewExecute;
    private TableView<String[]> tableviewExecute;

    List<String> campos;
    List<Campo> camposSelecionados;
    Map<String, String> map;
    private int N_COLS;
    private int N_ROWS;
    private static final int MAX_DATA_VALUE = 100;
    private String matriz[][];

    public void setTableViewExecute() {

//        for (int i = 0; i < camposSelecionados.size(); i++) {
//            TableColumn<String, String> coluna = new TableColumn(camposSelecionados.get(i).toString());
//            tableviewExecute.getColumns().add(coluna);
//            coluna.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
//        }
//
//        ObservableList<String> observableCampos = FXCollections.observableArrayList(campos);
//        tableviewExecute.setItems(observableCampos);
//        TableColumn<Map.Entry<String, String>, String> column1 = new TableColumn<>("Key");
//        column1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {
//
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
//                // this callback returns property for just one cell, you can't use a loop here
//                // for first column we use key
//                return new SimpleStringProperty(p.getValue().getKey());
//            }
//        });
        for (int i = 0; i < camposSelecionados.size(); i++) {

            TableColumn<Map.Entry<String, String>, String> column2 = new TableColumn<>(camposSelecionados.get(i).getNome());
            column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {


                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {

                    
                    for (int j = 0; j < 10; j++) {
                        String value = map.get("key"+j);
                        
                    }
                        return new SimpleStringProperty("");
                    // for second column we use value


                }

            });
            //tableviewExecute.getColumns().add(column2);
        }
//        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(map.entrySet());
        //tableviewExecute.setItems(items);

//        N_ROWS = campos.size()/camposSelecionados.size();
        //       N_COLS = camposSelecionados.size();
//        ObservableList<double[]> data = generateData();
//
//        TableView<double[]> table = new TableView<>(data);
//        table.getColumns().setAll(createColumns());
//        table.setPrefSize(200, 250);
//        for (int i = 0; i < campos.size(); i++) {
//            System.out.println(campos.get(i).toString());
        
        
//        }
        
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(matriz));
        data.remove(0);//remove titles from data
        TableView<String[]> table = new TableView<>();
        for (int i = 0; i < matriz[0].length; i++) {
            TableColumn tc = new TableColumn(matriz[0][i]);
            final int colNo = i;
            tc.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(90);
            tableviewExecute.getColumns().add(tc);
        }
        tableviewExecute.setItems(data);
        
        
    }

//    private ObservableList<double[]> generateData() {;
//        return FXCollections.observableArrayList(
//                IntStream.range(0, N_ROWS)
//                        .mapToObj(r ->
//                                IntStream.range(0, N_COLS)
//                                        .mapToDouble(c -> randomValue())
//                                        .toArray()
//                        ).collect(Collectors.toList())
//        );
//    }
    private List<TableColumn<double[], Double>> createColumns() {
        return IntStream.range(0, N_COLS)
                .mapToObj(this::createColumn)
                .collect(Collectors.toList());
    }

    private TableColumn<double[], Double> createColumn(int c) {
        TableColumn<double[], Double> col = new TableColumn<>("C" + (c + 1));
        col.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()[c]));

        return col;
    }

    public String[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(String[][] matriz) {
        this.matriz = matriz;
    }

    
    
    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
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
