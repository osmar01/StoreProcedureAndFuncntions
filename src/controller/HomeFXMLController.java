/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.BancoDAO;
import dao.TabelaDAO;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import modelo.Banco;
import modelo.Tabela;

/**
 * FXML Controller class
 *
 * @author Junnio
 */
public class HomeFXMLController implements Initializable {

    private BancoDAO bancodao;
    private List<Banco> bancos;
    private TabelaDAO tabeladao;
    private List<Tabela> tabelas;
    private TreeItem<String> itemTabela;
    private TreeItem<String> parentBanco;
    private TreeItem<String> root;
    
    @FXML
    private TreeTableView<String> treeTableviewBancoDados;
    @FXML
    private TreeTableColumn<String, String> treetableColumnBancoDados;
    @FXML
    private TableView<?> tableView;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarTreeTableview();
        inicializarTableView();
    }

    public void inicializarTreeTableview() {
        bancodao = new BancoDAO();
        bancos = bancodao.listarBancos();
        tabeladao = new TabelaDAO();

        root = new TreeItem<>("BANCOS DE DADOS");

        for (Banco banco : bancos) {
            tabelas = tabeladao.listarTabelas(banco);
            parentBanco = new TreeItem<>(banco.getNome());
            for (Tabela tabela : tabelas) {
                itemTabela = new TreeItem<>(tabela.getNome());
                parentBanco.getChildren().addAll(itemTabela);
            }
            root.getChildren().addAll(parentBanco);
        }
        treeTableviewBancoDados.setRoot(root);
        treetableColumnBancoDados.setCellValueFactory((TreeTableColumn.CellDataFeatures<String, String> param) -> new SimpleStringProperty(param.getValue().getValue()));
    }
    
    public void inicializarTableView(){
        
    }
}
