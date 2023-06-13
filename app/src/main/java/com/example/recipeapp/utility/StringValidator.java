package com.example.recipeapp.utility;

public class StringValidator {
    public static boolean isValid(String input) {
        return input.isEmpty();
    }

    public static String clean(String input) {
        return input.toLowerCase().trim();
    }

}
