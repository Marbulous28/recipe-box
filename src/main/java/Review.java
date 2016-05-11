import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Review{
  private int id;
  private int stars;
  private String text_review;
  private String user_name;
  private int recipe_id;

  public Review(int stars, String text_review, String user_name, int recipe_id){
    this.stars = stars;
    this.text_review = text_review;
    this.user_name = user_name;
    this.recipe_id = recipe_id;
  }

  public int getId(){
    return id;
  }

  public int getRecipeId() {
    return recipe_id;
  }

  public String getUserName(){
    return user_name;
  }

  public int getStars() {
    return stars;
  }

  public String getDescription() {
    return text_review;
  }

  public static List<Review> all() {
    String sql = "SELECT * FROM reviews";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Review.class);
    }
  }

  @Override
  public boolean equals(Object otherReview) {
    if (!(otherReview instanceof Review)) {
      return false;
    } else {
      Review newReview = (Review) otherReview;
      return this.getUserName().equals(newReview.getUserName()) &&
             this.getDescription().equals(newReview.getDescription()) &&
             this.getStars() == newReview.getStars() &&
             this.getRecipeId() == newReview.getRecipeId();
    }
  }

  public void save(){
    String sql = "INSERT INTO reviews (user_name, text_review, stars, recipe_id) VALUES (:user_name, :text_review, :stars, :recipe_id)";
    try (Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true)
        .addParameter("user_name", this.user_name)
        .addParameter("text_review", this.text_review)
        .addParameter("stars", this.stars)
        .addParameter("recipe_id", this.recipe_id)
        .executeUpdate()
        .getKey();
    }
  }

  public static Review find(int id){
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE id = :id";
      Review user = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Review.class);
      return user;
    }
  }

  public void update(int stars, String text_review, String user_name, int recipe_id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE reviews SET user_name = :user_name, text_review = :text_review, stars = :stars, recipe_id = :recipe_id WHERE id = :id;" ;
      con.createQuery(sql)
      .addParameter("user_name", user_name)
      .addParameter("text_review", text_review)
      .addParameter("stars", stars)
      .addParameter("recipe_id", recipe_id)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM reviews WHERE id = :id";
      con.createQuery(sql)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

}
