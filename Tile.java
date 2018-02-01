public class Tile {
    //the coordinates of each tile
    private int xCoordinate;
    private int yCoordinate;

    //boolean variable that shows whether or not a tile
    private boolean occupied;

    //Tile constructor
    public Tile(int xCoordinate, int yCoordinate, boolean occupied){
        this.xCoordinate=xCoordinate;
        this.yCoordinate=yCoordinate;
        this.occupied=occupied;
    }

    //Accessor methods
    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public boolean isOccupied() {
        return occupied;
    }

    //Mutator methods
    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
