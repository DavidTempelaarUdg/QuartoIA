package Quatro;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Usuari
 */
public class Player1 {
    private Tauler meutaulell;
    private int profundidatMinimax = 2;
    
    //Es marca quines peçes estan al taulell per saber si es poden donar o no
    int[] peces = new int[]{0,1,10,11,100,101,110,111,1000,1001,1010,1011,1100,1101,1110,1111};
    boolean[] pecesUsades = new boolean[16];
    
    private class Jugada {
        public int posX;
        public int posY;
        public int pecaDonar;

        public Jugada() {
            posX = -1;
            posY = -1;
            pecaDonar = -1;
        }
        
        public Jugada(int x, int y, int peca) {
            posX = x;
            posY = y;
            pecaDonar = peca;
        }
    }
    
    Player1(Tauler entrada){
        meutaulell = entrada;
    }
    
    private boolean fi(int[][] taulell) {
        //si ha acabat el joc retornar true altrament false

        if (guanya(taulell[0][0],taulell[0][1],taulell[0][2],taulell[0][3])){ return true; } 
        if (guanya(taulell[1][0],taulell[1][1],taulell[1][2],taulell[1][3])){ return true; }           
        if (guanya(taulell[2][0],taulell[2][1],taulell[2][2],taulell[2][3])){ return true; }
        if (guanya(taulell[3][0],taulell[3][1],taulell[3][2],taulell[3][3])){ return true; }
        if (guanya(taulell[0][0],taulell[1][0],taulell[2][0],taulell[3][0])){ return true; }   
        if (guanya(taulell[0][1],taulell[1][1],taulell[2][1],taulell[3][1])){ return true; }     
        if (guanya(taulell[0][2],taulell[1][2],taulell[2][2],taulell[3][2])){ return true; }
        if (guanya(taulell[0][3],taulell[1][3],taulell[2][3],taulell[3][3])){ return true; }
        if (guanya(taulell[0][0],taulell[1][1],taulell[2][2],taulell[3][3])){ return true; }       
        if (guanya(taulell[0][3],taulell[1][2],taulell[2][1],taulell[3][0])){ return true; }
        
        //fi de joc no queden caselles lliures ??
         for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if (taulell[i][j] == -1){
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean guanya(int p1, int p2, int p3, int p4){
        // 1 si es una combinacio guanyadora o si no
        if( p1==-1 || p2==-1 || p3==-1 || p4==-1){
            //hi ha algun dels 4 buit no podem guanyar
            return false;
        }else{
            //les 4 peçes tenen valor    

            //peça p1              
            int Color1=(int) (p1/1000);
            p1 = p1 - Color1 * 1000;
            int Forma1=(int) (p1%1000/100);
            p1 = p1 - Forma1 * 100;
            int Forat1=(int) (p1%1000/10);
            p1 = p1 - Forat1 * 10;
            int Tamany1=(int) (p1%1000/1);

            //peça p2
            int Color2=(int) (p2/1000);
            p2 = p2 - Color2 * 1000;
            int Forma2=(int) (p2%1000/100);
            p2 = p2 - Forma2 * 100;
            int Forat2=(int) (p2%1000/10);
            p2 = p2 - Forat2 * 10;
            int Tamany2=(int) (p2%1000/1);  

            //peça p3
            int Color3=(int) (p3/1000);
            p3 = p3 - Color3 * 1000;
            int Forma3=(int) (p3%1000/100);
            p3 = p3 - Forma3 * 100;
            int Forat3=(int) (p3%1000/10);
            p3 = p3 - Forat3 * 10;
            int Tamany3=(int) (p3%1000/1);

            //peça p4  
            int Color4=(int) (p4/1000);
            p4 = p4 - Color4 * 1000;
            int Forma4=(int) (p4%1000/100);
            p4 = p4 - Forma4 * 100;
            int Forat4=(int) (p4%1000/10);
            p4 = p4 - Forat4 * 10;
            int Tamany4=(int) (p4%1000/1); 

            int Color = Color1 + Color2 + Color3 + Color4;
            int Forma = Forma1 + Forma2 + Forma3 + Forma4;
            int Forat = Forat1 + Forat2 + Forat3 + Forat4;
            int Tamany = Tamany1 + Tamany2 + Tamany3 + Tamany4;

            return Color == 0 || Color == 4 || Forma==0 || Forma==4 || Forat==0 || Forat==4 || Tamany==0 || Tamany==4;
        }
    }
    
    private int heuristica(int[][] taulell){
        int valor=0;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                int zerosColorH = 0, zerosFormaH = 0, zerosForatH = 0, zerosTamanyH = 0;
                int unsColorH = 0, unsFormaH = 0, unsForatH = 0, unsTamanyH = 0;
                int zerosColorV = 0, zerosFormaV = 0, zerosForatV = 0, zerosTamanyV = 0;
                int unsColorV = 0, unsFormaV = 0, unsForatV = 0, unsTamanyV = 0;
                
                if(taulell[i][j]!=-1){
                    if(taulell[i][j]/1000==0){ zerosColorH++; } 
                    else if(taulell[i][j]/1000==1){ unsColorH++; }

                    if((taulell[i][j]%1000)/100==0){ zerosFormaH++; } 
                    else if((taulell[i][j]%1000)/100==1){ unsFormaH++; }

                    if((taulell[i][j]%100)/10==0){ zerosForatH++; } 
                    else if((taulell[i][j]%100)/10==1){ unsForatH++; }

                    if(taulell[i][j]%10==0){ zerosTamanyH++; } 
                    else if(taulell[i][j]%10==1){ unsTamanyH++; }
                }
                
                if(taulell[j][i]!=-1){
                    if(taulell[j][i]/1000==0){ zerosColorV++; } 
                    else if(taulell[j][i]/1000==1){ unsColorV++; }

                    if((taulell[j][i]%1000)/100==0){ zerosFormaV++; } 
                    else if((taulell[j][i]%1000)/100==1){ unsFormaV++; }

                    if((taulell[j][i]%100)/10==0){ zerosForatV++; } 
                    else if((taulell[j][i]%100)/10==1){ unsForatV++; }

                    if(taulell[j][i]%10==0){ zerosTamanyV++; } 
                    else if(taulell[j][i]%10==1){ unsTamanyV++; }
                }
                valor = valor + ((Math.abs(zerosColorH-unsColorH))*10) + ((Math.abs(zerosFormaH-unsFormaH))*10) 
                        + ((Math.abs(zerosForatH-unsForatH))*10) + ((Math.abs(zerosTamanyH-unsTamanyH))*10)
                        + ((Math.abs(zerosColorV-unsColorV))*10) + ((Math.abs(zerosForatV-unsForatV))*10) 
                        + ((Math.abs(zerosFormaV-unsFormaV))*10) + ((Math.abs(zerosTamanyV-unsTamanyV))*10);
            } 
        }
        
        for(int i=0;i<4;i++){
            int zerosColorDE = 0, zerosFormaDE = 0, zerosForatDE = 0, zerosTamanyDE = 0;
            int unsColorDE = 0, unsFormaDE = 0, unsForatDE = 0, unsTamanyDE = 0;
            int zerosColorDD = 0, zerosFormaDD = 0, zerosForatDD = 0, zerosTamanyDD = 0;
            int unsColorDD = 0, unsFormaDD = 0, unsForatDD = 0, unsTamanyDD = 0;
            
            if(taulell[i][i]!=-1){
                if(taulell[i][i]/1000==0){ zerosColorDE++; } 
                else if(taulell[i][i]/1000==1){ unsColorDE++; }

                if((taulell[i][i]%1000)/100==0){ zerosFormaDE++; } 
                else if((taulell[i][i]%1000)/100==1){ unsFormaDE++; }

                if((taulell[i][i]%100)/10==0){ zerosForatDE++; } 
                else if((taulell[i][i]%100)/10==1){ unsForatDE++; }

                if(taulell[i][i]%10==0){ zerosTamanyDE++; } 
                else if(taulell[i][i]%10==1){ unsTamanyDE++; }
            }

            if(taulell[i][3-i]!=-1){
                if(taulell[i][3-i]/1000==0){ zerosColorDD++; } 
                else if(taulell[i][3-i]/1000==1){ unsColorDD++; }

                if((taulell[i][3-i]%1000)/100==0){ zerosFormaDD++; } 
                else if((taulell[i][3-i]%1000)/100==1){ unsFormaDD++; }

                if((taulell[i][3-i]%100)/10==0){ zerosForatDD++; } 
                else if((taulell[i][3-i]%100)/10==1){ unsForatDD++; }

                if(taulell[i][3-i]%10==0){ zerosTamanyDD++; } 
                else if(taulell[i][3-i]%10==1){ unsTamanyDD++; }
            }
            valor = valor + ((Math.abs(zerosColorDE-unsColorDE))*10) + ((Math.abs(zerosFormaDE-unsFormaDE))*10) 
                        + ((Math.abs(zerosForatDE-unsForatDE))*10) + ((Math.abs(zerosTamanyDE-unsTamanyDE))*10)
                        + ((Math.abs(zerosColorDD-unsColorDD))*10) + ((Math.abs(zerosForatDD-unsForatDD))*10) 
                        + ((Math.abs(zerosFormaDD-unsFormaDD))*10) + ((Math.abs(zerosTamanyDD-unsTamanyDD))*10);
        }
        return valor;
    }
    
    private int minimax(int[][] taulell, int nivell, int pecaDonar, boolean esMax){
        
        if(nivell>=profundidatMinimax) return heuristica(taulell);
        else{
            ArrayList<Jugada> fills = new ArrayList<>();
            int mapPecaDonar  = -1;
            
            //GENEREM FILLS
            //Per a cada peça no usada
            for(int i=0; i<16; i++){
                if(peces[i]==pecaDonar){ pecesUsades[i] = true; mapPecaDonar = i;}
                else if(!pecesUsades[i]){
                    
                    //per a cada casella buida del taulell
                    for(int m=0; m<4; m++){
                        for(int n=0; n<4; n++){
                            if(taulell[m][n] == -1){
                                fills.add(new Jugada(m,n,peces[i]));
                            }
                        }
                    }   
                }
            }
            
            if(fills.isEmpty()) return heuristica(taulell);
            else{
                int heuristicaMillor;
                if(!esMax){ //MIN
                    heuristicaMillor = 100000;
                    
                    for(int i=0; i<fills.size(); i++){
                        
                        taulell[fills.get(i).posX][fills.get(i).posY] = pecaDonar;
                        pecesUsades[mapPecaDonar] = true;
                        
                        int valorJugFill = 100000;
                        if(fi(taulell)){valorJugFill = 0;}
                        else {valorJugFill = minimax(taulell, nivell+1, fills.get(i).pecaDonar , !esMax);}
                        if(valorJugFill<heuristicaMillor){ heuristicaMillor = valorJugFill; }

                        taulell[fills.get(i).posX][fills.get(i).posY] = -1;
                        pecesUsades[mapPecaDonar] = false;
                    }
                }
                else{//MAX
                    heuristicaMillor = 0;
                    
                    for(int i=0; i<fills.size(); i++){
                        
                        taulell[fills.get(i).posX][fills.get(i).posY] = pecaDonar;
                        pecesUsades[mapPecaDonar] = true;

                        int valorJugFill = 0;
                        if(fi(taulell)){valorJugFill = 100000;}  
                        else{valorJugFill = minimax(taulell, nivell+1, fills.get(i).pecaDonar , !esMax);}
                        if(valorJugFill>heuristicaMillor){ heuristicaMillor = valorJugFill; }
                            
                        taulell[fills.get(i).posX][fills.get(i).posY] = -1;
                        pecesUsades[mapPecaDonar] = false;
                    }
                }
                return heuristicaMillor;
            }            
        }
    }
    
    public int[] tirada(int colorin, int formain, int foratin, int tamanyin){
     //colorin - Color de la peça a colocar -> 	0 = Blanc 	1 = Negre
     //formain - Forma de la peça a colocar -> 	0 = Rodona 	1 = Quadrat
     //foratin - Forat de la peça a colocar -> 	0 = No  	1 = Si
     //tamanyin - Forat de la peça a colocar -> 0 = Petit 	1 = Gran

        int pecaRebuda = colorin*1000 + formain*100 + foratin*10 + tamanyin;
        
        //Es miren les peces que ja s'han utilitzat i si és cert, les marquem
        int pecesFaltants = 16;
        for(int i=0;i<16;i++){
            
            if(peces[i]==pecaRebuda){ pecesUsades[i] = true; pecesFaltants--;}
            else{
                boolean trobat = false;
                //es fa i<4 ja que per saber les dimensions hauriem de cridar una funcion 
                //dimensio de la classe meuTaulell que no existeix i no s'ha volgut tocar
                for(int m=0;m<4;m++){
                    for(int n=0;n<4;n++){
                        if(meutaulell.getpos(m,n) == peces[i]) trobat=true;
                        if(trobat) break;
                    }
                    if(trobat) break;
                }
                if(trobat){ pecesUsades[i] = true; pecesFaltants--;}
            }
        }
        
        if(pecesFaltants==0){ //només queda una peca per completar el tauler
            int x=-1, y=-1;
            for(int m=0;m<4;m++){
                for(int n=0;n<4;n++){
                    if(meutaulell.getpos(m,n) == -1){x=m; y=n;}
                }
            }
            return new int[]{x,y,-1,-1,-1,-1};
        }
        else{
            
            //Estudi de la millor jugada possible (posar la peça donada i triar peça per donar)
            int millorHeuristica = -1000;
            Jugada millorJugada = new Jugada();     

            int[][] taulell = meutaulell.getTaulell();

            //es fa i<4 ja que per saber les dimensions hauriem de cridar una funcion 
            //de la classe meuTaulell que no existeix i no s'ha volgut tocar
            //Per totes les caselles buides apliquem el minimax
            for(int i=0;i<4;i++){ 
                for(int j=0;j<4;j++){
                    //Mirem si la casella és buida
                    if(taulell[i][j] == -1){

                        //Provem totes les peçes que podem donar al contrincant
                        for(int k=0;k<16;k++){
                            if(!pecesUsades[k]){

                                //Posem la peça en la casella
                                taulell[i][j] = pecaRebuda;

                                int valorJugada = minimax(taulell,0,peces[k], false);

                                //Treiem la peça de la casella
                                taulell[i][j] = -1;

                                if(valorJugada>millorHeuristica){
                                    millorHeuristica = valorJugada;
                                    millorJugada.posX = i;
                                    millorJugada.posY = j;
                                    millorJugada.pecaDonar = peces[k];
                                }
                            }
                        }
                    }
                }
            }

            int colorout=(int) (millorJugada.pecaDonar/1000);
            int formaout=(int) (millorJugada.pecaDonar%1000/100);
            int foratout=(int) (millorJugada.pecaDonar%100/10);
            int tamanyout=(int) (millorJugada.pecaDonar%10);

            if(millorHeuristica!=-1000) return new int[]{millorJugada.posX,millorJugada.posY,colorout, formaout, foratout, tamanyout}; 

            //Un retorn per defecte
            return new int[]{0,0,0,0,0,0};
            //format del retorn vector de 6 int {posX[0a3], posY[0a3], color[0o1] forma[0o1], forat[0o1], tamany[0o1]}
            //posX i posY es la posicio on es coloca la peça d'entrada
            //color forma forat i tamany descriuen la peça que colocara el contrari
            //color -  	0 = Blanc 	1 = Negre
            //forma -  	0 = Rodona 	1 = Quadrat
            //forat - 	0 = No  	1 = Si
            //tamany -      0 = Petit 	1 = Gran
        }
    }
}
