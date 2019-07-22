/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 *
 * @author Junnio
 */
public class Conection {

    private static String host;
    private static String porta ;
    private static String usuario ;
    private static String senha;

    private static Connection con = null;
    
    

    public static void Conectar() {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            setCon(DriverManager.getConnection("jdbc:mysql://" + getHost() + ":" + getPorta(), getUsuario(), getSenha()));
            System.out.println("Conectado.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Classe não encontrada, adicione o driver");
            Logger.getLogger(Conection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            msgError();
            System.out.println("erro: ---------"+e.getMessage());
            throw new RuntimeException(e);
        }

    }
    
    public static Connection getConexao() {     
        Conectar();                 
        return con;
    }
    
    
    public static boolean verificaConexao(){
            return con!=null;
    }

    public static void msgError(){
        Alert msg = new Alert(AlertType.ERROR);
        msg.setTitle("Informação da conexão");
        msg.setHeaderText("Erro de conexão");
        msg.setContentText("Não foi possivel conectar com MySQL.");
        msg.showAndWait();
    }
    
    /**
     * @return the host
     */
    public static String getHost() {
        return host;
    }

    /**
     * @param aHost the host to set
     */
    public static void setHost(String aHost) {
        host = aHost;
    }

    /**
     * @return the porta
     */
    public static String getPorta() {
        return porta;
    }

    /**
     * @param aPorta the porta to set
     */
    public static void setPorta(String aPorta) {
        porta = aPorta;
    }

    /**
     * @return the usuario
     */
    public static String getUsuario() {
        return usuario;
    }

    /**
     * @param aUsuario the usuario to set
     */
    public static void setUsuario(String aUsuario) {
        usuario = aUsuario;
    }

    /**
     * @return the senha
     */
    public static String getSenha() {
        return senha;
    }

    /**
     * @param aSenha the senha to set
     */
    public static void setSenha(String aSenha) {
        senha = aSenha;
    }

    /**
     * @return the con
     */
    public static Connection getCon() {
        return con;
    }

    /**
     * @param aCon the con to set
     */
    public static void setCon(Connection aCon) {
        con = aCon;
    }

}
