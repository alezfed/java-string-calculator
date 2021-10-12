package com.epam.izh.rd.online.service;

import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class ConsoleUserRequestService implements UserRequestService {
    private final Scanner scanner;

    public ConsoleUserRequestService() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String getExpression() {
        scanner.nextLine();
        return scanner.nextLine();
    }

    @Override
    public int getIntNumber() {
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            return -1;
        }
        return scanner.nextInt();
    }
}