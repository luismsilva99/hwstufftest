package com.hw.lsilva;


public class Main {

    public static void main(String[] args) {
        System.out.println(fixSpelling("hostleworld"));
    }

    public static String fixSpelling(String name) {
        String wordToCheck = new String(name);
        if (wordToCheck.equals("hostleworld")   ) {
            name = "hostelworld";
        } else {
            fixSpelling(name);
        }
        return name;
    }
}
