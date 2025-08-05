package src.Tower;

import java.util.ArrayList;
import java.util.List;
import src.Aircraft.Flyable;

public class Tower {
    private List<Flyable> observers;
    protected int simulationNumber;
    protected String oldWeather;
    protected String newWeather;

    Tower(int p_simulationNumber){
        observers = new ArrayList<Flyable>();
        simulationNumber = p_simulationNumber;
        oldWeather = "";
        newWeather = "";
    };

    public void register(Flyable p_flyable){
        observers.add(p_flyable);
        System.out.println(p_flyable.getName() + " register from the tower");
    };
    public void unregister(Flyable p_flyable){
        observers.remove(p_flyable);
        System.out.println(p_flyable.getName() + " unregister from the tower");
    };
    public String getoldWeather(){return oldWeather;}
    public List<Flyable> getObservers(){return observers;}
    public void setNewWeather(String weather){newWeather = weather;}
    public int getSimulationNumber(){return simulationNumber;}
    
    protected void conditionChanged(){
        simulationNumber--;
    };
}