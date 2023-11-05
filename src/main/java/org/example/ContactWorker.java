package org.example;

import org.example.model.Contact;

public interface ContactWorker {

    void printContacts();

    boolean addContact(Contact newContact);

    boolean deleteContactByEmail(String email);

    void saveContactsToFile();
}
