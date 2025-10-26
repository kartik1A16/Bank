import java.io.*;
import java.util.*;

public class BankManager {
    private List<Customer> customers;
    private List<Account> accounts;
    private Scanner scanner;

    private static final String CUSTOMERS_FILE = "bank_customers.csv";
    private static final String ACCOUNTS_FILE = "bank_accounts.csv";

    public BankManager() {
        this.customers = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        loadData();
        
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1: handleCreateCustomer(); break;
                case 2: handleOpenAccount(); break;
                case 3: handleDeposit(); break;
                case 4: handleWithdraw(); break;
                case 5: handleFundTransfer(); break;
                case 6: handleCheckBalance(); break;
                case 7: handleViewCustomer(); break;
                case 8: 
                    saveData();
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
        System.out.println("Thank you for using Simple Indian Banking. Data saved.");
    }

    private void printMenu() {
        System.out.println("\n--- Simple Indian Banking System ---");
        System.out.println("1. Create New Customer (with KYC)");
        System.out.println("2. Open New Bank Account");
        System.out.println("3. Deposit Funds");
        System.out.println("4. Withdraw Funds");
        System.out.println("5. Transfer Funds");
        System.out.println("6. Check Account Balance & Details");
        System.out.println("7. View Customer Details");
        System.out.println("8. Save and Exit");
    }

    private void handleCreateCustomer() {
        try {
            String name = getStringInput("Enter customer name: ");
            String pan = getStringInput("Enter PAN (10 characters): ");
            String aadhaar = getStringInput("Enter 12-digit Aadhaar: ");
            
            KYCDetails kyc = new KYCDetails(pan, aadhaar);
            String customerId = "CUST-" + (customers.size() + 1);
            Customer customer = new Customer(customerId, name, kyc);
            
            customers.add(customer);
            System.out.println("Customer created successfully! ID: " + customerId);
        } catch (InvalidKYCException e) {
            System.out.println("Customer creation failed: " + e.getMessage());
        }
    }

    private void handleOpenAccount() {
        try {
            String customerId = getStringInput("Enter existing Customer ID: ");
            Customer customer = findCustomer(customerId);
            
            String type = getStringInput("Enter account type (1: Savings, 2: Current): ");
            double initialDeposit = getDoubleInput("Enter initial deposit amount: ");
            if (initialDeposit <= 0) {
                System.out.println("Initial deposit must be positive.");
                return;
            }
            
            String accountNumber = "ACCT-" + (accounts.size() + 1001);
            Account account;
            
            if (type.equals("1")) {
                account = new SavingsAccount(accountNumber, customerId, initialDeposit);
            } else if (type.equals("2")) {
                account = new CurrentAccount(accountNumber, customerId, initialDeposit);
            } else {
                System.out.println("Invalid account type.");
                return;
            }
            
            accounts.add(account);
            System.out.println("Account opened successfully! Number: " + accountNumber);
            
        } catch (CustomerNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleDeposit() {
        try {
            String acctNum = getStringInput("Enter Account Number: ");
            Account account = findAccount(acctNum);
            
            double amount = getDoubleInput("Enter amount to deposit: ");
            account.deposit(amount);
            
        } catch (AccountNotFoundException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleWithdraw() {
        try {
            String acctNum = getStringInput("Enter Account Number: ");
            Account account = findAccount(acctNum);
            
            double amount = getDoubleInput("Enter amount to withdraw: ");
            account.withdraw(amount);
            
        } catch (AccountNotFoundException | InsufficientFundsException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleFundTransfer() {
        try {
            String fromAcctNum = getStringInput("Enter YOUR Account Number: ");
            String toAcctNum = getStringInput("Enter Recipient Account Number: ");
            double amount = getDoubleInput("Enter amount to transfer: ");

            Account fromAccount = findAccount(fromAcctNum);
            Account toAccount = findAccount(toAcctNum);
            
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
            
            System.out.println("Transfer successful!");
            
        } catch (AccountNotFoundException | InsufficientFundsException | IllegalArgumentException e) {
            System.out.println("Transfer Failed: " + e.getMessage());
        }
    }

    private void handleCheckBalance() {
         try {
            String acctNum = getStringInput("Enter Account Number: ");
            Account account = findAccount(acctNum);
            System.out.println(account.getDetails());
            
        } catch (AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleViewCustomer() {
        try {
            String custId = getStringInput("Enter Customer ID: ");
            Customer customer = findCustomer(custId);
            System.out.println(customer.getDetails());
            
            System.out.println("Associated Accounts:");
            for(Account acc : accounts) {
                if(acc.getCustomerId().equals(custId)) {
                    System.out.println(" -> " + acc.getDetails());
                }
            }
            
        } catch (CustomerNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Customer findCustomer(String customerId) throws CustomerNotFoundException {
        for (Customer c : customers) {
            if (c.getCustomerId().equals(customerId)) {
                return c;
            }
        }
        throw new CustomerNotFoundException("No customer found with ID: " + customerId);
    }

    private Account findAccount(String accountNumber) throws AccountNotFoundException {
        for (Account a : accounts) {
            if (a.getAccountNumber().equals(accountNumber)) {
                return a;
            }
        }
        throw new AccountNotFoundException("No account found with number: " + accountNumber);
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(getStringInput(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(getStringInput(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (e.g., 500.00).");
            }
        }
    }

    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMERS_FILE))) {
            for (Customer c : customers) {
                writer.write(c.toCsvString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving customer data: " + e.getMessage());
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (Account a : accounts) {
                writer.write(a.toCsvString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving account data: " + e.getMessage());
        }
    }

    private void loadData() {
        File cFile = new File(CUSTOMERS_FILE);
        if (cFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(cFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    KYCDetails kyc = new KYCDetails(parts[2], parts[3]);
                    Customer c = new Customer(parts[0], parts[1], kyc);
                    customers.add(c);
                }
            } catch (Exception e) {
                System.out.println("Error loading customer data: " + e.getMessage());
            }
        }
        
        File aFile = new File(ACCOUNTS_FILE);
        if (aFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(aFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String acctNum = parts[0];
                    String custId = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    String type = parts[3];
                    
                    Account account = null;
                    if (type.equals("Savings")) {
                        account = new SavingsAccount(acctNum, custId, 0);
                    } else if (type.equals("Current")) {
                        account = new CurrentAccount(acctNum, custId, 0);
                    }
                    
                    if(account != null) {
                        account.balance = balance;
                        accounts.add(account);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error loading account data: " + e.getMessage());
            }
        }
        
        if (!customers.isEmpty() || !accounts.isEmpty()) {
            System.out.println("Data loaded successfully.");
        }
    }
}