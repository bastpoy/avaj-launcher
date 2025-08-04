package src;

import java.io.IOException;

import src.Exception.MyException;
import src.File.ManageInputFile;

class Main {
    public static void main( String [] args ) throws IOException{
        System.out.println("Cobra 17 a Dela Charlie Weather Station " + args.length);
        try{
            if(args.length < 1){
                throw new MyException.ErrorNoArgument();
            }
            else if(args.length > 1){
                throw new MyException.ErrorTooManyArgument();
            }

            StringBuffer fileText = ManageInputFile.readFile(args[0]);
            if(fileText.isEmpty()){
                throw new MyException.FileEmpty(args[0]);
            }
            ManageInputFile.parseFile(fileText);


        }catch(MyException.ErrorNoArgument | 
            MyException.ErrorTooManyArgument e){
            System.err.println("Error Arguments " + e);
        }catch(MyException.FileNotFound |
                MyException.FileEmpty e){
            System.err.println("Error File " + e);
        }
    }
}