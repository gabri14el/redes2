package model;

import conexao.*;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.Receiver;
import view.ProcessadorReqView;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public final static String SAIR_DA_SALA = "sairDaSala";
    public final static String INFO_SALA = "infoSala";
    
    
    public final static String COMECAR_JOGO= "comecarJogo";
    public final static String PONTUACAO = "pontuacao";

    public PalavriandoViewer viewer;
    public List<Integer> palavras;

    public String getNomeJogador() {
        return nomeJogador;
    }

    public void setViewer(PalavriandoViewer viewer) {
        this.viewer = viewer;
    }

    

    public Palavriando(PalavriandoViewer viewer) {
        this.viewer = viewer;
        dicionario = new Dicionario();
        palavras = new LinkedList<Integer>();
    }

    public boolean verificaLogin(){
        return nomeJogador != null && !nomeJogador.isEmpty() && senhaJogador !=null && !senhaJogador.isEmpty();
    }

    /**
     * Metodo chamado quando uma mensagem vinda do servidor deve ser processada
     * @param msg
     */
    public synchronized void processaMensagemDoServidor(String msg) {
        StringTokenizer token = new StringTokenizer(msg, ";");
        String tipoRequisicao = token.nextToken();
        System.out.println("Resposta: "+msg);
        
        if(tipoRequisicao.equals(ENTRAR_NA_SALA)){
            String proximoToken = token.nextToken();
            if(proximoToken.equals(ERRO))
                viewer.erroCriarSala();
            else{
                int salaId = Integer.parseInt(proximoToken);
                int qtd_jogadores = Integer.parseInt(token.nextToken());

                salaJogo = new Sala(qtd_jogadores, salaId, false);

                for(int i = 0; i < qtd_jogadores; i++){
                    String nome = token.nextToken();
                    
                    try {
                        InetAddress endereco = InetAddress.getByName(token.nextToken());
                        salaJogo.addJogador(new Jogador(endereco, nome));
                    } catch (UnknownHostException e) {
                        System.out.println("houve um erro ao resolver o endereço de um host...");
                    }
                }

                String dados[][]=new String[4][4];
                for (int i=0; i<4; i++)
                    for (int j = 0;j<4; j++){
                        dados[i][j] = token.nextToken();
                    }
                salaJogo.setDados(dados);
                //entra no canal JGroups
                configuraCanal(token.nextToken());
                //chama método no viewer pra setar a a sala
                viewer.entrouNaSala(salaJogo.nomeDosJogadores(), salaId);
                
            }
        }
        else if(tipoRequisicao.equals(CRIAR_SALA)){
            String proximoToken = token.nextToken();
            if(proximoToken.equals(ERRO))
                viewer.erroCriarSala();
            else{
                int salaId = Integer.parseInt(proximoToken);
                int qtd_jogadores = Integer.parseInt(token.nextToken());

                salaJogo = new Sala(qtd_jogadores, salaId, true);

                for(int i = 0; i < qtd_jogadores; i++){
                    String nome = token.nextToken();
                    
                    try {
                        InetAddress endereco = InetAddress.getByName(token.nextToken());
                        salaJogo.addJogador(new Jogador(endereco, nome));
                    } catch (UnknownHostException e) {
                        System.out.println("houve um erro na hora de resolver o endereço de um host...");
                    }
                }

                String dados[][]=new String[4][4];
                for (int i=0; i<4; i++)
                    for (int j = 0;j<4; j++){
                        dados[i][j] = token.nextToken();
                    }
                salaJogo.setDados(dados);
                //chama método no viewer pra setar a a sala
                configuraCanal(token.nextToken());
                viewer.entrouNaSala(salaJogo.nomeDosJogadores(), salaId);
                
            }
        }
        else if(tipoRequisicao.equals(LISTA_DE_SALAS)){
            int qtd_salas=Integer.parseInt(token.nextToken());
            List<Sala> salas=new LinkedList<Sala>();
            for(int i=0; i< qtd_salas; i++){
                int id = Integer.parseInt(token.nextToken());
                int qtd_jogadores = Integer.parseInt(token.nextToken());


                salas.add(new Sala(qtd_jogadores, id, false));
            }
            System.out.println(qtd_salas);
            viewer.listarSalas(salas);
        }
        else if(tipoRequisicao.equals(REMOVER_SALA)){
            String proximoToken = token.nextToken();
            if(proximoToken.equals(ERRO)){
                viewer.erroRmSala();
            }
        }
        else if(tipoRequisicao.equals(SAIR_DA_SALA)){
            String proximoToken = token.nextToken();
            if(proximoToken.equals(ERRO));
        }
        
        else if(tipoRequisicao.equals(INFO_SALA)){
            String proximoToken = token.nextToken();
            if(proximoToken.equals(ERRO))
                viewer.erroCriarSala();
            else{
                int salaId = Integer.parseInt(proximoToken);
                int qtd_jogadores = Integer.parseInt(token.nextToken());

                salaJogo = new Sala(qtd_jogadores, salaId, true);

                for(int i = 0; i < qtd_jogadores; i++){
                    String nome = token.nextToken();
                    
                    try {
                        InetAddress endereco = InetAddress.getByName(token.nextToken());
                        salaJogo.addJogador(new Jogador(endereco, nome));
                    } catch (UnknownHostException e) {
                        System.out.println("houve um erro na hora de resolver o endereço de um host...");
                    }
                }

                String dados[][]=new String[4][4];
                for (int i=0; i<4; i++)
                    for (int j = 0;j<4; j++){
                        dados[i][j] = token.nextToken();
                    }
                salaJogo.setDados(dados);
                //chama método no viewer pra setar a a sala
                //configuraCanal(token.nextToken());
               iniciarJogo();
               if(eCoordenadorDaSala()) comecarJogo();
                
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
        builder.append(LISTA_DE_SALAS+";");
        solicitaAoServidor(builder.toString());
    }
    
    public void infoSala(){
        StringBuilder builder = new StringBuilder();
        builder.append(INFO_SALA+";");
        builder.append(salaJogo.codigo);
        solicitaAoServidor(builder.toString());
    }

    public void comecarJogo() {
        StringBuilder builder = new StringBuilder();
        builder.append(REMOVER_SALA+";");
        builder.append(nomeJogador+";"+senhaJogador+";");
        builder.append(salaJogo.codigo+";");
        solicitaAoServidor(builder.toString());
    }
    
    public void criarSala(){
        StringBuilder builder = new StringBuilder();
        builder.append(CRIAR_SALA+";");
        builder.append(nomeJogador+";"+senhaJogador+";");
        solicitaAoServidor(builder.toString());
    }
    


    /**
     * Cria conexões como o servidor
     * @param mensagem
     */
    private void solicitaAoServidor(String mensagem){
        ConexaoServidor serv = new ConexaoServidor(this, mensagem);
        new Thread(serv).start();
        System.out.println("enviando mensagem ao serv: "+mensagem);
    }

    
    /**
     * Método responsável por processar mensagens que chegam do canal 
     * @param str mensagem 
     */
    public synchronized void processaMesagemGrupo(String str) {
        System.out.println("mensagem recebida no grupo: "+str);
        StringTokenizer token = new StringTokenizer(str, ";");
        String usuario = token.nextToken();
        String requisicao = token.nextToken();
        
        //se o coordenador enviar o comecar jogo
        if(requisicao.equals(COMECAR_JOGO)){
            if(usuario.equals(salaJogo.nomeDoCoordenador())){
               infoSala(); 
            }
        }else if(requisicao.equals(PONTUACAO)){
            int qtd = Integer.parseInt(token.nextToken());
            for(int i=0; i<qtd; i++)
                salaJogo.addPalavra(usuario, dicionario.palavra(Integer.parseInt(token.nextToken())));
        }          
    }

    /**
     * Retorna nome dos jogadores
     * @return lista com o nome dos jogadores
     */
    public List<String> pegaJogadores(){
        return salaJogo.nomeDosJogadores();
    }
    
    public int getSalaId(){
        return salaJogo.codigo;
    }
    private void configuraCanal(String str){
        if(canal != null){
            canal.close();
        }
        try {
            canal = new JChannel();
            canal.connect(str+salaJogo.codigo);
            System.out.println("setando recebedor de mensagens do canal...");
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
            System.out.println("enviando mensagem no canal: "+msg);
            canal.send(new Message(null, msg));
        } catch (Exception e) {
            System.err.println("houve um erro ao enviar uma mensagem");
        }
    }

    public void sairDaSala() {
        StringBuilder builder = new StringBuilder();
        builder.append(SAIR_DA_SALA+";");
        builder.append(nomeJogador+";"+senhaJogador+";");
        builder.append(salaJogo.codigo);
        solicitaAoServidor(builder.toString());
    }
    
    //retorna se o usuario eh o coordenador da sala
   public boolean eCoordenadorDaSala(){
       return salaJogo.nomeDoCoordenador().equals(nomeJogador);
   }
   
   
   //método que enviar no canal pra comecar jogo
   public void solicitaIniciarJogo(){
       //comecarJogo();
       enviaNoCanal(nomeJogador+";"+COMECAR_JOGO);
   }

   //método que cria string com a pontuação e envia no canal
   public void enviarPontuacaoAoGrupo(){
       StringBuilder builder = new StringBuilder();
       builder.append(nomeJogador+";");
       builder.append(PONTUACAO+";");
       builder.append(palavras.size()+";");
       for(Integer s: palavras)
           builder.append(s+";");
       enviaNoCanal(builder.toString());
   }

   //método chamado para coordenador iniciar jogo
   private void iniciarJogo() {
       viewer.comecarJogo(salaJogo);
   }

   //método chamado quando o timer do jogo acaba
    public void finalizarJogo() {
        enviarPontuacaoAoGrupo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(30000);
                    fimDeJogo();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Palavriando.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }
    
    public void fimDeJogo(){
        canal.close();
        salaJogo.calculaPontuacaoDosJogadores();
        viewer.gerarRelatorio();
    }
    
    //retorna a matriz que representa os dados
    public String[][] getDados(){
        return salaJogo.dados;
    }
    
    //retorna se a string está entre os dados 
    public boolean verificaSeStringEstaNosDados(String str){
        return salaJogo.verificaSeStringEstaNosDados(str);
    }
    //método usado para verificar se palavra existe no dicionário
    public boolean addPalavra(String plv){
        if(dicionario.existe(plv)){
            if(!palavras.contains(dicionario.codigo(plv)))
                palavras.add(dicionario.codigo(plv));
            return true;
        }
        else
            return false;
    }
    
    public List<Jogador> getJogadores(){
        return salaJogo.getJogadores();
    }
}
