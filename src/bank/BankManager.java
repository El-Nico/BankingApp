package bank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * @author el-nico
 */
public class BankManager implements DailyOperations {

    private String workingDirectory;

    public BankManager() {
        //create customers.txt and set the working directory where generated files will  be stored
        setWorkingDirectory();
        initializeCustomerDatabase();
    }

    @Override
    public void run() {
        boolean mainExit = false;
        do {
            Scanner inOptions = new Scanner(System.in);
            // welcome user and check if user is an employee or customer
            System.out.println(
                    "Welcome to Dorset bank please identify yourself as a customer or an employee\n"
                    + "press 'e' for employee\n"
                    + "press 'c' for customer\n"
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

    //log the customer in and process their actions
    private void runCustomerOperations() {
        //check customer details
        boolean customerLoginExit = false;
        do {
            Scanner inOptions = new Scanner(System.in);
            System.out.println(
                    "Welcome our esteemed customer to proceed you must login,"
                    + " you may enter exit to exit the login menu at any point\n"
                    + "please enter your first name"
            );
            String fName = inOptions.nextLine().toLowerCase();
            if (fName.equals("exit")) {
                System.out.println("you exited the login menu");
                break;
            }
            System.out.println("enter your last name");
            String lName = inOptions.nextLine().toLowerCase();
            if (lName.equals("exit")) {
                System.out.println("you exited the login menu");
                break;
            }
            System.out.println("enter your account number");
            String acNo = inOptions.nextLine().toLowerCase();
            if (acNo.equals("exit")) {
                System.out.println("you exited the login menu");
                break;
            }
            System.out.println("and finally the pin\n");
            String pin = inOptions.nextLine().toLowerCase();
            if (pin.equals("exit")) {
                System.out.println("you exited the login menu");
                break;
            }
            //verify details
            if (isCustomer(fName, lName, acNo, pin)) {
                //initialize customer object
                Customer thisCustomer = new Customer(acNo, pin);
                //show customer actions
                System.out.println("you have successfully logged in");
                boolean customerActionsExit = false;
                do {
                    System.out.println(
                            "Welcome our most esteemed customer, you may\n"
                            + "press 1 to retrieve your transaction history\n"
                            + "press 2 to deposit to your savings account\n"
                            + "press 3 to deposit to your current account\n"
                            + "press 4 to withdraw from your savings account\n"
                            + "press 5 to withdraw from your current account\n"
                            + "enter 'exit' to exit the actions menu"
                    );
                    String customerActionsMenuChoice = inOptions.nextLine();
                    switch (customerActionsMenuChoice) {
                        //polymorphism is used to specify that a manager of type employee is needed to attend the customers needs
                        case "1":
                            BankManager rt = new BankEmployee();
                            thisCustomer.requestTransactionHistory((BankEmployee) rt);
                            System.out.println("transaction history retrieved");
                            break;
                        case "2":
                            BankManager ds = new BankEmployee();
                            thisCustomer.depositSavings((BankEmployee) ds);
                            System.out.println("savings deposited");
                            break;
                        case "3":
                            BankManager dc = new BankEmployee();
                            thisCustomer.depositCurrent((BankEmployee) dc);
                            System.out.println("current deposited");
                            break;
                        case "4":
                            BankManager ws = new BankEmployee();
                            thisCustomer.withdrawSavings((BankEmployee) ws);
                            System.out.println("savings withdrawn");
                            break;
                        case "5":
                            BankManager wc = new BankEmployee();
                            thisCustomer.withdrawCurrent((BankEmployee) wc);
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

            } else {
                System.out.println("the login details were incorrect");
                customerLoginExit = true;
            }
        } while (!customerLoginExit);

    }

    //log the employee in and process their duties
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
                //show employee duties
                System.out.println("you have logged in successfully");
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
                            System.out.println("customer delete duty completed");
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
                System.out.println("the login details were incorrect");
            }
        } while (!employeeLoginExit);
    }

    /////////////////////////////////////////////////////initialization methods//////////////////////////////////////////////////////////////////////////////
    //initialize the customers.txt file
    private void initializeCustomerDatabase() {
        try {
            String workingDirectory = getWorkingDirectory() + "/customers.txt";
            File file = new File(workingDirectory);
            boolean createdCustomerDatabaseFile = file.createNewFile();
            if (createdCustomerDatabaseFile) {
                FileWriter writer = new FileWriter(workingDirectory, true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);

                bufferedWriter.write("FName\tLName\tAccountNo\tPin\tACType");
                bufferedWriter.newLine();
                bufferedWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //set the working directory where generated files will be stored
    private void setWorkingDirectory() {
        try {
            workingDirectory = new File(".").getCanonicalPath() + "/src/bank";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //get the working directory
    public String getWorkingDirectory() {
        return workingDirectory;
    }

////////////////////////////////////////////////////////////Methods carrying out daily workplace tasks//////////////////////////////////////////////////////////
    //create a new customer
    protected void createCustomer() {
        ///get the customer firstname, lastname and email
        Scanner inDetails = new Scanner(System.in);
        System.out.println("thankyou for choosing to create and account with us\n"
                + "please insert first name");
        String firstName = inDetails.nextLine();
        System.out.println("now insert last name");
        String lastName = inDetails.nextLine();
        System.out.println("and finally email");
        String email = inDetails.nextLine();
        System.out.println("we will now create your account");

        try {
            //create savings transaction account
            String savingsTransactionFile = getWorkingDirectory() + "/" + generateAccountNumber(firstName, lastName, "savings");
            File file = new File(savingsTransactionFile);
            boolean createdSavings = file.createNewFile();
            if (!createdSavings) {
                System.out.println("the savings account already exists");
            } else {
                //write boilerplate
                FileWriter writer = new FileWriter(savingsTransactionFile, true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);

                bufferedWriter.write("Date\tAction\tAMT\tBalance");
                bufferedWriter.newLine();
                bufferedWriter.close();
                //log their first transaction,  account created
                logTransaction(getWorkingDirectory() + "/" + getTransactionFileName(generateAccountNumber(firstName, lastName), "savings"), LocalDateTime.now(), "created", 0.00, 0.00);

            }
            //create current transaction account
            String currentTransactionFile = getWorkingDirectory() + "/" + generateAccountNumber(firstName, lastName, "current");
            file = new File(currentTransactionFile);
            boolean createdCurrent = file.createNewFile();
            if (!createdCurrent) {
                System.out.println("the current account already exists");
            } else {
                //write boilerplate
                FileWriter writer = new FileWriter(currentTransactionFile, true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);

                bufferedWriter.write("Date\tAction\tAMT\tBalance");
                bufferedWriter.newLine();
                bufferedWriter.close();
                //log the first transaction account created
                logTransaction(getWorkingDirectory() + "/" + getTransactionFileName(generateAccountNumber(firstName, lastName), "current"), LocalDateTime.now(), "created", 0.00, 0.00);
            }
            if (!createdSavings && !createdCurrent) {
                //the customer already exist
                return;
            } else {
                //add to customers.txt file
                FileWriter writer = new FileWriter(getWorkingDirectory() + "/customers.txt", true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);

                bufferedWriter.write(firstName + "\t" + lastName + "\t" + generateAccountNumber(firstName, lastName) + "\t" + generatePin(generateAccountNumber(firstName, lastName)) + "\t" + "savings");
                bufferedWriter.newLine();
                bufferedWriter.write(firstName + "\t" + lastName + "\t" + generateAccountNumber(firstName, lastName) + "\t" + generatePin(generateAccountNumber(firstName, lastName)) + "\t" + "current");
                bufferedWriter.newLine();
                bufferedWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //delete an existing customer
    protected void deleteCustomerAccount() {
        //get details and differentisate by type
        //get the account number and type
        Scanner inDetails = new Scanner(System.in);
        System.out.println("please insert the customer account number");
        String acNo = inDetails.nextLine().toLowerCase();
        System.out.println("please insert the account type");
        String acType = inDetails.nextLine().toLowerCase();

        //check that customer exists first
        if (new File(getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType)).exists()) {

            //initialize customer
            Customer customer = new Customer(
                    acNo,
                    getBalanceFromFile(acNo, acType.toLowerCase()),
                    acType.toLowerCase(),
                    getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType)
            );
            //check savings balance is zero first then delete savings
            if (acType.equals("savings") && customer.getSavingsBalance() == 0.0) {
                new File(getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType)).delete();
                //read customers.txt copy all lines except customer to temp file rename temp file to customers.txt and delete previous customers.txt,
                try {
                    File customersFile = new File(getWorkingDirectory() + "/customers.txt");
                    File tempFile = new File(getWorkingDirectory() + "/customers-temp.txt");

                    BufferedReader reader = new BufferedReader(new FileReader(customersFile));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                    String currentLine;

                    while ((currentLine = reader.readLine()) != null) {
                        if (null != currentLine && !currentLine.contains(acNo + "\t" + generatePin(acNo) + "\t" + "savings")) {
                            writer.write(currentLine + System.getProperty("line.separator"));
                        }
                    }
                    writer.close();
                    reader.close();
                    customersFile.delete();
                    tempFile.renameTo(customersFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } //balance is greater than zero
            else if (acType.equals("savings") && customer.getSavingsBalance() > 0.0) {
                System.out.println("the customer cannot be deleted because their balance is greater than zero");
            } //check current balance is -1500(overdraw limit) then delete current
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
                        if (null != currentLine && !currentLine.contains(acNo + "\t" + generatePin(acNo) + "\t" + "current")) {
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

            } //balance is greater than overdraw limit
            else if (acType.toLowerCase().equals("current") && customer.getSavingsBalance() > -1 * CurrentAccount.overdrawLimit) {
                System.out.println("the customer cannot be deleted because their balance is greater than the overdraw limit");
            }

        } else if (acNo.equals("jd-7-10-4")) {
            System.out.println("this customer is a legend and cannot be deleted");
        } else {
            System.out.println("the customer account does not exist, you should create the customer first or insert the correct acount number and type");
        }
    }

    //add to a customers account
    protected void addToCustomerAccount() {
        //get the account number and type
        Scanner inDetails = new Scanner(System.in);
        System.out.println("please insert the customer account number");
        String acNo = inDetails.nextLine().toLowerCase();
        System.out.println("please insert the account type");
        String acType = inDetails.nextLine().toLowerCase();

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
            if (acType.equals("savings")) {
                customer.setSavingsBalance(customer.getSavingsBalance() + amt);
                logTransaction(getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType), LocalDateTime.now(), "lodgement", amt, customer.getSavingsBalance());
            } else {
                customer.setCurrentBalance(customer.getCurrentBalance() + amt);
                logTransaction(getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType), LocalDateTime.now(), "lodgement", amt, customer.getCurrentBalance());
            }

        } else {
            System.out.println("the customer account does not exist, you should create the customer first or insert the correct acc no");
        }

    }

    //withdraw from a customers account TandC's apply
    protected void withdrawFromCustomerAccount() {
        //get the account number and type
        Scanner inDetails = new Scanner(System.in);
        System.out.println("please insert the customer account number");
        String acNo = inDetails.nextLine().toLowerCase();
        System.out.println("please insert the account type");
        String acType = inDetails.nextLine().toLowerCase();

        //log the transaction and decrease the balance
        if (new File(getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType)).exists()) {
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
            Customer customer = new Customer(
                    acNo,
                    getBalanceFromFile(acNo, acType.toLowerCase()),
                    acType.toLowerCase(),
                    getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType)
            );
            if (acType.equals("savings")) {
                //check for negative balance
                if ((customer.getSavingsBalance() - amt) >= 0) {
                    customer.setSavingsBalance(customer.getSavingsBalance() - amt);
                    //log the withdrawal
                    logTransaction(getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType), LocalDateTime.now(), "withdrawal", amt, customer.getSavingsBalance());
                } else {
                    System.out.println("insufficient balance");
                }

            } else {
                //check for negative current balance ie over overdawLimit
                if ((customer.getCurrentBalance() - amt) >= -1 * CurrentAccount.overdrawLimit) {
                    customer.setCurrentBalance(customer.getCurrentBalance() - amt);
                    //log the withdrawal
                    logTransaction(getWorkingDirectory() + "/" + getTransactionFileName(acNo, acType), LocalDateTime.now(), "withdrawal", amt, customer.getCurrentBalance());
                } else {
                    System.out.println("the amount is over the overdraw limit");
                }
            }

        } else {
            System.out.println("the customer account does not exist, you should create the customer first or insert the correct acc no");
        }

    }

    //print all customers
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
////////////////////////////////////////////////////////////////Helper Methods/////////////////////////////////////////////////////////////////////////////////

    //returns a transaction filename overrides generateAccountNumber to append the accountnumber and type
    protected String generateAccountNumber(String firstName, String lastName, String accountType) {
        return generateAccountNumber(firstName, lastName) + "-" + accountType.toLowerCase() + ".txt";
    }

    //log transaction to customer transaction file
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

    //get the customers account balance from the transaction file
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

    //return the transaction file name of a customer
    public String getTransactionFileName(String accNo, String accType) {
        return accNo + "-" + accType.toLowerCase() + ".txt";
    }

    //generate account number from customer name
    protected String generateAccountNumber(String firstName, String lastName) {
        firstName = firstName.toLowerCase();
        lastName = lastName.toLowerCase();
        return String.valueOf(firstName.charAt(0))
                + String.valueOf(lastName.charAt(0))
                + "-"
                + String.valueOf(firstName.concat(lastName).length())
                + "-"
                + String.valueOf((firstName.toLowerCase().charAt(0) - 'a') + 1)
                + "-"
                + String.valueOf((lastName.toLowerCase().charAt(0) - 'a') + 1);
    }

    //generate the customer pin
    protected String generatePin(String accountNo) {
        int substringFrom = 0;
        String pin = "";
        for (int i = 0, hyphenIndex = 2; i < accountNo.length(); i++) {
            if (accountNo.charAt(i) == '-') {
                hyphenIndex--;
            }
            if (hyphenIndex == 0) {
                pin = accountNo.substring(++i);
                break;
            }
        }

        return pin;
    }

    //verify there is a line entry with given details in customers.txt file
    protected boolean isCustomer(String fName, String lName, String acNo, String pin) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(getWorkingDirectory() + "/customers.txt"));

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (null != currentLine
                        && currentLine.contains(fName)
                        && currentLine.contains(lName)
                        && currentLine.contains(acNo)
                        && currentLine.contains(pin)) {
                    return true;
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
