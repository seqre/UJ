import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Banner {
    private BannerFont bannerFont;

    public void importBannerFont(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        bannerFont = new BannerFont();

        String input;
        String[] tempFontData;
        String[] tempSpacesData;
        Character letter;
        while ((input = reader.readLine()) != null) {
            tempFontData = new String[7];
            tempSpacesData = new String[7];
            letter = input.charAt(0);

            for (int row = 0; row < 7; row++) {
                switch (letter) {
                    case 'I':
                        tempFontData[row] = reader.readLine().substring(1, 4);
                        addSpaces(tempSpacesData, row, amountOfSpaces(tempFontData[row]));
                        tempFontData[row] = tempFontData[row].stripTrailing();
                        break;

                    case 'K':
                        tempFontData[row] = reader.readLine().substring(1, 7);
                        addSpaces(tempSpacesData, row, amountOfSpaces(tempFontData[row]));
                        tempFontData[row] = tempFontData[row].stripTrailing();
                        break;

                    default:
                        tempFontData[row] = reader.readLine().substring(1, 8);
                        addSpaces(tempSpacesData, row, amountOfSpaces(tempFontData[row]));
                        tempFontData[row] = tempFontData[row].stripTrailing();
                        break;
                }
            }

            bannerFont.create(letter, tempFontData, tempSpacesData);
        }

        tempFontData = new String[7];
        for (int row = 0; row < 7; row++) {
            tempFontData[row] = " ".repeat(3);
        }
        bannerFont.create(' ', tempFontData, new String[]{"", "", "", "", "", "", ""});
    }

    public String[] toBanner(String input) throws IOException {
        if (input == null) {
            return new String[]{};
        } else {
            String[] result = new String[7];
            importBannerFont(Banner.class.getResourceAsStream("BannerFont"));
            input = input.toUpperCase();

            for (int row = 0; row < 7; ++row) {
                result[row] = "";
                for (int letter = 0; letter < input.length(); ++letter) {
                    result[row] += bannerFont.getLine(input.charAt(letter), row);

                    if (letter != input.length() - 1) result[row] += bannerFont.getSpaces(input.charAt(letter), row);
                    if (letter < input.length() - 1 && input.charAt(letter + 1) != ' ') result[row] += " ";
                }
            }

            return result;
        }
    }

    private int amountOfSpaces(String line) {
        return (line.length() - line.stripTrailing().length());
    }

    private void addSpaces(String[] data, int row, int amount) {
        if (amount > 0) {
            data[row] = " ".repeat(amount);
        } else {
            data[row] = "";
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            String str = Arrays.stream(args).collect(Collectors.joining(" "));
            var toDisplay = new Banner().toBanner(str);
            for (var line : toDisplay) {
                System.out.println(line);
            }
        }
    }
}

class BannerFont {

    private HashMap<Character, CharacterData> bannerFont;

    public BannerFont() {
        this.bannerFont = new HashMap<>();
    }

    public void create(Character character, String[] fontData, String[] spaces) {
        bannerFont.put(character, new CharacterData(fontData, spaces));
    }

    public String getLine(Character character, int row) {
        return this.bannerFont.get(character).getLine(row);
    }

    public String getSpaces(Character character, int row) {
        return this.bannerFont.get(character).getSpaces(row);
    }

    private class CharacterData {
        private String[] fontLetter;
        private String[] spaces;

        CharacterData(String[] fontLetter, String[] spaces) {
            this.fontLetter = fontLetter;
            this.spaces = spaces;
        }

        String getLine(int row) {
            return (row < 7 ? fontLetter[row] : "");
        }

        String getSpaces(int row) {
            return (row < 7 ? spaces[row] : "");
        }

    }
}
