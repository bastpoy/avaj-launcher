package src.Tower;

import java.util.List;
import src.Aircraft.Flyable;

public class Tower {
    private List<Flyable> observers;

    Tower(){};
    public void register(Flyable p_flyable){
        observers.add(p_flyable);
    };

    public void unregister(Flyable p_flyable){
        observers.remove(p_flyable);
    };
    
    protected void conditionChanged(){};
}