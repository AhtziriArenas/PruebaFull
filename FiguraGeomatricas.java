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
public class FiguraGeomatricas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Perimetro p = new Perimetro();
        
        

        p.setRadio(1);
        System.out.println("El perimetro es: " +p.pCirculo());
        
        Perimetro p1 = new Perimetro(10.0);
        System.out.println("El perimetro es: "+p1.pCirculo());//Manda a trater los metodos get y set
        
        System.out.println("");

    }
    
}
