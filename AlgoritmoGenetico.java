import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class AlgoritmoGenetico {
    static int numGeracoes = 30;
    static int qntCromossomos = 4;
    static int qntPesos = 180;
    public static void main(String[] args,String dificuldade) {
        
        double populacaoInicial[][] = gerarPopulacaoInicial();
        double melhorPopulacao[] = null;
        double melhorAptidao = Double.NEGATIVE_INFINITY;

        for (int g = 0; g < numGeracoes; g++) {
            System.out.println("Numero da Geracao:"+g);
            // Joga o jogo da velha
            for (int i = 0; i < qntCromossomos; i++) {
                Tabuleiro Tabuleiro = new TestaRede().joga(populacaoInicial[i],dificuldade);
                populacaoInicial[i][qntPesos] = calcularAptidao(Tabuleiro);
                // Verifica se a aptidão atual é a melhor encontrada até agora
                if (populacaoInicial[i][qntPesos] > melhorAptidao) {
                    melhorAptidao = populacaoInicial[i][qntPesos];
                    melhorPopulacao = populacaoInicial[i];
                }
            }
            System.out.println("Melhor Aptidão: " + melhorAptidao);
            // Apresenta a melhor população
            //System.out.println("Melhor População: ");
            //imprimirPopulacao(melhorPopulacao);
            // Salvando a melhor população em um arquivo
            salvarMelhorPopulacao(melhorPopulacao, "melhor_populacao.txt");


            double[] elitismo = elitismo(populacaoInicial);
            System.out.println("Aptidão atual:"+ elitismo[elitismo.length - 1]);
            System.out.println("Fim da Geracao:"+g);
            System.out.println("-------------------------------------------");
        

        }
    }
    
    private static void imprimirPopulacao(double[] populacao) {
        for (int i = 0; i < qntPesos; i++) {
            System.out.print(populacao[i] + " ");
        }
        System.out.println();
    }

    private static double[] elitismo(double[][] populacao) {
        double aux[] = new double[qntPesos + 1];
        double maiorValor = populacao[0][qntPesos];
        int linha = 0;

        for (int i = 1; i < qntCromossomos; i++) {
            if (populacao[i][qntPesos] > maiorValor) {
                maiorValor = populacao[i][qntPesos];
                linha = i;
            }
        }

        for (int i = 0; i <= qntPesos; i++) {
            aux[i] = populacao[linha][i];
        }

        return aux;
    }

    private static int calcularAptidao(Tabuleiro Tabuleiro) {
        return new Heuristica().avaliarMovimento(Tabuleiro.getTabuleiro(), 1, Tabuleiro.getEmAndamento(),Tabuleiro.getQntErros());
    }

    private static double[][] gerarPopulacaoInicial() {
        Random gera = new Random();
        double populacao[][] = new double[qntCromossomos][qntPesos + 1];

        for (int i = 0; i < qntCromossomos; i++) {
            for (int j = 0; j < qntPesos; j++) {
                populacao[i][j] = gera.nextDouble();
                if (gera.nextBoolean()) {
                    populacao[i][j] = gera.nextDouble();
                }
            }
        }

        return populacao;
    }
    
    private static void salvarMelhorPopulacao(double[] populacao, String nomeArquivo) {
    try {
        FileWriter writer = new FileWriter(nomeArquivo);
        for (int i = 0; i < qntPesos; i++) {
            writer.write(populacao[i] + " ");
        }
        writer.close();
        System.out.println("Melhor população salva em " + nomeArquivo);
    } catch (IOException e) {
        System.out.println("Erro ao salvar a melhor população: " + e.getMessage());
    }
}
}
