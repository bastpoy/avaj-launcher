package src.Tower;
import src.Aircraft.Coordinates;

public class WeatherProvider {
    private static final WeatherProvider weatherProvider = new WeatherProvider();

    public static WeatherProvider singleton(){
        return weatherProvider;
    }

    public String getCurrentWeather(Coordinates p_coordinates) {
        int longitude = p_coordinates.getLongitude();
        int latitude = p_coordinates.getLatitude();
        int height = p_coordinates.getHeight();

        double baseValue = Math.sin(Math.toRadians(latitude * 2)) + 
                          Math.cos(Math.toRadians(longitude * 1.5));
        
        baseValue = (baseValue + 2) / 4.0;
        
        double heightFactor = height / 100.0;
        
        double coldFactor = heightFactor + (1 - Math.abs(latitude) / 90.0) * 0.3;
        double moistureFactor = baseValue + heightFactor * 0.2;
        
        double continentalFactor = Math.abs(Math.sin(Math.toRadians(longitude * 3)));
        moistureFactor += continentalFactor * 0.2;
        
        if (heightFactor > 0.7 && moistureFactor > 0.6) {
            return "FOG"; 
        }
        
        if (coldFactor > 0.6 && moistureFactor > 0.5) {
            return "SNOW";
        }
        
        if (moistureFactor > 0.7 && coldFactor < 0.5) {
            return "RAIN";
        }
        return "SUN";
    }
}
