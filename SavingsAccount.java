public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, String customerId, double initialDeposit) {
        super(accountNumber, customerId, initialDeposit);
        this.interestRate = 0.035;
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    public void applyInterest() {
        double interest = getBalance() * interestRate;
        try {
            deposit(interest);
            System.out.printf("Applied interest: %.2f\n", interest);
        } catch (IllegalArgumentException e) {
            // This shouldn't happen with valid interest
        }
    }
    
    @Override
    public String toCsvString() {
        return super.toCsvString() + "," + interestRate;
    }
}