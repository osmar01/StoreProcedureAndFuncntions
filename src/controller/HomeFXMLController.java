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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
    private CampoDAO campoDAO = new CampoDAO();
    private List<Campo> campos;

    private ObservableList<Campo> observableCampos;
    private ObservableList<String> observableFiltro;
    private ObservableList<String> observableOperador;

    private TabelaDAO tabelaDAO = new TabelaDAO();
    private List<Tabela> tabelas;
    private List<Tabela> tabelasCriadas = new ArrayList<>();
    private List<Tabela> tabelasReferenciadas = new ArrayList<>();
    private List<Tabela> tabelasRelacionadas = new ArrayList<>();
    private ObservableList<Tabela> observableTabelas;

    public List<Campo> camposSelecionados = new ArrayList<>();
    private List<Campo> filtrosSelecionados = new ArrayList<>();
    private List<Campo> camposOrdenadosPor = new ArrayList<>();

    String textoTab;

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
    private AnchorPane areaTrabalho;
    @FXML
    private TextField campoCriterio;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnAdd1;
    @FXML
    private Button btnGerarStoredProcedure;

    private double initX;
    private double initY;
    private Point2D dragAnchor;

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
        String selecione = "Selecione";
        String igual = "=";
        String maior = ">";
        String menor = "<";
        String menorIgual = "<=";
        String maoirIgual = ">=";
        String diferente = "!=";

        List<String> filtros = new ArrayList<>();
        filtros.add(selecione);
        filtros.add(igual);
        filtros.add(menor);
        filtros.add(maior);
        filtros.add(menorIgual);
        filtros.add(maoirIgual);
        filtros.add(diferente);

        observableFiltro = FXCollections.observableArrayList(filtros);
        comboboxFiltro.setItems(observableFiltro);
    }

    public void inicializarComboboxOperadorLogico() {
        String selecione = "Selecione";
        String and = "AND";
        String or = "OR";
        String not = "NOT";

        List<String> operadores = new ArrayList<>();
        operadores.add(selecione);
        operadores.add(and);
        operadores.add(or);
        operadores.add(not);

        observableOperador = FXCollections.observableArrayList(operadores);
        comboboxOperadorLogico.setItems(observableOperador);
    }

    @FXML
    public void getCampos() {
        campos = campoDAO.listarCampos(getBancoSelecionado().getNome(),
                getTabelaSelecionada().getNome());
        inicializarComboboxCampoFiltro();
        inicializarComboboxOrdenador();
        setResultado();
        criarTabela();
        camposSelecionadosExibir();
        adicionarCampoOrdenarPor();
    }

    public void inicializarComboboxCampoFiltro() {
        observableCampos = FXCollections.observableArrayList(campos);
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
        String campo = "";
        String filtro = "";
        String rel = "";
        String ordenador = "";
        String pontoVirgula = ";";
        if (camposSelecionados.isEmpty()) {
            campo = "*";
        } else {
            campo = camposSelecionados.get(0).toString();
            for (int i = 1; i < camposSelecionados.size(); i++) {
                campo = campo + ", " + camposSelecionados.get(i).toString();
            }
        }
        if (!filtrosSelecionados.isEmpty()) {
            filtro = " WHERE ";
            for (int i = 0; i < filtrosSelecionados.size(); i++) {
                if (filtrosSelecionados.get(i).getOperador().equals("Selecione")) {
                    filtrosSelecionados.get(i).setOperador("");
                }
                filtro = filtro + filtrosSelecionados.get(i).getNome() + " "
                        + filtrosSelecionados.get(i).getFiltro() + " "
                        + filtrosSelecionados.get(i).getValor() + "\n "
                        + filtrosSelecionados.get(i).getOperador() + " ";
            }
        }

        if (!tabelasRelacionadas.isEmpty()) {
            rel = rel + tabelasRelacionadas.get(0).getNome() + " INNER JOIN "
                    + tabelasRelacionadas.get(0).getNomeReferenciada() + " ON "
                    + tabelasRelacionadas.get(0).getNomeColuna() + " = "
                    + tabelasRelacionadas.get(0).getNomeColunaReferenciada() + "\n";
            for (int i = 1; i < tabelasRelacionadas.size(); i++) {
                if (tabelasRelacionadas.get(i).getNome() != " "
                        || tabelasRelacionadas.get(i).getNomeReferenciada() != " ") {
                    rel = rel + " INNER JOIN " + tabelasRelacionadas.get(i).getNome()
                            + tabelasRelacionadas.get(i).getNomeReferenciada() + " ON "
                            + tabelasRelacionadas.get(i).getNomeColuna() + " = "
                            + tabelasRelacionadas.get(i).getNomeColunaReferenciada() + "\n";
                }
            }
        } else if (getTabelaSelecionada() != null) {
            rel = getTabelaSelecionada().getNome();
        }

        if (!camposOrdenadosPor.isEmpty()) {
            ordenador = " ORDER BY " + camposOrdenadosPor.get(0).getNome()
                    + " " + camposOrdenadosPor.get(0).getOrdenador();
            for (int i = 1; i < camposOrdenadosPor.size(); i++) {
                ordenador = ordenador + ", " + camposOrdenadosPor.get(i).getNome()
                        + " " + camposOrdenadosPor.get(i).getOrdenador();
            }
        } else {
            ordenador = ";";
            pontoVirgula = "";
        }

        textAreaResultado.setWrapText(true);
        textAreaResultado.setText("SELECT " + campo + " FROM " + rel + filtro + ordenador + pontoVirgula);
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

        TableColumn<Campo, String> nomeColuna = new TableColumn<>(getTabelaSelecionada().getNome());
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
        tab.setText(getTabelaSelecionada().getNome());
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
                comboboxOrdenador.setItems(observableCampos);
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
                getTabelaSelecionada().getNome());
        for (int i = 0; i < listAux.size(); i++) {
            tabelasReferenciadas.add(listAux.get(i));

        }
        for (int i = 0; i < tabelasReferenciadas.size(); i++) {
            System.out.println(tabelasReferenciadas.get(i).getNomeReferenciada());
        }

        setTabelasCriadas(getTabelaSelecionada().getNome(), campos, tabelasReferenciadas);

        verificaRelacionameto();

        atualizaRelacionamento();

        setResultado();

        areaTrabalho.getChildren().addAll(tabPaneTabela);
    }

    public void verificaRelacionameto() {
        tabelasRelacionadas.clear();
        for (int i = 0; i < tabelasReferenciadas.size(); i++) {
            for (int j = 0; j < tabelasCriadas.size(); j++) {
                if (tabelasReferenciadas.get(i).getNomeReferenciada().equals(tabelasCriadas.get(j).getNome())) {
                    Tabela tabela = new Tabela();
                    tabela.setNomeReferenciada(tabelasReferenciadas.get(i).getNomeReferenciada());//cidade
                    tabela.setNome(tabelasReferenciadas.get(i).getNome());//bairro
                    tabela.setNomeColuna(tabelasReferenciadas.get(i).getNomeColuna());
                    tabela.setNomeColunaReferenciada(tabelasReferenciadas.get(i).getNomeColunaReferenciada());
                    tabelasRelacionadas.add(tabela);
                }
            }
        }
        System.out.println("tabelas relacionadas ");
        for (int i = 0; i < tabelasRelacionadas.size(); i++) {
            System.out.println("referencida: " + tabelasRelacionadas.get(i).getNomeReferenciada());
            System.out.println("nome: " + tabelasRelacionadas.get(i).getNome());
        }
    }

    public void atualizaRelacionamento() {
        int cont = 0;

        System.out.println("atualiza metodo");
        for (int i = 0; i < tabelasCriadas.size(); i++) {
            for (int j = 0; j < tabelasRelacionadas.size(); j++) {

                if (tabelasCriadas.get(i).getNome().equals(tabelasRelacionadas.get(j).getNome())) {
                    cont++;
                    if (cont >= 2) {
                        tabelasRelacionadas.get(j).setNome(" ");
                    }
                }
                if (tabelasCriadas.get(i).getNome().equals(tabelasRelacionadas.get(j).getNomeReferenciada())) {
                    cont++;
                    if (cont >= 2) {
                        tabelasRelacionadas.get(j).setNomeReferenciada(" ");
                    }
                }
            }
            cont = 0;
        }

    }

    public void setTabelasCriadas(String nome, List<Campo> campos, List<Tabela> referenciadas) {
        Tabela tabela = new Tabela();
        tabela.setNome(nome);
        tabela.setCampos(campos);
        tabela.setReferenciadas(referenciadas);
        tabelasCriadas.add(tabela);
    }

    @FXML
    public void limpar() {
        tabelasRelacionadas.clear();
        tabelasReferenciadas.clear();
        tabelasCriadas.clear();
        listViewTabela.getSelectionModel().clearSelection();
        filtrosSelecionados.clear();
        setResultado();
    }

    public void adicionarCampoOrdenarPor() {
        checkBoxCrescente.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!"Selecione".equals(comboboxOrdenador.getSelectionModel().getSelectedItem().getNome())) {
                    String nome = comboboxOrdenador.getSelectionModel().getSelectedItem().getNome();
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
    public String getTipoCampo(List<Campo> campos, String nome){
        String tipoCampo="";
        for (Campo campo : campos) {
            if(campo.getNome().equals(nome)){
                tipoCampo = campo.getTipo();
            }            
        }
        return tipoCampo;
    }
    
    public void tornarParametro(){
        for (int i = 0; i < filtrosSelecionados.size(); i++) {            
            filtrosSelecionados.get(i).setValor("param_"+i);            
        }        
        setResultado();
    }

    @FXML
    public void removerCamposFiltrados() {
        String campoRemove;
        if (!comboboxCampoFiltro.getSelectionModel().isEmpty()) {
            campoRemove = comboboxCampoFiltro.getSelectionModel().getSelectedItem().getNome();
            for (int i = 0; i < filtrosSelecionados.size(); i++) {
                if (filtrosSelecionados.get(i).getNome().equals(campoRemove)) {
                    filtrosSelecionados.remove(filtrosSelecionados.get(i));
                }
            }
        }
        setResultado();
    }

    @FXML
    public void abrirTelaGerarStoredProcedure() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/GerarSPFXML.fxml"));
        Parent root = loader.load();

        GerarSPlFXMLController spController = loader.getController();
        System.out.println(textAreaResultado.getText());
        tornarParametro();
        spController.setResultado(this.textAreaResultado.getText());
        spController.setParametros(filtrosSelecionados);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
