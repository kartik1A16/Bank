public abstract class Account implements Transactable {
    private String accountNumber;
    private String customerId;
    protected double balance;

    public Account(String accountNumber, String customerId, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.balance = initialDeposit;
    }

    public abstract String getAccountType();

    public String getAccountNumber() { return accountNumber; }
    public String getCustomerId() { return customerId; }
    public double getBalance() { return balance; }

    @Override
    public void deposit(double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance += amount;
        System.out.printf("Deposited: %.2f | New Balance: %.2f\n", amount, this.balance);
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (this.balance < amount) {
            throw new InsufficientFundsException(
                String.format("Insufficient funds. Balance: %.2f, Tried to withdraw: %.2f", this.balance, amount)
            );
        }
        this.balance -= amount;
        System.out.printf("Withdrew: %.2f | New Balance: %.2f\n", amount, this.balance);
    }

    public String toCsvString() {
        return accountNumber + "," + customerId + "," + balance + "," + getAccountType();
    }

    public String getDetails() {
        return String.format("Account: %s (%s) | Balance: %.2f", 
            accountNumber, getAccountType(), balance);
    }
}