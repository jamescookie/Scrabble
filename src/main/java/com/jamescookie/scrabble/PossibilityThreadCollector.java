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
                    yield();
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
        ArrayList<Possibility> topPossibilities = new ArrayList<Possibility>(possibilities);
        Collections.sort(topPossibilities, new Comparator<Possibility>() {
            public int compare(Possibility p1, Possibility p2) {
                int score1 = p1.getScore();
                int score2 = p2.getScore();
                return (score2 < score1 ? -1 : (score2 == score1 ? 0 : 1));
            }
        });
        return topPossibilities.subList(0, (topPossibilities.size() > numberReturned ? numberReturned : topPossibilities.size()));
    }

}
