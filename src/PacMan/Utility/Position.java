package PacMan.Utility;

/**
 * Created by Norbi on 2016. 11. 26..
 */
public class Position {
    private IntPosition iPos = new IntPosition(0, 0);
    private DoublePosition dPos = new DoublePosition(0, 0);


    public Position(int x, int y) {
        setIntPos(x, y);
        setDoublePos(x, y);
    }


    public IntPosition getIntPos(){return iPos;}

    public DoublePosition getDoublePos(){return dPos;}


    public void setIntPos(int x, int y){
        iPos.setX(x);
        iPos.setY(y);
    }

    public void setDoublePos(double x, double y){
        dPos.setX(x);
        dPos.setY(y);
    }

    public void setAllPos(double x, double y){
        setIntPos((int) x, (int) y);
        setDoublePos(x, y);
    }

    public void setAllXPos(double x){
        setAllPos(x, dPos.getY());
    }

    public void setAllYPos(double y){
        setAllPos(dPos.getX(), y);
    }

    public boolean equals(Position p){
        return (getIntPos().equals(p.getIntPos()) && getDoublePos().equals(p.getDoublePos()));
    }
}
