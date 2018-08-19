# Milkshakes!
Technical name: *milkshakes-app*

![Milkshakes!](https://github.com/gotonode/milkshakes-app/blob/master/docs/images/app.png)

**Notice!** When launching the app (hosted by Heroku) for the first time, it may take a few (2 < *n* < 8) seconds to launch.

Live version: https://milkshakes-app.herokuapp.com/

This is a very simple app (application, program, software) that you can use to view and edit recipes of milkshakes. A milkshake consists of a name, description and a collection (array) of ingredients. An ingredient has a name and a type associated with it.

Any and all feedback will be warmly welcome.

You can reach me via email: gotonode@outlook.com

Thank you in advance. :-)
___

Languages used: Java, HTML, CSS, JavaScript, SQL, XML

Technologies used: Spark, Thymeleaf, reCAPTCHA

Databases (SQL): SQLite, PostgreSQL

Services used: GitHub, Heroku

___

The database tables (SQL CREATE statements) are listed here.

For PostgreSQL, the column 'id' will be of type SERIAL.

Table: Recipe

```sqlite
CREATE TABLE "Recipe" (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name VARCHAR(32) CHECK(name <> "") NOT NULL UNIQUE,
  description VARCHAR(64) CHECK(description <> "") NOT NULL,
  isInitial BOOLEAN DEFAULT FALSE,
  createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  updateTime TIMESTAMP DEFAULT NULL
);
```

Table: Ingredient

```sqlite
CREATE TABLE "Ingredient" (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name VARCHAR(32) CHECK(name <> "") NOT NULL UNIQUE,
  unit CHAR(1) NOT NULL,
  isInitial BOOLEAN DEFAULT FALSE,
  createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  updateTime TIMESTAMP DEFAULT NULL
);
```

Table: Recipe_Ingredient

```sqlite
CREATE TABLE "Recipe_Ingredient" (
  recipe_id INT,
  ingredient_id INT,
  step INT CHECK(step >= 1) NOT NULL,
  amount FLOAT(2) NOT NULL,
  instructions VARCHAR(32),
  isInitial BOOLEAN DEFAULT FALSE,
  createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  updateTime TIMESTAMP DEFAULT NULL,
  PRIMARY KEY ("recipe_id", "ingredient_id"),
  FOREIGN KEY ("recipe_id") REFERENCES "Recipe"("id"),
  FOREIGN KEY ("ingredient_id") REFERENCES "Ingredient"("id")
);
```
