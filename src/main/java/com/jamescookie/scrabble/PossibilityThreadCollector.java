package com.jamescookie.scrabble;

import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class PossibilityThreadCollector extends Thread {
    private final Collection<PossibilityThread> threads = new ArrayList<>();
    private final int numberOfRequiredResults;
    private Collection<Possibility> possibilities = new HashSet<>();
    private final ResultExpector expector;
    private boolean stop = false;

    public void add(PossibilityThread possibilityThread) {
        threads.add(possibilityThread);
    }

    public void run() {
        for (PossibilityThread t : threads) {
            try {
                t.join();
                if (stop) {
                    break;
                } else {
                    Thread.yield();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            possibilities.addAll(t.getPossibilities());
        }
        if (!stop) {
            possibilities = findTopPossibilities(possibilities, numberOfRequiredResults);
            expector.resultsAreReady();
        }
    }

    public Collection<Possibility> getResults() {
        return possibilities;
    }

    public void stopProcessing() {
        stop = true;
        for (PossibilityThread t : threads) {
            if (t != null) {
                t.stopProcessing();
            }
        }
    }

    private List<Possibility> findTopPossibilities(Collection<Possibility> possibilities, int numberReturned) {
        ArrayList<Possibility> topPossibilities = new ArrayList<>(possibilities);
        topPossibilities.sort((p1, p2) -> Integer.compare(p2.score(), p1.score()));
        return topPossibilities.subList(0, Math.min(topPossibilities.size(), numberReturned));
    }

}
