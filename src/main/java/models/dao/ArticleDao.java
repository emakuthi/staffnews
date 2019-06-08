package models.dao;

import models.Article;

import java.util.List;

public interface ArticleDao {
    //create
    void add(Article article);

    //read
    List<Article> getAll();
    List<Article> getAllArticlesByDepartment(int departmentId);

    //update
    //omit for now

    //delete
    void deleteById(int id);
    void clearAll();
}