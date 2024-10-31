package fr.exalt.businessmicroservicespringsecurity.entities.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Builder
@Getter
@Setter
public class ApiError {
    private int errorCode;
    private String errorType;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="Europe/Paris")
    private Timestamp timestamp;
}
