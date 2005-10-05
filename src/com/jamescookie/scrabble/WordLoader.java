package com.jamescookie.scrabble;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class WordLoader {
    public static final Set<String> _words = loadWords();

    private WordLoader() {
    }

    private static Set<String> loadWords() {
        Set<String> retValue = new HashSet<String>();
        String thisLine;

        try {
            BufferedReader myInput = new BufferedReader(new InputStreamReader(getStream("WORD.LST")));
            while ((thisLine = myInput.readLine()) != null) {
                retValue.add(thisLine.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retValue;
    }

    public static InputStream getStream(String fileName) throws FileNotFoundException, IOException {
        URL url = getLoader().getResource(fileName);
        InputStream is;

        System.out.println("Using: "+url);

        if (url != null) {
            is = url.openStream();
        } else {
            throw new FileNotFoundException("Could not find XML file '"+fileName+"', expected in WEB-INF/classes.");
        }

        return is;
    }

    private static ClassLoader getLoader() {
        Class c = WordLoader.class;
        ClassLoader cl = (c == null) ? null : c.getClassLoader();
        if (cl == null) {
            cl = ClassLoader.getSystemClassLoader();
        }
        return cl;
    }
}
