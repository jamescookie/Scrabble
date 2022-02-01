package com.jamescookie.scrabble;

import java.util.LinkedHashSet;
import java.util.Set;

public class TwoLetters {
    public static final Set<String> TWO_LETTER_WORDS = new LinkedHashSet<String>();

    static {
        TWO_LETTER_WORDS.add("aa");
        TWO_LETTER_WORDS.add("ab");
        TWO_LETTER_WORDS.add("ad");
        TWO_LETTER_WORDS.add("ae");
        TWO_LETTER_WORDS.add("ag");
        TWO_LETTER_WORDS.add("ah");
        TWO_LETTER_WORDS.add("ai");
        TWO_LETTER_WORDS.add("al");
        TWO_LETTER_WORDS.add("am");
        TWO_LETTER_WORDS.add("an");
        TWO_LETTER_WORDS.add("ar");
        TWO_LETTER_WORDS.add("as");
        TWO_LETTER_WORDS.add("at");
        TWO_LETTER_WORDS.add("aw");
        TWO_LETTER_WORDS.add("ax");
        TWO_LETTER_WORDS.add("ay");
        TWO_LETTER_WORDS.add("ba");
        TWO_LETTER_WORDS.add("be");
        TWO_LETTER_WORDS.add("bi");
        TWO_LETTER_WORDS.add("bo");
        TWO_LETTER_WORDS.add("by");
        TWO_LETTER_WORDS.add("de");
        TWO_LETTER_WORDS.add("do");
        TWO_LETTER_WORDS.add("ed");
        TWO_LETTER_WORDS.add("ef");
        TWO_LETTER_WORDS.add("eh");
        TWO_LETTER_WORDS.add("el");
        TWO_LETTER_WORDS.add("em");
        TWO_LETTER_WORDS.add("en");
        TWO_LETTER_WORDS.add("er");
        TWO_LETTER_WORDS.add("es");
        TWO_LETTER_WORDS.add("et");
        TWO_LETTER_WORDS.add("ex");
        TWO_LETTER_WORDS.add("fa");
        TWO_LETTER_WORDS.add("go");
        TWO_LETTER_WORDS.add("ha");
        TWO_LETTER_WORDS.add("he");
        TWO_LETTER_WORDS.add("hi");
        TWO_LETTER_WORDS.add("hm");
        TWO_LETTER_WORDS.add("ho");
        TWO_LETTER_WORDS.add("id");
        TWO_LETTER_WORDS.add("if");
        TWO_LETTER_WORDS.add("in");
        TWO_LETTER_WORDS.add("is");
        TWO_LETTER_WORDS.add("it");
        TWO_LETTER_WORDS.add("jo");
        TWO_LETTER_WORDS.add("ka");
        TWO_LETTER_WORDS.add("la");
        TWO_LETTER_WORDS.add("li");
        TWO_LETTER_WORDS.add("lo");
        TWO_LETTER_WORDS.add("ma");
        TWO_LETTER_WORDS.add("me");
        TWO_LETTER_WORDS.add("mi");
        TWO_LETTER_WORDS.add("mm");
        TWO_LETTER_WORDS.add("mo");
        TWO_LETTER_WORDS.add("mu");
        TWO_LETTER_WORDS.add("my");
        TWO_LETTER_WORDS.add("na");
        TWO_LETTER_WORDS.add("ne");
        TWO_LETTER_WORDS.add("no");
        TWO_LETTER_WORDS.add("nu");
        TWO_LETTER_WORDS.add("od");
        TWO_LETTER_WORDS.add("oe");
        TWO_LETTER_WORDS.add("of");
        TWO_LETTER_WORDS.add("oh");
        TWO_LETTER_WORDS.add("om");
        TWO_LETTER_WORDS.add("on");
        TWO_LETTER_WORDS.add("op");
        TWO_LETTER_WORDS.add("or");
        TWO_LETTER_WORDS.add("os");
        TWO_LETTER_WORDS.add("ow");
        TWO_LETTER_WORDS.add("ox");
        TWO_LETTER_WORDS.add("oy");
        TWO_LETTER_WORDS.add("pa");
        TWO_LETTER_WORDS.add("pe");
        TWO_LETTER_WORDS.add("pi");
        TWO_LETTER_WORDS.add("re");
        TWO_LETTER_WORDS.add("sh");
        TWO_LETTER_WORDS.add("si");
        TWO_LETTER_WORDS.add("so");
        TWO_LETTER_WORDS.add("ta");
        TWO_LETTER_WORDS.add("ti");
        TWO_LETTER_WORDS.add("to");
        TWO_LETTER_WORDS.add("uh");
        TWO_LETTER_WORDS.add("um");
        TWO_LETTER_WORDS.add("un");
        TWO_LETTER_WORDS.add("up");
        TWO_LETTER_WORDS.add("us");
        TWO_LETTER_WORDS.add("ut");
        TWO_LETTER_WORDS.add("we");
        TWO_LETTER_WORDS.add("wo");
        TWO_LETTER_WORDS.add("xi");
        TWO_LETTER_WORDS.add("xu");
        TWO_LETTER_WORDS.add("ya");
        TWO_LETTER_WORDS.add("ye");
        TWO_LETTER_WORDS.add("yo");
    }

    public static String formatTwoLetterWords(String lineSeperator) {
        return formatTwoLetterWordsInternal(TWO_LETTER_WORDS, lineSeperator, 0);
    }

    public static String formatTwoLetterWordsInOppositeOrder(String lineSeperator) {
        return formatTwoLetterWordsInternal(reverseOrder(TWO_LETTER_WORDS), lineSeperator, 1);
    }

    static Set<String> reverseOrder(Set<String> twoLetterWords) {
        Set<String> twoLettersOppositeOrder = new LinkedHashSet<String>();

        for (int i = 'a'; i <= 'z'; i++) {
            for (String s : twoLetterWords) {
                if (i == s.charAt(1)) {
                    twoLettersOppositeOrder.add(s);
                }
            }
        }
        return twoLettersOppositeOrder;
    }

    private static String formatTwoLetterWordsInternal(Set<String> twoLetterWords, String lineSeperator, int index) {
        StringBuffer sb = new StringBuffer();
        char firstChar = 'a';
        String seperator = "";
        for (String s : twoLetterWords) {
            if (firstChar != s.charAt(index)) {
                seperator = lineSeperator;
            }
            firstChar = s.charAt(index);
            sb.append(seperator).append(s);
            seperator = ", ";
        }

        return sb.toString();
    }
}
