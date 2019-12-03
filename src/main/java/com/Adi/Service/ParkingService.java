package com.Adi.Service;

import java.util.Optional;

import com.Adi.Exception.ParkingException;
import com.Adi.Model.Vehicle;

public interface ParkingService extends AbstractService  {

	public void createParkingLot(int level, int capacity) throws ParkingException;
	public Optional<Integer> park(int level, Vehicle vehicle) throws ParkingException;
	public void unpark(int level, int slotNo) throws ParkingException;
	public void getStatus(int level) throws ParkingException;
	public Optional<Integer> getAvailableSlotsCount(int level) throws ParkingException;
	public void getRegistrationNoForColor(int level, String color) throws ParkingException;
	public void getSlotNosFromColor(int level, String color) throws ParkingException;
	public int getSlotNoFromRegNo(int level, String registrationNo) throws ParkingException;
	public void docleanup();
 }
