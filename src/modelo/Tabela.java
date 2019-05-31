/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;

/**
 *
 * @author Junnio
 */
public class Tabela {
    private String nome;
    private String nomeColuna;
    private String nomeReferenciada;
    private String nomeColunaReferenciada;
    private List<Campo> campos;
    List<Tabela> referenciadas;
    private int tamanho;

    public Tabela() {
    }

    public void exibirTabela(){
    }
    
    public void exibirCampos(){
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Campo> getCampos() {
        return campos;
    }

    public void setCampos(List<Campo> campos) {
        this.campos = campos;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public String getNomeReferenciada() {
        return nomeReferenciada;
    }

    public void setNomeReferenciada(String nomeReferenciada) {
        this.nomeReferenciada = nomeReferenciada;
    }

    public List<Tabela> getReferenciadas() {
        return referenciadas;
    }

    public void setReferenciadas(List<Tabela> referenciadas) {
        this.referenciadas = referenciadas;
    }

    public String getNomeColuna() {
        return nomeColuna;
    }

    public void setNomeColuna(String nomeColuna) {
        this.nomeColuna = nomeColuna;
    }

    public String getNomeColunaReferenciada() {
        return nomeColunaReferenciada;
    }

    public void setNomeColunaReferenciada(String nomeColunaReferenciada) {
        this.nomeColunaReferenciada = nomeColunaReferenciada;
    }
    
    
        
    @Override
    public String toString() {
        return nome;
    }
    
    
    
}
