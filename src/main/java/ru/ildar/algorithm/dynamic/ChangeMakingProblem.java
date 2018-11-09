package ru.ildar.algorithm.dynamic;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *
 * Give an efficient algorithm that correctly determines the minimum number of coins
 * needed to make change of n units using denominations {d1 , . . . , dk }.
 *
 */
public class ChangeMakingProblem {

    public static class GreedyAlgorithm {
        private int[] coins;
        private Change[] changes;

        /**
         *
         * @param amount - the amount of money it has to change
         * @param coins - the denonomination values of coins arranged by decrease order
         * @return - how many coins of each denonomination should
         * it take to represent the amount of money
         */
        public int[] makeChange(int amount, int[] coins) {
            this.coins = coins;

            this.changes = new Change[coins[0] + 1];
            for (int i = 0; i < coins.length; i++) {
                int[] numberOfEachCoin = new int[coins.length];
                numberOfEachCoin[i] = 1;
                changes[coins[i]] = new Change(numberOfEachCoin);
            }

            return makeChange(amount).getNumberOfEachCoin();
        }

        private Change makeChange(int amount) {
            if (amount < changes.length) {
                if (changes[amount] != null) {
                    return changes[amount];
                }
            }

            Change minChange = null;
            int minNumberOfCoins = Integer.MAX_VALUE;

            for (int i = 0; i < coins.length; i++) {
                if (coins[i] < amount) {
                    int count = amount / coins[i];
                    int remain = amount - (count * coins[i]);

                    int[] numberOfEachCoin = new int[coins.length];
                    numberOfEachCoin[i] = count;
                    Change change = new Change(numberOfEachCoin);

                    if (remain != 0) {
                        change = change.add(makeChange(remain));
                    }

                    int numberOfCoins = change.getNumberOfCoins();

                    if (numberOfCoins < minNumberOfCoins) {
                        minChange = change;
                        minNumberOfCoins = numberOfCoins;
                    }
                }
            }

            if (amount < changes.length) {
                changes[amount] = minChange;
            }

            return minChange;
        }
    }

    public static class ClassicAlgorithm {

        private int[] coins;
        private Change[] changes;

        public int[] makeChange(int amount, int[] coins) {
            this.coins = coins;

            changes = new Change[amount + 1];
            changes[0] = new Change(new int[coins.length]);
            for (int i = 0; i < coins.length; i++) {
                int[] change = new int[coins.length];
                change[i] = 1;
                changes[coins[i]] = new Change(change);
            }

            Change change = findChange(amount);
            return change.getNumberOfEachCoin();
        }

        private Change findChange(int amount) {
            for (int coin : coins) {
                if (coin <= amount) {
                    int remain = amount - coin;
                    Change remainChange = findChange(remain);
                    Change amountChange = changes[coin].add(remainChange);

                    if (changes[amount] == null || amountChange.getNumberOfCoins() < changes[amount].getNumberOfCoins()) {
                        changes[amount] = amountChange;
                    }
                }
            }

            return changes[amount];
        }

    }

    private static class Change {
        private int[] numberOfEachCoin;

        Change(int[] numberOfEachCoin) {
            this.numberOfEachCoin = numberOfEachCoin;
        }

        Change add(Change change) {
            int[] numberOfEachCoin = new int[this.numberOfEachCoin.length];

            for (int i = 0; i < numberOfEachCoin.length; i++) {
                numberOfEachCoin[i] = this.numberOfEachCoin[i] + change.numberOfEachCoin[i];
            }

            return new Change(numberOfEachCoin);
        }

        int getNumberOfCoins() {
            int numberOfCoins = 0;

            for (int numberOfCoin : numberOfEachCoin) {
                numberOfCoins += numberOfCoin;
            }

            return numberOfCoins;
        }

        int[] getNumberOfEachCoin() {
            return numberOfEachCoin;
        }
    }

}
