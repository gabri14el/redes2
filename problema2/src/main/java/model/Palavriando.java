package model;

import conexao.ConexaoClienteP2P;
import conexao.ConexaoServidor;
import conexao.ProcessaConexaoClienteP2P;
import conexao.ProcessaConexaoServidor;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Palavriando implements ProcessaConexaoServidor, ProcessaConexaoClienteP2P {

    List<ConexaoClienteP2P> conexoesComoCliente;
    ConexaoServidor conexaoServidor;
    String nomeJogador;

    private static String SALAS= "salas";
    private static String NOME_JA_UTILIZADO = "nomeUtilizado";
    private static String DADOS_SALA = "dadosSala";

    //======================================
    //                 MAIN

    public static void main(String[] args){
        new Palavriando();
    }

    //======================================



    public Palavriando() {
        conexoesComoCliente = new ArrayList<ConexaoClienteP2P>();

        conexaoServidor = new ConexaoServidor(this, nomeJogador);
        new Thread(conexaoServidor).start();
    }


    /**
     * Metodo chamado quando uma mensagem vinda do servidor deve ser processada
     * @param msg
     */
    public synchronized void processaMensagemDoServidor(String msg) {
        System.out.println(msg);
        System.out.println("processando mensagem recebida do servidor");
        StringTokenizer token = new StringTokenizer(msg);
        String tipoResposta = token.nextToken();
        System.out.println("tipoResposta: "+tipoResposta);
        String dados = token.nextToken("");
        if(tipoResposta.equals(NOME_JA_UTILIZADO)){
            // TODO enviar mensagem de nome já utilizado
        }
        else if(tipoResposta.equals(SALAS)){
            token = new StringTokenizer(dados, ";");
            List<Sala> salas = new LinkedList<Sala>();
            while (token.hasMoreTokens()) {
                int codigo = Integer.parseInt(token.nextToken(" "));
                int participantes = Integer.parseInt(token.nextToken());
                salas.add(new Sala(participantes, codigo));
            }
            //TODO enviar salas para a interface grafica
        }
        else if(tipoResposta.equals(DADOS_SALA)){
            //criar o protocolo para definir quem vai ser o coordenador
        }
    }


    /**
     * Método chamado quando um cliente envia uma mensagem
     * @param msg
     */
    public void ProcessaMensagemClienteP2P(InetAddress endereco, String msg) {

    }
}
