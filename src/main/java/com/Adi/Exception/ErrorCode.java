package com.Adi.Exception;

public enum ErrorCode {
	
	parking_Already_Exist("Sorry parking Already created"), parking_Not_Exist_Error("Sorry, car parking doesnt exist"), 
	Invalid_Value("{variable} value is incorrect"), Invalid_File("invalid_file"), Processing_Error("processing Error"), Invalid_Request("Invalid_Request");
	
	private String message = "";
	private ErrorCode(String message) {
		// TODO Auto-generated constructor stub
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
