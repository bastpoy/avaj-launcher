package src;

import java.io.IOException;
import java.util.*;

import src.Aircraft.Flyable;
import src.Exception.FileError;
import src.Exception.ValidationError;
import src.File.ManageInputFile;
import src.Tower.*;

class Main {
    public static void main( String [] args ) throws IOException{
        System.out.println("Cobra 17 a Dela Charlie Weather Station " + args.length);
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
            
            WeatherTower tower = ManageInputFile.parseFile(fileText);
            List<Flyable> flyables = tower.getObservers();

            for(Flyable flyable : flyables){
                flyable.updateConditions();
                if(flyable.getHeight() <= 0){
                    tower.unregister(flyable);
                }
                if(tower.getSimulationNumber() <= 0){
                    break;
                }
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
        }
    }
}