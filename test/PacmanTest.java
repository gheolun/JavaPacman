import PacMan.Characters.PacMan;
import PacMan.Utility.Position;
import javafx.geometry.Pos;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import PacMan.Game;

/**
 * Created by Norbi on 2016. 11. 30..
 */
public class PacmanTest {
    private static PacMan p;
    private static Game g;

    @BeforeClass
    public static void BeforeClass(){
        g = new Game();
        p = new PacMan(1, 1, "mario", g, 3);
    }

    @Test
    public void posTest(){
        Position pos = p.getPos();
        Assert.assertEquals(true, new Position(1,1).equals(pos));
    }
}
