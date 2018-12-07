import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Hyphenator {




    public static void main(String... args) {

        Word word = new Word("Mauerei");



        System.out.println("Word: " + word.getWord());
        System.out.println("Word Hyphenate: " + word.getWordHyphenated());
        System.out.println("Syllables: " + word.getSyllables());
    }

    public void hyphenate(Word word){



    }

    private String patternHyphenate(List<Character> chars){

        return null;

    }

}
