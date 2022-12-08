package corrida;

public class Pista {
	
	private int tamanho;
	private int qtdeRaias;
	
	//construtor
	Pista (int tamanho, int qtdeRaias){
		this.tamanho = tamanho;
		this.qtdeRaias = qtdeRaias;
	}
	
	public void correr() {
		System.out.println("Cavalos correndo!");
	}
	
	public int getTamanho() {
		return tamanho;
	}
	
	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public int getQtdeRaias() {
		return qtdeRaias;
	}

	public void setQtdeRaias(int qtdeRaias) {
		this.qtdeRaias = qtdeRaias;
	}
}
