/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            }
            else{
            //write boilerplate
            FileWriter writer = new FileWriter(workingDirectory, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
 
            bufferedWriter.write("Date\tAction\tAMT\tBalance");
            bufferedWriter.newLine();
            bufferedWriter.close();
            }
            //create current transaction account
            workingDirectory = new File(".").getCanonicalPath() + "/src/bank/" + generateAccountNumber(firstName, lastName, "current");
            file = new File(workingDirectory);
            boolean createdCurrent = file.createNewFile();
            if (!createdCurrent) {
                System.out.println("the current account already exists");
            }
            else{
            //write boilerplate
             FileWriter writer = new FileWriter(workingDirectory, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
 
            bufferedWriter.write("Date\tAction\tAMT\tBalance");
            bufferedWriter.newLine();
//            bufferedWriter.write("Date\tAction\tAMT\tBalance");
//            bufferedWriter.newLine();
            bufferedWriter.close();
            }
            if(!createdSavings && !createdCurrent){return;}
            else{
            //add to customer database file
            FileWriter writer = new FileWriter(getWorkingDirectory()+"/customers.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
 
            bufferedWriter.write(firstName +"\t"+ lastName+ "\t" +generateAccountNumber(firstName, lastName)+ "\t" +"Savings"+"0.00");
            bufferedWriter.newLine();
            bufferedWriter.write(firstName +"\t"+ lastName+ "\t" +generateAccountNumber(firstName, lastName)+ "\t" +"Current"+"0.00");
            bufferedWriter.newLine();
            bufferedWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void deleteCustomerAccount() {

    }

    private void addToCustomerAccount() {

    }

    private void showCustomerList() {

    }

    protected String generateAccountNumber(String firstName, String lastName, String accountType) {
        //The output you are expecting is just the offset of a upper case letter with respect to 'A'. So just subtract the Unicode value of 'A' from the unicode value of the letter whose offset is needed.
        return super.generateAccountNumber(firstName, lastName)+ "-" + accountType + ".txt";
    }
}
