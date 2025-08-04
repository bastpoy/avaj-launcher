package src.Tower;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import src.Aircraft.Coordinates;

public class WeatherProvider {
    private String weather;

    private WeatherProvider(){};

    public static String generateWeather(Coordinates p_coordinates) {
    // public static String generateWeather(int longitude, int latitude, int height){
        int longitude = p_coordinates.getLongitude();
        int latitude = p_coordinates.getLatitude();
        int height = p_coordinates.getHeight();

        // Calculate a base value using coordinates
        // We use sine and cosine to create natural variation patterns
        double baseValue = Math.sin(Math.toRadians(latitude * 2)) + 
                          Math.cos(Math.toRadians(longitude * 1.5));
        
        // Normalize baseValue to 0-1 range
        baseValue = (baseValue + 2) / 4.0; // baseValue was between -2 and 2
        
        // Height influence: higher altitudes are colder and foggier
        double heightFactor = height / 100.0; // 0 to 1
        
        // Calculate weather factors
        double coldFactor = heightFactor + (1 - Math.abs(latitude) / 90.0) * 0.3;
        double moistureFactor = baseValue + heightFactor * 0.2;
        
        // Add some variation based on longitude (simulating continental vs oceanic)
        double continentalFactor = Math.abs(Math.sin(Math.toRadians(longitude * 3)));
        moistureFactor += continentalFactor * 0.2;
        
        // Decision logic with thresholds
        if (heightFactor > 0.7 && moistureFactor > 0.6) {
            return "FOG"; // High altitude + moisture = fog
        }
        
        if (coldFactor > 0.6 && moistureFactor > 0.5) {
            return "SNOW"; // Cold + moisture = snow
        }
        
        if (moistureFactor > 0.7 && coldFactor < 0.5) {
            return "RAIN"; // High moisture + not too cold = rain
        }
        return "SUN";
    }
}
