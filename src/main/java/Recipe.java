import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Recipe{
  private int id;
  private String recipe_name;
  private String recipe_instructions;

  public Recipe(String name, String instructions){
    recipe_name = name;
    recipe_instructions = instructions;
  }

  public int getId(){
    return id;
  }

  public String getName(){
    return recipe_name;
  }

  public String getInstructions(){
    return recipe_instructions;
  }

  public void addIngredients(int ingredient_id){
    String sql = "INSERT INTO ingredients_recipes (recipe_id, ingredient_id) VALUES (:recipe_id, :ingredient_id)";
    try (Connection con = DB.sql2o.open()){
      con.createQuery(sql, true)
      .addParameter("ingredient_id", ingredient_id)
      .addParameter("recipe_id", this.id)
      .executeUpdate();
    }
  }

  public List<Ingredient> getIngredients(){
    String sql = "SELECT ingredients.* FROM recipes JOIN ingredients_recipes ON (recipes.id = ingredients_recipes.recipe_id) JOIN ingredients ON (ingredients_recipes.ingredient_id = ingredients.id) WHERE recipes.id = :id";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).addParameter("id", this.id).executeAndFetch(Ingredient.class);
    }
  }

  public static List<Recipe> all() {
    String sql = "SELECT * FROM recipes";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Recipe.class);
    }
  }

  @Override
  public boolean equals(Object otherRecipe) {
    if (!(otherRecipe instanceof Recipe)) {
      return false;
    } else {
      Recipe newRecipe = (Recipe) otherRecipe;
      return this.getName().equals(newRecipe.getName()) &&
             this.getInstructions().equals(newRecipe.getInstructions());
    }
  }

  public void save(){
    String sql = "INSERT INTO recipes (recipe_name, recipe_instructions) VALUES (:recipe_name, :recipe_instructions)";
    try (Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true)
        .addParameter("recipe_name", this.recipe_name)
        .addParameter("recipe_instructions", this.recipe_instructions)
        .executeUpdate()
        .getKey();
    }
  }

  public static Recipe find(int id){
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM recipes WHERE id = :id";
      Recipe recipe = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Recipe.class);
      return recipe;
    }
  }

  public void update(String recipe_name, String recipe_instructions) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE recipes SET recipe_name = :recipe_name, recipe_instructions = :recipe_instructions WHERE id =:id";
      con.createQuery(sql)
      .addParameter("recipe_name", recipe_name)
      .addParameter("recipe_instructions", recipe_instructions)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM recipes WHERE id = :id";
      con.createQuery(sql)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

}
