package antConolyOpt_TSP;

public class DistanceCal {

    /**
     *
     * @param p1
     * @param p2
     * @return 二维坐标两点之间的距离
     */
    public static double distanceCal(int[] p1, int[] p2){
        double tp1 = Math.pow(p1[0]-p2[0],2);
        double tp2 = Math.pow(p1[1]-p2[1],2);
        double tp =  Math.sqrt(tp1+tp2);
        return tp;
    }
}
