package ru.ildar.algorithm.dynamic;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *
 * Consider the following data compression technique. We have a table of m text strings, each at most k in length.
 * We want to encode a data string D of length n using as few text strings as possible. For example,
 * if our table contains (a,ba,abab,b) and the data string is bababbaababa, the best way to encode it
 * is (b,abab,ba,abab,a)— a total of five code words. Give an O(nmk) algorithm to find the length of the best encoding.
 * You may assume that every string has at least one encoding in terms of the table.
 *
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

}
