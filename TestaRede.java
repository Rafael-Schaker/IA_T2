
/**
 * Escreva a descrição da classe TestaRede aqui.
 * 
 * @author Silvia
 * @version 12/11/2020
 */
import java.util.Random;
import java.util.Scanner;

public class TestaRede {
    private double[] tabuleiro;
    private int[][] tabuleiroVelha;
    private Rede rn;
    private int qntErros;

    public TestaRede() {

    }

    public Tabuleiro joga(double cromossomo[],String dificuldade,boolean treino) {
        // ------------------------ EXEMPLO DE TABULEIRO
        // ------------------------------------------
        // tabuleiro do jogo da velha - Exemplo de teste
        tabuleiroVelha = new int[][] { { -1, -1, -1 }, // -1: celula livre 1: X 0: O
                { -1, -1, -1 },
                { -1, -1, -1 } };

        System.out.println("\f\nTabuleiro inicial: ");// Print do jogo reiniciado
        for (int i = 0; i < tabuleiroVelha.length; i++) {
            for (int j = 0; j < tabuleiroVelha.length; j++) {
                    System.out.print(" ");
                
                
                if (j < tabuleiroVelha[i].length - 1) {
                    System.out.print(" | ");
                }
                
            }
            System.out.println();
            if (i < tabuleiroVelha.length - 1) {
                System.out.println("--+---+--");
            }
        }

        // tabuleiro de teste - conversao de matriz para vetor
        tabuleiro = new double[tabuleiroVelha.length * tabuleiroVelha.length];
        int k = 0;
        for (int i = 0; i < tabuleiroVelha.length; i++) {
            for (int j = 0; j < tabuleiroVelha.length; j++) {
                tabuleiro[k] = tabuleiroVelha[i][j];
                k++;
            }
        }

        // Gerando a rede ------------------------------
        int oculta = 9; // neuronios da camada oculta
        int saida = 9; // neuronios da camada de saida
        rn = new Rede(oculta, saida); // topologia da rede: 9 neurônios na camada oculta e 9 na de saída

        // Simulando um cromossomo da populacao do AG
        Random gera = new Random();
        System.out.println("tamanho do cromossomo: " + cromossomo.length);

        for (int i = 0; i < cromossomo.length; i++) {
            cromossomo[i] = gera.nextDouble();
            if (gera.nextBoolean())
                cromossomo[i] = cromossomo[i] * -1;
        }

        rn.setPesosNaRede(tabuleiro.length, cromossomo); //Colocando os pesos na rede

        System.out.println();
        System.out.println("Rede Neural:" + rn);

        // Execução da AG --------------------------------------

        int n = 0;
        while (true) {
            System.out.println("\n --> RODADA: " + n);
            // Exibe um exemplo de propagação : saida dos neurônios da camada de saída
            double[] saidaRede = rn.propagacao(tabuleiro);
            System.out.println("\n Rede Neural -> Jogador1 = X");
             // informação sobre cada neurônio
                for (int i = 0; i < saidaRede.length; i++) {
                    System.out.println("Neuronio " + i + " : " + saidaRede[i]);
                }

            // Define posicao a jogar de acordo com rede
            int indMaior = 0;
            double saidaMaior = saidaRede[0];
            for (int i = 1; i < saidaRede.length; i++) {
                if (saidaRede[i] > saidaMaior) {
                    saidaMaior = saidaRede[i];
                    indMaior = i;
                }
            }
            int linha = indMaior / 3;
            int coluna = indMaior % 3;
            System.out.println("Neuronio de maior valor: " + indMaior + " - " + saidaRede[indMaior]);
            System.out.println("--> Rede escolheu - Linha: " + linha + " Coluna: " + coluna);

            while (tabuleiroVelha[linha][coluna] != -1) {//caso escolha errado é penalisado e entre em um loop até conseguir encontrar um local
                System.out.println("Posicao ocupada");
                qntErros++;
                // Refaz o cálculo para encontrar um espaço vazio
                indMaior++;
                if (indMaior >= saidaRede.length) {
                    // Indíce ultrapassou o tamanho máximo, volte ao início do tabuleiro
                    indMaior = 0;
                }
                linha = indMaior / 3;
                coluna = indMaior % 3;
                System.out.println("Neuronio de maior valor: " + indMaior + " - " + saidaRede[indMaior]);
                System.out.println("--> Rede escolheu - Linha: " + linha + " Coluna: " + coluna);
            }
            tabuleiroVelha[linha][coluna] = 1;

            System.out.println("\nTabuleiro atual: ");
            for (int i = 0; i < tabuleiroVelha.length; i++) {
                for (int j = 0; j < tabuleiroVelha.length; j++) {
                        
                    if (tabuleiroVelha[i][j] == 0){
                        System.out.print("X");
                    } else if(tabuleiroVelha[i][j] == 1){
                        System.out.print("O");
                    }else{
                        System.out.print(" ");
                    }
                    if (j < tabuleiroVelha[i].length - 1) {
                        System.out.print(" | ");
                    }       
                    }
                    System.out.println();
                    if (i < tabuleiroVelha.length - 1) {
                        System.out.println("--+---+--");
                    }
                }
            

            // Verifica se há vencedor
            if (verificaVitoria(tabuleiroVelha)) {
                System.out.println("\nVitória da Rede Neural!!!");
                return new Tabuleiro(tabuleiroVelha, false,qntErros);
            }

            // Verifica se há empate
            if (verificaEmpate(tabuleiroVelha)) {
                System.out.println("\nEmpate");
                return new Tabuleiro(tabuleiroVelha, false,qntErros);
            }

            // Minimax jogando -----------------------------
            if(treino){
                int linhaMinimax, colunaMinimax = 0;
                TestaMinimax mini = new TestaMinimax(tabuleiroVelha);
                Sucessor melhor = mini.joga(dificuldade);
                linhaMinimax = melhor.getLinha();
                colunaMinimax = melhor.getColuna();
                
                System.out.println("\n MINIMAX -> Jogardor2 = X");
                System.out.println("--> MINIMAX escolheu - Linha: " + linhaMinimax + " Coluna: " + colunaMinimax);

                while (tabuleiroVelha[linhaMinimax][colunaMinimax] != -1) {
                    System.out.println("Posicao ocupada");
                    mini = new TestaMinimax(tabuleiroVelha);
                    melhor = mini.joga(dificuldade);
                    linhaMinimax = melhor.getLinha();
                    colunaMinimax = melhor.getColuna();
                    //return new Tabuleiro(tabuleiroVelha, false,qntErros);
                } 
                tabuleiroVelha[linhaMinimax][colunaMinimax] = 0;

                System.out.println("\nTabuleiro atual: ");
                for (int i = 0; i < tabuleiroVelha.length; i++) {
                    for (int j = 0; j < tabuleiroVelha.length; j++) {

                        if (tabuleiroVelha[i][j] == 1){
                        System.out.print("O");
                        } else if(tabuleiroVelha[i][j] == 0){
                            System.out.print("X");
                        }else{
                            System.out.print(" ");
                        }
                        if (j < tabuleiroVelha[i].length - 1) {
                            System.out.print(" | ");
                        }  
                    }
                    System.out.println();
                    if (i < tabuleiroVelha.length - 1) {
                    System.out.println("--+---+--");
                    }
                }
                
                // Verifica se há vencedor
                if (verificaVitoria(tabuleiroVelha)) {
                    System.out.println("Vitória do Minimax");
                    return new Tabuleiro(tabuleiroVelha, false,qntErros);
                }
                // Verifica se há empate
                if (verificaEmpate(tabuleiroVelha)) {
                    System.out.println("Empate");
                    return new Tabuleiro(tabuleiroVelha, false,qntErros);
                }
                n++;
                // tabuleiro de teste - conversao de matriz para vetor
                k = 0;
                for (int i = 0; i < tabuleiroVelha.length; i++) {
                    for (int j = 0; j < tabuleiroVelha.length; j++) {
                        tabuleiro[k] = tabuleiroVelha[i][j];
                        k++;
                    }
                }

            }else{
                Scanner scanner = new Scanner(System.in);
                System.out.println("\n Usuario -> Jogardor2 = X");
                while (true) {
                    System.out.print("Jogador, escolha a linha (0-2): ");
                    linha = scanner.nextInt();

                    System.out.print("Jogador, escolha a coluna (0-2): ");
                    coluna = scanner.nextInt();

                    if (linha < 0 || linha > 2 || coluna < 0 || coluna > 2) {
                        System.out.println("Posição inválida. Escolha novamente.");
                        continue;
                    }

                    if (tabuleiroVelha[linha][coluna] != -1) {
                        System.out.println("Posição ocupada. Escolha novamente.");
                        continue;
                    }
                    break;

                }
                System.out.println("--> Usuario escolheu - Linha: " + linha + " Coluna: " + coluna);   
                tabuleiroVelha[linha][coluna] = 0;

                System.out.println("\nTabuleiro atual: ");
                for (int i = 0; i < tabuleiroVelha.length; i++) {
                    for (int j = 0; j < tabuleiroVelha.length; j++) {

                        if (tabuleiroVelha[i][j] == 1){
                        System.out.print("O");
                        } else if(tabuleiroVelha[i][j] == 0){
                            System.out.print("X");
                        }else{
                            System.out.print(" ");
                        }
                        if (j < tabuleiroVelha[i].length - 1) {
                            System.out.print(" | ");
                        }  
                    }
                    System.out.println();
                    if (i < tabuleiroVelha.length - 1) {
                    System.out.println("--+---+--");
                    }
                }
                // Verifica se há vencedor
                if (verificaVitoria(tabuleiroVelha)) {
                    System.out.println("Vitória do Usuario");
                    return new Tabuleiro(tabuleiroVelha, false,qntErros);
                }
                // Verifica se há empate
                if (verificaEmpate(tabuleiroVelha)) {
                    System.out.println("Empate");
                    return new Tabuleiro(tabuleiroVelha, false,qntErros);
                }
                n++;
                // tabuleiro de teste - conversao de matriz para vetor
                k = 0;
                for (int i = 0; i < tabuleiroVelha.length; i++) {
                    for (int j = 0; j < tabuleiroVelha.length; j++) {
                        tabuleiro[k] = tabuleiroVelha[i][j];
                        k++;
                    }
                }
            }
        }
    }
   

    public static boolean verificaVitoria(int[][] tabuleiro) {
        // Verificação das linhas
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0] == tabuleiro[i][1] && tabuleiro[i][1] == tabuleiro[i][2] && tabuleiro[i][0] != -1) {
                return true;
            }
        }

        // Verificação das colunas
        for (int j = 0; j < 3; j++) {
            if (tabuleiro[0][j] == tabuleiro[1][j] && tabuleiro[1][j] == tabuleiro[2][j] && tabuleiro[0][j] != -1) {
                return true;
            }
        }

        // Verificação das diagonais
        if ((tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][2] && tabuleiro[0][0] != -1)
                || (tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][0]
                        && tabuleiro[0][2] != -1)) {
            return true;
        }

        // Nenhuma vitória encontrada
        return false;
    }

    public static boolean verificaEmpate(int[][] tabuleiro) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == -1) {
                    return false; // Ainda há uma posição vazia, não é empate
                }
            }
        }
        return true; // Todas as posições estão preenchidas, é empate
    }

    public static void main(String args[]) {
        TestaRede teste = new TestaRede();
    }
}
