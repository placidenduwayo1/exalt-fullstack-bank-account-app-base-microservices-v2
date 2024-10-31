package fr.exalt.businessmicroserviceaccount.domain.entities;

public class CurrentBankAccount extends BankAccount{
    private double overdraft;
    private CurrentBankAccount(CurrentBankAccount.CurrentAccountBuilder builder){
        super();
        this.overdraft = builder.overdraft;
    }

    public static CurrentBankAccount.CurrentAccountBuilder builder(){
        return new CurrentBankAccount.CurrentAccountBuilder();
    }

    public double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }

    public static class CurrentAccountBuilder {
        private double overdraft;

        public CurrentAccountBuilder overdraft(double overdraft) {
            this.overdraft = overdraft;
            return this;
        }
        public CurrentBankAccount build(){
            return new CurrentBankAccount(this);
        }
    }
}
