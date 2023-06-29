import java.util.Random;

public class JogoVelhaAG {
    static int TAMANHO_TABULEIRO = 3;
    static int TAMANHO_POPULACAO = 10;
    
    public static void main(String[] args) {
        // População inicial
        int[][][] populacao = gerarPopulacaoInicial();
        
        // Exemplo: tabuleiro vazio
        int[][] tabuleiro = new int[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
        
        // Escolha aleatória do jogador inicial
        Random rand = new Random();
        boolean jogadorX = rand.nextBoolean();
        
        // Loop do jogo
        boolean jogoFinalizado = false;
        while (!jogoFinalizado) {
            // Exibir tabuleiro
            exibirTabuleiro(tabuleiro);
            
            // Verificar o turno do jogador
            if (jogadorX) {
                // Jogador X (Usuário)
                int posicao = escolherPosicaoUsuario(tabuleiro);
                realizarJogada(tabuleiro, posicao, 1); // 1 representa o jogador X
            } else {
                // Jogador O (Algoritmo Genético)
                int posicao = escolherPosicaoAG(tabuleiro, populacao);
                realizarJogada(tabuleiro, posicao, 2); // 2 representa o jogador O (Algoritmo Genético)
            }
            
            // Verificar se o jogo terminou
            if (verificarVitoria(tabuleiro, 1)) {
                System.out.println("O jogador X (Usuário) venceu!");
                jogoFinalizado = true;
            } else if (verificarVitoria(tabuleiro, 2)) {
                System.out.println("O jogador O (Algoritmo Genético) venceu!");
                jogoFinalizado = true;
            } else if (verificarEmpate(tabuleiro)) {
                System.out.println("Empate!");
                jogoFinalizado = true;
            }
            
            // Alternar o jogador
            jogadorX = !jogadorX;
        }
    }
    
    private static int[][][] gerarPopulacaoInicial() {
        int[][][] populacao = new int[TAMANHO_POPULACAO][TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
        // Preencher a população com estratégias de jogadas
        // Cada elemento da população é uma matriz representando uma estratégia
        // Exemplo: populacao[0] = {{0, 1, 0}, {1, 0, 0}, {0, 0, 0}} representa uma estratégia
        // onde o jogador O (Algoritmo Genético) jogará nas posições (0, 1) e (1, 0)
        // Você pode definir manualmente as estratégias ou utilizar algum método de geração aleatória
        return populacao;
    }
    
    private static void exibirTabuleiro(int[][] tabuleiro) {
        System.out.println("Tabuleiro:");
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                if (tabuleiro[i][j] == 0) {
                    System.out.print("- ");
                } else if (tabuleiro[i][j] == 1) {
                    System.out.print("X ");
                } else if (tabuleiro[i][j] == 2) {
                    System.out.print("O ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    private static int escolherPosicaoUsuario(int[][] tabuleiro) {
        // Exemplo: escolha aleatória de uma posição vazia pelo usuário
        Random rand = new Random();
        int linha, coluna;
        do {
            linha = rand.nextInt(TAMANHO_TABULEIRO);
            coluna = rand.nextInt(TAMANHO_TABULEIRO);
        } while (tabuleiro[linha][coluna] != 0); // Verificar se a posição está vazia
        return linha * TAMANHO_TABULEIRO + coluna; // Retornar a posição convertida para um número único
    }
    
    private static int escolherPosicaoAG(int[][] tabuleiro, int[][][] populacao) {
        // Exemplo: escolha aleatória de uma estratégia de jogada do Algoritmo Genético
        Random rand = new Random();
        int indiceEstrategia = rand.nextInt(TAMANHO_POPULACAO);
        
        int[][] estrategia = populacao[indiceEstrategia];
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                if (estrategia[i][j] == 1 && tabuleiro[i][j] == 0) {
                    return i * TAMANHO_TABULEIRO + j; // Retornar a posição convertida para um número único
                }
            }
        }
        
        // Se não encontrou uma posição válida na estratégia selecionada, escolhe uma posição aleatória
        return escolherPosicaoUsuario(tabuleiro);
    }
    
    private static void realizarJogada(int[][] tabuleiro, int posicao, int jogador) {
        int linha = posicao / TAMANHO_TABULEIRO;
        int coluna = posicao % TAMANHO_TABULEIRO;
        tabuleiro[linha][coluna] = jogador;
    }
    
    private static boolean verificarVitoria(int[][] tabuleiro, int jogador) {
        // Verificar linhas
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            if (tabuleiro[i][0] == jogador && tabuleiro[i][1] == jogador && tabuleiro[i][2] == jogador) {
                return true;
            }
        }
        
        // Verificar colunas
        for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
            if (tabuleiro[0][j] == jogador && tabuleiro[1][j] == jogador && tabuleiro[2][j] == jogador) {
                return true;
            }
        }
        
        // Verificar diagonais
        if (tabuleiro[0][0] == jogador && tabuleiro[1][1] == jogador && tabuleiro[2][2] == jogador) {
            return true;
        }
        if (tabuleiro[0][2] == jogador && tabuleiro[1][1] == jogador && tabuleiro[2][0] == jogador) {
            return true;
        }
        
        return false;
    }
    
    private static boolean verificarEmpate(int[][] tabuleiro) {
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                if (tabuleiro[i][j] == 0) {
                    return false; // Ainda há posições vazias no tabuleiro
                }
            }
        }
        return true; // Todas as posições estão preenchidas (empate)
    }
}
