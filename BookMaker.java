package horses_bet;

import org.jetbrains.annotations.NotNull;

public class BookMaker {
    private int bets_num = 0;
    private float total_value = 0;
    private float prize;
    private float bookmaker_profit;
    
    
    public int getBets_num() {
        return bets_num;
    }

    private void setBets_num(int bets_num) {
        this.bets_num = bets_num;
    }

    public float getTotal_value() {
        return total_value;
    }

    private void setTotal_value(float total_value) {
        this.total_value = total_value;
    }

    public float getPrize() {

        return prize;
    }

    private void setPrize(float prize) {
        this.prize = prize;
    }

    public float getBookmaker_profit() {

        return bookmaker_profit;
    }

    private void setBookmaker_profit(float bookmaker_profit) {
        this.bookmaker_profit = bookmaker_profit;
    }
    //-------------------------------------------------------------------------------------------
    public void bet(@NotNull Gambler gambler, @NotNull Horse horse, int value) {
        if (gambler.getWallet() >= value){
            gambler.setBet(value);
            gambler.setHorse(horse);
            gambler.setWallet(gambler.getWallet() - value);

            horse.setTotal_bet_value(horse.getTotal_bet_value() + value);
            horse.setBets_num(horse.getBets_num() + 1);

            this.setBets_num(this.getBets_num() + 1);
            this.setTotal_value(this.getTotal_value() + value);

            this.setPrize((float) (getTotal_value() * 0.95));
            this.setBookmaker_profit(getTotal_value() - getPrize());
        }
        else{
            System.out.println("Saldo insuficiente!");
        }
    }

    public  void result(@NotNull Gambler gambler){
        if(gambler.getBet() != 0){
            if(this.getBets_num() >= 2) {
                if(gambler.isWinner()) {
                    gambler.setReward((this.getPrize()/gambler.getHorse().getTotal_bet_value())*gambler.getBet());
                    gambler.setWallet(gambler.getWallet() + gambler.getReward());
                    this.clear(gambler);
                }
                else {
                    System.out.println("Você perdeu!!");
                    this.clear(gambler);
                }
            }
            else {
                System.out.println("Número de apostadores insuficiente, o valor apostado será estornado!");
                gambler.setWallet(gambler.getWallet() + gambler.getBet());
                this.clear(gambler);
            }
        }

    }

    public void clear(@NotNull Gambler gambler){
        gambler.setReward(0);
        gambler.setBet(0);
        gambler.setHorse(null);
        gambler.setWinner(false);
    }

    public void clear(@NotNull Horse horse){
         horse.setTotal_bet_value(0);
         horse.setBets_num(0);
    }

    public void clear(){
         this.setTotal_value(0);
         this.setBets_num(0);
    }
}
