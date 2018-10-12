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
        ContextFreeGrammar.CYKAlgorithm alg = new ContextFreeGrammar.CYKAlgorithm()
        int result = alg.check(g, sentence as String[])

        then: "The correctness of grammar should equals expected"
        result == expectedResult

        where:
        sentence                               | expectedResult
        ["the", "cat", "drank", "the", "milk"] | 0
        ["a", "cat", "drank", "the", "milk"]   | 0
        ["drank", "the", "milk"]               | 0
        ["the", "milk"]                        | 0
        ["drank"]                              | 0
        ["the", "cat", "the", "milk"]          | 1 // missing verb
        ["the", "cat", "drank", "milk"]        | 1 // missing...the|a
        ["a", "cat", "drink", "the", "milk"]   | 1 // drink -> drank
        ["cat", "drank", "milk"]               | 2 // missing the|a... the|a...
    }


}
