/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjetoDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;


public class UsuarioDaoProjeto {
    private ConexaoBancoProjeto conexao;
    
    public UsuarioDaoProjeto()
    {
        this.conexao = new ConexaoBancoProjeto();
    }
    public boolean LoginUsuario(String Usuario, String Senha) {
        String sql = "SELECT * FROM Usuarios WHERE NomeUsuario=? and Senha=?";
        boolean checkUser = false;
        try
        {
            if(conexao.Conectar())
            {
                PreparedStatement sentenca = conexao.getConnection().prepareStatement(sql);
                sentenca.setString(1,Usuario);
                sentenca.setString(2, Senha);
                ResultSet rs = sentenca.executeQuery();
                if(rs.next()){
                    JOptionPane.showMessageDialog(null, "Usuário válido");
                    checkUser = true;
                }else{
                    JOptionPane.showMessageDialog(null, "Dados Inválidos ");
                    checkUser = false;
                }
                sentenca.close();
                this.conexao.getConnection().close();
            }
        }
        catch(SQLException ex)
        {
           throw new RuntimeException(ex);
        }
        return checkUser;
    }
    
}
