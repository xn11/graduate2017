package edu.nju.software.util;

public class NoDataResult {
	private int resultCode;
	private String message;
	
	public NoDataResult() {
		this.resultCode = ResultCode.NORMAL;
	}
	
	public NoDataResult(int resultCode) {
		this.resultCode = resultCode;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
