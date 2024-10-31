package fr.exalt.businessmicroserviceaccount.domain.entities;

public class SavingBankAccount extends BankAccount{
    private double interestRate;
    private SavingBankAccount(SavingBankAccount.SavingAccountBuilder builder){
        super();
        this.interestRate = builder.interestRate;
    }

    public static SavingBankAccount.SavingAccountBuilder builder(){
        return new SavingBankAccount.SavingAccountBuilder();
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
    public static class SavingAccountBuilder {
        private double interestRate;
        public SavingAccountBuilder interestRate(double interestRate){
            this.interestRate = interestRate;
            return this;
        }
        public SavingBankAccount build(){
            return new SavingBankAccount(this);
        }
    }
}
