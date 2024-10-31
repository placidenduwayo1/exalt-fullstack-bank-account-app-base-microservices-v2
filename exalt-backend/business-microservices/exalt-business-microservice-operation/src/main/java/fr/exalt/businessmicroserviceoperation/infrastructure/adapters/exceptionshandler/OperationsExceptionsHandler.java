package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.exceptionshandler;

import fr.exalt.businessmicroserviceoperation.domain.exceptions.*;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.dtos.ApiErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.Instant;

@ControllerAdvice
public class OperationsExceptionsHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiErrorDto> handleBusinessExceptions(Exception exception){
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

        switch (exception) {
            case OperationRequestFieldsInvalidException e ->{
                apiErrorDto2.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto2, HttpStatus.BAD_REQUEST);
            }
            case OperationTypeInvalidException e -> {
                apiErrorDto1.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto1, HttpStatus.PRECONDITION_FAILED);
            }

            case RemoteBankAccountApiUnreachableException e -> {
                apiErrorDto3.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto3, HttpStatus.SERVICE_UNAVAILABLE);
            }
            case RemoteBankAccountBalanceException e -> {
                apiErrorDto1.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto1, HttpStatus.PRECONDITION_FAILED);
            }

            case RemoteBankAccountTypeInaccessibleFromOutsideException e -> {
                apiErrorDto1.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto1, HttpStatus.PRECONDITION_FAILED);
            }
            case RemoteCustomerStateInvalidException e -> {
                apiErrorDto1.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto1, HttpStatus.PRECONDITION_FAILED);
            }
            case RemoteCustomerApiUnreachableException e -> {
                apiErrorDto3.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto3, HttpStatus.SERVICE_UNAVAILABLE);
            }
            case RemoteAccountSuspendedException e -> {
                apiErrorDto1.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto1, HttpStatus.PRECONDITION_FAILED);
            }
            default -> {
                apiErrorDto1.setMessage(exception.getMessage());
                return new ResponseEntity<>(apiErrorDto1, HttpStatus.PRECONDITION_FAILED);
            }
        }
    }
}
