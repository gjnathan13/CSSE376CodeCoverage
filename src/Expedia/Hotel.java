package Expedia;

public class Hotel implements Booking {
	private int numberOfNightsToRent;
	
	public IDatabase Database;
	
	public int getMiles(){
		return 0;
	}
	
    //Returns the number of rooms available in the hotel
	public int AvailableRooms()
	{
		return Database.Rooms.size();	
	}

    //Returns the occupant of a room in the hotel
    public String getRoomOccupant(int roomNumber)
    {
        return Database.getRoomOccupant(roomNumber);
             
    }
	
	public double getBasePrice ()
	{
		return 45 * numberOfNightsToRent;
	}
	
	public Hotel (int nightsToRent)
	{
		if(nightsToRent <= 0)
			throw new RuntimeException("Nights to rent must be greater than zero!");
		
		numberOfNightsToRent = nightsToRent;
	}
}
