package edu.nju.software.util;

public class GeneralJsonResult<T> extends JsonBase {
	private int resultCode;
	private String message;
	private T data;
	
	public GeneralJsonResult() {
		this.resultCode = ResultCode.NORMAL;
	}
	
	public GeneralJsonResult(int resultCode) {
		this.resultCode = resultCode;
	}
	
	public GeneralJsonResult(int resultCode, T data) {
		this.resultCode = resultCode;
		this.data = data;
	}
	
	public GeneralJsonResult(GeneralResult<T> result) {
		this.resultCode = result.getResultCode();
		this.data = result.getData();
		this.message = result.getMessage();
	}
	
	public void convertFromGeneralResult(GeneralResult<T> result) {
		this.resultCode = result.getResultCode();
		this.data = result.getData();
		this.message = result.getMessage();
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
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}
