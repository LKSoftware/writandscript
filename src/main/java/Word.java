import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Word {

    private static final List<Character> VOWELS = Arrays.asList('a', 'ä', 'e', 'i', 'o', 'ö', 'u', 'ü', 'y');

    private static final List<String> CONSONANTPAIRS = Arrays.asList("ch", "ck", "ph", "rh", "sh", "th", "sch");

    private static final List<String> VOWELPAIRS = Arrays.asList("au", "eu", "ie", "ei", "äu");

    private static final List<Character> CONSONANTS = Arrays.asList('b', 'c', 'd', 'f', 'g', 'h', 'j',
            'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 'ß', 't', 'v', 'w', 'x', 'z');

    private final String word;

    private String wordHyphenated;

    private long syllables;

    private int[] vowelPairsLocation;

    private int vowelPairs = 0;
    private int vowels = 0;

    public Word(String word)
    {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public String getWordHyphenated() {
        return wordHyphenated != null ? wordHyphenated : word;
    }

    public List<Character> getWordAsCharacterList()
    {
        return word.toLowerCase().chars().mapToObj(c -> (char) c).collect(Collectors.toList());
    }

    public long getSyllables() {
        return syllables != 0 ? syllables : count();
    }

    public int getVowels()
    {
        if(vowels == 0)
        {
            count();
        }
        return vowels;
    }

    public int getVowelPairs()
    {
        if(vowelPairs == 0)
        {
            count();
        }
        return vowelPairs;
    }

    public static boolean isVowelPair(List<Character> chars, int i) {
        return i + 1 < chars.size() && VOWELPAIRS.contains(chars.get(i).toString() + chars.get(i + 1).toString());
    }

    public static boolean isVowel(Character c)
    {
        return VOWELS.contains(c);
    }

    public static boolean isVowelPair(Character first, Character second)
    {
        return VOWELPAIRS.contains(first.toString() + second.toString());
    }

    public static boolean isConsonant(Character c)
    {
        return CONSONANTS.contains(c);
    }

    public static boolean isConsonantPair(Character... c)
    {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(c).forEach(ch -> sb.append(ch.toString()));
        return CONSONANTPAIRS.contains(sb.toString());
    }

    private long count()
    {
        List<Character> chars = getWordAsCharacterList();
        vowelPairsLocation = new int[chars.size()];
        vowelPairs = 0;
        vowels = 0;

        for (int i = 0 ; i < chars.size(); i++) {

            if(Word.isVowel(chars.get(i)))
            {
                if(Word.isVowelPair(chars, i))
                {
                    vowelPairs++;
                    vowelPairsLocation[i] = 1;
                    vowelPairsLocation[i + 1] = 1;
                    continue;
                }
                if(vowelPairsLocation[i] == 0) {
                    vowels++;
                    vowelPairsLocation[i] = 0;
                }
            }
        }
        return vowels + vowelPairs;
    }

}
