package dao;

import database.Database;
import domain.Recipe_Ingredient;
import domain.Step;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Recipe_IngredientDao implements Dao<Recipe_Ingredient, Integer> {

    private final Database database;

    public Recipe_IngredientDao(Database database) {
        this.database = database;
    }

    @Override
    public Recipe_Ingredient findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Step> getStepsByRecipeId(Integer recipeKey) throws SQLException {

        try (Connection connection = database.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Ingredient.name, Ingredient.unit, Ingredient.id AS ingredientId, Recipe_Ingredient.step, Recipe_Ingredient.amount, Recipe_Ingredient.instructions FROM Recipe_Ingredient INNER JOIN Ingredient ON Recipe_Ingredient.ingredient_id = Ingredient.id WHERE Recipe_Ingredient.recipe_id = ? ORDER BY Recipe_Ingredient.step ASC;");

            preparedStatement.setInt(1, recipeKey);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Step> output = new ArrayList<>();

            while (resultSet.next()) {
                Step step = new Step(resultSet.getString("name"),
                        resultSet.getInt("step"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("unit").toCharArray()[0], // TODO: Is this the best way to do this?
                        resultSet.getInt("ingredientId"),
                        resultSet.getString("instructions"));

                output.add(step);
            }

            resultSet.close();

            preparedStatement.close();

            return output;

        }
    }

    @Override
    public List<Recipe_Ingredient> findAll() throws SQLException {

        try (Connection connection = database.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Recipe_Ingredient");

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Recipe_Ingredient> output = new ArrayList<>();

            while (resultSet.next()) {
                output.add(new Recipe_Ingredient(
                        resultSet.getInt("recipe_id"),
                        resultSet.getInt("ingredient_id"),
                        resultSet.getInt("step"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("instructions")));
            }

            resultSet.close();

            return output;
        }
    }

    @Override
    public Recipe_Ingredient saveOrUpdate(Recipe_Ingredient object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void delete(int recipeId, int ingredientId) throws SQLException {
        database.delete("DELETE FROM Recipe_Ingredient WHERE recipe_id=? AND ingredient_id=?", new int[]{recipeId, ingredientId});
    }

    public void addIngredientToRecipe(int recipeId, int ingredientId, int step, double amount, String instructions) throws SQLException {

        try (Connection connection = database.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Recipe_Ingredient (recipe_id, ingredient_id, step, amount, instructions) VALUES (?, ?, ?, ?, ?)");

            preparedStatement.setInt(1, recipeId);
            preparedStatement.setInt(2, ingredientId);
            preparedStatement.setInt(3, step);
            preparedStatement.setDouble(4, amount);
            preparedStatement.setString(5, instructions);

            preparedStatement.executeUpdate();
        }
    }

}
