package org.example;

import org.example.model.Contact;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class CommonContactWorker implements ContactWorker {
    Set<Contact> contacts = new HashSet<>();

    @Value("${app.path}")
    protected String filePath;

    @PostConstruct
    public void afterInit() {
        Path path = Paths.get(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                String fullName = data[0];
                String phoneNumber = data[1];
                String email = data[2];
                addContact(new Contact(fullName, phoneNumber, email));
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    @Override
    public void printContacts() {
        contacts.forEach(System.out::println);
    }

    @Override
    public boolean addContact(Contact newContact) {
        return contacts.add(newContact);
    }

    @Override
    public boolean deleteContactByEmail(String email) {
        return contacts.removeIf(contact -> contact.getEmail().equalsIgnoreCase(email));
    }

    @Override
    public void saveContactsToFile() {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (Contact contact : contacts) {
                writer.println(contact.getFullName() + ";" + contact.getPhoneNumber() + ";" + contact.getEmail());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Ошибка записи файла: " + e.getMessage());
        }
    }
}
