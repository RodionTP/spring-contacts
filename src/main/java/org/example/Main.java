package org.example;

import org.example.config.AppConfig;
import org.example.model.Contact;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static ContactWorkerManager contactWorkerManager;

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        contactWorkerManager = context.getBean(ContactWorkerManager.class);

        while (true) {
            int command = getCommand();
            switch (command) {
                case 1:
                    printContacts();
                    break;
                case 2:
                    addContact();
                    break;
                case 3:
                    deleteContact();
                    break;
                case 4:
                    saveContacts();
                    break;
                case 5:
                    return;
                default:
                    System.err.println(System.lineSeparator() + "Неверная команда. Попробуйте снова." + System.lineSeparator());
                    break;
            }
        }
    }

    private static int getCommand() {
        while (true) {
            System.out.println("1 - Вывести все контакты");
            System.out.println("2 - Добавить новый контакт");
            System.out.println("3 - Удалить контакт по email");
            System.out.println("4 - Сохранить контакты в файл");
            System.out.println("5 - Выход");
            System.out.print("Введите команду: ");
            String command = scanner.nextLine();
            try {
                return Integer.parseInt(command);
            } catch (NumberFormatException e) {
                System.err.println("Неверный формат числа");
            }
        }
    }

    private static void printContacts() {
        contactWorkerManager.printContacts();
    }

    private static void addContact() {
        while (true) {
            System.out.println("Введите данные контакта в формате \"Ф. И. О.; номер телефона (+7xxxxxxxxxx); адрес электронной почты\": ");
            System.out.println("Для отмены введите: \"отмена\"");
            String command = scanner.nextLine();
            String[] data = command.split(";");
            data = Arrays.stream(data).map(String::trim).toArray(String[]::new);
            if (data.length == 1 && data[0].equalsIgnoreCase("отмена")) {
                break;
            } else if (data.length != 3) {
                System.err.println("Некорректный формат данных");
            } else {
                String fullName = data[0];
                String phoneNumber = data[1];
                String email = data[2];
                String regexPhoneNumber = "\\+7\\d{10}";
                String regexEmail = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}";
                if (!phoneNumber.matches(regexPhoneNumber)) {
                    System.err.println("Номер телефона не соответствует формату \"+7XXXXXXXXXX\"");
                    break;
                }
                if (!email.matches(regexEmail)) {
                    System.err.println("Адрес электронной почты введен не верно");
                    break;
                }
                if (contactWorkerManager.addContact(new Contact(fullName, phoneNumber, email))) {
                    System.out.println(System.lineSeparator() + "Контакт добавлен" + System.lineSeparator());
                } else {
                    System.err.println("Контакт с такими контактными данными уже имеется");
                }
                break;
            }
        }
    }

    private static void deleteContact() {
        System.out.print("Для удаления контакта введите адрес электронной почты: ");
        String command = scanner.nextLine();
        if (contactWorkerManager.deleteContactByEmail(command)) {
            System.out.println(System.lineSeparator() + "Контакт удален" + System.lineSeparator());
        } else {
            System.err.println("Нет контакта с данным адресом электронной почты");
        }
    }

    private static void saveContacts() {
        contactWorkerManager.saveContactsToFile();
    }
}