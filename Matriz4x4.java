package matriz4x4;

import java.util.Scanner;

/**
 *
 * @author alicia
 */
public class Matriz4x4 {

    public static void main(String[] args) {
 
        Scanner sn = new Scanner(System.in);
 
        int matriz[][] = new int[4][4];
        boolean salir = false;
        int opcion, fila, columna;
        boolean rellenado = false;
        //Menu
        do {
            System.out.println("\n MENU");
            System.out.println("1.Rellenar toda la Matriz");
            System.out.println("2.Sumar una fila");
            System.out.println("3.Sumar una columna");
            System.out.println("4.Sumar la diagonal principal");
            System.out.println("5.Sumar la diagonal inversa");
            System.out.println("6.Media de todos los valores de la Matriz");
            System.out.println("7.Salir \n");
            System.out.println("Elije una opcion: ");
            opcion = sn.nextInt();
 
            switch (opcion) {
                case 1:
 
                    rellenarMatriz(sn, matriz);
                    rellenado = true;
                    break;
                case 2:
 
                    if (rellenado) {
                        do {
                            System.out.println("Elige una fila: ");
                            fila = sn.nextInt();
 
                        } while (!(fila >= 0 && fila < matriz.length));
 
                        System.out.println("La suma de los valores de la fila " + fila
                                + " es: " + sumaFila(matriz, fila));
 
                    } else {
                        System.out.println("Debes rellenar la matriz");
                    }
 
                    break;
                case 3:
 
                    if (rellenado) {
                        do {
 
                            System.out.println("Elige una fila");
                            columna = sn.nextInt();
 
                        } while (!(columna >= 0 && columna < matriz.length));
 
                        System.out.println("La suma de los valores de la columna " + columna
                                + " es: " + sumaColumna(matriz, columna));
 
                    } else {
                        System.out.println("Debes rellenar la matriz");
                    }
 
                    break;
                case 4:
 
                    if (rellenado) {
 
                        System.out.println("La suma de la diagonal principal es: " + sumaDiagonalPrinc(matriz));
 
                    } else {
                        System.out.println("Debes rellenar la matriz");
                    }
 
                    break;
                case 5:
 
                    if (rellenado) {
 
                        System.out.println("La suma de la diagonal inversa es: " + sumaDiagonalInversa(matriz));
 
                    } else {
                        System.out.println("Debes rellenar la matriz");
                    }
 
                    break;
                case 6:
 
                    if (rellenado) {
 
                        System.out.println("La media de los valores de la matriz es de: " + media(matriz));
 
                    } else {
                        System.out.println("Debes rellenar la matriz");
                    }
 
                    break;
                case 7:
                    salir = true;
                    break;
                default:
                    System.out.println("Tienes que poner un valor entre 1 y 7");
 
            }
 
        } while (!salir);
 
        System.out.println("FIN");
 
    }
 
    /**
     * Rellena la matriz con valores insertados por el usuario
     *
     * @param sn
     * @param matriz
     */
    public static void rellenarMatriz(Scanner sn, int[][] matriz) {
 
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
 
                System.out.println("Escribe un numero en la posicion [" + (i+1) + "] [" + (j+1) + "]");
                matriz[i][j] = sn.nextInt();
 
            }
        }
 
    }
 
    /**
     * Suma los valores de una determinada fila
     *
     * @param matriz
     * @param fila
     * @return
     */
    public static int sumaFila(int[][] matriz, int fila) {
 
        int suma = 0;
 
        for (int j = 0; j < matriz.length; j++) {
            suma += matriz[fila][j];
        }
 
        return suma;
 
    }
 
    /**
     * Suma los valores de una determinada columna
     *
     * @param matriz
     * @param columna
     * @return
     */
    public static int sumaColumna(int[][] matriz, int columna) {
 
        int suma = 0;
 
        for (int i = 0; i < matriz.length; i++) {
            suma += matriz[i][columna];
        }
 
        return suma;
 
    }
 
    /**
     * Suma los valores de la diagonal principal de la matriz
     *
     * @param matriz
     * @return
     */
    public static int sumaDiagonalPrinc(int[][] matriz) {
 
        int suma = 0;
 
        for (int i = 0, j = 0; i < matriz.length; i++, j++) {
            suma += matriz[i][j];
        }
 
        return suma;
 
    }
 
    /**
     * Suma los valores de la diagonal inversa de la matriz
     *
     * @param matriz
     * @return
     */
    public static int sumaDiagonalInversa(int[][] matriz) {
 
        int suma = 0;
 
        for (int i = 0, j = 3; i < matriz.length; i++, j--) {
            suma += matriz[i][j];
        }
 
        return suma;
 
    }
 
    /**
     * Media de todos los elementos de la matriz
     *
     * @param matriz
     * @return
     */
    public static double media(int[][] matriz) {
 
        double suma = 0, media;
 
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                suma += matriz[i][j];
            }
        }
        media = suma / (matriz.length * matriz.length);
        return media;
 
    }
    
}
//modificacion Diana
public static double media(int[][] matriz){
	double suma = 0 ;
	 for (int i = 1; i < matriz.length; i ++ ) {
	 	//bla bla
	 	
	 }
}