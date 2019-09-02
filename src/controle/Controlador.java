/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.util.ArrayList;
import negocio.Forca;

/**
 *
 * @author marcus.vasconcelos
 */
public class Controlador {
    public Forca forca = new Forca();
    private final char[] alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private int numeroImagemAtualDaForca;
        
    public char[] getAlfabeto(){
        return alfabeto;
    }
        
    public int getNumeroImagemAtualDaForca(){
        return numeroImagemAtualDaForca;
    }
    
    public void aumentaNumeroImagemAtualDaForca(){
        numeroImagemAtualDaForca++;
    }
    
    public void resetTudo(){
        forca.resetQtdeLetrasAdivinhadas();
        forca.resetQtdeErrosCometidos();
        numeroImagemAtualDaForca = 0;
    }
    
    /**Método para revelar as letras exibidas em tela, convertendo os traços
     * em letras
     * @param indices - a posição, na string da palavra digitada, onde estão as letras digitadas
     */
    public void revelaLetra(ArrayList indices){
        //Armazena a palavra em um array de caracteres para facilitar a manipulação
        char letrasDaPalavra[] = forca.getPalavraSendoAdivinhada().toCharArray();
        //contador de vezes que a lista indices foi percorrida
        int conta = 0;
        //pega o valor presente na última posição do arraylist
        int ultimo = (int)indices.get(indices.size()-1);
        //Percorre cada letra da palavra
        for (int i = 0; i < forca.getPalavraSendoAdivinhada().length(); i++) {
            
            //Se a posição atual for menor do que a última posição adivinhada
            //E o valor da posição (conta) da lista, ou seja, o índice da letra adivinhada
            //for igual a posição atual na palavra
            if (i <= ultimo && i == (int)indices.get(conta) ) {
                //Pega a letra da palavra secreta na posição correta e salva na letra atual da palavra sendo adivinhada
                letrasDaPalavra[i] = forca.getPalavraSecreta().charAt((int)indices.get(conta));
                conta++;
            } else {
                //A letra atual é a mesma da que já havia na palavra sendo adivinhada (podendo ser ou traço ou letra)
                letrasDaPalavra[i] = forca.getPalavraSendoAdivinhada().charAt(i);
            }
        }
        forca.setPalavraSendoAdivinhada(new String (letrasDaPalavra));
    }
    
    public boolean letraClicada(char letra){
        //Armazena em um arraylist a ou as posições de onde estão a letra pressionada
        ArrayList<Integer> listaPosicaoDasLetras = forca.temLetraNaPalavra(letra);
        //Se a lista não estiver vazia(só para garantir) e o valor da posição 0 for maior do que 0
        //Já que se não houver a letra digitada na palavra, ele vai retornar -1
        if (!listaPosicaoDasLetras.isEmpty() && listaPosicaoDasLetras.get(0) >= 0 ) {
            revelaLetra(listaPosicaoDasLetras);
            //System.out.println("Tem na palavra!");
            return true;
        } else {
            return false;
        }
    }
    /**Separa as letras da palavra com um espaço
     * 
     * @param palavra - recebe a palavra a ser espaçada
     * @return a mesma palavra, agora com espaços
     */
    public String espacadorLetras(String palavra){
        palavra =  palavra.replaceAll(".(?=.)", "$0 "); 
        return palavra;
    }
    
    /**Substitui a palavra digitada por traços para exibir na tela
     * 
     * @param palavra - palavra digitada
     * @return palavra porém com underlines em lugar das letras
     */
    public String substituiPorTracos(String palavra){
        int conta = 0;
        for (char letra : palavra.toCharArray()) {
            palavra = palavra.replace(letra, '_');
            conta++;
        }
        return palavra;
    }    
}
