package com.jamescookie.scrabble;

import java.util.*;

public class PossibilityThreadCollector extends Thread {
    private final Collection<PossibilityThread> threads = new ArrayList<PossibilityThread>();
    private final int numberOfRequiredResults;
    private Collection<Possibility> possibilities = new HashSet<Possibility>();
    private final ResultExpecter expecter;
    private boolean stop = false;

    public PossibilityThreadCollector(int numberOfRequiredResults, ResultExpecter expecter) {
        this.numberOfRequiredResults = numberOfRequiredResults;
        this.expecter = expecter;
    }

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
            expecter.resultsAreReady();
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
        topPossibilities.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        return topPossibilities.subList(0, Math.min(topPossibilities.size(), numberReturned));
    }

}
