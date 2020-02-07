package bank;

/**
 * @author 18630 Nicholas Chibuike-Eruba 18630
 */
public class Customer {

    double currentBalance;
    double savingsBalance;
    String accountType;
    String accountNumber;
    String transactionFileName;
    String pin;

    public Customer(String accountNumber, double balance, String accountType, String transactionFileName) {
        this.accountNumber = accountNumber;
        switch (accountType) {
            case "savings":
                this.accountType = accountType;
                setSavingsBalance(balance);
                break;
            case "current":
                this.accountType = accountType;
                setCurrentBalance(balance);
                break;
            default:
                try {
                    throw new Exception("the accountType is not spelled correctly try 'savings' || 'current' ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        this.transactionFileName = transactionFileName;
    }

    public Customer(String accountNumber, String pin) {
        this.accountNumber = accountNumber;
        this.pin = pin;
    }

    public double getSavingsBalance() {
        return savingsBalance;
    }

    public void setCurrentBalance(double curBal) {
        this.currentBalance = curBal;
    }

    public void setSavingsBalance(double savingsBalance) {
        this.savingsBalance = savingsBalance;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    String getAccountNumber() {
        return accountNumber;
    }

    //employee action methods
    void requestTransactionHistory(BankEmployee bankEmployee) {
        bankEmployee.customerTransactionHistory(this);
    }

    void depositSavings(BankEmployee bankEmployee) {
        bankEmployee.addToCustomerAccount(this, "savings");
    }

    void depositCurrent(BankEmployee bankEmployee) {
        bankEmployee.addToCustomerAccount(this, "current");
    }

    void withdrawSavings(BankEmployee bankEmployee) {
        bankEmployee.withdrawFromCustomerAccount(this, "savings");
    }

    void withdrawCurrent(BankEmployee bankEmployee) {
        bankEmployee.withdrawFromCustomerAccount(this, "current");
    }
}
