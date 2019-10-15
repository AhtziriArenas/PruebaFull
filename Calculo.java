public class Calculo {
   int digito; 
   int resultado;
   int exponente;
   String numero;
   
   public Calculo(){ // contructores sin parametros 
    
    }
             public Calculo(String numero){ // construcor con un parametro, poner el numero binario
             this.numero = numero;
             
             }
            
   /*metodos gets*/

    public String getNumero() {
        return numero;
    }
    
    public int getResultado() {
        return resultado;
    }