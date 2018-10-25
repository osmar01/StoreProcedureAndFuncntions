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

/**
 *
 * @author Junnio
 */
public class BancoDAO {

    private List<Banco> bancos;
    private Connection con;
    private PreparedStatement pstm;
    private ResultSet rs;
    private Banco banco;

    public List<Banco> listarBancos() {

        bancos = new ArrayList<>();

        String sql = "SELECT DISTINCT CONSTRAINT_SCHEMA FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS;";

        try {
            con = Conection.getConexao();
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery(sql);
            while (rs.next()) {
                banco = new Banco();
                banco.setNome(rs.getString("CONSTRAINT_SCHEMA"));
                bancos.add(banco);
            }
        } catch (Exception e) {
            System.err.println("Erro encontrado  001B, causa:" + e.getMessage());
        }
        return bancos;
    }

    public static void main(String[] args) {

    }
}
