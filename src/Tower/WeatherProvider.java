package src.Tower;
import src.Aircraft.Coordinates;

public class WeatherProvider {
    private static final WeatherProvider weatherProvider = new WeatherProvider();

    public static WeatherProvider singleton(){
        return weatherProvider;
    }

    public String getCurrentWeather(Coordinates p_coordinates) {
    int longitude = p_coordinates.getLongitude() % 90;
    int latitude = p_coordinates.getLatitude() % 90;
    int height = p_coordinates.getHeight();
    
    double normalizedLat = Math.abs(latitude) / 90.0;
    double normalizedHeight = height / 100.0;
    
    double longitudeVariation = (Math.sin(Math.toRadians(longitude * 2.7)) + 1) / 2.0;
    
    double temperatureFactor = normalizedLat * 0.6 + normalizedHeight * 0.4;
    
    double moistureFactor = longitudeVariation * 0.5 + 
                           (1 - normalizedLat) * 0.3 + 
                           normalizedHeight * 0.2;
    
    if (normalizedHeight > 0.7 && moistureFactor > 0.6) {
        return "FOG";
    }
    
    if (temperatureFactor > 0.6 && moistureFactor > 0.4) {
        return "SNOW";
    }
    
    if (temperatureFactor < 0.5 && moistureFactor > 0.65) {
        return "RAIN";
    }
    
    return "SUN";
    }
}
