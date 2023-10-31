package ProjetoDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConexaoBancoProjeto {   
    private String servidor;
    private String banco;
    private String usuario;
    private String senha;
    private Connection conexao;   
    public ConexaoBancoProjeto() {
        this.servidor = "200.195.178.52";
        this.banco = "grupo3_Gustavo";
        this.usuario = "grupo3";
        this.senha = "xpJHCEEGbU4mSys2";
    }
    public boolean Conectar(){
        try
        {
            this.conexao = DriverManager.getConnection("jdbc:mysql://"+this.servidor+"/"+this.banco,this.usuario,this.senha);
            return true;
        }
        catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    public Connection getConnection() {
        return conexao;
    }

}
