package io.mars.book_store.common.exception;

import lombok.Data;

@Data
public class ContentNotFoundException extends RuntimeException{

    public ContentNotFoundException(String message){
        super(message);
    }
}
