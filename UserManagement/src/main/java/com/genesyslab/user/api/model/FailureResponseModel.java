package com.genesyslab.user.api.model;

public class FailureResponseModel {
	
	private String reason;

	public FailureResponseModel(String reason) {
		super();
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
