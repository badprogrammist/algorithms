package ru.ildar.algorithm.dynamic

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class ContextFreeGrammarTest extends Specification {

    def "Test of checking grammar in sentence"() {
        given: "Some grammar rules"
        ContextFreeGrammar.Grammar g = ContextFreeGrammar.GrammarBuilder.init()
                .rule("verb", ["drank"] as String[])
                .rule("noun", ["cat", "milk"] as String[])
                .rule("article", ["the", "a"] as String[])
                .rule("noun-phrase", "article", "noun")
                .rule("verb-phrase", "verb", "noun-phrase")
                .rule("sentence", "noun-phrase", "verb-phrase")
                .create()

        when: "Trying to check grammar of some sentence"
        ContextFreeGrammar.GrammarChecker checker = new ContextFreeGrammar.GrammarChecker()
        boolean result = checker.check(g, sentence as String[])

        then: "The correctness of grammar should equals expected"
        result == expectedResult

        where:
        sentence                               | expectedResult
        ["the", "cat", "drank", "the", "milk"] | true
        ["a", "cat", "drank", "the", "milk"]   | true
        ["drank", "the", "milk"]               | true
        ["the", "milk"]                        | true
        ["drank"]                              | true
        ["the", "cat", "the", "milk"]          | false
        ["the", "cat", "drank", "milk"]        | false
        ["a", "cat", "drink", "the", "milk"]   | false
    }

}
