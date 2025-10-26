public interface Transactable {
    void deposit(double amount) throws IllegalArgumentException;
    void withdraw(double amount) throws InsufficientFundsException;
}