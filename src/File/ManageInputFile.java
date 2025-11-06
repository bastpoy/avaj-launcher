package src.File;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import src.Aircraft.*;
import src.Tower.*;
import src.Exception.FileError;
import src.Exception.ValidationError;

public class ManageInputFile {
    private static final Set<String> VALID_TYPES = Set.of("Helicopter", "Baloon", "JetPlane");
    public static final Map<Character, String> TYPE_PREFIXES = Map.of(
        'H', "Helicopter",
        'B', "Baloon", 
        'J', "JetPlane"
    );
    
    public static StringBuffer readFile(String fileName) throws IOException, FileError.FileNotFound{
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileError.FileNotFound(fileName);
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
    
    public static WeatherTower parseFile(StringBuffer fileContent, List<Flyable> flyableArray) {
        Map<String, List<Integer>> aircrafMap = new HashMap<>();
        aircrafMap.put("Helicopter", new ArrayList<>());
        aircrafMap.put("Baloon", new ArrayList<>());
        aircrafMap.put("JetPlane", new ArrayList<>());

        String[] lines = fileContent.toString().split("\n");
        
        if (lines.length < 2) {
            throw new ValidationError.LineLength();
        }
        
        int simulationNumber = Integer.parseInt(lines[0].trim());
        if(simulationNumber < 0){
            throw new ValidationError("Number of simulation can't be negative");
        }        
        WeatherTower tower = new WeatherTower(simulationNumber);

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            // System.out.println(line);
            if (line.isEmpty()) continue;
            Flyable flyable = parseLine(line, aircrafMap, i);
            flyableArray.add(flyable);
        }
        return (tower);
    }
    
    private static Flyable parseLine(String line,
                          Map<String, List<Integer>> aircrafMap, int lineNumber){
        String[] parts = line.split("\\s");
        
        if (parts.length != 5) {
            throw new ValidationError("Each line must have exactly 5 attributes (TYPE NAME LONGITUDE LATITUDE HEIGHT), found " + parts.length);
        }
        
        // Validate TYPE
        String type = parts[0];
        if (!VALID_TYPES.contains(type)) {
            throw new ValidationError("Invalid type '" + type + "'. Must be one of: Helicopter, Baloon, JetPlane");
        }
        
        // Validate NAME
        String name = parts[1];
        Optional <String> returnMessage = isValidName(name, type, aircrafMap);
        if (returnMessage.isPresent()) {
            throw new ValidationError(returnMessage.toString());
        }
        
        // Parse and validate LONGITUDE
        int longitude = Integer.parseInt(parts[2]);
        
        // Parse and validate LATITUDE
        int latitude = Integer.parseInt(parts[3]);

        // Parse and validate HEIGHT
        int height = Integer.parseInt(parts[4]);
        if (height < 0) {
            throw new ValidationError("Height must be between 0 and 100, found: " + height);
        }
        if (height > 100)
            height = 100;
        Coordinates coord = new Coordinates(longitude, latitude, height);
        Flyable flyable = AircraftFactory.singleton().newAircraft(type, name, coord);
        return (flyable);
    }
    
    private static Optional<String> isValidName(String name, String type,
                        Map<String, List<Integer>> aircrafMap) {
        //VALIDATE CODE TYPE
        if (name.length() != 2)
            return Optional.of("Error code Type doesnt contain two words");

        //VALIDATE FIRST CHAR OF CODE TYPE
        char firstChar = name.charAt(0);
        String expectedType = TYPE_PREFIXES.get(firstChar);
        if (!type.equals(expectedType)) 
            return Optional.of("Error type Aicraft doesnt correspond to a type");
        
        //VALIDATE NUMBER CODE TYPE
        String numberPart = name.substring(1);
        int codeNumber;
        try {
            codeNumber = Integer.parseInt(numberPart);
        } catch (NumberFormatException e) {
            return Optional.of("Error number part isn't an integer");
        }
        
        //VALIDATE IF DUPLICATE NUMBER CODE TYPE
        List<Integer> numberTypeAicraft = aircrafMap.get(type);
        if(numberTypeAicraft == null)
            return Optional.of("Can't get aircraft type");
        if(numberTypeAicraft.contains(codeNumber))
            return Optional.of("Duplicate code number for type " + type);

        //ADD NEZ NUMEBR CODE TYPE
        try{
            numberTypeAicraft.add(codeNumber);
        }catch(UnsupportedOperationException e){
            return Optional.of("Error adding number to aircreaft list");
        }

        return Optional.empty();
    }

}
