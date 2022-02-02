package com.jamescookie.controller;

import com.jamescookie.controller.dto.*;
import com.jamescookie.scrabble.*;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.views.View;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class ScrabbleController {
    @Inject
    Wordsmith wordsmith;

    @View("board")
    @Get()
    public HttpResponse<InitialResponse> index() {
        return HttpResponse.ok(new InitialResponse(new Board(wordsmith)));
    }

    @ExecuteOn(TaskExecutors.IO)
    @Post(uri="/find-words")
    public HttpResponse<BoardResponse> findWords(PossibilityRequest possibilityRequest) throws Exception {
        Board board = getBoard(possibilityRequest.board);
        Collection<Possibility> results = getPossibilities(possibilityRequest, board);

        BoardResponse body = new BoardResponse(board);
        body.setResults(results.stream().map(PossibilityResponse::from).collect(Collectors.toList()));

        return HttpResponse.ok(body);
    }

    @ExecuteOn(TaskExecutors.IO)
    @Post(uri="/add-word")
    public HttpResponse<BoardResponse> addWord(AddRequest addRequest) throws Exception {
        Board board = getBoard(addRequest.board);
        board.putLetters(addRequest.add.getLetters().toLowerCase(), board.getSquare(addRequest.add.getY(), addRequest.add.getX()), Direction.from(addRequest.add.getDirection()));

        BoardResponse body = new BoardResponse(board);
        return HttpResponse.ok(body);
    }

    private Board getBoard(String s) throws IOException, ScrabbleException {
        Board board = new Board(wordsmith);
        if (s != null && s.trim().length() != 0) {
            board.generate(new BufferedReader(new StringReader(s)));
        }
        return board;
    }

    private Collection<Possibility> getPossibilities(PossibilityRequest possibilityRequest, Board board) {
        PossibilityGenerator possibilityGenerator = new PossibilityGenerator(wordsmith, board);
        AtomicBoolean finished = new AtomicBoolean(false);
        long start = System.currentTimeMillis();
        possibilityGenerator.generate(possibilityRequest.letters, possibilityRequest.numberOfPossibilities, () -> finished.set(true));
        long allowed = possibilityRequest.secondsToWait * 1000L;
        while (!finished.get() || System.currentTimeMillis() - start < allowed) {
            try {
                Thread.sleep(allowed / 20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!finished.get()) {
            log.info("Did not finish in time, stopping.");
            possibilityGenerator.stop();
        }
        return possibilityGenerator.getResults();
    }

}
