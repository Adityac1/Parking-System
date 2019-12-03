package com.Adi.Model;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Car  extends Vehicle{

	public Car(String registrationNo, String color) {
		super(registrationNo, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub
		super.writeExternal(out);
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		super.readExternal(in);
	}
}
