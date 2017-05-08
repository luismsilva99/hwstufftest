package com.hwstuff.luis.uitesting.helpers;

import java.util.List;

public class ListHelper {

    private ListHelper() {
        // empty construtor
    }

    public static boolean isSorted(List<String> listToCheck) {
        String previous = "";

        for (final String current : listToCheck) {
            if (current.compareTo(previous) < 0)
                return false;
            previous = current;
        }

        return true;

    }
}
