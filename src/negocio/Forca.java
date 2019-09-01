/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.ArrayList;

/**
 *
 * @author marcus.vasconcelos
 */
public class Forca {    
    private String palavraSecreta;
    private String palavraSendoAdivinhada;
    private final int limiteTentativas = 6;
    private int qtdeErrosCometidos = 0;
    private int qtdeLetrasAdivinhadas;
    private ArrayList<Integer> indicePosicaoDasLetras = new ArrayList<>();
   
    
    public void setPalavraSecreta(String palavra){
        this.palavraSecreta = palavra;
        this.palavraSendoAdivinhada = palavra;
    }
    
    public int getQtdeLetrasAdivinhadas(){
        return qtdeLetrasAdivinhadas;
    }
    
    public void resetQtdeLetrasAdivinhadas(){
        qtdeLetrasAdivinhadas = 0;
    }
    
    public String getPalavraSecreta(){
        return palavraSecreta;
    }
    
    public int getLimiteTentativas(){
        return limiteTentativas;
    }
    
    public int getQtdeErrosCometidos(){
        return qtdeErrosCometidos;
    }
    
    public void aumentaQtdeErrosCometidos(){
        qtdeErrosCometidos++;
    }
    
    public void resetQtdeErrosCometidos(){
        this.qtdeErrosCometidos = 0;
    }
    
    public String getPalavraSendoAdivinhada(){
        return palavraSendoAdivinhada;
    }
    public void setPalavraSendoAdivinhada(String palavra){
        this.palavraSendoAdivinhada = palavra;
    }
    
    public ArrayList temLetraNaPalavra(char letra){
        int indice = 0;
        //Limpa o arrayList
        indicePosicaoDasLetras.removeAll(indicePosicaoDasLetras);
        //Percorre a palavra
        for (char letras : palavraSecreta.toCharArray()) {
            //Se ouver a letra, adiciona ela ao índice
            if (letra == letras) {
                qtdeLetrasAdivinhadas++;
                indicePosicaoDasLetras.add(indice);
            }
            indice++;
        }
        //Se a lista estiver vazia é porque não há letras
        if (indicePosicaoDasLetras.isEmpty()) {
            indicePosicaoDasLetras.add(-1);
        }
        return indicePosicaoDasLetras;            
    }
    
}
