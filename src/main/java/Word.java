public class Word {

    private final String word;
    private final String wordHyphenated;
    private final long syllables;

    public Word(String word, String wordHyphenated, long syllable)
    {
        this.word = word;
        this.wordHyphenated = wordHyphenated;
        this.syllables = syllable;
    }

    public String getWord() {
        return word;
    }

    public String getWordHyphenated() {
        return wordHyphenated;
    }

    public long getNouns() {
        return syllables;
    }
}
