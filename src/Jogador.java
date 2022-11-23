public class Jogador {
    private int id;
    private String nome;
    private float valor_apostado;
    private int id_cavalo;
    private boolean ganhou_aposta;

    // Encapsulamento da classe
    public Jogador(int id, String nome, float valor_apostado, int id_cavalo, boolean ganhou_aposta) {
        this.id = id;
        this.nome = nome;
        this.valor_apostado = valor_apostado;
        this.id_cavalo = id_cavalo;
        this.ganhou_aposta = false; // por padrao é false
    }

    // Métodos acessores, Get() e Set()
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public float getValor_apostado() { return valor_apostado; }
    public void setValor_apostado(float valor_apostado) { this.valor_apostado = valor_apostado; }

    public int getId_cavalo() { return id_cavalo; }
    public void setId_cavalo(int id_cavalo) { this.id_cavalo = id_cavalo; }

    public boolean isGanhou_aposta() { return ganhou_aposta; }
    public void setGanhou_aposta(boolean ganhou_aposta) { this.ganhou_aposta = ganhou_aposta;}
}
