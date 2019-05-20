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
public class StoredProcedure {

    private String nome;    
    private List<Parametro> parametrosSelecionados = new ArrayList<>();
    private ExpressaoSQL query;
    private String resultado;

    final String COMECO = "DELIMITER $$\n";
    final String TERMINO = "DELIMITER;";
    final String CRIA_PROCEDURE = "CREATE PROCEDURE ";
    final String INICIO = "BEGIN";
    final String FIM = "END $$";

    public StoredProcedure() {
    }

    public String storedProcedure(String query) {

        String parametro = "";

        if (parametrosSelecionados.isEmpty()) {
        } else {
            parametro = parametrosSelecionados.get(0).toString();
            for (int i = 1; i < parametrosSelecionados.size(); i++) {
                parametro = parametro + ", "+ parametrosSelecionados.get(i).toString();
            }
        }
        resultado = 
                COMECO + CRIA_PROCEDURE +nome+"( "+ parametro + " )\n" +
                INICIO + "\n" +
                query + "\n" +
                FIM + "\n" +
                TERMINO;
                
        return resultado;
    }

    public String salvarStoredProcedure() {
        return null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List getParametros() {
        return parametrosSelecionados;
    }

    public void setParametros(List parametros) {
        this.parametrosSelecionados = parametros;
    }

    public ExpressaoSQL getQuery() {
        return query;
    }

    public void setQuery(ExpressaoSQL query) {
        this.query = query;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    
}
