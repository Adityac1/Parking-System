package com.Adi.Exception;

public class ParkingException  extends Exception{

	private static final long serialVersionUId = -3552275262672621625L;
	private String errorCode = null;
	private Object[] errorParameters = null;
	
	public ParkingException(String message, Throwable throwable) {
		// TODO Auto-generated constructor stub
		super(message, throwable);
	}
	public ParkingException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}
	public ParkingException(Throwable throwable) {
		// TODO Auto-generated constructor stub
		super(throwable);
	}
	public ParkingException(String errorCode, String message, Object[] errorParameters) {
		// TODO Auto-generated constructor stub
		super(message);
		this.setErrorCode(errorCode);
		this.setErrorParameters(errorParameters);
	}
	public ParkingException(String errorCode, String message, Throwable throwable) {
		// TODO Auto-generated constructor stub
		super(message,throwable);
		this.setErrorCode(errorCode);
	}
	public ParkingException(String errorCode, String message, Object[] errorParameters, Throwable throwable) {
		// TODO Auto-generated constructor stub
		super(message, throwable);
		this.setErrorCode(errorCode);
		this.setErrorParameters(errorParameters);
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public Object[] getErrorParameters() {
		return errorParameters;
	}
	public void setErrorParameters(Object[] errorParameters) {
		this.errorParameters = errorParameters;
	}

}
