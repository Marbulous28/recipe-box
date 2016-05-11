
import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
      DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/recipe_box_test", null, null);
    }

  @Override
  protected void after() {
      try(Connection con = DB.sql2o.open()) {
        String deleteIngredientsQuery = "DELETE FROM ingredients *;";
        String deleteRecipesQuery = "DELETE FROM recipes *;";
        String deleteTagQuery = "DELETE FROM tags *;";
        String deleteReviewQuery = "DELETE FROM reviews *;";
        con.createQuery(deleteIngredientsQuery).executeUpdate();
        con.createQuery(deleteRecipesQuery).executeUpdate();
        con.createQuery(deleteTagQuery).executeUpdate();
        con.createQuery(deleteReviewQuery).executeUpdate();
      }
    }

}
