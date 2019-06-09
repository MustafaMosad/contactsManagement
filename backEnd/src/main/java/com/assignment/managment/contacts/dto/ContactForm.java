package com.assignment.managment.contacts.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class ContactForm {

	@NotEmpty(message = "first name can not be empty")
	@Pattern(regexp = "(?=.*[a-zA-Z ]).{1,20}", message = " first name should contains only alphabets , spaces and shouldn't exceed 20 characters")
	private String firstName;
	@NotEmpty(message = "last name can not be empty")
	@Pattern(regexp = "(?=.*[a-zA-Z ]).{1,20}", message = " last name should contains only alphabets , spaces and shouldn't exceed 20 characters")
	private String lastName;
	@NotEmpty(message = "email can not be empty")
	@Email(message = " Please provide valid Email")
	private String email;
	@NotEmpty(message = "phone can not be empty")
	@Pattern(regexp = "[0-9]{1,29}", message = "phone can be only numbers and at most 30 number")
	private String phone;
	@NotEmpty(message = "user email can not be empty")
	private String userEmail;

	private String contactId;

	public ContactForm() {
		super();
	}

	public ContactForm(String firstName, String lastName, String email, String phone, String contactId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.contactId = contactId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	@Override
	public String toString() {
		return "ContactForm [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phone="
				+ phone + "]";
	}

}
