package src.Aircraft;

public class JetPlane extends Aircraft{
    JetPlane(long p_id, String p_name, Coordinates p_coordinate){
        super(p_id, p_name, p_coordinate);
        // System.out.println(ANSI_RED + "JetPlane constructor" + ANSI_RESET);
    }

    public void updateConditions(){
        String weather = weatherTower.getWeather(coordinates);
        weatherTower.setNewWeather(weather);

        if(weather != weatherTower.getoldWeather()){
            weatherTower.changeWeather();
        }
        
        if(weather == "SUN"){
            coordinates.setLatitude(coordinates.getLatitude() + 10);
            coordinates.setHeight(coordinates.getHeight() + 2);
            if(coordinates.getHeight() > 100)
                coordinates.setHeight(100);
        }else if(weather == "RAIN"){
            coordinates.setLatitude(coordinates.getLatitude() + 5);
        }else if (weather == "FOG"){
            coordinates.setLatitude(coordinates.getLatitude() + 1);
        }else{
            coordinates.setHeight(coordinates.getHeight() - 7);
        }
        this.printMessage(weather);
    };
}