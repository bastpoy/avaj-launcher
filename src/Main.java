package src;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import src.Aircraft.Flyable;
import src.Exception.FileError;
import src.Exception.ValidationError;
import src.File.ManageInputFile;
import src.Tower.*;

class Main {
    public static void main( String [] args ) throws IOException{
        PrintStream fileOut = new PrintStream(new FileOutputStream("simulation.txt"));
        System.setOut(fileOut);
        try{

            if(args.length < 1){
                throw new FileError.ErrorNoArgument();
            }
            else if(args.length > 1){
                throw new FileError.ErrorTooManyArgument();
            }

            StringBuffer fileText = ManageInputFile.readFile(args[0]);
            if(fileText.isEmpty()){
                throw new FileError.FileEmpty(args[0]);
            }
            List<Flyable> flyableList = new ArrayList<>();
            WeatherTower tower = ManageInputFile.parseFile(fileText, flyableList);
            
            for(Flyable flyable : flyableList){
                tower.register(flyable);
                flyable.registerTower(tower);
            }
            

            for(int i = 0; i < tower.getSimulationNumber(); i++){
                tower.changeWeather();
            }

        }catch(FileError.ErrorNoArgument | 
            FileError.ErrorTooManyArgument e){
            System.err.println("Error Arguments " + e);
        }catch(FileError.FileNotFound |
                FileError.FileEmpty e){
            System.err.println("Error File " + e);
        }catch(UnsupportedOperationException e){
            System.err.println(e);
        }catch (NumberFormatException e) {
            System.err.println(e);
        }catch(ValidationError e){
            System.err.println(e);
        } finally {
            // Close the file stream
            fileOut.close();
        }
    }
}