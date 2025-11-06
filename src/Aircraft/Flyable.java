package src.Aircraft;
import src.Tower.WeatherTower;

public abstract class Flyable {
    protected WeatherTower weatherTower;

    public abstract void updateConditions();
    public abstract String getName();
    public abstract Coordinates getCoordinates();
    public abstract int getHeight();
    public abstract long getId();
    public void registerTower(WeatherTower p_tower){
        weatherTower = p_tower;
    };
}
