package PacMan.Characters;

import PacMan.*;
import PacMan.Map.*;
import PacMan.Utility.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.io.File;
import java.io.IOException;

/**
 * Created by Norbi on 2016. 11. 26..
 */
abstract class Character {
    protected Game game;
    protected BufferedImage bImgCharacter;
    protected double speed = 10;
    protected String name;

    protected Position pos;
    protected Direction di = Direction.STOP;

    private int neededPosToChangeDirection = 0;
    protected Direction wantedDi = Direction.STOP;


    public Character(int x, int y, String s, Game g, double speed){
        pos = new Position(x, y);
        game = g;
        name = s;
        this.speed = speed;
        try {
            bImgCharacter = ImageIO.read(new File(Globals.IMG_SRC + s + ".png"));
        } catch (IOException e) {}
    }

    public Position getPos(){
        return pos;
    }

    public void setWantedDirection(Direction d){
        wantedDi = d;
        if(wantedDi != di){
            wantToNewDirection();
        }
    }

    public void update(double dt){
        if(wantedDi != di){
            updateDi();
        }
        updatePos(dt);
    }

    public void updateDi(){
        if((di == Direction.LEFT || di == Direction.RIGHT)&&(pos.getIntPos().getX() == neededPosToChangeDirection)){
            if(di == Direction.LEFT)
                pos.setAllXPos(neededPosToChangeDirection+1);
            else
                pos.setAllXPos(neededPosToChangeDirection);
            di = wantedDi;
        } else if ((di == Direction.UP || di == Direction.DOWN)&&(pos.getIntPos().getY() == neededPosToChangeDirection)){
            if(di == Direction.UP)
                pos.setAllYPos(neededPosToChangeDirection+1);
            else
                pos.setAllYPos(neededPosToChangeDirection);
            di = wantedDi;
        } else if (di == Direction.STOP){
            di = wantedDi;
        }
    }

    public void updatePos(double dt){
        DoublePosition dPos = pos.getDoublePos();
        IntPosition iPos = pos.getIntPos();

        double newX, newY;

        switch (di) {
            case UP:
                newY = dPos.getY() - dt * speed;

                if (game.getMap().getValue(iPos.getX(), (int) (newY)) == MapValue.EMPTY || game.getMap().getValue(iPos.getX(), (int) (newY)) == MapValue.COIN){
                    pos.setAllYPos(newY);
                }else if (newY % 1.0 >= 1 - Globals.MISTAKE_LIMIT) {
                    pos.setAllYPos((int) newY + 1);
                    di = Direction.STOP;
                }

                break;
            case DOWN:
                newY = dPos.getY() + dt * speed;

                if (game.getMap().getValue(iPos.getX(), (int) (newY + 1)) == MapValue.EMPTY || game.getMap().getValue(iPos.getX(), (int) (newY + 1)) == MapValue.COIN){
                    pos.setAllYPos(newY);
                }else if (newY % 1.0 <= Globals.MISTAKE_LIMIT) {
                    pos.setAllYPos((int) newY);
                    di = Direction.STOP;
                }
                break;
            case LEFT:
                newX = dPos.getX() - dt * speed;

                if (game.getMap().getValue((int) (newX), iPos.getY()) == MapValue.EMPTY || game.getMap().getValue((int) (newX), iPos.getY()) == MapValue.COIN) {
                    pos.setAllXPos(newX);
                }else if(newX%1.0 >= 1 - Globals.MISTAKE_LIMIT) {
                    pos.setAllXPos((int) newX + 1);
                    di = Direction.STOP;
                }
                break;
            case RIGHT:
                newX = dPos.getX() + dt * speed;

                if (game.getMap().getValue((int) (newX + 1), iPos.getY()) == MapValue.EMPTY || game.getMap().getValue((int) (newX + 1), iPos.getY()) == MapValue.COIN){
                    pos.setAllXPos(newX);
                }else if(newX%1.0 <= Globals.MISTAKE_LIMIT) {
                    pos.setAllXPos((int) newX);
                    di = Direction.STOP;
                }
                break;
            default:
                break;
        }
    }

    public void wantToNewDirection(){
        int prevPos;
        int changePosNeededTo; /** +1, -1 */

        /** prevPos */
        if(di == Direction.LEFT || di == Direction.RIGHT){
            prevPos = pos.getIntPos().getX();
        } else if(di == Direction.UP || di == Direction.DOWN){
            prevPos = pos.getIntPos().getY();
        } else {
            prevPos = 0;
        }
        /** needToChangePos */
        if(di == Direction.LEFT || di == Direction.UP){
            changePosNeededTo = -1;
        } else if(di == Direction.RIGHT || di == Direction.DOWN){
            changePosNeededTo = +1;
        } else {
            changePosNeededTo = 0;
        }
        neededPosToChangeDirection = prevPos + changePosNeededTo;
    }

    public void draw(Graphics g){
        DoublePosition dPos = pos.getDoublePos();

        g.drawImage(bImgCharacter, (int) (pos.getDoublePos().getX()*Globals.TILE_SIZE), (int) (pos.getDoublePos().getY()*Globals.TILE_SIZE), null);
    }
}
