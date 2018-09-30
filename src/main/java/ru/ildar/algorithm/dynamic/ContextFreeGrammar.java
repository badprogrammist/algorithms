package ru.ildar.algorithm.dynamic;

import ru.ildar.algorithm.datastructure.tree.SearchTree;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class ContextFreeGrammar {

    public static class Grammar {
        private Map<String, Rule> rules;

        Grammar(Map<String, Rule> rules) {
            this.rules = rules;
        }

        Rule getRule(String symbol) {
            return rules.get(symbol);
        }

        int size() {
            return rules.size();
        }

        Set<String> getSymbols() {
            return rules.keySet();
        }

    }

    public static class GrammarBuilder {
        public static GrammarBuilder init() {
            return new GrammarBuilder();
        }

        private Map<String, Rule> rules = new HashMap<>();
        private int index = 0;

        public GrammarBuilder rule(String symbol, String[] values) {
            if (rules.containsKey(symbol)) {
                throw new IllegalArgumentException("There is a rule that already has the same symbol name");
            }

            rules.put(symbol, new Terminal(index, symbol, values));
            index++;

            return this;
        }

        public GrammarBuilder rule(String symbol, String first, String second) {
            if (rules.containsKey(symbol)) {
                throw new IllegalArgumentException("There is a rule that already has the same symbol name");
            }
            if (!rules.containsKey(first) || !rules.containsKey(second)) {
                throw new IllegalArgumentException("There is no rule with such symbol");
            }

            rules.put(symbol, new NonTerminal(index, symbol, first, second));
            index++;

            return this;
        }

        public Grammar create() {
            return new Grammar(rules);
        }
    }

    public static abstract class Rule {
        private String symbol;
        private int id;

        Rule(int id, String symbol) {
            this.id = id;
            this.symbol = symbol;
        }

        public int getId() {
            return id;
        }

        String getSymbol() {
            return symbol;
        }

        abstract boolean check(String...symbols);
    }

    private static class NonTerminal extends Rule {
        private String first;
        private String second;

        NonTerminal(int id, String symbol, String first, String second) {
            super(id, symbol);
            this.first = first;
            this.second = second;
        }

        @Override
        boolean check(String... symbols) {
            if (symbols == null || symbols.length != 2) {
                return false;
            }
            if (symbols[0] == null || symbols[0].isEmpty()) {
                return false;
            }
            if (symbols[1] == null || symbols[1].isEmpty()) {
                return false;
            }

            return first.equalsIgnoreCase(symbols[0]) && second.equalsIgnoreCase(symbols[1]);
        }
    }

    private static class Terminal extends Rule {
        private SearchTree<String> values;

        Terminal(int id, String symbol, String... values) {
            super(id, symbol);

            this.values = new SearchTree<>();

            for (String value : values) {
                this.values.add(value.toLowerCase());
            }
        }

        @Override
        boolean check(String... symbols) {
            if (symbols == null || symbols.length == 0) {
                return false;
            }
            if (symbols[0] == null || symbols[0].isEmpty()) {
                return false;
            }

            return values.contains(symbols[0].toLowerCase());
        }
    }

    public static class GrammarChecker {

        private String[][] m;
        private Grammar g;
        private String[] sentence;

        public boolean check(Grammar g, String[] sentence) {
            this.g = g;
            this.sentence = sentence;
            this.m = new String[sentence.length][sentence.length];
            int subStrLength = 0;

            while (subStrLength < sentence.length) {
                for (int begin = 0; begin < sentence.length - subStrLength; begin++) {
                    int end = begin + subStrLength;

                    if (begin == end) {
                        checkRules(begin, -1, end);
                    } else {
                        for (int k = begin; k < end; k++) {
                            checkRules(begin, k, end);
                        }
                    }
                }

                subStrLength++;
            }

            return m[0][sentence.length - 1] != null;
        }

        private void checkRules(int begin, int k, int end) {
            for (String symbol : g.getSymbols()) {
                Rule rule = g.getRule(symbol);

                if (begin == end) {
                    if (rule.check(sentence[begin])) {
                        m[begin][begin] = symbol;
                        break;
                    }
                } else {
                    String firstSymbol = m[begin][k];
                    String secondSymbol = m[k + 1][end];

                    if (rule.check(firstSymbol, secondSymbol)) {
                        m[begin][end] = symbol;
                        break;
                    }
                }

            }
        }
    }


}
