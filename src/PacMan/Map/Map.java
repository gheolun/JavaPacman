package PacMan.Map;

import PacMan.Characters.PacMan;
import PacMan.Utility.*;
import PacMan.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by Norbi on 2016. 10. 16..
 */

public class Map{
    private int width, height;
    private Game game;
    private MapValue[][] map;
    private Position[] ghostPosition = new Position[4];
    private BufferedImage bImgEmpty;
    private int coins;

    private Clip mCoin;

    private BufferedImage bImgMap;

    private Graphics mapGraphics;

    public Map(String fName, Game g){
        try {
            BufferedReader br = new BufferedReader(new FileReader(Globals.MAP + fName + ".txt"));
            width = Integer.parseInt(br.readLine());
            height = Integer.parseInt(br.readLine());
            map = new MapValue[height][width];
            for (int i = 0; i < height; i++) {
                map[i] = Map.stringArrayToMapValueArray(br.readLine().split(" "));
            }


            for(int i = 0; i < 4; i++){
                String[] stringPos = new String[2];
                stringPos = br.readLine().split(" ");
                ghostPosition[i] = new Position(Integer.parseInt(stringPos[0]), Integer.parseInt(stringPos[1]));
            }

            game = g;

            bImgMap = new BufferedImage(width*Globals.TILE_SIZE, height*Globals.TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
            mapGraphics = bImgMap.getGraphics();
            for(int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    BufferedImage img = ImageIO.read(new File(Globals.IMG_SRC + map[i][j].getFName()));
                    if(map[i][j] == MapValue.COIN)
                        coins++;
                    mapGraphics.drawImage(img, Globals.TILE_SIZE*j, Globals.TILE_SIZE*i, null);
                }
            }
            bImgEmpty = ImageIO.read(new File(Globals.IMG_SRC + MapValue.EMPTY.getFName()));

            loadCoinAudio(Globals.SND_SRC + Globals.COIN_WAV);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MapValue getValue(int x, int y){
        return map[y][x];
    }

    public MapValue getValue(Position p){
        return getValue(p.getIntPos());
    }

    public MapValue getValue(IntPosition p){
        return map[p.getY()][p.getX()];
    }

    public int getCoins(){
        return coins;
    }

    public int getHeightWithBlock (){
        return height;
    }

    public int getWidthWithBlock (){
        return width;
    }

    public Position getGhostPos(int i){
        return ghostPosition[i];
    }

    public void setValue(int x, int y, MapValue v){
        map[y][x] = v;
    }

    public void draw(Graphics g){
        g.drawImage(bImgMap, 0, 0, null);
    }

    public void coinToEmpty(int x, int y){

        if(x < width && y < height && getValue(x, y) == MapValue.COIN) {
            map[y][x] = MapValue.EMPTY;
            mapGraphics.drawImage(bImgEmpty, x*Globals.TILE_SIZE, y*Globals.TILE_SIZE, null);
            game.givePoint(100);
            mCoin.setFramePosition(0);
            mCoin.start();
        }
    }

    public static MapValue[] stringArrayToMapValueArray(String[] s){
        MapValue[] mv = new MapValue[s.length];
        for(int i = 0; i < s.length; i++)
            mv[i] = Map.stringToMapValue(s[i]);
        return mv;
    }

    public static MapValue stringToMapValue(String s){
        switch (Integer.parseInt(s)){
            case 0:
                return MapValue.WALL_00;
            case 1:
                return MapValue.WALL_01;
            case 2:
                return MapValue.WALL_02;
            case 3:
                return MapValue.WALL_03;
            case 4:
                return MapValue.WALL_04;
            case 5:
                return MapValue.WALL_05;
            case 6:
                return MapValue.WALL_06;
            case 7:
                return MapValue.WALL_07;
            case 8:
                return MapValue.WALL_08;
            case 9:
                return MapValue.WALL_09;
            case 10:
                return MapValue.WALL_10;
            case 11:
                return MapValue.WALL_11;
            case 12:
                return MapValue.WALL_12;
            case 13:
                return MapValue.WALL_13;
            case 14:
                return MapValue.WALL_14;
            case 15:
                return MapValue.WALL_15;
            case 16:
                return MapValue.WALL_16;
            case 17:
                return MapValue.WALL_17;
            case 18:
                return MapValue.WALL_18;
            case 19:
                return MapValue.WALL_19;
            case 20:
                return MapValue.WALL_20;
            case 21:
                return MapValue.WALL_21;
            case 22:
                return MapValue.WALL_22;
            case 23:
                return MapValue.WALL_23;
            case 24:
                return MapValue.WALL_24;
            case 25:
                return MapValue.WALL_25;
            case 26:
                return MapValue.WALL_26;
            case 27:
                return MapValue.WALL_27;
            case 28:
                return MapValue.EMPTY;
            case 88:
                return MapValue.COIN;
            default:
                return MapValue.EMPTY;
        }
    }

    private void loadCoinAudio(String coin){
        try {
            mCoin = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            System.out.println("Sorry no audio.");
        }

        try {
            File audioFile = new File(coin);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            mCoin = (Clip) AudioSystem.getLine(info);
            mCoin.open(audioStream);
            ((FloatControl) mCoin.getControl(FloatControl.Type.MASTER_GAIN)).setValue(-15);
        } catch (Exception e) {
            System.out.println("Coldn't read file: " + coin);
        }
    }
}
