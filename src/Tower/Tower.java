package src.Tower;

import java.util.ArrayList;
import java.util.List;
import src.Aircraft.Flyable;

public class Tower {
    private List<Flyable> observers;
    private final int simulationNumber;

    Tower(int p_simulationNumber){
        observers = new ArrayList<Flyable>();
        simulationNumber = p_simulationNumber;
    };

    public void register(Flyable p_flyable){
        observers.add(p_flyable);
        System.out.println(p_flyable.getName() + " register from the tower");
    };
    public void unregister(Flyable p_flyable){
        observers.remove(p_flyable);
        System.out.println(p_flyable.getName() + " unregister from the tower");
    };
    public List<Flyable> getObservers(){return observers;}
    public int getSimulationNumber(){return simulationNumber;}
    
    protected void conditionChanged(){
        for(int i = 0; i < observers.size(); i++){
            Flyable observer = observers.get(i);
            observer.updateConditions();
            if(observer.getHeight() <= 0){
                this.unregister(observer);
            }
        }
    };
}