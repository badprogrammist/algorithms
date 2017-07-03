package ru.ildar.algorithm.datastructure;

import ru.ildar.algorithm.datastructure.stack.LinkedStack;
import ru.ildar.algorithm.datastructure.stack.Stack;

import java.util.NoSuchElementException;

/**
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
                stack.put(c);
            } else if(c == CLOSE_CHAR) {
                try {
                    stack.poll();
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
