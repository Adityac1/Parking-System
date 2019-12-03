package com.Adi.Service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.Adi.Dao.ParkingDataManager;
import com.Adi.Dao.Impl.MemoryParkingManager;
import com.Adi.Exception.ErrorCode;
import com.Adi.Exception.ParkingException;
import com.Adi.Model.Vehicle;
import com.Adi.Model.strategy.NearestFirstParkingStartegy;
import com.Adi.Model.strategy.ParkingStrategy;
import com.Adi.Service.ParkingService;
import com.Adi.constants.Constants;

public class ParkingServiceImpl implements ParkingService {

	private ParkingDataManager<Vehicle> dataManager = null;
	
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	
	@Override
	public void createParkingLot(int level, int capacity) throws ParkingException {
		// TODO Auto-generated method stub
		if(dataManager != null) 
			throw new ParkingException(ErrorCode.parking_Already_Exist.getMessage());
			
		List<Integer> parkingLevels = new ArrayList<Integer>();
		List<Integer> capacityList = new ArrayList<Integer>();
		List<ParkingStrategy> parkingStrategy = new ArrayList<ParkingStrategy>();
		parkingLevels.add(level);
		capacityList.add(capacity);
		parkingStrategy.add(new NearestFirstParkingStartegy());
		this.dataManager = MemoryParkingManager.getInstance(parkingLevels, capacityList, parkingStrategy);
		System.out.println("created parking Lot with " +capacity+ "slots");
		
	}

	@Override
	public Optional<Integer> park(int level, Vehicle vehicle) throws ParkingException {
		// TODO Auto-generated method stub
		Optional<Integer> value = Optional.empty();
		lock.writeLock().lock();
		validateparkingLot();
		try {
			value = Optional.of(dataManager.parkCar(level, vehicle));
			if(value.get() == Constants.Not_Available)
				System.out.println("Sorry parking is full");
			else if (value.get() == Constants.Vehicle_Already_Exist) 
				System.out.println("sorry vehicle is already parked");
			else 
				System.out.println("Allocated Slot Number :"+value.get());
		} catch (Exception e) {
			// TODO: handle exception
			throw new ParkingException(ErrorCode.Processing_Error.getMessage(), e);
		}
		finally {
			lock.writeLock().unlock();
		}
		
		return value;
	}

	private void validateparkingLot() throws ParkingException {
		// TODO Auto-generated method stub
		if(dataManager == null) 
			throw new ParkingException(ErrorCode.parking_Not_Exist_Error.getMessage());
		
	}

	@Override
	public void unpark(int level, int slotNo) throws ParkingException {
		// TODO Auto-generated method stub
		lock.writeLock().lock();
		validateparkingLot();
		try {
			if(dataManager.leaveCar(level, slotNo))
				System.out.println("slot Number :" +slotNo + "is free" );
			else 
				System.out.println("slot Number is Empty Already");
		} catch (Exception e) {
			// TODO: handle exception
			throw new ParkingException(ErrorCode.Invalid_Value.getMessage().replace("{variables}", "slotNo"), e);
		}
		finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public void getStatus(int level) throws ParkingException {
		// TODO Auto-generated method stub
		lock.readLock().lock();
		validateparkingLot();
		try {
			System.out.println("slotno. \n Registartion No. \n Color");
			List<String> statusList = dataManager.getStatus(level);
			if(statusList.size() == 0)
				System.out.println("Sorry parking lot is empty");
			else {
				for(String statusSting : statusList) {
					System.out.println(statusSting);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new ParkingException(ErrorCode.Processing_Error.getMessage(), e);
		}
		finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public Optional<Integer> getAvailableSlotsCount(int level) throws ParkingException {
		// TODO Auto-generated method stub
		lock.readLock().lock();
		Optional<Integer> value = Optional.empty();
		validateparkingLot();
		try {
			value = Optional.of(dataManager.getAvailableslotsCount(level));
		} catch (Exception e) {
			// TODO: handle exception
			throw new ParkingException(ErrorCode.Processing_Error.getMessage(), e);
		}
		finally {
			lock.readLock().unlock();
		}
		return value;
	}

	@Override
	public void getRegistrationNoForColor(int level, String color) throws ParkingException {
		// TODO Auto-generated method stub
		lock.readLock().lock();
		validateparkingLot();
		try {
			List<String> registrationsList = dataManager.getRegNumberForColor(level, color);
			if(registrationsList.size() == 0)
				System.out.println("Not found");
			else 
				System.out.println(String.join(",", registrationsList));
		} catch (Exception e) {
			// TODO: handle exception
			throw new ParkingException(ErrorCode.Processing_Error.getMessage(), e);
		}
		finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public void getSlotNosFromColor(int level, String color) throws ParkingException {
		// TODO Auto-generated method stub
		lock.readLock().lock();
		validateparkingLot();
		try {
			List<Integer> slotList = dataManager.getSlotNumbersFromColor(level, color);
			if(slotList.size() == 0)
				System.out.println("not Found");
			StringJoiner joiner = new StringJoiner(",");
			for(Integer slot : slotList) {
				joiner.add(slot + "");
			}
			System.out.println(joiner.toString());
		} catch (Exception e) {
			// TODO: handle exception
			throw new ParkingException(ErrorCode.Processing_Error.getMessage(), e);
		}
		finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public int getSlotNoFromRegNo(int level, String registrationNo) throws ParkingException {
		// TODO Auto-generated method stub
		int value = -1;
		lock.readLock().lock();
		validateparkingLot();
		try {
			value = dataManager.getSlotNoFromRegistrationNo(level, registrationNo);
			System.out.println(value != -1 ? value : "Not found");
		} catch (Exception e) {
			// TODO: handle exception
			throw new ParkingException(ErrorCode.Processing_Error.getMessage(), e);
		}
		finally {
			lock.readLock().unlock();
		}
		return value;
		
	}

	@Override
	public void docleanup() {
		// TODO Auto-generated method stub
		if(dataManager!= null)
			dataManager.doCleanUp();
	} 

	
}
