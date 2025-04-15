package com.jamescookie.controller;

import com.jamescookie.controller.dto.*;
import com.jamescookie.scrabble.*;
import com.jamescookie.scrabble.types.Game;
import io.micronaut.context.annotation.Property;
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
import java.util.concurrent.locks.LockSupport;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class ScrabbleController {
    @Inject
    private Wordsmith wordsmith;
    @Property(name = "app.version", defaultValue = "0.0.1")
    private String version;

    @Produces(MediaType.TEXT_HTML)
    @ExecuteOn(TaskExecutors.BLOCKING)
    @View("board")
    @Get()
    public InitialResponse index() {
        return new InitialResponse(
                version,
                new Board(wordsmith, Game.normal()));
    }

    @ExecuteOn(TaskExecutors.BLOCKING)
    @Post(uri = "/find-words")
    public BoardResponse findWords(@Body PossibilityRequest possibilityRequest) throws Exception {
        Board board = getBoard(possibilityRequest.board());
        Collection<Possibility> results = getPossibilities(possibilityRequest, board);

        return new BoardResponse(board,
                results.stream().map(PossibilityResponse::from).collect(Collectors.toList()));
    }

    @ExecuteOn(TaskExecutors.BLOCKING)
    @Post(uri = "/add-word")
    public BoardResponse addWord(@Body AddRequest addRequest) throws Exception {
        Board board = getBoard(addRequest.board());
        board.putLetters(addRequest.add().letters().toLowerCase(), board.getSquare(addRequest.add().y(), addRequest.add().x()), Direction.from(addRequest.add().direction()));

        return new BoardResponse(board, null);
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
        possibilityGenerator.generate(possibilityRequest.letters(), possibilityRequest.numberOfPossibilities(), () -> finished.set(true));
        long allowed = possibilityRequest.secondsToWait() * 1000L;
        while (!finished.get() || System.currentTimeMillis() - start < allowed) {
            long remainingTime = allowed - (System.currentTimeMillis() - start);
            if (remainingTime > 0) {
                long sleepTime = Math.min(remainingTime, 50); // Sleep in smaller increments (e.g., 50ms)
                LockSupport.parkNanos(sleepTime * 1_000_000L);
            }
        }

        if (!finished.get()) {
            log.info("Did not finish in time, stopping.");
            possibilityGenerator.stop();
        }
        return possibilityGenerator.getResults();
    }

}
