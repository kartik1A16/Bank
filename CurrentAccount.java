public class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String customerId, double initialDeposit) {
        super(accountNumber, customerId, initialDeposit);
        this.overdraftLimit = 5000.00;
    }

    @Override
    public String getAccountType() {
        return "Current";
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (this.balance + this.overdraftLimit < amount) {
            throw new InsufficientFundsException(
                String.format("Insufficient funds, even with overdraft. Available: %.2f, Tried: %.2f",
                this.balance + this.overdraftLimit, amount)
            );
        }
        this.balance -= amount;
        System.out.printf("Withdrew: %.2f | New Balance: %.2f\n", amount, this.balance);
    }
    
    @Override
    public String toCsvString() {
        return super.toCsvString() + "," + overdraftLimit;
    }
}