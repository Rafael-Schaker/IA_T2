public class Tabuleiro {
    private int[][] tabuleiro;
    private boolean emAndamento;
    private int qntErros; 

    public Tabuleiro(int[][] tabuleiro, boolean emAndamento, int qntErros) {
        this.tabuleiro = tabuleiro;
        this.emAndamento = emAndamento;
        this.qntErros = qntErros;

    }

    public int[][] getTabuleiro() {
        return tabuleiro;
    }

    public boolean getEmAndamento() {
        return emAndamento;
    }

    public int getQntErros(){
        return qntErros;
    }
    
}