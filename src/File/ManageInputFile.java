package src.File;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import src.Exception.FileError;
import src.Exception.ValidationError;

public class ManageInputFile {
    private static final Set<String> VALID_TYPES = Set.of("Helicopter", "Baloon", "JetPlane");
    private static final Map<Character, String> TYPE_PREFIXES = Map.of(
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
    
    public static void parseFile(StringBuffer fileContent){
        Map<String, List<Integer>> aircrafMap = new HashMap<>();
        aircrafMap.put("Helicopter", new ArrayList<>());
        aircrafMap.put("Baloon", new ArrayList<>());
        aircrafMap.put("JetPlane", new ArrayList<>());
        List<List<String>> aircraftFinalList = new ArrayList<>(); 

        String[] lines = fileContent.toString().split("\n");
        
        if (lines.length < 2) {
            throw new ValidationError.LineLength();
        }
        
        int expectedCount;
        try {
            expectedCount = Integer.parseInt(lines[0].trim());
        } catch (NumberFormatException e) {
            throw new ValidationError.IntegerConvertion();
        }
        System.out.println(expectedCount);
        
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            System.out.println(line);
            if (line.isEmpty()) continue;
            parseLine(line, aircrafMap, i);
        }
    }
    
    private static void parseLine(String line,
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
        double longitude = Double.parseDouble(parts[2]);
        if (longitude < -180 || longitude > 180) {
            throw new ValidationError("Longitude must be between -180 and 180, found: " + longitude);
        }
        
        // Parse and validate LATITUDE
        double latitude = Double.parseDouble(parts[3]);
        if (latitude < -90 || latitude > 90) {
            throw new ValidationError("Latitude must be between -90 and 90, found: " + latitude);
        }

        // Parse and validate HEIGHT
        int height = Integer.parseInt(parts[4]);
        if (height <= 0 || height > 100) {
            throw new ValidationError("Height must be between 0 and 100, found: " + height);
        }
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
