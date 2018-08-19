package app;

import domain.Ingredient;
import domain.Recipe;
import java.util.Arrays;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

// Is this class necessary? Perhaps too much separation of concern?
public class LogClass {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());
    private static final String KEYWORD = "MAPP"; // Used to quickly find these items.

    // This is an inner class, usable only within this context.
    public class CustomFormatter extends Formatter {

        @Override
        public String format(LogRecord record) {

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(record.getLevel());
            stringBuilder.append(" (");
            stringBuilder.append(KEYWORD);
            stringBuilder.append("): ");
            stringBuilder.append(record.getMessage());
            stringBuilder.append(System.getProperty("line.separator"));

            return stringBuilder.toString();
        }
    }

    public LogClass() {
        for (Handler handler : LOG.getParent().getHandlers()) {
            LOG.getParent().removeHandler(handler);
        }

        CustomFormatter customFormatter = new CustomFormatter();
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(customFormatter);

        LOG.addHandler(consoleHandler);
    }

    public void attemptStart(String[] args) {

        if (args.length != 0) {
            LOG.log(Level.INFO, "Attempting to start with arguments: " + Arrays.toString(args));
        } else {
            LOG.log(Level.INFO, "Attempting to start...");
        }
    }

    public void started() {
        LOG.log(Level.INFO, "Started!");
    }

    public void reset() {
        LOG.log(Level.SEVERE, "Database reset done!");
    }

    public void addIngredient(Ingredient ingredient) {
        LOG.log(Level.INFO, "New ingredient '" + ingredient.getName() + "', '" + ingredient.getUnitString() + "'");
    }

    public void deleteIngredient(Ingredient ingredient) {
        LOG.log(Level.INFO, "Delete ingredient " + ingredient.getId() + " ('" + ingredient.getName() + "')");
    }

    public void addRecipe(Recipe recipe) {
        LOG.log(Level.INFO, "New recipe '" + recipe.getName() + "', '" + recipe.getDescription() + "'");
    }

    public void deleteRecipe(Recipe recipe) {
        LOG.log(Level.INFO, "Delete recipe " + recipe.getId() + " ('" + recipe.getName() + "')");
    }

    public void addIngredientToRecipe(Recipe recipe, Ingredient ingredient, int step, double amount, String instructions) {

        // We put String data types into quotes; int and double are not enclosed.
        LOG.log(Level.INFO, "Add ingredient " + ingredient.getId() + " ('" + ingredient.getName() + "') to recipe " + recipe.getId() + " ('" + recipe.getName() + "'), step=" + step + ", amount=" + amount + ", instructions='" + instructions + "'");
    }

    public void deleteIngredientFromRecipe(Recipe recipe, Ingredient ingredient) {
        LOG.log(Level.INFO, "Delete ingredient " + ingredient.getId() + " ('" + ingredient.getName() + "') from recipe " + recipe.getId() + " ('" + recipe.getName() + "')");
    }

}
