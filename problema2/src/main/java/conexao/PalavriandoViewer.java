package conexao;

import model.Sala;

import java.util.List;

public interface PalavriandoViewer {
    public void entrouNaSala(List<String> jogadores, int id);
    public void listarSalas(List<Sala> salas);
    public void infoSala(List<String> jogadores, int id, boolean eCoordenador);
    public void comecarJogo(Sala sala);

    //erros
    public void erroEntrouNaSala();
    public void erroListarSalas();
    public void erroInfoSala();
    public void erroCriarSala();
    public void erroRmSala();
}
