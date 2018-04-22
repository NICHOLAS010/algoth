package antConolyOpt_TSP;

import java.util.ArrayList;
import java.util.Random;
import rouletteWheel.*;

public class Ant {
    private ArrayList<Integer> nextNodes; //余下的没有经过的节点
    private ArrayList<Integer> pastNodes; //已经经过的节点
    private int roundDistance; //一轮后得出的这一轮的长度
    private int[] memPath;//该蚂蚁所经过的路径的记录
    private int id;//蚂蚁的编号
    private int TSP_length;
    private int[][] TSP;
    public double[][] infoPenne; //信息素强度
    double alpha;
    double belta;
    double rou;


    public Ant(double[][] infoPenre, int[][] TSP, int id, double alpha, double belta, double rou){ //inttPrene 最初的信息素强度；TSP[][]是TSP矩阵，每个矩阵元素是两点的距离；id标定蚂蚁的id
        this.infoPenne = infoPenre;
        this.TSP_length = TSP.length;
        this.memPath = new int[this.TSP_length];
        this.id = id;
        this.nextNodes = new ArrayList<Integer>(this.TSP_length);
        this.TSP = TSP;
        this.alpha = alpha;//信息素的影像参数
        this.belta = belta;//
        this.rou = rou;
        this.memPath = new int[TSP_length + 1];

        for(int i=0; i<this.TSP_length;i++){ //默认当前没有经过任何节点
            this.nextNodes.add(i);
        }
        this.pastNodes = new ArrayList<Integer>(0);
    }

    public void init(){//初始化随机选择一个节点；更新剩余的节点和经过的节点
        int startPoint = new Random().nextInt(this.TSP_length);
        this.pastNodes.add(startPoint);
        this.nextNodes.remove(this.nextNodes.indexOf(startPoint));
        this.memPath[0] = startPoint;
    }

    public void getNextNode(int nodeNum){ //选择出下一个节点
        int currentPoint = this.pastNodes.get(this.pastNodes.size()-1);
        double sum = 0;
        double[] temp = new double[this.pastNodes.size()];
        for(int i=0; i<this.nextNodes.size();i++){
            temp[i] = Math.pow(this.infoPenne[currentPoint][this.nextNodes.get(i)], this.alpha) + Math.pow(this.TSP[currentPoint][this.nextNodes.get(i)],(-1) * this.belta);
            sum = sum + temp[i];
        }
        double[] temp2 = new double[this.pastNodes.size()];
        for(int j=0;j<this.nextNodes.size();j++){
            temp2[j] = temp[j] / sum;
        }
        int choose = this.nextNodes.get(new RouletteWheel(temp2).doRouletteWheel());
        this.pastNodes.add(this.nextNodes.get(choose));
        this.nextNodes.remove(choose);
        this.memPath[nodeNum] = choose;
    }

    public void oneRound(){
        this.init();
        int nodeNum = 1;
        while (this.nextNodes.size() != 0){
            this.getNextNode(nodeNum);
            nodeNum ++;
        }
        this.memPath[this.memPath.length-1] = memPath[0];
    }

    public static void main(String[] args) {
        Ant ant1 = new Ant(new double[][] {{0,0.3},{0.3,0}},new int[][] {{0,10},{10,0}},0,1,2,0.5);
        ant1.oneRound();
        for(int i:ant1.memPath){
            System.out.println(i);
        }
    }
}
