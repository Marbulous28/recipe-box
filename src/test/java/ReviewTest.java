import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class ReviewTest{

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Review_instantiatesCorrectly_true(){
    Review newReview = new Review(1, "a", "a", 1);
    assertEquals(true, newReview instanceof Review);
  }

  @Test
  public void getName_returnsCorrectName_String(){
    Review newReview = new Review(1, "a", "b", 2);
    assertEquals("b", newReview.getUserName());
  }

  @Test
  public void getDescription_returnsCorrectDescription_String(){
    Review newReview = new Review(1, "a", "b", 2);
    assertEquals("a", newReview.getDescription());
  }

  @Test
  public void all_emptyAtFirst(){
    assertEquals(Review.all().size(), 0);
  }

  @Test
  public void equals_returnTrueIfObjectsAreSame() {
    Review firstReview = new Review(1, "a", "b", 2);
    Review secondReview = new Review(1, "a", "b", 2);
    assertTrue(firstReview.equals(secondReview));
  }

  @Test
  public void save_savesCorrectly(){
    Review saveReview = new Review(1, "a", "b", 2);
    saveReview.save();
    assertEquals(1, Review.all().size());
  }

  @Test
  public void getId_returnsCorrectId(){
    Review newReview = new Review(1, "a", "b", 2);
    newReview.save();
    Review otherReview = Review.all().get(0);
    assertEquals(otherReview.getId(), newReview.getId());
  }

  @Test
  public void find_findsReviewInDatabase_true() {
    Review newReview = new Review(1, "a", "b", 2);
    newReview.save();
    Review foundReview = Review.find(newReview.getId());
    assertTrue(newReview.equals(foundReview));
  }

  @Test
  public void update_updatesReviewInDatabase_true() {
    Review myReview = new Review(1, "a", "b", 2);
    myReview.save();
    myReview.update(4, "c", "e", 3);
    assertEquals("e", Review.find(myReview.getId()).getUserName());
  }

  @Test
  public void delete_deletesReviewInDatabase_true() {
    Review myReview = new Review(1, "a", "b", 2);
    myReview.save();
    myReview.delete();
    assertEquals (0, Review.all().size());
  }
}
