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
import modelo.Parametro;
import modelo.StoredProcedure;

/**
 * FXML Controller class
 *
 * @author Junnio
 */
public class GerarSPlFXMLController implements Initializable {

        
    public List<Parametro> parametros;    
    
    private StoredProcedure procedures = new StoredProcedure();
    
    public String resultado = "";
    
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
    }

    public void setParametros(List<Campo> campos) {
        parametros = new ArrayList<>();
        for (int i = 0; i < campos.size(); i++) {
            Parametro p = new Parametro();
            p.setNome(campos.get(i).getNome());
            p.setTipo(campos.get(i).getTipo());

            parametros.add(p);
        }              
    }
    

 
    
   
    @FXML
    private void gerarStoredProcedure(){
        procedures.setNome(txtNomeProcedure.getText());
        procedures.setParametros(parametros);
        
        txtAreaResultFinal.setText(procedures.storedProcedure(resultado));

    }

}
