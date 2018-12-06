import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Hyphenator {

    private static final List<Character> vowels = Arrays.asList('a', 'ä', 'e', 'i', 'o', 'ö', 'u', 'ü', 'y');

    private static final List<String> notCuttable = Arrays.asList("ch", "ck", "ph", "rh", "sh", "th", "sch");

    private static final List<String> vowelPairs = Arrays.asList("au", "eu", "ie", "ei", "äu");

    private static final List<Character> consonants = Arrays.asList('b', 'c', 'd', 'f', 'g', 'h', 'j',
            'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 'ß', 't', 'v', 'w', 'x', 'z');

    public static void main(String... args) {
        //i, e, e, a, e, ä, o, a, e
        String test = "Dieser Satz enthält Vokale";
        //System.out.println(getVowels(test));
        String testSilbe = "haus";//"";
        String testSilbe2 = "Auetal";
        Word result = hyphenate(testSilbe);

        System.out.println("Word: " + result.getWord());
        System.out.println("Word Hyphenated: " + result.getWordHyphenated());
        System.out.println("Syllables: " + result.getNouns());

        Word result2 = hyphenate(testSilbe2);

        System.out.println("Word: " + result2.getWord());
        System.out.println("Word Hyphenated: " + result2.getWordHyphenated());
        System.out.println("Syllables: " + result2.getNouns());
    }

    public static long getVowels(String text) {
        return text.chars().mapToObj(s -> (char) s).filter(vowels::contains).count();
    }

    public static Word hyphenate(String word) {
        List<Character> chars = word.toLowerCase().chars().mapToObj(c -> (char) c).collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.size(); i++) {

            if (i + 3 < chars.size()) {
                //Vokal + Konsonant + gleicher Konsonant + Vokal
                if (isVowel(chars.get(i)) && chars.get(i + 1).equals(chars.get(i + 2)) && isVowel(chars.get(i + 3))) {
                    sb.append(chars.get(i)).append(chars.get(i + 1)).append('-').append(chars.get(i + 2)).append(chars.get(i + 3));
                    i += 3;
                    continue;
                }
                //Vokal + Konsonant + Konsonant + Vokal
                if (isVowel(chars.get(i)) && isConsonant(chars.get(i + 1)) && isConsonant(chars.get(i + 2)) && isVowel(chars.get(i + 3))) {
                    sb.append(chars.get(i)).append(chars.get(i + 1)).append('-').append(chars.get(i + 2)).append(chars.get(i + 3));
                    i += 3;
                    continue;
                }
                //Vokal + Consonant + Vokalpaar
                if (isVowel(chars.get(i)) && isConsonant(chars.get(i + 1)) && isVowelPair(chars.get(i + 2), chars.get(i + 3))) {
                    sb.append(chars.get(i)).append(chars.get(i + 1)).append('-').append(chars.get(i + 2)).append(chars.get(i + 3)).append("-");
                    i += 3;
                    continue;
                }
            }
            if (i + 2 < chars.size()) {
                //Vokal + Consonant + Vokal
                if (isVowel(chars.get(i)) && isConsonant(chars.get(i + 1)) && isVowel(chars.get(i + 2))) {

                    sb.append(chars.get(i)).append('-').append(chars.get(i + 1)).append(chars.get(i + 2));
                    i += 2;
                    continue;
                }
                //Bei Konsonant + Konsonant + Konsonant vor dem letzten Konsnanten trennen.
                if (isConsonant(chars.get(i)) && isConsonant(chars.get(i + 1)) && isConsonant(chars.get(i + 2))) {
                    //"SCH" case
                    if (notCuttable.contains(chars.get(i).toString() + chars.get(i + 1).toString() + chars.get(i + 2).toString())) {
                        //Falls nicht am Satzanfang dann trennen.
                        if (i > 0) {
                            sb.append('-').append(chars.get(i)).append(chars.get(i + 1)).append(chars.get(i + 2));
                            i += 2;
                            continue;
                        }
                        sb.append(chars.get(i)).append(chars.get(i + 1)).append(chars.get(i + 2));
                        i += 2;
                        continue;
                    }
                    sb.append(chars.get(i)).append(chars.get(i + 1)).append('-').append(chars.get(i + 2));
                    i += 2;
                    continue;
                }
            }
            if(i + 1 < chars.size())
            {
                //Bei Vokalpaaren
                if(isVowelPair(chars.get(i), chars.get(i + 1)))
                {
                    sb.append(chars.get(i)).append(chars.get(i + 1)).append('-');
                    i += 1;
                    continue;
                }
                //Vokal + Vokal + Konsonant gleicher Konsonant
                if(isVowel(chars.get(i)) && isVowel(chars.get(i + 1)) && chars.get(i).equals(chars.get(i +1)))
                {
                    sb.append(chars.get(i)).append(chars.get(i + 1)).append('-');
                    i += 1;
                    continue;
                }
            }
            sb.append(chars.get(i));
        }

        return new Word(word, sb.toString(), sb.toString().chars().mapToObj(c -> (char)c).filter(c -> c.equals('-')).count() + 1);
    }


    private static boolean isVowel(Character s) {
        return vowels.contains(s);
    }

    private static boolean isConsonant(Character s) {
        return consonants.contains(s);
    }

    private static boolean isVowelPair(Character a, Character b){
        return vowelPairs.contains(a.toString() + b.toString());
    }

    private static boolean isNotCuttablePair(String s) {
        return notCuttable.contains(s);
    }
}
