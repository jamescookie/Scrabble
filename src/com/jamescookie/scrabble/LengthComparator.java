package com.jamescookie.scrabble;

import java.util.Comparator;

public class LengthComparator implements Comparator {
    public int compare(Object o1, Object o2) {
        int retValue;
        if (((String) o2).length() == ((String) o1).length()) {
            retValue = ((String) o1).compareTo((String) o2);
        } else {
            retValue = ((String) o2).length() - ((String) o1).length();
        }
        return retValue;
    }
}
