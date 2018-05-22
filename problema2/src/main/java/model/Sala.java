package model;

import java.util.LinkedList;
import java.util.List;

public class Sala {
    int numeroParticipantes;
    int codigo;
    List<Jogador> jogadores;
    boolean eCoordenador;
    String dados[][];

    public Sala(int numeroParticipantes, int codigo, boolean eCoordenador) {
        this.numeroParticipantes = numeroParticipantes;
        this.codigo = codigo;
        jogadores = new LinkedList<Jogador>();
        this.eCoordenador=eCoordenador;
    }


    public void addJogador(Jogador j){
        jogadores.add(j);
    }

    //retorn lista com nome dos jogadores
    public List<String> nomeDosJogadores(){
        LinkedList<String> nomeJogadores = new LinkedList<String>();
        for(Jogador j: jogadores)
            nomeJogadores.add(j.getNome());
        return nomeJogadores;
    }

    public void setDados(String[][] dados) {
        this.dados = dados;
    }
}
