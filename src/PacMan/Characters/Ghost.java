package PacMan.Characters;

import PacMan.*;
import PacMan.Utility.Direction;
import PacMan.Utility.Position;

/**
 * Created by Norbi on 2016. 11. 26..
 */
public class Ghost extends Character{

    public Ghost(int x, int y, String s, Game g, double speed) {
        super(x, y, s, g, speed);
    }

    public Ghost(Position pos, String s, Game g, double speed) {
        super(pos.getIntPos().getX(), pos.getIntPos().getY(), s, g, speed);
    }

    public void update(double dt){
        super.update(dt);

        int okay = (int)(Math.random()*101);

        if(okay >= 98 || di == Direction.STOP){
            int n = (int)(Math.random()*4);

            switch (n){
                case 0:
                    setWantedDirection(Direction.LEFT);
                    break;
                case 1:
                    setWantedDirection(Direction.RIGHT);
                    break;
                case 2:
                    setWantedDirection(Direction.UP);
                    break;
                case 3:
                    setWantedDirection(Direction.DOWN);
                    break;
                default:
                    break;
            }
        }
    }

}
