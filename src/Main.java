package src;

import java.io.IOException;

import src.Exception.FileError;
import src.File.ManageInputFile;

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
            ManageInputFile.parseFile(fileText);


        }catch(FileError.ErrorNoArgument | 
            FileError.ErrorTooManyArgument e){
            System.err.println("Error Arguments " + e);
        }catch(FileError.FileNotFound |
                FileError.FileEmpty e){
            System.err.println("Error File " + e);
        }catch(UnsupportedOperationException e){
            System.err.println(e);
        }
    }
}