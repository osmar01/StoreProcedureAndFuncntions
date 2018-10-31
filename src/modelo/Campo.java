/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javafx.scene.control.CheckBox;

/**
 *
 * @author Junnio
 */
public class Campo {
    private String nome;
    private String filtro;
    private String ordenador;
    private CheckBox checkbox;

    public Campo() {
        checkbox = new CheckBox();
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

    @Override
    public String toString() {
        return nome;
    }
    
    
    
}
