package com.Adi.Model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Vehicle implements Externalizable {
	
	private String registrationNo = null;
	private String color = null;
	
	public Vehicle(String registrationNo, String color) {
		// TODO Auto-generated constructor stub
		this.registrationNo = registrationNo;
		this.color = color;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[registartion No :" +registrationNo+ ", color :" +color+"]";
	}

	public String getRegistrationNo() {
		return registrationNo;
	}
	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeObject(getRegistrationNo());
		out.writeObject(getColor());
		
	}

	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		setRegistrationNo((String) in.readObject());
		setColor((String) in.readObject());
		
	}

}
