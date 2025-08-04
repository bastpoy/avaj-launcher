package src.Aircraft;

public class AircraftFactory {
    static long id = 0;
    private static final AircraftFactory factory = new AircraftFactory();
    
    private AircraftFactory(){};
    public static AircraftFactory singleton(){
        return (factory);
    }

    public Flyable newAircraft(String p_type, String p_name, Coordinates p_coordinates){
        if(p_type.contentEquals("Helicopter")){
            return new Helicopter(id++, p_name, p_coordinates);
        }else if(p_type.contentEquals("Baloon")){
            return new Baloon(id++, p_name, p_coordinates);
        }else if(p_type.contentEquals("JetPlane")){
            return new JetPlane(id++, p_name, p_coordinates);
        }
        return null;
    }
}
