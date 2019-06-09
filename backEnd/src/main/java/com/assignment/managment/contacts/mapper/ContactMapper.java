package com.assignment.managment.contacts.mapper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.assignment.managment.contacts.dto.ContactForm;
import com.assignment.managment.contacts.model.Contact;

public class ContactMapper {

	private final static Logger logger = LoggerFactory.getLogger(ContactMapper.class);

	public static Contact getDao(ContactForm contactDto) {
		logger.info("Start of getDao");
		logger.debug("contact dto {} ", contactDto);
		if (contactDto != null) {
			logger.info("End of getDao");
			return new Contact(contactDto.getFirstName(), contactDto.getLastName(), contactDto.getEmail(),
					contactDto.getPhone(), contactDto.getContactId());
		} else {
			logger.warn("null value will be returned");
			logger.info("End of getDao");
			return null;
		}

	}

	public static ContactForm getDto(Contact contact) {
		logger.info("Start of getDto");

		if (contact != null) {
			logger.info("End of getDto");
			return new ContactForm(contact.getFirstName(), contact.getLastName(), contact.getEmail(),
					contact.getPhone(), contact.getContactId());

		} else {
			logger.warn("null value will be returned");
			logger.info("End of getDto");
			return null;
		}
	}

	public static List<ContactForm> getDtoContactsList(List<Contact> contacts) {
		logger.info("Start of getDtoContactsList");

		if (contacts != null) {
			List<ContactForm> contactsDto = new ArrayList<ContactForm>();
			for (Contact contact : contacts) {
				contactsDto.add(getDto(contact));
			}
			logger.debug("contact dto {} ", contactsDto);
			logger.info("End of getDtoContactsList");
			return contactsDto;
		} else {
			logger.warn("null value will be returned");
			logger.info("End of getDtoContactsList");
			return null;
		}
	}

}
