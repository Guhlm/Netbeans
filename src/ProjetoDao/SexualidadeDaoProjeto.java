/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProjetoDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Sexualidade;

/**
 *
 * @author xtremebass
 */
public class SexualidadeDaoProjeto {
    private ConexaoBancoProjeto conexao;
    
    public SexualidadeDaoProjeto()
    {
        this.conexao = new ConexaoBancoProjeto();
    }
    public ArrayList<Sexualidade> consultar() {
        
        ArrayList<Sexualidade> listaSexualidade = new ArrayList<Sexualidade>();
        String sql = "SELECT IdSexo, NomeSexo FROM CadSexo ORDER BY NomeSexo";
        
        try
        {
            if(this.conexao.Conectar())
            {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                
                //recebe o resultado da consulta
                ResultSet resultadoSentenca = sentenca.executeQuery();

                //percorrer cada linha do resultado
                while(resultadoSentenca.next()) 
                {
                    //resgata o valor de cada linha, selecionando pelo nome de cada coluna da tabela Escola
                    Sexualidade sexualidade = new Sexualidade();
                    sexualidade.setIdSexo(resultadoSentenca.getInt("IdSexo"));
                    sexualidade.setNomeSexo(resultadoSentenca.getString("NomeSexo"));

                    
                    listaSexualidade.add(sexualidade);
                }

                sentenca.close();
                this.conexao.getConnection().close();
            }
            
            return listaSexualidade;
        }
        catch(SQLException ex)
        {
           throw new RuntimeException(ex);
        }
    }
    
}
