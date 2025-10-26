public class Customer {
    private String customerId;
    private String name;
    private KYCDetails kyc;

    public Customer(String customerId, String name, KYCDetails kyc) {
        this.customerId = customerId;
        this.name = name;
        this.kyc = kyc;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public KYCDetails getKyc() { return kyc; }

    public String toCsvString() {
        return customerId + "," + name + "," + kyc.toCsvString();
    }

    public String getDetails() {
        return String.format("Customer: %s (ID: %s) | PAN: %s | Aadhaar: ...%s",
            name, customerId, kyc.getPanNumber(), 
            kyc.getAadhaarNumber().substring(8));
    }
}