package com.assignment.managment.contacts.dto;

import java.util.List;

public class RetrieveContactsResponse {

	private List<ContactForm> contacts;
	private Long totalContacts;

	public RetrieveContactsResponse() {
		super();
	}

	public RetrieveContactsResponse(List<ContactForm> contacts, Long totalContacts) {
		super();
		this.contacts = contacts;
		this.totalContacts = totalContacts;
	}

	public List<ContactForm> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactForm> contacts) {
		this.contacts = contacts;
	}

	public Long getTotalContacts() {
		return totalContacts;
	}

	public void setTotalContacts(Long totalContacts) {
		this.totalContacts = totalContacts;
	}

}
