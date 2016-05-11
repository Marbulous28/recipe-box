import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class RecipeTest{

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Recipe_instantiatesCorrectly_true(){
    Recipe newRecipe = new Recipe("Chicken Pot Pie", "take chicken add pot pie");
    assertEquals(true, newRecipe instanceof Recipe);
  }

  @Test
  public void getName_returnsCorrectName_String(){
    Recipe newRecipe = new Recipe("Chicken? Pot Pie?", "instructions unclear. Take chicken.");
    assertEquals("Chicken? Pot Pie?", newRecipe.getName());
  }

  @Test
  public void getInstructions_returnsCorrectInstructions_String(){
    Recipe newRecipe = new Recipe("Chicken? Pot Pie?", "instructions unclear. Take chicken.");
    assertEquals("instructions unclear. Take chicken.", newRecipe.getInstructions());
  }


  @Test
  public void all_emptyAtFirst(){
    assertEquals(Recipe.all().size(), 0);
  }

  @Test
  public void equals_returnTrueIfNamesAreSame() {
    Recipe firstRecipe = new Recipe("Chicken?", "a");
    Recipe secondRecipe = new Recipe("Chicken?", "a");
    assertTrue(firstRecipe.equals(secondRecipe));
  }

  @Test
  public void save_savesCorrectly(){
    Recipe saveRecipe = new Recipe("Paprika", "a");
    saveRecipe.save();
    assertEquals(1, Recipe.all().size());
  }

  @Test
  public void getId_returnsCorrectId(){
    Recipe newRecipe = new Recipe("Tumeric? Chicken!", "a");
    newRecipe.save();
    Recipe otherRecipe = Recipe.all().get(0);
    assertEquals(otherRecipe.getId(), newRecipe.getId());
  }

  @Test
  public void find_findsRecipeInDatabase_true() {
    Recipe newRecipe = new Recipe("Channa Masala", "a");
    newRecipe.save();
    Recipe foundRecipe = Recipe.find(newRecipe.getId());
    assertTrue(newRecipe.equals(foundRecipe));
  }

  @Test
  public void update_updatesRecipeInDatabase_true() {
    Recipe myRecipe = new Recipe("Orange Duck", "a");
    myRecipe.save();
    myRecipe.update("Fried Duck", "a");
    assertEquals("Fried Duck", Recipe.find(myRecipe.getId()).getName());
  }

  @Test
  public void delete_deletesRecipeInDatabase_true() {
    Recipe myRecipe = new Recipe("Bon Jovi Stew", "a");
    myRecipe.save();
    myRecipe.delete();
    assertEquals (0, Recipe.all().size());
  }

  @Test
  public void addIngredient_insertsIntoJoinTable() {
    Recipe myRecipe = new Recipe("Bon Jovi Stew", "a");
    myRecipe.save();
    Ingredient myIngredient = new Ingredient("Sauce");
    myIngredient.save();
    Ingredient foundIngredient = Ingredient.find(myIngredient.getId());
    myRecipe.addIngredients(foundIngredient.getId());
    List<Ingredient> ingredientList = myRecipe.getIngredients();
    assertEquals(ingredientList.get(0).getId(),foundIngredient.getId());

  }
}
