public class Room {

    public enum roomType{ // four room types
      DR, KR, KS, PS
    }
    
    double price;
    roomType type;

    public Room(double price, roomType type){ // Creat a room object with price and type.
        this.price = price;
        this.type = type;
    }

      public double getPrice() {
        return price; // return the price of the room object
      }

      public roomType getRoomType() {
        return type; // return the type of the room object
      }

}
