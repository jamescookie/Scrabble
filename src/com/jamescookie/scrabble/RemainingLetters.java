import java.util.LinkedHashMap;

/**
 * @author UKJamesCook
 */
public class RemainingLetters {
    private static LinkedHashMap<Character, Integer> letters = new LinkedHashMap<Character, Integer>();

    {
        letters.put('a', 7);
        letters.put('b', 2);
        letters.put('c', 3);
        letters.put('d', 4);
        letters.put('e', 11);
        letters.put('f', 3);
        letters.put('g', 2);
        letters.put('h', 6);
        letters.put('i', 7);
        letters.put('j', 1);
        letters.put('k', 1);
        letters.put('l', 4);
        letters.put('m', 2);
        letters.put('n', 6);
        letters.put('o', 6);
        letters.put('p', 2);
        letters.put('q', 1);
        letters.put('r', 6);
        letters.put('s', 6);
        letters.put('t', 8);
        letters.put('u', 3);
        letters.put('v', 1);
        letters.put('w', 2);
        letters.put('x', 1);
        letters.put('y', 2);
        letters.put('z', 1);
        letters.put('?', 2);
    }

    public String lettersLeft(String usedLetters) {
        LinkedHashMap<Character, Integer> localLetters = new LinkedHashMap<Character, Integer>(letters);
        String missing = "";
        String seperator = ". These extra letters could not be found: '";
        if (usedLetters != null) {
            char[] chars = usedLetters.toLowerCase().toCharArray();
            for (char c : chars) {
                if (localLetters.containsKey(c)) {
                    int score = localLetters.get(c);
                    if (score < 2) {
                        localLetters.remove(c);
                    } else {
                        localLetters.put(c, score - 1);
                    }
                } else {
                    missing += seperator + c + "'";
                    seperator = ", '";
                }
            }
        }
        String retValue = localLetters.toString();
        return retValue.substring(1, retValue.length() - 1) + missing;
    }

    public static void main(String[] args) {
        System.out.println(new RemainingLetters().lettersLeft("AAf??"));
    }
}
