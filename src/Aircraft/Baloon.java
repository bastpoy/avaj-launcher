package src.Aircraft;

public class Baloon extends Aircraft{
    Baloon(long p_id, String p_name, Coordinates p_coordinate){
        super(p_id, p_name, p_coordinate);
        System.out.println("Baloon constructor");
    }

    public void updateConditions(){
        String weather = weatherTower.getWeather(coordinates);
        
        if(weather == "SUN"){
            coordinates.setLongitude(coordinates.getLongitude() + 2);
            coordinates.setHeight(coordinates.getHeight() + 4);
        }else if(weather == "RAIN"){
            coordinates.setHeight(coordinates.getHeight() - 5);
        }else if (weather == "FOG"){
            coordinates.setHeight(coordinates.getHeight() - 3);
        }else{
            coordinates.setHeight(coordinates.getHeight() - 15);
        }
    };
}
