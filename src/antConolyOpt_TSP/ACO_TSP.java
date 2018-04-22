package antConolyOpt_TSP;

import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ACO_TSP {

    private int iterNum;

    public ACO_TSP(int iterNum){
        this.iterNum;
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

        int[][] tsp_distance = new int[tsp_info.length][tsp_info.length];
        for(int i=0;i<tsp_info.length;i++){
            for(int j=0;j<tsp_info.length;j++){
                tsp_distance[i][j] = (int)distanceCal(tsp_info[i],tsp_info[j]);
            }
        }

        System.out.println(tsp_distance);

    }


    public void doIter(){


    }

    public void updatePhero(){

    }

    public void greedyPath(){


    }

    public double distanceCal(int[] p1, int[] p2){
        double tp1 = Math.pow(p1[0]-p2[0],2);
        double tp2 = Math.pow(p1[1]-p2[1],2);
        double tp =  Math.sqrt(tp1+tp2);
        return tp;
    }

    public static void main(String[] args) {
        ACO_TSP demo = new ACO_TSP();
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
