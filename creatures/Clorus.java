package creatures;

import huglife.*;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import static huglife.HugLifeUtils.random;
import static huglife.HugLifeUtils.randomEntry;


public class Clorus extends Creature {
    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;


    public Clorus(double e){
        super("clorus");
        r = 0;
        b = 0;
        g = 0;
        energy = e;
    }

    public Clorus(){
        this(1);
    }

    @Override
    public Color color(){
        r = 34;
        b = 231;
        g = 0;
        return color(r,g, b);
    }

    @Override
    public void move() {
        this.energy -= 0.03;
    }

    @Override
    public void attack(Creature c) {
        this.energy += c.energy();
    }

    @Override
    public Clorus replicate() {
        Clorus offspring = new Clorus(this.energy*0.50);
        this.energy *= 0.50;
        return null;
    }

    @Override
    public void stay() {
        this.energy -= 0.01;
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();
        for(Direction direction : neighbors.keySet()){
            Occupant typeOfOccupant = neighbors.get(direction);
            if(typeOfOccupant.name().equals("empty")){
                emptyNeighbors.add(direction);
            }else if(typeOfOccupant.name().equals("plip")){
                plipNeighbors.add(direction);
            }
        }
        if(emptyNeighbors.isEmpty()){
            return new Action(Action.ActionType.STAY);
        }else if(!plipNeighbors.isEmpty()){
            return new Action(Action.ActionType.ATTACK, randomEntry(plipNeighbors));
        }else if(this.energy >=1){
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors) );
        }else{
            return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
        }
    }


}
