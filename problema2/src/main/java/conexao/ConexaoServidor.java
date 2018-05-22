package conexao;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConexaoServidor implements Runnable{

    ProcessaConexaoServidor processaConexaoServidor;
    String mensagem;
    public ConexaoServidor(ProcessaConexaoServidor processador, String mensagem){
        processaConexaoServidor =processador;
        this.mensagem = mensagem;
    }

    public void run() {
        try {
            Socket socket = new Socket(Configuracoes.SERVIDOR, Configuracoes.PORTA);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            System.out.println("conectando com o servidor e esperando hosts...");

            out.writeUTF(mensagem);
            //lê mensagem enviada pelo servidor
            String resposta = in.readUTF();
            processaConexaoServidor.processaMensagemDoServidor(resposta);

            socket.close();

        } catch (IOException e) {
            System.err.println("servidor não responde..");
        }
    }

}
