package PacMan;

import PacMan.Utility.Globals;

import java.io.*;

/**
 * Created by András Fridvalszky on 2016. 11. 28..
 */
public class ScoreBoard {
    private static String[] name = new String[Globals.SCORE_BOARD];
    private static  int[] scores = new int[Globals.SCORE_BOARD];

    static{
        for(int i = 0; i < name.length; i++) {
            name[i] = new String("AAA");
            scores[i] = 0;
        }
    }

    public static void load(String fileName){
        File f = new File(fileName);

        if(f == null) {
            return;
        } else {
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String s;
                int i = 0;
                while((s = br.readLine()) != null) {
                    String[] line = s.split(" ");
                    name[i] = line[0];
                    scores[i] = Integer.parseInt(line[1]);
                }

            } catch (Exception e) {}
        }
    }

    public static void save(String fileName){
        File f = new File(fileName);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            for(int i = 0; i < Globals.SCORE_BOARD; i++){
                bw.write(name[i] + " " + scores[i] + "\r\n");
            }
            bw.close();
        } catch (Exception e) {}

    }

    public static void add(String n, int score){
        for(int i = 0; i < Globals.SCORE_BOARD; i++){
            if(score > scores[i]){
                for(int j = Globals.SCORE_BOARD -1; j >= i + 1; j--){
                    scores[j] = scores[j-1];
                    name[j] = name[j-1];
                }
                name[i] = n;
                scores[i] = score;
                break;
            }
        }
    }
}
