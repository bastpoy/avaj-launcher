package src.Tower;

import src.Aircraft.Coordinates;

public class WeatherTower extends Tower {
    public WeatherTower(int p_simulationNumber){
        super(p_simulationNumber);
    };

    public String getWeather(Coordinates p_coordinates){
        WeatherProvider weatherProvider = WeatherProvider.singleton();
        String actualWeather = weatherProvider.getCurrentWeather(p_coordinates);
        return (actualWeather);
    };

    public void changeWeather(){
        oldWeather = newWeather;
        this.conditionChanged();
    }
}