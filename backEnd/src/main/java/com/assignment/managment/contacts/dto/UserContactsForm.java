package com.assignment.managment.contacts.dto;

public class UserContactsForm {

	private String email;
	private int pageNumber = 0; // default value
	private int pageSize = 10; // default value
	private String sortBy = "id"; // default value
	private String sortDirection = "asc"; // default value

	public UserContactsForm() {
		super();
	}

	public UserContactsForm(String email, int pageNumber, int pageSize, String sortBy, String sortDirection) {
		super();
		this.email = email;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.sortBy = sortBy;
		this.sortDirection = sortDirection;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	@Override
	public String toString() {
		return "UserContactsForm [email=" + email + ", pageNumber=" + pageNumber + ", pageSize=" + pageSize
				+ ", sortBy=" + sortBy + ", sortDirection=" + sortDirection + "]";
	}

}
