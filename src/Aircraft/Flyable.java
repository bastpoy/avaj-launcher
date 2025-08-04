package src.Aircraft;

import src.Tower.WeatherTower;

public interface Flyable {
    public void updateConditions();
    default void registerTower(WeatherTower p_tower){};
}
