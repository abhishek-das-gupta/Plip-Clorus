package creatures;
import huglife.*;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;
public class TestClorus {

    @Test
    public void testBasic(){
        Clorus c  = new Clorus(4);
        assertEquals(4,c.energy(),0.01);
        c.move();
        assertEquals(3.97,c.energy(),0.01);
        c.stay();
        assertEquals(3.96,c.energy(),0.01);
        Clorus c1 = new Clorus();
        assertEquals(1,c1.energy(),0.01);

    }

    @Test
    public void testReplicate() {
        // TODO
        Clorus c = new Clorus(1);
        Clorus offspring = c.replicate();
        assertEquals(0.5,offspring.energy(), 0.01);
    }

    @Test
    public void testChoose() {

        // No empty adjacent spaces; stay.
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // No empty adjacent spaces, even if any plips are present; attack.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> topPlip = new HashMap<Direction, Occupant>();
        topPlip.put(Direction.TOP, new Plip());
        topPlip.put(Direction.BOTTOM, new Impassible());
        topPlip.put(Direction.LEFT, new Impassible());
        topPlip.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(topPlip);
        expected = new Action(Action.ActionType.ATTACK, Direction.TOP);

        assertEquals(expected, actual);

        // Two Empty spaces, two plips are seen; attack.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> twoPlips = new HashMap<Direction, Occupant>();
        twoPlips.put(Direction.TOP, new Empty());
        twoPlips.put(Direction.BOTTOM, new Plip());
        twoPlips.put(Direction.LEFT, new Empty());
        twoPlips.put(Direction.RIGHT, new Plip());

        actual = c.chooseAction(twoPlips);
        Action unexpected = new Action(Action.ActionType.STAY);

        assertNotEquals(unexpected, actual);


        // Energy >= 1; replicate towards an empty space.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> allEmpty = new HashMap<Direction, Occupant>();
        allEmpty.put(Direction.TOP, new Empty());
        allEmpty.put(Direction.BOTTOM, new Empty());
        allEmpty.put(Direction.LEFT, new Empty());
        allEmpty.put(Direction.RIGHT, new Empty());

        Action actual1 = c.chooseAction(allEmpty);
        Action unexpected1 = new Action(Action.ActionType.STAY);

        assertNotEquals(unexpected1, actual1);



        // Energy < 1; stay.
        c = new Clorus(.99);
        Action actual3 = c.chooseAction(twoPlips);
        Action unexpected3 = new Action(Action.ActionType.STAY);

        assertNotEquals(unexpected3, actual3);


        // Energy < 1; stay.
        c = new Clorus(.99);

        Action actual4 = c.chooseAction(topPlip);
        Action unexpected4 = new Action(Action.ActionType.STAY);

        assertNotEquals(unexpected4, actual4);


        // We don't have Cloruses yet, so we can't test behavior for when they are nearby right now.
    }
}
