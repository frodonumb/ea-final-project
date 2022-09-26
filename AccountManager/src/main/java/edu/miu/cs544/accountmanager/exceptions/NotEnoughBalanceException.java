package edu.miu.cs544.accountmanager.exceptions;

public class NotEnoughBalanceException extends Exception {

    public NotEnoughBalanceException(String message) {
        super(message);
    }
}
