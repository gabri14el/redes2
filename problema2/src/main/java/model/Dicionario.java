package model;

import java.io.*;
import java.util.HashMap;

public class Dicionario {

    public static final String path = "dicionario.dic";
    HashMap<String, Integer> palavras;

    public Dicionario() {
        palavras = new HashMap<String, Integer>();
        leDiocionario();
    }


    //le dicionario da arquivo
    private void leDiocionario() {
        File file = new File(path);
        if(file.exists()){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                for(int i = 0; reader.ready(); i++){
                    palavras.put(reader.readLine(), i);
                }
            } catch (IOException e) {
                e.printStackTrace();
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

}
