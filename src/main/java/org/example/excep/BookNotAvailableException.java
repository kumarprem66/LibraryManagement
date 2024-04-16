package org.example.excep;

public class BookNotAvailableException extends Exception{

    public BookNotAvailableException(String message){
        super(message);
    }
}
