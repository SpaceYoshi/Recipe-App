package com.example.recipeapp.utility;

public class StringValidator {

    public static boolean isInvalid(String input) {
        return input.isEmpty();
    }

    public static String clean(String input) {
        return input.toLowerCase().trim();
    }

    public static boolean isInvalidClean(String input) {
        return isInvalid(clean(input));
    }

}
