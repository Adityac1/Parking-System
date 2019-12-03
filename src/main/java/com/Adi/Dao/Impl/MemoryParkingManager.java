package com.Adi.Dao.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Adi.Dao.ParakingLevelDataManager;
import com.Adi.Dao.ParkingDataManager;
import com.Adi.Model.Vehicle;
import com.Adi.Model.strategy.NearestFirstParkingStartegy;
import com.Adi.Model.strategy.ParkingStrategy;

public class MemoryParkingManager<T extends Vehicle> implements ParkingDataManager<T>{

	private Map<Integer, ParakingLevelDataManager<T>> levelParkingMap;
	
	
	@SuppressWarnings("rawtypes")
	private static MemoryParkingManager instance = null;
	
	private MemoryParkingManager(List<Integer> parkingLevels, List<Integer> capacityList,
			List<ParkingStrategy> parkingStrategy) {
		// TODO Auto-generated constructor stub
		if(levelParkingMap == null) {
			levelParkingMap = new HashMap<>();
			for(int i = 0; i<= parkingLevels.size();i++) {
				levelParkingMap.put(parkingLevels.get(i), (ParakingLevelDataManager<T>) MemoryParkingLevelManager.getInstance(parkingLevels.get(i), capacityList.get(i), new NearestFirstParkingStartegy()));
			}
		}
	}

	public static <T extends Vehicle> MemoryParkingManager<T> getInstance(List<Integer> parkingLevels, List<Integer> capacityList, List<ParkingStrategy> parkingStrategy) {
		if(instance == null) {
			synchronized (MemoryParkingManager.class) {
				if(instance == null) {
					instance = new MemoryParkingManager<T>(parkingLevels, capacityList, parkingStrategy);
				}
			}
		}
		
		return instance;
		
	}
	
	@Override
	public int parkCar(int level, T vehicle) {
		// TODO Auto-generated method stub

		return levelParkingMap.get(level).parkCar(vehicle);
	}

	@Override
	public boolean leaveCar(int level, int slotNumber) {
		// TODO Auto-generated method stub
		return levelParkingMap.get(level).leaveCar(slotNumber);
	}

	@Override
	public List<String> getStatus(int level) {
		// TODO Auto-generated method stub
		return levelParkingMap.get(level).getStatus();
	}

	@Override
	public List<String> getRegNumberForColor(int level, String color) {
		// TODO Auto-generated method stub
		return levelParkingMap.get(level).getRegistartionNoForColor(color);
	}

//	@Override
//	public List<String> getSlotNumbersFromColor(int level, String color) {
//		// TODO Auto-generated method stub
//		return levelParkingMap.get(level).getSlotsNumbersFromColor(color);
//	}

	@Override
	public int getSlotNoFromRegistrationNo(int level, String registrationNo) {
		// TODO Auto-generated method stub
		return levelParkingMap.get(level).getSlotNumberFromRegistrationNo(registrationNo);
	}

	@Override
	public int getAvailableslotsCount(int level) {
		// TODO Auto-generated method stub
		return levelParkingMap.get(level).getAvailableSlotsCount();
	}

	@Override
	public void doCleanUp() {
		// TODO Auto-generated method stub
		for(ParakingLevelDataManager<T> levelDataManager : levelParkingMap.values()) {
			levelDataManager.doCleanUp();
		}
		levelParkingMap = null;
		instance = null;
	}

	@Override
	public List<Integer> getSlotNumbersFromColor(int level, String color) {
		// TODO Auto-generated method stub
		return levelParkingMap.get(level).getSlotsNumbersFromColor(color);
	}

	
}
