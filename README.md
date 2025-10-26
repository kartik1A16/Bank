# Simple Indian Banking System

A comprehensive Java-based banking system that demonstrates core Object-Oriented Programming concepts, file I/O operations, and exception handling. The system implements basic banking operations while following Indian banking norms including KYC (Know Your Customer).

## Project Structure

```
├── MyBank.java              # Main entry point
├── BankManager.java         # Core banking operations manager
├── Account.java            # Abstract base class for accounts
├── SavingsAccount.java     # Savings account implementation
├── CurrentAccount.java     # Current account implementation
├── Customer.java          # Customer entity class
├── KYCDetails.java        # KYC information handler
├── Transactable.java      # Banking transaction interface
├── Exceptions/
│   ├── InsufficientFundsException.java
│   ├── AccountNotFoundException.java
│   ├── CustomerNotFoundException.java
│   └── InvalidKYCException.java
└── Data/
    ├── bank_accounts.csv   # Account data storage
    └── bank_customers.csv  # Customer data storage
```

## Core Concepts Implemented

### 1. Object-Oriented Programming (OOP)

#### a. Encapsulation
- All classes use private fields with public getters/setters
- Data hiding is implemented to protect internal state
- Implementation details are hidden from external classes
- Example: `Customer` class encapsulates customer data and KYC details

#### b. Inheritance
- `Account` serves as the abstract base class
- `SavingsAccount` and `CurrentAccount` inherit from `Account`
- Common account behavior defined in parent class
- Specialized behavior implemented in child classes

#### c. Polymorphism
- Method overriding in account classes
- Different withdraw behaviors for different account types
- Runtime polymorphism through account type handling
- Example: `withdraw()` method behaves differently in `CurrentAccount`

#### d. Abstraction
- `Account` as abstract class
- `Transactable` interface
- High-level operations defined without implementation details
- Common contract for all account types

### 2. Interfaces and Abstract Classes

#### Transactable Interface
```java
public interface Transactable {
    void deposit(double amount) throws IllegalArgumentException;
    void withdraw(double amount) throws InsufficientFundsException;
}
```
- Defines contract for banking transactions
- Enforces implementation of basic banking operations
- Provides abstraction for different account types

#### Abstract Account Class
```java
public abstract class Account implements Transactable {
    // Common account properties and methods
    public abstract String getAccountType();
}
```
- Base template for all account types
- Implements common functionality
- Forces account type specification through abstract method

### 3. Class Descriptions

#### MyBank Class
- Entry point of the application
- Creates BankManager instance
- Starts the banking system

#### BankManager Class
- Central controller for banking operations
- Manages customer and account collections
- Handles user interface and input
- Implements file I/O operations
- Key functionalities:
  - Customer creation
  - Account opening
  - Fund transfers
  - Balance inquiries
  - Data persistence

#### Customer Class
- Represents bank customers
- Stores personal information
- Links to KYC details
- Properties:
  - Customer ID
  - Name
  - KYC Information

#### KYCDetails Class
- Handles Know Your Customer information
- Validates Indian ID formats
- Stores:
  - PAN (Permanent Account Number)
  - Aadhaar Number
- Implements validation rules

#### SavingsAccount Class
- Personal savings account implementation
- Features:
  - Interest rate calculation
  - Standard withdrawal limits
  - Basic banking operations

#### CurrentAccount Class
- Business account implementation
- Features:
  - Overdraft facility
  - No interest earnings
  - Enhanced withdrawal limits

### 4. Exception Handling

#### Custom Exceptions
1. **InsufficientFundsException**
   - Thrown when withdrawal amount exceeds balance
   - Includes overdraft consideration for current accounts

2. **AccountNotFoundException**
   - Thrown when account number doesn't exist
   - Used in account lookup operations

3. **CustomerNotFoundException**
   - Thrown when customer ID isn't found
   - Used in customer lookup operations

4. **InvalidKYCException**
   - Thrown during KYC validation
   - Handles invalid PAN or Aadhaar formats

### 5. File I/O Operations

#### Data Persistence
- CSV file format for data storage
- Separate files for customers and accounts
- Automatic loading on startup
- Regular saving of changes

#### File Format
1. **bank_customers.csv**
   ```
   CustomerID,Name,PANNumber,AadhaarNumber
   ```

2. **bank_accounts.csv**
   ```
   AccountNumber,CustomerID,Balance,AccountType,AdditionalDetails
   ```

### 6. Features

1. **Customer Management**
   - Create new customers with KYC
   - View customer details
   - Link multiple accounts

2. **Account Operations**
   - Open new accounts (Savings/Current)
   - Deposit funds
   - Withdraw funds
   - Transfer between accounts
   - Check balance

3. **Security Features**
   - Input validation
   - Exception handling
   - Data persistence
   - KYC verification

### 7. How to Run

1. Compile all Java files:
```bash
javac *.java
```

2. Run the application:
```bash
java MyBank
```

3. Follow the interactive menu to perform banking operations

### 8. Best Practices Implemented

1. **Code Organization**
   - Separate files for each class
   - Logical package structure
   - Clear class responsibilities

2. **Error Handling**
   - Custom exceptions for business logic
   - Proper exception propagation
   - User-friendly error messages

3. **Data Validation**
   - Input validation
   - Business rule enforcement
   - KYC compliance checks

4. **Code Maintainability**
   - Clear documentation
   - Consistent coding style
   - Modular design

### 9. Future Enhancements

1. Database Integration
   - Replace CSV files with proper database
   - Implement transaction management
   - Add data backup features

2. Security Improvements
   - User authentication
   - Encryption for sensitive data
   - Access control levels

3. Additional Features
   - Transaction history
   - Account statements
   - Multiple currency support
   - Online banking interface

## Program Working and Flow

### 1. Program Initialization
1. When you run `java MyBank`:
   - The `MyBank` class creates an instance of `BankManager`
   - `BankManager` initializes empty lists for customers and accounts
   - The program attempts to load existing data from CSV files

### 2. Main Menu Operation
The program presents a menu with 8 options:
```
1. Create New Customer (with KYC)
2. Open New Bank Account
3. Deposit Funds
4. Withdraw Funds
5. Transfer Funds
6. Check Account Balance & Details
7. View Customer Details
8. Save and Exit
```

### 3. Detailed Operation Flow

#### a. Creating a New Customer (Option 1)
1. User inputs:
   - Customer name
   - PAN number (10 characters)
   - Aadhaar number (12 digits)
2. Process:
   - `KYCDetails` validates the input formats
   - Generates unique customer ID (e.g., "CUST-1")
   - Creates `Customer` object with KYC details
   - Adds to customer list
3. Output:
   - Success message with customer ID
   - Error message if KYC validation fails

#### b. Opening New Account (Option 2)
1. User inputs:
   - Existing customer ID
   - Account type (1: Savings, 2: Current)
   - Initial deposit amount
2. Process:
   - Verifies customer existence
   - Creates appropriate account object (`SavingsAccount`/`CurrentAccount`)
   - Generates unique account number (e.g., "ACCT-1001")
   - Links account to customer
3. Output:
   - Success message with account number
   - Error if customer not found or invalid deposit

#### c. Depositing Funds (Option 3)
1. User inputs:
   - Account number
   - Deposit amount
2. Process:
   - Locates account
   - Validates amount (must be positive)
   - Updates account balance
3. Output:
   - New balance confirmation
   - Error if account not found or invalid amount

#### d. Withdrawing Funds (Option 4)
1. User inputs:
   - Account number
   - Withdrawal amount
2. Process:
   - Locates account
   - Checks balance sufficiency
   - For CurrentAccount: Considers overdraft limit
   - Updates balance if successful
3. Output:
   - New balance confirmation
   - Error if insufficient funds or invalid amount

#### e. Fund Transfer (Option 5)
1. User inputs:
   - Source account number
   - Destination account number
   - Transfer amount
2. Process:
   - Validates both accounts
   - Checks source account balance
   - Performs withdrawal from source
   - Performs deposit to destination
3. Output:
   - Success confirmation
   - Error if any step fails

#### f. Balance Check (Option 6)
1. User inputs:
   - Account number
2. Process:
   - Retrieves account details
   - Formats balance information
3. Output:
   - Account type
   - Current balance
   - Error if account not found

#### g. Customer Details (Option 7)
1. User inputs:
   - Customer ID
2. Process:
   - Retrieves customer information
   - Finds all linked accounts
3. Output:
   - Customer details (Name, ID, masked KYC)
   - List of associated accounts
   - Error if customer not found

#### h. Save and Exit (Option 8)
1. Process:
   - Writes customer data to bank_customers.csv
   - Writes account data to bank_accounts.csv
   - Closes scanner and exits

### 4. Data Persistence

#### Customer Data Format (bank_customers.csv)
```
CUST-1,John Doe,ABCDE1234F,123456789012
[CustomerID,Name,PANNumber,AadhaarNumber]
```

#### Account Data Format (bank_accounts.csv)
```
ACCT-1001,CUST-1,5000.00,Savings,0.035
[AccountNumber,CustomerID,Balance,AccountType,InterestRate/OverdraftLimit]
```

### 5. Error Handling
- Invalid inputs are caught and reported
- Insufficient funds trigger appropriate exceptions
- File operations handle IO exceptions gracefully
- KYC validation ensures data integrity

### 6. Security Features
- Aadhaar numbers are masked in displays
- Balance checks before transactions
- Data validation at every step
- Proper error reporting without exposing system details

## Contributing

This project is designed for educational purposes to demonstrate Java programming concepts. Contributions are welcome to enhance functionality or improve code organization.

## License

This project is open-source and available for educational purposes.