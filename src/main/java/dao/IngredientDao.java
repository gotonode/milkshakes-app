package dao;

import database.Database;
import domain.Ingredient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientDao implements Dao<Ingredient, Integer> {

    private final Database database;

    public IngredientDao(Database database) {
        this.database = database;
    }

    @Override
    public Ingredient findOne(Integer key) throws SQLException {

        try (Connection connection = database.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Ingredient WHERE id=?");

            preparedStatement.setInt(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Ingredient output = new Ingredient(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("unit").toCharArray()[0]);

                resultSet.close();

                return output;
            }

        }

        return null;
    }

    @Override
    public List<Ingredient> findAll() throws SQLException {

        try (Connection connection = database.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Ingredient");

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Ingredient> output = new ArrayList<>();

            while (resultSet.next()) {
                output.add(new Ingredient(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("unit").toCharArray()[0]));
            }

            return output;
        }
    }

    public List<Ingredient> findAllNotUsedByRecipe(int recipeId) throws SQLException {

        // TODO: Is this the right place for this method?
        try (Connection connection = database.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Ingredient WHERE id NOT IN (SELECT ingredient_id FROM Recipe_Ingredient WHERE Recipe_Ingredient.recipe_id = ?) ORDER BY Ingredient.name ASC");
            preparedStatement.setInt(1, recipeId);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Ingredient> output = new ArrayList<>();

            while (resultSet.next()) {
                output.add(new Ingredient(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("unit").toCharArray()[0]));
            }

            return output;
        }
    }

    public List<Ingredient> findAllWithCount() throws SQLException {

        // TODO: Is this the right place for this method?
        try (Connection connection = database.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT DISTINCT Ingredient.name, Ingredient.unit, Ingredient.id, (SELECT COUNT(*) FROM Recipe_Ingredient WHERE Recipe_Ingredient.ingredient_id = Ingredient.id) AS count FROM Ingredient LEFT JOIN Recipe_Ingredient ON Ingredient.id = Recipe_Ingredient.ingredient_id ORDER BY Ingredient.name ASC");

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Ingredient> output = new ArrayList<>();

            while (resultSet.next()) {

                Ingredient ingredient = new Ingredient(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("unit").toCharArray()[0]);

                ingredient.setCount(resultSet.getInt("count"));

                output.add(ingredient);
            }

            return output;
        }
    }

    @Override
    public Ingredient saveOrUpdate(Ingredient ingredient) throws SQLException {

        try (Connection connection = database.getConnection()) {

            if (ingredient.getId() == -1) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Ingredient (name, unit) VALUES (?, ?)");

                preparedStatement.setString(1, ingredient.getName());
                preparedStatement.setObject(2, ingredient.getUnit());

                preparedStatement.executeUpdate();
            }

        }
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        database.delete("DELETE FROM Recipe_Ingredient WHERE ingredient_id=?", key);
        database.delete("DELETE FROM Ingredient WHERE id=?", key);
    }

}
