import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.ArrayList;
import static spark.Spark.*;


public class App{
  public static void main(String[] args){
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/addRecipe", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("ingredients", Ingredient.all());
      model.put("template","templates/addRecipe.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/submitRecipe", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String recipe_name = request.queryParams("recipe_name");
      String text_description = request.queryParams("text_description");
      int ingredient_id = Integer.parseInt(request.queryParams("add-ingredient"));
      Recipe newRecipe = new Recipe(recipe_name, text_description);
      newRecipe.save();
      newRecipe.addIngredients(ingredient_id);
      response.redirect("/recipes");
      return null;
    });

    get("/addIngredient", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template","templates/addIngredient.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/submitIngredient", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String ingredientName = request.queryParams("ingredient_name");
      Ingredient newIngredient = new Ingredient(ingredientName);
      newIngredient.save();
      response.redirect("/ingredients");
      return null;
    });

    get("/ingredients", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template","templates/ingredients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipes", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template","templates/recipes.vtl");
      model.put("recipes", Recipe.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipe/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template","templates/recipe.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
