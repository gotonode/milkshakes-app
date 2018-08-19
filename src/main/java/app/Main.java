package app;

import helper.*;
import dao.*;
import database.*;
import domain.*;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import java.io.IOException;

public class Main {

    // The logging functionality of this app causes a lot of database queries.
    //
    // But since this is a learning app, gathered data from logs is more
    // important than high-grade performance.
    private static LogClass logClass;

    private static Database database;

    // TODO: Invent better names?
    private static InputHelper inputHelper;
    private static NetworkHelper networkHelper;

    // DAO
    private static RecipeDao recipeDao;
    private static IngredientDao ingredientDao;
    private static Recipe_IngredientDao recipe_IngredientDao;

    // TODO: Make this a constant?
    private static final String CONNECTION_PREFIX = "jdbc:sqlite:";

    public static void main(String[] args) {

        logClass = new LogClass();

        logClass.attemptStart(args);

        try {
            inputHelper = new InputHelper();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final String port = System.getenv("PORT");

        if (port != null) {
            Spark.port(Integer.valueOf(port));
        }

        Spark.staticFileLocation("/public");

        database = new Database(CONNECTION_PREFIX + inputHelper.getDatabaseFilePath());

        // Initialize DAO
        recipeDao = new RecipeDao(database);
        ingredientDao = new IngredientDao(database);
        recipe_IngredientDao = new Recipe_IngredientDao(database);

        logClass.started();

        setHandlers();
    }

    private static void setHandlers() {

        setGetHandlers(); // All the HTTP GET handlers.
        setPostHandlers(); // All the HTTP POST handlers.
    }

    private static void setGetHandlers() {

        Spark.get("/", (Request req, Response res) -> {
            HashMap map = new HashMap<>();
            List<Recipe> recipes = recipeDao.findAll();

            map.put("recipes", recipes);
            map.put("disclaimer", req.cookie("disclaimer"));
            map.put("version", System.getenv("SOURCE_VERSION"));

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.get("/view/:id", (Request req, Response res) -> {
            HashMap map = new HashMap<>();

            int id = Integer.parseInt(req.params(":id"));

            Recipe recipe = recipeDao.findOne(id);

            List<Ingredient> ingredientsNotInUse = ingredientDao.findAllNotUsedByRecipe(id);

            List<Step> steps = recipe_IngredientDao.getStepsByRecipeId(id);

            int nextStep;

            if (steps.isEmpty()) {
                nextStep = 1;

            } else {
                nextStep = steps.get(0).getStep();

                for (Step step : steps) {
                    if (step.getStep() > nextStep) {
                        nextStep = step.getStep();
                    }
                }

                nextStep++;
            }

            map.put("recipe", recipe);
            map.put("steps", steps);
            map.put("nextStep", nextStep);
            map.put("ingredients", ingredientsNotInUse);
            map.put("disclaimer", req.cookie("disclaimer"));
            map.put("version", System.getenv("SOURCE_VERSION"));

            return new ModelAndView(map, "view");
        }, new ThymeleafTemplateEngine());

        Spark.get("/ingredients", (Request req, Response res) -> {
            HashMap map = new HashMap<>();

            // A special-case method that'll return all the ingredients
            // alongside information on how many recipes each ingredient
            // is used on.
            List<Ingredient> ingredientsWithCount = ingredientDao.findAllWithCount();

            map.put("units", Ingredient.getUnitStrings());
            map.put("ingredientsWithCount", ingredientsWithCount);
            map.put("disclaimer", req.cookie("disclaimer"));
            map.put("version", System.getenv("SOURCE_VERSION"));

            return new ModelAndView(map, "ingredients");
        }, new ThymeleafTemplateEngine());

        Spark.get("/database", (Request req, Response res) -> {
            HashMap map = new HashMap<>();

            map.put("sql", inputHelper.readResetFile("<br />"));
            map.put("disclaimer", req.cookie("disclaimer"));
            map.put("version", System.getenv("SOURCE_VERSION"));

            return new ModelAndView(map, "database");
        }, new ThymeleafTemplateEngine());
    }

    private static void setPostHandlers() {

        Spark.post("/reset", (Request req, Response res) -> {

//            if (networkHelper.verify(req.queryParams("g-recaptcha-response"), req.ip())) {
//                 TODO: This is still incomplete.
//            }
            database.executeTransaction(inputHelper.readResetFile());

            logClass.reset();

            res.redirect("/");

            return null;
        }, new ThymeleafTemplateEngine());

        Spark.post("/add/recipe", (Request req, Response res) -> {

            String name = req.queryParams("name").trim();
            String description = req.queryParams("description").trim();

            Recipe recipe = new Recipe(-1, name, description);

            recipeDao.saveOrUpdate(recipe);

            logClass.addRecipe(recipe);

            //res.redirect("/");
            int id = recipeDao.findOneByName(name).getId();
            res.redirect("/view/" + id);

            return null;
        }, new ThymeleafTemplateEngine());

        Spark.post("/delete/recipe", (Request req, Response res) -> {

            int recipeId = Integer.parseInt(req.queryParams("id").trim());

            Recipe recipe = recipeDao.findOne(recipeId);

            recipeDao.delete(recipeId);

            logClass.deleteRecipe(recipe);

            res.redirect("/");

            return null;
        }, new ThymeleafTemplateEngine());

        Spark.post("/add/ingredient", (Request req, Response res) -> {

            String name = req.queryParams("name").trim();
            char unit = req.queryParams("unit").trim().charAt(0);

            Ingredient ingredient = new Ingredient(-1, name, unit);

            ingredientDao.saveOrUpdate(ingredient);

            logClass.addIngredient(ingredient);

            res.redirect("/ingredients");

            return null;
        }, new ThymeleafTemplateEngine());

        Spark.post("/delete/ingredient", (Request req, Response res) -> {

            int ingredientId = Integer.parseInt(req.queryParams("id").trim());

            Ingredient ingredient = ingredientDao.findOne(ingredientId);

            ingredientDao.delete(ingredientId);

            logClass.deleteIngredient(ingredient);

            res.redirect("/ingredients");

            return null;
        }, new ThymeleafTemplateEngine());

        Spark.post("/add/ingredientToRecipe", (Request req, Response res) -> {

            int ingredientId = Integer.parseInt(req.queryParams("ingredient").trim());
            int recipeId = Integer.parseInt(req.queryParams("recipe").trim());

            int step = Integer.parseInt(req.queryParams("step").trim());
            double amount = Double.parseDouble(req.queryParams("amount").trim());

            String instructions = req.queryParams("instructions").trim();

            Recipe recipe = recipeDao.findOne(recipeId);
            Ingredient ingredient = ingredientDao.findOne(ingredientId);

            recipe_IngredientDao.addIngredientToRecipe(recipeId, ingredientId, step, amount, instructions);

            logClass.addIngredientToRecipe(recipe, ingredient, step, amount, instructions);

            res.redirect("/view/" + recipeId);

            return null;
        }, new ThymeleafTemplateEngine());

        Spark.post("/delete/ingredientFromRecipe", (Request req, Response res) -> {

            int recipeId = Integer.parseInt(req.queryParams("id").trim());
            int ingredientId = Integer.parseInt(req.queryParams("id2").trim());

            Recipe recipe = recipeDao.findOne(recipeId);
            Ingredient ingredient = ingredientDao.findOne(ingredientId);

            recipe_IngredientDao.delete(recipeId, ingredientId);

            logClass.deleteIngredientFromRecipe(recipe, ingredient);

            res.redirect("/view/" + recipeId);

            return null;
        }, new ThymeleafTemplateEngine());
    }

}
