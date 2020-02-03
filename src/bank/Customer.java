/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 18630
 */
public class Customer { 
    double currentBalance;
    double savingsBalance;
    String accountType;
    String accountNumber;
    String transactionFileName;
    public Customer(String accountNumber, double balance, String accountType, String transactionFileName){
      this.accountNumber=accountNumber;
      if(accountType.equals("savings")){
          this.accountType= accountType;
          setSavingsBalance(balance);
      }
      else if(accountType.equals("current")){
        this.accountType= accountType;
          setCurrentBalance(balance);
      }
      else{
          try {
              throw new Exception("the accountType is not spelled correctly try 'savings' || 'current' ");
          } catch (Exception e) {
             e.printStackTrace();
          }
    }
      this.transactionFileName=transactionFileName;
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
    
}
