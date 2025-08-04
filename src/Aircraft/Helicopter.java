package src.Aircraft;

public class Helicopter extends Aircraft{
    Helicopter(long p_id, String p_name, Coordinates p_coordinate){
        super(p_id, p_name, p_coordinate);
        System.out.println("Helicopter constructor");
    }
    
    public void updateConditions(){
        String weather = weatherTower.getWeather(coordinates);
        
        if(weather == "SUN"){
            coordinates.setLongitude(coordinates.getLongitude() + 10);
            coordinates.setHeight(coordinates.getHeight() + 2);
        }else if(weather == "RAIN"){
            coordinates.setLongitude(coordinates.getLongitude() + 5);
        }else if (weather == "FOG"){
            coordinates.setLongitude(coordinates.getLongitude() + 1);
        }else{
            coordinates.setHeight(coordinates.getHeight() - 12);
        }
    };
}
