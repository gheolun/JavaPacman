package PacMan.Characters;

import PacMan.*;
import PacMan.Map.*;
import PacMan.Utility.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.io.File;
import java.io.IOException;
import java.lang.*;

/**
 * Created by Norbi on 2016. 10. 16..
 */
public class PacMan extends Character implements KeyListener {
    BufferedImage unRotatedImg = bImgCharacter;

    public PacMan(int x, int y, String s, Game g, double speed){
        super(x, y, s, g, speed);
    }

    public String getName(){
        return name;
    }

    public void update(double dt){
        super.update(dt);
        switch (di) {
            case LEFT:
                if(name == "pacman")
                    rotateImage(180);
                game.getMap().coinToEmpty(pos.getIntPos().getX() + 1, pos.getIntPos().getY());
                break;
            case UP:
                if(name == "pacman")
                    rotateImage(-90);
                game.getMap().coinToEmpty(pos.getIntPos().getX(), pos.getIntPos().getY() + 1);
                break;
            case RIGHT:
                if(name == "pacman")
                    rotateImage(0);
                game.getMap().coinToEmpty(pos.getIntPos().getX(), pos.getIntPos().getY());
                break;
            case DOWN:
                if(name == "pacman")
                    rotateImage(90);
                game.getMap().coinToEmpty(pos.getIntPos().getX(), pos.getIntPos().getY());
                break;
            case STOP:
                game.getMap().coinToEmpty(pos.getIntPos().getX(), pos.getIntPos().getY());
                break;
        }
    }

    @Override /* not used */
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                setWantedDirection(Direction.UP);
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                setWantedDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                setWantedDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                setWantedDirection(Direction.RIGHT);
                break;
            default:
                break;
        }


    }

    @Override /* not used */
    public void keyReleased(KeyEvent e) {

    }

    private void rotateImage(double degree){
        double rotationRequired = Math.toRadians (degree);
        double locationX = unRotatedImg.getWidth() / 2;
        double locationY = unRotatedImg.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        bImgCharacter = op.filter(unRotatedImg, null);
    }



    /**FONTOS :)
     int newX = getIntX(), newY = getIntY();
     double newDoubleX = getX(), newDoubleY = getY();
     if((di == Direction.LEFT && (newX = (int) (newDoubleX = x-dt*speed)) == -1) ||
     (di == Direction.RIGHT && (newX = (int) (newDoubleX = 1+x+dt*speed)) == -1) ||
     (di == Direction.DOWN && (newY = (int) (newDoubleY = 1+y+dt*speed)) == -1) ||
     (di == Direction.UP && (newY = (int) (newDoubleY = y-dt*speed)) == -1));
     if((map.getValue(newX, newY) == MapValue.EMPTY || map.getValue(newX, newY) == MapValue.COIN) &&
     ((di == Direction.LEFT || di == Direction.UP) && (((x = newDoubleX) == -1) || true) && (((y = newDoubleY) == -1 || true))) ||
     ((di == Direction.RIGHT) &&(((x = newDoubleX-1) == -1) || true) && (((y = newDoubleY) == -1) || true)) ||
     ((di == Direction.DOWN) &&(((x = newDoubleX) == -1) || true) && (((y = newDoubleY-1) == -1) || true)));
     */

}
