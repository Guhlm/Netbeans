/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjetoDao;

import java.util.ArrayList;

public interface DaoGenericaProjeto<ObjetoGenerico> {
    
    public void inserir(ObjetoGenerico objt);
    
    public void alterar(ObjetoGenerico objt);
    
    public void excluir();
    
    public ArrayList<ObjetoGenerico> consultar();

    public ArrayList<ObjetoGenerico> dashboard();    
    
}
