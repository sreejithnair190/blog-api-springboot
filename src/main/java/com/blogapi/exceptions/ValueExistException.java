package com.blogapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValueExistException extends RuntimeException{
    public ValueExistException(String message){
        super(message);
    }
}
