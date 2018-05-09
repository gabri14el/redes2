package model;

import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe que representa um host
 */
public class Jogador {

    InetAddress endereco;
    String nome;
    List<String> palavras;

    public Jogador(InetAddress endereco, String nome) {
        this.endereco = endereco;
        this.nome = nome;
        palavras = new LinkedList<String>();
    }

    public InetAddress getEndereco() {
        return endereco;
    }

    public String getNome() {
        return nome;
    }

    /**
     * Verifica se os jagores são iguais com base no nome
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Jogador)
            return ((Jogador)obj).nome.equals(this.endereco);
        else
            return false;
    }
}
