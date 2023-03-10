package org.glebchanskiy.labTests.services;

import org.glebchanskiy.labTests.exceptions.BracketsException;
import org.glebchanskiy.labTests.exceptions.TokenException;
import org.glebchanskiy.labTests.services.utils.LanguageGrammar;

import java.util.*;

public class PdnfValidator {

    private PdnfValidator() {}
    public static boolean inDetail = true;

    public static boolean validate(String toBeValidate) {
        Map<Integer, Integer> brackets = getBracketsPositions(toBeValidate);

        if (!isDisjunction(toBeValidate, brackets))
            return false;

        List<Set<Token>> parsedExpression = parse(toBeValidate, brackets);

        if (!parsedExpression.isEmpty())
            return analyze(parsedExpression);
        else
            return false;
    }

    private static List<Set<Token>> parse(String toBeValidate, Map<Integer, Integer> brackets) {
        List<Set<Token>> parsedExpression = new ArrayList<>();

        if (inDetail) {
            System.out.println("Исходная формула:\n" + toBeValidate);
            System.out.println();
        }

        for (Map.Entry<Integer, Integer> entry : brackets.entrySet()) {
            Integer openBracketIndex = entry.getKey();
            Integer closeBracketIndex = entry.getValue();

            Set<Token> tokens = parseExpressionToTokens(toBeValidate, openBracketIndex, closeBracketIndex);

            if (inDetail) {
                System.out.println(toBeValidate.substring(openBracketIndex, closeBracketIndex+1) + ":");
                for (Token t : tokens) {
                    System.out.println(t);
                }
                System.out.println();
            }

            parsedExpression.add(tokens);
        }
        return parsedExpression;
    }

    private static boolean isDisjunction(String expression, Map<Integer, Integer> brackets) {
        Integer[] values = brackets.values().stream().sorted().toArray(Integer[]::new);
        for (Integer i : values) {
            if (i.equals(values[values.length - 1])) {
                return true;
            }
            if (LanguageGrammar.logicalConjunction.compareTo(expression.charAt(i+1)+"\\") == 0 ) {
                return false;
            }

        }
        return true;
    }


    private static boolean analyze(List<Set<Token>> parsedExpression) {
        Set<Token> base = parsedExpression.get(0);
        Set<String> expressionsInBinary = new HashSet<>();

        if (inDetail)
            System.out.println("В двоичной форме:");

        for (Set<Token> expr : parsedExpression) {

            if (expr.size() != base.size())
                return false;

            StringBuilder binary = new StringBuilder();
            for (Token token : expr) {
                if (!base.contains(token))
                    return false;

                if (token.isNegative)
                    binary.append("1");
                else
                    binary.append("0");
            }

            if (inDetail)
                System.out.println(binary);

            if (expressionsInBinary.contains(binary.toString())) {
                StringBuilder errBin = new StringBuilder();
                errBin.append("( ");
                for (Token token : expr)
                    errBin.append(token.isNegative ? "!" : "").append(token.symbols).append(" ");
                errBin.append(")");

                throw new TokenException("Одинаковые скобки:" + errBin);
            }
            else
                expressionsInBinary.add(binary.toString());
        }
        if (inDetail)
            System.out.println();
        return true;
    }

    private static Set<Token> parseExpressionToTokens(String expression,
                                                      Integer openBracketIndex,
                                                      Integer closeBracketIndex)
    {
        Set<Token> tokens = new TreeSet<>();
        StringBuilder token = new StringBuilder();
        for (int i = openBracketIndex + 1; i<=closeBracketIndex; i++) {
            if ( (LanguageGrammar.logicalConjunction.compareTo(expression.charAt(i)+"\\") == 0 && expression.charAt(i+1) == '\\')|| i == closeBracketIndex) {
                i++;
                try {
                    tokens.add(makeToken(token.toString()));
                } catch (TokenException e) {
                    throw new TokenException("Ошибка. Опечатка в скобках: " + expression.substring(openBracketIndex, closeBracketIndex+1));
                }
                token.setLength(0);
            }
            else if (LanguageGrammar.logicalDisjunction.compareTo(expression.charAt(i)+"/") == 0 && expression.charAt(i+1) == '/') {
                throw new TokenException("Дизъюнкция в скобках");
            }
            else {
                token.append(expression.charAt(i));
            }
        }
        return tokens;
    }

    private static Token makeToken(String planeToken) {
        StringBuilder symbols = new StringBuilder();
        boolean isNegative = false;

        char[] arrayToken = planeToken.toCharArray();
        for (char symbol : arrayToken)
            if (LanguageGrammar.latinCapitalLetter.contains(symbol+"") || Character.isDigit(symbol))
                symbols.append(symbol);
            else if (LanguageGrammar.logicalNot.compareTo(symbol+"") == 0 && isNegative)
                throw new TokenException("");
            else if (LanguageGrammar.logicalNot.compareTo(symbol+"") == 0)
                isNegative = true;
            else
                throw new IllegalArgumentException("Ошибка. Неверный символ: " + symbol);
        if (symbols.isEmpty())
            throw new TokenException("");
        return new Token(symbols.toString(), isNegative);

    }

    private static Map<Integer, Integer> getBracketsPositions(String lineWithBrackets) {
        Map<Integer, Integer> brackets = new HashMap<>();
        Stack<Integer> openBrackets = new Stack<>();
        try {
            for (int i = 0; i < lineWithBrackets.length(); i++) {
                if (LanguageGrammar.openBracket.equals(lineWithBrackets.charAt(i)+""))
                    openBrackets.push(i);
                if (LanguageGrammar.closeBracket.equals(lineWithBrackets.charAt(i)+""))
                    brackets.put(openBrackets.pop(), i);
            }
            if (!openBrackets.isEmpty())
                throw new BracketsException("");
        }
        catch (RuntimeException e) {
            throw new BracketsException("Ошибка ввода. Неправильное кол-во скобок.");
        }

        return brackets;
    }


}

