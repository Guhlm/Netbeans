/*
 * PARG Desenvolvimento de Sistemas
 * Pablo Alexander - pablo@parg.com.br
 * 
 * Obtem um Cep no ViaCep
 */
package br.com.parg.viacep;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Classe java para obter um Cep no ViaCep
 *
 * @author Pablo Alexander da Rocha Gonçalves
 */
public class ViaCep extends ViaCepBase {

    // constantes
    public static final double VIACEP_VERSAO = 0.33;

    /**
     * Constrói uma nova classe
     */
    public ViaCep() {
        super();
    }

    /**
     * Constrói uma nova classe
     *
     * @param events eventos para a classe
     */
    public ViaCep(ViaCepEvents events) {
        super();
        this.Events = events;
    }

    /**
     * Constrói uma nova classe e busca um Cep no ViaCEP
     *
     * @param events eventos para a classe
     * @param cep
     * @throws br.com.parg.viacep.ViaCepException caso ocorra algum erro
     */
    public ViaCep(String cep, ViaCepEvents events) throws ViaCepException {
        super();
        this.Events = events;
        this.buscar(cep);
    }

    /**
     * Constrói uma nova classe e busca um Cep no ViaCEP
     *
     * @param cep
     * @throws br.com.parg.viacep.ViaCepException caso ocorra algum erro
     */
    public ViaCep(String cep) throws ViaCepException {
        super();
        this.buscar(cep);
    }

    /**
     * Busca um Cep no ViaCep
     *
     * @param cep
     * @throws br.com.parg.viacep.ViaCepException caso ocorra algum erro
     */
    @Override
    public final void buscar(String cep) throws ViaCepException {
        // define o cep atual
        currentCEP = cep;

        // define a url
        String url = "http://viacep.com.br/ws/" + cep + "/json/";

        // define os dados
        JSONObject obj = new JSONObject(getHttpGET(url));

        if (!obj.has("erro")) {
            Cep novoCEP = new Cep(obj.getString("cep"),
                    obj.getString("logradouro"),
                    obj.getString("complemento"),
                    obj.getString("bairro"),
                    obj.getString("localidade"),
                    obj.getString("uf"),
                    obj.getString("ibge"),
                    obj.getString("gia"));

            // insere o novo Cep
            CEPs.add(novoCEP);

            // atualiza o index
            index = CEPs.size() - 1;

            // verifica os Eventos
            if (Events instanceof ViaCepEvents) {
                Events.onCEPSuccess(this);
            }
        } else {
            // verifica os Eventos
            if (Events instanceof ViaCepEvents) {
                Events.onCEPError(currentCEP);
            }

            throw new ViaCepException("Não foi possível encontrar o CEP", cep, ViaCepException.class.getName());
        }
    }
    
    /**
     * Busca um Cep usando um endereço
     *
     * @param cep classe Cep com uf, localidade, logradouro
     * @throws ViaCepException
     */
    @Override
    public void buscarCEP(Cep cep) throws ViaCepException {
        buscarCEP(cep.Uf, cep.Localidade, cep.Logradouro);
    }

    /**
     * Busca um Cep usando um endereço
     *
     * @param Uf Estado
     * @param Localidade Municipio
     * @param Logradouro Rua, Avenidade, Viela...
     * @throws ViaCepException
     */
    @Override
    public void buscarCEP(String Uf, String Localidade, String Logradouro) throws ViaCepException {
        // define o cep atual
        currentCEP = "?????-???";

        // define a url
        String url = "http://viacep.com.br/ws/" + Uf.toUpperCase() + "/" + Localidade + "/" + Logradouro + "/json/";

        // obtem a lista de Cep's
        JSONArray ceps = new JSONArray(getHttpGET(url));

        if (ceps.length() > 0) {
            for (int i = 0; i < ceps.length(); i++) {
                JSONObject obj = ceps.getJSONObject(i);

                if (!obj.has("erro")) {
                    Cep novoCEP = new Cep(obj.getString("cep"),
                            obj.getString("logradouro"),
                            obj.getString("complemento"),
                            obj.getString("bairro"),
                            obj.getString("localidade"),
                            obj.getString("uf"),
                            obj.getString("ibge"),
                            obj.getString("gia"));

                    // insere o novo Cep
                    CEPs.add(novoCEP);

                    // atualiza o index
                    index = CEPs.size() - 1;

                    // verifica os Eventos
                    if (Events instanceof ViaCepEvents) {
                        Events.onCEPSuccess(this);
                    }
                } else {
                    // verifica os Eventos
                    if (Events instanceof ViaCepEvents) {
                        Events.onCEPError(currentCEP);
                    }

                    throw new ViaCepException("Não foi possível validar o CEP", currentCEP, ViaCepException.class.getName());
                }
            }
        } else {
            throw new ViaCepException("Nenhum CEP encontrado", currentCEP, getClass().getName());
        }
    }
}
