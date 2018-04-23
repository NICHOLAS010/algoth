package antConolyOpt_TSP;

import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                this.tsp_distance[i][j] = (int)distanceCal(tsp_info[i],tsp_info[j]);
            }
        }

    }


    public void doAntIter(){
        for (int i=0;i<this.iterNum;i++){
            for (int j=0;j<this.ants;j++){


            }
        }


    }

    public void updatePhero(double[][] infoPenne, int[][] allMemPath, int[] distanceRoundAnts) {
        for (int k = 0; k < this.ants; k++) {
            for (int i = 0; i < allMemPath.length; i++) {
                infoPenne[allMemPath[k][i]][allMemPath[k][i + 1]] = ((1 - this.rou) * infoPenne[allMemPath[k][i]][allMemPath[k][i + 1]] + distanceRoundAnts[k]);
            }
        }
    }

    public double[][] initPenre(){
        int[] initP = greedyPath();
        for(int i=0; i<initP.length-1;i++){
            
        }
    }

    public int[] greedyPath(){
        int[] greedyp = new int[this.tsp_distance.length + 1];
        int tmp_min= 9999999;
        int st=0;
        int en=0;
        for (int i=0;i<this.tsp_distance.length;i++){
            for(int j=0;j<this.tsp_distance.length;j++){
                if((tmp_min) >= tsp_distance[i][j]){
                    st = i;
                    en = j;
                    tmp_min = this.tsp_distance[i][j];
                }
            }
        }
        greedyp[0] = st;
        greedyp[1] = en;
        st = en;
        int lo = 2;
        while (lo<greedyp.length - 1){
            tmp_min = 9999999;
            for(int k=0;k<this.tsp_distance.length;k++){
                if(tmp_min > this.tsp_distance[st][k]){
                    en = k;
                }
            }
            greedyp[lo] = en;
            st = en;
            lo++;
        }
        greedyp[greedyp.length-1] = greedyp[0];

        return greedyp;
    }

    public double distanceCal(int[] p1, int[] p2){
        double tp1 = Math.pow(p1[0]-p2[0],2);
        double tp2 = Math.pow(p1[1]-p2[1],2);
        double tp =  Math.sqrt(tp1+tp2);
        return tp;
    }

    public static void main(String[] args) {
        ACO_TSP demo = new ACO_TSP(32,32);
        demo.initTSP();
    }

    public int[] string2intArray(String strin){
        String[] tpStr = strin.split(" ");
        int[] tmp = new int[tpStr.length-1];
        for(int i=0;i<tmp.length;i++){
            tmp[i] = Integer.valueOf(tpStr[i+1]);
        }
        return tmp;
    }
}
