/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.BancoDAO;
import dao.CampoDAO;
import dao.TabelaDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modelo.Banco;
import modelo.Campo;
import modelo.ExpressaoSQL;
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

    private ObservableList<String> observableFiltro;
    private ObservableList<String> observableOperador;
    private ObservableList<String> observableAgrupador;

    private TabelaDAO tabelaDAO = new TabelaDAO();
    private Tabela tabela = new Tabela();
    private List<Tabela> tabelas;
    private List<Tabela> tabelasCriadas = new ArrayList<>();
    private List<Tabela> tabelasReferenciadas = new ArrayList<>();
    private List<Tabela> tabelasRelacionadas = new ArrayList<>();
    private ObservableList<Tabela> observableTabelas;

    private Campo cmp = new Campo();
    private CampoDAO campoDAO = new CampoDAO();
    private List<Campo> campos;
    private List<Campo> camposSelecionados = new ArrayList<>();
    private List<Campo> filtrosSelecionados = new ArrayList<>();
    private List<String> agrupadoresSelecionados = new ArrayList<>();
    private List<Campo> parametros = new ArrayList<>();
    private List<Campo> camposOrdenadosPor = new ArrayList<>();
    private ObservableList<Campo> observableCampos;

    private ExpressaoSQL query = new ExpressaoSQL();
    private String textoTab;

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
    private CheckBox checkBoxCrescente;
    @FXML
    private CheckBox checkBoxDecrescente;
    @FXML
    private TextArea textAreaResultado;
    @FXML
    private AnchorPane anchorPanePrincipal;
    @FXML
    private AnchorPane areaTrabalho;
    @FXML
    private TextField campoCriterio;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemover;
    @FXML
    private Button btnGerarStoredProcedure;

    @FXML
    private RadioButton radioFunction;
    @FXML
    private RadioButton radioStoredProcedure;

    String radioSelected;

    private double initX;
    private double initY;
    private Point2D dragAnchor;
    @FXML
    private Button btnAlterar;
    @FXML
    private ComboBox<String> comboboxAgrupar;

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
        inicializarComboboxAgrupadores();
        inicializaRadioButton();

    }

    public void inicializarComboboxFiltro() {
        observableFiltro = FXCollections.observableArrayList(cmp.getfiltros());
        comboboxFiltro.setItems(observableFiltro);
    }

    public void inicializarComboboxOperadorLogico() {
        observableOperador = FXCollections.observableArrayList(cmp.getOperadores());
        comboboxOperadorLogico.setItems(observableOperador);
    }
    
    public void inicializarComboboxAgrupadores(){
        observableAgrupador = FXCollections.observableArrayList(cmp.getAgrupadores());
        comboboxAgrupar.setItems(observableAgrupador);
    }
    @FXML
    public void getCampos() {
        campos = campoDAO.listarCampos(getBancoSelecionado().getNome(),
                getTabelaSelecionada());
        inicializarComboboxCampoFiltro();
        setResultado();
        criarTabela();
        camposSelecionadosExibir();
        adicionarCampoOrdenarPorCrescente();
        adicionarCampoOrdenarPorDescrescente();        
    }

    public void inicializarComboboxCampoFiltro() {
        observableCampos = FXCollections.observableArrayList(campos);
        comboboxCampoFiltro.setItems(observableCampos);

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

    public String getTabelaSelecionada() {
        String nomeTabela = "";        
        if (listViewTabela.getSelectionModel().getSelectedItem()!=null) {
            nomeTabela = listViewTabela.getSelectionModel().getSelectedItem().getNome();
            return nomeTabela;
        }
        return "";
    }

    @FXML
    public void inicializarListViewTabela() {
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

        query.setCamposSelecionados(camposSelecionados);
        query.setFiltrosSelecionados(filtrosSelecionados);
        query.setTabelasRelacionadas(tabelasRelacionadas);
        query.setCamposOrdenadosPor(camposOrdenadosPor);
        query.setTabelaSelecionada(getTabelaSelecionada());
        textAreaResultado.setText(query.getQuery());

    }

    public void criarTabela() {

        TableView<Campo> tabelaView = new TableView<>();
        //esconde o cabeçalho da tableview tabela
        tabelaView.widthProperty().addListener((ObservableValue<? extends Number> source, Number oldWidth, Number newWidth) -> {
            Pane header = (Pane) tabelaView.lookup("TableHeaderRow");
            if (header.isVisible()) {
                header.setMaxHeight(0);
                header.setMinHeight(0);
                header.setPrefHeight(0);
                header.setVisible(false);
            }
        });

        TableColumn<Campo, String> nomeColuna = new TableColumn<>(getTabelaSelecionada());
        nomeColuna.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nomeColuna.setSortable(false);

        TableColumn<Campo, String> exibircheckbox = new TableColumn<>();
        exibircheckbox.setCellValueFactory(new PropertyValueFactory<>("checkbox"));
        exibircheckbox.setSortable(false);
        //------------
        tabelaView.getColumns().add(nomeColuna);
        tabelaView.getColumns().add(exibircheckbox);

        tabelaView.setItems(observableCampos);
        //ajusta o tamnaho da tabela, altura automatica e largura fixa de 160
        tabelaView.setPrefWidth(160);
        tabelaView.setFixedCellSize(25);
        tabelaView.prefHeightProperty().bind(tabelaView.fixedCellSizeProperty()
                .multiply(Bindings.size(tabelaView.getItems()).add(1.01)));
        tabelaView.minHeightProperty().bind(tabelaView.prefHeightProperty());
        tabelaView.maxHeightProperty().bind(tabelaView.prefHeightProperty());

        TabPane tabPaneTabela = new TabPane();
        //posicoes inicias da tabpane
        tabPaneTabela.setLayoutX(10);
        tabPaneTabela.setLayoutY(10);

        Tab tab = new Tab();
        tab.setText(getTabelaSelecionada());
        tab.setContent(tabelaView);

        tabPaneTabela.getTabs().add(tab);
        //calcula a nova posição arrastando a tabela
        tabPaneTabela.setOnMouseDragged((MouseEvent me) -> {
            double dragX = me.getSceneX() - dragAnchor.getX();
            double dragY = me.getSceneY() - dragAnchor.getY();

            double newXPosition = initX + dragX;
            double newYPosition = initY + dragY;

            tabPaneTabela.setTranslateX(newXPosition);
            tabPaneTabela.setTranslateY(newYPosition);
        });

        tabPaneTabela.setOnMouseEntered((MouseEvent me) -> {
            tabPaneTabela.toFront();
        });
        //calcula a nova posicao enquanto o click é pressionado
        tabPaneTabela.setOnMousePressed((MouseEvent me) -> {
            initX = tabPaneTabela.getTranslateX();
            initY = tabPaneTabela.getTranslateY();
            dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
        });

        tabPaneTabela.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                String nomeTabela = tab.getText();
                List<Campo> campos = campoDAO.listarCampos(getBancoSelecionado().getNome(), nomeTabela);
                ObservableList<Campo> observableCampos = FXCollections.observableArrayList(campos);
                comboboxCampoFiltro.setItems(observableCampos);
            }
        });
        textoTab = tab.getText();

        tab.setOnClosed(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
//                if (!tabelasCriadas.isEmpty()) {
//                    for (int i = 0; i < tabelasCriadas.size(); i++) {
//                        if (tabelasCriadas.get(i).getNome().equals(textoTab)) {
//                            System.out.println("removida da tabela criadas: " + tabelasCriadas.get(i).getNome());
//                            tabelasCriadas.remove(tabelasCriadas.get(i));
//                            listViewTabela.getSelectionModel().clearSelection();
//                            setResultado();
//
//                        }
//                    }
//                }
//                if (!tabelasReferenciadas.isEmpty()) {
//                    int tam = tabelasReferenciadas.size();                    
//                    for (int i = 0; i < tam; ) {
//                        if (tabelasReferenciadas.get(i).getNome().equals(textoTab)) {
//                            System.out.println("removida da lista referenciadas: " + tabelasReferenciadas.get(i).getNome());
//                            tabelasReferenciadas.remove(i);
//                            tam--;
//                            listViewTabela.getSelectionModel().clearSelection();
//                            setResultado();
//                        }else{
//                            i++;
//                        }
//                    }
//                }
//                if (!tabelasRelacionadas.isEmpty()) {
//                    for (int i = 0; i < tabelasRelacionadas.size(); i++) {
//                        if (tabelasRelacionadas.get(i).getNomeReferenciada().equals(textoTab)) {
//                            System.out.println("removida da lista relacionadas: " + tabelasRelacionadas.get(i).getNome());
//                            tabelasRelacionadas.remove(i);
//                            listViewTabela.getSelectionModel().clearSelection();
//                            setResultado();
//                        }
//                    }
//                }
//
//                for (int i = 0; i < tabelasReferenciadas.size(); i++) {
//                    System.out.println(tabelasReferenciadas.get(i).getNomeReferenciada());
//                    //System.out.println(tabelasReferenciadas.get(i).getNome());
//                }
            }
        });

        //acumula as tabelas referencias 
        List<Tabela> listAux = tabelaDAO.referenciadas(getBancoSelecionado().getNome(),
                getTabelaSelecionada());
        for (int i = 0; i < listAux.size(); i++) {
            tabelasReferenciadas.add(listAux.get(i));

        }
        for (int i = 0; i < tabelasReferenciadas.size(); i++) {
            System.out.println(tabelasReferenciadas.get(i).getNomeReferenciada());
        }

        tabelasCriadas = tabela.setTabelasCriadas(getTabelaSelecionada(),
                campos, tabelasCriadas);

        tabelasRelacionadas = tabela.verificaRelacionameto(tabelasRelacionadas,
                tabelasReferenciadas, tabelasCriadas);

        tabelasRelacionadas = tabela.atualizaRelacionamento(tabelasRelacionadas, tabelasCriadas);

        setResultado();

        areaTrabalho.getChildren().addAll(tabPaneTabela);
    }

    @FXML
    public void limpar() {
        
        tabelasRelacionadas.clear();
        tabelasReferenciadas.clear();
        tabelasCriadas.clear();
        listViewTabela.getSelectionModel().clearSelection();
        filtrosSelecionados.clear();
        camposSelecionados.clear();
        setResultado();
        textAreaResultado.clear();
        areaTrabalho.getChildren().clear();
        comboboxCampoFiltro.setItems(null);

    }

    public void adicionarCampoOrdenarPorCrescente() {
        checkBoxCrescente.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!"Selecione".equals(comboboxCampoFiltro.getSelectionModel().
                        getSelectedItem().getNome())) {
                    String nome = comboboxCampoFiltro.getSelectionModel().
                            getSelectedItem().getNome();
                    for (Campo campo : campos) {
                        if (campo.getNome().equals(nome)) {
                            campo.setNome(nome);
                            campo.setOrdenador(" ASC");

                            if (checkBoxCrescente.isSelected()) {
                                if (!camposOrdenadosPor.contains(campo)) {
                                    camposOrdenadosPor.add(campo);
                                }
                                setResultado();
                            }
                            if (!checkBoxCrescente.isSelected()) {
                                camposOrdenadosPor.remove(campo);
                                setResultado();
                            }

                        }
                    }
                }

            }
        });
    }

    public void adicionarCampoOrdenarPorDescrescente() {
        checkBoxDecrescente.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (!"Selecione".equals(comboboxCampoFiltro.getSelectionModel().getSelectedItem().getNome())) {
                    String nome = comboboxCampoFiltro.getSelectionModel().getSelectedItem().getNome();
                    for (Campo campo : campos) {
                        if (campo.getNome().equals(nome)) {
                            campo.setNome(nome);
                            campo.setOrdenador(" DESC");

                            if (checkBoxDecrescente.isSelected()) {
                                if (!camposOrdenadosPor.contains(campo)) {
                                    camposOrdenadosPor.add(campo);
                                }
                                setResultado();
                            }
                            if (!checkBoxDecrescente.isSelected()) {
                                camposOrdenadosPor.remove(campo);
                                setResultado();
                            }
                        }
                    }
                }

            }
        });
    }

    public void camposSelecionadosExibir() {
        for (Campo campo : campos) {
            campo.getCheckbox().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (campo.getCheckbox().isSelected()) {
                        if (!camposSelecionados.contains(campo)) {
                            camposSelecionados.add(campo);
                        }
                        setResultado();
                    }
                    if (!campo.getCheckbox().isSelected()) {
                        camposSelecionados.remove(campo);
                        setResultado();

                    }
                }
            });
        }
    }

    @FXML
    public void adicionarCampoAgrupador(){        
        String nome = "";
        if(!comboboxAgrupar.getSelectionModel().isEmpty()){
            nome = comboboxAgrupar.getSelectionModel().getSelectedItem();
            agrupadoresSelecionados.add(nome);
        }
        for (int i = 0; i < agrupadoresSelecionados.size(); i++) {
            System.out.println(agrupadoresSelecionados.get(i));
        }
        
    }
    
    @FXML
    public void adicionarCamposFiltrar() {
        Campo cmpo = new Campo();
        String nome = "";
        if (!comboboxCampoFiltro.getSelectionModel().isEmpty()) {
            nome = comboboxCampoFiltro.getSelectionModel().getSelectedItem().getNome();
            cmpo.setNome(nome);
            cmpo.setFiltro(comboboxFiltro.getSelectionModel().getSelectedItem());
            cmpo.setValor(campoCriterio.getText());
            cmpo.setOperador(comboboxOperadorLogico.getSelectionModel().getSelectedItem());
            cmpo.setTipo(getTipoCampo(campos, nome));

            if (cmpo.getOperador() == null) {
                cmpo.setOperador("");
            }
            filtrosSelecionados.add(cmpo);
            setResultado();
        }
        System.out.println("Selecione um campo");
    }

    //retorna o tipo do campo (varchar, int, double)
    public String getTipoCampo(List<Campo> campos, String nome) {
        String tipoCampo = "";
        for (Campo campo : campos) {
            if (campo.getNome().equals(nome)) {
                tipoCampo = campo.getTipo();
            }
        }
        return tipoCampo;
    }

    @FXML
    public void removerCamposFiltrados() {
        if (!comboboxCampoFiltro.getSelectionModel().isEmpty()) {
            String campoRemove = comboboxCampoFiltro.getSelectionModel().getSelectedItem().getNome();
            for (int i = 0; i < filtrosSelecionados.size(); i++) {
                if (filtrosSelecionados.get(i).getNome().equals(campoRemove)) {
                    filtrosSelecionados.remove(filtrosSelecionados.get(i));
                }
            }
        }
        setResultado();
    }

    public void inicializaRadioButton() {
        ToggleGroup group = new ToggleGroup();

        radioStoredProcedure.setToggleGroup(group);
        radioFunction.setToggleGroup(group);

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> observable,
                    Toggle oldValue, Toggle newValue) {
                if (group.getSelectedToggle() != null) {
//                    radioSelected = group.getSelectedToggle().getToggleGroup().getUserData().toString();
//                    System.out.println(radioSelected);;
                }
            }
        });

    }

    public boolean verificaRadioButton() {
        if (radioStoredProcedure.isSelected() || radioFunction.isSelected()) {
            return true;
        } else {
            System.out.println("Selecione uma opção!");
            return false;
        }
    }

    @FXML
    public void abrirTelaGerarStoredProcedure() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();

        if (verificaRadioButton()) {
            if (radioStoredProcedure.isSelected()) {
                loader.setLocation(getClass().getResource("/view/GerarSPFXML.fxml"));
                Parent root = loader.load();
                GerarSPlFXMLController spController = loader.getController();
                //System.out.println(textAreaResultado.getText());                
                spController.setQueryReference(query);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            if (radioFunction.isSelected()) {
                loader.setLocation(getClass().getResource("/view/GerarFunction.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }

    }

}
