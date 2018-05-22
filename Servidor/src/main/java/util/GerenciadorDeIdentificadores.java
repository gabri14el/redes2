package util;

import java.util.LinkedList;
import java.util.StringTokenizer;

public class GerenciadorDeIdentificadores {


    LinkedList<Integer> salasID;
    LinkedList<Integer> multicastFinal;
    public final static String SUFIXO_MULTICAST = ".";

    public GerenciadorDeIdentificadores() {
        salasID = new LinkedList<Integer>();
        multicastFinal=new LinkedList<Integer>();
        init();
    }

    private void init() {
        for(int i = 0; i<10000; i++ ){
           salasID.add(new Integer(i));
        }

    }

    /**
     * Método responsável por retornar ids
     * @param id
     */
    public void disponibilizaSalaId(int id){
        salasID.add(new Integer(id));
    }

    /**
     * Método que retorna se pode criar novos ids
     * @return
     */
    public boolean temSalaIDDisponivel(){
        return salasID.size()!=0;
    }

    public int geraSalaId(){
        return salasID.remove().intValue();
    }

    /**
     * Retorna um sufixo disponível para multicast
     * @return
     */
    public String getSufixoMulticast(){
        int aux = multicastFinal.remove().intValue();
        if(aux <=9){
            return("00"+aux);
        }else if(aux<= 99){
            return("0"+aux);
        }else{
            return ""+aux;
        }
    }

    /**
     * Método libera um sufixo de endereco multicast a partir do endereco recebido
     * @param enderecoMulticast
     */
    public void liberaSufixoMulticas(String enderecoMulticast){
        StringTokenizer token = new StringTokenizer(enderecoMulticast, ".");
        token.nextToken();token.nextToken();token.nextToken();

        multicastFinal.add(new Integer(token.nextToken()));
    }
}
