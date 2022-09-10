package Data;

public class Coordinates{
    private Double x;
    private int y;

    public Coordinates(Double x, int y){

        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return "Coordinates{" + "x=" + x + ", y=" + y + "}";
    }

    public Double getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
