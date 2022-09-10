package Data;

public class House {

    private String name;
    private int year;
    private Integer numberOfFloors;
    private Long numberOfFlatsOnFloor;
    private Long numberOfLifts;

    public House(String name, int year, Integer numberOfFloors, Long numberOfFlatsOnFloor, Long numberOfLifts ) {
        this.name = name;
        this.year = year;
        this.numberOfFloors = numberOfFloors;
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
        this.numberOfLifts = numberOfLifts;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public Integer getNumberOfFloors() {
        return numberOfFloors;
    }

    public Long getNumberOfFlatsOnFloor() {
        return numberOfFlatsOnFloor;
    }

    public Long getNumberOfLifts() {
        return numberOfLifts;
    }

    @Override
    public String toString() {
        String info = "";

        info += "Name: " + name;
        info += "\n Year: " + year;
        info += "\n Number Of Floors : " + numberOfFloors;
        info += "\n Number Of Flats On Floor: " + numberOfFlatsOnFloor;
        info += "\n Number Of Lifts: " + numberOfLifts;

        return info;
    }
}
