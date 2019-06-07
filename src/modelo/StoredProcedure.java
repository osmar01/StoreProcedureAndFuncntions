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
    private List<Parametro> parametros = new ArrayList<>();
    private ExpressaoSQL query;
    private String procedure;

    final String COMECO = "DELIMITER $$\n";
    final String TERMINO = "DELIMITER $$";
    final String CRIA_PROCEDURE = "CREATE PROCEDURE ";
    final String INICIO = "BEGIN";
    final String FIM = "END $$";

    public StoredProcedure() {
    }

    public String storedProcedure(String query) {
        
        String parametro = "";
        tornarParametro();
        if (parametros.isEmpty()) {
        } else {
            parametro = parametros.get(0).toString();
            for (int i = 1; i < parametros.size(); i++) {
                parametro = parametro + ", "+ parametros.get(i).toString();
            }
        }
        procedure = 
                COMECO + CRIA_PROCEDURE +nome+"( "+ parametro + " )\n" +
                INICIO + "\n" +
                query + "\n" +
                FIM + "\n" +
                TERMINO;
                
        return procedure;
    }
    
    public void tornarParametro(){
        for (int i = 0; i < parametros.size(); i++) {            
            parametros.get(i).setNome("param_"+i);            
        }               
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
        return parametros;
    }

    public void setParametros(List parametros) {
        this.parametros = parametros;
    }

    public ExpressaoSQL getQuery() {
        return query;
    }

    public void setQuery(ExpressaoSQL query) {
        this.query = query;
    }

    public String getResultado() {
        return procedure;
    }

    public void setResultado(String resultado) {
        this.procedure = resultado;
    }
    
}
