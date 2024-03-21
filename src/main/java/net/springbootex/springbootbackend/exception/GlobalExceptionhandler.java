package net.springbootex.springbootbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionhandler {
    @ExceptionHandler(ResourceNotfoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotfoundException exception, WebRequest request)
    {
        Errordetails errordetails=new Errordetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity(errordetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleAPIException(ApiException exception, WebRequest request)
    {
        Errordetails errordetails=new Errordetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity(errordetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception, WebRequest request)
    {
        Errordetails errordetails=new Errordetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity(errordetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}