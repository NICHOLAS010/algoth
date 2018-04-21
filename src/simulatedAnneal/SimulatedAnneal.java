package simulatedAnneal;

import java.util.Random;

public class SimulatedAnneal{
    int[] weight;
    int[] value;
    int capcity;
    public SimulatedAnneal(int[] w,int[] v, int cap) {
        this.weight = w;
        this.value = v;
        this.capcity = cap;
    }

    public int[] getBestPkgChs() {
        double tpStrat = 100;                                   //start Temperature
        double tpEnd = 0.3;                                     //end temperature
        double tpDownRate = 0.99;                               //temperature descreases rate
        double currentTp = tpStrat;                             //current temperature
        int currentV = 0;                                       //current packaged value
        int currentW = 0;                                       //current wight packaged
        int size = this.weight.length;
        int[] flag = new int[size];
        for(int i=0;i<size;i++){
            flag[i] = 0;                                        //flag initialized; 0 means unpackaged,
            // 1 means packaged.
        }

        // initialize
        for(int i=0;i<size;i++){
            if(this.capcity - currentW > this.weight[i]){
                flag[i] = 1;
                currentV = currentV + this.value[i];
                currentW = currentW + this.weight[i];
            }
        }

        int nextCurser;
        //simulated annealing
        while(currentTp >= tpEnd){
            //Generate next random good index not packaged
            nextCurser = new Random().nextInt(size);
            int tmpCurser;
            int gapValue;
            int gapWeight;
            int[] flag_tmp = new int[this.weight.length];
            double judge;
            if(flag[nextCurser] == 0) {
                tmpCurser = new Random().nextInt(size);
                while (tmpCurser == 0) {
                    tmpCurser = new Random().nextInt(size);
                }
                int fg2 = 0;
                while (fg2 == 0) {
                    gapValue = this.value[nextCurser] - this.value[tmpCurser];
                    gapWeight = this.weight[nextCurser] - this.weight[tmpCurser];
                    judge = Math.exp(gapValue / currentTp);
                    if (currentW + gapWeight <= this.capcity) {
                        flag_tmp[tmpCurser] = 1;
                        if (gapValue > 0 | judge > Math.random()) {
                            flag[nextCurser] = 1;
                            flag[tmpCurser] = 0;
                            currentW = currentW + gapWeight;
                            currentV = currentV + gapValue;
                            fg2 = 1;
                        }
                    } else {
                        tmpCurser = new Random().nextInt(size);
                        while ((flag[tmpCurser] == 0 | flag_tmp[tmpCurser] == 1) & sumOf(flag_tmp) != sumOf(flag)) {
                            tmpCurser = new Random().nextInt(size);
                        }
                    }
                }
            }
            else {
                tmpCurser = new Random().nextInt(size);
                while (tmpCurser == 1){
                    tmpCurser = new Random().nextInt(size);
                }
                gapValue = this.value[tmpCurser] - this.value[nextCurser];
                gapWeight = this.weight[tmpCurser] - this.weight[nextCurser];
                judge = Math.exp(gapValue / currentTp);
                if (currentW + gapWeight <= this.capcity) {
                    if (gapValue > 0 | judge > Math.random()) {
                        flag[nextCurser] = 0;
                        flag[tmpCurser] = 1;
                        currentW = currentW + gapWeight;
                        currentV = currentV + gapValue;
                    }
                }
            }
            currentTp = tpDownRate * currentTp;
        }
        return flag;
    }

    public int sumOf(int[] arrIn) {
        int sum = 0;
        for(int i:arrIn) {
            sum = sum + i;
        }
        return sum;
    }



    public static void main(String[] args) {
//        int[] weight = new int[20];
//        int[] value = new int[20];
//        int fg = 1;
//        int cap = new Random().nextInt(10) + 40;
//        while (fg == 1) {
//            for (int i = 0; i < 20; i++) {
//                weight[i] = new Random().nextInt(50);
//                value[i] = new Random().nextInt(40);
//            }
//            int sumW=0;
//            for(int i:weight){
//                sumW = sumW + i;
//            }
//            if(sumW > cap){
//                fg = 0;
//            }
//        }
        int[] weight = {1,2,3,4,5};
        int[] value = {5,4,3,2,1};

        SimulatedAnneal demo = new SimulatedAnneal(weight,value,10);
        int[] p = demo.getBestPkgChs();
        for(int i:weight){
            System.out.print(i + " ");
        }
        System.out.println("\n");
        for(int i:value){
            System.out.print(i + " ");
        }
        System.out.println("\n");
        for(int i:p){
            System.out.print(i + " ");
        }

    }

}
