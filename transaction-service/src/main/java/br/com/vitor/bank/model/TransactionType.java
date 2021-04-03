package br.com.vitor.bank.model;

public enum TransactionType {

    INCOME, EXPENSE;

    public boolean isIncome() {
        return this == INCOME;
    }

}