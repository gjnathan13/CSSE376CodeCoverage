package ExpediaTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import Expedia.Hotel;
import Expedia.IDatabase;
import ExpediaTest.FlightTest.FakeDatabase;

public class HotelTest
{
	private Hotel targetHotel;
	private int NightsToRentHotel = 5;
	
	@Before
	public void TestInitialize()
	{
		targetHotel = new Hotel(NightsToRentHotel);
	}
	
	@Test(expected=RuntimeException.class)
	public void TestThatBadHotelInitializeThrowsException(){
		Hotel badHotel = new Hotel(0);
	}
	
	@Test
	public void TestThatHotelHasCorrectBasePriceForOneDayStay()
	{
		Hotel target = new Hotel(1);
		Assert.assertEquals(45, target.getBasePrice(), 0.01);
	}
	
	@Test
	public void TestThatHotelHasCorrectBasePriceForTwoDayStay()
	{
		Hotel target = new Hotel(2);
		Assert.assertEquals(90, target.getBasePrice(), 0.01);
	}
	
	@Test
	public void TestThatHotelHasCorrectBasePriceForTenDaysStay()
	{
		Hotel target = new Hotel(10);
		Assert.assertEquals(450, target.getBasePrice(), 0.01);
	}
	
	@Test(expected=RuntimeException.class)
	public void TestThatHotelThrowsOnBadLength()
	{
		new Hotel(-5);
	}

	
	@Test
    public void TestThatHotelDoesGetRoomOccupantFromTheDatabase()
    {
		
		IDatabase mockDB = EasyMock.createMock(IDatabase.class);
        String roomOccupant = "Whale Rider";
        String anotherRoomOccupant = "Raptor Wrangler";
        
        EasyMock.expect(mockDB.getRoomOccupant(24)).andReturn(roomOccupant);
        EasyMock.expect(mockDB.getRoomOccupant(1025)).andReturn(anotherRoomOccupant);

        // stub default return value
        EasyMock.expect(mockDB.getRoomOccupant(EasyMock.anyInt())).andStubReturn("Empty room");
        
        EasyMock.replay(mockDB);
       
        Hotel target = new Hotel(10);
        target.Database = mockDB;

        String result;


        result = target.getRoomOccupant(25);
        Assert.assertEquals("Empty room", result);

        result = target.getRoomOccupant(1025);
        Assert.assertEquals(anotherRoomOccupant, result);

        result = target.getRoomOccupant(24);
        Assert.assertEquals(roomOccupant, result);

        result = target.getRoomOccupant(23);
        Assert.assertEquals("Empty room", result);

        //mocks.VerifyAll();
        EasyMock.verify(mockDB);
    }
	
	@Test
	public void TestGetMilesReturnsZero()
	{
		Assert.assertEquals(targetHotel.getMiles(), 0);
	}
	
	@Test
	public void TestAvailableRoomsReturnsCorrectValue()
	{
		IDatabase fakeDatabase = new FakeDatabase();
		ArrayList<String> roomList = new ArrayList<String>();
		roomList.add("204");
		roomList.add("560");
		fakeDatabase.Rooms = roomList;
		targetHotel.Database = fakeDatabase;
		assertEquals(targetHotel.AvailableRooms(), roomList.size());
	}
	
	public class FakeDatabase extends IDatabase{

		@Override
		public String getCarLocation(int carNumber) {
			return null;
		}

		@Override
		public String getRoomOccupant(int roomNumber) {
			return null;
		}
		
	}
//    @Test
//    public void TestThatHotelDoesGetRoomCountFromDatabase()
//    {
//    	IDatabase mockDB = EasyMock.createStrictMock(IDatabase.class);
//        List<String> Rooms = new LinkedList<String>();
//        for (int i = 0; i < 100; i++)
//        {
//            Rooms.add(i);
//        }
//
//        // FIXME: what is the EasyMock equivalent, if any?
//        //EasyMock.expect(mockDB.Rooms).PropertyBehavior();
//
//        EasyMock.replay(mockDB);
//
//        mockDB.Rooms = Rooms;
//
//
//        var target = new Hotel(10);
//        target.Database = mockDatabase;
//
//        int roomCount = target.AvailableRooms;
//        Assert.AreEqual(Rooms.Count, roomCount);
//
//        mocks.VerifyAll();
//    }
}
