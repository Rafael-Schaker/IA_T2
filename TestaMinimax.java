public class TestaMinimax
{
    private char[][] velha;
    
    public TestaMinimax(int tabuleiroVelha[][]){   //Formato e base para o jogo da velha
        velha = new char[3][3];
        for(int i=0; i<3;i++) 
            for(int j=0; j<3;j++)
                if(tabuleiroVelha[i][j]==-1) velha[i][j]='#';
                else if(tabuleiroVelha[i][j]==1) velha[i][j]='X';
                     else velha[i][j]='O';
    }
    
    //Indica a dificuldade do jogo
    public Sucessor joga(String dificuldade){
        Minimax mini = new Minimax(velha,dificuldade); // 
        Sucessor melhor = mini.getMelhor(); //chama versão clássica
        //return mini.getMelhorAB(); //chama versão Alfa Beta Pruning
        return melhor;
    }    
    
    public String toString(){
        String saida=" ----- Jogo da Velha -----\n";
        for(int i=0; i<3; i++) saida = saida + "\t"+i;
        saida = saida + "\n";
        for(int i=0; i<3; i++){
            saida = saida + i +"\t";
            for(int j=0; j<3;j++) saida = saida + velha[i][j]+"\t";
            saida = saida + "\n";
        }
        return saida;
    }
}