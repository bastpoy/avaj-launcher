package src.Aircraft;

public class JetPlane extends Aircraft{
    JetPlane(long p_id, String p_name, Coordinates p_coordinate){
        super(p_id, p_name, p_coordinate);
        System.out.println("JetPlane constructor");
    }
}