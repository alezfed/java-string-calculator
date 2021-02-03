package com.epam.izh.rd.online.service;

public interface ParsingService {

    void validateOnContent(String expression);

    String toPolishNotation(String expression);

    String getAllowedOperation();
}