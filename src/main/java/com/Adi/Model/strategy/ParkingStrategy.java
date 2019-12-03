package com.Adi.Model.strategy;

public interface ParkingStrategy {

	public void add(int i);
	public int getSlot();
	public void removeSlot(int availableSlot);
}
