import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Tag{
  private int id;
  private String tag_name;

  public Tag(String name){
    tag_name = name;
  }

  public int getId(){
    return id;
  }

  public String getName(){
    return tag_name;
  }

  public static List<Tag> all() {
    String sql = "SELECT * FROM tags";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Tag.class);
    }
  }

  @Override
  public boolean equals(Object otherTag) {
    if (!(otherTag instanceof Tag)) {
      return false;
    } else {
      Tag newTag = (Tag) otherTag;
      return this.getName().equals(newTag.getName());
    }
  }

  public void save(){
    String sql = "INSERT INTO tags (tag_name) VALUES (:tag_name)";
    try (Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true)
        .addParameter("tag_name", this.tag_name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Tag find(int id){
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tags WHERE id = :id";
      Tag tag = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Tag.class);
      return tag;
    }
  }

  public void update(String tag_name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tags SET tag_name = :tag_name WHERE id =:id";
      con.createQuery(sql)
      .addParameter("tag_name", tag_name)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM tags WHERE id = :id";
      con.createQuery(sql)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

}
