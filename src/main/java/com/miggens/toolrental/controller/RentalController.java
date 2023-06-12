package com.miggens.toolrental.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.miggens.toolrental.biz.RentalAgreement;
import com.miggens.toolrental.transaction.RentalTransaction;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/rentals")
@Validated
public class RentalController {

    @Autowired
    private RentalTransaction rentalTransaction; 

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("Atleast one request argument is invalid: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    //need exception for an empty rental agreement
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<String> handleException(ConstraintViolationException e) {
        return new ResponseEntity<>("Atleast one request argument is invalid: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/checkout/{date:\\d{1,2}-\\d{1,2}-\\d{4}}/item/{tool-code:\\w{4}}/days/{day-count:\\d{1,3}}/discount/{discount:\\d{1,3}}")
    public ResponseEntity<RentalAgreement> checkout(  @PathVariable("date") String date,
                                        @PathVariable("tool-code") String toolCode,
                                        @PathVariable("day-count") @Min(1) Integer dayCount,
                                        @PathVariable("discount") @Min(0) @Max(100) Integer discount
                                        ) throws Exception{

        Optional<RentalAgreement> rentalAgreement = this.rentalTransaction.runRransaction(date, toolCode, dayCount, discount);  

        if (rentalAgreement.isEmpty()) {
            //throw Exception!!!!
            throw new Exception("Data Service Error: Failed to create transaction.");
        }

        return new ResponseEntity<RentalAgreement>(rentalAgreement.get(), HttpStatusCode.valueOf(200));
    }
}
