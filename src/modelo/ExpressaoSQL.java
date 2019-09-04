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
    private List<Campo> camposInsert;
    private List<Campo> camposDelete;
    private List<Campo> camposUpdates;
    private List<Campo> camposUpdatesCondicao;
    private List<Campo> camposInsertSelecionados;

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
            } else {
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
                agrupador = ", " + agrupador + agrupadoresSelecionados.get(i);
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

    public String getQueryExecute(String banco) {
        String campo = "";
        String filtro = "";
        String rel = "";
        String ordenador = "";
        String pontoVirgula = ";";
        String queryExecute = "";
        String agrupador = "";
        String camposAgrupado = "";

        if (camposSelecionados.isEmpty()) {
            if (!agrupadoresSelecionados.isEmpty()) {
                campo = "";
            } else {
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
            rel = rel + banco + "." + tabelasRelacionadas.get(0).getNome() + "\nINNER JOIN "
                    + banco + "." + tabelasRelacionadas.get(0).getNomeReferenciada() + " ON "
                    + tabelasRelacionadas.get(0).getNome() + "." + tabelasRelacionadas.get(0).getNomeColuna() + " = "
                    + tabelasRelacionadas.get(0).getNomeReferenciada() + "." + tabelasRelacionadas.get(0).getNomeColunaReferenciada();
            for (int i = 1; i < tabelasRelacionadas.size(); i++) {
                if (tabelasRelacionadas.get(i).getNome() != " "
                        || tabelasRelacionadas.get(i).getNomeReferenciada() != " ") {
                    rel = rel + "\nINNER JOIN " + banco + "." + tabelasRelacionadas.get(i).getNome()
                            + tabelasRelacionadas.get(i).getNome() + "." + tabelasRelacionadas.get(i).getNomeReferenciada() + " ON "
                            + tabelasRelacionadas.get(i).getNome() + "." + tabelasRelacionadas.get(i).getNomeColuna() + " = "
                            + tabelasRelacionadas.get(i).getNome() + "." + tabelasRelacionadas.get(i).getNomeColunaReferenciada();
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
                agrupador = ", " + agrupador + agrupadoresSelecionados.get(i);
            }
        }

        if (!camposAgrupados.isEmpty()) {
            camposAgrupado = "\nGROUP BY " + camposAgrupados.get(0);
            for (int i = 1; i < camposAgrupados.size(); i++) {
                camposAgrupado = ", " + camposAgrupado + camposAgrupados.get(i);
            }
        }

        return queryExecute = "SELECT " + campo + agrupador + "\nFROM " + rel + filtro
                + camposAgrupado + ordenador + pontoVirgula;
    }

    public String getQueryInsert() {
        String camposInserir = "";
        String valoresInserir = "";
        String queryInsert;
        if (!camposInsert.isEmpty()) {
            camposInserir = camposInsert.get(0).getNome();
            for (int i = 1; i < camposInsert.size(); i++) {
                camposInserir = camposInserir + ", " + camposInsert.get(i).getNome();
            }
            valoresInserir = camposInsert.get(0).getValor();
            for (int i = 1; i < camposInsert.size(); i++) {
                valoresInserir = valoresInserir + ", " + camposInsert.get(i).getValor();
            }
        }
        return queryInsert = "INSERT INTO " + getTabelaSelecionada()
                + " (" + camposInserir + ")" + "\nVALUES (" + valoresInserir + ");";

    }

    public String getQueryDelete() {
        String camposDel = "";
        String queryDelete;
        if (!camposDelete.isEmpty()) {
            camposDel = camposDelete.get(0).getNome() + " = '" + camposDelete.get(0).getValor() + "'";
            for (int i = 1; i < camposDelete.size(); i++) {
                camposDel = camposDel + ", " + camposDelete.get(i).getNome() + " = '" + camposDelete.get(0).getValor() + "'";
            }
        }
        return queryDelete = "DELETE FROM " + getTabelaSelecionada()
                + " WHERE " + camposDel;
    }

    public String getQueryUpdate() {
        String camposUp = "";
        String camposUpdateCondicao = "";
        String queryUp;
        if (!camposUpdates.isEmpty()) {
            camposUp = camposUpdates.get(0).getNome() + " = '" + camposUpdates.get(0).getValor() + "'";
            for (int i = 1; i < camposUpdates.size(); i++) {
                camposUp = camposUp + ", " + camposUpdates.get(i).getNome() + " = '" + camposUpdates.get(0).getValor() + "'";
            }
            camposUpdateCondicao = camposUpdatesCondicao.get(0).getNome() + " = '" + camposUpdatesCondicao.get(0).getValor() + "'";
            for (int i = 1; i < camposUpdatesCondicao.size(); i++) {
                camposUpdateCondicao = camposUpdateCondicao + ", " + camposUpdatesCondicao.get(i).getNome() + " = '" + camposUpdatesCondicao.get(0).getValor() + "'";
            }
        }
        return queryUp = "UPDATE " + getTabelaSelecionada() + "\nSET "
                + camposUp + "\nWHERE " + camposUpdateCondicao + ";";
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

    public List<Campo> getCamposInsertSelecionados() {
        return camposInsertSelecionados;
    }

    public void setCamposInsertSelecionados(List<Campo> camposInsertSelecionados) {
        this.camposInsert = camposInsertSelecionados;

    }

    public void setCamposDeleteSelecionados(List<Campo> camposDeleteSelecionados) {
        this.camposDelete = camposDeleteSelecionados;

    }

    public void setCamposUpdateSelecionados(List<Campo> camposUpdateSelecionados) {
        this.camposUpdates = camposUpdateSelecionados;
    }

    public void setCamposCondicaoUpdateSelecionados(List<Campo> camposCondicaoUpdateSelecionados) {
        this.camposUpdatesCondicao = camposCondicaoUpdateSelecionados;

    }

}
