public class Heuristica {

    public Heuristica() {
    }

    public int avaliarMovimento(int[][] tabuleiro, int jogador, boolean isEmpate, int qntErros) {
        int pontuacao = 0;

        if (venceu(tabuleiro, jogador)) {
            pontuacao += 200; // Caso de vitória
        } else if (venceu(tabuleiro, getOponente(jogador))) {
            pontuacao -= 70; // Caso de derrota
        } else {
            // Caso de empate - não recebe nem perde, tentou o seu melhor
            pontuacao += avaliarMovimentosDeBloqueio(tabuleiro, jogador);
            pontuacao += avaliarMovimentosDeOportunidade(tabuleiro, jogador);
            pontuacao += avaliarVantagemPosicional(tabuleiro, jogador);
            double percentualReducao = 0.1; // Ajuste o valor conforme necessário
            pontuacao -= (int) (qntErros * percentualReducao);
            pontuacao += avaliarRodadas(tabuleiro);
        }

        return pontuacao;
    }

    private int avaliarRodadas(int[][] tabuleiro) {
        int qntRodadas = 0;

        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro.length; j++) {
                if (tabuleiro[i][j] != -1) {
                    qntRodadas++;
                }
            }
        }

        return qntRodadas * 10; // Multiplica a quantidade de rodadas jogadas por 10
    }

    private int avaliarMovimentosDeBloqueio(int[][] tabuleiro, int jogador) {
        int pontuacao = 0;
        int oponente = getOponente(jogador);

        // Verificar linhas em busca de possíveis jogadas vencedoras para o oponente
        for (int i = 0; i < 3; i++) {
            if (temPossivelVitoria(tabuleiro[i], oponente)) {
                pontuacao += 10;
            }
        }

        // Verificar colunas em busca de possíveis jogadas vencedoras para o oponente
        for (int j = 0; j < 3; j++) {
            int[] coluna = { tabuleiro[0][j], tabuleiro[1][j], tabuleiro[2][j] };
            if (temPossivelVitoria(coluna, oponente)) {
                pontuacao += 10;
            }
        }

        // Verificar diagonais em busca de possíveis jogadas vencedoras para o oponente
        int[] diagonal1 = { tabuleiro[0][0], tabuleiro[1][1], tabuleiro[2][2] };
        int[] diagonal2 = { tabuleiro[0][2], tabuleiro[1][1], tabuleiro[2][0] };
        if (temPossivelVitoria(diagonal1, oponente)) {
            pontuacao += 10;
        }
        if (temPossivelVitoria(diagonal2, oponente)) {
            pontuacao += 10;
        }

        return pontuacao;
    }

    private int avaliarMovimentosDeOportunidade(int[][] tabuleiro, int jogador) {
        int pontuacao = 0;

        // Avaliar possíveis jogadas vencedoras para o jogador
        pontuacao += contarPossiveisVitorias(tabuleiro, jogador) * 5;

        return pontuacao;
    }

    private int avaliarVantagemPosicional(int[][] tabuleiro, int jogador) {
        int pontuacao = 0;

        // Atribuir pontuações mais altas a posições estratégicas
        if (tabuleiro[1][1] == jogador) {
            pontuacao += 5; // Posição central
        }
        if (tabuleiro[0][0] == jogador || tabuleiro[0][2] == jogador ||
                tabuleiro[2][0] == jogador || tabuleiro[2][2] == jogador) {
            pontuacao += 3; // Posições de canto
        }

        return pontuacao;
    }

    private boolean temPossivelVitoria(int[] linha, int jogador) {
        int contagemJogador = 0;
        int contagemVazio = 0;

        for (int celula : linha) {
            if (celula == jogador) {
                contagemJogador++;
            } else if (celula == -1) {
                contagemVazio++;
            }
        }

        return contagemJogador == 2 && contagemVazio == 1;
    }

    private int contarPossiveisVitorias(int[][] tabuleiro, int jogador) {
        int contagem = 0;

        // Contar possíveis jogadas vencedoras em linhas
        for (int i = 0; i < 3; i++) {
            if (temPossivelVitoria(tabuleiro[i], jogador)) {
                contagem++;
            }
        }

        // Contar possíveis jogadas vencedoras em colunas
        for (int j = 0; j < 3; j++) {
            int[] coluna = { tabuleiro[0][j], tabuleiro[1][j], tabuleiro[2][j] };
            if (temPossivelVitoria(coluna, jogador)) {
                contagem++;
            }
        }

        // Contar possíveis jogadas vencedoras em diagonais
        int[] diagonal1 = { tabuleiro[0][0], tabuleiro[1][1], tabuleiro[2][2] };
        int[] diagonal2 = { tabuleiro[0][2], tabuleiro[1][1], tabuleiro[2][0] };
        if (temPossivelVitoria(diagonal1, jogador)) {
            contagem++;
        }
        if (temPossivelVitoria(diagonal2, jogador)) {
            contagem++;
        }

        return contagem;
    }

    private boolean venceu(int[][] tabuleiro, int jogador) {
        // Verificar linhas
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0] == jogador && tabuleiro[i][1] == jogador && tabuleiro[i][2] == jogador) {
                return true;
            }
        }

        // Verificar colunas
        for (int j = 0; j < 3; j++) {
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

    private int getOponente(int jogador) {
        return (jogador == 1) ? 0 : 1;
    }
}
