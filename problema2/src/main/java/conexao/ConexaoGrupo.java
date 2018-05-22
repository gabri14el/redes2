package conexao;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.Receiver;

import java.nio.channels.Channel;

public class ConexaoGrupo {

    JChannel canal;
    ProcessadorDeGrupo processador;

    public ConexaoGrupo(String nomeCanal, final ProcessadorDeGrupo processador) {
        try {
            canal = new JChannel(nomeCanal);
            canal.setReceiver(new Receiver() {
                public void receive(Message message) {
                    processador.processaMesagemGrupo((String)message.getObject());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
