package com.epam.izh.rd.online.enity;

import lombok.*;

@Data
@AllArgsConstructor
public class Operation {
    private String stringForm;
    private int operandCount;
    private int priority;
    private String regex;
}