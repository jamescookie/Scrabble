package com.jamescookie.scrabble;

import java.util.Collection;
import javax.swing.JList;

public class WordProcessing extends Thread {
    private final Tray _tray;
    private final Filter _filter;
    private JList _list;
    private Collection<String> _words;

    public WordProcessing(Tray tray, Filter filter) {
        _tray = tray;
        _filter = filter;
    }

    public WordProcessing(Tray tray, Filter filter, JList list) {
        this(tray, filter);
        _list = list;
    }

    public void run() {
        _words = _filter.filter(_tray.getWords());
        if (_list != null) {
            Utils.displayCollection(_words, _list);
        }
    }

    public void stopProcessing() {
        _tray.stop();
    }

    public Collection<String> getWords() {
        return _words;
    }
}
