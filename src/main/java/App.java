import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.ArrayList;
import static spark.Spark.*;
import java.util.List;


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
      model.put("tags", Tag.all());
      model.put("template","templates/addRecipe.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/submitRecipe", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String recipe_name = request.queryParams("recipe_name");
      String text_description = request.queryParams("text_description");
      int ingredient_id = Integer.parseInt(request.queryParams("add-ingredient"));
      int tag_id = Integer.parseInt(request.queryParams("add-tag"));
      Recipe newRecipe = new Recipe(recipe_name, text_description);
      newRecipe.save();
      newRecipe.addIngredients(ingredient_id);
      newRecipe.addTag(tag_id);
      response.redirect("/recipes");
      return null;
    });

    get("/addTag", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/addTag.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

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

    post("/submitTag", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String tagName = request.queryParams("tag_name");
      Tag newTag = new Tag(tagName);
      newTag.save();
      response.redirect("/recipes");
      return null;
    });

    post("/submitReview/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Recipe newRecipe = Recipe.find(Integer.parseInt(request.params("id")));
      String userName = request.queryParams("user-name");
      String reviewDescription = request.queryParams("review-desc");
      int stars = Integer.parseInt(request.queryParams("stars"));
      Review newReview= new Review(stars, reviewDescription, userName, newRecipe.getId());
      newReview.save();
      response.redirect("/recipe/" + newRecipe.getId());
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
      Recipe newRecipe = Recipe.find(Integer.parseInt(request.params("id")));
      List<Ingredient> ingredientList = newRecipe.getIngredients();
      List<Tag> tagList = newRecipe.getTags();
      List<Review> reviewList = newRecipe.getReview();
      model.put("reviewList", reviewList);
      model.put("ingredientList", ingredientList);
      model.put("tagList", tagList);
      model.put("recipe", newRecipe);
      model.put("template","templates/recipe.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/addReview/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Recipe newRecipe = Recipe.find(Integer.parseInt(request.params("id")));
      List<Ingredient> ingredientList = newRecipe.getIngredients();
      List<Tag> tagList = newRecipe.getTags();
      Boolean confirmReviewForm = true;
      model.put("ingredientList", ingredientList);
      model.put("tagList", tagList);
      model.put("recipe", newRecipe);
      model.put("confirmReviewForm", confirmReviewForm);
      model.put("template","templates/recipe.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/addNewTag/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Recipe newRecipe = Recipe.find(Integer.parseInt(request.params("id")));
      List<Ingredient> ingredientList = newRecipe.getIngredients();
      List<Tag> tagList = newRecipe.getTags();
      List<Tag> allTags = Tag.all();
      Boolean confirmAddNewTag = true;
      model.put("allTags", allTags);
      model.put("ingredientList", ingredientList);
      model.put("tagList", tagList);
      model.put("recipe", newRecipe);
      model.put("confirmAddNewTag", confirmAddNewTag);
      model.put("template","templates/recipe.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/submitNewTag/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Recipe newRecipe = Recipe.find(Integer.parseInt(request.params("id")));
      int newTagID = Integer.parseInt(request.queryParams("new-tag"));
      newRecipe.addTag(newTagID);
      response.redirect("/recipe/" + newRecipe.getId());
      return null;
    });
  }
}
