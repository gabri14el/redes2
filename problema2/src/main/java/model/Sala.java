package model;

import java.util.LinkedList;
import java.util.List;

public class Sala {
    int numeroParticipantes;
    int codigo;
    List<Jogador> jogadores;

    public Sala(int numeroParticipantes, int codigo) {
        this.numeroParticipantes = numeroParticipantes;
        this.codigo = codigo;
        jogadores = new LinkedList<Jogador>();
    }
}
