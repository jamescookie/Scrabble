package com.jamescookie.controller;

import com.jamescookie.scrabble.ScrabbleException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton 
@Requires(classes = {ScrabbleException.class, ExceptionHandler.class})
public class ScrabbleExceptionHandler implements ExceptionHandler<ScrabbleException, HttpResponse<String>> {

    @Override
    public HttpResponse<String> handle(HttpRequest request, ScrabbleException exception) {
        return HttpResponse.badRequest(exception.getMessage());
    }
}
