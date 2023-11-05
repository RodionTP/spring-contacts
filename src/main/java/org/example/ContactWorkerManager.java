package org.example;

import org.example.model.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactWorkerManager {
    private final ContactWorker contactWorker;

    public ContactWorkerManager(ContactWorker contactWorker) {
        this.contactWorker = contactWorker;
    }

    void printContacts() {
        contactWorker.printContacts();
    }

    boolean addContact(Contact newContact) {
        return contactWorker.addContact(newContact);
    }

    boolean deleteContactByEmail(String email) {
        return contactWorker.deleteContactByEmail(email);
    }

    void saveContactsToFile() {
        contactWorker.saveContactsToFile();
    }
}
