/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.BancoDAO;
import dao.CampoDAO;
import dao.TabelaDAO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
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
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    private List<String> camposAgrupadores = new ArrayList<>();
    private List<Campo> parametros = new ArrayList<>();
    private List<Campo> camposOrdenadosPor = new ArrayList<>();
    private List<Campo> camposInserir = new ArrayList<>();
    private List<Campo> camposDelete = new ArrayList<>();
    private List<Campo> camposUpdate = new ArrayList<>();
    private List<Campo> camposUpdateCondicao = new ArrayList<>();
    private ObservableList<Campo> observableCampos;
    List<TabPane> tabelasTabPanes = new ArrayList<>();

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
    @FXML
    private Tab tabSelect;
    @FXML
    private Button btnAdionarAgrupador;
    @FXML
    private Button btnRemoverAgrupador;
    @FXML
    private Tab tabInsert;
    @FXML
    private ComboBox<Campo> comboboxCamposInsert;
    @FXML
    private Button btnAdicionarInput;
    @FXML
    private Button btnRemoverInput;
    @FXML
    private Tab tabDelete;
    @FXML
    private ComboBox<Campo> comboboxDelete;
    @FXML
    private TextField txtValorDelete;
    @FXML
    private Button btnAdicionarDelete;
    @FXML
    private Button btnRemoverDelete;
    @FXML
    private Tab tabUpdate;
    @FXML
    private TextField txtValorInsert;
    @FXML
    private ComboBox<Campo> comboboxUpdate;
    @FXML
    private TextField txtUpdateCampo;
    @FXML
    private TextField txtUpdateCondicao;
    @FXML
    private MenuItem menuItemAtualizar;

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

    public void inicializarComboboxAgrupadores() {
        observableAgrupador = FXCollections.observableArrayList(cmp.getAgrupadores());
        comboboxAgrupar.setItems(observableAgrupador);
    }

    @FXML
    public void getCampos() {
        campos = campoDAO.listarCampos(getBancoSelecionado().getNome(),
                getTabelaSelecionada());
        inicializarComboboxCampoFiltro();
        inicializaComboboxCamposInserir();
        inicializaComboboxCamposDelete();
        inicializaComboboxCamposUpdate();
        setResultado();
        //criarTabela();
        camposSelecionadosExibir();
        adicionarCampoOrdenarPorCrescente();
        adicionarCampoOrdenarPorDescrescente();
    }

    public void inicializarComboboxCampoFiltro() {
        observableCampos = FXCollections.observableArrayList(campos);
        comboboxCampoFiltro.setItems(observableCampos);

    }

    @FXML
    public void inicializarListViewBancos() {
        bancodao = new BancoDAO();
        bancos = bancodao.listarBancos();
        observableBancos = FXCollections.observableArrayList(bancos);
        listViewBancos.setItems(observableBancos);
        for (Banco banco : bancos) {
            System.out.println(banco.getNome());
        }
    }

    public Banco getBancoSelecionado() {
        Banco nomeBanco = listViewBancos.getSelectionModel().getSelectedItem();
        return nomeBanco;
    }

    public String getTabelaSelecionada() {
        String nomeTabela = "";
        if (listViewTabela.getSelectionModel().getSelectedItem() != null) {
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
        detectarArrasto();
        arrastoAreaTrabalho();
        novaPosicaoAreaTrabalho();
    }

    public void detectarArrasto() {
        listViewTabela.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                System.out.println("onDragDetected");

                Dragboard db = listViewTabela.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(listViewTabela.getSelectionModel().getSelectedItem().getNome());
                System.out.println(getTabelaSelecionada());
                db.setContent(content);

                event.consume();
            }
        });
    }

    public void arrastoAreaTrabalho() {
        areaTrabalho.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != areaTrabalho
                        && event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });
    }

    public void novaPosicaoAreaTrabalho() {
        areaTrabalho.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                initX = event.getSceneX() - 220;
                initY = event.getSceneY() - 26;
                System.out.println(initX);
                System.out.println(initY);
                event.consume();
                getCampos();
                criarTabela();
            }
        });
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

    @FXML
    public void setResultado() {
        query.setCamposSelecionados(camposSelecionados);
        query.setFiltrosSelecionados(filtrosSelecionados);
        query.setTabelasRelacionadas(tabelasRelacionadas);
        query.setCamposOrdenadosPor(camposOrdenadosPor);
        query.setAgrupadoresSelecionados(agrupadoresSelecionados);
        query.setCamposAgrupados(camposAgrupadores);
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
        System.out.println(getTabelaSelecionada());
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
        tabPaneTabela.setLayoutX(initX);
        tabPaneTabela.setLayoutY(initY);

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
                campos = campoDAO.listarCampos(getBancoSelecionado().getNome(), nomeTabela);
                observableCampos = FXCollections.observableArrayList(campos);
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

        tabelasTabPanes.add(tabPaneTabela);

        if (tabelasTabPanes.size() > 1) {
            System.out.println("natália");
            
            
            Bounds bounds1 = tabelasTabPanes.get(0).localToScene(tabelasTabPanes.get(0).getBoundsInLocal());
            Bounds bounds2 = tabelasTabPanes.get(1).localToScene(tabelasTabPanes.get(1).getBoundsInLocal());
            Line line = new Line(bounds1.getMinX() + bounds1.getWidth() / 2, bounds1.getMinY() + bounds1.getHeight() / 2,
                    bounds2.getMinX() + bounds2.getWidth() / 2, bounds2.getMinY() + bounds2.getHeight() / 2);
            line.setStrokeWidth(2.5f);
            line.getStrokeDashArray().addAll(10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d);
            line.toBack();

//            tabelasTabPanes.get(0).toFront();
//            tabelasTabPanes.get(1).toFront();
//            line.toBack();
            addLineAnimation(line);
            areaTrabalho.getChildren().addAll(line);
            areaTrabalho.getChildren().addAll(tabelasTabPanes.get(0));
            areaTrabalho.getChildren().addAll(tabelasTabPanes.get(1));
            //areaTrabalho.getChildren().addAll(tabPaneTabela);
            
        }
    }
    
    public void addLineAnimation(Line line) {
        double maxOffset
                = line.getStrokeDashArray().stream()
                        .reduce(
                                0d,
                                (a, b) -> a + b
                        );

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(
                                line.strokeDashOffsetProperty(),
                                0,
                                Interpolator.LINEAR
                        )
                ),
                new KeyFrame(
                        Duration.seconds(70),
                        new KeyValue(
                                line.strokeDashOffsetProperty(),
                                maxOffset,
                                Interpolator.LINEAR
                        )
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void limpar() {

        tabelasRelacionadas.clear();
        tabelasReferenciadas.clear();
        tabelasCriadas.clear();
        listViewTabela.getSelectionModel().clearSelection();
        filtrosSelecionados.clear();
        camposSelecionados.clear();
        camposOrdenadosPor.clear();
        setResultado();
        
        textAreaResultado.clear();
        areaTrabalho.getChildren().clear();
        comboboxCampoFiltro.setItems(null);
        campoCriterio.clear();

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
    public void adicionarCampoAgrupador() {
        String group = "";
        if (!comboboxAgrupar.getSelectionModel().isEmpty()) {
            group = comboboxAgrupar.getSelectionModel().getSelectedItem();
            if (!group.equals("Selecione")) {
                group = group + "(" + comboboxCampoFiltro.getSelectionModel().getSelectedItem() + ")";
                if (!agrupadoresSelecionados.contains(group)) {
                    agrupadoresSelecionados.add(group);
                    setResultado();
                }
            } else {
                group = comboboxCampoFiltro.getSelectionModel().getSelectedItem().getNome();
                camposAgrupadores.add(group);
                setResultado();
            }
        }
        comboboxAgrupar.getSelectionModel().select(0);
        for (int i = 0; i < agrupadoresSelecionados.size(); i++) {
            System.out.println(agrupadoresSelecionados.get(i));
        }
        for (int i = 0; i < camposAgrupadores.size(); i++) {
            System.out.println(camposAgrupadores.get(i));
        }
    }

    @FXML
    public void removerCampoAgrupador() {
        String group = "";
        if (!comboboxAgrupar.getSelectionModel().isEmpty()) {
            group = comboboxAgrupar.getSelectionModel().getSelectedItem();
            if (!group.equals("Selecione")) {
                group = group + "(" + comboboxCampoFiltro.getSelectionModel().getSelectedItem() + ")";
                if (agrupadoresSelecionados.contains(group)) {
                    agrupadoresSelecionados.remove(group);
                    setResultado();
                }
            } else {
                group = comboboxCampoFiltro.getSelectionModel().getSelectedItem().getNome();
                camposAgrupadores.remove(group);
                setResultado();
            }
        }
        for (int i = 0; i < agrupadoresSelecionados.size(); i++) {
            System.out.println(agrupadoresSelecionados.get(i));
        }
        for (int i = 0; i < camposAgrupadores.size(); i++) {
            System.out.println(camposAgrupadores.get(i));
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
        comboboxCampoFiltro.getSelectionModel().select(0);
        comboboxFiltro.getSelectionModel().select(0);
        comboboxOperadorLogico.getSelectionModel().select(0);

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

    //exportar -----------------------------------------------
    @FXML
    public void exportarConsulta() {
        try {
            // Conteudo
            String content = textAreaResultado.getText();

            // Cria arquivo
            File file = new File("query.sql");

            // Se o arquivo nao existir, ele gera
            if (!file.exists()) {
                file.createNewFile();
            }

            // Prepara para escrever no arquivo
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            // Escreve e fecha arquivo
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//Delete ---------------------------------------------
    public void inicializaComboboxCamposDelete() {
        comboboxDelete.setItems(observableCampos);
    }

    @FXML
    public void adicionarCamposDelete() {
        Campo cmp = new Campo();
        cmp.setNome(comboboxDelete.getSelectionModel().getSelectedItem().getNome());
        cmp.setValor(txtValorDelete.getText());
        camposDelete.add(cmp);
        setResultadoDelete();
        comboboxDelete.getSelectionModel().select(0);
    }

    @FXML
    public void setResultadoDelete() {
        query.setCamposDeleteSelecionados(camposDelete);
        query.setTabelaSelecionada(getTabelaSelecionada());
        textAreaResultado.setText(query.getQueryDelete());
    }

    //Insert ----------------------------------------------
    public void inicializaComboboxCamposInserir() {
        comboboxCamposInsert.setItems(observableCampos);
    }

    @FXML
    public void adicionarCamposInserir() {
        Campo cmp = new Campo();
        cmp.setNome(comboboxCamposInsert.getSelectionModel().getSelectedItem().getNome());
        cmp.setValor(txtValorInsert.getText());
        camposInserir.add(cmp);
        setResultadoInsert();
        comboboxCamposInsert.getSelectionModel().select(0);
    }

    @FXML
    public void setResultadoInsert() {
        query.setCamposInsertSelecionados(camposInserir);
        query.setTabelaSelecionada(getTabelaSelecionada());
        textAreaResultado.setText(query.getQueryInsert());
    }
    //Update -------------------------------------------------------

    public void inicializaComboboxCamposUpdate() {
        comboboxUpdate.setItems(observableCampos);
    }

    @FXML
    public void adicionarCamposUpdate() {
        Campo cmp = new Campo();
        cmp.setNome(comboboxUpdate.getSelectionModel().getSelectedItem().getNome());
        cmp.setValor(txtUpdateCampo.getText());
        camposUpdate.add(cmp);
        setResultadoUpdate();
        comboboxUpdate.getSelectionModel().select(0);
    }

    @FXML
    public void adicionarCamposUpdateCondicao() {
        Campo cmp = new Campo();
        cmp.setNome(comboboxUpdate.getSelectionModel().getSelectedItem().getNome());
        cmp.setValor(txtUpdateCondicao.getText());
        camposUpdateCondicao.add(cmp);
        setResultadoUpdate();
        comboboxUpdate.getSelectionModel().select(0);
    }

    @FXML
    public void setResultadoUpdate() {
        query.setCamposUpdateSelecionados(camposUpdate);
        query.setCamposCondicaoUpdateSelecionados(camposUpdateCondicao);
        query.setTabelaSelecionada(getTabelaSelecionada());
        textAreaResultado.setText(query.getQueryUpdate());
    }
    // Telas ------------------------------------------------

    @FXML
    public void abrirTelaAlteracao() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ResultFXML.fxml"));
        Parent root = loader.load();
        ResultFXMLController controller = loader.getController();
        controller.setControllerHome(this);
        controller.setListFiltrosSelecionados(filtrosSelecionados);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
