package ru.ildar.algorithm.dynamic;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *         <p>
 *         Consider the following data compression technique. We have a table of m text strings, each at most k in length.
 *         We want to encode a data string D of length n using as few text strings as possible. For example,
 *         if our table contains (a,ba,abab,b) and the data string is bababbaababa, the best way to encode it
 *         is (b,abab,ba,abab,a)— a total of five code words. Give an O(nmk) algorithm to find the length of the best encoding.
 *         You may assume that every string has at least one encoding in terms of the table.
 */
public class DataСompression {

    public static class BacktrackingApproach {
        private char[][] compressionTable;
        private char[] data;
        private char[] archive;

        public void encode(char[] data, char[][] compressionTable) {
            this.data = data;
            this.compressionTable = compressionTable;
            this.archive = data;

            find(0, "");
        }

        private void find(int seek, String solution) {
            if (seek >= data.length) {
                checkSolution(solution);
                return;
            }

            for (int compIdx = 0; compIdx < compressionTable.length; compIdx++) {
                boolean equals = true;

                if ((seek + compressionTable[compIdx].length) > data.length) {
                    continue;
                }

                for (int compStrIdx = 0; compStrIdx < compressionTable[compIdx].length; compStrIdx++) {
                    if (compressionTable[compIdx][compStrIdx] != data[seek + compStrIdx]) {
                        equals = false;
                        break;
                    }
                }

                if (equals) {
                    find(seek + compressionTable[compIdx].length, solution + compIdx);
                }
            }
        }

        private void checkSolution(String solution) {
            if (solution.length() < this.archive.length) {
                this.archive = solution.toCharArray();
            }
        }

        public char[] getArchive() {
            return this.archive;
        }
    }

    public static class DynamicApproach {
        private char[][] compressionTable;
        private char[] data;
        private int[] codes;
        private int codeNumbers;

        public void encode(char[] data, char[][] compressionTable) {
            this.compressionTable = compressionTable;
            this.data = data;

            int[] codeNumbers = new int[data.length + 1];
            for (int i = 0; i < data.length; i++) {
                codeNumbers[i] = Integer.MAX_VALUE;
            }

            codes = new int[data.length];
            for (int i = 0; i < data.length; i++) {
                codes[i] = -1;
            }

            for (int i = data.length - 1; i >= 0; i--) {
                for (int codeIdx = 0; codeIdx < compressionTable.length; codeIdx++) {
                    if (equals(i, codeIdx)
                            && (1 + codeNumbers[i + compressionTable[codeIdx].length]) < codeNumbers[i]) {
                        codeNumbers[i] = 1 + codeNumbers[i + compressionTable[codeIdx].length];
                        codes[i] = codeIdx;
                    }
                }
            }

            this.codeNumbers = codeNumbers[0];
        }

        private boolean equals(int i, int codeIdx) {
            if ((data.length - i) < compressionTable[codeIdx].length) {
                return false;
            }

            for (int codeCharIdx = 0; codeCharIdx < compressionTable[codeIdx].length; codeCharIdx++) {
                if (data[i + codeCharIdx] != compressionTable[codeIdx][codeCharIdx]) {
                    return false;
                }
            }

            return true;
        }

        public char[] getArchive() {
            char[] archive = new char[codeNumbers];
            int codeIdx = 0;

            for (int archiveIdx = 0; archiveIdx < codeNumbers; archiveIdx++) {
                archive[archiveIdx] = Character.forDigit(codes[codeIdx], 10);
                codeIdx += compressionTable[codes[codeIdx]].length;
            }

            return archive;
        }


    }

}
