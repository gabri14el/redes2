package conexao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Conexao implements Runnable{


    ProcessadorDeConexoes processadorDeConexoes;

    /**
     * Construtor padrão;
     * @param processadorDeConexoes processador de conexoes que será enviado para todos os clientes
     */
    public Conexao(ProcessadorDeConexoes processadorDeConexoes) {
        this.processadorDeConexoes = processadorDeConexoes;
    }

    public void run() {
        while(true){

            //fica esperando uma conexao TCP
            try {
                ServerSocket serverSocket = new ServerSocket(Configuracoes.PORTA);
                System.out.println("esperando conexao TCP na porta "+Configuracoes.PORTA +"...");
                Socket socket = serverSocket.accept();
                new Thread(new TrataConexoes(socket, processadorDeConexoes)).start();

            } catch (IOException e) {
                //System.err.println("erro durante a comunicação com um dos clientes...");
            }

        }
    }



        private class TrataConexoes implements Runnable, EnviadorDeResposta {

            Socket socket;
            DataOutputStream out;
            DataInputStream in;
            ProcessadorDeConexoes processadorDeConexoes;
            boolean controle;

            /**
             * Contrutor padrão
             * @param socket socket que será processado
             * @param processadorDeConexoes processador de conexoes, no caso a classe Servidor
             */
            public TrataConexoes(Socket socket, ProcessadorDeConexoes processadorDeConexoes) {
                this.socket = socket;
                controle = true;
                try {
                    out = new DataOutputStream(socket.getOutputStream());
                    in = new DataInputStream(socket.getInputStream());
                } catch (IOException e) {
                    //System.err.println("erro na conexao com o cliente "+socket.getInetAddress().toString());
                    //processadorDeConexoes.erroDuranteConexao(socket.getInetAddress().getHostAddress(), this, null);
                    System.err.println("houve um erro na hora de instaciar a conexao...");
                }
                this.processadorDeConexoes = processadorDeConexoes;
            }

            public void run() {
                try {
                    System.out.println("tratamento de conexão com "+ socket.getInetAddress().getHostAddress()+"iniciado... ");
                    InetAddress ip = socket.getInetAddress();
                    String msg = in.readUTF();
                    processadorDeConexoes.processaConexao(ip.getHostAddress(), this, msg);

                    while(controle)
                        if(socket.isClosed())
                            processadorDeConexoes.erroDuranteConexao(ip.getHostAddress(), this, msg);


                } catch (IOException e) {
                    e.printStackTrace();
                }
                //pega endereco e envia para o processador de conexoes
            }

            /**
             * Método chamado quando o processador de conexoes monta a lista dos
             * jogadores que irão participar da partida e envia essa lista para todos eles;
             * @param msg mensagem que será enviada
             */
            public void enviaResposta(String msg) {
                try {
                    out.writeUTF(msg);
                    controle = false;
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
}
