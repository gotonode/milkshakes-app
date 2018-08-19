package domain;

public class Recipe_Ingredient {

    private final int recipeId, ingredientId;
    private final int step;
    private final double amount;
    private final String instructions;

    public Recipe_Ingredient(int recipeId, int ingredientId, int step, double amount, String instructions) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.step = step;
        this.amount = amount;
        this.instructions = instructions;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public int getStep() {
        return step;
    }

    public double getAmount() {
        return amount;
    }

    public String getInstructions() {
        return instructions;
    }

    @Override
    public String toString() {
        return "Recipe_Ingredient{" + "recipeId=" + recipeId + ", ingredientId=" + ingredientId + ", step=" + step + ", amount=" + amount + ", instructions=" + instructions + '}';
    }
}
