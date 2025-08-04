package src.Exception;

public class ValidationError extends RuntimeException{
    public ValidationError(String errorMessage){
        super("Error parsing Validation: " + errorMessage);
    }

    public static class LineLength extends RuntimeException{
        public LineLength(){
            super("Not enough line in file");
        }
    }
    
    public static class IntegerConvertion extends RuntimeException{
        public IntegerConvertion(){
            super("First line of file need to me an integer");
        }
    }
}
