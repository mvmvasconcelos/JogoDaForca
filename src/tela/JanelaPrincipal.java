/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tela;

import controle.Controlador;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author marcus.vasconcelos
 */
public class JanelaPrincipal extends javax.swing.JFrame {

    Controlador controlador = new Controlador();
    boolean novoJogo = true;
    ArrayList<ImageIcon> imagensForca = new ArrayList<>();
            
    
    
    public JanelaPrincipal() {
        initComponents();
        configuraPainelAlfabeto();
        desativaBotoes();
        acoesBtnNovoJogo(1);
        //Armazena as imagens da forca em uma lista
        for (int i = 0; i <= 7; i++) {
            imagensForca.add(new javax.swing.ImageIcon(getClass().getResource("/recursos/f" + i + ".gif")));            
        }
    }
    
    /**Método criado na última hora só para adicionar e remover os inputs de salvar e novo jogo
     * 
     * @param i - inteiro 1, 2 ou 3
     */
    private void acoesBtnNovoJogo(int i){
        switch(i){
            case 1:
                btnNovoJogo.setBounds(btnGuardarPalavra.getX(), btnGuardarPalavra.getY(), 250, btnGuardarPalavra.getHeight());
                btnNovoJogo.setPreferredSize(new Dimension(250, btnGuardarPalavra.getHeight()));
                btnNovoJogo.setVisible(false);
                break;
            case 2:    
                btnNovoJogo.setVisible(false);
                lblDigiteSecreta.setVisible(true);
                txtPalavraSecreta.setVisible(true);
                btnGuardarPalavra.setVisible(true);
                break;
            case 3:
                btnNovoJogo.setVisible(true);
                lblDigiteSecreta.setVisible(false);
                txtPalavraSecreta.setVisible(false);
                btnGuardarPalavra.setVisible(false);                
                break;
        }
    }
        
    private boolean validaPalavra(String palavra){
        if (!palavra.matches("[A-Z]+")) {
            JOptionPane.showMessageDialog(this, "Não use caracteres especiais, acentos, espaços ou números");
            return false;
        } else if (palavra.length() > 17){
            JOptionPane.showMessageDialog(this, "A palavra não deve conter mais do que 17 letras");            
            return false;
        } else if (palavra.isBlank()) {
            JOptionPane.showMessageDialog(this, "A palavra não pode ser em branco");
            return false;
        } 
        return true;
    }
    
    
    private void salvarPalavraSecreta(){
        //Se validar
        String palavra = txtPalavraSecreta.getText().toUpperCase(); 
        if (validaPalavra(palavra)) {
            acoesBtnNovoJogo(3);
            ativaBotoes();
            //Armazena a palavra secreta
            controlador.forca.setPalavraSecreta(palavra);
            //Converte a palavra secreta em underlines
            controlador.forca.setPalavraSendoAdivinhada(controlador.substituiPorTracos(palavra));
            //Imprime na tela a palavra com os underlines e espaçada
            lblPalavra.setText(controlador.espacadorLetras(controlador.forca.getPalavraSendoAdivinhada()));
            //Apaga os textos
            txtPalavraSecreta.setText("");
            lblFimDeJogo.setText("");
            //Reseta os contadores
            controlador.resetTudo();
            //Reseta imagem da forca
            lblImagem.setIcon(imagensForca.get(0));
        }
    }
    
    /**Cria o painel com as letras do alfabeto
     */
    private void configuraPainelAlfabeto(){
        //Define o painelLetras com layout fluido
        painelLetras.setLayout(new FlowLayout());
        //Força dimensão do painel
        painelLetras.setPreferredSize(new Dimension(240, 280));
        //Para cada letra do alfabeto
        for (int i = 0; i < controlador.getAlfabeto().length; i++) {
              //Converte em string
            String letra = String.valueOf(controlador.getAlfabeto()[i]);
            //Cria novo botão com texto da letra
            JButton botaoLetra = new JButton(letra);
            //Força dimensão do botão
            botaoLetra.setPreferredSize(new Dimension(40,40));
            if (novoJogo) {
            //Adiciona botão ao painel
            painelLetras.add(botaoLetra);  
            }
            
            //Adiciona listener para cada botão de letra
            botaoLetra.addActionListener((arg0) -> {
                //Se a letra pressionada está certa e ainda faltam letras a serem adivinhadas                    
                if (controlador.letraClicada(letra.charAt(0)) && controlador.forca.getQtdeLetrasAdivinhadas() < controlador.forca.getPalavraSecreta().length()) {
                    //Altera o texto do label com a palavra espaçada atualizada
                    lblPalavra.setText(controlador.espacadorLetras(controlador.forca.getPalavraSendoAdivinhada()));                   
                } else if(controlador.letraClicada(letra.charAt(0))){                    
                    //Altera o texto do label com a palavra espaçada atualizada
                    lblPalavra.setText(controlador.espacadorLetras(controlador.forca.getPalavraSendoAdivinhada()));
                    //Exibe imagem de vitória e o texto
                    lblImagem.setIcon(imagensForca.get(7));
                    lblFimDeJogo.setText("Você ganhou!");                    
                    desativaBotoes();
                } else {                    
                    controlador.forca.aumentaQtdeErrosCometidos();                    
                    if (controlador.forca.getQtdeErrosCometidos() >= controlador.forca.getLimiteTentativas()) {
                        lblFimDeJogo.setText("Você perdeu.");
                        lblPalavra.setText("A palavra era: " + controlador.forca.getPalavraSecreta());
                        desativaBotoes();
                    }
                    controlador.aumentaNumeroImagemAtualDaForca();
                    lblImagem.setIcon(imagensForca.get(controlador.getNumeroImagemAtualDaForca()));
                }
                //Desabilita o botão pressionado
                botaoLetra.setEnabled(false);
            });
        }
        pack();
        novoJogo = false;
    }
    private void ativaBotoes(){
        for (Component component : painelLetras.getComponents()) {
            if (component instanceof JButton) {
                ((JButton) component).setEnabled(true);
            }
        }
    }
    private void desativaBotoes(){
        for (Component component : painelLetras.getComponents()) {
            if (component instanceof JButton) {
                ((JButton) component).setEnabled(false);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblPalavra = new javax.swing.JLabel();
        painelLetras = new javax.swing.JPanel();
        lblImagem = new javax.swing.JLabel();
        lblFimDeJogo = new javax.swing.JLabel();
        lblDigiteSecreta = new javax.swing.JLabel();
        txtPalavraSecreta = new javax.swing.JTextField();
        btnGuardarPalavra = new javax.swing.JButton();
        btnNovoJogo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jogo da Forca");
        setResizable(false);

        jPanel1.setMaximumSize(new java.awt.Dimension(800, 400));
        jPanel1.setMinimumSize(new java.awt.Dimension(800, 400));

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Jogo da Forca");

        lblPalavra.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblPalavra.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPalavra.setText("D I G I T E  U M A  P A L A V R A");
        lblPalavra.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        painelLetras.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        painelLetras.setPreferredSize(new java.awt.Dimension(240, 280));

        javax.swing.GroupLayout painelLetrasLayout = new javax.swing.GroupLayout(painelLetras);
        painelLetras.setLayout(painelLetrasLayout);
        painelLetrasLayout.setHorizontalGroup(
            painelLetrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 246, Short.MAX_VALUE)
        );
        painelLetrasLayout.setVerticalGroup(
            painelLetrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
        );

        lblImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/f0.gif"))); // NOI18N
        lblImagem.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblImagem.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblImagem.setPreferredSize(new java.awt.Dimension(200, 200));

        lblFimDeJogo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblFimDeJogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblDigiteSecreta.setText("Digite a palavra secreta:");

        btnGuardarPalavra.setText("Guardar");
        btnGuardarPalavra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarPalavraActionPerformed(evt);
            }
        });

        btnNovoJogo.setText("Novo Jogo");
        btnNovoJogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoJogoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(350, 350, 350))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblDigiteSecreta)
                        .addGap(18, 18, 18)
                        .addComponent(txtPalavraSecreta))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPalavra, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblImagem, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                            .addComponent(lblFimDeJogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(painelLetras, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnGuardarPalavra, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNovoJogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(182, 182, 182))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblTitulo)
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDigiteSecreta)
                    .addComponent(txtPalavraSecreta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarPalavra)
                    .addComponent(btnNovoJogo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(190, 190, 190)
                                .addComponent(lblFimDeJogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblImagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPalavra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(painelLetras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarPalavraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarPalavraActionPerformed
        salvarPalavraSecreta();
    }//GEN-LAST:event_btnGuardarPalavraActionPerformed

    private void btnNovoJogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoJogoActionPerformed
        acoesBtnNovoJogo(2);
    }//GEN-LAST:event_btnNovoJogoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JanelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardarPalavra;
    private javax.swing.JButton btnNovoJogo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDigiteSecreta;
    private javax.swing.JLabel lblFimDeJogo;
    private javax.swing.JLabel lblImagem;
    private javax.swing.JLabel lblPalavra;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel painelLetras;
    private javax.swing.JTextField txtPalavraSecreta;
    // End of variables declaration//GEN-END:variables
}
