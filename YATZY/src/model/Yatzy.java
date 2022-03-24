package model;

import javafx.scene.control.Alert;
import projekt_algoritme_aflevering.PigGame;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import java.awt.*;
import java.sql.Array;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Random;

public class Yatzy {
    // Face values of the 5 dice.
    // 1 <= values[i] <= 6.
    private int[] values = new int[5];

    // Number of times the 5 dice have been thrown.
    // 0 <= throwCount <= 3.
    private int throwCount = 0;

    // Random number generator.
    private Random random = new Random();


    public Yatzy() {
        //
    }

    /**
     * Returns the 5 face values of the dice.
     */
    public int[] getValues() {
        return values;
    }

    /**
     * Sets the 5 face values of the dice. Pre: values contains 5 face values in
     * [1..6]. Note: This method is only meant to be used for test, and
     * therefore has package visibility.
     */
    void setValues(int[] values) {
        this.values = values;
    }

    /**
     * Returns the number of times the 5 dice has been thrown.
     */
    public int getThrowCount() {
        return throwCount;
    }

    /**
     * Resets the throw count.
     */
    public void resetThrowCount() {
        throwCount = 0;
        for (int i = 0; i < values.length; i++) {
            values[i] = 0;
        }
    }

    /**
     * Rolls the 5 dice. Only roll dice that are not hold. Pre: holds contain 5
     * boolean values.
     */
    public void throwDice(boolean[] holds) {
        for (int i = 0; i < values.length; i++) {
            if (!holds[i]) {
                values[i] = random.nextInt(1, 7);
            }
        }
        throwCount++;
    }

    // -------------------------------------------------------------------------

    /**
     * Returns all results possible with the current face values. The order of
     * the results is the same as on the score board. Note: This is an optional
     * method. Comment this method out, if you don't want use it.
     */
    public int[] getResults() {
        int[] results = new int[15];
        for (int i = 0; i <= 5; i++) {
            results[i] = this.sameValuePoints(i + 1);
        }
        results[6] = this.onePairPoints();
        results[7] = this.twoPairPoints();
        results[8] = this.threeSamePoints();
        results[9] = this.fourSamePoints();
        results[10] = this.fullHousePoints();
        results[11] = this.smallStraightPoints();
        results[12] = this.largeStraightPoints();
        results[13] = this.chancePoints();
        results[14] = this.yatzyPoints();

        return results;
    }

    // -------------------------------------------------------------------------

    // Returns an int[7] containing the frequency of face values.
    // Frequency at index v is the number of dice with the face value v, 1 <= v
    // <= 6.
    // Index 0 is not used.
    private int[] calcCounts() {
        int[] numberControl = new int[7];
        int temp = 0;
        for (int value : values) {
            temp = value;
            numberControl[temp]++;
        }
        return numberControl;
    }

    /**
     * Returns same-value points for the given face value. Returns 0, if no dice
     * has the given face value. Pre: 1 <= value <= 6;
     */
    public int sameValuePoints(int value) {
        int[] counts = calcCounts();
        int result = 0;
        for (int i = 0; i < counts.length; i++) {
            if (i == value) {
                result = counts[i] * i;
            }
        }
        return result;
    }

    /**
     * Returns points for one pair (for the face value giving highest points).
     * Returns 0, if there aren't 2 dice with the same face value.
     */
    public int onePairPoints() {
        int[] counts = calcCounts();
        int result = 0;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] >= 2) {
                result = i * 2;
            }
        }
        return result;
    }

    /**
     * Returns points for two pairs (for the 2 face values giving highest
     * points). Returns 0, if there aren't 2 dice with one face value and 2 dice
     * with a different face value.
     */
    public int twoPairPoints() {
        int[] counts = calcCounts();
        int resultat = 0;
        int par2 = 0;
        int par1 = 0;
        for (int i = 0; par1 == 0 && i < counts.length; i++) {
            if (counts[i] >= 2) {
                par1 = i * 2;
            }
        }
        for (int j = (par1 / 2) + 1; j < counts.length; j++) {
            if (counts[j] >= 2) {
                par2 = j * 2;
            }
        }
        if (par1 > 0 && par2 > 0) {
            resultat = par2 + par1;
        }

        return resultat;
    }

    /**
     * Returns points for 3 of a kind. Returns 0, if there aren't 3 dice with
     * the same face value.
     */
    public int threeSamePoints() {
        int[] counts = calcCounts();
        int result = 0;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] >= 3) {
                result = i * 3;
            }
        }
        return result;
    }


    /**
     * Returns points for 4 of a kind. Returns 0, if there aren't 4 dice with
     * the same face value.
     */
    public int fourSamePoints() {
        int[] counts = calcCounts();
        int result = 0;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] >= 4) {
                result = i * 4;
            }
        }
        return result;
    }


    /**
     * Returns points for full house. Returns 0, if there aren't 3 dice with one
     * face value and 2 dice a different face value.
     */
    public int fullHousePoints() {
        int[] counts = calcCounts();
        int resultialt = 0;
        int result2 = 0;
        int result1 = 0;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] == 3) {
                result1 = i * 3;
            }
        }
        for (int j = 0; j < counts.length; j++) {
            if (counts[j] == 2) {
                result2 = j * 2;
            }

        }
        if (result1 > 0 && result2 > 0) {
            resultialt = result2 + result1;
        }

        return resultialt;
    }

    /**
     * Returns points for small straight. Returns 0, if the dice are not showing
     * 1,2,3,4,5.
     */
    public int smallStraightPoints() {
        int[] counts = calcCounts();
        int points = 0;
        for (int i = 1; i < counts.length - 1; i++) {
            if (counts[i] != 1) {
                return 0;
            }
            points += i;
        }
        return points;
    }

    /**
     * Returns points for large straight. Returns 0, if the dice is not showing
     * 2,3,4,5,6.
     */
    public int largeStraightPoints() {
        int[] counts = calcCounts();
        int points = 0;
        for (int i = 2; i < counts.length; i++) {
            if (counts[i] != 1) {
                return 0;
            }
            points += i;
        }
        return points;
    }

    /**
     * Returns points for chance.
     */
    public int chancePoints() {
        int[] count = calcCounts();
        int points = 0;
        for (int i = 0; i < count.length; i++) {
            points += (count[i] * i);
        }
        return points;
    }

    /**
     * Returns points for yatzy. Returns 0, if there aren't 5 dice with the same
     * face value.
     */
    public int yatzyPoints() {
        int[] counts = calcCounts();
        int result = 0;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] == 5) {
                result = 50;
            }
        }
        return result;
    }

}
