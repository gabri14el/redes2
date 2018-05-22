package model;

import conexao.*;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.Receiver;
import view.ProcessadorReqView;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Palavriando implements ProcessaConexaoServidor, ProcessaConexaoClienteP2P, ProcessadorReqView , ProcessadorDeGrupo{


    String nomeJogador;
    String senhaJogador;
    Sala salaJogo;
    Dicionario dicionario;

    JChannel canal;


    private static String SALAS= "salas";
    private static String NOME_JA_UTILIZADO = "nomeUtilizado";
    private static String DADOS_SALA = "dadosSala";

    public final static String LISTA_DE_SALAS = "listaSalas";
    public final static String ENTRAR_NA_SALA = "entrarNaSala";
    public final static String CRIAR_SALA = "criarSala";
    public final static String CRIAR_CONTA = "criarConta";
    public final static String REMOVER_SALA = "removerSala";
    public final static String ERRO_AUTENTICACAO = "erroAutenticacao";
    public final static String ERRO = "erro";
    public final static String SUCESSO = "sucesso";

    public PalavriandoViewer viewer;

    //======================================
    //                 MAIN

    public static void main(String[] args){
        new Palavriando(null);
    }

    //======================================



    public Palavriando(PalavriandoViewer viewer) {
        this.viewer = viewer;
        dicionario = new Dicionario();
    }


    /**
     * Metodo chamado quando uma mensagem vinda do servidor deve ser processada
     * @param msg
     */
    public synchronized void processaMensagemDoServidor(String msg) {
        StringTokenizer token = new StringTokenizer(msg);
        String tipoRequisicao = token.nextToken();
        System.out.println("tipoResposta: "+tipoRequisicao);
        if(tipoRequisicao.equals(CRIAR_SALA)){
            String nextToken = token.nextToken();
            if(!nextToken.equals(ERRO)){
                int salaId = Integer.parseInt(nextToken);
                int qtd_jogadores = Integer.parseInt(token.nextToken());

                salaJogo = new Sala(qtd_jogadores, salaId, true);

                for(int i = 0; i < qtd_jogadores; i++){
                    String aux = token.nextToken();
                    StringTokenizer tokenJogadores = new StringTokenizer(aux);
                    try {
                        salaJogo.addJogador(new Jogador(InetAddress.getByName(tokenJogadores.nextToken()), tokenJogadores.nextToken()));
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }

                    //chama método no viewer pra setar a a sala
                    viewer.entrouNaSala(salaJogo.nomeDosJogadores(), salaId);
                }
            }
            else
                viewer.erroCriarSala();

        }
        else if(tipoRequisicao.equals(ENTRAR_NA_SALA)){
            String proximoToken = token.nextToken();
            if(proximoToken.equals(ERRO))
                viewer.erroCriarSala();
            else{
                int salaId = Integer.parseInt(proximoToken);
                int qtd_jogadores = Integer.parseInt(token.nextToken());

                salaJogo = new Sala(qtd_jogadores, salaId, true);

                for(int i = 0; i < qtd_jogadores; i++){
                    String aux = token.nextToken();
                    StringTokenizer tokenJogadores = new StringTokenizer(aux);
                    try {
                        salaJogo.addJogador(new Jogador(InetAddress.getByName(tokenJogadores.nextToken()), tokenJogadores.nextToken()));
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }

                String dados[][]=new String[4][4];
                for (int i=0; i<4; i++)
                    for (int j = 0;j<4; j++){
                        dados[i][j] = token.nextToken();
                    }
                salaJogo.setDados(dados);
                //chama método no viewer pra setar a a sala
                viewer.entrouNaSala(salaJogo.nomeDosJogadores(), salaId);

            }
        }
        else if(tipoRequisicao.equals(LISTA_DE_SALAS)){
            int qtd_salas=Integer.parseInt(token.nextToken());
            List<Sala> salas=new LinkedList<Sala>();
            for(int i=0; i< qtd_salas; i++){
                int id = Integer.parseInt(token.nextToken());
                int qtd_jogadores = Integer.parseInt(token.nextToken());


                salas.add(new Sala(id, qtd_jogadores, false));
            }

            viewer.listarSalas(salas);
        }
        else if(tipoRequisicao.equals(REMOVER_SALA)){
            String proximoToken = token.nextToken();
            if(proximoToken.equals(ERRO)){
                viewer.erroRmSala();
            }
        }
    }


    /**
     * Método chamado quando um cliente envia uma mensagem
     * @param msg
     */
    public void ProcessaMensagemClienteP2P(InetAddress endereco, String msg) {

    }

    //muda nome e senha do jogador
    public void configurarJogador(String nome, String senha){
        this.nomeJogador = nome;
        senhaJogador = senha;
    }

    public void entrarNaSala(int id) {
        StringBuilder builder = new StringBuilder();
        builder.append(ENTRAR_NA_SALA+";");
        builder.append(nomeJogador+";"+senhaJogador+";");
        builder.append(id+";");
        solicitaAoServidor(builder.toString());
    }

    public void listarSalas() {
        StringBuilder builder = new StringBuilder();
        builder.append(LISTA_DE_SALAS);
        builder.append(nomeJogador+";"+senhaJogador+";");
        solicitaAoServidor(builder.toString());
    }

    public void comecarJogo() {
        StringBuilder builder = new StringBuilder();
        builder.append(REMOVER_SALA+";");
        builder.append(nomeJogador+";"+senhaJogador+";");
        builder.append(salaJogo.codigo+";");
        solicitaAoServidor(builder.toString());
    }


    /**
     * Cria conexões como o servidor
     * @param mensagem
     */
    private void solicitaAoServidor(String mensagem){
        ConexaoServidor serv = new ConexaoServidor(this, mensagem);
        new Thread(serv).start();
    }

    public synchronized void processaMesagemGrupo(String str) {

    }

    private void configuraCanal(String str){
        try {
            canal = new JChannel();
            canal.connect(str);
            canal.setReceiver(new Receiver() {
                public void receive(Message message) {
                    processaMesagemGrupo((String)message.getObject());
                }
            });
        } catch (Exception e) {
            System.out.println("houve um erro ao iniciar o canal");
        }
    }

    private void enviaNoCanal(String msg){
        try {
            canal.send(new Message(null, msg));
        } catch (Exception e) {
            System.err.println("houve um erro ao enviar uma mensagem");
        }
    }

}
