package com.jamescookie.scrabble;

import java.util.ArrayList;
import java.util.Collection;

public class Filter {
    private Integer _length;
    private String _mustContain;
    private boolean _containsWildcards;
    private Operator _operator = Operator.EQUALS;

    public Filter() {}

    public void setLength(int data) {
        _length = data;
    }

    public void setOperator(Operator operator) {
        _operator = operator;
    }

    public void setMustContain(String data) {
        _mustContain = data;
        if (data != null && data.length() < 1) {
            _mustContain = null;
        }
        if (_mustContain != null) {
            _containsWildcards = (_mustContain.indexOf("*") != -1);
        }
    }

    public String getMustContain() {
        return _mustContain;
    }

    /**
     * finds words that conform to the filter.
     *
     * @param words A Collection of words to check.
     * @return A Collection of words that pass the filter.
     */
    public Collection<String> filter(Collection<String> words) {
        Collection<String> retValue;

        if (_length == null && _mustContain == null) { // no filter
            retValue = words;
        } else {
            retValue = new ArrayList<String>();
            boolean shouldAdd;
            for (String word : words) {
                shouldAdd = true;
                // Check the length
                if (_length != null) {
                    if (Operator.LESS_THAN.equals(_operator)) {
                        if (word.length() >= _length) {
                            shouldAdd = false;
                        }
                    } else if (Operator.GREATER_THAN.equals(_operator)) {
                        if (word.length() <= _length) {
                            shouldAdd = false;
                        }
                    } else {
                        if (_length != word.length()) {
                            shouldAdd = false;
                        }
                    }
                }
                // Check it contains the required string
                if (shouldAdd && _mustContain != null) {
                    if (word.length() < _mustContain.length()) {
                        shouldAdd = false;
                    } else {
                        if (_containsWildcards) {
                            shouldAdd = contains(word, _mustContain);
                        } else {
                            shouldAdd = (word.indexOf(_mustContain) != -1);
                        }
                    }
                }
                // if everything is ok, add it.
                if (shouldAdd) {
                    retValue.add(word);
                }
            }
        }

        return retValue;
    }

    /**
     * checks to see if one String contains the other.
     * This deals with wildcards (*) that are used to represent any character.
     *
     * @param str The String to check if it contains the other.
     * @param mustContain The String that might be contained.
     * @return <code>True</code> if one String can be found in the other.
     */
    private static boolean contains(String str, String mustContain) {
        boolean doesContain = false;
        String characterToLookFor;
        int position;
        char[] letters = str.toCharArray();
        String newWord;

        while (letters.length >= mustContain.length()) {
            doesContain = true;
            newWord = new String(letters);
            position = -1;
            for (int i = 0; i < mustContain.length(); i++) {
                characterToLookFor = mustContain.substring(i, i + 1);
                if ("*".equals(characterToLookFor)) {
                    position++;
                } else {
                    if (position > -1) {
                        position++;
                        if (newWord.indexOf(characterToLookFor, position) != position) {
                            doesContain = false;
                        }
                    } else {
                        position = newWord.indexOf(characterToLookFor);
                        if (position == -1) {
                            doesContain = false;
                        }
                    }
                }
            }
            if (doesContain) {
                break;
            }
            letters = Utils.removeElement(letters, 0);
        }

        return doesContain;
    }


}
