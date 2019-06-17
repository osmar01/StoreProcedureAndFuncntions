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
public class Funcao {
    private String nome;
    private String tipo;
    private List parametros;
    private ExpressaoSQL query;
    
    final String COMECO = "DELIMITER $$\n";
    final String TERMINO = "DELIMITER $$";
    final String CRIA_FUNCTION = "CREATE FUNCTION ";
    final String RETORNO = "RETURNS";
    final String INICIO = "BEGIN";
    final String RETURNN = "RETURN";
    final String RETURNS = "RETURNS";
    final String FIM = "END $$";

    public Funcao() {
    }
    
    public String gerarFunction() {
        String function = "";
        String parametro = "";
        tornarValorParametro(query.getFiltrosSelecionados());
        tornarParametro(query.getFiltrosSelecionados());
        if (parametros.isEmpty()) {
        } else {
            parametro = parametros.get(0).toString();
            for (int i = 1; i < parametros.size(); i++) {
                parametro = parametro + ", " + parametros.get(i).toString();
            }
        }
        function = COMECO + CRIA_FUNCTION + nome + "( " + parametro + " ) "
                + RETORNO +"\n"
                + INICIO + "\n" 
                + RETURNN
                +"("+ query.getQuery() +")"+ "\n"
                + FIM + "\n"
                + TERMINO;
        return function;
    }
    
    public void tornarValorParametro(List<Campo> campos) {
        List<Campo> parametros = new ArrayList<>();
        for (int i = 0; i < campos.size(); i++) {
            Campo p = new Campo();
            p.setNome(campos.get(i).getNome());
            p.setTipo(campos.get(i).getTipo());
            p.setOperador(campos.get(i).getOperador());
            p.setFiltro(campos.get(i).getFiltro());
            parametros.add(p);
        }
        for (int i = 0; i < parametros.size(); i++) {
            parametros.get(i).setValor("param_" + i);
        }
        query.setFiltrosSelecionados(parametros);

    }

    public void tornarParametro(List<Campo> campos) {
        parametros = new ArrayList<>();
        for (int i = 0; i < campos.size(); i++) {
            Parametro p = new Parametro();
            p.setNome("param_" + i);
            p.setTipo(campos.get(i).getTipo());

            parametros.add(p);
        }
    }
    
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ExpressaoSQL getQuery() {
        return query;
    }

    public void setQuery(ExpressaoSQL query) {
        this.query = query;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List getParametros() {
        return parametros;
    }

    public void setParametros(List parametros) {
        this.parametros = parametros;
    }

    
    
}
