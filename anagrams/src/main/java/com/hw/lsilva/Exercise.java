package com.hw.lsilva;

import com.hw.lsilva.tests.AnagramTest;

public class Exercise {

    public static void main(String[] args) {
        System.out.println(new AnagramTest("Punishment", "Nine Thumps").toString());
        System.out.println(new AnagramTest("The Morse code", "Here come dots").toString());
        System.out.println(new AnagramTest("Snooze alarms", "Alas! No more Zs").toString());
        System.out.println(new AnagramTest("Halley's Comet", "Shall yet come").toString());
        System.out.println(new AnagramTest("One good turn deserves another.", "Do rogues endorse that? No,never!").toString());
    }
}
