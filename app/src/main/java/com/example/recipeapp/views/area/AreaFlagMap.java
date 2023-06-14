package com.example.recipeapp.views.area;

import com.example.recipeapp.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Mapping of area names to their corresponding flag.<br>
 * Hardcoded solution, since the API currently does not provide the area images itself.
 */
public class AreaFlagMap {
    public static final Map<String, Integer> MAP = createMap();

    private static Map<String, Integer> createMap() {
        Map<String, Integer> result = new HashMap<>(40);

        result.put("British",      R.drawable.gb);
        result.put("American",     R.drawable.us);
        result.put("French",       R.drawable.fr);
        result.put("Canadian",     R.drawable.ca);
        result.put("Jamaican",     R.drawable.jm);
        result.put("Chinese",      R.drawable.cn);
        result.put("Dutch",        R.drawable.nl);
        result.put("Egyptian",     R.drawable.eg);
        result.put("Greek",        R.drawable.gr);
        result.put("Indian",       R.drawable.in);
        result.put("Irish",        R.drawable.ie);
        result.put("Italian",      R.drawable.it);
        result.put("Japanese",     R.drawable.jp);
        result.put("Kenyan",       R.drawable.kn);
        result.put("Malaysian",    R.drawable.my);
        result.put("Mexican",      R.drawable.mx);
        result.put("Moroccan",     R.drawable.ma);
        result.put("Croatian",     R.drawable.hr);
        result.put("Norwegian",    R.drawable.no);
        result.put("Portuguese",   R.drawable.pt);
        result.put("Russian",      R.drawable.ru);
        result.put("Argentinian",  R.drawable.ar);
        result.put("Spanish",      R.drawable.es);
        result.put("Slovakian",    R.drawable.sk);
        result.put("Thai",         R.drawable.th);
        result.put("Saudi Arabian",R.drawable.sa);
        result.put("Vietnamese",   R.drawable.vn);
        result.put("Turkish",      R.drawable.tr);
        result.put("Syrian",       R.drawable.sy);
        result.put("Algerian",     R.drawable.dz);
        result.put("Tunisian",     R.drawable.tn);
        result.put("Polish",       R.drawable.pl);
        result.put("Filipino",     R.drawable.ph);

        return Collections.unmodifiableMap(result);
    }

}
