package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();

        service.createUsersTable();
        service.saveUser("Petr", "Petrov", (byte) 20);
        service.saveUser("Ivan", "Ivanov", (byte) 23);
        service.saveUser("Anatoliy", "Sidirov", (byte) 33);
        service.saveUser("Andrew", "Pavlov", (byte) 18);
        service.getAllUsers();
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
