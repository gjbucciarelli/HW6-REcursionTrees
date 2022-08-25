 package edu.miracosta.cs113.change;

 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.PrintWriter;
 import java.util.HashSet;

 /**
 * ChangeCalculator : Class containing the recursive method calculateChange, which determines and prints all
 * possible coin combinations representing a given monetary value in cents.
 *
 * Problem derived from Koffman & Wolfgang's Data Structures: Abstraction and Design Using Java (2nd ed.):
 * Ch. 5, Programming Project #7, pg. 291.
 *
 * NOTE: An additional method, printCombinationsToFile(int), has been added for the equivalent tester file to
 * verify that all given coin combinations are unique.
 */
public class ChangeCalculator {
    // Combination order not relevent, using a HashSet
    private static HashSet<String> combos = new HashSet<>();
    public static File outputFile = new File ("src/edu/miracosta/cs113/change/CoinCombinations.txt");

    /**
     * Wrapper method for determining all possible unique combinations of quarters, dimes, nickels, and pennies that
     * equal the given monetary value in cents.
     *
     * In addition to returning the number of unique combinations, this method will print out each combination to the
     * console. The format of naming each combination is up to the user, as long as they adhere to the expectation
     * that the coins are listed in descending order of their value (quarters, dimes, nickels, then pennies). Examples
     * include "1Q 2D 3N 4P", and "[1, 2, 3, 4]".
     *
     * @param cents a monetary value in cents
     * @return the total number of unique combinations of coins of which the given value is comprised
     */
    public static int calculateChange(int cents) {
        combos.clear();
        // Start it off with pennies
        calculateChange(0,0,0,cents);
        return combos.size();

    }

    private static int calculateChange(int quarters, int dimes, int nickels, int pennies)
    {
        String currCombo = "[" + quarters + "," + dimes + "," + nickels + "," + pennies + "]";

            combos.add(currCombo);
       // System.out.println(currCombo);

        // adjust for nickel combos
           if (pennies >= 5)
               calculateChange(quarters, dimes, nickels + 1, pennies -5);
           // adjust for dime combinations
           if (pennies >= 10)
               calculateChange(quarters, dimes + 1, nickels, pennies - 10);
           // adjust for quarter combinations
           if (pennies >= 25)
               calculateChange(quarters + 1, dimes, nickels, pennies - 25);

           return combos.size();
    }

    /**
     * Calls upon calculateChange(int) to calculate and print all possible unique combinations of quarters, dimes,
     * nickels, and pennies that equal the given value in cents.
     *
     * Similar to calculateChange's function in printing each combination to the console, this method will also
     * produce a text file named "CoinCombinations.txt", writing each combination to separate lines.
     *
     * @param cents a monetary value in cents
     */
    public static void printCombinationsToFile(int cents) {
            calculateChange(cents);

            try{
                PrintWriter writer = new PrintWriter(outputFile);

                for (String combo : combos)
                {
                    writer.append(combo);
                    writer.append("\n");
                }
                // Flush and close :)
                writer.flush();
                writer.close();
            } catch (FileNotFoundException e)
            {
                System.out.println("Error locating file: " + e);
            }
    }

} // End of class ChangeCalculator