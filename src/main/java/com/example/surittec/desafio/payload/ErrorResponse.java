package com.example.surittec.desafio.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message, String... details) {
        this.message = message;
        this.details = Arrays.asList(details);

    }

    //General error message about nature of error
    private String message;

    //Specific errors in API request processing
    private List<String> details;
}
