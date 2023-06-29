import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Usuario {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bem-vindo ao Jogo da Velha!");

        System.out.println("Escolha uma opção:");
        System.out.println("1 - Treinar o algoritmo genético");
        System.out.println("2 - Jogar contra o algoritmo genético");

        int escolha = scanner.nextInt();

        if (escolha == 1) {
            // Treinar o algoritmo genético
            treinarAlgoritmoGenético();
        } else if (escolha == 2) {
            // Jogar contra o algoritmo genético
            jogarContraAlgoritmoGenético();
        } else {
            System.out.println("Opção inválida. Encerrando o programa.");
        }

        scanner.close();
    }

    public static void treinarAlgoritmoGenético() {
        Scanner scanner = new Scanner(System.in);
        // Lógica para treinar o algoritmo genético
        System.out.println("Escolha a dificuldade do treinamento:");
        System.out.println("1 - Facil");
        System.out.println("2 - Medio");
        System.out.println("3 - Dificil");
        int escolha = scanner.nextInt();
        
        if (escolha == 1) {//facil
            AlgoritmoGenetico.main(new String[0],"Facil");

        } else if (escolha == 2) {//medio
            AlgoritmoGenetico.main(new String[0],"Medio");


        } else if (escolha == 3) {//dificil
            AlgoritmoGenetico.main(new String[0],"Dificil");


        } else {
            System.out.println("Opção inválida. Encerrando o programa.");
        }

        System.out.println("Algoritmo genético Treinado");

        // Coloque aqui a implementação para treinar o algoritmo genético
    }

    public static void jogarContraAlgoritmoGenético() {
        try (BufferedReader reader = new BufferedReader(new FileReader("melhor_populacao.txt"))) {
            String linha = reader.readLine();
            String[] valores = linha.split(" ");

            double[] populacao = new double[valores.length];

            for (int i = 0; i < valores.length; i++) {
                populacao[i] = Double.parseDouble(valores[i]);
            }
            // Lógica para jogar contra o algoritmo genético
            TestaRede testaRede = new TestaRede();
            testaRede.joga(populacao, "Dificil", false);
            System.out.println("Jogo contra o algoritmo genético encerrado!");
        } catch (IOException e) {
            System.out.println("Erro ao ler a melhor população: " + e.getMessage());
        }
    }

}
