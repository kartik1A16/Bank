/**
 * A simple Indian Banking System with separate class files.
 * This is the main entry point class.
 */
public class MyBank {
    /**
     * Main Method
     * This is the entry point for the JVM. All it does
     * is create the BankManager object and start the 'run' loop.
     */
    public static void main(String[] args) {
        BankManager bank = new BankManager();
        bank.run();
    }
}