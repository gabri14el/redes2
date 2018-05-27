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
    int pontos;

    public Jogador(InetAddress endereco, String nome) {
        this.endereco = endereco;
        this.nome = nome;
        palavras = new LinkedList<String>();
        pontos = 0;
    }

    public InetAddress getEndereco() {
        return endereco;
    }

    public String getNome() {
        return nome;
    }
    
    public void addPalavra(String plv){
        palavras.add(plv);
    }

    public List<String> getPalavras() {
        return palavras;
    }
    
   public void calcularPontos(){
       for(String aux: palavras){
           pontos+=aux.length();
       }
   }

    public int getPontos() {
        return pontos;
    }
   
   
    /**
     * Verifica se os jagores são iguais com base no nome
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Jogador)
            return ((Jogador)obj).nome.equals(this.nome);
        else
            return false;
    }
}
