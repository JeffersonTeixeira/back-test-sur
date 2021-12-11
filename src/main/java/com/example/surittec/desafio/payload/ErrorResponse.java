package com.example.surittec.desafio.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse() {
    }

    private String message;

}
