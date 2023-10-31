/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author xtremebass
 */
public class Sexualidade {
    private int idsexo;
    private String nomesexo;
    

    public Sexualidade() {
    }
    
    public Sexualidade(String nomesexo) {
        this.nomesexo = nomesexo;
    }

 public Sexualidade(int idsexo, String nomesexo) {
        this.idsexo = idsexo;     
        this.nomesexo = nomesexo;
    }   
    public int getIdSexo() {
        return idsexo;
    }

    public void setIdSexo(int idsexo) {
        this.idsexo = idsexo;
    }

    public String getNomeSexo() {
        return nomesexo;
    }

    public void setNomeSexo(String nomesexo) {
        this.nomesexo = nomesexo;
    }
    
}
