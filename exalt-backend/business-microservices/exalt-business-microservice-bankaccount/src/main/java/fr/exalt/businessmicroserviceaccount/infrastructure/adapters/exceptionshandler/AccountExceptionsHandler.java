package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.exceptionshandler;

import fr.exalt.businessmicroserviceaccount.domain.exceptions.*;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.ApiErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.Instant;

@ControllerAdvice
public class AccountExceptionsHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiErrorDto> handleBusinessExceptions(Exception exception) {
        ApiErrorDto apiErrorDto1 = ApiErrorDto.builder()
                .errorCode(HttpStatus.PRECONDITION_FAILED.value())
                .errorType(HttpStatus.PRECONDITION_FAILED.name())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        ApiErrorDto apiErrorDto2 = ApiErrorDto.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .errorType(HttpStatus.BAD_REQUEST.name())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        ApiErrorDto apiErrorDto3 = ApiErrorDto.builder()
                .errorCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .errorType(HttpStatus.SERVICE_UNAVAILABLE.name())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        ApiErrorDto apiErrorDto4 = ApiErrorDto.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .errorType(HttpStatus.NOT_FOUND.name())
                .timestamp(Timestamp.from(Instant.now()))
                .build();


        switch (exception) {
            case BankAccountBalanceInvalidException e -> {
                apiErrorDto1.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto1, HttpStatus.PRECONDITION_FAILED);
            }
            case BankAccountFieldsInvalidException e -> {
                apiErrorDto2.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto2, HttpStatus.BAD_REQUEST);
            }
            case BankAccountTypeInvalidException e ->{
                apiErrorDto1.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto1, HttpStatus.PRECONDITION_FAILED);
            }
            case BankAccountStateInvalidException e ->{
                apiErrorDto1.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto1, HttpStatus.PRECONDITION_FAILED);
            }

           case RemoteCustomerApiUnreachableException e ->{
                apiErrorDto3.setMessage(e.getMessage());
               return new ResponseEntity<>(apiErrorDto3, HttpStatus.SERVICE_UNAVAILABLE);
           }
           case RemoteCustomerStateInvalidException e ->{
                apiErrorDto1.setMessage(e.getMessage());
               return new ResponseEntity<>(apiErrorDto1, HttpStatus.PRECONDITION_FAILED);
           }
            case BankAccountNotFoundException e ->{
                apiErrorDto4.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto4, HttpStatus.NOT_FOUND);
            }
            case BankAccountSameStateException e ->{
                apiErrorDto1.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto1, HttpStatus.PRECONDITION_FAILED);
            }

           case BankAccountTypeNotAcceptedException e ->{
               apiErrorDto1.setMessage(e.getMessage());
               return  new ResponseEntity<>(apiErrorDto1, HttpStatus.PRECONDITION_FAILED);
           }
            case BankAccountOverdraftInvalidException e ->{
                apiErrorDto1.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto1, HttpStatus.PRECONDITION_FAILED);
            }
            case BankAccountSuspendException e ->{
                apiErrorDto1.setMessage(e.getMessage());
               return new ResponseEntity<>(apiErrorDto1, HttpStatus.PRECONDITION_FAILED);
            }

            default ->{
                apiErrorDto1.setMessage(exception.getMessage());
                return new ResponseEntity<>(apiErrorDto1, HttpStatus.PRECONDITION_FAILED);
            }
        }
    }
}
