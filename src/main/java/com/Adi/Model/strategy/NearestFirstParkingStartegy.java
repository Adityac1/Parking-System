package com.Adi.Model.strategy;

import java.util.TreeSet;

public class NearestFirstParkingStartegy implements ParkingStrategy {

	private TreeSet<Integer> freeSlots;
	public NearestFirstParkingStartegy() {
		// TODO Auto-generated constructor stub
		freeSlots = new TreeSet<Integer>();
	}
	public void add(int i) {
		// TODO Auto-generated method stub
		freeSlots.add(i);
		
	}

	public int getSlot() {
		// TODO Auto-generated method stub
		return freeSlots.first();
	}

	public void removeSlot(int availableSlot) {
		// TODO Auto-generated method stub
		freeSlots.remove(availableSlot);
	}

}
