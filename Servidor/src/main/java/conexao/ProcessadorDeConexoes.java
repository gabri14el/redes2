package conexao;

public interface ProcessadorDeConexoes {
    public void processaConexao(String str, EnviadorDeResposta enviadorDeResposta, String nomeJogador);
    public void erroDuranteConexao(String cliente,EnviadorDeResposta enviadorDeResposta, String nomeJogador);
}
