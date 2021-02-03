package com.epam.izh.rd.online.enity;

public class Operation {
    private String stringForm;
    private int operandCount;
    private int priority;
    private String regex;

    public Operation(String stringForm,
                     int operandCount,
                     int priority,
                     String regex) {
        this.stringForm = stringForm;
        this.operandCount = operandCount;
        this.priority = priority;
        this.regex = regex;
    }

    public String getStringForm() {
        return stringForm;
    }

    public void setStringForm(String stringForm) {
        this.stringForm = stringForm;
    }

    public int getOperandCount() {
        return operandCount;
    }

    public void setOperandCount(int operandCount) {
        this.operandCount = operandCount;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "stringForm='" + stringForm + '\'' +
                ", operandCount=" + operandCount +
                ", priority=" + priority +
                ", regex='" + regex + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation = (Operation) o;

        if (operandCount != operation.operandCount) return false;
        if (priority != operation.priority) return false;
        if (stringForm != null ? !stringForm.equals(operation.stringForm) : operation.stringForm != null) return false;
        return regex != null ? regex.equals(operation.regex) : operation.regex == null;
    }

    @Override
    public int hashCode() {
        int result = stringForm != null ? stringForm.hashCode() : 0;
        result = 31 * result + operandCount;
        result = 31 * result + priority;
        result = 31 * result + (regex != null ? regex.hashCode() : 0);
        return result;
    }
}