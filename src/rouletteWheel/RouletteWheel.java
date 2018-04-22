package rouletteWheel;

public class RouletteWheel {
    double[] P;
    public RouletteWheel(double[] P){
        this.P = P;
    }

    /**
     * 输入一个任意顺序的概率总和为1 的数组，
     * 输出轮盘转后选择的原来数组的位置
     * @return
     */
    public int doRouletteWheel(){
        double[]sortedP = this.sorted();
        double rand = Math.random();
        System.out.println(rand);
        double tp=0;
        int result = 99999;
        for (int i = 0; i < sortedP.length; i++) {
            tp = tp + sortedP[i];
            if(rand<tp){
                double tp2 = sortedP[i];
                for(int k=0; k<sortedP.length;k++){
                    if(this.P[k] == tp2){
                        result = k;
                        return result;
                    }
                }
            }
        }
        return result;
    }

    public double[] sorted() {
        double[] tmp = this.P.clone();
        double[] tmp2 = new double[tmp.length];
        double[] tmp3 = new double[tmp.length];
        double[] res = new double[tmp.length];
        double sw;
        for (int i = 0; i < tmp.length; i++) {
            int fg = 99999;
            double min = 999999;
            for(int j=i;j<tmp.length;j++){
                if(tmp[j] <= min){
                    min = tmp[j];
                    fg = j;
                }
            }
            sw = tmp[i];
            tmp[i] = min;
            tmp[fg] = sw;
            tmp2[i] = min; // i是排序后的下标，fg是实际未排序顺序的下标
            tmp3[i] = fg;
        }
        res = tmp2;
        for(double i:res){
            System.out.println(i);
        }
        return res;
    }

    public static void main(String[] args) {
        RouletteWheel demo = new RouletteWheel(new double[] {0.2,0.3,0.1,0.35,0.05});
        System.out.println(demo.doRouletteWheel());
    }
}
