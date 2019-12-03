package com.Adi.Dao;

import java.util.List;

import com.Adi.Model.Vehicle;

public interface ParakingLevelDataManager <T extends Vehicle>{

	public int parkCar(T vehicle);
	public boolean leaveCar(int slotNumber);
	public List<String> getStatus();
	public List<String> getRegistartionNoForColor(String color);
	public List<Integer> getSlotsNumbersFromColor(String color);
	public int getSlotNumberFromRegistrationNo(String registrationNo);
	public int getAvailableSlotsCount();
	public void doCleanUp();
}
