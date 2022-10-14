package com.taskmanagerrest.taskmanager.exception;

public class TaskNotFoundException extends Exception {

    public TaskNotFoundException(String message){
        super(message);
    }
}
