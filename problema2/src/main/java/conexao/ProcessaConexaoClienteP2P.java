package conexao;

import java.net.InetAddress;

public interface ProcessaConexaoClienteP2P {

    public void ProcessaMensagemClienteP2P(InetAddress endereco, String msg);
}
