package com.epam.izh.rd.online;

import com.epam.izh.rd.online.service.ProcessCalculateService;

public class Calculator {

    public static void main(String[] args) {
        ProcessCalculateService processCalculateService = new ProcessCalculateService();
        processCalculateService.startExpressionCalculator();
    }
}