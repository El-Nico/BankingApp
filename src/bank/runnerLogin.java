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
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author el-nico
 */
public class runnerLogin implements DailyOperations {

    private String workingDirectory;

    public runnerLogin() {
        initializeCustomerDatabase();
        setWorkingDirectory();
    }

    @Override
    public void run() {
        boolean mainExit = false;
        do {
            Scanner inOptions = new Scanner(System.in);
            // welcome user and check if user is an employee or customer
            System.out.println(
                    "Welcome our to dorset bank please identify yourself as a customer or employee\n"
                    + "press 'e' for employee\n"
                    + "press 'c' if your\n"
                    + "enter 'exit' to exit the program"
            );
            // accept and process the users choice
            String userType = inOptions.nextLine().toLowerCase();
            switch (userType) {
                case "c":
                    runCustomerOperations();
                    break;
                case "e":
                    runEmployeeOperations();
                    break;
                case "exit":
                    System.out.println("goodbye thankyou for banking with us");
                    mainExit = true;
                    break;
                default:
                    System.out.println("wrong input, please read the menu and try again");
                    break;
            }
        } while (!mainExit);
    }

    private void runCustomerOperations() {
        //check employee login
        boolean customerLoginExit = false;
        do {
            Scanner inOptions = new Scanner(System.in);
            System.out.println(
                    "Welcome our esteemed customer to proceed you must login, you may\n"
                    + "enter the fname\n"
                    + "enter 'exit' to exit the program"
            );
            String fName = inOptions.nextLine();
            System.out.println(
                    "Welcome our esteemed customer to proceed you must login, you may\n"
                    + "enter the lname\n"
                    + "enter 'exit' to exit the program"
            );
            String lName = inOptions.nextLine();
            System.out.println(
                    "Welcome our esteemed customer to proceed you must login, you may\n"
                    + "enter the password\n"
                    + "enter 'exit' to exit the program"
            );
            String acNo = inOptions.nextLine();
            System.out.println(
                    "Welcome our esteemed customer to proceed you must login, you may\n"
                    + "enter the password\n"
                    + "enter 'exit' to exit the program"
            );
            String pin = inOptions.nextLine();
            //verify pin
            if (isCustomer(fName, lName, acNo, pin)) {
                //initialize custome
                Customer thisCustomer = new Customer(acNo, pin);
                //show customer actions
                System.out.println("customer chose correct pass");
                boolean customerActionsExit = false;
                do {
                    System.out.println(
                            "Welcome our most esteemed customer, you may\n"
                            + "press 1 to retrieve transaction history\n"
                            + "press 2 to deposit to your savings account\n"
                            + "press 3 to deposit to your current account\n"
                            + "press 4 to withdraw from your savings account\n"
                            + "press 5 to withdraw from your current account\n"
                            + "enter 'exit' to exit the actions menu"
                    );
                    String customerActionsMenuChoice = inOptions.nextLine();
                    switch (customerActionsMenuChoice) {
                        case "1":
                            thisCustomer.requestTransactionHistory(new BankEmployee());
                            System.out.println("transaction history retrieved");
                            break;
                        case "2":
                            thisCustomer.depositSavings(new BankEmployee());
                            System.out.println("savings deposited");
                            break;
                        case "3":
                            thisCustomer.depositCurrent(new BankEmployee());
                            System.out.println("current deposited");
                            break;
                        case "4":
                            thisCustomer.withdrawSavings(new BankEmployee());
                            System.out.println("savings withdrawn");
                            break;
                        case "5":
                            thisCustomer.withdrawCurrent(new BankEmployee());
                            System.out.println("current withdrawn");
                            break;
                        case "exit":
                            System.out.println("customer exited actions");
                            customerActionsExit = true;
                            break;
                        default:
                            System.out.println("wrong input, read the menu and try again");
                            break;

                    }
                } while (!customerActionsExit);

            } else if (fName.equals("exit") || lName.equals("exit") || acNo.equals("exit") || pin.equals("exit")) {
                System.out.println("customer exited login menu");
                customerLoginExit = false;
            } else {
                System.out.println("wrong input, read the menu and try again");
            }
        } while (!customerLoginExit);

    }

    private void runEmployeeOperations() {
        //check employee login
        boolean employeeLoginExit = false;
        do {
            Scanner inOptions = new Scanner(System.in);
            System.out.println(
                    "Welcome our esteemed employee to proceed you must login, you may\n"
                    + "enter the password\n"
                    + "enter 'exit' to exit the program"
            );
            String employeePass = inOptions.nextLine();
            if (employeePass.equals("A1234")) {
                //run options
                System.out.println("user chose correct pass");
                boolean employeeDutiesExit = false;
                do {
                    System.out.println(
                            "Welcome our most esteemed employee, you may\n"
                            + "press 1 to create a new customer \n"
                            + "press 2 to delete customer\n"
                            + "press 3 to showlist of customers\n"
                            + "press 4 to add money to customer account\n"
                            + "press 5 to withdraw money from customer customer"
                            + "press exit to exit  the duties menu"
                    );
                    String employeeAction = inOptions.nextLine();
                    switch (employeeAction) {
                        case "1":
                            new BankEmployee().createCustomer();
                            System.out.println("customer created");
                            break;
                        case "2":
                            new BankEmployee().deleteCustomerAccount();
                            System.out.println("customer deleted");
                            break;
                        case "3":
                            new BankEmployee().showCustomerList();
                            System.out.println("customer list shown");
                            break;
                        case "4":
                            new BankEmployee().addToCustomerAccount();
                            System.out.println("money added to customer account");
                            break;
                        case "5":
                            new BankEmployee().withdrawFromCustomerAccount();
                            System.out.println("money withdrawn from customer account");
                            break;
                        case "exit":
                            System.out.println("employee exited duties");
                            employeeDutiesExit = true;
                            break;
                        default:
                            System.out.println("wrong input, read the menu and try again");
                            break;

                    }
                } while (!employeeDutiesExit);
            } else if (employeePass.equals("exit")) {
                System.out.println("you have chosen to exit the login process");
                employeeLoginExit = true;

            } else {
                System.out.println("wrong input, read the menu and try again");
            }
        } while (!employeeLoginExit);
    }

    private void initializeCustomerDatabase() {
        try {
            String workingDirectory = new File(".").getCanonicalPath() + "/src/bank/customers.txt";
            File file = new File(workingDirectory);
            boolean createdCustomerDatabaseFile = file.createNewFile();
            if (createdCustomerDatabaseFile) {
                FileWriter writer = new FileWriter(workingDirectory, true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);

                bufferedWriter.write("FName\tLName\tAccountNo\tACType");
                bufferedWriter.newLine();
                bufferedWriter.write("John\tDoe\t" + generateAccountNumber("John", "Doe") + "\tSavings");
                bufferedWriter.newLine();
                bufferedWriter.write("John\tDoe\t" + generateAccountNumber("John", "Doe") + "\tCurrent");
                bufferedWriter.newLine();
                bufferedWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String generateAccountNumber(String firstName, String lastName) {
        //The output you are expecting is just the offset of a upper case letter with respect to 'A'. So just subtract the Unicode value of 'A' from the unicode value of the letter whose offset is needed.
        System.out.println(String.valueOf(firstName.charAt(0))
                + String.valueOf(lastName.charAt(0))
                + "-"
                + String.valueOf(firstName.concat(lastName).length())
                + "-"
                + String.valueOf((firstName.toLowerCase().charAt(0) - 'a') + 1)
                + "-"
                + String.valueOf((lastName.toLowerCase().charAt(0) - 'a') + 1));
        return String.valueOf(firstName.charAt(0))
                + String.valueOf(lastName.charAt(0))
                + "-"
                + String.valueOf(firstName.concat(lastName).length())
                + "-"
                + String.valueOf((firstName.toLowerCase().charAt(0) - 'a') + 1)
                + "-"
                + String.valueOf((lastName.toLowerCase().charAt(0) - 'a') + 1);
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    private void setWorkingDirectory() {
        try {
            workingDirectory = new File(".").getCanonicalPath() + "/src/bank";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean isCustomer(String fName, String lName, String acNo, String pin) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(getWorkingDirectory() + "/customers.txt"));

            String currentLine;
            int lineIndex = 0;
            while ((currentLine = reader.readLine()) != null) {
                System.out.println(lineIndex);
                if (null != currentLine
                        && currentLine.contains(fName)
                        && currentLine.contains(lName)
                        && currentLine.contains(acNo)
                        && currentLine.contains(pin)) {
                    return true;
                }
                lineIndex++;
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//                    
        return false;
    }
}
