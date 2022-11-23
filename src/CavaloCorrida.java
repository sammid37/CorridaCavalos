import java.util.Random;

public class CavaloCorrida {

    private static class Cavalo implements Runnable {
        private int id;
        private String nome = "";
        private int distanciaPercorrida = 0;

        Cavalo (int id, String nomeCavalo) { nome = nomeCavalo; }

        @Override
        public void run() {
            try {
                while (distanciaPercorrida < 100) {
                    distanciaPercorrida += this.galopar();

                    if (distanciaPercorrida < 100) {

                    } else {
                    }
                }
            } catch (Exception e) { threadMessage("I wasn't done!"); }
        }

        private int galopar() {
            Random galopa = new Random();
            int maxVelocidade = 20;
            int minVelocidade = 5;

            return galopa.nextInt(maxVelocidade - minVelocidade) + minVelocidade;;
        }
    }

    public static void main(String args[]) throws Exception {
        Cavalo[] cavalos = new Cavalo[]{
            new Cavalo(1, "Pé de pano"),
            new Cavalo(2, "Ventania"),
            new Cavalo(3, "Maximus"),
        };
        // Cria n threads de acordo com a quantidade de cavalos
        Thread[] threads = new Thread[cavalos.length];

    }


}
