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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
    private ObservableList<Banco> observableBancos;
    private CampoDAO campoDAO;
    private List<Campo> campos;
    private ObservableList<Campo> observableCampos;
    private ObservableList<String> observableFiltro;
    private ObservableList<String> observableOperador;
    private TabelaDAO tabelaDAO;
    private List<Tabela> tabelas;
    private ObservableList<Tabela> observableTabelas;
    private TableView<Campo> tabela;
    private TableColumn<Campo, String> nomeColuna;
    private TableColumn<Campo, String> exibirColuna;
    private TabPane tabPaneTabela;
    private Tab tabNome;
    private ListView<Campo> listViewCampos;

    @FXML
    private TableView<Campo> tableView;
    @FXML
    private TableColumn<String, String> tableColumnAtributos;
    @FXML
    private TableColumn<String, String> tableColumnExibir;
    @FXML
    private ListView<Banco> listViewBancos;
    @FXML
    private ListView<Tabela> listViewTabela;
    @FXML
    private ComboBox<Campo> comboboxCampoFiltro;
    @FXML
    private ComboBox<String> comboboxFiltro;
    @FXML
    private ComboBox<String> comboboxOperadorLogico;
    @FXML
    private ComboBox<Campo> comboboxOrdenador;
    @FXML
    private CheckBox checkBoxCrescente;
    @FXML
    private CheckBox checkBoxDecrescente;
    @FXML
    private TextArea textAreaResultado;
    @FXML
    private AnchorPane anchorPanePrincipal;
    @FXML
    private AnchorPane anchorPaneTabela;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarListViewBancos();
        inicializarComboboxFiltro();
        inicializarComboboxOperadorLogico();
    }

    public void inicializarComboboxFiltro() {
        String igual = "=";
        String maior = ">";
        String menor = "<";

        List<String> filtros = new ArrayList<>();
        filtros.add(igual);
        filtros.add(menor);
        filtros.add(maior);

        observableFiltro = FXCollections.observableArrayList(filtros);
        comboboxFiltro.setItems(observableFiltro);
    }

    public void inicializarComboboxOperadorLogico() {
        String and = "AND";
        String or = "OR";
        String not = "NOT";

        List<String> operadores = new ArrayList<>();
        operadores.add(and);
        operadores.add(or);
        operadores.add(not);

        observableOperador = FXCollections.observableArrayList(operadores);
        comboboxOperadorLogico.setItems(observableOperador);
    }

    @FXML
    public void getCampos() {
        campoDAO = new CampoDAO();
        campos = campoDAO.listarCampos(getBancoSelecionado().getNome(), getTabelaSelecionada().getNome());
        observableCampos = FXCollections.observableArrayList(campos);
        inicializarTableViewCampos();
        inicializarComboboxCampoFiltro();
        inicializarComboboxOrdenador();
        setResultado();
        criarTabela();

    }

    public void inicializarTableViewCampos() {
        tableColumnAtributos.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnExibir.setCellValueFactory(new PropertyValueFactory<>("checkbox"));

        tableView.setItems(observableCampos);
    }

    public void inicializarComboboxCampoFiltro() {
        comboboxCampoFiltro.setItems(observableCampos);
    }

    public void inicializarComboboxOrdenador() {
        comboboxOrdenador.setItems(observableCampos);
    }

    public void inicializarListViewBancos() {
        bancodao = new BancoDAO();
        bancos = bancodao.listarBancos();
        observableBancos = FXCollections.observableArrayList(bancos);
        listViewBancos.setItems(observableBancos);
    }

    public Banco getBancoSelecionado() {
        Banco nomeBanco = listViewBancos.getSelectionModel().getSelectedItem();
        return nomeBanco;
    }

    public Tabela getTabelaSelecionada() {
        Tabela nomeTabela = listViewTabela.getSelectionModel().getSelectedItem();
        return nomeTabela;
    }

    @FXML
    public void inicializarListViewTabela() {
        tabelaDAO = new TabelaDAO();
        tabelas = tabelaDAO.listarTabelas(getBancoSelecionado().getNome());
        observableTabelas = FXCollections.observableArrayList(tabelas);
        listViewTabela.setItems(observableTabelas);
    }

    @FXML
    public void marcarCheckBoxCrescente() {
        if (checkBoxCrescente.isSelected()) {
            checkBoxDecrescente.setSelected(false);
        }
    }

    @FXML
    public void marcarCheckBoxDecrescente() {
        if (checkBoxDecrescente.isSelected()) {
            checkBoxCrescente.setSelected(false);
        }
    }

    public void setResultado() {
        String resultado;

        textAreaResultado.setText("SELECT * FROM " + getTabelaSelecionada().getNome() + ";");
    }

    public void criarTabela() {
                                      
        Pane painelTabela = new Pane();
        painelTabela.setLayoutX(50);
        painelTabela.setLayoutY(40); 
        
        listViewCampos = new ListView<>();
        listViewCampos.setItems(observableCampos);
        listViewCampos.setPrefWidth(160);
        listViewCampos.setPrefHeight(190);
               
        tabPaneTabela = new TabPane();
        tabNome = new Tab();
                
        tabNome.setText(getTabelaSelecionada().getNome());
        tabNome.setContent(listViewCampos);        
        
        tabPaneTabela.getTabs().add(tabNome);
                
        painelTabela.getChildren().add(tabPaneTabela);        
        anchorPaneTabela.getChildren().addAll(painelTabela);

    }
    
    public void posicionarTabela(){
        
        
        
    
    }

}
