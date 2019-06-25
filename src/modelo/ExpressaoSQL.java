/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Junnio
 */
public class ExpressaoSQL {

    private String tipo;
    private String tabelaSelecionada;
    private List<Campo> campos;
    private List<Campo> camposSelecionados;
    private List<Campo> filtrosSelecionados;
    private List<Tabela> tabelasRelacionadas;
    private List<Campo> camposOrdenadosPor;
    private List<String> camposAgrupados;
    private List<String> agrupadoresSelecionados;

    public String getQuery() {
        String campo = "";
        String filtro = "";
        String rel = "";
        String ordenador = "";
        String pontoVirgula = ";";
        String query = "";
        String agrupador = "";
        String camposAgrupado = "";

        if (camposSelecionados.isEmpty()) {
             if (!agrupadoresSelecionados.isEmpty()) {
                campo = "";
             }else{
                campo = " * ";
             }
        } else {
            campo = camposSelecionados.get(0).toString();
            for (int i = 1; i < camposSelecionados.size(); i++) {
                campo = campo + ", " + camposSelecionados.get(i).toString();
            }
        }
        if (!filtrosSelecionados.isEmpty()) {
            filtro = "\nWHERE ";
            for (int i = 0; i < filtrosSelecionados.size(); i++) {
                if (filtrosSelecionados.get(i).getOperador().equals("Selecione")) {
                    filtrosSelecionados.get(i).setOperador("");
                }
                filtro = filtro + filtrosSelecionados.get(i).getNome() + " "
                        + filtrosSelecionados.get(i).getFiltro() + " "
                        + filtrosSelecionados.get(i).getValor() + " "
                        + filtrosSelecionados.get(i).getOperador() + " ";
            }
        }

        if (!tabelasRelacionadas.isEmpty()) {
            rel = rel + tabelasRelacionadas.get(0).getNome() + "\nINNER JOIN "
                    + tabelasRelacionadas.get(0).getNomeReferenciada() + " ON "
                    + tabelasRelacionadas.get(0).getNomeColuna() + " = "
                    + tabelasRelacionadas.get(0).getNomeColunaReferenciada();
            for (int i = 1; i < tabelasRelacionadas.size(); i++) {
                if (tabelasRelacionadas.get(i).getNome() != " "
                        || tabelasRelacionadas.get(i).getNomeReferenciada() != " ") {
                    rel = rel + "\nINNER JOIN " + tabelasRelacionadas.get(i).getNome()
                            + tabelasRelacionadas.get(i).getNomeReferenciada() + " ON "
                            + tabelasRelacionadas.get(i).getNomeColuna() + " = "
                            + tabelasRelacionadas.get(i).getNomeColunaReferenciada();
                }
            }
        } else if (tabelaSelecionada != null) {
            rel = tabelaSelecionada;
        }

        if (!camposOrdenadosPor.isEmpty()) {
            ordenador = "\nORDER BY " + camposOrdenadosPor.get(0).getNome()
                    + " " + camposOrdenadosPor.get(0).getOrdenador();
            for (int i = 1; i < camposOrdenadosPor.size(); i++) {
                ordenador = ordenador + ", " + camposOrdenadosPor.get(i).getNome()
                        + " " + camposOrdenadosPor.get(i).getOrdenador();
            }
        } else {
            ordenador = ";";
            pontoVirgula = "";
        }

        if (!agrupadoresSelecionados.isEmpty()) {
            if (camposSelecionados.isEmpty()) {
                agrupador = agrupadoresSelecionados.get(0);
            } else {
                agrupador = ", " + agrupadoresSelecionados.get(0);
            }
            for (int i = 1; i < agrupadoresSelecionados.size(); i++) {
                agrupador = ", "+agrupador + agrupadoresSelecionados.get(i);
            }
        }

        if (!camposAgrupados.isEmpty()) {
            camposAgrupado = "\nGROUP BY " + camposAgrupados.get(0);
            for (int i = 1; i < camposAgrupados.size(); i++) {
                camposAgrupado = ", " + camposAgrupado + camposAgrupados.get(i);
            }
        }

        return query = "SELECT " + campo + agrupador + "\nFROM " + rel + filtro
                + camposAgrupado + ordenador + pontoVirgula;
    }

    public void salvarConsulta() {

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Campo> getCampos() {
        return campos;
    }

    public void setCampos(List<Campo> campos) {
        this.campos = campos;
    }

    public String getTabelaSelecionada() {
        return tabelaSelecionada;
    }

    public void setTabelaSelecionada(String tabelaSelecionada) {
        this.tabelaSelecionada = tabelaSelecionada;
    }

    public List<Campo> getCamposSelecionados() {
        return camposSelecionados;
    }

    public void setCamposSelecionados(List<Campo> camposSelecionados) {
        this.camposSelecionados = camposSelecionados;
    }

    public List<Campo> getFiltrosSelecionados() {
        return filtrosSelecionados;
    }

    public void setFiltrosSelecionados(List<Campo> filtrosSelecionados) {
        this.filtrosSelecionados = filtrosSelecionados;
    }

    public List<Tabela> getTabelasRelacionadas() {
        return tabelasRelacionadas;
    }

    public void setTabelasRelacionadas(List<Tabela> tabelasRelacionadas) {
        this.tabelasRelacionadas = tabelasRelacionadas;
    }

    public List<Campo> getCamposOrdenadosPor() {
        return camposOrdenadosPor;
    }

    public void setCamposOrdenadosPor(List<Campo> camposOrdenadosPor) {
        this.camposOrdenadosPor = camposOrdenadosPor;
    }

    public List<String> getCamposAgrupados() {
        return camposAgrupados;
    }

    public void setCamposAgrupados(List<String> camposAgrupados) {
        this.camposAgrupados = camposAgrupados;
    }

    public List<String> getAgrupadoresSelecionados() {
        return agrupadoresSelecionados;
    }

    public void setAgrupadoresSelecionados(List<String> agrupadoresSelecionados) {
        this.agrupadoresSelecionados = agrupadoresSelecionados;
    }

}
