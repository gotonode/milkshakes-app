package domain;

import java.util.HashMap;
import java.util.Map;

public class Ingredient extends Item {

    // TODO: Override toString -method.
    private final char unit;
    private final String unitString;

    private int count; // Optional.

    private static HashMap<Character, String> unitStrings;

    public Ingredient(int id, String name, char unit) {
        super(id, name);
        this.unit = unit;
        this.unitString = getUnitString(this.unit);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public char getUnit() {
        return unit;
    }

    public String getUnitString() {
        return unitString;
    }

    public static String getUnitString(char unit) {
        return getUnitStrings().getOrDefault(unit, "unknown");
    }

    public static Map<Character, String> getUnitStrings() {

        if (unitStrings == null) {

            // The first time this is called, the values will be cached.
            unitStrings = new HashMap<>();

            unitStrings.put('v', "liters");      // V = volume
            unitStrings.put('p', "pieces");      // P = pieces
            unitStrings.put('m', "kilograms");   // M = mass
            unitStrings.put('o', "other");       // O = other
        }

        return unitStrings;
    }
}
