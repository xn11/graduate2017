package edu.nju.software.util;

public class GeneralResult<T> {
	private int resultCode;
	private String message;
	private T data;
	
	public GeneralResult() {
		this.resultCode = ResultCode.NORMAL;
	}
	
	public GeneralResult(int resultCode) {
		this.resultCode = resultCode;
	}
	
	public GeneralResult(int resultCode, T data) {
		this.resultCode = resultCode;
		this.data = data;
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
