package com.jamescookie.controller;

import com.jamescookie.controller.dto.*;
import com.jamescookie.scrabble.*;
import com.jamescookie.scrabble.types.Game;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.views.View;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class ScrabbleController {
    @Inject
    private Wordsmith wordsmith;

    @Produces(MediaType.TEXT_HTML)
    @ExecuteOn(TaskExecutors.BLOCKING)
    @View("board")
    @Get()
    public InitialResponse index() {
        return new InitialResponse(new Board(wordsmith, Game.normal()));
    }

    @ExecuteOn(TaskExecutors.BLOCKING)
    @Post(uri = "/find-words")
    public BoardResponse findWords(@Body PossibilityRequest possibilityRequest) throws Exception {
        Board board = getBoard(possibilityRequest.board);
        Collection<Possibility> results = getPossibilities(possibilityRequest, board);

        BoardResponse body = new BoardResponse(board);
        body.setResults(results.stream().map(PossibilityResponse::from).collect(Collectors.toList()));

        return body;
    }

    @ExecuteOn(TaskExecutors.BLOCKING)
    @Post(uri = "/add-word")
    public BoardResponse addWord(@Body AddRequest addRequest) throws Exception {
        Board board = getBoard(addRequest.board);
        board.putLetters(addRequest.add.letters().toLowerCase(), board.getSquare(addRequest.add.y(), addRequest.add.x()), Direction.from(addRequest.add.direction()));

        return new BoardResponse(board);
    }

    private Board getBoard(String s) throws IOException, ScrabbleException {
        Board board = new Board(wordsmith, Game.normal());
        if (s != null && !s.trim().isEmpty()) {
            board.importBoard(s);
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
