package model;

import java.util.LinkedList;
import java.util.List;

public class GeradorDeConexoes implements Runnable {

    volatile List<Mensagem> mensagens;
    public final int QTD_MENSAGENS = 3;

    public GeradorDeConexoes() {
        mensagens = new LinkedList<Mensagem>();
    }

    /**
     * adiciona host na fila
     * @param msg host
     */
    public void addMensagem(Mensagem msg){
        mensagens.add(msg);
        System.out.println("host adicionada na fila...");
    }

    public void rmMensagem(Mensagem msg){
        mensagens.remove(msg);
        System.out.println("host removida da fila...");
    }
    public void run() {
        while(true){
            if(mensagens.size() >= QTD_MENSAGENS){
                LinkedList<Mensagem> msgs = new LinkedList<Mensagem>();
                for(int i =0; i<QTD_MENSAGENS; i++)
                    msgs.add(mensagens.remove(0));

                System.out.println(QTD_MENSAGENS+" clientes conectados, enviando respostas para conexao p2p...");
                StringBuilder builder = new StringBuilder();
                //primeira linha da host para indicar que a host leva os hosts
                builder.append("hosts\n");

                //pega os ips participantes e envia como resposta para os clientes.
                for(Mensagem m: msgs)
                    builder.append(m.getHost()+" "+m.getNome()+";");


                String mensagem = builder.toString();
                for(Mensagem m: msgs)
                    m.enviarResposta(mensagem);

                System.out.println("mensagens enviadas...");
            }
        }
    }
}
