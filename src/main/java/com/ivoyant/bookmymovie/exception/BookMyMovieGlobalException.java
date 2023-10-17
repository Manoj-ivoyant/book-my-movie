package com.ivoyant.bookmymovie.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookMyMovieGlobalException extends RuntimeException{

    private String message;
    private HttpStatus httpStatus;

    public BookMyMovieGlobalException(String message){
        super(message);
    }
}
