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
public class Parametro {
    
    private String nome;
    private String tipo;
    private CheckBox checkbox;       

    public Parametro() {
        checkbox = new CheckBox();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public CheckBox getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(CheckBox checkbox) {
        this.checkbox = checkbox;
    }
    
    @Override
    public String toString() {
        return nome +" "+ tipo;
    }
    
}
