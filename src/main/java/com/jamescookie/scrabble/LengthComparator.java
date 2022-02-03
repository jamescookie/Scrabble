package com.jamescookie.scrabble;

import java.util.Comparator;

class LengthComparator implements Comparator<String> {
    public int compare(String o1, String o2) {
        int retValue;
        if (o2.length() == o1.length()) {
            retValue = o1.compareTo(o2);
        } else {
            retValue = o2.length() - o1.length();
        }
        return retValue;
    }
}
