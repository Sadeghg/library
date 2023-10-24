package io.mars.book_store.common.exception;

import lombok.Data;

@Data
public class DuplicateRecordException extends RuntimeException{

    public DuplicateRecordException(String message){
        super(message);
    }}
