package org.glebchanskiy.labTests.services.utils;

import java.util.List;
import java.util.Set;

public class LanguageGrammar {
    private LanguageGrammar() {}

    public static final Set<String> logicalConstants = Set.of("0", "1");
    public static final Set<String> latinCapitalLetter = Set.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
    public static final String logicalNot = "!";
    public static final String logicalConjunction = "/\\";
    public static final String logicalDisjunction = "\\/";
    public static final String implication = "->";
    public static final String equivalence = "~";
    public static final String openBracket = "(";
    public static final String closeBracket = ")";
}
