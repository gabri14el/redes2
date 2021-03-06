/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.List;
import javax.swing.DefaultListModel;
import model.Jogador;
import model.Palavriando;

/**
 *
 * @author gabriel
 */
public class Relatorio extends javax.swing.JPanel {
    Palavriando jogo;
    /**
     * Creates new form Relatorio
     */
    public Relatorio(Palavriando jogo) {
        initComponents();
        this.jogo = jogo;
        configura();
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lista_resultado = new javax.swing.JList<>();
        nome_vencedor = new javax.swing.JLabel();

        lista_resultado.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lista_resultado);

        nome_vencedor.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nome_vencedor)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(184, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nome_vencedor)
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> lista_resultado;
    private javax.swing.JLabel nome_vencedor;
    // End of variables declaration//GEN-END:variables

    private void configura() {
        DefaultListModel<String> modelo = new DefaultListModel<>();
        List<Jogador> jogadores = jogo.getJogadores();
        
        for(Jogador j: jogadores){
            modelo.addElement(j.getNome()+"fez "+j.getPontos()+" pontos");
            System.out.println(j.getNome() + " "+j.getPontos());
        }
        
        lista_resultado.setModel(modelo);
        System.out.println("o vencedor foi: "+jogadores.get(jogadores.size() - 1).getNome());
        
        nome_vencedor.setText("o vencedor foi: "+jogadores.get(jogadores.size() - 1).getNome());
    }
}
