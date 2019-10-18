/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package figurageomatricas;

/**
 *
 * @author Orlando
 */
public class Perimetro {
    
    private int nlados;
    private double tam;
    private double radio;

    public Perimetro(int nlados, double tam, double radio) {
        this.nlados = nlados;
        this.tam = tam;
        this.radio = radio;
    }
    
    /*Metodos get*/
    public int getNlados(){
        return nlados;
    }

    public double getTam() {
        return tam;
    }

    public double getRadio() {
        return radio;
    }
    
    /*Metodo set*/

    public void setNlados(int nlados) {
        this.nlados = nlados;
    }

    public void setTam(double tam) {
        this.tam = tam;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }
    
    double pCirculo(){
        return Math.PI*2*radio;
    }
    
    double pCirculo(double radio){
    return 0;
    }
    
    
}
