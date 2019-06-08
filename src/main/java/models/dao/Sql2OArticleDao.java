package models.dao;

import models.Article;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2OArticleDao implements ArticleDao {
    private final Sql2o sql2o;
    public Sql2OArticleDao(Sql2o sql2o) { this.sql2o = sql2o; }

    @Override
    public void add(Article article) {
        String sql = "INSERT INTO reviews (writtenby, content, rating, restaurantid) VALUES (:writtenBy, :content, :rating, :restaurantId)"; //if you change your model, be sure to update here as well!
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(article)
                    .executeUpdate()
                    .getKey();
            article.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Article> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM reviews")
                    .executeAndFetch(Article.class);
        }
    }

    @Override
    public List<Article> getAllReviewsByRestaurant(int restaurantId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM reviews WHERE restaurantId = :restaurantId")
                    .addParameter("restaurantId", restaurantId)
                    .executeAndFetch(Article.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from reviews WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from reviews";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
