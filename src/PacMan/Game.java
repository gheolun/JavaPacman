package PacMan;

import PacMan.Map.*;
import PacMan.Characters.*;
import PacMan.Utility.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Norbi on 2016. 10. 22..
 */
public class Game extends JFrame implements Runnable, WindowListener {
    private static int numOfGhosts = 1;
    private static String mapName = "map01";
    private static boolean mario = false;
    private static boolean pony = false;

    private Clip mIngame, mWin, mLose;

    private static double time = 300;

    private Map map;
    private PacMan p;
    private Ghost[] gh;
    private boolean bLose = false;
    private boolean bWin = false;
    private boolean running = true;

    private int score = 0;

    Graphics g;

    BufferedImage imgBuffer;
    Graphics gBuffer;

    public Game(){
        super("GAME");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(this);
        map = new Map(mapName, this);

        if(mario){
            p = new PacMan(1, 1, "mario", this, 9);
            loadLoseAudio(Globals.SND_SRC + "mario.wav");
        }else{
            p = new PacMan(1, 1, "pacman", this, 7);
            loadLoseAudio(Globals.SND_SRC + "lose.wav");
        }


        if(mapName.equals("easy")){
            loadInGameAudio(Globals.SND_SRC + "vivaldi.wav");
        } else if(pony){
            loadInGameAudio(Globals.SND_SRC + "mlp.wav");
        } else {
            loadInGameAudio(Globals.SND_SRC + "snkop.wav");
        }

        if(mapName.equals("easy")){
            loadWinAudio(Globals.SND_SRC + "scream.wav");
            ((FloatControl) mWin.getControl(FloatControl.Type.MASTER_GAIN)).setValue((float) 6.0206);
        } else {
            loadWinAudio(Globals.SND_SRC + "victory.wav");
        }

        gh = new Ghost[numOfGhosts];

        if(pony) {
            for (int i = 0; i < gh.length; i++)
                gh[i] = new Ghost(map.getGhostPos(i), "pony" + String.valueOf(i), this, 8);
        } else {
            for (int i = 0; i < gh.length; i++)
                gh[i] = new Ghost(map.getGhostPos(i), "ghost" + String.valueOf(i), this, 8);
        }

        setResizable(false);                                                                         /**kordinátáknak*/
        setSize(Globals.TILE_SIZE*map.getWidthWithBlock()+6, Globals.TILE_SIZE*map.getHeightWithBlock()+29+100);
        setVisible(true);
        g = getContentPane().getGraphics();
        imgBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        gBuffer = imgBuffer.getGraphics();
        addKeyListener(p);


        stopAudio();
        mIngame.start();
    }

    public Map getMap(){
        return map;
    }

    public static int getNumOfGhosts(){
        return numOfGhosts;
    }

    public static String getMapName(){
        return mapName;
    }

    public static boolean getMario(){
        return mario;
    }

    public static boolean getPony(){
        return pony;
    }

    public static double getTime(){
        return time;
    }

    public static void setNumOfGhosts(int n){
        numOfGhosts = n;
    }

    public static void setMapName(String map){
        mapName = map;
    }

    public static void setMario(boolean b){
        mario = b;
    }

    public static void setPony(boolean b){
        pony = b;
    }

    public static void setTime(double t){
        time = t;
    }

    public void run(){
        long startTime = System.currentTimeMillis();
        double timeSinceLastUpdate = 0.0;

        while (isVisible() && running){
            long currentTime = System.currentTimeMillis();
            double dt = (double)(currentTime - startTime) / 1000.0;
            timeSinceLastUpdate += dt;
            startTime = currentTime;

            while (timeSinceLastUpdate > Globals.TIME_PER_FRAME) {
                timeSinceLastUpdate -= Globals.TIME_PER_FRAME;

                update(Globals.TIME_PER_FRAME);
            }

            draw();
        }
    }

    private void update(double dt){
        p.update(dt);

        for(Ghost g: gh){
            if (running && p.getPos().getIntPos().equals(g.getPos().getIntPos())) {
                bLose = true;
                gameOver();
            }
        }

        for(int i = 0; i < gh.length; i++){
            gh[i].update(dt);
        }

        for(Ghost g: gh){
            if (running && p.getPos().getIntPos().equals(g.getPos().getIntPos())) {
                bLose = true;
                gameOver();
            }
        }

        if(running && (int) time <= 0){
            bLose = true;
            gameOver();
        }

        if(running && score / 100 == map.getCoins()){
            bWin = true;
            score += (int) time * 10;
            gameOver();
        }


        time -= dt;
    }

    private void draw() {
        map.draw(gBuffer);
        p.draw(gBuffer);

        for (int i = 0; i < gh.length; i++) {
            gh[i].draw(gBuffer);
        }

        gBuffer.setColor(Color.black);
        gBuffer.fillRect(0, 30 * map.getHeightWithBlock(), 30 * map.getWidthWithBlock(), 100);
        gBuffer.setColor(Color.white);
        gBuffer.setFont(new Font(gBuffer.getFont().getFontName(), gBuffer.getFont().getStyle(), 40));
        gBuffer.drawString("Score:", 0, map.getHeightWithBlock() * 30 + 30);
        gBuffer.drawString(String.valueOf(score), 115, map.getHeightWithBlock() * 30 + 30);
        gBuffer.drawString("Time:", 0, map.getHeightWithBlock() * 30 + 80);
        gBuffer.drawString(String.valueOf((int) time), 115, map.getHeightWithBlock() * 30 + 80);

        if (bWin && !mapName.equals("easy")){
            try {
                BufferedImage winImg = ImageIO.read(new File(Globals.IMG_SRC + "win.png"));
                gBuffer.drawImage(winImg, map.getWidthWithBlock() * Globals.TILE_SIZE / 2 - winImg.getWidth() / 2, map.getHeightWithBlock() * Globals.TILE_SIZE / 2 - winImg.getHeight() / 2, null);
            } catch (IOException e) {
            }
        } else if (bWin && mapName.equals("easy")) {
            try {
                BufferedImage bImg = ImageIO.read(new File(Globals.IMG_SRC + "sc.png"));
                gBuffer.drawImage(bImg, map.getWidthWithBlock() * Globals.TILE_SIZE / 2 - bImg.getWidth() / 2, map.getHeightWithBlock() * Globals.TILE_SIZE / 2 - bImg.getHeight() / 2, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(bLose) {
            try {
                BufferedImage gameOverImg = ImageIO.read(new File(Globals.IMG_SRC + "gameover.png"));
                gBuffer.setColor(Color.black);
                gBuffer.fillRect(0, 0, 30 * map.getWidthWithBlock(), 30 * map.getHeightWithBlock());
                gBuffer.setColor(Color.white);
                gBuffer.drawImage(gameOverImg, map.getWidthWithBlock() * Globals.TILE_SIZE / 2 - gameOverImg.getWidth() / 2, map.getHeightWithBlock() * Globals.TILE_SIZE / 2 - gameOverImg.getHeight() / 2, null);
            } catch (IOException e) {
            }
        }
        g.drawImage(imgBuffer, 0, 0, null);
    }

    public void givePoint(int s){
        score += s;
    }

    private void gameOver(){
        running = false;
        ScoreBoard.add(p.getName(), score);

        if (bWin) {
            stopAudio();
            mWin.start();
        }

        if (bLose){
            stopAudio();
            mLose.start();
        }
    }

    private void loadInGameAudio(String ingame){
        try {
            mIngame = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            System.out.println("Sorry no audio.");
        }

        try {
            File audioFile = new File(ingame);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            mIngame = (Clip) AudioSystem.getLine(info);
            mIngame.open(audioStream);
            ((FloatControl) mIngame.getControl(FloatControl.Type.MASTER_GAIN)).setValue(-15);
            mIngame.loop(Clip.LOOP_CONTINUOUSLY);
            mIngame.stop();
        } catch (Exception e) {
            System.out.println("Coldn't read file: " + ingame);
        }




    }

    private void loadWinAudio(String win) {
        try{
            mWin = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            System.out.println("Sorry no audio.");
        }

        try {
            File audioFile = new File(win);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            mWin = (Clip) AudioSystem.getLine(info);
            mWin.open(audioStream);
            ((FloatControl) mWin.getControl(FloatControl.Type.MASTER_GAIN)).setValue(-50);
            mWin.loop(Clip.LOOP_CONTINUOUSLY);
            mWin.stop();
        } catch (Exception e) {
            System.out.println("Coldn't read file: " + win);
        }
    }

    private void loadLoseAudio(String lose) {
        try {
            mLose = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            System.out.println("Sorry no audio.");
        }

        try {
            File audioFile = new File(lose);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            mLose = (Clip) AudioSystem.getLine(info);
            mLose.open(audioStream);
            ((FloatControl) mLose.getControl(FloatControl.Type.MASTER_GAIN)).setValue(-15);
            mLose.loop(Clip.LOOP_CONTINUOUSLY);
            mLose.stop();
        } catch (Exception e) {
            System.out.println("Coldn't read file: " + lose);
        }
    }

    private void stopAudio(){
        mIngame.stop();
        mWin.stop();
        mLose.stop();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        stopAudio();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
