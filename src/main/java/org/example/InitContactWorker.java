package org.example;

public class InitContactWorker extends CommonContactWorker {
    @Override
    public void saveContactsToFile() {
        System.out.println("В данном профиле нельзя сохранить данные в файл");
    }
}
