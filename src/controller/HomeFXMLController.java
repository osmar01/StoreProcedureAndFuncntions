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
import java.util.stream.IntStream;
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

    private TabelaDAO tabelaDAO;
    private List<Tabela> tabelas;
    private ObservableList<Tabela> observableTabelas;

    private ListView<Campo> listViewCampos;
    public List<Campo> camposSelecionados = new ArrayList<>();
    private List<Campo> filtrosSelecionados = new ArrayList<>();

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

        List<String> filtros = new ArrayList<>();
        filtros.add(selecione);
        filtros.add(igual);
        filtros.add(menor);
        filtros.add(maior);

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
        campos = campoDAO.listarCampos(getBancoSelecionado().getNome(), getTabelaSelecionada().getNome());
        inicializarComboboxCampoFiltro();
        inicializarComboboxOrdenador();
        setResultado();
        criarTabela();
        camposSelecionadosExibir();
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
        String campo = "";
        String filtro = "";
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
                filtro = filtro + filtrosSelecionados.get(i).getNome() + " " + filtrosSelecionados.get(i).getFiltro() + " "
                        + filtrosSelecionados.get(i).getValor() + "\n " + filtrosSelecionados.get(i).getOperador() + " ";
            }
        }
        textAreaResultado.setWrapText(true);
        textAreaResultado.setText("SELECT " + campo + " FROM " + getTabelaSelecionada().getNome() + filtro + ";");
    }

    public void criarTabela() {

        TableView<Campo> tabela = new TableView<>();
        //esconde o cabeçalho da tableview tabela
        tabela.widthProperty().addListener((ObservableValue<? extends Number> source, Number oldWidth, Number newWidth) -> {
            Pane header = (Pane) tabela.lookup("TableHeaderRow");
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

        tabela.getColumns().add(nomeColuna);
        tabela.getColumns().add(exibircheckbox);

        tabela.setItems(observableCampos);
        //ajusta o tamnaho da tabela, altura automatica e largura fixa de 160
        tabela.setPrefWidth(160);
        tabela.setFixedCellSize(25);
        tabela.prefHeightProperty().bind(tabela.fixedCellSizeProperty().multiply(Bindings.size(tabela.getItems()).add(1.01)));
        tabela.minHeightProperty().bind(tabela.prefHeightProperty());
        tabela.maxHeightProperty().bind(tabela.prefHeightProperty());

//        listViewCampos = new ListView<>();
//        listViewCampos.setItems(observableCampos);
//        listViewCampos.setPrefWidth(160);
//        listViewCampos.setPrefHeight(190);
        TabPane tabPaneTabela = new TabPane();
        //posicoes inicias da tabpane
        tabPaneTabela.setLayoutX(10);
        tabPaneTabela.setLayoutY(10);

        Tab tab = new Tab();
        tab.setText(getTabelaSelecionada().getNome());
        tab.setContent(tabela);

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

        if (tab.isSelected()) {
            String nomeTabela = tab.getText();
            System.out.println(nomeTabela);
            List<Campo> campos = campoDAO.listarCampos(nomeTabela, getTabelaSelecionada().getNome());
            ObservableList<Campo> observableCampos = FXCollections.observableArrayList(campos);
            comboboxCampoFiltro.setItems(observableCampos);
            

        }
//        tab.isSelected(new EventHandler<Event>() {
//            @Override
//            public void handle(Event event) {
//            }
//        });

        areaTrabalho.getChildren().addAll(tabPaneTabela);

//        tabela.setOnMouseDragged((MouseEvent me) -> {
//            double dragX = me.getSceneX() - dragAnchor.getX();
//            double dragY = me.getSceneY() - dragAnchor.getY();
//            //calculate new position of the node
//            double newXPosition = initX + dragX;
//            double newYPosition = initY + dragY;
//            //if new position do not exceeds borders of the rectangle, translate to this position
//            tabela.setTranslateX(newXPosition);
//            tabela.setTranslateY(newYPosition);
//        });
//
//        tabela.setOnMouseEntered((MouseEvent me) -> {
//            tabela.toFront();
//        });
//        tabela.setOnMousePressed((MouseEvent me) -> {
//            //when mouse is pressed, store initial position
//            initX = tabela.getTranslateX();
//            initY = tabela.getTranslateY();
//            dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
//        });
//        areaTrabalho.getChildren().addAll(tabela);
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
        if (!comboboxCampoFiltro.getSelectionModel().isEmpty()) {
            cmpo.setNome(comboboxCampoFiltro.getSelectionModel().getSelectedItem().getNome());
            cmpo.setFiltro(comboboxFiltro.getSelectionModel().getSelectedItem());
            cmpo.setValor(campoCriterio.getText());
            cmpo.setOperador(comboboxOperadorLogico.getSelectionModel().getSelectedItem());

            if (cmpo.getOperador() == null) {
                cmpo.setOperador("");
            }
            filtrosSelecionados.add(cmpo);
            setResultado();
        }
        System.out.println("Selecione um campo");
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
        //Parent root = FXMLLoader.load(getClass().getResource("/view/GerarSPFXML.fxml"));
        Parent root = loader.load();

        GerarSPlFXMLController spController = loader.getController();
        System.out.println(textAreaResultado.getText());
        spController.setResultado(this.textAreaResultado.getText());
        spController.setParametros(camposSelecionados);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
