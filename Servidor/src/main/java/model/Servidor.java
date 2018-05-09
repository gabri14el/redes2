package model;

import conexao.Conexao;
import conexao.EnviadorDeResposta;
import conexao.ProcessadorDeConexoes;

public class Servidor implements ProcessadorDeConexoes{

    public static Servidor instancia = null;
    Conexao conexao;
    GeradorDeConexoes geradorDeConexoes;

    //======================================
    //                 MAIN

    public static void main(String[] args){
        new Servidor();
    }

    //======================================




    private Servidor(){
        //iniciando a thread que faz conexao com os clientes
        conexao = new Conexao(this);
        new Thread(conexao).start();

        //iniciando a thread que processa as conexoes com os clientes
        geradorDeConexoes = new GeradorDeConexoes();
        new Thread(geradorDeConexoes).start();
    }

    /**
     * implementação do singleton
     * @return instancia
     */
    public static Servidor getInstancia(){
        if(instancia == null)
            instancia = new Servidor();
        return instancia;
    }


    public synchronized void processaConexao(String str, EnviadorDeResposta enviadorDeResposta, String nomeJogador) {
        geradorDeConexoes.addMensagem(new Mensagem(enviadorDeResposta, str, nomeJogador));
    }

    public void erroDuranteConexao(String cliente, EnviadorDeResposta enviadorDeResposta, String nomeJogador) {
        System.out.println("processando erro..");
        geradorDeConexoes.rmMensagem(new Mensagem(enviadorDeResposta, cliente, nomeJogador));
    }
}
