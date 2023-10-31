/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjetoDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import modelo.Cadastro;


public class CadastroDaoProjeto implements DaoGenericaProjeto<Cadastro>{

    private ConexaoBancoProjeto conexao;
    
    public CadastroDaoProjeto()
    {
        this.conexao = new ConexaoBancoProjeto();
    }
    
    @Override
    public void inserir(Cadastro cadastro) {
        //string com a consulta que será executada no banco
        String sql = "INSERT INTO CadastroProjeto (NomeCad, Cpf, IdSexo, Email) VALUES (?,?,(select IdSexo from CadSexo where NomeSexo = ?),?)";
        
        try
        {
            //tenta realizar a conexão, se retornar verdadeiro entra no IF
            if(this.conexao.Conectar())
            {
                //prepara a sentença com a consulta da string
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                
                //subtitui as interrograções da consulta, pelo valor específico
                sentenca.setString(1,cadastro.getNomeCad()); //subsitui a primeira ocorrência da interrogação pelo atributo nome
                sentenca.setString(2,cadastro.getCpf());
                sentenca.setString(3,cadastro.getNomeSexo()); 
                sentenca.setString(4,cadastro.getEmail());
                
                sentenca.execute(); //executa o comando no banco
                sentenca.close(); //fecha a sentença
                this.conexao.getConnection().close(); //fecha a conexão com o banco
            }
        }
        catch(SQLException ex)
        {
           throw new RuntimeException(ex);
        }
    }

    @Override
    public void alterar(Cadastro cadastro) {
        String sql = "UPDATE CadastroProjeto SET NomeCad = ?, Cpf = ?, IdSexo = (select IdSexo from CadSexo where NomeSexo = ?), Email = ? where IdCad = ?";
        
        try
        {
            if(this.conexao.Conectar())
            {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                
                sentenca.setString(1,cadastro.getNomeCad());
                sentenca.setString(2,cadastro.getCpf());
                sentenca.setString(3,cadastro.getNomeSexo());
                sentenca.setString(4,cadastro.getEmail());
                sentenca.setInt(5, cadastro.getIdCad());
                sentenca.execute();
                sentenca.close();
                this.conexao.getConnection().close();
            }
        }
        catch(SQLException ex)
        {
           throw new RuntimeException(ex);
        }
    }

    @Override
    public void excluir() {
        String sql = "DELETE FROM ESCOLA";
        
        try
        {
            if(this.conexao.Conectar())
            {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
   
                sentenca.execute();
                sentenca.close();
                this.conexao.getConnection().close();
            }
        }
        catch(SQLException ex)
        {
           throw new RuntimeException(ex);
        }
    }
    
    public void excluirID(int id) {
        String sql = "DELETE FROM CadastroProjeto WHERE IdCad = ?";
        
        try
        {
            if(this.conexao.Conectar())
            {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                
                sentenca.setInt(1, id);
                
                sentenca.execute();
                sentenca.close();
                this.conexao.getConnection().close();
            }
        }
        catch(SQLException ex)
        {
           throw new RuntimeException(ex);
        }
    }
    

    @Override
    public ArrayList<Cadastro> consultar() {
        
        ArrayList<Cadastro> listaCadastros = new ArrayList<Cadastro>();
        String sql = "SELECT c.IdCad, c.NomeCad, c.Cpf, c.Email, s.NomeSexo "+
                     "FROM CadastroProjeto as c "+
                     "LEFT JOIN CadSexo AS s ON (s.idsexo = c.IdSexo) "+  
                     "ORDER BY c.IdCad ";
        
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
                    Cadastro cadastro = new Cadastro();
                    cadastro.setIdCad(resultadoSentenca.getInt("IdCad"));
                    cadastro.setNomeCad(resultadoSentenca.getString("NomeCad"));
                    cadastro.setCpf(resultadoSentenca.getString("Cpf"));
                    cadastro.SetNomeSexo(resultadoSentenca.getString("NomeSexo"));
                    cadastro.setEmail(resultadoSentenca.getString("Email"));
                    
                    listaCadastros.add(cadastro);
                }

                sentenca.close();
                this.conexao.getConnection().close();
            }
            
            return listaCadastros;
        }
        catch(SQLException ex)
        {
           throw new RuntimeException(ex);
        }
    }
    public ArrayList<Cadastro> consultar(String str) {
        
        ArrayList<Cadastro> listaCadastrosStr = new ArrayList<Cadastro>();
        String sql = "SELECT c.IdCad, c.NomeCad, c.Cpf, c.Email, s.NomeSexo "+
                     "FROM CadastroProjeto as c "+
                     "LEFT JOIN CadSexo AS s ON (s.Idsexo = c.IdSexo) "+
                     "WHERE ( UPPER(c.NomeCad like UPPER(?))) "+   
                     "ORDER BY s.NomeSexo ";
        
        try
        {
            if(this.conexao.Conectar())
            {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                
                //recebe o resultado da consulta
                sentenca.setString(1, "%"+str+"%");
                ResultSet resultadoSentenca = sentenca.executeQuery();

                //percorrer cada linha do resultado
                while(resultadoSentenca.next()) 
                {
                    //resgata o valor de cada linha, selecionando pelo nome de cada coluna da tabela Escola
                    Cadastro cadastro = new Cadastro();
                    cadastro.setIdCad(resultadoSentenca.getInt("IdCad"));
                    cadastro.setNomeCad(resultadoSentenca.getString("NomeCad"));
                    cadastro.setCpf(resultadoSentenca.getString("Cpf"));
                    cadastro.SetNomeSexo(resultadoSentenca.getString("NomeSexo"));
                    cadastro.setEmail(resultadoSentenca.getString("Email"));
                    
                    listaCadastrosStr.add(cadastro);
                }

                sentenca.close();
                this.conexao.getConnection().close();
            }
            
            return listaCadastrosStr;
        }
        catch(SQLException ex)
        {
           throw new RuntimeException(ex);
        }
    }
    public ArrayList<Cadastro> dashboard() {
        
        ArrayList<Cadastro> ListarDashBoard = new ArrayList<Cadastro>();
        String sql = "select count(IdCad) as NumCad, count(IdCad)*2 as SumCad, (select count(IdSexo)+100 from CadSexo) as NumSexualidade from CadastroProjeto;";
        //String sql = "select FLOOR(RAND()*(10-5+1)*10) as NumCad, FLOOR(RAND()*(10-5+1)*10) as SumCad, FLOOR(RAND()*(10-5+1)*10) as NumSexualidade";
        
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
                    Cadastro cadastro = new Cadastro();
                    cadastro.setTotalCadastros(resultadoSentenca.getInt("NumCad"));
                    cadastro.SetSomaCodigos(resultadoSentenca.getInt("SumCad"));
                    cadastro.SetNumSexualidade(resultadoSentenca.getInt("NumSexualidade"));
                    
                    ListarDashBoard.add(cadastro);
                }

                sentenca.close();
                this.conexao.getConnection().close();
            }
            
            return ListarDashBoard;
        }
        catch(SQLException ex)
        {
           throw new RuntimeException(ex);
        }
    }
    
}
