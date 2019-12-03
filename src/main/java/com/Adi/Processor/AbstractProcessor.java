package com.Adi.Processor;

import com.Adi.Exception.ParkingException;
import com.Adi.Service.AbstractService;
import com.Adi.constants.CommandImputMap;

public interface AbstractProcessor {

	public void setService(AbstractService service);
	
	public void execute(String action) throws ParkingException;
	
	public default boolean 	validate(String inputstring) {
		boolean valid = true;
		try {
			String[] inputs = inputstring.split(" ");
			int params = CommandImputMap.getCommandsParameterMap().get(inputs[0]);
			switch (inputs.length) {
			case 1:
				if(params != 0)
					valid = false;
				break;
			case 2:
				if(params != 1)
					valid = false;
				break;
			case 3 :
				if(params != 2) 
					valid = false;
				break;

			default:
				valid = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			valid = false;
		}
		
		return valid;
		
	}
}
