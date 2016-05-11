import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class IngredientTest{

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Ingredient_instantiatesCorrectly_true(){
    Ingredient newIngredient = new Ingredient("Pepper");
    assertEquals(true, newIngredient instanceof Ingredient);
  }

  @Test
  public void getName_returnsCorrectName_String(){
    Ingredient newIngredient = new Ingredient("Pepper");
    assertEquals("Pepper", newIngredient.getName());
  }

  @Test
  public void all_emptyAtFirst(){
    assertEquals(Ingredient.all().size(), 0);
  }

  @Test
  public void equals_returnTrueIfNamesAreSame() {
    Ingredient firstIngredient = new Ingredient("Salt");
    Ingredient secondIngredient = new Ingredient("Salt");
    assertTrue(firstIngredient.equals(secondIngredient));
  }

  @Test
  public void save_savesCorrectly(){
    Ingredient saveIngredient = new Ingredient("Paprika");
    saveIngredient.save();
    assertEquals(1, Ingredient.all().size());
  }

  @Test
  public void getId_returnsCorrectId(){
    Ingredient newIngredient = new Ingredient("allspice");
    newIngredient.save();
    Ingredient otherIngredient = Ingredient.all().get(0);
    assertEquals(otherIngredient.getId(), newIngredient.getId());
  }

  @Test
  public void find_findsIngredientInDatabase_true() {
    Ingredient newIngredient = new Ingredient("Pizaz");
    newIngredient.save();
    Ingredient foundIngredient = Ingredient.find(newIngredient.getId());
    assertTrue(newIngredient.equals(foundIngredient));
  }

  @Test
  public void update_updatesIngredientInDatabase_true() {
    Ingredient myIngredient = new Ingredient("Cinnamon");
    myIngredient.save();
    myIngredient.update("Sugar");
    assertEquals("Sugar", Ingredient.find(myIngredient.getId()).getName());
  }

  @Test
  public void delete_deletesIngredientInDatabase_true() {
    Ingredient myIngredient = new Ingredient("Cinnamon");
    myIngredient.save();
    myIngredient.delete();
    assertEquals (0, Ingredient.all().size());
  }
}
