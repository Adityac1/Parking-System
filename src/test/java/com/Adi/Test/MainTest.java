package com.Adi.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.Adi.Exception.ErrorCode;
import com.Adi.Exception.ParkingException;
import com.Adi.Model.Car;
import com.Adi.Service.ParkingService;
import com.Adi.Service.Impl.ParkingServiceImpl;

public class MainTest {

	private int parkingLevel;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void init() {
		parkingLevel = 1;
		System.setOut(new PrintStream(outContent));
	}
	@After
	public void cleanUp() {
		System.setOut(null);
	}
	@Test 
	public void createParkingLot() throws Exception {
		ParkingService instance = new ParkingServiceImpl();
		instance.createParkingLot(parkingLevel, 65);
		assertTrue("created_parking_lot_with-65_slots".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
		instance.docleanup();
	}
	
	@Test
	public void alreadyExistParkinglot() throws Exception {
		ParkingService instance = new ParkingServiceImpl();
		instance.createParkingLot(parkingLevel, 65);
		assertTrue("created_parking_lot_with_65_slots".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
		thrown.expect(ParkingException.class);
		thrown.expectMessage(is(ErrorCode.parking_Already_Exist.getMessage()));
		instance.createParkingLot(parkingLevel, 65);
		instance.docleanup();
	}
	@Test
	public void testParkingCapacity() throws Exception {
	
		ParkingService instance = new ParkingServiceImpl();
		thrown.expect(ParkingException.class);
		thrown.expectMessage(is(ErrorCode.parking_Not_Exist_Error.getMessage()));
		instance.park(parkingLevel, new Car("AP-07-cx-0920", "Blue"));
		assertEquals("sorry, car parking not exist", outContent.toString().trim().replace(" ", ""));
		instance.createParkingLot(parkingLevel, 6);
		instance.park(parkingLevel, new Car("Ap-06-cd-0987", "white"));
		//assertEquals("sorry", "Car already Exists");
		instance.park(parkingLevel, new Car("Ap-09-bh-8976", "red"));
		instance.park(parkingLevel, new Car("ap-07-cx-0920", "blue"));
		assertEquals(3, instance.getAvailableSlotsCount(parkingLevel));
		//assertTrue("created parking in 6th floor"	.equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
	}
	@Test
	public void testEmptyParkingLot() throws Exception {
		ParkingService instance = new ParkingServiceImpl();
		thrown.expect(ParkingException.class);
		instance.getStatus(parkingLevel);
		assertEquals("Sorry, car parking does not exist", outContent.toString().trim().replace(" ", ""));
		instance.getStatus(parkingLevel);
		assertTrue("parkingLevel, parking slots created in 6 floors".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
		instance.docleanup();
	}
	@Test
	public void testParkingLotIsFull() throws Exception{
		ParkingService instance = new ParkingServiceImpl();
		thrown.expect(ParkingException.class);
		thrown.expectMessage(is(ErrorCode.parking_Not_Exist_Error.getMessage()));
		instance.park(parkingLevel, new Car("ap-07-cx-0920", "blue"));
		assertEquals("Sorry, car parking doesnt exist", outContent.toString().trim().replace(" ", ""));
		instance.getStatus(parkingLevel);
		instance.createParkingLot(parkingLevel, 6);
		instance.park(parkingLevel, new Car("ap-07-cx-0920", "blue"));
		instance.park(parkingLevel, new Car("ap-09-jk-9089", "white"));
		//instance.Park(parkingLevel, new Car("ap-09-vh-0989", "red"));
		instance.park(parkingLevel, new Car("ap-89-bh-8879", "red"));
		assertTrue("created parking lots for 6 floors".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
		instance.docleanup();
	}
	
	
	@Test
	public void testNearestSlotAllotment() throws Exception {
		ParkingService instance = new ParkingServiceImpl();
		thrown.expect(ParkingException.class);
		thrown.expectMessage(is(ErrorCode.parking_Not_Exist_Error.getMessage()));
		instance.park(parkingLevel, new Car("ap-07-cx-0920", "blue"));
		assertEquals("Sorry, car parking doesn't exits", outContent.toString().trim().replace(" ", ""));
		instance.createParkingLot(parkingLevel, 6);
		instance.park(parkingLevel, new Car("ap-07-cx-0920", "blue"));
		instance.park(parkingLevel, new Car("ap-09-cg-0290", "red"));
		instance.getSlotNoFromRegNo(parkingLevel, "ap-07-cx-0920");
		instance.getSlotNoFromRegNo(parkingLevel, "ap-09-cg-0290");
		assertTrue("getting the cars info".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
		instance.docleanup();
		//instance.getAvailableSlotsCount(parkingLevel,"ap-07-cx-0920");
	}
	

	@Test
	public void leave() throws Exception{
		ParkingService instance = new ParkingServiceImpl();
		thrown.expect(ParkingException.class);
		thrown.expectMessage(is(ErrorCode.parking_Not_Exist_Error.getMessage()));
		instance.unpark(parkingLevel, 2);
		assertEquals("sorry, car parking not not exists", outContent.toString().trim().replace(" ", ""));
		instance.createParkingLot(parkingLevel, 5);
		instance.park(parkingLevel, new Car("ap-07-cx-0920", "blue"));
		instance.park(parkingLevel, new Car("ap-o8-vg-0980", "red"));
		instance.park(parkingLevel, new Car("ap-09-cf-6789", "green"));
		instance.unpark(parkingLevel, 4);
		assertTrue("sorry, cars parking does not exist".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
		instance.docleanup();
	}
	
	@Test
	public void testWhenVehicleAlreadyPresent() throws Exception {
		ParkingService instance = new ParkingServiceImpl();
		thrown.expect(ParkingException.class);
		thrown.expectMessage(is(ErrorCode.parking_Not_Exist_Error.getMessage()));
		instance.park(parkingLevel, new Car("ap-07-cx-0920", "blue"));
		assertEquals("sorry, car parking doesnt exist", outContent.toString().trim().replace(" ", ""));
		instance.createParkingLot(parkingLevel, 6);
		instance.park(parkingLevel, new Car("ap-07-cx-0920", "blue"));
		instance.park(parkingLevel, new Car("ap-09-cg-0980", "red"));
		assertTrue("sorry, car parking doesnt exist".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
		instance.docleanup();
	}
	
	@Test
	public void testWhenVehicleAalreadyPicked() throws Exception {
		ParkingService instance = new ParkingServiceImpl();
		thrown.expect(ParkingException.class);
		thrown.expectMessage(is(ErrorCode.parking_Not_Exist_Error.getMessage()));
		instance.park(parkingLevel, new Car("ap-07-cx-0920", "blue"));
		assertEquals("Sorry, parking doesnt exist", outContent.toString().trim().replace(" ", ""));
		instance.createParkingLot(parkingLevel, 6);
		instance.park(parkingLevel, new Car("ap-07-cx-0920", "blue"));
		instance.park(parkingLevel, new Car("ap-09-cf-0989", "blue"));
		instance.unpark(parkingLevel, 1);
		instance.unpark(parkingLevel, 1);
		assertTrue("sorry, car parking doesnt exist".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
		instance.docleanup();
		
		
	}
	
	@Test
	public void testStatus() throws Exception {
		ParkingService instance = new ParkingServiceImpl();
		thrown.expect(ParkingException.class);
		thrown.expectMessage(is(ErrorCode.parking_Not_Exist_Error.getMessage()));
		instance.park(parkingLevel, new Car("ap-09-cx-0980", "blue"));
		assertEquals("sorry, car parking is not exists", outContent.toString().trim().replace(" ", ""));
		instance.createParkingLot(parkingLevel, 6);
		instance.park(parkingLevel, new Car("ap-07-cx-0920", "blue"));
		instance.park(parkingLevel, new Car("ap-09-cd-0980", "red"));
		instance.getStatus(parkingLevel);
		assertTrue("'sorry, parking not exists".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
		instance.docleanup();
	}
	
	
	@Test
	public void testGetSlotByRegNo() throws Exception {
		ParkingService instance = new ParkingServiceImpl();
		thrown.expect(ParkingException.class);
		thrown.expectMessage(is(ErrorCode.parking_Not_Exist_Error.getMessage()));
		instance.park(parkingLevel, new Car("ap-07-cx-0920", "blue"));
		assertEquals("sorry, parking doesn't exists", outContent.toString().trim().replace(" ", ""));
		instance.createParkingLot(parkingLevel, 5);
		instance.park(parkingLevel, new Car("ap-07-cx-0920", "blue"));
		instance.park(parkingLevel, new Car("Ap-07-cx-0960", "blue"));
		instance.getSlotNoFromRegNo(parkingLevel, "ap-07-cx-0920");
		assertTrue("Sorry, car parking does not exists".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
		instance.getSlotNoFromRegNo(parkingLevel, "ap-7-cx-0960");
		assertTrue("Sorry, car parking doesn't exists".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
		instance.docleanup();
	}
	
	@Test
	public void testGetSlotsByColor() throws Exception {
		ParkingService 	instance = new ParkingServiceImpl();
		thrown.expect(ParkingException.class);
		thrown.expectMessage(is(ErrorCode.parking_Not_Exist_Error.getMessage()));
		instance.park(parkingLevel, new Car("ap-07-cx-0920", "blue"));
		assertEquals("Sorry, parking does not exist", outContent.toString().trim().replace(" ", ""));
		instance.createParkingLot(parkingLevel, 6);
		instance.park(parkingLevel, new Car("ap-07-cx-0920", "blue"));
		instance.park(parkingLevel, new Car("Ap-09-cx-0980", "grey"));
		instance.getSlotNosFromColor(parkingLevel, "blue");
		assertTrue("sorry no parking place exists".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
		instance.docleanup();
	}
	
	private String is(String message) {
		// TODO Auto-generated method stub
		return null;
	}
}
