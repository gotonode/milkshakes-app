package dao;

import database.Database;
import domain.Recipe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDao implements Dao<Recipe, Integer> {

    private final Database database;

    public RecipeDao(Database database) {
        this.database = database;
    }

    @Override
    public Recipe findOne(Integer key) throws SQLException {

        try (Connection connection = database.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Recipe WHERE id=?");

            preparedStatement.setInt(1, key);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                Recipe output = new Recipe(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"));

                resultSet.close();

                return output;
            }
        }

        return null;
    }

    public Recipe findOneByName(String name) throws SQLException {
        try (Connection connection = database.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Recipe WHERE name=?");

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                Recipe output = new Recipe(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"));

                resultSet.close();

                return output;
            }
        }

        return null;
    }

    @Override
    public List<Recipe> findAll() throws SQLException {

        try (Connection connection = database.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Recipe ORDER BY name ASC");

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Recipe> output = new ArrayList<>();

            while (resultSet.next()) {
                output.add(new Recipe(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description")));
            }

            resultSet.close();

            return output;
        }
    }

    @Override
    public Recipe saveOrUpdate(Recipe recipe) throws SQLException {

        try (Connection connection = database.getConnection()) {

            if (recipe.getId() == -1) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Recipe (name, description) VALUES (?, ?)");

                preparedStatement.setString(1, recipe.getName());
                preparedStatement.setString(2, recipe.getDescription());

                preparedStatement.executeUpdate();
            }
        }

        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        database.delete("DELETE FROM Recipe_Ingredient WHERE recipe_id=?", key);
        database.delete("DELETE FROM Recipe WHERE id=?", key);
    }

}
