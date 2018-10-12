package ru.ildar.algorithm.dynamic;

import ru.ildar.algorithm.datastructure.Iterator;
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
        private Rule[] indexToRule;

        Grammar(Map<String, Rule> rules) {
            this.rules = rules;
            indexToRule = new Rule[rules.size()];

            for (Rule rule : rules.values()) {
                indexToRule[rule.getId()] = rule;
            }
        }

        Rule getRule(String symbol) {
            return rules.get(symbol);
        }

        Rule getRule(int id) {
            return indexToRule[id];
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

        private GrammarBuilder() {
        }

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

        abstract int check(String... symbols);

        abstract String[] getRightPart();

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
        int check(String... symbols) {
            if (symbols != null) {
                if (symbols.length == 1 && symbols[0] != null
                        && (first.equalsIgnoreCase(symbols[0]) || second.equalsIgnoreCase(symbols[0]))) {
                    return 1;
                }

                if (symbols.length == 2 && (
                        (!first.equalsIgnoreCase(symbols[0]) && second.equalsIgnoreCase(symbols[1]))
                                || (first.equalsIgnoreCase(symbols[0]) && !second.equalsIgnoreCase(symbols[1])))) {
                    return 1;
                }

                if (symbols.length == 2 && symbols[0] != null && symbols[1] != null
                        && first.equalsIgnoreCase(symbols[0]) && second.equalsIgnoreCase(symbols[1])) {
                    return 0;
                }
            }

            return 2;
        }

        @Override
        String[] getRightPart() {
            return new String[]{first, second};
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
        int check(String... symbols) {
            if (symbols != null) {
                if (symbols[0] != null && values.contains(symbols[0].toLowerCase())) {
                    return 0;
                }
            }

            return 1;
        }

        @Override
        String[] getRightPart() {
            String[] rightPart = new String[values.size()];
            Iterator<String> iter = values.iterator();
            int idx = 0;

            while (iter.hasNext()) {
                rightPart[idx] = iter.next();
            }

            return rightPart;
        }
    }

    public static class CYKAlgorithm {
        private int[][][] costs;
        private Grammar g;
        private String[] sentence;

        public int check(Grammar g, String[] sentence) {
            this.sentence = sentence;
            this.g = g;
            costs = new int[sentence.length][sentence.length][g.size()];

            for (int index1 = 0; index1 < sentence.length; index1++) {
                for (int index2 = 0; index2 < sentence.length; index2++) {
                    for (int id = 0; id < g.size(); id++) {
                        costs[index1][index2][id] = Integer.MAX_VALUE;
                    }
                }
            }

            checkTerminals();

            for (int length = 1; length < sentence.length; length++) {
                for (int i = 0; i < sentence.length - length; i++) {
                    int j = i + length;

                    for (int s = i; s < j; s++) {
                        checkNonTerminalRules(i, s, j);
                        checkCaseOfInserting(i, j);

                    }
                }
            }

            return costs[0][sentence.length - 1][getMinCostIndex(costs[0][sentence.length - 1])];
        }

        private void checkTerminals() {
            for (int index = 0; index < sentence.length; index++) {
                String symbol = sentence[index];

                for (int id = 0; id < g.size(); id++) {
                    Rule rule = g.getRule(id);
                    int cost = rule.check(symbol);
                    costs[index][index][id] = cost;
                }

                checkCaseOfInserting(index, index);
            }
        }

        private void checkNonTerminalRules(int i, int s, int j) {
            for (int id = 0; id < g.size(); id++) {
                Rule rule = g.getRule(id);
                String[] rightPart = rule.getRightPart();
                int min = Integer.MAX_VALUE;

                if (rightPart.length == 2) {
                    Rule firstRule = g.getRule(rightPart[0]);
                    Rule secondRule = g.getRule(rightPart[1]);

                    if (firstRule != null && secondRule != null) {
                        if (costs[i][s][firstRule.getId()] != Integer.MAX_VALUE && costs[s + 1][j][secondRule.getId()] != Integer.MAX_VALUE) {
                            min = costs[i][s][firstRule.getId()] + costs[s + 1][j][secondRule.getId()];
                        }
                    }
                }

                if (min < costs[i][j][id]) {
                    costs[i][j][id] = min;
                }
            }
        }

        private void checkCaseOfInserting(int i, int j) {
            for (int id = 0; id < g.size(); id++) {
                Rule rule = g.getRule(id);
                int min = Integer.MAX_VALUE;

                for (int firstRuleId = 0; firstRuleId < g.size(); firstRuleId++) {
                    if (id != firstRuleId) {
                        int cost = Integer.MAX_VALUE;

                        if (costs[i][j][firstRuleId] != Integer.MAX_VALUE) {
                            cost = costs[i][j][firstRuleId] + rule.check(g.getRule(firstRuleId).getSymbol());
                        }

                        if (cost < min) {
                            min = cost;
                        }
                    }
                }

                if (min < costs[i][j][id]) {
                    costs[i][j][id] = min;
                }
            }
        }

        private static int getMinCostIndex(int... values) {
            int min = Integer.MAX_VALUE;
            int index = 0;

            for (int i = 0; i < values.length; i++) {
                if (values[i] < min) {
                    min = values[i];
                    index = i;
                }
            }

            return index;
        }
    }


}
