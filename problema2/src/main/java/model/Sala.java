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

    public int getNumeroParticipantes() {
        return numeroParticipantes;
    }
    
   
    public int getCodigo() {
        return codigo;
    }
    
    public String nomeDoCoordenador(){
        return jogadores.get(0).getNome();
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

    @Override
    public String toString() {
        return codigo+"\t("+numeroParticipantes+")";
    }
    
    //retorna lista com as Strings contidas nos dados da sala
    public List<String> getListDados(){
        List<String> letras = new LinkedList<String>();
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                letras.add(dados[i][j]);
            }
        }
        
        return letras;
    }
    
    //método que verifica se uma string está nos dados
    public boolean verificaSeStringEstaNosDados(String s){
        List<String> letras = this.getListDados();
        s = s.toUpperCase();
        char[] vetor = s.toCharArray();
        String aux;
        for(int i=0; i< vetor.length; i++){
            if(vetor[i] == 'Q' && vetor[i+1] == 'U'){
                i++;
                if(!letras.remove("Qu")) return false;
                
            }else if(vetor[i] == 'Q' && vetor[i+1] != 'U'){
                return false;
            }
            else{
                aux = vetor[i]+"";
                if(!letras.remove(aux)) 
                    return false;
            }
        }
        return true;
    }
}
