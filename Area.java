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
public class Area extends Perimetro {
    
    double aCirculo(){
        return Math.PI*Math.pow(this.getRadio(), 2)
    }
}
