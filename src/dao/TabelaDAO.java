/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexao.Conection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Banco;
import modelo.Tabela;

/**
 *
 * @author Junnio
 */
public class TabelaDAO {

    private List<Tabela> tabelas;
    private Connection con;
    private PreparedStatement pstm;
    private ResultSet rs;
    private Tabela tabela;    
    
    /**
     *
     * @param banco
     * @return
     */
    public List<Tabela> listarTabelas(Banco banco) {
       
        tabelas = new ArrayList<>();
        con = null;
        pstm =null;
        rs = null;
                
        String sql = "SELECT DISTINCT TABLE_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS "
                + "WHERE TABLE_SCHEMA LIKE '"+banco.getNome()+"';";

        try {
            con = Conection.getConexao();
            pstm = con.prepareStatement(sql);
            //pstm.setString(1,"%"+ banco.getNome());
            rs = pstm.executeQuery(sql);
            while (rs.next()) {
                tabela = new Tabela();
                tabela.setNome(rs.getString("TABLE_NAME"));
                tabelas.add(tabela);
            }
        } catch (Exception e) {
            System.err.println("Erro encotrado   001T, causa:" + e.getMessage());
        }
        return tabelas;
    }

}
