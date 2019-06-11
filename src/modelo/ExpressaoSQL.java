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
public class ExpressaoSQL {
    private String tipo;
    private String resultado;
    private List relacionamento;
    private List<Campo> campos;
    
    public String gerarExpressaoSQL(Tabela tabela, List<Campo> campos){
        
        return null;
    }
    public void salvarConsulta(){
        
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List getRelacionamento() {
        return relacionamento;
    }

    public void setRelacionamento(List relacionamento) {
        this.relacionamento = relacionamento;
    }

    public List<Campo> getCampos() {
        return campos;
    }

    public void setCampos(List<Campo> campos) {
        this.campos = campos;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }           
}
