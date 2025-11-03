package br.com.danielmota.data.dto;

public class DashboardDTO {
    private double income;
    private double outcome;
    private double balance;

    public DashboardDTO(double income, double outcome) {
        this.income = income;
        this.outcome = outcome;
        this.balance = income - outcome;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getOutcome() {
        return outcome;
    }

    public void setOutcome(double outcome) {
        this.outcome = outcome;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
