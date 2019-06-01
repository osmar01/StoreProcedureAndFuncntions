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
import modelo.Campo;

/**
 *
 * @author Junnio
 */
public class CampoDAO {

    private Connection con;
    private PreparedStatement pstm;
    private ResultSet rs;

    private List<Campo> campos;

    public CampoDAO() {
        con = Conection.getConexao();
    }

    public List<Campo> listarCampos(String banco, String tabela) {
        campos = new ArrayList<>();

        String sql = "SELECT DISTINCT COLUMN_NAME, COLUMN_TYPE "
                + "FROM INFORMATION_SCHEMA.COLUMNS "
                + "WHERE TABLE_SCHEMA = '" + banco + "' AND TABLE_NAME LIKE '%" + tabela + "'; ";

        try {

            pstm = con.prepareStatement(sql);
//            pstm.setString(1, banco);
//            pstm.setString(2, "%" +tabela);
            rs = pstm.executeQuery(sql);

            Campo campoSelectIndex;
            campoSelectIndex = new Campo();
            campoSelectIndex.setNome("Selecione");
            campos.add(campoSelectIndex);

            while (rs.next()) {
                Campo campo = new Campo();
                campo.setNome(rs.getString("COLUMN_NAME"));
                campo.setTipo(rs.getString("COLUMN_TYPE"));
                campos.add(campo);
            }
        } catch (Exception e) {
            System.err.println("Erro encotrado   003 Campo_SQL, causa:" + e.getMessage());
        }
        return campos;

    }

}
