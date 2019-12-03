package com.Adi.Dao.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.Adi.Dao.ParakingLevelDataManager;
import com.Adi.Model.Vehicle;
import com.Adi.Model.strategy.NearestFirstParkingStartegy;
import com.Adi.Model.strategy.ParkingStrategy;
import com.Adi.constants.Constants;

public class MemoryParkingLevelManager<T extends Vehicle> implements ParakingLevelDataManager<T> {

	private AtomicInteger level = new AtomicInteger(0);
	private AtomicInteger capacity = new AtomicInteger();
	private AtomicInteger availability = new AtomicInteger();
	private ParkingStrategy parkingStrategy;
	private Map<Integer, Optional<T>> slotVehicleMap;
	
	@SuppressWarnings("rawtypes")
	private static MemoryParkingLevelManager instance = null;
	
//	public MemoryParkingLevelManager(int level2, int capacity2, ParkingStrategy parkingStrategy2) {
//		// TODO Auto-generated constructor stub
//	}

	@SuppressWarnings("unchecked")
	public static <T extends Vehicle> MemoryParkingLevelManager<T> getInstance(int level, int capacity, ParkingStrategy parkingStrategy) {
		if(instance == null) {
			synchronized (MemoryParkingLevelManager.class) {
				if (instance == null) {
					instance = new MemoryParkingLevelManager<T>(level, capacity,parkingStrategy );
				}
			}
		}
		
		return instance;
		
	}
//	@Override
	private MemoryParkingLevelManager(int level, int capacity, ParkingStrategy parkingStrategy) {
		// TODO Auto-generated constructor stub
		this.level.set(level);
		this.capacity.set(capacity);
		this.availability.set(capacity);
		if(parkingStrategy == null) 
			parkingStrategy = new NearestFirstParkingStartegy();
			slotVehicleMap = new ConcurrentHashMap<>();
			for(int i = 1; i<= capacity; i++) {
				slotVehicleMap.put(i,Optional.empty());
				parkingStrategy.add(i);
			}
	}
	
	@Override
	public int parkCar(T vehicle) {
		// TODO Auto-generated method stub
		int availableSlot;
		if(availability.get() == 0) {
			return Constants.Not_Available;
		} else {
			availableSlot = parkingStrategy.getSlot();
			if(slotVehicleMap.containsValue(Optional.of(vehicle))) {
				return Constants.Vehicle_Already_Exist;
				
//				slotVehicleMap.put(availableSlot,Optional.of(vehicle));
//				availability.decrementAndGet();
//				parkingStrategy.removeSlot(availableSlot);
				
			}
		}
		
		return availableSlot;
	}

	@Override
	public boolean leaveCar(int slotNumber) {
		// TODO Auto-generated method stub
		if(!slotVehicleMap.get(slotNumber).isPresent())
		return false;
		availability.incrementAndGet();
		parkingStrategy.add(slotNumber);
		slotVehicleMap.put(slotNumber, Optional.empty());
		return true;
	}

	public List<String> getStatus() {
		// TODO Auto-generated method stub
		List<String> statusList = new ArrayList<String>();
		for(int i = 0; i <capacity.get(); i++) 
		{
			Optional<T> vehicle = slotVehicleMap.get(i);
			if(vehicle.isPresent()) {
				statusList.add(i+"\t\t" +vehicle.get().getRegistrationNo()+"\t\t" +vehicle.get().getColor());
			}
		}
		
		return statusList;
	}

	public List<String> getRegistartionNoForColor(String color) {
		// TODO Auto-generated method stub
		List<String> statusList = new ArrayList<String>();
		for(int i = 0; i<capacity.get();i++) {
			Optional<T> vehicle = slotVehicleMap.get(i);
			if(vehicle.isPresent() && color.equalsIgnoreCase(vehicle.get().getColor())) {
				statusList.add(vehicle.get().getRegistrationNo());
			}
		}
		return statusList;
	}

	@Override
	public List<Integer> getSlotsNumbersFromColor(String color) {
		// TODO Auto-generated method stub
		List<Integer> statusList = new ArrayList<>();
		for(int i = 0; i <= capacity.get();i++) {
			Optional<T> vehicle = slotVehicleMap.get(i);
			if(vehicle.isPresent() && color.equalsIgnoreCase(vehicle.get().getColor())) {
				statusList.add(i);
			}
		}
		return statusList;
	}

	public int getSlotNumberFromRegistrationNo(String registrationNo) {
		// TODO Auto-generated method stub
		int result = Constants.Not_Found;
		for(int i = 0; i<= capacity.get(); i++) {
			Optional<T> vehicle = slotVehicleMap.get(i);
			if(vehicle.isPresent() && registrationNo.equalsIgnoreCase(vehicle.get().getRegistrationNo()) ) {
				result = i;
			}
		}
		
		return result;
	}

	public int getAvailableSlotsCount() {
		// TODO Auto-generated method stub
		return availability.get();
	}

	public void doCleanUp() {
		// TODO Auto-generated method stub
		this.level = new AtomicInteger();
		this.capacity = new AtomicInteger();
		this.availability = new AtomicInteger();
		this.parkingStrategy = null;
		slotVehicleMap = null;
		instance = null;
	}
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	

}
