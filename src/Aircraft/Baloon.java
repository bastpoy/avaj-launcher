package src.Aircraft;

public class Baloon extends Aircraft{
    Baloon(long p_id, String p_name, Coordinates p_coordinate){
        super(p_id, p_name, p_coordinate);
        // System.out.println(ANSI_GREEN + "Baloon constructor" + ANSI_RESET);
    }

    public void updateConditions(){
        String weather = weatherTower.getWeather(coordinates);
        weatherTower.setNewWeather(weather);

        if(weather != weatherTower.getoldWeather()){
            weatherTower.changeWeather();
        }
        
        if(weather == "SUN"){
            coordinates.setLongitude(coordinates.getLongitude() + 2);
            coordinates.setHeight(coordinates.getHeight() + 4);
            if(coordinates.getHeight() > 100)
                coordinates.setHeight(100);
        }else if(weather == "RAIN"){
            coordinates.setHeight(coordinates.getHeight() - 5);
        }else if (weather == "FOG"){
            coordinates.setHeight(coordinates.getHeight() - 3);
        }else{
            coordinates.setHeight(coordinates.getHeight() - 15);
        }
        this.printMessage(weather);
    };
}
