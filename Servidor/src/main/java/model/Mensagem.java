package model;

import conexao.EnviadorDeResposta;

public class Mensagem {

    EnviadorDeResposta enviadorDeResposta;
    String host;
    String msg;

    public Mensagem(EnviadorDeResposta enviadorDeResposta, String host, String nome) {
        this.enviadorDeResposta = enviadorDeResposta;
        this.host = host;
        this.msg = nome;
    }

    public String getHost() {
        return host;
    }

    public void enviarResposta(String str){
        enviadorDeResposta.enviaResposta(str);
    }

    public String getNome() {
        return msg;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Mensagem){
            return ((Mensagem)obj).host.equals(this.host);
        }
        else
            return false;
    }
}
