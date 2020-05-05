package org.word.counter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Holds the Map of word and count. The count is no of times a given word is added.
 */
public class WordCounter {
    /**
     * Pattern only allows numbers and characters
     */
    private static final Pattern PATTERN = Pattern.compile("[^A-Za-z0-9]+");

    private Map<String, Integer> wordCounterMap = null;
    private Translator translator;

    public WordCounter(Translator translator) {
        this.wordCounterMap = new HashMap<>();
        this.translator=translator;
    }

    /**
     * Add word to the collection
     *
     * @param word
     * @return
     */
    public Integer addWord(String word) {
        int count = 0;
        if (canAdd(word)) {
            Locale aDefault = Locale.getDefault();
            word = translator.translate(aDefault,word);
            if (wordCounterMap.containsKey(word)) {
                count = wordCounterMap.get(word) + 1;
            } else {
                count = 1;
            }
            wordCounterMap.put(word, count);
        }
        return count;
    }

    protected boolean canAdd(String word) {

        return !PATTERN.matcher(word).find();

    }
}
