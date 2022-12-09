public class RaceTrack {
	
	private int size; // dimensão da pista de corrida
	private int horses; // número de cavalos competindo
	
	public int getRaceTrackSize() { return size; }
	public void setRaceTrackSize(int size) { this.size = size; }
	
	// Recebe a quantidade de cavalos que estão correndo, é interessante recuperar utilizando horselist.size()
	public int getRaceTrackHorses() { return horses; }
	public void setRaceTrackHorses(int horses) { this.horses = horses; }
	
	//construtor
	RaceTrack(int rtsize, int qtdhorses) {
		setRaceTrackSize(rtsize);
		setRaceTrackHorses(qtdhorses);
	}
}
