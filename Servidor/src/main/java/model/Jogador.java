package model;

public class Jogador {

    String nome;
    String host;
    String senha;

    public Jogador(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    public Jogador(String nome, String senha, String host) {
        this.nome = nome;
        this.host = host;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha(){
        return senha;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return nome+";"+host;
    }
}
