/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;
import javafx.geometry.Bounds;
import javafx.scene.control.TabPane;
import javafx.scene.shape.Line;

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
    private List<TabPane> tabelasTabPane;

    public Tabela() {
    }

    public List<Tabela> verificaRelacionameto(List<Tabela> tabelasRelacionadas, List<Tabela> tabelasReferenciadas, List<Tabela> tabelasCriadas) {
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
            System.out.println("referenciada: " + tabelasRelacionadas.get(i).getNomeReferenciada());
            System.out.println("nome: " + tabelasRelacionadas.get(i).getNome());
        }
        return tabelasRelacionadas;
    }
    
      public List<Tabela> atualizaRelacionamento(List<Tabela> tabelasRelacionadas, List<Tabela> tabelasCriadas) {
        int cont = 0;

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
        return tabelasRelacionadas;
    }

    public List<Tabela> setTabelasCriadas(String nome, List<Campo> campos, List<Tabela> tabelasCriadas) {
        Tabela tabela = new Tabela();
        tabela.setNome(nome);
        tabela.setCampos(campos);
        tabelasCriadas.add(tabela);
        return tabelasCriadas;
    }

    public void exibirTabela() {
    }

    public void exibirCampos() {
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
