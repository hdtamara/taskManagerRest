package com.taskmanagerrest.taskmanager.exception;

public class UserNotEnabledException extends Exception{
    public UserNotEnabledException(String message){
        super(message);
    }
}
