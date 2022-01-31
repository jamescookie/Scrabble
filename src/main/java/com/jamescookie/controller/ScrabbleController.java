package com.jamescookie.controller;

import com.jamescookie.scrabble.Board;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

@Controller
public class ScrabbleController {

    @View("board")
    @Get("/")
    public HttpResponse<BoardModel> index() {
        return HttpResponse.ok(new BoardModel(new Board(null).getEntireBoard()));
    }

}
