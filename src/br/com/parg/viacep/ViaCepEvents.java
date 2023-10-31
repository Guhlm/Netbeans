/*
 * PARG Desenvolvimento de Sistemas
 * Pablo Alexander - pablo@parg.com.br
 * 
 * Obtem um CEP no ViaCep
 */
package br.com.parg.viacep;

/**
 * Interface para os eventos
 *
 * @author Pablo Alexander da Rocha Gonçalves
 */
public interface ViaCepEvents {
    /**
     * Quando o CEP for encontrado com sucesso
     * @param cep retorna o objeto ViaCep
     */
    public void onCEPSuccess(ViaCep cep);
    
    /**
     * Quando ocorrer qualquer erro ao encontrar o CEP
     * @param cep retorna o CEP que foi requisitado
     */
    public void onCEPError(String cep);
}
