package antConolyOpt_TSP;

import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import antConolyOpt_TSP.Ant;

public class ACO_TSP {

    private int iterNum;
    int ants;
    double alpha;
    double belta;
    double rou;
    int[][] tsp_distance;

    public ACO_TSP(int iterNum, int ants){
        this.iterNum = iterNum;
        this.ants = ants;
        this.alpha = 1;
        this.belta = 2;
        this.rou = 0.5;


    }

    /**
     *
     */
    public void initTSP(){
        String tsp_file_path = System.getProperty("user.dir") + "/src/antConolyOpt_TSP/att48.tsp";
        File f = new File(tsp_file_path);
        String context = "";
        try(FileInputStream fi = new FileInputStream(f)) {
            int size = fi.available();
            byte[] buffer = new byte[size];
            fi.read(buffer);
            context = new String(buffer);
        }catch (IOException e){
            e.printStackTrace();
        }

        int flag = 0;
        String pattern = "\\d+\\s+\\d+\\s+\\d+";
        Pattern r = Pattern.compile(pattern);
        ArrayList<String> tsp_list = new ArrayList<>();
        String[] tmp2 = context.split("\n");
        int[][] tsp_info = new int[48][2];
        int cnt = 0;
        for(int i=0;i<tmp2.length;i++){
            Matcher m = r.matcher(tmp2[i]);
            if(m.find()){
                tsp_info[cnt] = this.string2intArray(m.group(0));
                cnt ++;
            }
        }

        this.tsp_distance = new int[tsp_info.length][tsp_info.length];
        for(int i=0;i<tsp_info.length;i++){
            for(int j=0;j<tsp_info.length;j++){
                this.tsp_distance[i][j] = (int) DistanceCal.distanceCal(tsp_info[i],tsp_info[j]);
            }
        }

    }


    /**
     *
     * @return
     */
    public int[] doAntIter(){
        this.initTSP();
        double[][] infoPeneForTSP = this.initPenre();
        int[][] allMemPath = new int[this.tsp_distance.length][this.tsp_distance.length+1];
        int[] distanceRoundAnts = new int[this.tsp_distance.length];
        for (int i=0;i<this.iterNum;i++){
            allMemPath = new int[this.ants][this.tsp_distance.length+1];
            distanceRoundAnts = new int[this.ants];
            for (int j=0;j<this.ants;j++){
                Ant ant = new Ant(infoPeneForTSP,this.tsp_distance,j,this.alpha,this.belta,this.rou);
                ant.oneRound();
                allMemPath[j] = ant.getMemPath();
                distanceRoundAnts[j] = ant.getRoundDistance();
                ant = null;
            }
            infoPeneForTSP = this.updatePhero(infoPeneForTSP,allMemPath,distanceRoundAnts);
        }
        return allMemPath[allMemPath.length-1];
    }

    /**
     *
     * @param infoPenne
     * @param allMemPath
     * @param distanceRoundAnts
     * @return
     */
    public double[][] updatePhero(double[][] infoPenne, int[][] allMemPath, int[] distanceRoundAnts) {
        for (int k = 0; k < this.ants; k++) {
            for (int i = 0; i < allMemPath.length; i++) {
                infoPenne[allMemPath[k][i]][allMemPath[k][i + 1]] = ((1 - this.rou) * infoPenne[allMemPath[k][i]][allMemPath[k][i + 1]] + distanceRoundAnts[k]);
            }
        }
        return infoPenne;
    }

    /**
     *
     * @return
     */
    public double[][] initPenre(){
        int[] initP = greedyPath();
        int roundlenth=0;
        double[][] initPen = new double[this.tsp_distance.length][this.tsp_distance.length];
        for(int i=0; i<initP.length-1;i++){
                roundlenth = roundlenth +  this.tsp_distance[initP[i]][initP[i+1]];
        }
        assert roundlenth > 0;
        System.out.println(roundlenth);
        double tp2 = this.ants * (1.0 / roundlenth);
        for(int k=0;k<initP.length-1;k++){
            for(int l=0;l<initP.length-1;l++){
                if(k == l){
                    initPen[k][l] = 0;
                }
                else {
                    initPen[k][l] = tp2;
                }
                System.out.println(l);
            }
        }
        return initPen;
    }

    /**
     *
     * @return
     */
    public int[] greedyPath(){
        int[] greedyp = new int[this.tsp_distance.length + 1];
        int tmp_min= 9999999;
        int st=0;
        int en=0;
        for (int i=0;i<this.tsp_distance.length;i++){
            for(int j=0;j<this.tsp_distance.length;j++){
                if((tmp_min) >= this.tsp_distance[i][j]  & this.tsp_distance[i][j] > 0){
                    st = i;
                    en = j;
                    tmp_min = this.tsp_distance[i][j];
                }
            }
        }

        ArrayList<Integer> tmp_past = new ArrayList<>();
        greedyp[0] = st;
        tmp_past.add(st);
        tmp_past.add(en);
        greedyp[1] = en;
        st = en;
        int lo = 2;
        while (lo<greedyp.length - 1){
            tmp_min = 9999999;
            for(int k=0;k<this.tsp_distance.length;k++){
                if(tmp_min > this.tsp_distance[st][k] & this.tsp_distance[st][k]>0 & !tmp_past.contains(k)){
                    en = k;
                    tmp_min = this.tsp_distance[st][k];
                }
            }
            greedyp[lo] = en;
            tmp_past.add(en);
            st = en;
            lo++;
        }
        greedyp[greedyp.length-1] = greedyp[0];
        return greedyp;
    }

    /**
     *
     * @param strin
     * @return
     */
    public int[] string2intArray(String strin){
        String[] tpStr = strin.split(" ");
        int[] tmp = new int[tpStr.length-1];
        for(int i=0;i<tmp.length;i++){
            tmp[i] = Integer.valueOf(tpStr[i+1]);
        }
        return tmp;
    }


    /**
     *
     * main
     */
    public static void main(String[] args) {
        ACO_TSP demo = new ACO_TSP(32,32);
        int[] path = demo.doAntIter();
    }

}
