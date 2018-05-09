package conexao;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConexaoServidor implements Runnable{

    ProcessaConexaoServidor processaConexaoServidor;
    String nome;
    public ConexaoServidor(ProcessaConexaoServidor processador, String nome){
        processaConexaoServidor =processador;
        this.nome = nome;
    }

    public void run() {
        try {
            Socket socket = new Socket(Configuracoes.SERVIDOR, Configuracoes.PORTA);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            System.out.println("conectando com o servidor e esperando hosts...");

            out.writeUTF(solicitaMensagem());
            //lê mensagem enviada pelo servidor
            String salas = in.readUTF();
            processaConexaoServidor.processaMensagemDoServidor(salas);

            socket.close();

        } catch (IOException e) {
            System.err.println("servidor não responde..");
        }
    }

    private String solicitaMensagem(){
        return nome+";"+"salas";
    }
}
