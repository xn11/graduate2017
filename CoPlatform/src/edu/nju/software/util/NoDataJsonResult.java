package edu.nju.software.util;

public class NoDataJsonResult extends JsonBase {
	private int resultCode;
	private String message;
	
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
	
	public NoDataJsonResult() {
		this.resultCode = ResultCode.NORMAL;
	}
	
	public NoDataJsonResult(int resultCode) {
		this.resultCode = resultCode;
	}
	
	public NoDataJsonResult(int resultCode, String message) {
		this.resultCode = resultCode;
		this.message = message;
	}
	
	public NoDataJsonResult(NoDataResult result) {
		this.resultCode = result.getResultCode();
		this.message = result.getMessage();
	}
	
	
	public <T> NoDataJsonResult(GeneralResult<T> result) {
		this.resultCode = result.getResultCode();
		this.message = result.getMessage();
	}

}
