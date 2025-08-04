package src.Aircraft;

public class Helicopter extends Aircraft implements Flyable{
    Helicopter(long p_id, String p_name, Coordinates p_coordinate){
        super(p_id, p_name, p_coordinate);
        System.out.println("Helicopter constructor");
    }
}
