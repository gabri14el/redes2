package conexao;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConexaoClienteP2P implements Runnable{

    InetAddress endereco;
    ProcessaConexaoClienteP2P processaConexaoClienteP2P;
    public ConexaoClienteP2P(String host, ProcessaConexaoClienteP2P processadorDeMensagens) {
        try {
            endereco = InetAddress.getByName(host);
            processaConexaoClienteP2P = processadorDeMensagens;
        } catch (UnknownHostException e) {
            System.err.println("erro ao obter endereço a partir do nome do host... "+host);
        }
    }

    public void run() {
        try {
            Socket socket = new Socket(endereco, Configuracoes.PORTA_P2P);
            System.out.println("conectando com o host "+endereco.getCanonicalHostName());

            //Lê do barramento o que foi enviado
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String mensagem = in.readUTF();

            //envia o
            processaConexaoClienteP2P.ProcessaMensagemClienteP2P(endereco, mensagem);
            socket.close();
        } catch (IOException e) {
            System.err.println("erro durante conexao com o host "+endereco.getHostAddress());
        }
    }
}
