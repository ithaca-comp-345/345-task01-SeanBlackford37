package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if(!isAmountValid(startingBalance)){
            throw new IllegalArgumentException("Amount: " + startingBalance + " is invalid, cannot create account");
        }
        else if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }
     /**
     * @Post adds the balance by amount for the current account, throws an error if the amount is invalid.
     */
    public void deposit (double amount){
        if(!isAmountValid(amount)){
            throw new IllegalArgumentException("Amount: " + amount + " is invalid, cannot deposit");
        }
        balance += amount;

    }
     /**
     * @Post reduces the balance by amount for the current account, takes in the second account.
     */
    public void transfer (double amount, BankAccount accountTransferTo) throws InsufficientFundsException{
        if(!isAmountValid(amount)){
            throw new IllegalArgumentException("Amount: " + amount + " is invalid, cannot deposit");
        }
        else if(amount > balance){
            throw new InsufficientFundsException("Not enough money to transfer");
        }
        accountTransferTo.balance += amount;
        balance -= amount;
        
    }
     /**
     * @Post return true or false by checking if the amount is valid
     */
    public static boolean isAmountValid(double balance){
        String s = "" + balance;
        String[] result = s.split("\\."); //Splits on the decimal and puts each side into result[1] (left half) and result[2] (right half)
        if(balance >=0 && result[1].length() <= 2){
          return true;
        }
       return false;
    }
    public double getBalance(){
        if (balance < 0){
            balance = 0;
        }
        int temp = (int)(balance * 100);
        balance = ((double)temp) /100;
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if(!isAmountValid(amount)){
            throw new IllegalArgumentException("Amount: " + amount + " is invalid, cannnot withdraw");
        }
        else if(amount > 0){
            int temp = (int)(amount * 100);
            amount = ((double)temp) /100;
            if (amount <= balance){
                balance -= amount;
            }
            else {
                throw new InsufficientFundsException("Not enough money");
            }
        }
        
    }

     /**
     * @Post return true or false by checking if the email is valid
     */
    public static boolean isEmailValid(String email){
        if ((email.indexOf('@') == -1) || (email.indexOf('@') == 0)){
            return false;
        }
        else if((email.indexOf('#')!= -1) ||(email.indexOf('-')!= -1) ){
            return false;
        }
        else if(email.indexOf('.')== 0 || (email.contains(".."))){
            return false;
        }
        else if(!(email.endsWith(".com"))){
            return false;
        }
        else if(email.charAt(email.indexOf('@')-1) == '#'||email.charAt(email.indexOf('@')-1) == '-' || email.charAt(email.indexOf('@')-1) == '.'  ){
            return false;
        }
        else {
            return true;
        }
    }
}
