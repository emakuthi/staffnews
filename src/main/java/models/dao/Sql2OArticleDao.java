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
        String sql = "INSERT INTO articles (content, departmentid) VALUES (:content,:departmentId)"; //if you change your model, be sure to update here as well!
        try (Connection con = DB.sql2o.open()) {
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
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery("SELECT * FROM articles")
                    .executeAndFetch(Article.class);
        }
    }

    @Override
    public List<Article> getAllArticlesByDepartment(int departmentId) {
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery("SELECT * FROM articles WHERE departmentId = :departmentId")
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(Article.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from articles WHERE id=:id";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from articles";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
