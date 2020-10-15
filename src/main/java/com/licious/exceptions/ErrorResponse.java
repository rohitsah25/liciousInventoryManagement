package com.licious.exceptions;


import lombok.Value;

@Value
public class ErrorResponse {

    private final String error;
    private final String errorDescription;
}