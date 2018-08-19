/* This SQL file has been stylized for human reading, but it is also valid for the system. */

/* Used by "milkshakes-app.herokuapp.com" database engine. */

BEGIN TRANSACTION;

DELETE FROM "recipe_ingredient";
DELETE FROM "ingredient";
DELETE FROM "recipe";

INSERT INTO "recipe" (id,name,description,isInitial) VALUES 
  (1,'Strawberry-Lime Milkshake', 'Contains strawberries and maybe a lime.','TRUE'),
  (2,'The "Icarian Flight" Shake', 'Drink with caution and avoid jumping.','TRUE'),
  (3,'The Atomic Blast', 'May irritate a little.','TRUE'),
  (4,'SQL Injection Shake', 'Just don''t. Please. Pretty please with a cherry on top.','TRUE'),
  (5,'One with Everything', 'For those who just cannot choose.','TRUE'),
  (6,'Blueberry-Strawberry Oatshake', 'Milkshake for (almost) everyone.','TRUE'),
  (7,'Zero-calorie Voidshake', 'Contains absolutely nothing.','TRUE'),
  (8,'Pineapple Milkshake', 'Because pineapples.','TRUE');

INSERT INTO "ingredient" (id,name,unit,isInitial) VALUES 
  (1,'milk', 'v','TRUE'),
  (2,'strawberries', 'p','TRUE'),
  (3,'limes', 'p','TRUE'),
  (4,'chocolate syrup', 'v','TRUE'),
  (5,'whipped cream', 'v','TRUE'),
  (6,'cookies', 'p','TRUE'),
  (7,'energy drink', 'v','TRUE'),
  (8,'essence of Cthulhu', 'o','TRUE'),
  (9,'uranium-232', 'm','TRUE'),
  (10,'unsanitized input', 'p','TRUE'),
  (11,'peanuts', 'p','TRUE'),
  (12,'superglue', 'v','TRUE'),
  (13,'blueberries', 'p','TRUE'),
  (14,'rasberries', 'p','TRUE'),
  (15,'hydrophobic water', 'v','TRUE'),
  (16,'cherries', 'p','TRUE'),
  (17,'vanilla ice cream', 'v','TRUE'),
  (18,'banana', 'p','TRUE'),
  (19,'oatmilk', 'v','TRUE'),
  (20,'pineapple', 'k','TRUE');

INSERT INTO "recipe_ingredient" (recipe_id,ingredient_id,step,amount,instructions,isInitial) VALUES 
  (1,1,1,0.2,'First add milk.','TRUE'),
  (1,2,2,5,'Drop in some strawberries','TRUE'),
  (1,3,3,1,'Just one lime.','TRUE'),
  (1,17,4,1.1,'This needs some ice cream.','TRUE'),
  (2,1,1,0.2,'Always start with milk. It''s a milkshake after all.','TRUE'),
  (2,7,2,5,'Pour in your favorite cheap energy drink.','TRUE'),
  (2,17,3,2.5,'And to finish it off, ice cream.','TRUE'),
  (3,9,1,0.056,'Careful now!','TRUE'),
  (3,1,2,0.2,'After it doesn''t glow so much, add the milk.','TRUE'),
  (3,17,3,1,'Add some ice cream, to make it into a milkshake.','TRUE'),
  (4,10,1,1,'It only takes one.','TRUE'),
  (4,16,2,1,'Add the cherry I promised.','TRUE'),
  (5,1,1,1,'','TRUE'),
  (5,2,2,1,'','TRUE'),
  (5,3,3,1,'','TRUE'),
  (5,4,4,1,'','TRUE'),
  (5,5,5,1,'','TRUE'),
  (5,6,6,1,'','TRUE'),
  (5,7,7,1,'','TRUE'),
  (5,8,8,1,'','TRUE'),
  (5,9,9,1,'','TRUE'),
  (5,10,10,1,'','TRUE'),
  (5,11,11,1,'','TRUE'),
  (5,12,12,1,'','TRUE'),
  (5,13,13,1,'','TRUE'),
  (5,14,14,1,'','TRUE'),
  (5,15,15,1,'','TRUE'),
  (5,16,16,1,'','TRUE'),
  (5,17,17,1,'','TRUE'),
  (5,18,18,1,'','TRUE'),
  (5,19,19,1,'','TRUE'),
  (5,20,20,1,'','TRUE'),
  (6,13,1,100,'Add blueberries.','TRUE'),
  (6,2,2,20,'Add strawberries.','TRUE'),
  (6,19,3,0.5,'Add oatmilk.','TRUE'),
  (8,20,1,8,'Feel free to use a bit less pineapple.','TRUE'),
  (8,17,2,1,'Pineapple ice cream would''ve been even better.','TRUE');

COMMIT;
