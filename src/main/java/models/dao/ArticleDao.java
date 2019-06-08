package models.dao;

import models.Article;

import java.util.List;

public interface ArticleDao {
    //create
    void add(Article article);

    //read
    List<Article> getAll();
    List<Article> getAllReviewsByRestaurant(int restaurantId);

    //update
    //omit for now

    //delete
    void deleteById(int id);
    void clearAll();
}