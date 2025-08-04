package src.Aircraft;

public class Coordinates {
    private int latitude;
    private int longitude;
    private int height;

    public Coordinates(int p_latitude, int p_longitude, int p_height){
        this.latitude = p_latitude;
        this.longitude = p_longitude;
        this.height = p_height;
    }
    
    //getter
    public int getLongitude() {return latitude;}
    public int getLatitude() {return longitude;}
    public int getHeight() {return height;}

    //setter
    public void setLongitude(int longitude) {this.longitude = longitude;}
    public void setLatitude(int latitude) {this.latitude = latitude;}
    public void setHeight(int height) {this.height = height;}
}
