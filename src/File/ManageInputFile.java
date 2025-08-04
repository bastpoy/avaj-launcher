package src.File;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import src.Aircraft.Aircraft;
import src.Exception.MyException;

public class ManageInputFile {
    private static final Set<String> VALID_TYPES = Set.of("Helicopter", "Baloon", "JetPlane");
    private static final Map<Character, String> TYPE_PREFIXES = Map.of(
        'H', "Helicopter",
        'B', "Baloon", 
        'J', "JetPlane"
    );
    
    public static StringBuffer readFile(String fileName) throws IOException, MyException.FileNotFound{
        File file = new File(fileName);
        if (!file.exists()) {
            throw new MyException.FileNotFound(fileName);
        }
        try(
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);  
            ){

            StringBuffer sb = new StringBuffer();    
            String line;
            while((line = br.readLine()) != null)
            {
                sb.append(line);
                sb.append("\n");
            }
            return (sb);
        }catch(IOException e){
            System.err.println("Error File: " + e);
        }
        return new StringBuffer();
    }
    
    public static void parseFile(StringBuffer fileContent){
        Set<String> usedNumbers = new HashSet<>(); // Track used numbers across all types
        
        String[] lines = fileContent.toString().split("\n");
        
        // Skip first line (should be the count)
        if (lines.length < 2) {
            // throw new ValidationException("File must contain at least a count line and one data line");
        }
        
        int expectedCount;
        try {
            expectedCount = Integer.parseInt(lines[0].trim());
        } catch (NumberFormatException e) {
            // throw new ValidationException("First line must be a valid number indicating count of records");
        }
        
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;
            
            try {
                parseLine(line, usedNumbers, i);
            } catch (ValidationException e) {
                // throw new ValidationException("Line " + i + ": " + e.getMessage());
            }
        }
        
    }
    
    private static void parseLine(String line, Set<String> usedNumbers, int lineNumber){
        String[] parts = line.split("\\s");
        
        // Check if all 5 attributes are present
        if (parts.length != 5) {
            // throw new ValidationException("Each line must have exactly 5 attributes (TYPE NAME LONGITUDE LATITUDE HEIGHT), found " + parts.length);
        }
        
        // Validate TYPE
        String type = parts[0];
        if (!VALID_TYPES.contains(type)) {
            // throw new ValidationException("Invalid type '" + type + "'. Must be one of: Helicopter, Baloon, JetPlane");
        }
        
        // Validate NAME
        String name = parts[1];
        if (!isValidName(name, type, usedNumbers)) {
            // throw new ValidationException("Invalid name '" + name + "'. Must be first letter of type + number, and number must be unique across all types");
        }
        
        // Parse and validate LONGITUDE
        double longitude;
        try {
            longitude = Double.parseDouble(parts[2]);
            if (longitude < -180 || longitude > 180) {
                // throw new ValidationException("Longitude must be between -180 and 180, found: " + longitude);
            }
        } catch (NumberFormatException e) {
            // throw new ValidationException("Invalid longitude format: " + parts[2]);
        }
        
        // Parse and validate LATITUDE
        double latitude;
        try {
            latitude = Double.parseDouble(parts[3]);
            if (latitude < -90 || latitude > 90) {
                // throw new ValidationException("Latitude must be between -90 and 90, found: " + latitude);
            }
        } catch (NumberFormatException e) {
            // throw new ValidationException("Invalid latitude format: " + parts[3]);
        }
        
        // Parse and validate HEIGHT
        int height;
        try {
            height = Integer.parseInt(parts[4]);
            if (height <= 0 || height > 100) {
                // throw new ValidationException("Height must be between 0 and 100, found: " + height);
            }
        } catch (NumberFormatException e) {
            // throw new ValidationException("Invalid height format: " + parts[4]);
        }
    }
    
    private static boolean isValidName(String name, String type, Set<String> usedNumbers) {
        if (name.length() < 2) return false;
        
        char firstChar = name.charAt(0);
        String expectedType = TYPE_PREFIXES.get(firstChar);
        
        // Check if first character matches the type
        if (!type.equals(expectedType)) {
            return false;
        }
        
        // Extract number part
        String numberPart = name.substring(1);
        try {
            Integer.parseInt(numberPart); // Validate it's a number
        } catch (NumberFormatException e) {
            return false;
        }
        
        // Check if number is already used
        if (usedNumbers.contains(numberPart)) {
            return false;
        }
        
        // Add to used numbers
        usedNumbers.add(numberPart);
        return true;
    }
}
