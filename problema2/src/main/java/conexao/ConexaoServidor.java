package conexao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConexaoServidor implements Runnable{

    ProcessaConexaoServidor processaConexaoServidor;
    String mensagem;
    int tentativas;
    public ConexaoServidor(ProcessaConexaoServidor processador, String mensagem){
        processaConexaoServidor =processador;
        this.mensagem = mensagem;
        tentativas = 0;
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
            System.err.println("servidor não responde.. tentando novamente");
            //Thread.sleep(((int)100*Math.random())); 
            if(tentativas <= 5)
                this.run();
            else
                System.out.println("não conseguimos conectar com o servidor");
        } finally{
           
        }
    }

}
