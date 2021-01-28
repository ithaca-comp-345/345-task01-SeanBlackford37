package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com")); //valid email
        assertFalse( BankAccount.isEmailValid("")); //Checks if  empty
        assertFalse( BankAccount.isEmailValid("abc-@mail.com")); //Checks symbol before @
        assertFalse( BankAccount.isEmailValid("abc..def@mail.com")); //Symbol followed by symbol 
        assertFalse( BankAccount.isEmailValid(".abc@mail.com")); //Starting off with a symbol 
        assertFalse( BankAccount.isEmailValid("#abc@mail.com")); //Starting off with a symbol 
        assertFalse( BankAccount.isEmailValid("abc#def@mail.com")); //Having an illegal symbol in the middle 
        assertFalse( BankAccount.isEmailValid("@.com")); //Invalid
        assertFalse( BankAccount.isEmailValid("abc.def@mail.c")); //Invalid
        assertFalse( BankAccount.isEmailValid("abc.def@mail#archive.com")); //Invalid can't have hastag at all
        assertFalse( BankAccount.isEmailValid("abc.def@mail")); //No .com 
        assertFalse( BankAccount.isEmailValid("abc.def@mail..com")); //too many periods at the end

        
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

    

}