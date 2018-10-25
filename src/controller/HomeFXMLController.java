/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.BancoDAO;
import dao.CampoDAO;
import dao.TabelaDAO;
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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Banco;
import modelo.Campo;
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
    private CampoDAO campoDAO;
    private ObservableList<Campo> obsservableCampos;
    private List<Campo> campos; 
    
    @FXML
    private TreeTableView<String> treeTableviewBancoDados;
    @FXML
    private TreeTableColumn<String, String> treetableColumnBancoDados;
    @FXML
    private TableView<Campo> tableView;
    @FXML
    private TableColumn<String, String> tableColumnAtributos;
    @FXML
    private TableColumn<String, String> tableColumnExibir;

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
        campoDAO = new CampoDAO();
        campos = campoDAO.listarCampos("dra", "cidade");
        
        tableColumnAtributos.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnExibir.setCellValueFactory(new PropertyValueFactory<>("checkbox"));
        
        obsservableCampos = FXCollections.observableArrayList(campos);
        tableView.setItems(obsservableCampos);
        
        
        
    }
    
    public void selecionarTabela(){
        String banco = itemTabela.getValue().toString();
        String table = parentBanco.getValue().toString();
        
        banco = treeTableviewBancoDados.getSelectionModel().getSelectedItem().toString();
        System.out.println(banco);
        System.out.println(table);
    
    }
}
