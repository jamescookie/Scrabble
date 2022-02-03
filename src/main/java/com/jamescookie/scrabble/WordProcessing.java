package com.jamescookie.scrabble;

import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class WordProcessing extends Thread {
    private final Tray _tray;
    private final Filter _filter;
    private Collection<String> _words;

    public void run() {
        _words = _filter.filter(_tray.getWords());
    }

    public void stopProcessing() {
        _tray.stop();
    }

    public Collection<String> getWords() {
        return _words;
    }
}
