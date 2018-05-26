package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Sala {
    List<Jogador> jogadores;
    Jogador coordenador;
    int id;
    String[][] dados;
    String canal;
    HashMap<String, Jogador> jogadores_;

    public Sala(Jogador coordenador, int id) {
        jogadores = new LinkedList<Jogador>();
        this.coordenador = coordenador;
        this.id = id;
        jogadores.add(0, coordenador);
        sorteiaDados();
        this.canal = coordenador.getNome()+id;
    }

    public void addPalavra(String nome_jogador, String plv){
        
    }
    public void addJogador(Jogador jogador){
        if(!jogadores.contains(jogador)){
             jogadores.add(jogadores.size(),jogador);
             
        }
           
    }

    public void rmJogador(Jogador jogador){
        jogadores.remove(jogador);
    }
    public int quantidadeDeJogadores(){
        return jogadores.size();
    }

    public boolean eCoordenador(Jogador jogador){
        return jogador.equals(coordenador);
    }

    public String informacoesBasicasFormatadas(){
        return id+";"+jogadores.size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(id+";");
        builder.append(jogadores.size()+";");

        for(int i = 0; i < jogadores.size(); i++){
            builder.append(jogadores.get(i).toString()+";");
        }


        for(int i=0; i < dados.length; i++){
            for(int k=0; k < dados[0].length; k++){
                builder.append(dados[i][k]+";");
            }
        }
        builder.append(canal);
        return builder.toString();
    }

    public void sorteiaDados(){
        Random random = new Random();
        String[][] dados = {{"R", "I", "F", "O", "B", "X"},
                {"I", "F", "E", "H", "E", "Y"},
                {"D", "E", "N", "O", "W", "S"},
                {"U", "T", "O", "K", "N", "D"},
                {"H", "M", "S", "R", "A", "O"},
                {"L", "U", "P", "E", "T", "S"},
                {"A", "C", "I", "T", "O", "A"},
                {"Y", "L", "G", "K", "U", "E"},
                {"Qu", "B", "M", "J", "O", "A"},
                {"E", "H", "I", "S", "P", "N"},
                {"V", "E", "T", "I", "G", "N"},
                {"B", "A", "L", "I", "Y", "T"},
                {"E", "Z", "A", "V", "N", "D"},
                {"R", "A", "L", "E", "S", "C"},
                {"U", "W", "I", "L", "R", "G"},
                {"P", "A", "C", "E", "M", "D"}};

        String sorteados[][]= new String[4][4];
        int l = 0;
        for(int i=0; i < sorteados.length; i++){
            for(int k=0; k < sorteados[0].length; k++){
                sorteados[i][k] = dados[l][random.nextInt(5)];
                l++;
            }
        }
        this.dados = sorteados;
    }

    void removeJogador(Jogador get) {
        jogadores.remove(get);
    }

}
