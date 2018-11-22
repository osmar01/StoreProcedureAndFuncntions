/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import conexao.Conection;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Junnio
 */
public class AcessoFXMLController implements Initializable {

    @FXML
    private TextField localhosttxt;
    @FXML
    private TextField portatxt;
    @FXML
    private TextField usuariotxt;
    @FXML
    private PasswordField senhatxt;
    @FXML
    private Button conectarbtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {  
    }

    public void acessar() throws IOException {
        Conection.setHost(localhosttxt.getText());
        Conection.setPorta(portatxt.getText());
        Conection.setUsuario(usuariotxt.getText());
        Conection.setSenha(senhatxt.getText());
        Conection.Conectar();

//        if (Conection.verificaConexao()) {
//            Principal principal = new Principal();
//            try {
//                principal.start(new Stage());
//            } catch (Exception ex) {
//                Logger.getLogger(AcessoFXMLController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            conectarbtn.getScene().getWindow().hide();
//        }
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/InicioFXML.fxml"));        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        conectarbtn.getScene().getWindow().hide();

    }

}
