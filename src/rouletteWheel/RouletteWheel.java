package rouletteWheel;

public class RouletteWheel {
    double[] P;
    int spin;
    public RouletteWheel(double[] P,int spin){
        this.P = P;
        this.spin = spin;
        this.doRouletteWheel();
    }

    public void doRouletteWheel(){
        for(int j=0;j<this.spin;j++) {
            double rand = Math.random();
            double tp=0;
            for (int i = 0; i < this.P.length; i++) {
                tp = tp + this.P[i];
                if(rand<tp){
                    System.out.println("spin is: " + rand + " | " + "in: " + (i+1) + "block");
                    break;
                }
            }
        }
    }


    public static void main(String[] args) {
        RouletteWheel demo = new RouletteWheel(new double[] {0.2,0.35,0.45},100);
    }
}
