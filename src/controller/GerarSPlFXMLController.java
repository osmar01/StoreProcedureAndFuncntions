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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.Campo;
import modelo.ExpressaoSQL;
import modelo.Parametro;
import modelo.StoredProcedure;

/**
 * FXML Controller class
 *
 * @author Junnio
 */
public class GerarSPlFXMLController implements Initializable {

    private ExpressaoSQL query;
    private StoredProcedure procedure = new StoredProcedure();
    String textoFinal;

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

    @FXML
    private void inicializaStoredProcedure() {
        procedure.setNome(txtNomeProcedure.getText());
        procedure.setQuery(query);
        textoFinal = procedure.gerarStoredProcedure();
        txtAreaResultFinal.setText(textoFinal);

    }

    public void setQueryReference(ExpressaoSQL query) {
        this.query = query;
    }

}
