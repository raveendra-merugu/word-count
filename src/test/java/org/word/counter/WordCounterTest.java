package org.word.counter;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Locale;

import static org.mockito.Mockito.when;

public class WordCounterTest {

    public static final Locale SPANISH = new Locale("es", "ES");

    @Test
    public void addWord() {
        Translator mock = getMockTranslator();
        WordCounter wordCounter = new WordCounter(mock);
        int actualCount = wordCounter.addWord("flower");
        Assert.assertEquals("Check count when a new word is added", actualCount, 1);
    }

    private Translator getMockTranslator() {
        Translator mock = Mockito.mock(Translator.class);
        when(mock.translate(Locale.ENGLISH, "flower")).thenReturn("flower");
        when(mock.translate(Locale.GERMANY, "blume")).thenReturn("flower");
        when(mock.translate(SPANISH, "flor")).thenReturn("flower");
        return mock;
    }

    @Test
    public void addSameWords() {
        Translator mock = getMockTranslator();
        WordCounter wordCounter = new WordCounter(mock);
        int actualCount = wordCounter.addWord("flower");
        actualCount = wordCounter.addWord("flower");
        Assert.assertEquals("Check count when add same word ", actualCount, 2);
    }

    @Test
    public void addInvalidWord() {
        Translator mock = getMockTranslator();
        WordCounter wordCounter = new WordCounter(mock);
        int actualCount = wordCounter.addWord("@flower");
        Assert.assertEquals("Check count when adding word contains invalid chars", 0, actualCount);
    }

    @Test
    public void addInvalidWordAndValidWord() {
        Translator mock = getMockTranslator();
        WordCounter wordCounter = new WordCounter(mock);
        int actualCount = wordCounter.addWord("@flower");
        actualCount = wordCounter.addWord("flower");
        Assert.assertEquals("Check count when adding invalid word and a valid word", 1, actualCount);
    }


    @Test
    public void addSameWordsFromDifferentLanguages() {

        Translator mock = getMockTranslator();
        WordCounter wordCounter = new WordCounter(mock);
        Locale.setDefault(Locale.ENGLISH);
        int actualCount = wordCounter.addWord("flower");
        Assert.assertEquals("Check count when adding first word", 1, actualCount);
        Locale.setDefault(Locale.GERMANY);
        actualCount = wordCounter.addWord("blume");
        Assert.assertEquals("Check count when adding same word in German Language", 2, actualCount);
        Locale.setDefault(SPANISH);
        actualCount = wordCounter.addWord("flor");
        Assert.assertEquals("Check count when adding same word in Spanish Language", 3, actualCount);
    }

    @Test
    public void canAdd() {
        WordCounter wordCounter = new WordCounter(getMockTranslator());
        int actualCount = wordCounter.addWord("flor_");
        Assert.assertEquals("Check count when adding an invalid value", 0, actualCount);
        actualCount = wordCounter.addWord("florqw@");
        Assert.assertEquals("Check count when adding an invalid value", 0, actualCount);


    }
}