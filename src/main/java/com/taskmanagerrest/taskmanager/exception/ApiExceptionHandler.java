package com.taskmanagerrest.taskmanager.exception;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.CredentialException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.MalformedJwtException;


@RestControllerAdvice
public class ApiExceptionHandler {

  
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex){
        Map<String,String> errorMap = new HashMap<>();        
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap; 
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({MalformedJwtException.class})
    public Map<String,String>  JwtnotFound(MalformedJwtException ex){      
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("ErrorMessage", ex.getMessage());
      
        return errorMap; 
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UserNotFoundException.class, TaskNotFoundException.class})
    public Map<String, String> handleBusinessException(Exception ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    } 

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({CredentialException.class,UserNotEnabledException.class})
    public Map<String,String> handleWrongCredentials(Exception ex){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({UserAlreadyExistsException.class})
    public Map<String,String> handleUserExist(Exception ex){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }



}
    

