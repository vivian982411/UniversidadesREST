package com.ibm.academia.apirest.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String mensaje){
        super(mensaje);
    }

}
