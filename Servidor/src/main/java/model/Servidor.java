package model;

import conexao.Conexao;
import conexao.EnviadorDeResposta;
import conexao.ProcessadorDeConexoes;
import util.GerenciadorDeIdentificadores;

import java.util.*;

public class Servidor implements ProcessadorDeConexoes{

    public static Servidor instancia = null;
    Conexao conexao;
    GeradorDeConexoes geradorDeConexoes;
    HashMap<String, Jogador> jogadores;
    TreeMap<Integer, Sala> salas;
    int contadorSalas;
    GerenciadorDeIdentificadores identificadores;




    //======================================
    //                 MAIN

    public static void main(String[] args){
        new Servidor();
    }

    //======================================

    public final static String LISTA_DE_SALAS = "listaSalas";
    public final static String ENTRAR_NA_SALA = "entrarNaSala";
    public final static String CRIAR_SALA = "criarSala";
    public final static String CRIAR_CONTA = "criarConta";
    public final static String REMOVER_SALA = "removerSala";
    public final static String ERRO_AUTENTICACAO = "erroAutenticacao";
    public final static String ERRO = "erro";
    public final static String SUCESSO = "sucesso";



    private Servidor(){
        //iniciando a thread que faz conexao com os clientes
        conexao = new Conexao(this);
        new Thread(conexao).start();

        //iniciando a thread que processa as conexoes com os clientes
        geradorDeConexoes = new GeradorDeConexoes();
        new Thread(geradorDeConexoes).start();
        jogadores = new HashMap<String, Jogador>();
        salas =new TreeMap<Integer, Sala>();
        contadorSalas = 0;
        identificadores = new GerenciadorDeIdentificadores();
    }

    /**
     * implementação do singleton
     * @return instancia
     */
    public static Servidor getInstancia(){
        if(instancia == null)
            instancia = new Servidor();
        return instancia;
    }

    /**
     * Verificar se as credenciais enviadas batem certo
     * @param nome
     * @param senha
     * @return
     */
    private boolean autentica(String nome, String senha){
        Jogador aux = jogadores.get(nome);
        return aux.getSenha().equals(senha);
    }

    /**
     * verificar se jogador existe
     * @param nome
     * @return
     */
    private boolean jogadorExiste(String nome){
        return jogadores.get(nome) != null;
     }


    /**
     * Método responsavel por validar uma transação, verificando se a senha do jogaor é válise
     * Caso jogador não exista no sistema, é cadastrado uma conta para ele.
     * A cada nova transacao o host do jogador é atualizado
     * @param nome nome
     * @param senha senha
     * @param host ip
     * @return
     */
    private boolean transacaoValida(String nome, String senha,String host){
        if(jogadorExiste(nome)) {
            if (autentica(nome, senha)){
                jogadores.get(nome).setHost(host);
                return true;
            }
            else
                return false;
        }
        else{
            jogadores.put(nome, new Jogador(nome, senha));
            return true;
        }
    }
    public synchronized void processaConexao(String host, EnviadorDeResposta enviadorDeResposta, String nomeJogador) {
        StringTokenizer token = new StringTokenizer(host, ";");
        String requisicao = token.nextToken();

        StringBuilder resposta = new StringBuilder();
        resposta.append(requisicao+";");
        //reposnde requisicao de criar sala
        if(requisicao.equals(CRIAR_SALA)){
            String nome = token.nextToken();
            String senha = token.nextToken();
            if(transacaoValida(nome, senha, host) && identificadores.temSalaIDDisponivel()){
                Sala sala = new Sala(jogadores.get(nome), identificadores.geraSalaId());
                salas.put(sala.id, sala);
                resposta.append(sala.toString());
            }else{
                resposta.append(ERRO);
            }
        }
        //responde requisição de entrar na sala
        else if(requisicao.equals(ENTRAR_NA_SALA)){
            String nome = token.nextToken();
            String senha = token.nextToken();
            if(transacaoValida(nome, senha, host)){
                int id = Integer.parseInt(token.nextToken());
                Sala sala = salas.get(id);
                sala.addJogador(jogadores.get(nome));
                resposta.append(sala.toString());

            }else{
                resposta.append(ERRO);
            }
        }
        //responde requisicao de limpar salas
        else if(requisicao.equals(LISTA_DE_SALAS)){
            resposta.append(salas.size()+";");
            for(Sala s: salas.values()){
                resposta.append(s.informacoesBasicasFormatadas()+";");
            }
        }
        //responde requisicao de remover sala da lista de salas
        else if(requisicao.equals(REMOVER_SALA)){
            String nome = token.nextToken();
            String senha = token.nextToken();
            if(transacaoValida(nome, senha, host)){
                int id = Integer.parseInt(token.nextToken());
                Jogador jogador = jogadores.get(nome);
                Sala sala = salas.get(id);
                //verifica se quem enviou a mensagem foi o coordenador da sala e
                if(sala.eCoordenador(jogador)){
                    salas.remove(id);
                    identificadores.disponibilizaSalaId(id);
                    resposta.append(SUCESSO);
                }
                else{
                    resposta.append(ERRO);
                }
            }
        }
        enviadorDeResposta.enviaResposta(resposta.toString());
    }

    public void erroDuranteConexao(String cliente, EnviadorDeResposta enviadorDeResposta, String nomeJogador) {
        System.out.println("processando erro..");
        geradorDeConexoes.rmMensagem(new Mensagem(enviadorDeResposta, cliente, nomeJogador));
    }


}
