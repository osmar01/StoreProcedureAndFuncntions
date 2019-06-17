/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.ExpressaoSQL;
import modelo.Funcao;
import modelo.StoredProcedure;

/**
 * FXML Controller class
 *
 * @author osmar.frota
 */
public class GerarFunctionController implements Initializable {
    
    private ExpressaoSQL query;
    private Funcao funcao = new Funcao();
    String textoFinal;
    
    @FXML
    private TextField txtNomeFunction;
    @FXML
    private Button btnGerarFunction;
    @FXML
    private TextArea txtAreaResultFinal;
    @FXML
    private TextField txtNomeRetorno;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void inicializaFunction() {
        funcao.setNome(txtNomeFunction.getText());
        funcao.setTipo(txtNomeRetorno.getText());
        funcao.setQuery(query);
        textoFinal = funcao.gerarFunction();
        txtAreaResultFinal.setText(textoFinal);       
    }
    
    public void setQueryReference(ExpressaoSQL query) {
        this.query = query;
    }
}
