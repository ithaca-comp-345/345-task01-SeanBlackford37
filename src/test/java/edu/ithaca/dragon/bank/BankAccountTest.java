package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance()); //Correct Balance displays 
        
        
         //Balance is negative (should set it to zero)
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", -1));
        

       
        BankAccount bankAccountTwo = new BankAccount("a@b.com", 10.50);  //Balance with a Double
        assertEquals(10.50, bankAccountTwo.getBalance());

       
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", 10.213));
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance()); //Valid Amount withdrawn
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300)); //Over drawing
        
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-300));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(300.33333)); 
        assertThrows(IllegalArgumentException.class, () ->  bankAccount.withdraw(-50)); 
       
        assertEquals(100, bankAccount.getBalance()); //Withdraw a negative amount
        bankAccount.withdraw(0);
        assertEquals(100, bankAccount.getBalance()); //Withdraw nothing

        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(.0005));
        
        assertEquals(100, bankAccount.getBalance()); //Invalid Amount to widthdraw

        
        bankAccount.withdraw(0.10);
        assertEquals(99.90, bankAccount.getBalance()); //Withdraw an amount less than $1

        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(0.555));
        
        assertEquals(99.9, bankAccount.getBalance()); //Withdraw a Double with too many places


    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com")); //valid email
        assertFalse( BankAccount.isEmailValid("")); //Checks if  empty 
        //Placement of symbol class
        assertFalse( BankAccount.isEmailValid("abc-@mail.com")); //Checks symbol before @       
        //Repition of symbol class
        assertFalse( BankAccount.isEmailValid("abc..def@mail.com")); //Symbol followed by symbol 
        //Placement of symbol back edge case
        assertFalse( BankAccount.isEmailValid(".abc@mail.com")); //Starting off with a symbol 
        assertFalse( BankAccount.isEmailValid("#abc@mail.com")); //Starting off with a symbol 
        //Symbol not valid class
        assertFalse( BankAccount.isEmailValid("abc#def@mail.com")); //Having an illegal symbol in the middle 
        //Placement of symbol front edge case
        assertFalse( BankAccount.isEmailValid("@.com")); //Invalid
        //Ending email invalid class
        assertFalse( BankAccount.isEmailValid("abc.def@mail.c")); //Invalid
        //Symbol not valid  class
        assertFalse( BankAccount.isEmailValid("abc.def@mail#archive.com")); //Invalid can't have hastag at all
        //Ending email invalid class
        assertFalse( BankAccount.isEmailValid("abc.def@mail")); //No .com 
        //Repition of symbol class
        assertFalse( BankAccount.isEmailValid("abc.def@mail..com")); //too many periods at the end

        
    }
    @Test
    void isAmountValidTest(){
        //Equivalence class
        assertTrue(BankAccount.isAmountValid(1000)); //Base case 
        //Equivalence class
        assertTrue(BankAccount.isAmountValid(1000.50)); //Doubles
        //Equivalence class
        assertFalse(BankAccount.isAmountValid(1000.3333)); //Not valid too many decimals
        //Equivalence class
        assertFalse(BankAccount.isAmountValid(-1000)); //Not valid negative number
        //Equivalence class
        assertFalse(BankAccount.isAmountValid(0.33333)); //Not valid too many decimal places
        //Equivalence class
        assertTrue(BankAccount.isAmountValid(0)); //Bank account has nothing in it
        //No border cases
    }
    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));

        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", -100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", -100.3333));

    }

    @Test
    void depositTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        //border case
        assertThrows(InsufficientFundsException.class, () -> bankAccount.deposit(-200)); //Trying to deposit a negative amount
        //equivalence class
        bankAccount.deposit(0);
        assertEquals(200, bankAccount.getBalance()); //deposit nothing
        //border case
        assertThrows(InsufficientFundsException.class, () -> bankAccount.deposit(200.9999)); //Deposit with too many decimals
        //equivalence class
        bankAccount.deposit(100.50);
        assertEquals(300.50, bankAccount.getBalance()); //deposit double
        //equivalence class
        bankAccount.deposit(100);
        assertEquals(400.50, bankAccount.getBalance()); //deposit int
    }
    @Test
    void transferTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        BankAccount bankAccountTwo = new BankAccount("seanb@gmai.com", 20);
        //Base case
        BankAccount.transfer(bankAccount, 150, bankAccountTwo); 
        //equivalence class
        assertEquals(170, bankAccountTwo.getBalance()); //Tranfer int amount to second account
        assertEquals(50, bankAccount.getBalance()); //Remaining balance of first account
        //equivalence class
        assertThrows(InsufficientFundsException.class, () -> BankAccount.transfer(bankAccount, 150, bankAccountTwo)); //Not enough money to transfer
        //border case
        assertThrows(InsufficientFundsException.class, () -> BankAccount.transfer(bankAccount, 150.5555, bankAccountTwo)); //too many decimals
        //border case
        assertThrows(InsufficientFundsException.class, () -> BankAccount.transfer(bankAccount, -150, bankAccountTwo)); //Can't transfer negative amount
        //equivalence class
        BankAccount.transfer(bankAccount, 25.01, bankAccountTwo);
        assertEquals(195.01, bankAccountTwo.getBalance()); //Tranfer double amount to second account
        //equivalence class
        BankAccount.transfer(bankAccountTwo, 100, bankAccount); //Tranfer money back into the second account 
        assertEquals(95.01, bankAccountTwo.getBalance()); //Check remaining balance
        assertEquals(150, bankAccount.getBalance()); //Check remaining balance
    }

    

}