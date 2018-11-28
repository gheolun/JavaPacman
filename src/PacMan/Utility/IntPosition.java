package PacMan.Utility;

/**
 * Created by Norbi on 2016. 10. 16..
 */
public class IntPosition {
    private int x;
    private int y;

    public IntPosition(int xPos, int yPos){
        x = xPos;
        y = yPos;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public boolean equals(IntPosition iP){
        return (x == iP.getX() && y == iP.getY());
    }
}
