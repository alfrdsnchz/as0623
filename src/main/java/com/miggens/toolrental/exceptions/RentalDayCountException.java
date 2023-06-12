package com.miggens.toolrental.exceptions;

import java.lang.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST )
public class RentalDayCountException extends Exception {
    
    public RentalDayCountException(String msg) {
        super(msg);
    }

    @Override
    public String getMessage() {
        return "RentalDayCountException : " + super.getMessage(); 
    }
}
