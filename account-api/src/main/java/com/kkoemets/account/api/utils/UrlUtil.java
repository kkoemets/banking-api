package com.kkoemets.account.api.utils;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import static java.util.stream.Collectors.joining;

public class UrlUtil {
    private static final String DELIMITER = "/";
    private static final String PATH_VARIABLE_OPENING = "{";

    public static String replace(String url, String... pathVariables) {
        Queue<String> variablesQueue = new ArrayDeque<>(Arrays.asList(pathVariables));
        return Arrays
                .stream(url.split(DELIMITER))
                .map(urlPart -> urlPart.contains(PATH_VARIABLE_OPENING) ? variablesQueue.poll() : urlPart)
                .collect(joining(DELIMITER));
    }

}
