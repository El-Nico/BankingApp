package bank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * @author 18630
 *  Nicholas Chibuike-Eruba
 * 18630
 */
public class BankEmployee extends BankManager {

    ///////////////////////////////////////////////////////Methods implementing customer request//////////////////////////////////////
    //will print the customers transaction history
    void customerTransactionHistory(Customer customer) {
        //get account type
        Scanner inType = new Scanner(System.in);
        System.out.println("Current or Savings?");
        String acType = inType.nextLine().toLowerCase();
        if (acType.equals("current")) {
            try {
                FileReader reader = new FileReader(getWorkingDirectory() + "/" + getTransactionFileName(customer.getAccountNumber(), "current"));
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    //display each line
                    System.out.println(line);
                }
                reader.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (acType.equals("savings")) {
            try {
                FileReader reader = new FileReader(getWorkingDirectory() + "/" + getTransactionFileName(customer.getAccountNumber(), "savings"));
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    //display each line
                    System.out.println(line);
                }
                reader.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("this is not a valid account type");
        }

    }

    public void addToCustomerAccount(Customer customer, String acType) {
        //log the transaction and increase the balance
        Scanner inDetails = new Scanner(System.in);
        if (new File(getWorkingDirectory() + "/" + getTransactionFileName(customer.getAccountNumber(), acType)).exists()) {
            double amt = 0.00;
            boolean initializedProposedAmount = false;
            System.out.println("how much would you like to add to this account");
            do {//get the proposed amount

                //check if a double and not negative
                if (inDetails.hasNextDouble()) {
                    amt = inDetails.nextDouble();
                    if (amt >= 0) {
                        initializedProposedAmount = true;
                    } else {
                        System.out.println("wrong input,insert a non negative number");
                    }
                } else if (inDetails.hasNext("exit")) {
                    System.out.println("you exited the amount menu");
                    return;
                } else {
                    System.out.println("wrong input,insert a non negative number value");
                    inDetails.next();
                }
            } while (!initializedProposedAmount);
            customer = new Customer(
                    customer.getAccountNumber(),
                    getBalanceFromFile(customer.getAccountNumber(), acType.toLowerCase()),
                    acType.toLowerCase(),
                    getWorkingDirectory() + "/" + getTransactionFileName(customer.getAccountNumber(), acType)
            );
            if (acType.toLowerCase().equals("savings")) {
                customer.setSavingsBalance(customer.getSavingsBalance() + amt);
                logTransaction(getWorkingDirectory() + "/" + getTransactionFileName(customer.getAccountNumber(), acType), LocalDateTime.now(), "lodgement", amt, customer.getSavingsBalance());
            } else {
                customer.setCurrentBalance(customer.getCurrentBalance() + amt);
                logTransaction(getWorkingDirectory() + "/" + getTransactionFileName(customer.getAccountNumber(), acType), LocalDateTime.now(), "lodgement", amt, customer.getCurrentBalance());
            }

        } else {
            System.out.println("How on God's green earth did we get here");
        }

    }

    public void withdrawFromCustomerAccount(Customer customer, String acType) {
        //get the account number and type
        Scanner inDetails = new Scanner(System.in);

        //log the transaction and decrease the balance
        if (new File(getWorkingDirectory() + "/" + getTransactionFileName(customer.getAccountNumber(), acType)).exists()) {
            double amt = 0.00;
            boolean initializedProposedAmount = false;
            System.out.println("how much would you like to withdraw from this account");
            do {//get the proposed amount

                //check if a double and not negative
                if (inDetails.hasNextDouble()) {
                    amt = inDetails.nextDouble();
                    if (amt >= 0) {
                        initializedProposedAmount = true;
                    } else {
                        System.out.println("wrong input,insert a non negative number");
                    }
                } else if (inDetails.hasNext("exit")) {
                    System.out.println("you exited the amount menu");
                    return;
                } else {
                    System.out.println("wrong input,insert a non negative number value");
                    inDetails.next();
                }
            } while (!initializedProposedAmount);
            customer = new Customer(
                    customer.getAccountNumber(),
                    getBalanceFromFile(customer.getAccountNumber(), acType.toLowerCase()),
                    acType.toLowerCase(),
                    getWorkingDirectory() + "/" + getTransactionFileName(customer.getAccountNumber(), acType)
            );
            if (acType.toLowerCase().equals("savings")) {
                if ((customer.getSavingsBalance() - amt) >= 0) {
                    customer.setSavingsBalance(customer.getSavingsBalance() - amt);
                    logTransaction(getWorkingDirectory() + "/" + getTransactionFileName(customer.getAccountNumber(), acType), LocalDateTime.now(), "withdrawal", amt, customer.getSavingsBalance());
                } else {
                    System.out.println("insufficient balance");
                }

            } else {
                if ((customer.getCurrentBalance() - amt) >= -1 * CurrentAccount.overdrawLimit) {
                    customer.setCurrentBalance(customer.getCurrentBalance() - amt);
                    logTransaction(getWorkingDirectory() + "/" + getTransactionFileName(customer.getAccountNumber(), acType), LocalDateTime.now(), "withdrawal", amt, customer.getCurrentBalance());
                } else {
                    System.out.println("the amount is over the overdraw limit");
                }
            }

        } else {
            System.out.println("How on God's green earth did we get here");
        }
    }
}
