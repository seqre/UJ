public class Reverser {

    public String reverse(String input) {
        if (input == null) {
            return null;
        } else {
            input = input.strip();
            StringBuilder result = new StringBuilder();
            for (int letter = input.length() - 1; letter >= 0; letter--) {
                result.append(input.charAt(letter));
            }
            return result.toString();
        }
    }

    public String reverseWords(String input) {
        input = input.strip();
        StringBuilder result = new StringBuilder();
        StringBuilder word = new StringBuilder();

        for (int it = 0; it < input.length(); it++) {
            if (!Character.isSpaceChar(input.charAt(it))) {
                word.append(input.charAt(it));
            } else {
                result.insert(0, word.toString());
                result.insert(0, input.charAt(it));
                word = new StringBuilder();
            }
        }

        if (!word.toString().isEmpty()) {
            result.insert(0, word.toString());
        }
        return result.toString();
    }
}
