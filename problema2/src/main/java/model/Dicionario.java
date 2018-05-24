package model;

import java.io.*;
import java.util.HashMap;
import org.jgroups.util.BoundedHashMap;

public class Dicionario {

    public static final String path = "dicionario.dic";
    HashMap<String, Integer> palavras; //mapa usado no indexamento
    HashMap<Integer, String> palavras_; //mapa usado no desindexamento

    public Dicionario() {
        palavras = new HashMap<String, Integer>();
        palavras_=new HashMap<Integer, String>();
        leDiocionario();
    }


    //le dicionario da arquivo
    private void leDiocionario() {
        File file = new File(path);
        if(file.exists()){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                for(int i = 0; reader.ready(); i++){
                    String aux = reader.readLine();
                    palavras.put(aux, i);
                    palavras_.put(i, aux);
                }
            } catch (IOException e) {
                System.err.println("houve um erro com o dicionario...");
            }
        }
        else System.out.println("houve um erro ao carregar o dicionario...");
    }

    //verifica se palavra existe
    public boolean existe(String plv){
        if (palavras.get(plv) != null)
                return true;
        else
            return false;
    }

    //retorna codigo da palavra, caso palavra não exista, retorna -1
    public int codigo(String plv)   {
        if (palavras.get(plv) != null)
            return palavras.get(plv);
        else
            return -1;
    }
    
    //retorna uma palavra a partir de um código
    public String palavra(int codigo){
        if (palavras_.get(codigo) != null)
            return palavras_.get(codigo);
        else
            return null;
    }

}
