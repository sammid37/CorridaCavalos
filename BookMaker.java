public class BookMaker {
	private int bets_num;
	private float total_value;
	private float prize;
	private float bookmaker_profit;
	private Server server;

	// Quantidade total de apostas
	public int getBets_num() { return bets_num; }
	private void setBets_num(int bets_num) { this.bets_num = bets_num; }

	// Valor total das apostas
	public float getTotal_value() { return total_value; }
	private void setTotal_value(float total_value) { this.total_value = total_value; }

	// Lucro possivel dos apostadores
	public float getPrize() { return prize; }
	private void setPrize(float prize) { this.prize = prize; }

	// Lucro da casa de apostas
	public float getBookmaker_profit() { return bookmaker_profit; }
	private void setBookmaker_profit(float bookmaker_profit) { this.bookmaker_profit = bookmaker_profit; }
	
	// Construtor
	BookMaker (Server server) { this.server = server; }
	
	// Recu
	public void bet(Gambler gambler, Horse horse) {
		// Verifica se o saldo >= valor da aposta 
		if (gambler.getWallet() >= gambler.getBet()) {
			// Atualiza o valor da carteira (carteira - valor aposta)
			gambler.setWallet(gambler.getWallet() - gambler.getBet());

			// Atualiza o valor de aposta e o nº de apostas no cavalo apostado 
			horse.setTotal_bet_value(horse.getTotal_bet_value() + gambler.getBet());
			horse.setBets_num(horse.getBets_num() + 1);

			// Recupera o número e o valor total de apostas para a casa de apostas
			this.setBets_num(this.getBets_num() + 1);
			this.setTotal_value(this.getTotal_value() + gambler.getBet());

			// Recompensa total da Casa de Aposta
			this.setPrize((float) (this.getPrize() + (gambler.getBet() * 0.95)));
			
			// Lucro da casa de apostas
			this.setBookmaker_profit((float) (this.getBookmaker_profit() + (gambler.getBet() * 0.05)));
		}
		else {
			server.writer.println("Saldo insuficiente!");
		}
	}

	// public void prize(){
			
	// }

	// Informa se um jogador ganhou ou perdeu a aposta
	public void result(Gambler gambler) {
		// A aposta tem de ser != 0
		if(gambler.getBet() != 0){
			if(gambler.isWinner()) {
				// Reparta o prêmio para o(s) ganhador(es)
				gambler.setReward((this.getPrize() / gambler.getHorse().getTotal_bet_value()) * gambler.getBet());
				gambler.setWallet(gambler.getWallet() + gambler.getReward());
			}
			else {
				server.writer.println("Você perdeu!!");
			}
		}
	}
}
