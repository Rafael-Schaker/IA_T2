import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class AlgoritmoGenetico {
    static int numGeracoes = 30;
    static int qntCromossomos = 4;
    static int qntPesos = 180;
    public static void main(String[] args,String dificuldade) {
        
        double populacaoInicial[][] = gerarPopulacaoInicial();
        double populacaoIntermediaria[][] = new double[qntCromossomos][qntPesos + 1];
        double melhorPopulacao[] = null;
        double melhorAptidao = Double.NEGATIVE_INFINITY;
        Random aleatorio = new Random();

        for (int g = 0; g < numGeracoes; g++) {
            System.out.println("Numero da Geracao:"+g);
            // Joga o jogo da velha
            for (int i = 0; i < qntCromossomos; i++) {
                Tabuleiro Tabuleiro = new TestaRede().joga(populacaoInicial[i],dificuldade,true);//informa a população, dificuldade e se é treinamento ou n
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

            populacaoIntermediaria[0]=elitismo(populacaoInicial);
            crossOver(populacaoInicial, populacaoIntermediaria);
            populacaoInicial = populacaoIntermediaria;
            if(aleatorio.nextInt(2)==0) mutacao(populacaoInicial);
            System.out.println("Aptidão atual:"+ elitismo(populacaoIntermediaria));
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
        public static void mutacao(double [][]populacao){
        Random gera = new Random();
        int quantidade = gera.nextInt(3)+1;
        
        while(quantidade>0){
            int linha = gera.nextInt(qntCromossomos -1 )+1;
            int coluna = gera.nextInt(qntPesos - 1);
        
            if(populacao[linha][coluna]==0) populacao[linha][coluna] = 1;
            else populacao[linha][coluna] = 0;
            System.out.println("Mutacao no cromossomo: " + linha + " na coluna: " + coluna);
            quantidade--;
        }
        
    }
    public static void crossOver(double[][] populacao, double[][] populacaoIntermediaria) {
        for (int i = 0; i < qntCromossomos - 1; i += 2) {
            int individuo1 = torneio(populacao);
            int individuo2 = torneio(populacao);
            for(int j= 0; j<qntCromossomos - 1; j++){
                populacaoIntermediaria[i][j] = populacao[individuo1][j];
                populacaoIntermediaria[i+1][j] = populacao[individuo2][j];
            }
            for(int j= 10; j<(qntCromossomos - 1)*2; j++){
                populacaoIntermediaria[i][j] = populacao[individuo2][j];
                populacaoIntermediaria[i+1][j] = populacao[individuo1][j];
            }
        }
    }

    
    public static int torneio(double[][] populacao){
        Random aleatorio = new Random();
        
        int linha1 = aleatorio.nextInt(qntCromossomos);
        int linha2 = aleatorio.nextInt(qntCromossomos);
        
        if(populacao[linha1][qntPesos - 1]>populacao[linha2][qntPesos - 1]){
            return linha2;
        }
        else return linha1;
    }
}
