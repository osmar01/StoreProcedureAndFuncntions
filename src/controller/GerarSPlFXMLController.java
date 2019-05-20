/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Campo;
import modelo.Parametro;
import modelo.StoredProcedure;

/**
 * FXML Controller class
 *
 * @author Junnio
 */
public class GerarSPlFXMLController implements Initializable {

    
    private ObservableList<Parametro> observableParametro;
    public List<Parametro> parametros;
    private List<Parametro> parametrosSelecionados = new ArrayList<>();
    
    private StoredProcedure procedure = new StoredProcedure();
    
    public String resultado = "";
    
    @FXML
    private TableView<Parametro> tableViewParametro;
    @FXML
    private TableColumn<String, String> columnNome;
    @FXML
    private TableColumn<String, String> columnTipo;
    @FXML
    private TableColumn<String, String> columnCheckBox;
    @FXML
    private Button btnGerarSP;
    @FXML
    private TextArea txtAreaResultFinal;

    
    @FXML
    private TextField txtNomeProcedure;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setResultado(String resultado) {
        this.resultado = resultado;

        System.out.println(this.resultado);
    }

    public void inicializarListViewParam() {
        observableParametro = FXCollections.observableArrayList(parametros);

        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        columnCheckBox.setCellValueFactory(new PropertyValueFactory<>("checkbox"));

        tableViewParametro.setItems(observableParametro);
    }

    public void setParametros(List<Campo> campos) {
        parametros = new ArrayList<>();
        for (int i = 0; i < campos.size(); i++) {
            Parametro p = new Parametro();
            p.setNome(campos.get(i).getNome());
            p.setTipo(campos.get(i).getTipo());

            parametros.add(p);
        }
        inicializarListViewParam();
        parametrosSelecionadosExibir();
    }

    public void parametrosSelecionadosExibir() {
        for (Parametro parametro : parametros) {
            parametro.getCheckbox().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (parametro.getCheckbox().isSelected()) {
                        if (!parametrosSelecionados.contains(parametro)) {
                            parametrosSelecionados.add(parametro);
                        }
                        gerarStoredProcedure();
                    }
                    if (!parametro.getCheckbox().isSelected()) {
                        parametrosSelecionados.remove(parametro);
                       gerarStoredProcedure();
                    }
                }
            });
        }
    }
    
    
    

    @FXML
    private void gerarStoredProcedure(){
        procedure.setNome(txtNomeProcedure.getText());
        procedure.setParametros(parametrosSelecionados);
        
        txtAreaResultFinal.setText(procedure.storedProcedure(resultado));

    }

}
