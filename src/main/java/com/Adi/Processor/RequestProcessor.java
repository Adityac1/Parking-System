package com.Adi.Processor;

import com.Adi.Exception.ErrorCode;
import com.Adi.Exception.ParkingException;
import com.Adi.Model.Car;
import com.Adi.Service.AbstractService;
import com.Adi.Service.ParkingService;
import com.Adi.constants.Constants;

public class RequestProcessor implements AbstractProcessor {

	private ParkingService parkingService;
	
	public void setParkingService(ParkingService parkingService ) throws ParkingException {
		this.parkingService = parkingService;
	}
	
	@Override
	public void setService(AbstractService service) {
		// TODO Auto-generated method stub
		this.parkingService = (ParkingService) service;
	}

	@Override
	public void execute(String input) throws ParkingException {
		// TODO Auto-generated method stub
	
		int level = 1;
		String[] inputs = input.split(" ");
		String key = inputs[0];
		switch (key) {
		case Constants.create_parking_Lot:
			try {
				int capacity = Integer.parseInt(inputs[1]);
				parkingService.createParkingLot(level, capacity);
			} catch (Exception e) {
				// TODO: handle exception
				throw new ParkingException(ErrorCode.Invalid_Value.getMessage().replace("{variable}", "capacity"));
			}	
			break;
			case Constants.Park:
				parkingService.park(level, new Car(inputs[1], inputs[2]));
				break;
				
			case Constants.Leave:
				try {
					int slotNo = Integer.parseInt(inputs[1]);
					parkingService.unpark(level, slotNo);
				}catch (Exception e) {
					// TODO: handle exception
					throw new ParkingException(ErrorCode.Invalid_Value.getMessage().replace("{variable}", "slot_No"));
				}
				break;
				
			case Constants.status:
				parkingService.getStatus(level);
				break;
			case Constants.REG_NUMBER_FOR_CARS_WITH_COLOR:
				parkingService.getRegistrationNoForColor(level, inputs[1]);
				break;
			case Constants.SLOTS_NUMBER_FOR_CARS_WITH_COLOR:
				parkingService.getSlotNosFromColor(level, inputs[1]);
				break;
			case Constants.SLOTS_NUMBER_FOR_REG_NUMBER:
				parkingService.getSlotNoFromRegNo(level, inputs[1]);
				break;

		default:
			break;
		}
	}

}
