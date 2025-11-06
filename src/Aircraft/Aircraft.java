package src.Aircraft;

import src.File.ManageInputFile;

abstract class Aircraft extends Flyable{
    protected long id;
    protected String name;
    protected Coordinates coordinates;
    protected final String ANSI_RESET = "\u001B[0m";
    protected final String ANSI_RED = "\u001B[31m";
    protected final String ANSI_GREEN = "\u001B[32m";
    protected final String ANSI_YELLOW = "\u001B[33m";
    protected final String ANSI_BLUE = "\u001B[34m";
    protected final String ANSI_PURPLE = "\u001B[35m";
    protected final String ANSI_CYAN = "\u001B[36m";
    protected final String ANSI_WHITE = "\u001B[37m";

    protected Aircraft(long p_id, String p_name, Coordinates p_coordinate){
        super();

        this.id = p_id;
        this.name = p_name;
        this.coordinates = p_coordinate;
        // System.out.println(ANSI_CYAN + "Aircraft constructor" + ANSI_RESET);
    }
    public String getName(){ return name;}
    public Coordinates getCoordinates(){return coordinates;}
    public long getId(){return id;}
    public int getHeight(){return coordinates.getHeight();}
    public void printMessage(String weather){
        String type = ManageInputFile.TYPE_PREFIXES.get(name.charAt(0));

        if(weather == "SUN"){
            System.out.println(type + "#" + this.name + "(" + this.id + 
            ")" + ": " + type + " is hot");
        }else if(weather == "RAIN"){
            System.out.println(type + "#" + this.name + "(" + this.id + 
            ")" + ": " + type + " is wet");
        }else if (weather == "FOG"){
            System.out.println(type + "#" + this.name + "(" + this.id + 
            ")" + ": " + type + " see nothing");
        }else{
            System.out.println(type + "#" + this.name + "(" + this.id + 
            ")" + ": " + type + " is cold");
        }
    }

    protected void verifyCoordinates(){
        if(coordinates.getHeight() > 100){
            coordinates.setHeight(100);
        }
    }
}