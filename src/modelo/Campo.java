/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

/**
 *
 * @author Junnio
 */
public class Campo {

    private String nome;
    private String tipo;
    private String filtro;
    private String ordenador;
    private String valor;
    private String operador;
    private CheckBox checkbox;
    private ComboBox comboboxFiltro;
    private ComboBox comboboxOperador;
    List<Campo> camposSelecionados = new ArrayList<>();
    private ObservableList<String> observableFiltros;
    private ObservableList<String> observableOperadores;

    public Campo() {
        checkbox = new CheckBox();
        comboboxFiltro = new ComboBox<String>();
        observableFiltros = FXCollections.observableArrayList(getfiltros());
        comboboxFiltro.setItems(observableFiltros);
        comboboxOperador = new ComboBox<String>();
        observableOperadores = FXCollections.observableArrayList(getOperadores());
        comboboxOperador.setItems(observableOperadores);
        
    }

    public List<String> getfiltros() {
        List<String> filtros = new ArrayList<>();
        filtros.add("selecione");
        filtros.add("=");
        filtros.add("<");
        filtros.add(">");
        filtros.add("<=");
        filtros.add(">=");
        filtros.add("!=");
        return filtros;
    }

    public List<String> getOperadores() {
        List<String> operadores = new ArrayList<>();
        operadores.add("Selecione");
        operadores.add("AND");
        operadores.add("OR");
        operadores.add("NOT");
        return operadores;
    }

    public List<String> getAgrupadores() {
        List<String> agrupadores = new ArrayList<>();
        agrupadores.add("Selecione");
        agrupadores.add("COUNT");
        agrupadores.add("COUNT(*)");
        agrupadores.add("SUM");
        agrupadores.add("MAX");
        agrupadores.add("MIN");
        agrupadores.add("AVG");
        return agrupadores;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public String getOrdenador() {
        return ordenador;
    }

    public void setOrdenador(String ordenador) {
        this.ordenador = ordenador;
    }

    public CheckBox getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(CheckBox checkbox) {
        this.checkbox = checkbox;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return nome;
    }

    public ComboBox getComboboxFiltro() {
        return comboboxFiltro;
    }

    public void setComboboxFiltro(ComboBox comboboxFiltro) {
        this.comboboxFiltro = comboboxFiltro;
    }

    public ComboBox getComboboxOperador() {
        return comboboxOperador;
    }

    public void setComboboxOperador(ComboBox comboboxOperador) {
        this.comboboxOperador = comboboxOperador;
    }
    
    

}
