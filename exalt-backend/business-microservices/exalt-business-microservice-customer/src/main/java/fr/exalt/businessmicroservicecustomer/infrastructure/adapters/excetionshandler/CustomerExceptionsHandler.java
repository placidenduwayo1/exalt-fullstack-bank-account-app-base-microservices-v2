package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.excetionshandler;

import fr.exalt.businessmicroservicecustomer.domain.exceptions.*;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.dtos.ApiErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.Instant;

@ControllerAdvice
public class CustomerExceptionsHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiErrorDto> handleBusinessException(Exception exception) {

        ApiErrorDto apiErrorDto1 = ApiErrorDto.builder()
                .errorCode(HttpStatus.FORBIDDEN.value())
                .errorType(HttpStatus.FORBIDDEN.name())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        ApiErrorDto apiErrorDto2 = ApiErrorDto.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .errorType(HttpStatus.BAD_REQUEST.name())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        ApiErrorDto apiErrorDto3 = ApiErrorDto.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .errorType(HttpStatus.NOT_FOUND.name())
                .timestamp(Timestamp.from(Instant.now()))
                .build();

        switch (exception) {
            case CustomerNotFoundException e -> {
                apiErrorDto3.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto3, HttpStatus.NOT_FOUND);
            }
            case CustomerOneOrMoreFieldsInvalidException e -> {
                apiErrorDto2.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto1, HttpStatus.BAD_REQUEST);
            }
            case CustomerStateInvalidException e -> {
                apiErrorDto2.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto1, HttpStatus.BAD_REQUEST);
            }
            case CustomerAlreadyExistsException e -> {
                apiErrorDto1.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto1, HttpStatus.FORBIDDEN);
            }
            case AddressNotFoundException e -> {
                apiErrorDto3.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto1, HttpStatus.NOT_FOUND);
            }
            case EmailAlreadyTakenException e -> {
                apiErrorDto1.setMessage(e.getMessage());
                return new ResponseEntity<>(apiErrorDto1, HttpStatus.FORBIDDEN);
            }
            default -> {
                ApiErrorDto defaultError = ApiErrorDto.builder()
                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .errorType(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .timestamp(Timestamp.from(Instant.now()))
                        .message(exception.getMessage())
                        .build();

                return new ResponseEntity<>(defaultError, HttpStatus.PRECONDITION_FAILED);
            }
        }
    }
}
