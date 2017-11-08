package ru.ildar.algorithm.datastructure;

import ru.ildar.algorithm.datastructure.stack.LinkedStack;
import ru.ildar.algorithm.datastructure.stack.Stack;

import java.util.NoSuchElementException;

/**
 *
 * A common problem for compilers and text editors is determining whether the parentheses in a string are balanced and properly nested.
 * For example, the string ((())())() contains properly nested pairs of parentheses, which the strings )()( and ()) do not.
 * Give an algorithm that returns true if a string contains properly nested and balanced parentheses, and false if otherwise.
 * For full credit, identify the position of the first offending parenthesis if the string is not properly nested and balanced.
 *
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class BraceValidator {

    private static final char OPEN_CHAR = '(';
    private static final char CLOSE_CHAR = ')';

    public static int validate(String s) {
        Stack<Character> stack = new LinkedStack<>();
        int index = 0;
        while (index < s.length()) {
            char c = s.charAt(index);
            if(c == OPEN_CHAR) {
                stack.push(c);
            } else if(c == CLOSE_CHAR) {
                try {
                    stack.pop();
                } catch (NoSuchElementException ex) {
                    return index;
                }
            }
            index++;
        }
        if (stack.size() != 0) {
            return 0;
        }
        return -1;
    }


}
