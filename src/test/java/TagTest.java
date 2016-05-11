import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class TagTest{

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Tag_instantiatesCorrectly_true(){
    Tag newTag = new Tag("Cajun?");
    assertEquals(true, newTag instanceof Tag);
  }

  @Test
  public void getName_returnsCorrectName_String(){
    Tag newTag = new Tag("Cajun");
    assertEquals("Cajun", newTag.getName());
  }

  @Test
  public void all_emptyAtFirst(){
    assertEquals(Tag.all().size(), 0);
  }

  @Test
  public void equals_returnTrueIfNamesAreSame() {
    Tag firstTag = new Tag("Cajun");
    Tag secondTag = new Tag("Cajun");
    assertTrue(firstTag.equals(secondTag));
  }

  @Test
  public void save_savesCorrectly(){
    Tag saveTag = new Tag("Thai");
    saveTag.save();
    assertEquals(1, Tag.all().size());
  }

  @Test
  public void getId_returnsCorrectId(){
    Tag newTag = new Tag("Thai");
    newTag.save();
    Tag otherTag = Tag.all().get(0);
    assertEquals(otherTag.getId(), newTag.getId());
  }

  @Test
  public void find_findsTagInDatabase_true() {
    Tag newTag = new Tag("Thai");
    newTag.save();
    Tag foundTag = Tag.find(newTag.getId());
    assertTrue(newTag.equals(foundTag));
  }

  @Test
  public void update_updatesTagInDatabase_true() {
    Tag myTag = new Tag("Mexican");
    myTag.save();
    myTag.update("Japanese");
    assertEquals("Japanese", Tag.find(myTag.getId()).getName());
  }

  @Test
  public void delete_deletesTagInDatabase_true() {
    Tag myTag = new Tag("Thai");
    myTag.save();
    myTag.delete();
    assertEquals (0, Tag.all().size());
  }
}
