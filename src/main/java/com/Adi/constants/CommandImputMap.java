package com.Adi.constants;

import java.util.HashMap;
import java.util.Map;

public class CommandImputMap {

	private static volatile Map<String, Integer> commandsParameterMap = new HashMap<String, Integer>();
	static {
		commandsParameterMap.put(Constants.create_parking_Lot, 1);
		commandsParameterMap.put(Constants.Park, 2);
		commandsParameterMap.put(Constants.Leave, 1);
		commandsParameterMap.put(Constants.status, 0);
		//commandsParameterMap.put(Constants.REG_NUMBER_FOR_CARS_WITH_COLOR, 1);
		commandsParameterMap.put(Constants.REG_NUMBER_FOR_CARS_WITH_COLOR, 1);
		commandsParameterMap.put(Constants.SLOTS_NUMBER_FOR_CARS_WITH_COLOR, 1);
		commandsParameterMap.put(Constants.SLOTS_NUMBER_FOR_REG_NUMBER, 1);
	}
	public static Map<String, Integer> getCommandsParameterMap() {
		return commandsParameterMap;
	}
	public static void addCommand(String command, int parameterCount) {
		commandsParameterMap.put(command, parameterCount);
	}
}
