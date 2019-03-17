/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Quatro;

import java.util.Scanner;

/**
 *
 * @author David
 */
public class JugadorFisic {
    
    private Tauler meutaulell;
    
    JugadorFisic(Tauler entrada){
        meutaulell = entrada;
    }
    
    
    public int[] tirada(int colorin, int formain, int foratin, int tamanyin){
     //colorin - Color de la peça a colocar -> 	0 = Blanc 	1 = Negre
     //formain - Forma de la peça a colocar -> 	0 = Rodona 	1 = Quadrat
     //foratin - Forat de la peça a colocar -> 	0 = No  	1 = Si
     //tamanyin - Forat de la peça a colocar -> 0 = Petit 	1 = Gran
        
        System.out.println("T'han donat la peça: "+colorin+formain+foratin+tamanyin);
        System.out.println("Entra la posició del tauler on el vols ficar (en format (x,y)): ");
        
        int[][] taulell = meutaulell.getTaulell();
        
        Scanner read = new Scanner(System.in);
        int x = -1;
        int y = -1;
        
        boolean correcte = false;
        while(!correcte){
            x = read.nextInt();
            y = read.nextInt();
            if(x>3 || y>3){System.out.println("La posició entrada no existeix, torna a provar-ho");}
            else if(taulell[x][y]!=-1){System.out.println("Aquesta posició no està buida, torna a provar-ho");}
            else{ correcte = true; }
        }
        
        System.out.println("Un cop posat la peça el taulell queda de la seguent manera:");
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if (taulell[i][j]==-1){System.out.print("---- ");} 
                else {System.out.print(colorin+formain+foratin+tamanyin+" ");}
            }
            System.out.println("");
        }

        System.out.println("Ara tria la peça que vols donar (format xxxx):");
        
        Scanner peca = new Scanner(System.in);
        int p = peca.nextInt();
        
        int colorout=(int) (p/1000);
        int formaout=(int) (p%1000/100);
        int foratout=(int) (p%100/10);
        int tamanyout=(int) (p%10);
        
        return new int[]{x,y,colorout, formaout, foratout, tamanyout}; 

        
        //Un retorn per defecte
        //return new int[]{0,0,0,0,0,0};
        //format del retorn vector de 6 int {posX[0a3], posY[0a3], color[0o1] forma[0o1], forat[0o1], tamany[0o1]}
        //posX i posY es la posicio on es coloca la peça d'entrada
        //color forma forat i tamany descriuen la peça que colocara el contrari
        //color -  	0 = Blanc 	1 = Negre
        //forma -  	0 = Rodona 	1 = Quadrat
        //forat - 	0 = No  	1 = Si
        //tamany -      0 = Petit 	1 = Gran
    }
}
