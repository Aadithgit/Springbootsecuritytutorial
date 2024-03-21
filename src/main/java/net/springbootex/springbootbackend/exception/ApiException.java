package net.springbootex.springbootbackend.exception;

public class ApiException extends RuntimeException {
    private static final long serviceVersionUID=1L;
    public ApiException(String message){
        super(message);
    }
}
