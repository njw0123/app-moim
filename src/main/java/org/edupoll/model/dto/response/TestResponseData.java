package org.edupoll.model.dto.response;

public class TestResponseData {
	int code;
	String message;
	String[] results;
	
	public TestResponseData(int code, String message, String[] results) {
		super();
		this.code = code;
		this.message = message;
		this.results = results;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String[] getResults() {
		return results;
	}

	public void setResults(String[] results) {
		this.results = results;
	}
	
}
