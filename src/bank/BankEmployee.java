/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 *
 * @author 18630
 */
public class BankEmployee extends runnerLogin {

    public void createCustomer() {
        ///get their firstname, lastname and email
        Scanner inDetails = new Scanner(System.in);
        System.out.println("thankyou for choosing to create and account with us\n"
                + "please insert your first name");
        String firstName = inDetails.nextLine();
        System.out.println("now insert your last name");
        String lastName = inDetails.nextLine();
        System.out.println("and finally your email");
        String email = inDetails.nextLine();
        System.out.println("we will now create your account");

        try {
            //create savings transaction account
            String workingDirectory = new File(".").getCanonicalPath() + "/src/bank/" + generateAccountNumber(firstName, lastName, "savings");
            File file = new File(workingDirectory);
            boolean createdSavings = file.createNewFile();
            if (!createdSavings) {
                System.out.println("the savings account already exists");
            } else {
                //write boilerplate
                FileWriter writer = new FileWriter(workingDirectory, true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);

                bufferedWriter.write("Date\tAction\tAMT\tBalance");
                bufferedWriter.newLine();
                bufferedWriter.close();
                logTransaction(getWorkingDirectory() + "/" + getTransactionFileName(generateAccountNumber(firstName, lastName), "savings"), LocalDateTime.now(), "created", 0.00, 0.00);

            }
            //create current transaction account
            workingDirectory = new File(".").getCanonicalPath() + "/src/bank/" + generateAccountNumber(firstName, lastName, "current");
            file = new File(workingDirectory);
            boolean createdCurrent = file.createNewFile();
            if (!createdCurrent) {
                System.out.println("the current account already exists");
            } else {
                //write boilerplate
                FileWriter writer = new FileWriter(workingDirectory, true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);

                bufferedWriter.write("Date\tAction\tAMT\tBalance");
                bufferedWriter.newLine();
                bufferedWriter.close();
                logTransaction(getWorkingDirectory() + "/" + getTransactionFileName(generateAccountNumber(firstName, lastName), "current"), LocalDateTime.now(), "created", 0.00, 0.00);

//            bufferedWriter.write("Date\tAction\tAMT\tBalance");
//            bufferedWriter.newLine();
            }
            if (!createdSavings && !createdCurrent) {
                return;
            } else {
                //add to customer database file
                FileWriter writer = new FileWriter(getWorkingDirectory() + "/customers.txt", true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);

                bufferedWriter.write(firstName + "\t" + lastName + "\t" + generateAccountNumber(firstName, lastName) + "\t" + "Savings");
                bufferedWriter.newLine();
                bufferedWriter.write(firstName + "\t" + lastName + "\t" + generateAccountNumber(firstName, lastName) + "\t" + "Current");
                bufferedWriter.newLine();
                bufferedWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void deleteCustomerAccount() {
        //get details and differentisate by type
        //get the account number and type
        Scanner inDetails = new Scanner(System.in);
        System.out.println("please insert the customer account number");
        String acNo = inDetails.nextLine();
        System.out.println("please insert the account type");
        String acType = inDetails.nextLine();

//then check if balance zero or -1500
        if (new File(getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType)).exists()) {

            Customer customer = new Customer(
                    acNo,
                    getBalanceFromFile(acNo, acType.toLowerCase()),
                    acType.toLowerCase(),
                    getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType)
            );
            if (acType.toLowerCase().equals("savings") && customer.getSavingsBalance() == 0.0) {
                new File(getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType)).delete();
                //read customers.txt,
                try {
                    File customersFile = new File(getWorkingDirectory() + "/customers.txt");
                    File tempFile = new File(getWorkingDirectory() + "/customers-temp.txt");

                    BufferedReader reader = new BufferedReader(new FileReader(customersFile));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                    String currentLine;

                    while ((currentLine = reader.readLine()) != null) {
                        if (null != currentLine && !currentLine.contains(acNo+"\tSavings")) {
                            writer.write(currentLine + System.getProperty("line.separator"));
                        }
                    }
                    writer.close();
                    reader.close();
                    customersFile.delete();
                    boolean successful = tempFile.renameTo(customersFile);
                    System.out.println(successful);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } //current
            else if (acType.toLowerCase().equals("current") && customer.getSavingsBalance() == -1 * CurrentAccount.overdrawLimit) {
                new File(getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType)).delete();
                //read customers.txt,
                try {
                    File customersFile = new File(getWorkingDirectory() + "/customers.txt");
                    File tempFile = new File(getWorkingDirectory() + "/customers-temp.txt");

                    BufferedReader reader = new BufferedReader(new FileReader(customersFile));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                    String currentLine;

                    while ((currentLine = reader.readLine()) != null) {
                        if (null != currentLine && !currentLine.contains(acNo)) {
                            writer.write(currentLine + System.getProperty("line.separator"));
                        }
                    }
                    writer.close();
                    reader.close();
                    boolean successful = tempFile.renameTo(customersFile);
                    System.out.println(successful);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } else {
            System.out.println("the customer account does not exist, you should create the customer first or insert the correct acc no");
        }

        //delete their current or savings file, and their current or savings entry on customers.txt
    }

    protected void addToCustomerAccount() {
        //get the account number and type
        Scanner inDetails = new Scanner(System.in);
        System.out.println("please insert the customer account number");
        String acNo = inDetails.nextLine();
        System.out.println("please insert the account type");
        String acType = inDetails.nextLine();

        //log the transaction and increase the balance
        if (new File(getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType)).exists()) {
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
                        //inDetails.next();
                    }
                } else if (inDetails.hasNext("exit")) {
                    System.out.println("you exited the amount menu");
                    return;
                } else {
                    System.out.println("wrong input,insert a non negative number value");
                    inDetails.next();
                }
            } while (!initializedProposedAmount);
            Customer customer = new Customer(
                    acNo,
                    getBalanceFromFile(acNo, acType.toLowerCase()),
                    acType.toLowerCase(),
                    getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType)
            );
            if (acType.toLowerCase().equals("savings")) {
                customer.setSavingsBalance(customer.getSavingsBalance() + amt);
                //System.out.println(customer.getSavingsBalance());
                logTransaction(getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType), LocalDateTime.now(), "lodgement", amt, customer.getSavingsBalance());
            } else {
                customer.setCurrentBalance(customer.getCurrentBalance() + amt);
                logTransaction(getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType), LocalDateTime.now(), "lodgement", amt, customer.getCurrentBalance());
            }

        } else {
            System.out.println("the customer account does not exist, you should create the customer first or insert the correct acc no");
        }

    }

    protected void withdrawFromCustomerAccount() {
        //get the account number and type
        Scanner inDetails = new Scanner(System.in);
        System.out.println("please insert the customer account number");
        String acNo = inDetails.nextLine();
        System.out.println("please insert the account type");
        String acType = inDetails.nextLine();

        //log the transaction and decrease the balance
        if (new File(getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType)).exists()) {
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
                        //inDetails.next();
                    }
                } else if (inDetails.hasNext("exit")) {
                    System.out.println("you exited the amount menu");
                    return;
                } else {
                    System.out.println("wrong input,insert a non negative number value");
                    inDetails.next();
                }
            } while (!initializedProposedAmount);
            Customer customer = new Customer(
                    acNo,
                    getBalanceFromFile(acNo, acType.toLowerCase()),
                    acType.toLowerCase(),
                    getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType)
            );
            if (acType.toLowerCase().equals("savings")) {
                if ((customer.getSavingsBalance() - amt) >= 0) {
                    customer.setSavingsBalance(customer.getSavingsBalance() - amt);
                    //System.out.println(customer.getSavingsBalance());
                    logTransaction(getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType), LocalDateTime.now(), "withdrawal", amt, customer.getSavingsBalance());
                } else {
                    System.out.println("insufficient balance");
                }

            } else {
                if ((customer.getCurrentBalance() - amt) >= -1 * CurrentAccount.overdrawLimit) {
                    customer.setCurrentBalance(customer.getCurrentBalance() - amt);
                    logTransaction(getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType), LocalDateTime.now(), "withdrawal", amt, customer.getCurrentBalance());
                } else {
                    System.out.println("the amount is over the overdraw limit");
                }
            }

        } else {
            System.out.println("the customer account does not exist, you should create the customer first or insert the correct acc no");
        }

    }

    protected void showCustomerList() {
        //read customers.txt file
        try {
            FileReader reader = new FileReader(getWorkingDirectory() + "/customers.txt");
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

    }

    protected String generateAccountNumber(String firstName, String lastName, String accountType) {
        //The output you are expecting is just the offset of a upper case letter with respect to 'A'. So just subtract the Unicode value of 'A' from the unicode value of the letter whose offset is needed.
        return super.generateAccountNumber(firstName, lastName) + "-" + accountType.toLowerCase() + ".txt";
    }

    protected void logTransaction(String fileName, LocalDateTime date, String action, double amt, double balance) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(date + "\t" + action + "\t" + amt + "\t" + balance);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected double getBalanceFromFile(String accNo, String accType) {
        //read file
        double balance = 0.00;
        try {
            FileReader reader = new FileReader(getWorkingDirectory() + "/" + getTransactionFileName(accNo, accType));
            BufferedReader bufferedReader = new BufferedReader(reader);

            String fileContent = " ";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                fileContent += line;
            }
            //extract balance
            fileContent = fileContent.replaceAll("null", "  ").trim();
            String[] words = fileContent.split("\\s+");

            balance = Double.parseDouble(words[words.length - 1]);
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //return balance
        return balance;
    }

    public String getTransactionFileName(String accNo, String accType) {
        return accNo + "-" + accType.toLowerCase() + ".txt";
    }
}
