package com.assignment.managment.contacts.exception;

import java.util.Date;
import java.util.List;

public class ExceptionResponse extends Exception {

	private Date timestamp;
	private List<String> messages;

	public ExceptionResponse(Date timestamp, List<String> messages) {
		super();
		this.timestamp = timestamp;
		this.messages = messages;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}
