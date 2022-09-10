package Data;

public class Flat implements Comparable<Flat> {
    public Long id;
    public String name;
    public Coordinates coordinates;
    public String creationDate;
    public Integer area;
    public Long numberOfRooms;
    public Boolean newbie;
    public Furnish furnish;
    public View view;
    public House house;

    public Flat(Long id, String name, Coordinates coordinates, String creationDate, Integer area, Long numberOfRooms, Boolean newbie, Furnish furnish, View view, House house){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.numberOfRooms =numberOfRooms;
        this.newbie = newbie;
        this.furnish = furnish;
        this.view= view;
        this.house= house;
    }

    public Flat() {}

    @Override
    public String toString(){
        String info = "";

        info += "Id: " + id;
        info += "\n Name: " + name;
        info += "\n Coordinates: " + coordinates;
        info += "\n creationDate: " + creationDate;
        info += "\n Area: " + area;
        info += "\n Number Of Rooms " + numberOfRooms;
        info += "\n New: " + newbie;
        info += "\n Furnish: " + furnish;
        info += "\n View: " + view;
        info += "\n House: \n" + house;

        return info;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Coordinates getCoordinates(){
        return this.coordinates;
    }

    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }

    public String getCreationDate(){
        return this.creationDate;
    }

    public void setCreationDate(String creationDate){
        this.creationDate = creationDate;
    }

    public Integer getArea(){
        return this.area;
    }

    public void setArea(Integer area){
        this.area = area;
    }

    public Long getNumberOfRooms(){
        return this.numberOfRooms;
    }

    public void setNumberOfRooms(Long numberOfRooms){
        this.numberOfRooms = numberOfRooms;
    }

    public Boolean getNew(){
        return this.newbie;
    }

    public void setNew(Boolean newbie){
        this.newbie = newbie;
    }

    public Furnish getFurnish(){
        return this.furnish;
    }

    public void setFurnish(Furnish furnish){
        this.furnish = furnish;
    }

    public View getView(){
        return this.view;
    }

    public void setView(View view){
        this.view = view;
    }


    public House getHouse(){
        return this.house;
    }

    public void setHouse(House house){
        this.house = house;
    }

    @Override
    public int compareTo(Flat flatObj) {
        return 1;
    }
}
