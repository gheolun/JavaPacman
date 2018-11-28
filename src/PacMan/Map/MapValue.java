package PacMan.Map;

/**
 * Created by Norbi on 2016. 10. 16..
 */
public enum MapValue {
    WALL_00("wall_00.png"),
    WALL_01("wall_01.png"),
    WALL_02("wall_02.png"),
    WALL_03("wall_03.png"),
    WALL_04("wall_04.png"),
    WALL_05("wall_05.png"),
    WALL_06("wall_06.png"),
    WALL_07("wall_07.png"),
    WALL_08("wall_08.png"),
    WALL_09("wall_09.png"),
    WALL_10("wall_10.png"),
    WALL_11("wall_11.png"),
    WALL_12("wall_12.png"),
    WALL_13("wall_13.png"),
    WALL_14("wall_14.png"),
    WALL_15("wall_15.png"),
    WALL_16("wall_16.png"),
    WALL_17("wall_17.png"),
    WALL_18("wall_18.png"),
    WALL_19("wall_19.png"),
    WALL_20("wall_20.png"),
    WALL_21("wall_21.png"),
    WALL_22("wall_22.png"),
    WALL_23("wall_23.png"),
    WALL_24("wall_24.png"),
    WALL_25("wall_25.png"),
    WALL_26("wall_26.png"),
    WALL_27("wall_27.png"),
    GHOST0("ghost_0.png"),
    GHOST1("ghost_1.png"),
    GHOST2("ghost_2.png"),
    GOHST3("ghost_3.png"),
    EMPTY("empty.png"),
    COIN("coin.png");

    private String fname;

    MapValue(String fname) {
        this.fname = fname;
    }

    public String getFName() {
        return fname;
    }
}
