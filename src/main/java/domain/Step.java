package domain;

public class Step {
    // TODO: Figure out a better name for this class.

    private final String name;
    private final int step;
    private final double amount;
    private final char unit;
    private final String unitString;
    private final int ingredientId;
    private final String instructions;

    public Step(String name, int step, double amount, char unit, int ingredientId, String instructions) {
        this.name = name;
        this.step = step;
        this.amount = amount;
        this.unit = unit;
        this.ingredientId = ingredientId;

        // Only cache this if the intent is never to change the unit
        // inside the instance, or recache as needed.
        this.unitString = Ingredient.getUnitString(this.unit); // TODO: Should we really cache this value?
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public int getStep() {
        return step;
    }

    public double getAmount() {
        return amount;
    }

    public char getUnit() {
        return unit;
    }

    public String getUnitString() {
        return unitString;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    @Override
    public String toString() {
        return "Step{" + "name=" + name + ", step=" + step + ", amount=" + amount + ", unit=" + unit + ", unitString=" + unitString + ", ingredientId=" + ingredientId + ", instructions=" + instructions + '}';
    }

}
