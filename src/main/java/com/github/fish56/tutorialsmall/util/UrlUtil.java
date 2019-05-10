package com.github.fish56.tutorialsmall.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class UrlUtil {
    public static Map<String, String> queryToMap(String query){
        String decodedString = query;
        // String decodedString = URLDecoder.decode(query, StandardCharsets.UTF_8);

        Map<String, String> queryPairs = new LinkedHashMap<String, String>();

        String[] pairs = decodedString.split("&");

        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            queryPairs.put(pair.substring(0, idx), pair.substring(idx + 1));
        }

        return queryPairs;
    }
}
