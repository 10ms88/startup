/*
 * (c) Sergey Butenin 2017
 */

package com.company.startup.utils;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.http.util.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtils {

    public static final String EMPTY_STRING = "";

    /*
     * Форматирование строки в формат имени-фамилии-отчества.
     * - удаление всех лишних символов
     * - все буквы прописные, кроме 1-й
     * */
    public static String initialsFormat(String val) {
        if (!TextUtils.isEmpty(val)) {
            StringBuilder sb = new StringBuilder();
            String[] vals = val.split("\\s");
            String prefix = "";
            for (String str : vals) {
                str = str.toLowerCase();
                str = capitalizeFirstLetter(str);
                sb.append(prefix);
                prefix = " ";
                sb.append(str);
            }
            return sb.toString();
        } else {
            return EMPTY_STRING;
        }
    }

    //Сделать первый знак заглавным
    public static String capitalizeFirstLetter(String val) {
        if (!TextUtils.isEmpty(val)) {
            String firstLetter = val.substring(0, 1).toUpperCase();
            String restLetters = val.substring(1);
            return firstLetter + restLetters;
        } else {
            return EMPTY_STRING;
        }

    }

    /**
     * Привести к нормальному виду Имя
     *
     * @param val
     * @return
     */
    public static String capitalizeName(String val) {
        final char[] delimeters = {' ', '_', '-'};
        return WordUtils.capitalizeFully(val, delimeters);
    }

    /*
     * Рассчет расстояния Левенштейна. Взято тут:
     * http://www.baeldung.com/java-levenshtein-distance
     * */
    public static int getLevenshteinDistance(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1]
                                    + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }

        return dp[x.length()][y.length()];
    }

    //Удалить из строки все символы, кроме чисел.
    public static String removeAllExcludeNums(String value) {
        if (!TextUtils.isEmpty(value)) {
            value = value.replaceAll("[^0-9]", "");
        } else {
            value = StringUtils.EMPTY_STRING;
        }

        return value;
    }

    //Удалить все пробелы
    public static String removeAllSpaces(String value) {
        if (!TextUtils.isEmpty(value)) {
            value = value.replaceAll("\\s+", "");
        }
        return value;
    }


    public static String removeLastChar(String str) {
        if (!TextUtils.isEmpty(str)) {
            return str.substring(0, str.length() - 1);
        } else {
            return EMPTY_STRING;
        }
    }

    private static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    private static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }

    /**
     * Convert List.toString() format to SQL query in format
     *
     * @param list String:  [1231,456,6,46487]
     * @return String: '1231','456','6','46487'
     */
    public static String listToSQLStringINArray(String list) {
        list = removeAllSpaces(list.replace("[", "'").replace("]", "'").replace(",", "','"));
        return list;
    }

    public static List<Long> converFromStringArrayToListLong(String array) {
        List<Long> result = new ArrayList<>();

        String[] split = removeAllSpaces(array.replace("[", "").replace("]", "")).split(",");

        for (String num : split) {
            result.add(Long.valueOf(num, 10));
        }

        return result;
    }

    /*
     * Форматирование оставляет Латинские буквы и Кирилицу
     * цифры, пробелы между словами, и знаки  /.(_)@?-
     * удаляет запятую, если не содержится латиницы
     * Каждое слово с заглавной буквы
     * */
    public static String fioFormatting(String val) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(val)) {
            val = removeExtraSymbols(val);
            if (val.contains(" ")) {
                String[] vals = val.split("\\s");
                String prefix = "";
                for (String str : vals) {
                    if (!TextUtils.isBlank(str)) {
                        sb.append(prefix);
                        prefix = " ";
                        str = splitFormatting(str);
                        sb.append(str);
                    }
                }
            } else {
                val = splitFormatting(val);
                sb.append(val);
            }
        }
        return sb.toString();
    }

    /**
     * Форматирование обрезает строку до нужной длины
     */
    public static String truncate(String val, int len) {
        return val.length() <= len ? val : val.substring(0, len);
    }

    private static String removeExtraSymbols(String val) {
        String result = EMPTY_STRING;
        if (!TextUtils.isEmpty(val)) {
            result = val.replaceAll("[^\\da-zA-Zа-яёА-ЯЁ /.,(_)@?-]", "");
            result = clearExtraWhitespaces(result);
            if (result.matches("[^a-zA-Z]{0,}")) {
                result = result.replaceAll("[,]", "");
            }
        }
        return result;
    }

    private static String clearExtraWhitespaces(String val) {
        String result = EMPTY_STRING;
        if (!TextUtils.isEmpty(val)) {
            result = val.replaceAll("[\\s]{2,}", " ");
            result = removeWhitespaces(result, "/");
            result = removeWhitespaces(result, " (");
            result = removeWhitespaces(result, ") ");
            result = removeWhitespaces(result, ". ");
            result = removeWhitespaces(result, "-");

        }
        return result;
    }

    private static String removeWhitespaces(String val, String symbol) {
        String result;
        result = val.replaceAll("[\\s][" + symbol + "][\\s]", symbol);
        result = result.replaceAll("[" + symbol + "][\\s]", symbol);
        result = result.replaceAll("[\\s][" + symbol + "]", symbol);
        return result;
    }

    private static String splitFormatting(String str) {
        str = str.toLowerCase();
        str = capitalizeFirstLetter(str);
        if (str.contains("-")) {
            str = splitRegex(str, "-");
        }
        if (str.contains("/")) {
            str = splitRegex(str, "/");
        }
        if (str.contains(".") && str.length() != 2) {
            str = splitRegex(str, ".");
        }
        if (str.contains("(")) {
            str = splitRegex(str, "(");
        }
        return str;
    }

    private static String splitRegex(String val, String regex) {
        StringBuilder sb = new StringBuilder();
        String[] vals = val.split("[" + regex + "]");
        String prefix = "";
        for (String str : vals) {
            str = str.toLowerCase();
            str = capitalizeFirstLetter(str);
            sb.append(prefix);
            sb.append(str);
            prefix = regex;
        }
        return sb.toString();
    }

    // Оставляет только латинские буквы и кирилицу.
    public static String getLetters(String val) {
        return val.replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "");
    }

}
