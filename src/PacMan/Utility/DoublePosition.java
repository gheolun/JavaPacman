package PacMan.Utility;

/**
 * Created by Norbi on 2016. 10. 16..
 */
public class DoublePosition {
    private double x, y;

    public DoublePosition(double xPos, double yPos){
        x = xPos;
        y = yPos;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public boolean equals(DoublePosition dP){
        return (x == dP.getX() && y == dP.getY());
    }
}
