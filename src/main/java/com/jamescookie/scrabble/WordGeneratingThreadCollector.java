package com.jamescookie.scrabble;

import java.util.ArrayList;
import java.util.Collection;

public class WordGeneratingThreadCollector extends Thread {
    private final Collection<WordGeneratingThread> threads = new ArrayList<WordGeneratingThread>();
    private final PossibilityThreadCollector possibilityThreadCollector;
    private boolean stop = false;

    public WordGeneratingThreadCollector(PossibilityThreadCollector possibilityThreadCollector) {
        this.possibilityThreadCollector = possibilityThreadCollector;
    }

    public void add(WordGeneratingThread wordGeneratingThread) {
        threads.add(wordGeneratingThread);
    }

    public void run() {
        for (WordGeneratingThread t : threads) {
            t.start();
            if (stop) {
                break;
            }
        }
        yield();
        for (WordGeneratingThread t : threads) {
            try {
                t.join();
                if (stop) {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!stop) {
            possibilityThreadCollector.start();
        }
    }

    public void stopProcessing() {
        stop = true;
        for (WordGeneratingThread t : threads) {
            t.stopProcessing();
        }
    }
}
