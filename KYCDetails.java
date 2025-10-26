public class KYCDetails {
    private String panNumber;
    private String aadhaarNumber;

    public KYCDetails(String panNumber, String aadhaarNumber) throws InvalidKYCException {
        if (panNumber == null || panNumber.length() != 10) {
            throw new InvalidKYCException("Invalid PAN number format (must be 10 characters).");
        }
        
        if (aadhaarNumber == null || aadhaarNumber.length() != 12) {
             throw new InvalidKYCException("Invalid Aadhaar number format (must be 12 digits).");
        }
        
        for (char c : aadhaarNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new InvalidKYCException("Invalid Aadhaar number format (must contain only digits).");
            }
        }
        
        this.panNumber = panNumber;
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getPanNumber() { return panNumber; }
    public String getAadhaarNumber() { return aadhaarNumber; }

    public String toCsvString() {
        return panNumber + "," + aadhaarNumber;
    }
}