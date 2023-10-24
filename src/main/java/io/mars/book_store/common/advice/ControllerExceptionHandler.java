package io.mars.book_store.common.advice;

import io.mars.book_store.common.exception.ConstraintsViolationException;
import io.mars.book_store.common.exception.ContentNotFoundException;
import io.mars.book_store.common.exception.DuplicateRecordException;
import org.hibernate.TransientObjectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collections;
import java.util.Date;


@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = ConstraintsViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorMessage> handleInvalidEntity(ConstraintsViolationException exception, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage( new Date(),
                exception.getViolations(), request.getDescription(false));

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorMessage);
    }

    @ExceptionHandler(value = ContentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> handleContentNotFound(ContentNotFoundException exception, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage( new Date(),
                Collections.singletonList(exception.getMessage()), request.getDescription(false));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(value = TransientObjectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleTransientObject(TransientObjectException exception, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage( new Date(),
                Collections.singletonList(exception.getMessage()), request.getDescription(false));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessage> handleSSQLIntegrity(SQLIntegrityConstraintViolationException exception, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage( new Date(),
                Collections.singletonList(exception.getMessage()), request.getDescription(false));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage( new Date(),
                Collections.singletonList(exception.getMessage()), request.getDescription(false));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(value = DuplicateRecordException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessage> handleDuplicateRecord(DuplicateRecordException exception, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage( new Date(),
                Collections.singletonList(exception.getMessage()), request.getDescription(false));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }
}
