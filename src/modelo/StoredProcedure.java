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
public class StoredProcedure {
    private String nome;
    private List Parametros;
    private ExpressaoSQL query;

    public StoredProcedure() {
    }

    public String gerarStoredProcedure(){
        return null;
    }
    
    public String salvarStoredProcedure(){
        return null;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List getParametros() {
        return Parametros;
    }

    public void setParametros(List Parametros) {
        this.Parametros = Parametros;
    }

    public ExpressaoSQL getQuery() {
        return query;
    }

    public void setQuery(ExpressaoSQL query) {
        this.query = query;
    }
    
    
    
}
